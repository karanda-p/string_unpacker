import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUnpacker {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String str = null;
        boolean isValid;
        do {
            System.out.println("Введите строку");
            str = scanner.next();
            isValid = isValid(str);
            if (!isValid) {
                System.out.println("Не верный формат строки");
            }
        } while (isValid);
        System.out.println(unpack(str));
    }

    public static String unpack(String str) {

        StringBuilder result = new StringBuilder(str);

        // Создаем регулярное выражение для поиска всех внутренних сжатых строк
        Matcher matcherBlocks = Pattern.compile("\\d+\\[\\w+\\]").matcher(str);
        //Добавляем все внешние строки в список
        List<String> blocks = new ArrayList<>();
        while (matcherBlocks.find()) {
            blocks.add(matcherBlocks.group());
        }

        //Из списка строк добавляем в список числа, определяющие количество появлений
        List<Integer> digits = new ArrayList<>();
        for (String s : blocks) {
            //Регулярное выражение для поиска чисел в строке
            Matcher matcherDigits = Pattern.compile("\\d+").matcher(s);
            while (matcherDigits.find()) {
                digits.add(Integer.valueOf(matcherDigits.group()));
            }
        }

        //Форматируем строки из исписка, удаляя скобки и числа
        //Затем, начиная с последней строки, раскрываем. Идем с конца из-за необходимости сохранять индексы
        for (int i = blocks.size() - 1; i >= 0; i--) {
            StringBuilder tmp = new StringBuilder();
            for (int j = 0; j < digits.get(i); j++) {
                //Удаление строк и чисел
                tmp.append(blocks.get(i).replaceAll("[\\d+\\[\\]]", ""));
            }
            result.replace(result.indexOf(blocks.get(i)), result.indexOf(blocks.get(i))
                    + blocks.get(i).length(), tmp.toString());

        }

        //Так как мы раскрыли только внутренние строки,
        //рекурсивно проведем такие же действия к полученной строке,
        //если остались скобки в целевой строке
        if (result.toString().contains("[") || result.toString().contains("]")) {
            return unpack(result.toString());
        } else {
            return result.toString();
        }
    }

    public static boolean isValid(String str) {

        if (str.equals("")){return false;}

        int iterations = 0;
        StringBuilder newStr = new StringBuilder(str);
        Matcher matcher = Pattern.compile("\\d+\\[\\w+\\]").matcher(newStr);


        List<String> blocks = new ArrayList<>();
        while (matcher.find()) {
            blocks.add(matcher.group());
        }

        for (String s : blocks) {
            newStr.replace(newStr.indexOf(s), newStr.indexOf(s) + s.length(), "");
            iterations++;
        }

        if (newStr.toString().matches("[a-zA-Z]+") || newStr.toString().equals("")) {
            return true;
        } else if (iterations == 0) {
            return false;
        } else if (iterations != 0) {
            return isValid(newStr.toString());
        }
        return false;
    }
}
