package es.upm.miw.apaw_ep_computers.dtos;

import es.upm.miw.apaw_ep_computers.documents.Address;
import es.upm.miw.apaw_ep_computers.documents.Client;
import es.upm.miw.apaw_ep_computers.documents.Computer;
import es.upm.miw.apaw_ep_computers.exceptions.BadRequestException;

import java.time.LocalDateTime;
import java.util.List;

public class ClientDto {

    private String id;

    private String idCard;

    private String name;

    private LocalDateTime birthDate;

    private Address address;

    private List<Computer> computers;

    public ClientDto() {
        // empty for framework
    }

    public ClientDto(String idCard, String name, LocalDateTime birthDate) {
        this.idCard = idCard;
        this.name = name;
        this.birthDate = birthDate;
    }

    public ClientDto(Client client) {
        this.id = client.getId();
        this.idCard = client.getIdCard();
        this.name = client.getName();
        this.birthDate = client.getBirthDate();
    }

    public String getId() {
        return id;
    }
    public String getIdCard() {
        return idCard;
    }
    public String getName() {
        return name;
    }
    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void validate() {
        if (idCard == null || idCard.isEmpty() || name == null || name.isEmpty() || birthDate == null ) {
            throw new BadRequestException("Incomplete ClientDto. ");
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", idCard=" + idCard +
                ", name=" + name +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
