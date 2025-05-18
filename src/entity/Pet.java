package entity;

import entity.enums.PetGender;
import entity.enums.PetType;


public class Pet {
    private final String name;
    private final PetType petType;
    private final PetGender petGender;
    private final PetAddress petAddress;
    private final int petAge;
    private final double petWeight;
    private final String petBreed;


    Pet(String name, PetType petType, PetGender petGender, PetAddress petAddress, int petAge, double petWeight, String petBreed) {
        this.name = name;
        this.petType = petType;
        this.petGender = petGender;
        this.petAddress = petAddress;
        this.petAge = petAge;
        this.petWeight = petWeight;
        this.petBreed = petBreed;
    }

    public String getName() {
        return name;
    }

    public PetType getPetType() {
        return petType;
    }

    public PetGender getPetGender() {
        return petGender;
    }

    public PetAddress getPetAddress() { return petAddress; }

    public Integer getPetAge() {
        return petAge;
    }

    public Double getPetWeight() {
        return petWeight;
    }

    public String getPetBreed() {
        return petBreed;
    }

}
