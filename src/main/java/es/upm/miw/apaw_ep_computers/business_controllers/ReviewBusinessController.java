package es.upm.miw.apaw_ep_computers.business_controllers;

import es.upm.miw.apaw_ep_computers.daos.ReviewDao;
import es.upm.miw.apaw_ep_computers.documents.Review;
import es.upm.miw.apaw_ep_computers.dtos.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReviewBusinessController {

    private ReviewDao reviewDao;

    @Autowired
    public ReviewBusinessController(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    public ReviewDto create(ReviewDto reviewDto) {
        Review review = Review.builder().description(reviewDto.getDescription()).valuation(reviewDto.getValuation()).build();
        this.reviewDao.save(review);
        return new ReviewDto(review);
    }

    public List<ReviewDto> findByValuationEqual(String value) {
        return this.reviewDao.findAll().stream()
                .filter(review -> review.getValuation().equals(Integer.valueOf(value)))
                .map(ReviewDto::new)
                .collect(Collectors.toList());
    }
}
