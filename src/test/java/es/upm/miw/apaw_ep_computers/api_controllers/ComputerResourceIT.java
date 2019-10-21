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
        ComputerDto computerDto = postComputerDto("cpu",160.5,150.5,true);
        assertNotNull(computerDto);
        assertEquals("cpu", computerDto.getDescription());
        assertEquals((Double)160.5, computerDto.getPrice());
        assertEquals((Double)150.5, computerDto.getCost());
        assertEquals(true, computerDto.getIsStocked());
        assertEquals(supplierDto.getId(), computerDto.getSupplierId());
        assertEquals(components, computerDto.getComponentsId());
    }

    public ComputerDto postComputerDto(String description, Double price, Double cost, Boolean isStocked) {
        ComputerDto computerDto = this.webTestClient
                .post().uri(ComputerResource.COMPUTERS)
                .body(BodyInserters.fromObject(new ComputerDto(description, price, cost, isStocked, this.supplierDto.getId(), this.components)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ComputerDto.class).returnResult().getResponseBody();
        return computerDto;
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
        ComputerDto computerDto = new ComputerDto("computer",160.5, 150.5,true, this.supplierDto.getId(), null);
        this.webTestClient
                .post().uri(ComputerResource.COMPUTERS)
                .body(BodyInserters.fromObject(computerDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUpdateDescriptionComputer(){
        ComputerDto firstComputerDto = postComputerDto("computer",500.0,450.0,true);
        firstComputerDto.setDescription("gpu");

        this.webTestClient
                .put().uri(ComputerResource.COMPUTERS + ComputerResource.ID_ID + ComputerResource.DESCRIPTION, firstComputerDto.getId())
                .body(BodyInserters.fromObject(firstComputerDto))
                .exchange()
                .expectStatus().isOk();
        ComputerDto secondComputerDto = this.webTestClient
                .get().uri(ComputerResource.COMPUTERS + ComputerResource.ID_ID + ComputerResource.DESCRIPTION, firstComputerDto.getId())
                .exchange().expectStatus().isOk()
                .expectBody(ComputerDto.class).returnResult().getResponseBody();

        assertEquals(firstComputerDto.getDescription(), secondComputerDto.getDescription());
    }

    @Test
    void testUpdateDescriptionComputerExceptionBadRequest() {
        ComputerDto computerDto = postComputerDto("cpu",100.0,90.0,true);
        computerDto.setDescription("gpu");

        this.webTestClient
                .put().uri(ComputerResource.COMPUTERS + ComputerResource.ID_ID + ComputerResource.DESCRIPTION, computerDto.getId())
                .body(BodyInserters.fromObject(this.supplierDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUpdateDescriptionExceptionNotFound() {
        ComputerDto computerDto = postComputerDto("cpu",160.5,150.5,true);
        computerDto.setDescription("gpu");

        this.webTestClient
                .put().uri(ComputerResource.COMPUTERS + ComputerResource.ID_ID + ComputerResource.DESCRIPTION, 123)
                .body(BodyInserters.fromObject(computerDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testPatchPrices() {
        ComputerDto computerDto1 = postComputerDto("computer1",1000.0,750.6,true);
        ComputerDto computerDto2 = postComputerDto("computer2",1200.5,950.5,false);
        ComputerDto computerDto3 = postComputerDto("computer3",800.0,550.5,true);

        String id1 = computerDto1.getId();
        String id2 = computerDto2.getId();
        String id3 = computerDto3.getId();

        List<ComputerDto> list = new ArrayList<>();
        list.add(new ComputerDto(id1,900.0));
        list.add(new ComputerDto(id2,1100.0));
        list.add(new ComputerDto(id3,950.0));

        this.webTestClient
                .patch().uri(ComputerResource.COMPUTERS)
                .body(BodyInserters.fromObject(list))
                .exchange()
                .expectStatus().isOk();
        ComputerDto computerResponse1 = this.webTestClient
                .get().uri(ComputerResource.COMPUTERS + ComputerResource.ID_ID, id1)
                .exchange().expectStatus().isOk()
                .expectBody(ComputerDto.class).returnResult().getResponseBody();
        ComputerDto computerResponse2 = this.webTestClient
                .get().uri(ComputerResource.COMPUTERS + ComputerResource.ID_ID, id2)
                .exchange().expectStatus().isOk()
                .expectBody(ComputerDto.class).returnResult().getResponseBody();
        ComputerDto computerResponse3 = this.webTestClient
                .get().uri(ComputerResource.COMPUTERS + ComputerResource.ID_ID, id3)
                .exchange().expectStatus().isOk()
                .expectBody(ComputerDto.class).returnResult().getResponseBody();

        assertEquals((Double)900.0, computerResponse1.getPrice());
        assertEquals((Double)1100.0, computerResponse2.getPrice());
        assertEquals((Double)950.0, computerResponse3.getPrice());
    }

    @Test
    void testPatchPricesWithFullBody() {
        ComputerDto computerDto1 = postComputerDto("computer1",1000.0,750.6,true);
        ComputerDto computerDto2 = postComputerDto("computer2",1200.5,950.5,false);

        String id1 = computerDto1.getId();
        String id2 = computerDto2.getId();

        computerDto1.setPrice(200.0);
        computerDto2.setPrice(300.0);

        List<ComputerDto> list = new ArrayList<>();
        list.add(computerDto1);
        list.add(computerDto2);

        this.webTestClient
                .patch().uri(ComputerResource.COMPUTERS)
                .body(BodyInserters.fromObject(list))
                .exchange()
                .expectStatus().isOk();
        ComputerDto computerResponse1 = this.webTestClient
                .get().uri(ComputerResource.COMPUTERS + ComputerResource.ID_ID, id1)
                .exchange().expectStatus().isOk()
                .expectBody(ComputerDto.class).returnResult().getResponseBody();
        ComputerDto computerResponse2 = this.webTestClient
                .get().uri(ComputerResource.COMPUTERS + ComputerResource.ID_ID, id2)
                .exchange().expectStatus().isOk()
                .expectBody(ComputerDto.class).returnResult().getResponseBody();

        assertEquals((Double)200.0, computerResponse1.getPrice());
        assertEquals((Double)300.0, computerResponse2.getPrice());
    }

    @Test
    void testPatchPricesExceptionBadRequest() {
        ComputerDto computerDto1 = postComputerDto("computer1",1000.0,750.6,true);
        ComputerDto computerDto2 = postComputerDto("computer2",1200.5,950.5,false);

        String id1 = computerDto1.getId();
        String id2 = computerDto2.getId();

        computerDto1.setPrice(200.0);
        computerDto2.setPrice(300.0);

        List<ComputerDto> list = new ArrayList<>();
        list.add(computerDto1);
        list.add(computerDto2);

        this.webTestClient
                .patch().uri(ComputerResource.COMPUTERS + ComputerResource.ID_ID, id1)
                .body(BodyInserters.fromObject(list))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
