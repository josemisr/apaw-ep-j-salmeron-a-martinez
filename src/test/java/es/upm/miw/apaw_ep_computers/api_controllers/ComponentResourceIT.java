package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.ApiTestConfig;
import es.upm.miw.apaw_ep_computers.dtos.ComponentDto;

import es.upm.miw.apaw_ep_computers.dtos.ComputerDto;
import es.upm.miw.apaw_ep_computers.dtos.SupplierDto;

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
public class ComponentResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        ComponentDto componentDto = createComponentAndReturn("cpu", "intel core i5",150d,"7400");
        assertNotNull(componentDto);
        assertEquals("cpu", componentDto.getType());
        assertEquals("intel core i5", componentDto.getName());
        assertEquals(150, componentDto.getCost());
        assertEquals("7400", componentDto.getModel());
    }

    @Test
    void testCreateComponentException() {
        ComponentDto componentDto = new ComponentDto("cpu","intel", 0,null);
        this.webTestClient
                .post().uri(ComponentResource.COMPONENTS)
                .body(BodyInserters.fromObject(componentDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    void createComponent(String type, String name, Double cost, String model){
        this.webTestClient
                .post().uri(ComponentResource.COMPONENTS)
                .body(BodyInserters.fromObject(new ComponentDto(type, name, cost, model)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ComponentDto.class).returnResult().getResponseBody();
    }

    ComponentDto createComponentAndReturn(String type, String name, Double cost, String model){
        return this.webTestClient
                .post().uri(ComponentResource.COMPONENTS)
                .body(BodyInserters.fromObject(new ComponentDto(type, name, cost, model)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ComponentDto.class).returnResult().getResponseBody();
    }

    void createSupplierAndComputer(String description, Double price, Double cost, Boolean isStocked, List<String> components){
        SupplierDto supplierDto = this.webTestClient
                .post().uri(SupplierResource.SUPPLIERS)
                .body(BodyInserters.fromObject(new SupplierDto("Amazon", 10)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(SupplierDto.class).returnResult().getResponseBody();
        this.webTestClient
                .post().uri(ComputerResource.COMPUTERS)
                .body(BodyInserters.fromObject(new ComputerDto(description, price, cost, isStocked, supplierDto.getId(), components)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ComputerDto.class).returnResult().getResponseBody();
    }

    @Test
    void testGetSearchType() {
        createComponent("cpu","intel core i5",150.0,"7400");
        createComponent("cpu","intel core i7",180.0,"8400");
        createComponent("cpu","intel core i7",200.90,"9700");
        createComponent("memory","Samsung HDD",50.0,"HDD-1TB");

        List<ComponentDto> components = this.webTestClient
                .get().uri(uriBuilder ->
                        uriBuilder.path(ComponentResource.COMPONENTS + ComponentResource.SEARCH)
                                .queryParam("q", "type:=cpu")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ComponentDto.class)
                .returnResult().getResponseBody();
        assertFalse(components.isEmpty());
    }

    @Test
    void testGetSearchTypeBadQueryException() {
        this.webTestClient
                .get().uri(uriBuilder ->
                uriBuilder.path(ComponentResource.COMPONENTS + ComponentResource.SEARCH)
                        .queryParam("q", "types:=cpu")
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteComponentIdWithNoReferences() {
        ComponentDto componentDto = createComponentAndReturn("cpu", "intel core i5",150d,"7400");
        this.webTestClient
                .delete().uri(ComponentResource.COMPONENTS + ComponentResource.ID_ID, componentDto.getId())
                .exchange().expectStatus().isOk();
        this.webTestClient
                .get().uri(ComponentResource.COMPONENTS + ComponentResource.ID_ID, componentDto.getId())
                .exchange().expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteComponentIdWithReferences() {
        ComponentDto componentDto = createComponentAndReturn("cpu", "intel core i5",150d,"7400");
        List<String> comps = new ArrayList<>();
        comps.add(componentDto.getId());
        createSupplierAndComputer("pc",500.0d,400.0d,true, comps );
        this.webTestClient
                .delete().uri(ComponentResource.COMPONENTS + ComponentResource.ID_ID, componentDto.getId())
                .exchange().expectStatus().isOk();
        this.webTestClient
                .get().uri(ComponentResource.COMPONENTS + ComponentResource.ID_ID, componentDto.getId())
                .exchange().expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteExceptionBadRequest() {
        this.webTestClient
                .delete().uri(ComponentResource.COMPONENTS, "comp123")
                .exchange().expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteExceptionNoContent() {
        this.webTestClient
                .delete().uri(ComponentResource.COMPONENTS + ComponentResource.ID_ID, "1")
                .exchange().expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
