package calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private final Map<String, Integer> PRECEDENCE = Map.of(
            "+", 1,
            "-", 1,
            "/", 2,
            "*", 2,
            "^", 3
    );

    private final Map<String, BigDecimal> variables = new HashMap<>();

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        Request request = Request.get(input);

        while (request != Request.EXIT) {
                switch (request) {
                    case HELP:
                    case UNKNOWN_COMMAND:
                        System.out.println(request.getMessage());
                        break;
                    case CONTINUE:
                        try {
                            if (isDigits(input) || isLetters(input)) {
                                System.out.println(getValue(input));
                            } else if (input.contains("=")) {
                                processAssignment(input);
                            } else {
                                String postfix = prefixToPostfix(input);
                                System.out.println(processPostfix(postfix));
                            }
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }

                        break;
                }

            input = scanner.nextLine().trim();
            request = Request.get(input);
        }

        System.out.println(request.getMessage());
    }

    private void processAssignment(String input) {
        String[] args = input.split("\\s*=\\s*");

        if (!isLetters(args[0])) {
            throw new RuntimeException(Error.INVALID_IDENTIFIER.getMessage());
        }

        if (args.length > 2 || !isDigits(args[1]) && !isLetters(args[1])) {
            throw new RuntimeException(Error.INVALID_ASSIGNMENT.getMessage());
        }

        variables.put(args[0], getValue(args[1]));
    }
    
    private BigDecimal getValue(String input) {
        BigDecimal value;

        try {
            value = new BigDecimal(input);
        } catch (NumberFormatException e) {
            value = getVariable(input);
        }

        return value;
    }

    private BigDecimal getVariable(String key) {
        return Optional.ofNullable(variables.get(key)).orElseThrow(() ->
                new RuntimeException(Error.UNKNOWN_VARIABLE.getMessage()));
    }

    private String prefixToPostfix(String input) {
        final String INVALID_INPUT = "(?i)((/(?=/))|(\\*(?=\\*))|(\\^(?=\\^))|[a-z]+\\d|\\d[a-z]+|[^ a-z()^\\d/*+-])";

        if (input.matches(INVALID_INPUT)) {
            throw new RuntimeException(Error.INVALID_EXPRESSION.getMessage());
        }

        final String QUERY = "(?i)([a-z]+|(?<=^|\\s|\\(|\\^)-?\\d+|\\d+|\\++|-+|\\*|/|\\(|\\)|\\^)";
        Deque<String> stack = new ArrayDeque<>();
        Matcher matcher = Pattern.compile(QUERY).matcher(input);
        StringBuilder postfix = new StringBuilder();

        matcher.results().forEach(m -> {
            if (isDigits(m.group()) || isLetters(m.group())) {
                postfix.append(m.group()).append(" ");
            } else {
                if ("(".equals(m.group())) {
                    stack.offerLast(m.group());
                } else if (")".equals(m.group())) {
                    boolean foundPair = false;

                    while (!stack.isEmpty() && !foundPair) {
                        postfix.append(stack.pollLast()).append(" ");
                        foundPair = "(".equals(stack.peekLast());
                    }

                    if (!foundPair) {
                        throw new RuntimeException(Error.INVALID_EXPRESSION.getMessage());
                    }

                    stack.pollLast();
                } else {
                    String operator = simplifyOperator(m.group());

                    if (stack.isEmpty() || "(".equals(stack.peekLast()) ||
                            PRECEDENCE.get(operator) > PRECEDENCE.get(stack.peekLast())) {
                        stack.offerLast(operator);
                    } else if (PRECEDENCE.get(operator) <= PRECEDENCE.get(stack.peekLast())) {
                        while (!stack.isEmpty() && !"(".equals(stack.peekLast()) &&
                                PRECEDENCE.get(operator) <= PRECEDENCE.get(stack.peekLast())) {
                            postfix.append(stack.pollLast()).append(" ");
                        }

                        stack.offerLast(operator);
                    }
                }
            }
        });

        while (!stack.isEmpty()) {
            postfix.append(stack.pollLast()).append(" ");
        }

        return postfix.toString().trim();
    }

    private BigDecimal processPostfix(String input) {
        String[] values = input.split("\\s+");
        Deque<BigDecimal> stack = new ArrayDeque<>();

        for (String v: values) {
            if (isDigits(v) || isLetters(v)) {
                stack.offerLast(getValue(v));
            } else {
                BigDecimal num1 = Optional.ofNullable(stack.pollLast())
                        .orElseThrow(() -> new RuntimeException(Error.INVALID_EXPRESSION.getMessage()));

                BigDecimal num2 = Optional.ofNullable(stack.pollLast())
                        .orElseThrow(() -> new RuntimeException(Error.INVALID_EXPRESSION.getMessage()));

                switch (v) {
                    case "+":
                        stack.offerLast(num2.add(num1));
                        break;
                    case "-":
                        stack.offerLast(num2.subtract(num1));
                        break;
                    case "*":
                        stack.offerLast(num2.multiply(num1));
                        break;
                    case "/":
                        BigDecimal result = num2.divide(num1, 10, RoundingMode.HALF_UP);
                        stack.offerLast(result.setScale(calcScale(result), RoundingMode.HALF_UP));
                        break;
                    case "^":
                        stack.offerLast(num2.pow(num1.intValue()));
                        break;
                    default:
                        throw new RuntimeException(Error.INVALID_EXPRESSION.getMessage());
                }
            }
        }

        return Optional.ofNullable(stack.pollLast())
                .orElseThrow(() -> new RuntimeException(Error.INVALID_EXPRESSION.getMessage()));
    }

    private boolean isDigits(String value) {
        final String NUMBER = "^-?\\d+$";

        return value.matches(NUMBER);
    }

    private boolean isLetters(String value) {
        final String LETTERS = "(?i)^[a-z]+$";

        return value.matches(LETTERS);
    }

    private String simplifyOperator(String op) {
        final String MULTI_ADD = "^\\+{2,}$";
        final String MULTI_SUB = "^-{2,}$";
        final String OPERATOR = "^([*/^+-])$";

        if (op.matches(OPERATOR)) {
            return op;
        } else if (op.matches(MULTI_ADD)) {
            return "+";
        } else if (op.matches(MULTI_SUB)) {
            return op.length() % 2 == 0 ? "+" : "-";
        } else {
            throw new RuntimeException(Error.INVALID_EXPRESSION.getMessage());
        }
    }

    private int calcScale(BigDecimal number) {
        if (!number.toString().matches("^\\d+.0+$")) {
            int start = number.toString().indexOf(".");

            for (int i = number.toString().length() - 1; i > start; i--) {
                if (number.toString().substring(i, i + 1).matches("^[1-9]$")) {
                    return i - start ;
                }
            }
        }

        return 0;
    }
}