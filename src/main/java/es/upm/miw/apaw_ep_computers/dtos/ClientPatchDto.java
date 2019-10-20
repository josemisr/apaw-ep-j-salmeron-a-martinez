package es.upm.miw.apaw_ep_computers.dtos;

import es.upm.miw.apaw_ep_computers.documents.Client;
import es.upm.miw.apaw_ep_computers.exceptions.BadRequestException;

public class ClientPatchDto {

    private String id;

    private String name;

    private String idCard;


    public ClientPatchDto() {
        // empty for framework
    }

    public ClientPatchDto(Client client) {
        this.id =client.getId();
        this.name = client.getName();
        this.idCard = client.getIdCard();
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getIdCard() {
        return idCard;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void validate() {
        if ((this.name == null || this.name.isEmpty()) && (this.idCard == null || this.idCard.isEmpty())) {
            throw new BadRequestException("Incomplete, Patch");
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name=" + name +'\'' +
                ", idCard=" + idCard +'\'' +
                '}';
    }
}
