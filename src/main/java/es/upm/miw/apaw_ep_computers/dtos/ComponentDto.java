package es.upm.miw.apaw_ep_computers.dtos;

import es.upm.miw.apaw_ep_computers.documents.Component;
import es.upm.miw.apaw_ep_computers.exceptions.BadRequestException;

public class ComponentDto {
    private String id;

    private String type;

    private String name;

    private Double cost;

    private String model;

    private Boolean isComposite;

    public ComponentDto() {
        // empty for framework
    }

    public ComponentDto(String type, String name, double cost, String model, boolean isComposite) {
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.model = model;
        this.isComposite = isComposite;
    }

    public ComponentDto(Component component) {
        this.id = component.getId();
        this.type = component.getType();
        this.name = component.getName();
        this.cost = component.getCost();
        this.model = component.getModel();
        this.isComposite = component.isComposite();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean getIsComposite() { return isComposite; }

    public void validate() {
        if (type == null || type.isEmpty() || name == null || name.isEmpty() || model == null || model.isEmpty() ) {
            throw new BadRequestException("Incomplete ComponentDto. ");
        }
    }

    @Override
    public String toString() {
        return "Component{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", name=" + name +
                ", cost=" + cost +
                ", model='" + model + '\'' +
                '}';
    }
}
