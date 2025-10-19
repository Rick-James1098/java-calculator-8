package calculator;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;

public class Application {
    public static void main(String[] args) throws IllegalArgumentException{
        System.out.println("덧샘할 문자열을 입력해 주세요. 음수는 입력할 수 없습니다. '.' 은 커스텀 구분자로 사용할 수 없습니다.");
        System.out.print("문자열 : ");
        String userInput = Console.readLine();
        BigDecimal result;

        List<BigDecimal> parsedNums = parseUserInput(userInput);

        result = calculateSum(parsedNums);

        System.out.println("결과 : " + result);

        Console.close();
    }

    static List<BigDecimal> parseUserInput(String userInput) throws IllegalArgumentException{
        List<Character> separator = new ArrayList<>(Arrays.asList(',', ':')); // init separator
        List<BigDecimal> parsedNums = new ArrayList<>(); // return value
        StringBuilder tempString = new StringBuilder(); // will be inverted to Double
        userInput += ':';

        if (userInput.length() == 1) {
            return parsedNums;
        }

        if (checkCustomSeparator(userInput)) {
            if (userInput.charAt(2) == '.') {
                throw new IllegalArgumentException(".은 커스텀 구분자로 사용할 수 없습니다");
            }
            separator.add(userInput.charAt(2));
        }

        int startIndex = 0;
        if (separator.size() == 3) {
            startIndex = 5;

            if (userInput.length() == 6) {
                return parsedNums;
            }
        }

        for (int i = startIndex; i < userInput.length(); i++) {

            Character curChar = userInput.charAt(i);
            Boolean isSeparator = Boolean.FALSE;

            for (Character sep : separator) { // is separator?
                if (sep == curChar) {
                    if (tempString.isEmpty()) {
                        throw new IllegalArgumentException("잘못된 입력입니다.");
                    }

                    parsedNums.add(new BigDecimal(tempString.toString()));
                    tempString.delete(0, tempString.length()); // init
                    isSeparator = Boolean.TRUE;
                    break;
                }
            }

            if (isSeparator == Boolean.TRUE) continue;

            if (0 <= curChar - '0'  && 9 >= curChar - '0') { // is number?
                tempString.append(curChar);
                continue;
            }

            if (curChar == '.') {
                if (!tempString.isEmpty()) {
                    tempString.append(curChar);
                    continue;
                }
            }

            throw new IllegalArgumentException("잘못된 입력입니다.");
        }

        return parsedNums;
    }

    static Boolean checkCustomSeparator(String userInput) {

        if (userInput.charAt(0) != '/' || userInput.charAt(1) != '/' ||
                userInput.charAt(3) != '\\' || userInput.charAt(4) != 'n') {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    static BigDecimal calculateSum (List<BigDecimal> parsedNums) {

        BigDecimal sum = BigDecimal.ZERO;

        for (BigDecimal parsedNum : parsedNums) {
            sum = sum.add(parsedNum);
        }

        return sum;
    }
}
