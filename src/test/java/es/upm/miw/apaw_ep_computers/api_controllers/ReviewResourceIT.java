package es.upm.miw.apaw_ep_computers.api_controllers;

import es.upm.miw.apaw_ep_computers.ApiTestConfig;
import es.upm.miw.apaw_ep_computers.dtos.ReviewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
public class ReviewResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    ReviewDto createReviewAndReturn(String description, Integer valuation){
        return this.webTestClient
                .post().uri(ReviewResource.REVIEWS)
                .body(BodyInserters.fromObject(new ReviewDto(description, valuation)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReviewDto.class).returnResult().getResponseBody();
    }

    @Test
    void testCreateReview() {
        ReviewDto reviewDto = createReviewAndReturn("This is a review",5);
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

    @Test
    void testSearchValuation() {
        createReviewAndReturn("review 1",4);
        createReviewAndReturn("review 2",2);
        createReviewAndReturn("review 3",4);
        createReviewAndReturn("review 4",0);
        createReviewAndReturn("review 5",5);

        List<ReviewDto> reviews = this.webTestClient
                .get().uri(uriBuilder ->
                        uriBuilder.path(ReviewResource.REVIEWS + ReviewResource.SEARCH)
                                .queryParam("q", "valuation:=0")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ReviewDto.class)
                .returnResult().getResponseBody();
        assertTrue(reviews.size() > 0);
    }

    @Test
    void testSearchValuationExceptionBadRequest() {
        this.webTestClient
                .get().uri(uriBuilder ->
                uriBuilder.path(ReviewResource.REVIEWS + ReviewResource.SEARCH)
                        .queryParam("q", "valuations:=5")
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
