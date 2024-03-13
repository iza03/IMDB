package org.example;

public class Experience implements ExperienceStrategy {
    @Override
    public int calculateExperience(ActionType actionType) {
        return switch (actionType) {
            case REVIEW -> 2;
            case REQUEST -> 3;
            case ADD_ITEM -> 5;
            default -> 0;
        };
    }
}
