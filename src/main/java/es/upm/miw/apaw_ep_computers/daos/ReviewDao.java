package es.upm.miw.apaw_ep_computers.daos;

import es.upm.miw.apaw_ep_computers.documents.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewDao extends MongoRepository<Review, String> {
}
