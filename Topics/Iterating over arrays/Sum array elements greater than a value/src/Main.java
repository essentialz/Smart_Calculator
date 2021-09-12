import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        scanner.nextLine();
        int[] numbers = new int[size];

        for (int i = 0; i < size; i++) {
            numbers[i] = scanner.nextInt();
        }

        scanner.nextLine();
        int min = scanner.nextInt();
        int sum = 0;

        for (int i = 0; i < size; i++) {
            sum += numbers[i] > min ? numbers[i] : 0;
        }

        System.out.println(sum);
    }
}
