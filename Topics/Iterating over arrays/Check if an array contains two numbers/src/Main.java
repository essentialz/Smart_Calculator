import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] numbers = new int[size];
        scanner.nextLine();

        for (int i = 0; i < size; i++) {
            numbers[i] = scanner.nextInt();
        }

        scanner.nextLine();
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();

        System.out.println(hasPair(numbers, size, num1, num2));
    }

    private static boolean hasPair(int[] numbers, int size, int num1, int num2) {
        for (int i = 0; i < size - 1; i++) {
            if (numbers[i] == num1 && numbers[i + 1] == num2
                    || numbers[i] == num2 && numbers[i + 1] == num1) {
                return true;
            }
        }

        return false;
    }
}