package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.ApiTestConfig;
import es.upm.miw.apaw_ep_computers.dtos.ReviewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
public class ReviewResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateReview() {
        ReviewDto reviewDto = this.webTestClient
                .post().uri(ReviewResource.REVIEWS)
                .body(BodyInserters.fromObject(new ReviewDto("This is a review", 5)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReviewDto.class).returnResult().getResponseBody();
        assertNotNull(reviewDto);
        assertEquals("This is a review", reviewDto.getDescription());
        assertEquals((Integer)5, reviewDto.getValuation());
    }

    @Test
    void testCreateReviewException() {
        ReviewDto reviewDto = new ReviewDto(null,0);
        this.webTestClient
                .post().uri(ReviewResource.REVIEWS)
                .body(BodyInserters.fromObject(reviewDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
