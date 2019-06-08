import java.util.HashSet;

/**
 * Created by jinjang on 2/6/19.
 */
public class MiniMaxAI {
    GameBoard thinkingBoard;
    int player;
    int opponent;


    int MAX_DEPTH;
    final double TIME_ALLOWED = 10;

    public MiniMaxAI(int player) {
        this.player = player;
        MAX_DEPTH = 1;
        thinkingBoard = new GameBoard();

    }

    public MiniMaxAI(int player, int maxdepth) {
        this.player = player;
        this.opponent = player==1?2:1;
        thinkingBoard = new GameBoard();

        MAX_DEPTH = maxdepth;

    }

    public Point move(GameBoard board) {
        thinkingBoard = board.cloneGame();
        int index = think(thinkingBoard, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true, System.currentTimeMillis());
        int count = 0;


        //Main.printBoard(board);
        HashSet<Point> points = board.getLegalList(this.player);

        //System.out.println(Main.testMethod());
        Point badplay = new Point(-1,-1);

        //System.out.println("INDEX is : " + index);
        for(Point p : points) {
            //System.out.println("Checking list, point is: " + p.x + ", " + p.y);
            if(count == index) {
                return p;
                //currplay = p;
                //break;
            }
            count++;
        }


        return badplay;
    }

    /*
    function alphabeta(node, depth, α, β, maximizingPlayer) is
    if depth = 0 or node is a terminal node then
        return the heuristic value of node
    if maximizingPlayer then
        value := −∞
        for each child of node do
            value := max(value, alphabeta(child, depth − 1, α, β, FALSE))
            α := max(α, value)
            if α ≥ β then
                break (* β cut-off *)
        return value
    else
        value := +∞
        for each child of node do
            value := min(value, alphabeta(child, depth − 1, α, β, TRUE))
            β := min(β, value)
            if α ≥ β then
                break (* α cut-off *)
        return value
     */
    public int think(GameBoard board, int depth, int alpha, int beta, boolean maximizingPlayer, long starttime) {


        HashSet<Point> points = board.getLegalList(maximizingPlayer ? player:opponent);

       // for(Main.Point p : points) {
        //    System.out.println("THINKING LEGAL LIST POINT: " + p);
        //}


        long currtime = System.currentTimeMillis();
        double elapsed = (currtime - starttime) / 1000.0;

        if(points.isEmpty() || depth == 0 || elapsed > TIME_ALLOWED) {
            return board.pointsValue(this.player);
        }

        if(maximizingPlayer) {
            int val = Integer.MIN_VALUE;
            int index = 0, count = 0;
            for (Point p : points) {
                GameBoard tempboard = board.cloneGame();
                tempboard.playChip(p.x, p.y, this.player);

                int thinkval = think(tempboard, depth - 1, alpha, beta, false, starttime);
                if(thinkval > val) {
                    val = thinkval;
                    index = count;
                }

                count++;
                //val = Integer.max(val, think(tempboard, depth - 1, alpha, beta, this.player==1 ? 2:1));



                alpha = Integer.max(alpha, val);
                if(alpha >= beta) {
                    break;
                }


            }
            if(depth == MAX_DEPTH) return index;
            return val;

        }
        else {
            int val = Integer.MAX_VALUE;
            int index = 0, count = 0;
            for(Point p : points) {
                GameBoard tempboard = board.cloneGame();
                tempboard.playChip(p.x, p.y, this.opponent);

                int thinkval = think(tempboard, depth - 1, alpha, beta, true, starttime);
                if(thinkval < val) {
                    val = thinkval;
                    index = count;
                }

                count++;


                beta = Integer.min(beta, val);
                if(alpha >= beta) {
                    break;
                }

            }

            if(depth == MAX_DEPTH) return index;
            return val;
        }
    }

    //player1 - player2




       // public int
}
