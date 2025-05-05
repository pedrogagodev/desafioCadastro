package entity;

import exception.RequiredFieldException;

public class PetAddress {
    private String street; // required
    private int number;
    private String neighborhood; // required

    public PetAddress(String street, int number, String neighborhood) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street == null) {
            throw new RequiredFieldException("street is required, please provide a valid value.");
        }
        this.street = street;
    }

    public int getNumber() { return number; }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        if (neighborhood == null) {
            throw new RequiredFieldException("neighborhood is required, please provide a valid value.");
        }
        this.neighborhood = neighborhood;
    }


    @Override
    public String toString() {
        return street + ", " + (number == -1 ? "NOT INFORMED" : number) + ", " + neighborhood;
    }
}
