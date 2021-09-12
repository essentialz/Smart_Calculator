import java.util.Map;
import java.util.Scanner;


class CheckTheEssay {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        Map<String, String> wordMap = Map.of(
                "Franse", "France",
                "Eifel tower", "Eiffel Tower",
                "19th", "XIXth",
                "20th", "XXth",
                "21st", "XXIst"
                );

        for (Map.Entry<String, String> entry: wordMap.entrySet()) {
            text = text.replaceAll(entry.getKey(), entry.getValue());
        }

        System.out.println(text);
    }
}