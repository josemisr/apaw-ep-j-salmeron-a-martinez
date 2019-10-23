package es.upm.miw.apaw_ep_computers.dtos;

import es.upm.miw.apaw_ep_computers.documents.Review;
import es.upm.miw.apaw_ep_computers.exceptions.BadRequestException;

public class ReviewDto {

    private String id;

    private String description;

    private Integer valuation;

    public ReviewDto(){
        //empty on purpose
    }

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.description = review.getDescription();
        this.valuation = review.getValuation();
    }

    public static Builder builder(){
        return new Builder();
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getValuation() {
        return this.valuation;
    }

    public void validate() {
        if (this.description == null || this.description.isEmpty() || this.valuation == null ) {
            throw new BadRequestException("Incomplete ReviewDto. ");
        }
    }

    public static class Builder{

        private ReviewDto review;

        public Builder(){
            this.review = new ReviewDto();
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

        public ReviewDto build(){
            return this.review;
        }
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + this.id + '\'' +
                ", description=" + this.description +
                ", valuation='" + this.valuation + '\'' +
                '}';
    }
}
