package es.upm.miw.apaw_ep_computers.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Address {

    private String streetName;

    private String streetNumber;

    private String zipCode;

    public Address(String streetName, String streetNumber, String zipCode) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
