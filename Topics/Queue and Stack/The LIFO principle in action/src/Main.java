import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deque<String> stack = new ArrayDeque<>();

        scanner.tokens().skip(1).forEach(stack::offerFirst);
        stack.forEach(System.out::println);
    }
}