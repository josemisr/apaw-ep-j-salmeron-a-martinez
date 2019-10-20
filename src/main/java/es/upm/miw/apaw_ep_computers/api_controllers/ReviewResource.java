package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.business_controllers.ReviewBusinessController;
import es.upm.miw.apaw_ep_computers.dtos.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ReviewResource.REVIEWS)
public class ReviewResource {

    static final String REVIEWS = "/reviews";

    private ReviewBusinessController reviewBusinessController;

    @Autowired
    public ReviewResource(ReviewBusinessController reviewBusinessController) {
        this.reviewBusinessController = reviewBusinessController;
    }

    @PostMapping
    public ReviewDto create(@RequestBody ReviewDto reviewDto) {
        reviewDto.validate();
        return this.reviewBusinessController.create(reviewDto);
    }
}
