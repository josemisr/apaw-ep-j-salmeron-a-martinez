package es.upm.miw.apaw_ep_computers.daos;

import es.upm.miw.apaw_ep_computers.documents.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplierDao extends MongoRepository<Supplier, String> {
}
