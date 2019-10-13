package es.upm.miw.apaw_ep_computers.daos;

import es.upm.miw.apaw_ep_computers.documents.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientDao extends MongoRepository<Client, String> {
}
