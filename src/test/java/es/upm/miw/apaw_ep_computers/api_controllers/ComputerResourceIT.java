package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.ApiTestConfig;
import es.upm.miw.apaw_ep_computers.dtos.ComponentDto;
import es.upm.miw.apaw_ep_computers.dtos.ComputerDto;
import es.upm.miw.apaw_ep_computers.dtos.SupplierDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
public class ComputerResourceIT {
    private SupplierDto supplierDto;
    private List<String> components = new ArrayList<String>();

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void before() {
        this.supplierDto = this.webTestClient
                .post().uri(SupplierResource.SUPPLIERS)
                .body(BodyInserters.fromObject(new SupplierDto("Amazon", 10)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(SupplierDto.class).returnResult().getResponseBody();

        ComponentDto componentDto = this.webTestClient
                .post().uri(ComponentResource.COMPONENTS)
                .body(BodyInserters.fromObject(new ComponentDto("cpu", "intel core i5",150,"7400")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ComponentDto.class).returnResult().getResponseBody();
        components.add(componentDto.getId());
    }

    @Test
    void testCreateComputer() {
        ComputerDto computerDto = this.webTestClient
                .post().uri(ComputerResource.COMPUTERS)
                .body(BodyInserters.fromObject(new ComputerDto("cpu", 160.5,150.5,true, this.supplierDto.getId(), this.components)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ComputerDto.class).returnResult().getResponseBody();
        assertNotNull(computerDto);
        assertEquals("cpu", computerDto.getDescription());
        assertEquals((Double)160.5, computerDto.getPrice());
        assertEquals((Double)150.5, computerDto.getCost());
        assertEquals(true, computerDto.getIsStocked());
        assertEquals(supplierDto.getId(), computerDto.getSupplierId());
        assertEquals(components, computerDto.getComponentsId());
    }

    @Test
    void testCreateComputerExceptionBadRequest() {
        ComputerDto computerDto = new ComputerDto("cpu",160.5, 150.5,true, this.supplierDto.getId(), this.components);
        this.webTestClient
                .post().uri(ComponentResource.COMPONENTS)
                .body(BodyInserters.fromObject(computerDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateComputerExceptionBadPrice() {
        ComputerDto computerDto = new ComputerDto("cpu",140.5, 150.5,true, this.supplierDto.getId(), this.components);
        this.webTestClient
                .post().uri(ComputerResource.COMPUTERS)
                .body(BodyInserters.fromObject(computerDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateComputerExceptionBadSupplierNotFound() {
        ComputerDto computerDto = new ComputerDto("cpu",160.5, 150.5,true, "badSupplier", this.components);
        this.webTestClient
                .post().uri(ComputerResource.COMPUTERS)
                .body(BodyInserters.fromObject(computerDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateComputerExceptionNullComponentList() {
        ComputerDto computerDto = new ComputerDto("cpu",160.5, 150.5,true, this.supplierDto.getId(), null);
        this.webTestClient
                .post().uri(ComputerResource.COMPUTERS)
                .body(BodyInserters.fromObject(computerDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
