package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.ApiTestConfig;
import es.upm.miw.apaw_ep_computers.dtos.ComponentDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
public class ComponentResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        ComponentDto componentDto = this.webTestClient
                .post().uri(ComponentResource.COMPONENTS)
                .body(BodyInserters.fromObject(new ComponentDto("cpu", "intel core i5",150,"7400")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ComponentDto.class).returnResult().getResponseBody();
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

    void createComponent(String type,String name, Double cost, String model){
        this.webTestClient
                .post().uri(ComponentResource.COMPONENTS)
                .body(BodyInserters.fromObject(new ComponentDto(type, name,cost,model)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ComponentDto.class).returnResult().getResponseBody();
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
        assertEquals(3, components.size());
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
}
