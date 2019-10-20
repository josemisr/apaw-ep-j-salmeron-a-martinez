package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.business_controllers.ComponentBusinessController;
import es.upm.miw.apaw_ep_computers.dtos.ComponentDto;

import es.upm.miw.apaw_ep_computers.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ComponentResource.COMPONENTS)
public class ComponentResource {

    static final String COMPONENTS = "/components";
    static final String SEARCH = "/search";
    static final String ID_ID = "/{id}";

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

    @GetMapping(value = SEARCH)
    public List<ComponentDto> find(@RequestParam String q) {
        if (!"type".equals(q.split(":=")[0])) {
            throw new BadRequestException("query param q is incorrect, missing 'type:='");
        }
        return this.componentBusinessController.findByTypeEqual(q.split(":=")[1]);
    }

    @GetMapping(value = ID_ID)
    public ComponentDto get(@PathVariable String id){
        return this.componentBusinessController.get(id);
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id){
        this.componentBusinessController.deleteReferencedComponents(id);
        this.componentBusinessController.delete(id);
    }
}