import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class D02Q01 {
    public static void main(String[] args) {
        final int pRED = 12;
        final int pGREEN = 13;
        final int pBLUE = 14;

        try {
            Scanner reader = new Scanner(new FileReader(new File("test-data.txt")));
            String line = null;
            List<entryData> lists = new ArrayList<>();
    
            while(reader.hasNextLine()) {
                line = reader.nextLine();
                lists.add(new entryData(line));
            }

            Map<Boolean, List<entryData>> partitionedMap = lists.parallelStream().collect(Collectors.partitioningBy((list) -> list.sets.stream().allMatch(a -> (a.red <= pRED && a.green <= pGREEN && a.blue <= pBLUE))));
            int result = partitionedMap.get(true).stream().mapToInt(e -> e.id).reduce(0, Integer :: sum);
            System.out.println("result: " + result); // result: 2679
        } catch (IOException e) {
            System.err.println("ERROR:: TRY AGAIN!!");
        }
    }

    private static class entryData {
        public int id;
        public List<Vec3INT> sets;
        
        public entryData(String line) {
            processLine(line);
        }

        public void processLine(String line) {
            String[] splitLineToParts = line.split(": ");
            id = Integer.parseInt(splitLineToParts[0].split(" ")[1]);
            sets = new ArrayList<>();
            
            Vec3INT colors = null;
            // TODO : do this with .stream()
            for (String splitPartToSets: splitLineToParts[1].split("; ")) {
                colors = new Vec3INT();
                for (String splitSetsToParts: splitPartToSets.split(", ")) {
                    String[] colorAndValue = splitSetsToParts.split(" ");
                    int value = Integer.parseInt(colorAndValue[0]);
                    String color = colorAndValue[1];
                    if (color.equals("red")) {
                        colors.red += value;
                    } else if (color.equals("green")) {
                        colors.green += value;
                    } else {
                        colors.blue += value;
                    }
                }
                sets.add(colors);      
            }
            // cute (^_^)
            return;
        }
    }

    private static class Vec3INT {
        int red = 0;
        int green = 0;
        int blue = 0;
    }
}