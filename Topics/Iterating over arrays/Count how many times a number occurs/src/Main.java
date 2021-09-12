import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        String data = scanner.nextLine();

        Pattern pattern = Pattern.compile(scanner.nextLine());
        Matcher matcher = pattern.matcher(data);

        System.out.println(matcher.results().count());
    }
}