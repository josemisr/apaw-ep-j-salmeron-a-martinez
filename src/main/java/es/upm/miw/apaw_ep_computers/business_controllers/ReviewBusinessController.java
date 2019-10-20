package es.upm.miw.apaw_ep_computers.business_controllers;

import es.upm.miw.apaw_ep_computers.daos.ReviewDao;
import es.upm.miw.apaw_ep_computers.documents.Review;
import es.upm.miw.apaw_ep_computers.dtos.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ReviewBusinessController {

    private ReviewDao reviewDao;

    @Autowired
    public ReviewBusinessController(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    public ReviewDto create(ReviewDto reviewDto) {
        Review review = new Review(reviewDto.getDescription(), reviewDto.getValuation());
        this.reviewDao.save(review);
        return new ReviewDto(review);
    }
}
