package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.business_controllers.ClientBusinessController;
import es.upm.miw.apaw_ep_computers.dtos.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ClientResource.CLIENTS)
public class ClientResource {
    static final String CLIENTS = "/clients";

    private ClientBusinessController clientBusinessController;

    @Autowired
    public ClientResource(ClientBusinessController clientBusinessController) {
        this.clientBusinessController = clientBusinessController;
    }

    @PostMapping
    public ClientDto create(@RequestBody ClientDto clientDto) {
        clientDto.validate();
        return this.clientBusinessController.create(clientDto);
    }
}
