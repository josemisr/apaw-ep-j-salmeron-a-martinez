package es.upm.miw.apaw_ep_computers.dtos;

import es.upm.miw.apaw_ep_computers.documents.Client;
import es.upm.miw.apaw_ep_computers.exceptions.BadRequestException;

public class ClientBasicDto {

    private String id;

    private String name;

    public ClientBasicDto() {
        // empty for framework
    }

    public ClientBasicDto(Client client) {
        this.id =client.getId();
        this.name = client.getName();
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void validateName() {
        if (this.name == null || this.name.isEmpty()) {
            throw new BadRequestException("Incomplete, lost name");
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name=" + name +'\'' +
                '}';
    }
}
