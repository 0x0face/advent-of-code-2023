import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class D01Q02 {
    private static Map<String, Integer> numsMap = new HashMap<>();
    public static void main(String[] args) {
        initMap();
        
        List<Integer> rows = new ArrayList<>();
        String row;

        try {
            Scanner reader = new Scanner(new FileReader(new File("test-data.txt")));

            while (reader.hasNextLine()) {
                row = reader.nextLine();
                System.out.println(row);

                int rowValue = processLine(row);

                System.out.println("row value: " + rowValue);
                rows.add(rowValue);
            }
            int result = rows.stream().reduce(0, Integer :: sum);
            System.out.println("result: " + result); // result: 54076
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

    public static int processLine(String line) {
        int firstDigit = -1, lastDigit = -1;
        String tempStr;

        int strLen = line.length();
        for (int i = 0; i < strLen; i++) {
            int n = 0;
            char c = line.charAt(i);
            try {
                n = Integer.parseInt(Character.toString(c));
            } catch(NumberFormatException e) {
                if (!isValidChar(c)) {
                    continue;
                }

                boolean tryNext = true;
                for (int x = 3; x < 6 && i + x < strLen + 1; x++) {
                    tempStr = line.substring(i, i + x);
                    if (tryNext && numsMap.containsKey(tempStr)) {
                        n = numsMap.get(tempStr);
                        tryNext = false;
                    }
                }
            }
            
            if (n == 0) {
                continue;
            }
            
            if (firstDigit == -1) {
                firstDigit = n;
            }

            lastDigit = n;
        }

        if (firstDigit == -1) {
            return 0;
        }

        int num = firstDigit * 10 + lastDigit;
        return num;
    }

    private static boolean isValidChar(char c) {
        return c == 'e' || c == 'f' || c == 't' || c == 's' || c == 'n' || c == 'o';
    }

    private static void initMap() {
        numsMap.put("one", 1);
        numsMap.put("two", 2);
        numsMap.put("three", 3);
        numsMap.put("four", 4);
        numsMap.put("five", 5);
        numsMap.put("six", 6);
        numsMap.put("seven", 7);
        numsMap.put("eight", 8);
        numsMap.put("nine", 9);
    }
}