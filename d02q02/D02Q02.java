import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class D02Q02 {
    public static void main(String[] args) {
        try {
            Scanner reader = new Scanner(new FileReader(new File("test-data.txt")));
            String line = null;
            List<entryData> lists = new ArrayList<>();
    
            while(reader.hasNextLine()) {
                line = reader.nextLine();
                lists.add(new entryData(line));
            }

            List<Vec3INT> listedList = lists.parallelStream().map(D02Q02 :: setsToVec3INT).collect(Collectors.toList());
            IntStream powers = listedList.parallelStream().mapToInt(e -> e.red * e.green * e.blue);
            int result = powers.sum();
            System.out.println("result: " + result); // result: 77607
        } catch (IOException e) {
            System.err.println("ERROR:: TRY AGAIN!!");
        }
    }

    public static Vec3INT setsToVec3INT(entryData e) {
        Map<Character, List<Integer>> map = new HashMap<>();
        map.put('r', new ArrayList<>());
        map.put('g', new ArrayList<>());
        map.put('b', new ArrayList<>());

        e.sets.parallelStream().forEach(set -> {
            map.get('r').add(set.red);
            map.get('g').add(set.green);
            map.get('b').add(set.blue);
        });

        int red = map.get('r').stream().max(Comparator.comparing(Integer :: valueOf)).orElse(0);
        int green = map.get('g').stream().max(Comparator.comparing(Integer :: valueOf)).orElse(0);
        int blue = map.get('b').stream().max(Comparator.comparing(Integer :: valueOf)).orElse(0);
        return new Vec3INT(red, green, blue);
    }

    private static class entryData {
        public List<Vec3INT> sets;
        
        public entryData(String line) {
            processLine(line);
        }

        public void processLine(String line) {
            String[] splitLineToParts = line.split(": ");
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

        public Vec3INT() {}

        public Vec3INT(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }
}