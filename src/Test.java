import StdLib.StdDraw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        ArrayList<double[]> points = new ArrayList<>();
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(700, 700);
        StdDraw.setXscale(-6000, 6000);
        StdDraw.setYscale(-6000, 6000);
        File file = new File("/Users/bjiang2/Documents/My_Java_Programs/coords/src/coords.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // add points to arraylist
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] arr = line.split(", ");
            double r = Double.parseDouble(arr[0]);
            if (r > 6000) continue;
            if (r < 150) continue;
            if (3950 < r && r < 4050) continue;
            double theta = Double.parseDouble(arr[1]);
            if (124 < theta && theta < 126) continue;
            theta = Math.toRadians(theta);
            int quality = Integer.parseInt(arr[2]);
            if (quality != 15) continue;
            points.add(new double[]{r, theta});
        }
        for (double[] point : points) {
            r = point[0];
            theta = point[1];
        }
    }
}