package entity;

import entity.enums.PetGender;
import entity.enums.PetType;

import java.util.Objects;

public class PetBuilder {
    private String name = "NOT INFORMED";
    private PetType petType;
    private PetGender petGender;
    private PetAddress petAddress;
    private int petAge = -1;
    private double petWeight = -1.0;
    private String petBreed = "NOT INFORMED";

    public PetBuilder withName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
        return this;
    }

    public PetBuilder withType(PetType type) {
        this.petType = Objects.requireNonNull(type, "PetType is required");
        return this;
    }

    public PetBuilder withGender(PetGender gender) {
        this.petGender = Objects.requireNonNull(gender, "PetGender is required");
        return this;
    }

    public PetBuilder withAddress(PetAddress address) {
        this.petAddress = Objects.requireNonNull(address, "PetAddress is required");
        return this;
    }

    public PetBuilder withAge(int age) {
        this.petAge = age;
        return this;
    }

    public PetBuilder withWeight(double weight) {
        this.petWeight = weight;
        return this;
    }

    public PetBuilder withBreed(String breed) {
        if (breed != null && !breed.isEmpty()) {
            this.petBreed = breed;
        }
        return this;
    }

    public Pet build() {
        if (petType == null || petGender == null || petAddress == null) {
            throw new IllegalStateException("Required fields were not filled in");
        }

        return new Pet(name, petType, petGender, petAddress, petAge, petWeight, petBreed);
    }

}
