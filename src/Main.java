import StdLib.StdDraw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    private final ArrayList<double[]> points = new ArrayList<>();

    public Main() {
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
    }

    public void run() {
        //filter
        int nearNeeded = 15;
        int distLimit = 200;
        int[] neighbors = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            neighbors[i] = 0;
        }
        for (int i = 0; i < points.size(); i++) {
            double a;
            double t1;
            double b = points.get(i)[0];
            double t2 = points.get(i)[1];
            for (int j = i; j < points.size(); j++) {
                a = points.get(j)[0];
                t1 = points.get(j)[1];
                double dist = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) - 2 * a * b * Math.cos(Math.abs(t1 - t2)));
                if (dist < distLimit) {
                    neighbors[i] += 1;
                    neighbors[j] += 1;
                }
            }
        }
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] > nearNeeded) {
                double r = points.get(i)[0];
                double t = points.get(i)[1];
                StdDraw.point(r*Math.cos(t), r*Math.sin(t));
            }
        }
        // fill in spots between points
        /**
         StdDraw.setPenRadius(0.01);
         for(int j = 0; j<points.size()-1;j++) {
         double r1 = points.get(j)[0];
         double t1 = points.get(j)[1];
         double r2 = points.get(j + 1)[0];
         double t2 = points.get(j + 1)[1];
         double x1 = r1 * Math.cos(t1);
         double x2 = r2 * Math.cos(t2);
         double y1 = r1 * Math.sin(t1);
         double y2 = r2 * Math.sin(t2);
         double m = (y2 - y1) / (x2 - x1);
         double b = y1 - m * x1;
         if (t1 < t2) {
         for (double i = t1; i < t2; i += 0.1 * Math.PI / 180) {
         double r = b / (Math.sin(i) - m * Math.cos(i));
         StdDraw.point(r * Math.cos(i), r * Math.sin(i));
         }
         } else {
         for (double i = t2; i < t1; i += 0.1 * Math.PI / 180) {
         double r = b / (Math.sin(i) - m * Math.cos(i));
         StdDraw.point(r * Math.cos(i), r * Math.sin(i));
         }
         }
         }
         **/
        //StdDraw.point(0,0);
        StdDraw.setPenRadius(0.0005);
        for (int i = -6000; i <= 6000; i += 200) {
            StdDraw.line(i, -6000, i, 6000);
            StdDraw.line(-6000, i, 6000, i);
        }
        StdDraw.show();
        System.out.println("finished");
    }


    public static void main(String[] args) {
        Main m = new Main();
        m.run();
    }
}
