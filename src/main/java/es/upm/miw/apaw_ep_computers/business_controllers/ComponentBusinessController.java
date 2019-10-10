package es.upm.miw.apaw_ep_computers.business_controllers;

import es.upm.miw.apaw_ep_computers.daos.ComponentDao;
import es.upm.miw.apaw_ep_computers.documents.Component;
import es.upm.miw.apaw_ep_computers.dtos.ComponentDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ComponentBusinessController {

        private ComponentDao componentDao;

        @Autowired
        public ComponentBusinessController(ComponentDao componentDao) {
            this.componentDao = componentDao;
        }

        public ComponentDto create(ComponentDto componentDto) {
            Component component = new Component(componentDto.getType(), componentDto.getName(), componentDto.getCost(), componentDto.getModel());
            this.componentDao.save(component);
            return new ComponentDto(component);
        }

        public List<ComponentDto> readAll() {
            List<Component> components = this.componentDao.findAll();
            return components.stream().map(ComponentDto::new).collect(Collectors.toList());
        }
}
