package es.upm.miw.apaw_ep_computers.business_controllers;

import es.upm.miw.apaw_ep_computers.daos.ComputerDao;
import es.upm.miw.apaw_ep_computers.daos.SupplierDao;
import es.upm.miw.apaw_ep_computers.daos.ComponentDao;
import es.upm.miw.apaw_ep_computers.documents.Computer;
import es.upm.miw.apaw_ep_computers.documents.Supplier;
import es.upm.miw.apaw_ep_computers.documents.Component;
import es.upm.miw.apaw_ep_computers.dtos.ComputerDto;

import es.upm.miw.apaw_ep_computers.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ComputerBusinessController {

    private ComputerDao computerDao;

    private SupplierDao supplierDao;

    private ComponentDao componentDao;

    @Autowired
    public ComputerBusinessController(ComputerDao computerDao, SupplierDao supplierDao, ComponentDao componentDao) {
        this.computerDao = computerDao;
        this.supplierDao = supplierDao;
        this.componentDao =componentDao;
    }

    public ComputerDto create(ComputerDto computerDto) {
        Supplier supplier = supplierDao.findById(computerDto.getSupplierId())
                .orElseThrow(() -> new NotFoundException("Supplier id: " + computerDto.getSupplierId()));
        List<Component> components = (List<Component>) componentDao.findAllById(computerDto.getComponentsId());

        Computer computer = new Computer(computerDto.getDescription(), computerDto.getPrice(), computerDto.getCost(), computerDto.getIsStocked(), supplier, components);
        this.computerDao.save(computer);
        return new ComputerDto(computer);
    }

}
