package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.business_controllers.ComponentBusinessController;
import es.upm.miw.apaw_ep_computers.dtos.ComponentDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ComponentResource.COMPONENTS)
public class ComponentResource {

    static final String COMPONENTS = "/components";

    private ComponentBusinessController componentBusinessController;

    @Autowired
    public ComponentResource(ComponentBusinessController componentBusinessController) {
        this.componentBusinessController = componentBusinessController;
    }

    @PostMapping
    public ComponentDto create(@RequestBody ComponentDto componentDto) {
        componentDto.validate();
        return this.componentBusinessController.create(componentDto);
    }
}