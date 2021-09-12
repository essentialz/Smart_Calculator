package calculator;

public enum Request {
    CONTINUE(null, null),
    UNKNOWN_COMMAND(null, Error.UNKNOWN_COMMAND.getMessage()),
    EMPTY("", null),
    HELP("/help", "The program will calculate expressions like these: 3 + 8 * ((4 + 3) * 2 + 1) - 6 / (2^(2 + 1)), and so on."),
    EXIT("/exit", "Bye!");

    String option;
    String message;

    Request(String option, String message) {
        this.option = option;
        this.message = message;
    }

    public static Request get(String input) {
        for (Request r : Request.values()) {
            if (input.equalsIgnoreCase(r.option)) {
                return r;
            }
        }

        return input.startsWith("/") ? UNKNOWN_COMMAND : CONTINUE;
    }

    public String getMessage() {
        return message;
    }
}
