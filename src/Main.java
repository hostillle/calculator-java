import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.println("Введите выражение: ");

        String userInput = in.nextLine();

        System.out.println(calc(userInput));
    }

    // Конвертирует романские числа в нормальные
    public static int romanToInteger(String roman)
    {
        Map<Character,Integer> numbersMap = new HashMap<>();
        numbersMap.put('I',1);
        numbersMap.put('V',5);
        numbersMap.put('X',10);
        numbersMap.put('L',50);
        numbersMap.put('C',100);
        numbersMap.put('D',500);
        numbersMap.put('M',1000);

        int result=0;

        for(int i=0;i<roman.length();i++)
        {
            char ch = roman.charAt(i);      // Current Roman Character

            //Case 1
            if(i>0 && numbersMap.get(ch) > numbersMap.get(roman.charAt(i-1)))
            {
                result += numbersMap.get(ch) - 2*numbersMap.get(roman.charAt(i-1));
            }

            // Case 2: just add the corresponding number to result.
            else
                result += numbersMap.get(ch);
        }

        return result;
    }

    // Проверяет на римскую запись.
    // Если это арабская, то возвращает ЛОЖЬ
    // Если римская возвращает ИСТИНУ
    private static boolean isRoman(String input) {
        if (input.startsWith("X") || input.startsWith("I") || input.startsWith("V")) {
            return true;
        } else {
            return false;
        }
    }

    private static String convertToRoman(int mInt) {
        String[] rnChars = { "M",  "CM", "D", "C",  "XC", "L",  "X", "IX", "V", "I" };
        int[] rnVals = {  1000, 900, 500, 100, 90, 50, 10, 9, 5, 1 };
        String retVal = "";

        for (int i = 0; i < rnVals.length; i++) {
            int numberInPlace = mInt / rnVals[i];
            if (numberInPlace == 0) continue;
            retVal += numberInPlace == 4 && i > 0? rnChars[i] + rnChars[i - 1]:
                    new String(new char[numberInPlace]).replace("\0",rnChars[i]);
            mInt = mInt % rnVals[i];
        }
        return retVal;
    }

    public static String calc(String input) throws Exception {
        String rawNumbers = input.replace(" ", "");
        String[] numbers = rawNumbers.split("[+-/*]");
        Integer number1, number2;

        if (numbers.length != 2) throw new Exception("you must provide only two numbers!");

        if (isRoman(rawNumbers)) {
            number1 = romanToInteger(numbers[0]);
            number2 = romanToInteger(numbers[1]);
        } else {
            number1 = Integer.parseInt(numbers[0]);
            number2 = Integer.parseInt(numbers[1]);
        }

        String calcResult;

        if (number1 > 10 || number1 < 0 || number2 > 10 || number2 < 0) throw new Exception("numbers must be less than 10 and bigger than 0");

        if (input.contains("+")) {
            Integer result = number1 + number2;

            calcResult = result.toString();
        } else if (input.contains("-")) {
            Integer result = number1 - number2;

            calcResult = result.toString();
        } else if (input.contains("/")) {
            Integer result = number1 / number2;

            calcResult = result.toString();
        } else if (input.contains("*")){
            Integer result = number1 * number2;

            calcResult = result.toString();
        } else {
            throw new Exception("operation is not supported");
        }

        if (isRoman(rawNumbers)) {
            if (Integer.parseInt(calcResult) < 1) throw new Exception("result of calculation that uses roman digits must be greater than 0");

            calcResult = convertToRoman(Integer.parseInt(calcResult));
        }

        return calcResult;
    }
}