package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.ApiTestConfig;
import es.upm.miw.apaw_ep_computers.daos.ComponentDao;
import es.upm.miw.apaw_ep_computers.daos.ComputerDao;
import es.upm.miw.apaw_ep_computers.daos.SupplierDao;

import es.upm.miw.apaw_ep_computers.documents.Component;
import es.upm.miw.apaw_ep_computers.documents.Computer;
import es.upm.miw.apaw_ep_computers.documents.Supplier;
import es.upm.miw.apaw_ep_computers.dtos.ComponentDto;

import org.junit.Assert;
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
class ComponentResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ComputerDao computerDao;

    @Autowired
    private SupplierDao supplierDao;

    @Autowired
    private ComponentDao componentDao;

    @Test
    void testCreate() {
        ComponentDto componentDto = createComponentAndReturn("cpu", "intel core i5",150d,"7400", true);
        assertNotNull(componentDto);
        assertEquals("cpu", componentDto.getType());
        assertEquals("intel core i5", componentDto.getName());
        assertEquals(150, componentDto.getCost());
        assertEquals("7400", componentDto.getModel());
    }

    @Test
    void testCreateComponentException() {
        ComponentDto componentDto = new ComponentDto("cpu","intel", 0,null, true);
        this.webTestClient
                .post().uri(ComponentResource.COMPONENTS)
                .body(BodyInserters.fromObject(componentDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    void createComponent(String type, String name, Double cost, String model, Boolean isComposite){
        this.webTestClient
                .post().uri(ComponentResource.COMPONENTS)
                .body(BodyInserters.fromObject(new ComponentDto(type, name, cost, model, isComposite)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ComponentDto.class).returnResult().getResponseBody();
    }

    ComponentDto createComponentAndReturn(String type, String name, Double cost, String model, Boolean isComposite){
        return this.webTestClient
                .post().uri(ComponentResource.COMPONENTS)
                .body(BodyInserters.fromObject(new ComponentDto(type, name, cost, model, isComposite)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ComponentDto.class).returnResult().getResponseBody();
    }

    void createSupplierAndComputer(String description, Double price, Double cost, Boolean isStocked, String component){
        Supplier supplier = new Supplier("supplier1", 10.0);
        this.supplierDao.save(supplier);
        List<Component> list = new ArrayList<>();
        if(this.componentDao.findById(component).isPresent())
            list.add(this.componentDao.findById(component).get());
        Computer computer = new Computer(description, price, cost, isStocked, supplier, list);
        this.computerDao.save(computer);
    }

    @Test
    void testGetSearchType() {
        createComponent("cpu","intel core i5",150.0,"7400", true);
        createComponent("cpu","intel core i7",180.0,"8400", false);
        createComponent("cpu","intel core i7",200.90,"9700", true);
        createComponent("memory","Samsung HDD",50.0,"HDD-1TB", false);

        List<ComponentDto> components = this.webTestClient
                .get().uri(uriBuilder ->
                        uriBuilder.path(ComponentResource.COMPONENTS + ComponentResource.SEARCH)
                                .queryParam("q", "type:=cpu")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ComponentDto.class)
                .returnResult().getResponseBody();
        assertFalse(components != null && components.isEmpty());
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
        ComponentDto componentDto = createComponentAndReturn("component1", "intel core i5",150d,"7400", true);
        this.webTestClient
                .delete().uri(ComponentResource.COMPONENTS + ComponentResource.ID_ID, componentDto.getId())
                .exchange().expectStatus().isOk();
        this.webTestClient
                .get().uri(ComponentResource.COMPONENTS + ComponentResource.ID_ID, componentDto.getId())
                .exchange().expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteComponentIdWithReferences() {
        ComponentDto componentDto = createComponentAndReturn("component2", "intel core i3",125d,"6100", true);
        createSupplierAndComputer("pc",500.0d,400.0d,true, componentDto.getId() );
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
    void testDeleteNoContent() {
        ComponentDto componentDto = this.webTestClient
                .delete().uri(ComponentResource.COMPONENTS + ComponentResource.ID_ID, "example")
                .exchange().expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(ComponentDto.class).returnResult().getResponseBody();
        Assert.assertNull(componentDto);
    }
}
