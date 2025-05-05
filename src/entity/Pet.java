package entity;

import entity.enums.PetGender;
import entity.enums.PetType;
import exception.RequiredFieldException;

public class Pet {
    private String name;
    private PetType petType; // required
    private PetGender petGender; // required
    private PetAddress petAddress; // required
    private int petAge;
    private double petWeight;
    private String petBreed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
       if (name == null || name.isEmpty()) {
           this.name = "NOT INFORMED";
           return;
       }

        this.name = name;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        if (petType == null) {
            throw new RequiredFieldException("petType is required, please provide a valid value.");
        }
        this.petType = petType;
    }

    public PetGender getPetGender() {
        return petGender;
    }

    public void setPetGender(PetGender petGender) {
        if (petGender == null) {
            throw new RequiredFieldException("petGender is required, please provide a valid value.");
        }
        this.petGender = petGender;
    }

    public PetAddress getPetAddress() {
        return petAddress;
    }

    public void setPetAddress(PetAddress petAddress) {
        if (petAddress == null) {
            throw new RequiredFieldException("petAddress is required, please provide a valid value.");
        }
        this.petAddress = petAddress;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }

    public double getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(double petWeight) {
        this.petWeight = petWeight;
    }


    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

}
