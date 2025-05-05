package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetValidator {
    private static final String simpleRegex = "^[A-Za-z]{4,}$";
    private static final String nameRegex = "^[\\p{L}'-]{2,}(?: [\\p{L}'-]+)*$";

    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(
                nameRegex,
                Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE
        );
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidStreet(String street) {
        return street != null && street.matches(simpleRegex);
    }

    public static boolean isValidPetAddressNumber(Integer number) {
        return number == null || number > 0;
    }

    public static boolean isValidNeighborhood(String neighborhood) {
        return neighborhood != null && neighborhood.matches(simpleRegex);
    }

    public static boolean isValidAge(Integer age) {
        return age == null || (age >= 0 && age <= 20);
    }

    public static boolean isValidWeight(Double weight) {
        return weight == null || (weight >= 0.5 && weight <= 60);
    }

    public static boolean isValidBreed(String breed) {
        return breed != null && breed.matches(simpleRegex);
    }
}
