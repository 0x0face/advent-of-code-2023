import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class D01Q01 {
    public static void main(String[] args) {
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
            System.out.println("result: " + result); // result: 54561
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

    public static int processLine(String line) {
        int firstDigit = -1, lastDigit = -1;
                
        for (int i = 0; i < line.length(); i++) {
            int n = 0;

            try {
                n = Integer.parseInt(Character.toString(line.charAt(i)));
            } catch(NumberFormatException e) {
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
}