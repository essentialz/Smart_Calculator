import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.tokens().skip(1)
                .mapToInt(Integer::parseInt)
                .min()
                .ifPresent(System.out::println);
    }
}