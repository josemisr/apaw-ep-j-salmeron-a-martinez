package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.ApiTestConfig;
import es.upm.miw.apaw_ep_computers.dtos.SupplierDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
public class SupplierResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateSupplier() {
        SupplierDto supplierDto = this.webTestClient
                .post().uri(SupplierResource.SUPPLIERS)
                .body(BodyInserters.fromObject(new SupplierDto("Amazon", 10)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(SupplierDto.class).returnResult().getResponseBody();
        assertNotNull(supplierDto);
        assertEquals("Amazon", supplierDto.getName());
        assertEquals(10, supplierDto.getMargin());
    }

    @Test
    void testCreateSupplierException() {
        SupplierDto supplierDto = new SupplierDto(null,0);
        this.webTestClient
                .post().uri(SupplierResource.SUPPLIERS)
                .body(BodyInserters.fromObject(supplierDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
