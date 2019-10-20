package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.business_controllers.ReviewBusinessController;
import es.upm.miw.apaw_ep_computers.dtos.ReviewDto;
import es.upm.miw.apaw_ep_computers.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ReviewResource.REVIEWS)
public class ReviewResource {

    static final String REVIEWS = "/reviews";
    static final String SEARCH = "/search";

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

    @GetMapping(value = SEARCH)
    public List<ReviewDto> find(@RequestParam String q) {
        if (!"valuation".equals(q.split(":=")[0])) {
            throw new BadRequestException("query param q is incorrect, missing 'valuation:='");
        }
        return this.reviewBusinessController.findByValuationEqual(q.split(":=")[1]);
    }
}
