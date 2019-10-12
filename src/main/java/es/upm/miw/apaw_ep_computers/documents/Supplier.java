package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Supplier {
    @Id
    private String id;

    private String name;

    private Double margin;

    public Supplier(String name, Double margin) {
        this.name = name;
        this.margin = margin;
    }

    public String getId() { return this.id; }

    public String getName() {
        return this.name;
    }

    public Double getMargin() {
        return this.margin;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", margin=" + margin + '\'' +
                '}';
    }
}
