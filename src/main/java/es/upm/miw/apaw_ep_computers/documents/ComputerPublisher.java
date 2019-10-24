package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@Document
public class ComputerPublisher {

    private EmitterProcessor<Computer> createComputer;

    public ComputerPublisher() {
        this.createComputer = EmitterProcessor.create();
    }

    public Flux<Computer> publisher() {
        return this.createComputer;
    }
}
