package es.upm.miw.apaw_ep_computers.business_controllers;

import es.upm.miw.apaw_ep_computers.daos.ClientDao;
import es.upm.miw.apaw_ep_computers.documents.Client;
import es.upm.miw.apaw_ep_computers.dtos.ClientBasicDto;
import es.upm.miw.apaw_ep_computers.dtos.ClientDto;
import es.upm.miw.apaw_ep_computers.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ClientBusinessController {

    private ClientDao clientDao;

    @Autowired
    public ClientBusinessController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public ClientDto create(ClientDto clientDto) {
        Client client = new Client(clientDto.getIdCard(), clientDto.getName(), clientDto.getBirthDate());
        this.clientDao.save(client);
        return new ClientDto(client);
    }

    private Client findClientByIdAssured(String id) {
        return this.clientDao.findById(id).orElseThrow(() -> new NotFoundException("Client id: " + id));
    }

    public ClientBasicDto readName(String id) {
        return new ClientBasicDto(this.findClientByIdAssured(id));
    }

    public void updateName(String id, String name) {
        Client client = this.findClientByIdAssured(id);
        client.setName(name);
        this.clientDao.save(client);
    }
}