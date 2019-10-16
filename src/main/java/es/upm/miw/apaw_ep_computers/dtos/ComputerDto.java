package es.upm.miw.apaw_ep_computers.dtos;

import es.upm.miw.apaw_ep_computers.documents.Computer;
import es.upm.miw.apaw_ep_computers.exceptions.BadRequestException;

import java.util.List;
import java.util.stream.Collectors;

public class ComputerDto {
    private String id;

    private String description;

    private Double price;

    private Double cost;

    private Boolean isStocked;

    private String supplierId;

    List<String> componentsId;

    public ComputerDto() {
        // empty for framework
    }

    public ComputerDto(String description, Double price, Double cost, Boolean isStocked,String supplierId, List<String> componentsId ) {
        this.description = description;
        this.price = price;
        this.cost = cost;
        this.isStocked = isStocked;
        this.supplierId = supplierId;
        this.componentsId = componentsId;
    }

    public ComputerDto(Computer computer) {
        this.id =computer.getId();
        this.description = computer.getDescription();
        this.price = computer.getPrice();
        this.cost = computer.getCost();
        this.isStocked = computer.getIsStocked();
        this.supplierId = computer.getSupplier().getId();
        this.componentsId = computer.getComponents().stream().map(obj-> obj.getId()).collect(Collectors.toList());
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getPrice() {
        return this.price;
    }

    public Double getCost() {
        return this.cost;
    }

    public Boolean getIsStocked() {
        return this.isStocked;
    }

    public String getSupplierId() {
        return this.supplierId;
    }

    public List<String> getComponentsId() {
        return this.componentsId;
    }

    public void validate() {
        if (description == null || description.isEmpty() ||this.price == null || this.price < 0 || this.cost == null || this.cost < 0 ||  this.isStocked == null ||  this.supplierId == null ||  this.componentsId == null ||  this.componentsId.size() <= 0) {
            throw new BadRequestException("Incomplete ComputerDto. ");
        }
        if (this.price < this.cost) {
            throw new BadRequestException("The price can not be greater than cost. ");
        }
    }

    @Override
    public String toString() {
        return "Computer{" +
                "id='" + id + '\'' +
                ", description=" + this.description +
                ", price=" + this.price +
                ", cost=" + this.cost +
                ", isStocked='" + this.isStocked + '\'' +
                '}';
    }
}
