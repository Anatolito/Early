import java.io.IOException;
import java.util.Scanner;

public class Main {

    static String firstDigit;
    static String secondDigit;
    static String[] digits;
    static int result;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(calc(input));
    }


    public static String calc(String input) {
        digits = checkMath(input);
        firstDigit = digits[0];
        secondDigit = digits[1];
        if (isArabianDigit(firstDigit) && isArabianDigit(secondDigit)) {
            calculationArabian(input, firstDigit, secondDigit);
            return String.valueOf(result);
        } else if (isRomanDigit(firstDigit) && isRomanDigit(secondDigit)) {
            calculationRoman(input, firstDigit, secondDigit);
            return translateArabianToRoman(result);
        } else if (isArabianDigit(firstDigit) && isRomanDigit(secondDigit) || isRomanDigit(firstDigit) && isArabianDigit(secondDigit)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Используются одновременно разные системы счисления");
                System.exit(0);
            }
        }
        return null;
    }

    public static String[] checkMath(String input) {
        String[] numberOfDigits = input.split("\\+|-|/|\\*");
        if (numberOfDigits.length > 2) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                System.exit(0);
            }
        } else if (numberOfDigits.length < 2 || numberOfDigits[0].isEmpty() || numberOfDigits[1].isEmpty()) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Строка не является математической операцией");
                System.exit(0);
            }
        }
        return numberOfDigits;
    }

    public static boolean isRomanDigit(String input) {
        String romanRegex = "[IVX]+";
        return input.trim().matches(romanRegex);
    }

    public static boolean isArabianDigit(String input) {
        if (!isRomanDigit(input)) {
            try {
                Integer.parseInt(input.trim());
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Введеные строки не являются арабскими или римскими цифрами");
                System.exit(0);
            }
        }
        return false;
    }

    public static int calculationArabian(String input, String firstDigitStr, String secondDigitStr) {
        int firstDigit = Integer.parseInt(firstDigitStr.trim());
        int secondDigit = Integer.parseInt(secondDigitStr.trim());
        if (firstDigit < 1 || firstDigit > 10 || secondDigit < 1 || secondDigit > 10) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Вы ввели число меньше единицы или больше 10");
                System.exit(0);
            }
        }
        if (input.contains("+")) {
            result = firstDigit + secondDigit;
        } else if (input.contains("-")) {
            result = firstDigit - secondDigit;
        } else if (input.contains("*")) {
            result = firstDigit * secondDigit;
        } else if (input.contains("/")) {
            result = (int) Math.round((double) firstDigit / secondDigit);
        }
        return result;
    }

    public static void calculationRoman(String input, String firstDigitStr, String secondDigitStr) {
        int firstDigit = translateRomanToArabian(firstDigitStr);
        int secondDigit = translateRomanToArabian(secondDigitStr);
        if (firstDigit < 1 || firstDigit > 10 || secondDigit < 1 || secondDigit > 10) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Вы ввели число меньше единицы или больше 10");
                System.exit(0);
            }
        }
        if (input.contains("+")) {
            result = firstDigit + secondDigit;
        } else if (input.contains("-")) {
            result = firstDigit - secondDigit;
        } else if (input.contains("*")) {
            result = firstDigit * secondDigit;
        } else if (input.contains("/")) {
            result = (int) Math.round((double) firstDigit / secondDigit);
        }

        if (result == 0) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("В римской системе нет нуля");
                System.exit(0);
            }
        } else if (result < 0) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("В римской системе нет отрицательных чисел");
                System.exit(0);
            }
        }

    }

    public static int translateRomanToArabian(String romanDigitStr)       {
        String romanDigit = romanDigitStr.trim();
        int arabianDigit=0;
        switch (romanDigit) {
            case "I": arabianDigit = RomanDigits.I.getArabianDigit();
                break;
            case "II": arabianDigit = RomanDigits.II.getArabianDigit();
                break;
            case "III": arabianDigit = RomanDigits.III.getArabianDigit();
                break;
            case "IV": arabianDigit = RomanDigits.IV.getArabianDigit();
                break;
            case "V": arabianDigit = RomanDigits.V.getArabianDigit();
                break;
            case "VI": arabianDigit = RomanDigits.VI.getArabianDigit();
                break;
            case "VII": arabianDigit = RomanDigits.VII.getArabianDigit();
                break;
            case "VIII": arabianDigit = RomanDigits.VIII.getArabianDigit();
                break;
            case "IX": arabianDigit = RomanDigits.IX.getArabianDigit();
                break;
            case "X": arabianDigit = RomanDigits.X.getArabianDigit();
                break;
            default:
                System.out.println("Вы ввели неправильное число");
        }
        return arabianDigit;
    }

    public static String translateArabianToRoman(int arabianDigitI) {
        int[] values = {100,90,50,40,10,9,5,4,1};
        String [] romanLetters = {"C","CX","L","LX","X","IX","V","IV","I"};
        StringBuilder romanDigit = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (arabianDigitI>=values[i]){
                arabianDigitI-=values[i];
                romanDigit.append(romanLetters[i]);
            }
        }
        return romanDigit.toString();
    }

    public enum RomanDigits {
        I(1), II(2), III(3), IV(4), V(5), VI(6),
        VII(7), VIII(8), IX(9), X(10);
        private int arabianDigit;

        RomanDigits(int arabianDigit) {
            this.arabianDigit = arabianDigit;
        }

        public int getArabianDigit() {
            return arabianDigit;
        }
    }

}