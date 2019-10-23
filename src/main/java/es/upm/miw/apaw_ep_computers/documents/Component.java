package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public abstract class Component {
    @Id
    private String id;

    private String type;

    private String name;

    private Double cost;

    private String model;

    @DBRef
    private List<Computer> computers;

    public Component(String type, String name, double cost, String model) {
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.model = model;
        this.computers = new ArrayList<>();
    }

    public String getId() { return id; }

    public String getType() {
            return type;
        }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String getModel() { return model; }

    public List<Computer> getComputers() {return computers; }

    public abstract void addComponent(Component component);

    public abstract void removeComponent(Component component);

    public abstract Boolean isComposite();

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