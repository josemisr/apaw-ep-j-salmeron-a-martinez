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
}
