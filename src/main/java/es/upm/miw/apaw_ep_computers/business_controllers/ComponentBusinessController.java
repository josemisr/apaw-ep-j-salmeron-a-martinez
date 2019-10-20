package es.upm.miw.apaw_ep_computers.business_controllers;

import es.upm.miw.apaw_ep_computers.daos.ComponentDao;
import es.upm.miw.apaw_ep_computers.daos.ComputerDao;
import es.upm.miw.apaw_ep_computers.documents.Component;
import es.upm.miw.apaw_ep_computers.documents.Computer;
import es.upm.miw.apaw_ep_computers.dtos.ComponentDto;

import es.upm.miw.apaw_ep_computers.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ComponentBusinessController {

    private ComponentDao componentDao;
    private ComputerDao computerDao;

    @Autowired
    public ComponentBusinessController(ComponentDao componentDao, ComputerDao computerDao) {
        this.componentDao = componentDao;
        this.computerDao = computerDao;
    }

    public ComponentDto create(ComponentDto componentDto) {
        Component component = new Component(componentDto.getType(), componentDto.getName(), componentDto.getCost(), componentDto.getModel());
        this.componentDao.save(component);
        return new ComponentDto(component);
    }

    public List<ComponentDto> findByTypeEqual(String value) {
        return this.componentDao.findAll().stream()
                .filter(component -> component.getType().equals(value))
                .map(ComponentDto::new)
                .collect(Collectors.toList());
    }

    public ComponentDto get(String id) {
        Component component = this.componentDao.findById(id).orElseThrow(() -> new NotFoundException("Computer id: " + id));
        return new ComponentDto(component);
    }

    public void delete(String id) {
        this.componentDao.deleteById(id);
    }

    public void deleteReferencedComponents(String componentId) {
        List<Computer> computers = this.computerDao.findAll();
        if (computers.size() > 0) {
        for (Computer computer : computers)
            computer.getComponents().removeIf(comp -> comp.getId().equals(componentId));
        }
    }
}
