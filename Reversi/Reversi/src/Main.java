import java.net.SocketPermission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {
    static GameBoard board;

    public static void main(String[] args) {

        board = new GameBoard();
        Scanner sc = new Scanner(System.in);

        System.out.println("How many human players?");
        int humanplayers = sc.nextInt();
        sc.nextLine();

        if(humanplayers == 50) {
            int totalwins = 0;
            int totscore = 0;

            int times = 100;

            long start = System.currentTimeMillis();
            for(int i = 0; i < times; i++) {
                if(i%(times/5) == 0) System.out.print(".");
                int tempres = manytest();
                if(tempres > 0) totalwins++;
                totscore+=tempres;

                board = new GameBoard();
            }
            long end = System.currentTimeMillis();
            double elapse = (end - start) / 1000.0;
            double avgscore = totscore/(times + 0.0);
            double avgtime  = elapse/(times + 0.0);

            System.out.println();
            System.out.println("Won " + totalwins + " out of " + times + " times with an average score of " + avgscore + ".");
            System.out.println("Avg time per game: " + avgtime + ". Time total for " + times + " games: " + elapse + ".");
            return;
        }

        System.out.println((2-humanplayers) + " AI Players");
        if(humanplayers == 2) humanOnly(sc);
        if(humanplayers == 1) oneHuman(sc);
        if(humanplayers == 0) twoAI(sc);

        board.finalCountdown();

        return;

        //printBoard();
    }

    private static int manytest() {
        int currplayer = 2;
        MiniMaxAI minibot2 = new MiniMaxAI(2, 3);
        //RandomAI randobot = new RandomAI();
        //FirstPathAI fpAI = new FirstPathAI();
        //RandomAI fpAI = new RandomAI();
        MiniMaxAI minibot = new MiniMaxAI(1,1);
        while(true) {
            if (board.getLegalList(currplayer).size() == 0) {
                break;
            }

            if (currplayer == 1) {
                Point p = minibot.move(board);
                //Point p = fpAI.move(getLegalList(currplayer, realboard));
                board.playChip(p.x, p.y, currplayer);
                currplayer = 2;
            } else if (currplayer == 2) {
                //Point p = randobot.move(getLegalList(currplayer, realboard));
                Point p = minibot2.move(board);
                board.playChip(p.x, p.y, currplayer);
                currplayer = 1;
            } else {
                System.out.println("ERROR");
            }
        }

        int player1 = 0, player2 = 0;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board.currboard[i][j] == 1) player1++;
                if(board.currboard[i][j] == 2) player2++;
            }
        }

        return player1 - player2;
    }

    private static void humanOnly(Scanner sc) {
        int currplayer = 1;
        while(true) {
            board.printBoard();
            System.out.println("What will you do Player " + currplayer + "?\n");


            String temp = sc.nextLine();
            //temp.toUpperCase();

            char fx = temp.toUpperCase().toCharArray()[0];
            char fy = temp.toCharArray()[1];

            int x = (int) fx - 65;
            int y = Character.getNumericValue(fy) - 1;



            boolean valid = board.playChip(x, y, currplayer);
            if(valid) {
                System.out.println("Valid move.");
                currplayer = (currplayer == 1) ? 2:1;
            }
            else {
                System.out.println("Invalid move");
            }

        }
    }

    private static void oneHuman(Scanner sc) {
        System.out.println("Are you player one or two?");
        int humanplayer = sc.nextInt();

        int currplayer = 1;
        RandomAI randobot = new RandomAI();
        while(true) {

            if(currplayer == humanplayer) {
                board.printBoard();
                System.out.println("What will you do Player " + currplayer + "?");

                String temp = sc.nextLine();
                char fx = temp.toUpperCase().toCharArray()[0];
                char fy = temp.toCharArray()[1];
                int x = (int) fx - 65;
                int y = Character.getNumericValue(fy) - 1;

                boolean valid = board.playChip(x, y, currplayer);
                if (valid) {
                    System.out.println("Valid move.");
                    currplayer = (currplayer == 1) ? 2 : 1;
                } else {
                    System.out.println("Invalid move");
                }
            }
            else {
                if(board.getLegalList(currplayer).size() == 0) {
                    break;
                }
                board.printBoard();

                Point p = randobot.move(board.getLegalList(currplayer));
                board.playChip(p.x, p.y, currplayer);
                currplayer = (currplayer == 1) ? 2 : 1;

            }

        }
    }

    private static void twoAI(Scanner sc) {

        int currplayer = 1;
        //RandomAI randobot = new RandomAI();
        MiniMaxAI minibot = new MiniMaxAI(1, 2);
        MiniMaxAI minibot2 = new MiniMaxAI(2, 1);
        while(true) {

                if(board.getLegalList(currplayer).size() == 0) {
                    break;
                }
                board.printBoard();

                if(currplayer == 1) {

                    long starttime = System.currentTimeMillis();
                    Point p = minibot.move(board);
                    long endttime = System.currentTimeMillis();
                    double elapsed = (endttime - starttime) / 1000.0;

                    System.out.println("MINIBOT is playing chip : " + p + " after " + elapsed + " seconds.");
                    board.playChip(p.x, p.y, 1);
                    currplayer = 2;
                }

                else if(currplayer == 2) {
                    long starttime = System.currentTimeMillis();
                    //Point p = randobot.move(getLegalList(currplayer, realboard));
                    Point p = minibot2.move(board);
                    long endttime = System.currentTimeMillis();
                    double elapsed = (endttime - starttime) / 1000.0;
                    //System.out.println("RANDOBOT is playing chip : " + p);

                    System.out.println("MINIBOT is playing chip : " + p + " after " + elapsed + " seconds.");
                    board.playChip(p.x, p.y, 2);
                    currplayer = 1;
                }
                else {
                    System.out.println("ERROR");
                }


        }

        System.out.println("end game");
    }


}

