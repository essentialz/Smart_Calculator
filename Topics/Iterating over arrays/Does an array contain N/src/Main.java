import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size =  scanner.nextInt();
        scanner.nextLine();

        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }

        scanner.nextLine();

        int value = scanner.nextInt();
        boolean found = false;

        for (int i = 0; i < size && !found; i++) {
            if (array[i] == value) {
                found = true;
            }
        }

        System.out.println(found);
    }
}
