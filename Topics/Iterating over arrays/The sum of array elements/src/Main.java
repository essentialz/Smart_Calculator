import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        scanner.nextLine();

        int sum = 0;

        for (int i = 0; i < size; i++) {
            sum += scanner.nextInt();
        }

        System.out.println(sum);
    }
}