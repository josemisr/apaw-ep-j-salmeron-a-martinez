package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Review {
    @Id
    private String id;

    private String description;

    private Integer valuation;

    public Review(String description, Integer valuation){
        this.description = description;
        this.valuation = valuation;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Integer getValuation() {
        return valuation;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", description=" + description +
                ", valuation=" + valuation + '\'' +
                '}';
    }
}
