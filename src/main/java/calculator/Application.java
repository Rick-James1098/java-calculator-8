package calculator;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        String userInput = Console.readLine();
        List<Double> parsedNums = parseUserInput(userInput);

        System.out.println(calculateSum(parsedNums));

    }

    static List<Double> parseUserInput(String userInput) throws IllegalArgumentException{
        List<Character> separator = new ArrayList<>(Arrays.asList(',', ':'));
        List<Double> parsedNums = new ArrayList<>();
        StringBuilder tempString = new StringBuilder();
        userInput += ':';

        if (checkCustomSeparator(userInput)) {
            separator.add(userInput.charAt(2));
        }

        int startIndex = 0;
        if (separator.size() == 3) {
            startIndex = 5;
        }

        for (int i = startIndex; i < userInput.length(); i++) {

            Character curChar = userInput.charAt(i);
            Boolean isSeparator = Boolean.FALSE;

            for (Character sep : separator) { // is separator?
                if (sep == curChar) {
                    if (!tempString.isEmpty()) {
                        parsedNums.add(Double.parseDouble(tempString.toString()));
                    }

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

    static Double calculateSum (List<Double> parsedNums) {

        Double sum = 0.0;

        for (int i = 0; i < parsedNums.size(); i++) {
            sum += parsedNums.get(i);
        }

        return sum;
    }
}
