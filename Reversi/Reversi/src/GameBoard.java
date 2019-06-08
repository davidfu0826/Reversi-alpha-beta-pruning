import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by jinjang on 2/13/19.
 */


public class GameBoard {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";



    int[][] currboard;
    ArrayList<Point> currentChips;


    public GameBoard() {
        currboard = new int[8][8];
        currboard[3][3] = 2;
        currboard[4][4] = 2;
        currboard[3][4] = 1;
        currboard[4][3] = 1;
        currentChips = new ArrayList<Point>();
        currentChips.add(new Point(3,3));
        currentChips.add(new Point(3,4));
        currentChips.add(new Point(4,3));
        currentChips.add(new Point(4,4));
    }

    public void printBoard() {
        String output = "";
        output += "_||A|B|C|D|E|F|G|H|\n";
        for(int i = 0; i < 8; i++) {
            output += ((i+1)+"||");
            for(int j = 0; j < 8; j++) {
                if(currboard[j][i] == 1) {
                    output += ANSI_RED + currboard[j][i] + ANSI_RESET + "|";
                }
                else if(currboard[j][i] == 2) {
                    output += ANSI_GREEN + currboard[j][i] + ANSI_RESET + "|";
                }
                else {
                    output += (currboard[j][i] + "|");
                }
            }
            output += "\n";
        }
        System.out.println(output);
    }

    public boolean playChip(int x, int y, int player) {
        if(currboard[x][y] != 0) return false;

        HashSet<Point> legalMoves = getLegalList(player);
        for(Point temp : legalMoves) {
            if(temp.x == x && temp.y == y) {
                currboard[x][y] = player;
                currentChips.add(new Point(x,y));
                flipChips(x, y, player, true);

                char tempcol = (char) (x + 65);
                int temprow = y + 1;
                //System.out.println("Playing at " + tempcol + temprow);
                return true;
            }
        }

        System.out.println("FAILED TO PLAY CHIP");
        return false;
    }


    public HashSet<Point> getLegalList(int player) {
        HashSet<Point> output = new HashSet<Point>();
        for (Point temp : currentChips) {
            if(currboard[temp.x][temp.y] != player && currboard[temp.x][temp.y] != 0) {
                if(temp.y - 1 >= 0 && currboard[temp.x][temp.y - 1] == 0) {
                    if(flipChips(temp.x, temp.y-1, player, false))
                        output.add(new Point(temp.x, temp.y-1));
                }
                if(temp.y + 1 < 8 && currboard[temp.x][temp.y+1] == 0) {
                    if(flipChips(temp.x, temp.y+1, player, false))
                        output.add(new Point(temp.x, temp.y+1));
                }
                if(temp.x - 1 >= 0 && currboard[temp.x-1][temp.y] == 0) {
                    if(flipChips(temp.x-1, temp.y, player, false))
                        output.add(new Point(temp.x-1, temp.y));
                }
                if(temp.x + 1 < 8 && currboard[temp.x+1][temp.y] == 0) {
                    if(flipChips(temp.x+1, temp.y, player, false))
                        output.add(new Point(temp.x+1, temp.y));
                }
            }
        }

        return output;

    }


