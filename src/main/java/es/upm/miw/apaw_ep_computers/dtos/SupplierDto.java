package es.upm.miw.apaw_ep_computers.dtos;

import es.upm.miw.apaw_ep_computers.documents.Supplier;
import es.upm.miw.apaw_ep_computers.exceptions.BadRequestException;

public class SupplierDto {
    private String id;

    private String name;

    private Double margin;

    public SupplierDto(String name, double margin) {
        this.name = name;
        this.margin = margin;
    }

    public SupplierDto(Supplier supplier) {
        this.id = supplier.getId();
        this.name = supplier.getName();
        this.margin = supplier.getMargin();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getMargin() {
        return this.margin;
    }

    public void validate() {
        if (this.name == null || this.name.isEmpty() || this.margin == null ) {
            throw new BadRequestException("Incomplete SupplierDto. ");
        }
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id='" + this.id + '\'' +
                ", name=" + this.name +
                ", margin='" + this.margin + '\'' +
                '}';
    }
}
