package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.business_controllers.SupplierBusinessController;
import es.upm.miw.apaw_ep_computers.dtos.SupplierDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SupplierResource.SUPPLIERS)
public class SupplierResource {

    static final String SUPPLIERS = "/suppliers";

    private SupplierBusinessController supplierBusinessController;

    @Autowired
    public SupplierResource(SupplierBusinessController supplierBusinessController) {
        this.supplierBusinessController = supplierBusinessController;
    }

    @PostMapping
    public SupplierDto create(@RequestBody SupplierDto supplierDto) {
        supplierDto.validate();
        return this.supplierBusinessController.create(supplierDto);
    }

    @GetMapping
    public List<SupplierDto> readAll() {
        return this.supplierBusinessController.readAll();
    }
}