    public boolean flipChips(int x, int y, int player, boolean flip) {
        HashSet<Point> flipThis = new HashSet<Point>();
        int currX = x, currY = y;

        //Going UP
        boolean foundEnd = false;
        int chipsflipped = 0;
        while(currY - 1 >= 0 && currboard[currX][currY-1] != 0) {
            if(currboard[currX][currY-1] != player) {
                flipThis.add(new Point(currX, currY-1));
                chipsflipped++;
            }
            else {
                foundEnd = true;
                break;
            }
            currY--;
        }

        if(foundEnd) {
            if(flip) {
                for (Point p : flipThis) {
                    currboard[p.x][p.y] = player;
                }
            }
            else {
                if(chipsflipped != 0)
                    return true;
            }
        }

        //Going DOWN
        flipThis.clear();
        currX = x; currY = y;
        foundEnd = false;
        chipsflipped = 0;
        while(currY + 1 < 8 && currboard[currX][currY+1] != 0) {
            if(currboard[currX][currY+1] != player) {
                flipThis.add(new Point(currX, currY+1));
                chipsflipped++;
            }
            else {
                foundEnd = true;
                break;
            }
            currY++;
        }
        if(foundEnd) {
            if(flip) {
                for (Point p : flipThis) {
                    currboard[p.x][p.y] = player;
                }
            }
            else {
                if(chipsflipped != 0)
                    return true;
            }
        }

        //Going LEFT
        flipThis.clear();
        currX = x; currY = y;
        foundEnd = false;
        chipsflipped = 0;
        while(currX - 1 >= 0 && currboard[currX-1][currY] != 0) {
            if(currboard[currX-1][currY] != player) {
                flipThis.add(new Point(currX-1, currY));
                chipsflipped++;
            }
            else {
                foundEnd = true;
                break;
            }
            currX--;
        }
        if(foundEnd) {
            if(flip) {
                for (Point p : flipThis) {
                    currboard[p.x][p.y] = player;
                }
            }
            else {
                if(chipsflipped != 0)
                    return true;
            }
        }

        //Going RIGHT
        flipThis.clear();
        currX = x; currY = y;
        foundEnd = false;
        chipsflipped = 0;
        while(currX + 1 < 8 && currboard[currX+1][currY] != 0) {
            if(currboard[currX+1][currY] != player) {
                flipThis.add(new Point(currX+1, currY));
                chipsflipped++;
            }
            else {
                foundEnd = true;
                break;
            }
            currX++;
        }
        if(foundEnd) {
            if(flip) {
                for (Point p : flipThis) {
                    currboard[p.x][p.y] = player;
                }
            }
            else {
                if(chipsflipped != 0)
                    return true;
            }
        }

        //Going UP-LEFT
        flipThis.clear();
        currX = x; currY = y;
        foundEnd = false;
        chipsflipped = 0;
        while(currX - 1 >= 0 && currY - 1 >= 0 && currboard[currX-1][currY-1] != 0) {
            if(currboard[currX-1][currY-1] != player) {
                flipThis.add(new Point(currX-1, currY-1));
                chipsflipped++;
            }
            else {
                foundEnd = true;
                break;
            }
            currX--; currY--;
        }
        if(foundEnd) {
            if(flip) {
                for (Point p : flipThis) {
                    currboard[p.x][p.y] = player;
                }
            }
            else {
                if(chipsflipped != 0)
                    return true;
            }
        }

        //Going UP-RIGHT
        flipThis.clear();
        currX = x; currY = y;
        foundEnd = false;
        chipsflipped = 0;
        while(currX + 1 < 8 && currY - 1 >= 0 && currboard[currX+1][currY-1] != 0) {
            if(currboard[currX+1][currY-1] != player) {
                flipThis.add(new Point(currX+1, currY-1));
                chipsflipped++;
            }
            else {
                foundEnd = true;
                break;
            }
            currX++; currY--;
        }
        if(foundEnd) {
            if(flip) {
                for (Point p : flipThis) {
                    currboard[p.x][p.y] = player;
                }
            }
            else {
                if(chipsflipped != 0)
                    return true;
            }
        }

        //Going DOWN-LEFT
        flipThis.clear();
        currX = x; currY = y;
        foundEnd = false;
        chipsflipped = 0;
        while(currX - 1 >= 0 && currY + 1 < 8 && currboard[currX-1][currY+1] != 0) {
            if(currboard[currX-1][currY+1] != player) {
                flipThis.add(new Point(currX-1, currY+1));
                chipsflipped++;
            }
            else {
                foundEnd = true;
                break;
            }
            currX--; currY++;
        }
        if(foundEnd) {
            if(flip) {
                for (Point p : flipThis) {
                    currboard[p.x][p.y] = player;
                }
            }
            else {
                if(chipsflipped != 0)
                    return true;
            }
        }

        //Going DOWN-RIGHT
        flipThis.clear();
        currX = x; currY = y;
        foundEnd = false;
        chipsflipped = 0;
        while(currX + 1 < 8 && currY + 1 < 8 && currboard[currX+1][currY+1] != 0) {
            if(currboard[currX+1][currY+1] != player) {
                flipThis.add(new Point(currX+1, currY+1));
                chipsflipped++;
            }
            else {
                foundEnd = true;
                break;
            }
            currX++; currY++;
        }
        if(foundEnd) {
            if(flip) {
                for (Point p : flipThis) {
                    currboard[p.x][p.y] = player;
                }
            }
            else {
                if(chipsflipped != 0)
                    return true;
            }
        }

        return false;
    }

    public void finalCountdown() {
        int tot1 = 0;
        int tot2 = 0;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(currboard[j][i] == 1) tot1++;
                if(currboard[j][i] == 2) tot2++;
            }
        }

        printBoard();
        String output = (tot1>tot2) ? "player 1" : "player 2";
        System.out.println("Player 1 has " + tot1 + " points. Player 2 has " + tot2 + " points. The winner is "
                + output);
    }

    public GameBoard cloneGame() {
        GameBoard newBoard = new GameBoard();
        int[][] output = new int[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                output[i][j] = currboard[i][j];
            }
        }
        newBoard.currboard = output;
        ArrayList<Point> newPoints = new ArrayList<Point>();

        for(Point p : currentChips) {
            newPoints.add(p);
        }

        newBoard.currentChips = newPoints;
        return newBoard;
    }

    public int pointsValue(int player) {
        int player1 = 0, player2 = 0;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(currboard[i][j] == 1) player1++;
                if(currboard[i][j] == 2) player2++;
            }
        }
        if(player == 1)
            return player1 - player2;
        else
            return player2 - player1;
    }
}
