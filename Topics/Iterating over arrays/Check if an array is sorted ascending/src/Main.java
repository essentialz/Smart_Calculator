import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] numbers = scanner.tokens().skip(1).mapToInt(Integer::parseInt).toArray();
        int[] sorted = numbers.clone();
        Arrays.sort(sorted);

        System.out.println(Arrays.equals(numbers, sorted));
    }
}