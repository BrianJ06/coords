import StdLib.StdDraw;

public class Test {
    public static void main(String[] args) {
        StdDraw.setCanvasSize(700, 700);
        StdDraw.setXscale(-1200, 1200);
        StdDraw.setYscale(-1200, 1200);
        double r = 1000.0;
        double theta = 0.0;
        for (int i = 0; i < 1000; i++) {
            StdDraw.point(r * Math.cos(theta), r * Math.sin(theta));
            theta += 0.1;
            r = 1/Math.cos(theta);
        }
        StdDraw.show();
    }
}
