package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Computer {
    @Id
    private String id;

    private String description;

    private Double price;

    private Double cost;

    private Boolean isStocked;

    @DBRef
    private Supplier supplier;

    @DBRef
    private List<Component> components;

    public Computer(String description, double price, double cost, boolean isStocked, Supplier supplier, List<Component> components){
        this.description = description;
        this.price = price;
        this.cost = cost;
        this.isStocked = isStocked;
        this.supplier = supplier;
        this.components = components;
    }

    public String getId() {return id; }

    public String getDescription() { return description; }

    public double getPrice() { return price; }

    public double getCost() { return cost; }

    public boolean getIsStocked() { return isStocked; }

    public Supplier getSupplier() {return supplier; }

    public void setDescription(String description){
        this.description = description;
    }

    public List<Component> getComponents() {return components; }

    @Override
    public String toString() {
        return "Computer{" +
                "id='" + id + '\'' +
                ", description=" + description +
                ", price=" + price +
                ", cost=" + cost +
                ", isStocked=" + isStocked +
                ", supplier='" + supplier + '\'' +
                '}';
    }

}