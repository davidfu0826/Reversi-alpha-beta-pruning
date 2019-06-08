import java.util.HashSet;
import java.util.Random;

/**
 * Created by jinjang on 2/13/19.
 */
public class FirstPathAI {
    public Point move(HashSet<Point> valid) {


        for(Point p : valid) {
            return p;
        }

        return new Point(-1, -1);

    }
}
