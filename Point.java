package Experiment1_5;

class Point2D {

    int x;
    int y;
    public Point2D(){};

    public Point2D(int x , int y){
        this.x = x;
        this.y = y;
    }

    public void offset ( int a ,int b) {
        x += a;
        y += b;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
class Point3D extends Point2D {
    int z ;
    Point3D(){};
    Point3D(int x , int y , int z){
        super(x,y);
        this.z = z;
    }
    Point3D(Point2D p2d,int z){
        super(p2d.getX(), p2d.getY());
        this.z = z ;
    }


    public int getX() {
        return super.getX();
    }

    public int getY() {
        return super.getY();
    }

    public int getZ() {
        return z;
    }


    public void setX(int x) {
        super.setX(x);
    }


    public void setY(int y) {
        super.setY(y);
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void offset(int a, int b, int c) {
        super.offset(a, b);
        z += c;
    }
}
public class Point {
    
    public static void main(String[] args) {
        Point2D p2d1 = new Point2D(1,1);
        Point2D p2d2 = new Point2D(2,2);
        double length1 = distance2D(p2d1, p2d2);
        System.out.println(length1);

        Point3D p3d1 = new Point3D(1,1,1);
        Point3D p3d2 = new Point3D(2,2,2);
        double length2 = distance3D(p3d1, p3d2);
        System.out.println(length2);
    }

    public static double distance2D(Point2D p2d1, Point2D p2d2) {
        double distance;
        double x;
        double y;
        x = Math.pow((p2d1.getX() - p2d2.getX()), 2);
        y = Math.pow((p2d1.getY() - p2d2.getY()), 2);
        distance = Math.sqrt(y + x);
        return distance;
    }

    public static double distance3D(Point3D p3d1, Point3D p3d2) {
        double distance;
        double x;
        double y;
        double z;
        x = Math.pow((p3d1.getX() - p3d2.getX()), 2);
        y = Math.pow((p3d1.getY() - p3d2.getY()), 2);
        z = Math.pow((p3d1.getZ() - p3d2.getZ()), 2);
        distance = Math.sqrt(y + x + z);
        return distance;
    }
}