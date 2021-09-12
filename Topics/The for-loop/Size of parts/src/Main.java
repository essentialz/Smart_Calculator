import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] output = {0, 0, 0};

        scanner.tokens().skip(1)
                .mapToInt(Integer::parseInt)
                .forEach(r -> output[r == -1 ? 2 : r]++);

        System.out.printf("%d %d %d", output[0], output[1], output[2]);
    }
}