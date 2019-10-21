package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.business_controllers.ClientBusinessController;
import es.upm.miw.apaw_ep_computers.dtos.ClientBasicDto;
import es.upm.miw.apaw_ep_computers.dtos.ClientDto;
import es.upm.miw.apaw_ep_computers.dtos.ClientPatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ClientResource.CLIENTS)
public class ClientResource {
    static final String CLIENTS = "/clients";
    static final String ID_ID = "/{id}";
    static final String NAME = "/name";

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

    @GetMapping(value = ID_ID)
    public ClientBasicDto readById(@PathVariable String id) {
        return this.clientBusinessController.readById(id);
    }

    @PutMapping(value = ID_ID + NAME)
    public void updateName(@PathVariable String id, @RequestBody ClientBasicDto clientDto) {
        clientDto.validateName();
        this.clientBusinessController.updateName(id, clientDto.getName());
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) {
        this.clientBusinessController.deleteClient(id);
    }

    @PatchMapping(value = ID_ID)
    public void patch(@PathVariable String id, @RequestBody ClientPatchDto clientPatchDto) {
        clientPatchDto.validate();
        this.clientBusinessController.patch(id, clientPatchDto);
    }
}
