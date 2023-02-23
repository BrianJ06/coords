import StdLib.StdDraw;
import StdLib.StdOut;
import StdLib.StdStats;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static ArrayList<double[]> points = new ArrayList<>();

    public Main() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(700, 700);
        StdDraw.setXscale(-6000, 6000);
        StdDraw.setYscale(-6000, 6000);
        File file = new File("src/coords.txt");
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
                StdDraw.point(r * Math.cos(t), r * Math.sin(t));
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

    public static void drawGrid() {
        StdDraw.setPenRadius(0.0005);
        StdDraw.setPenColor(new Color(0, 0, 0, 50));
        for (int i = -6000; i <= 6000; i += 200) {
            StdDraw.line(i, -6000, i, 6000);
            StdDraw.line(-6000, i, 6000, i);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        //StdDraw.circle(0,0,4699);
        //StdDraw.line(0,0,10000*Math.cos(Math.toRadians(124.55)), 10000*Math.sin(Math.toRadians(124.55)));
        StdDraw.show();
    }

    public static void displayFile(String filename) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(700, 700);
        StdDraw.setXscale(-6000, 6000);
        StdDraw.setYscale(-6000, 6000);

        points = new ArrayList<>();
        File file = new File(filename);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // add points to arraylist
        StdDraw.setPenRadius(0.005);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] arr = line.split(" ");
            double r = Double.parseDouble(arr[1]);

            double theta = Double.parseDouble(arr[0]);

            points.add(new double[]{theta, r, Integer.parseInt(arr[2]), Integer.parseInt(arr[3])});

        }

        StdDraw.show();
        printPoints();
        //drawGrid();
    }

    //t2 should always be the angle ahead of t1
    private static double angleDistance(double t1, double t2) {
        if (t2 < t1) { //this happens when t2 is like 10, but t2 is about 350
            return (t2 + 360) - t1;
        }
        return (t2 - t1);
    }

    private static void printPoints() {
        var s = 20;

        StdDraw.disableDoubleBuffering();
        double prevAngle = points.get(0)[0];
        int e = 0;
        int iterationNo = 1;
        int noAccepted = 0;
        for (double[] i : points) {
            if (i[1] > 150 && i[1] < 6000 && (i[1] < 3900 || i[1] > 4100) && (i[0] < 123 || i[0] > 126) && i[2] > 14 && i[2] == 15 && i[0] < 360) {
                //angle filter needs improvment, map out transient angles
                if (angleDistance(prevAngle, i[0]) < s + e) {
                    StdDraw.point((i[1] * Math.cos(Math.toRadians(i[0]))), i[1] * Math.sin(Math.toRadians(i[0])));
                    System.out.println(iterationNo + " " + Arrays.toString(i));
                    e = 0;
                    noAccepted++;
                } else {
                    e+= s; //if we fail secondary filter
                }
                try {
                    Thread.sleep(0);
                } catch (InterruptedException d) {
                    d.printStackTrace();
                }
            } else {
                e += s; //if we fail main filter
            }
            iterationNo++;

        }
        System.out.println(noAccepted + " points accepted");

        //StdDraw.show();
    }

    private static void stats() {
        //run some stats
        List<Double> angles = points.stream().map(i -> i[0]).collect(Collectors.toList());
        double[] a = new double[angles.size()];
        IntStream.range(0, a.length).forEach(i -> a[i] = angles.get(i));

        List<Double> distances = points.stream().map(i -> i[1]).collect(Collectors.toList());
        double[] d = new double[distances.size()];
        IntStream.range(0, d.length).forEach(i -> d[i] = distances.get(i));

        System.out.println("average angle (deg) " + StdStats.mean(a));
        System.out.println("angle stddev " + StdStats.stddev(a));
        System.out.println("average distance " + StdStats.mean(d));
        System.out.println("distance stddev " + StdStats.stddev(d));

        Arrays.sort(d);
        System.out.println("median distance " + d[d.length / 2]);

        Arrays.sort(a);
        System.out.println("median angle " + a[a.length / 2]);
    }

    private static void startBitData() {
        points.stream().filter(i -> i[3] == 1).collect(Collectors.toList()).forEach(i -> System.out.println(Arrays.toString(i)));
    }

    private static void thetaRPrint() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(700, 700);
        StdDraw.setXscale(-6000, 6000);
        StdDraw.setYscale(-6000, 6000);

        points = new ArrayList<>();
        File file = new File("src/memoryDump.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // add points to arraylist
        StdDraw.setPenRadius(0.0005);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] arr = line.split(" ");
            double r = Double.parseDouble(arr[1]);

            double theta = Double.parseDouble(arr[0]);

            StdDraw.point((r * Math.cos(Math.toRadians(theta))), r * Math.sin(Math.toRadians(theta)));

        }
        StdDraw.show();
    }

    public static void main(String[] args) {
        //Main m = new Main();
        //displayFile("src/data255spd.txt");
        //startBitData();
        Main.thetaRPrint();

        System.out.println("done");
    }
}
