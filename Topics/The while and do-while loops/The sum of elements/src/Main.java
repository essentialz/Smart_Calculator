import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        int sum = 0;

        while (number != 0) {
            sum += number;
            number = scanner.nextInt();
        }

        System.out.println(sum);
    }
}