package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.business_controllers.ComputerBusinessController;
import es.upm.miw.apaw_ep_computers.dtos.ComputerDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ComputerResource.COMPUTERS)
public class ComputerResource {

    static final String COMPUTERS = "/computers";

    private ComputerBusinessController computerBusinessController;

    @Autowired
    public ComputerResource(ComputerBusinessController computerBusinessController) {
        this.computerBusinessController = computerBusinessController;
    }

    @PostMapping
    public ComputerDto create(@RequestBody ComputerDto computerDto) {
        computerDto.validate();
        return this.computerBusinessController.create(computerDto);
    }
}