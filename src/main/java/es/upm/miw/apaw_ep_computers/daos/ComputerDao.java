package es.upm.miw.apaw_ep_computers.daos;

import es.upm.miw.apaw_ep_computers.documents.Computer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComputerDao extends MongoRepository<Computer, String> {
}
