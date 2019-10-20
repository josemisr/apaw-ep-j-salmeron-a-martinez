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

    public ReviewDto(String description, Integer valuation) {
        this.description = description;
        this.valuation = valuation;
    }

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.description = review.getDescription();
        this.valuation = review.getValuation();
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

    @Override
    public String toString() {
        return "Review{" +
                "id='" + this.id + '\'' +
                ", description=" + this.description +
                ", valuation='" + this.valuation + '\'' +
                '}';
    }
}
