package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.ApiTestConfig;
import es.upm.miw.apaw_ep_computers.dtos.ClientDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
public class ClientResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        ClientDto clientDto = this.webTestClient
                .post().uri(ClientResource.CLIENTS)
                .body(BodyInserters.fromObject(new ClientDto("00000000T", "paco", LocalDateTime.of(2000, Month.JANUARY,01,0,0))))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientDto.class).returnResult().getResponseBody();
        assertNotNull(clientDto);
        assertEquals("00000000T", clientDto.getIdCard());
        assertEquals("paco", clientDto.getName());
        assertEquals(LocalDateTime.of(2000,Month.JANUARY,01,0,0), clientDto.getBirthDate());
    }

    @Test
    void testCreateClientException() {
        ClientDto clientDto = new ClientDto("12345678Z",null, LocalDateTime.of(1990,Month.DECEMBER,31,0,0));
        this.webTestClient
                .post().uri(ClientResource.CLIENTS)
                .body(BodyInserters.fromObject(clientDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
