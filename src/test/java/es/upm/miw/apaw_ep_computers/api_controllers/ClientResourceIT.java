package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.ApiTestConfig;
import es.upm.miw.apaw_ep_computers.daos.ClientDao;
import es.upm.miw.apaw_ep_computers.daos.ComputerDao;
import es.upm.miw.apaw_ep_computers.documents.Client;
import es.upm.miw.apaw_ep_computers.documents.Computer;
import es.upm.miw.apaw_ep_computers.dtos.*;
import es.upm.miw.apaw_ep_computers.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ApiTestConfig
public class ClientResourceIT {

    @Autowired
    private ComputerDao computerDao;

    @Autowired
    private ClientDao clientDao;

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

    ClientDto createClientWithOneComputer(String name, Computer computer){
        ClientDto clientDto= createClient("client");
        Client client = this.clientDao.findById(clientDto.getId()).orElseThrow(() -> new NotFoundException("Supplier id: " + clientDto.getId()));
        client.addComputer(computer);
        this.clientDao.save(client);
        return clientDto;
    }

    private Computer createComputer(){
        SupplierDto supplierDto;
        List<String> components = new ArrayList<String>();
        supplierDto = this.webTestClient
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

        ComputerDto computerDto = this.webTestClient
                .post().uri(ComputerResource.COMPUTERS)
                .body(BodyInserters.fromObject(new ComputerDto("cpu", 160.5,150.5,true, supplierDto.getId(), components)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ComputerDto.class).returnResult().getResponseBody();

        Computer computer =  this.computerDao.findById(computerDto.getId()).orElseThrow(() -> new NotFoundException("Supplier id: " + computerDto.getSupplierId()));
        return computer;
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

    @Test
    void testDeleteClient() {
        ClientDto clientDto = createClient("name");
        this.webTestClient
                .get().uri(ClientResource.CLIENTS + ClientResource.ID_ID + ClientResource.NAME, clientDto.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientDto.class)
                .returnResult().getResponseBody();

        this.webTestClient
                .delete().uri(ClientResource.CLIENTS + ClientResource.ID_ID + ClientResource.NAME, clientDto.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientBasicDto.class)
                .returnResult().getResponseBody();

        this.webTestClient
                .get().uri(ClientResource.CLIENTS + ClientResource.ID_ID + ClientResource.NAME, clientDto.getId())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteClientWithComputers() {
        Computer computer = createComputer();
        ClientDto clientDto = createClientWithOneComputer("prueba",computer);
        Computer computerExistent =  this.computerDao.findById(computer.getId()).orElse(null);
        assertEquals(computer.getId(),computerExistent.getId());
        this.webTestClient
                .delete().uri(ClientResource.CLIENTS + ClientResource.ID_ID + ClientResource.NAME, clientDto.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientBasicDto.class)
                .returnResult().getResponseBody();
        Computer computerNull =  this.computerDao.findById(computer.getId()).orElse(null);
        assertEquals(null,computerNull);
    }
}
