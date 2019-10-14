package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.ApiTestConfig;
import es.upm.miw.apaw_ep_computers.dtos.ClientBasicDto;
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
        ClientDto clientDto =createClient("paco");
        assertNotNull(clientDto);
        assertEquals("00000000T", clientDto.getIdCard());
        assertEquals("paco", clientDto.getName());
        assertEquals(LocalDateTime.of(2000,Month.JANUARY,01,0,0), clientDto.getBirthDate());
    }

    ClientDto createClient(String name){
        ClientDto clientDto = this.webTestClient
                .post().uri(ClientResource.CLIENTS)
                .body(BodyInserters.fromObject(new ClientDto("00000000T", name, LocalDateTime.of(2000, Month.JANUARY,01,0,0))))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientDto.class).returnResult().getResponseBody();
        return clientDto;
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
    @Test
    void testPutName() {
        String id = createClient("name").getId();
        ClientBasicDto clientDto = new ClientBasicDto();
        clientDto.setName("newName");
        this.webTestClient
                .put().uri(ClientResource.CLIENTS + ClientResource.ID_ID + ClientResource.NAME, id)
                .body(BodyInserters.fromObject(clientDto))
                .exchange()
                .expectStatus().isOk();
        clientDto = this.webTestClient
                .get().uri(ClientResource.CLIENTS + ClientResource.ID_ID + ClientResource.NAME, id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientBasicDto.class)
                .returnResult().getResponseBody();
        assertEquals(id, clientDto.getId());
        assertEquals("newName", clientDto.getName());
    }

    @Test
    void testPutNameBadName() {
        String id = createClient("name").getId();
        ClientBasicDto clientDto = new ClientBasicDto();
        clientDto.setName("");
        this.webTestClient
                .put().uri(ClientResource.CLIENTS + ClientResource.ID_ID + ClientResource.NAME, id)
                .body(BodyInserters.fromObject(clientDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPutNameNotFoundException() {
        String id = createClient("name").getId();
        this.webTestClient
                .put().uri(ClientResource.CLIENTS + ClientResource.ID_ID + ClientResource.NAME, id)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPutNameBadRequestException() {
        this.webTestClient
                .put().uri(ClientResource.CLIENTS + ClientResource.ID_ID + ClientResource.NAME, "no-name")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
