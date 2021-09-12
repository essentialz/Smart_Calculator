package calculator;

public enum Error {
    INVALID_IDENTIFIER("Invalid identifier"),
    INVALID_ASSIGNMENT("Invalid assignment"),
    INVALID_EXPRESSION("Invalid expression"),
    UNKNOWN_VARIABLE("Unknown variable"),
    UNKNOWN_COMMAND("Unknown command");

    private final String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
