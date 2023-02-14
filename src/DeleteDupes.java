import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DeleteDupes {
    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        HashSet<String> dataChecker = new HashSet<>();
        File file = new File("src/EliaRoomFirstRow.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // add points to arraylist
        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            if (!dataChecker.contains(line)) {
                dataChecker.add(line);
                data.add(line);
            }
        }

        try {
            data.sort(Comparator::comparator);
            FileWriter fileWriter = new FileWriter("sortedDupeRemoved.txt");
            for (String datum : data) {
                fileWriter.write(datum + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Comparator {
    public static int comparator(String s, String s2) {
        var v1 = Double.parseDouble(s.split(" ")[0]);
        var v2 = Double.parseDouble(s2.split(" ")[0]);
        return Double.compare(v1, v2);
    }
}
