package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Review {
    @Id
    private String id;

    private String description;

    private Integer valuation;

    public Review(){
        //empty
    }

    public static Builder builder(){
        return new Builder();
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

    public static class Builder{

        private Review review;

        public Builder(){
            this.review = new Review();
        }
        public Builder id(String id){
            this.review.id =id;
            return this;
        }

        public Builder description(String description){
            this.review.description =description;
            return this;
        }

        public Builder valuation(Integer valuation){
            this.review.valuation =valuation;
            return this;
        }

        public Review build(){
            return this.review;
        }
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
