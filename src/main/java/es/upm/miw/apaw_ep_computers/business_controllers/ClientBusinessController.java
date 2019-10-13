package es.upm.miw.apaw_ep_computers.business_controllers;

import es.upm.miw.apaw_ep_computers.daos.ClientDao;
import es.upm.miw.apaw_ep_computers.daos.ComponentDao;
import es.upm.miw.apaw_ep_computers.documents.Client;
import es.upm.miw.apaw_ep_computers.documents.Component;
import es.upm.miw.apaw_ep_computers.dtos.ClientDto;
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
}
