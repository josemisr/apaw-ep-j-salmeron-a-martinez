package es.upm.miw.apaw_ep_computers.business_controllers;

import es.upm.miw.apaw_ep_computers.daos.SupplierDao;
import es.upm.miw.apaw_ep_computers.documents.Supplier;
import es.upm.miw.apaw_ep_computers.dtos.SupplierDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SupplierBusinessController {

    private SupplierDao supplierDao;

    @Autowired
    public SupplierBusinessController(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    public SupplierDto create(SupplierDto supplierDto) {
        Supplier supplier = new Supplier(supplierDto.getName(), supplierDto.getMargin());
        this.supplierDao.save(supplier);
        return new SupplierDto(supplier);
    }

    public List<SupplierDto> readAll() {
        List<Supplier> suppliers = this.supplierDao.findAll();
        return suppliers.stream().map(SupplierDto::new).collect(Collectors.toList());
    }
}
