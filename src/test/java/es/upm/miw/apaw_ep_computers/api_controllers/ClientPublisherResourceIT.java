package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.ApiTestConfig;
import es.upm.miw.apaw_ep_computers.documents.ClientPublisher;
import es.upm.miw.apaw_ep_computers.documents.Computer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ApiTestConfig
public class ClientPublisherResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testPublisher() {
        ClientPublisher clientPublisher = new ClientPublisher("idCard","name", LocalDateTime.now());
        String name ="newName";
        String idCard="newIdCard";
        Computer computer = new Computer("cpu",160.5,150.5,true,null,null);
        StepVerifier
                .create(clientPublisher.publisher())
                .then(() -> clientPublisher.setName(name))
                .expectNext("setted name:" + name)
                .then(() -> clientPublisher.setIdCard(idCard))
                .expectNext("setted idCard:" + idCard)
                .then(() -> clientPublisher.addComputer(computer))
                .expectNext("list size:" + 1)
                .then(() -> clientPublisher.addComputer(computer))
                .expectNext("list size:" + 2)
                .thenCancel()
                .verify();
    }

    @Test
    void testAdd() {
        ClientPublisher clientPublisher = new ClientPublisher("idCard","name", LocalDateTime.now());
        assertEquals("idCard", clientPublisher.getIdCard());
        assertEquals("name", clientPublisher.getName());
        String name ="newName";
        String idCard="newIdCard";
        clientPublisher.setIdCard(idCard);
        assertEquals(idCard, clientPublisher.getIdCard());
        clientPublisher.setName(name);
        assertEquals(name, clientPublisher.getName());
        Computer computer = new Computer("cpu",160.5,150.5,true,null,null);
        clientPublisher.addComputer(computer);
        assertTrue(clientPublisher.getComputers().size() > 0);
    }
}
