package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Document
public class Computer {

    @Id
    private String id;

    private String description;

    private double price;

    private double cost;

    private boolean isStocked;

    @DBRef
    private Supplier supplier;

    @DBRef
    private List<Component> components;

    public Computer(String description, double price, double cost, boolean isStocked, Supplier supplier){
        this.description = description;
        this.price = price;
        this.cost = cost;
        this.isStocked = isStocked;
        this.supplier = supplier;
        this.components = new ArrayList<>();
    }

    public String getId() {return id; }

    public String getDescription() { return description; }

    public double getPrice() { return price; }

    public double getCost() { return cost; }

    public boolean getIsStocked() { return isStocked; }

    public Supplier getSupplier() {return supplier; }

    public List<Component> getComponent() {return components; }

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
