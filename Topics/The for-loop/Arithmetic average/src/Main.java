import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int start = scanner.nextInt();
        int[] values = new int[scanner.nextInt() - start + 1];
        Arrays.parallelSetAll(values, i -> start + i);

        System.out.println(Arrays.stream(values)
                .filter(i -> i % 3 == 0)
                .average()
                .orElse(0));
    }
}