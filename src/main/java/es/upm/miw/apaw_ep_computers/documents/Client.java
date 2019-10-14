package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class Client {

    @Id
    private String id;

    private String idCard;

    private String name;

    private LocalDateTime birthDate;

    private Address address;

    private List<Computer> computers;

    public Client(String idCard, String name, LocalDateTime birthDate){
        this.idCard = idCard;
        this.name = name;
        this.birthDate = birthDate;
        this.computers = new ArrayList<>();
    }

    public String getId(){ return id; }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard(){ return idCard; }

    public String getName() { return name; }

    public LocalDateTime getBirthDate() { return birthDate; }

    public Address getAddress() { return address; }

    public List<Computer> getVotes() {
        return computers;
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
