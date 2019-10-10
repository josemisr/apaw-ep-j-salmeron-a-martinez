package es.upm.miw.apaw_ep_computers.daos;
import es.upm.miw.apaw_ep_computers.documents.Component;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComponentDao extends MongoRepository<Component, String> {
}
