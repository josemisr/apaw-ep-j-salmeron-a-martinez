package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.business_controllers.ComputerBusinessController;
import es.upm.miw.apaw_ep_computers.dtos.ComputerDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ComputerResource.COMPUTERS)
public class ComputerResource {

    static final String COMPUTERS = "/computers";
    static final String ID_ID = "/{id}";
    static final String DESCRIPTION = "/description";

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

    @GetMapping(value = ID_ID + DESCRIPTION)
    public ComputerDto getComputerDescription(@PathVariable String id){
        return this.computerBusinessController.getComputer(id);
    }

    @PutMapping(value = ID_ID + DESCRIPTION)
    public void update(@PathVariable String id, @RequestBody ComputerDto computer){
        computer.validateDescription();
        this.computerBusinessController.updateDescription(id, computer.getDescription());
    }

    @GetMapping(value = ID_ID)
    public ComputerDto getComputer(@PathVariable String id){
        return this.computerBusinessController.getComputer(id);
    }

    @PatchMapping
    public void updatePrices(@RequestBody List<ComputerDto> list){
        for (ComputerDto computerDto: list) {
            computerDto.validatePrice();
            this.computerBusinessController.updatePrice(computerDto);
        }
    }
}