import java.util.HashSet;
import java.util.Random;

/**
 * Created by jinjang on 2/1/19.
 */
public class RandomAI {
    public Point move(HashSet<Point> valid) {

        Random rand = new Random();
        int random = rand.nextInt(valid.size());

        int count = 0;
        for(Point p : valid) {
            if(count == random) {
                return p;
            }
            count++;
        }

        return new Point(-1, -1);

    }
}
