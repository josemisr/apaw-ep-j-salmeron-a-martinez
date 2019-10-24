package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.yaml.snakeyaml.emitter.Emitter;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class ClientPublisher {

    @Id
    private String id;

    private String idCard;

    private String name;

    private LocalDateTime birthDate;

    private Address address;

    private List<Computer> computers;

    private EmitterProcessor<String> emitter;
    public ClientPublisher(String idCard, String name, LocalDateTime birthDate){
        this.idCard = idCard;
        this.name = name;
        this.birthDate = birthDate;
        this.emitter = EmitterProcessor.create();
    }

    public String getIdCard(){ return idCard; }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
        this.emitter.onNext("setted idCard:" + idCard);
    }

    public void setName(String name) {
        this.name = name;
        this.emitter.onNext("setted name:" + name);
    }

    public String getName(){ return name; }

    public void addComputer(Computer computer){
        if(this.computers == null){
            this.computers = new ArrayList<>();
        }
        this.computers.add(computer);
        this.emitter.onNext("list size:" + computers.size());
    }

    public List<Computer> getComputers() {
        return computers;
    }

    public Flux<String> publisher() {
        return this.emitter;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", idCard=" + idCard +
                ", name=" + name +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                '}';
    }
}
