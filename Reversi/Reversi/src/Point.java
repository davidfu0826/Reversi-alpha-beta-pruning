/**
 * Created by jinjang on 2/13/19.
 */
public class Point {
    int x; int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(x + "" + y);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        char tempcol = (char) (x + 65);
        int temprow = y + 1;
        return tempcol + ", " + temprow;
    }
}
