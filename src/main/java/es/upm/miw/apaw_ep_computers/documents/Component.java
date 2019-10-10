package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Component {
    @Id
    private String id;

    private String type;

    private String name;

    private double cost;

    private String model;

    public Component(String type, String name, double cost, String model) {
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.model = model;
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