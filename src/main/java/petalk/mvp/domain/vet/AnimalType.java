package petalk.mvp.domain.vet;

public enum AnimalType {
    DOG, CAT, RABBIT, HAMSTER, GUINEA_PIG, CHINCHILLA, FERRET, TURTLE, SNAKE, LIZARD, BIRD, FISH, OTHER;

    public static AnimalType from(String value) {
        return AnimalType.valueOf(value.toUpperCase());
    }
}
