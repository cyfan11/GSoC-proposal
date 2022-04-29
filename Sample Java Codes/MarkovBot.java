import java.util.*;

public class MarkovBot implements RoShamBot {
    int roundsCompleted;
    ArrayList<Action> prevMoves;
    int[][] MarkovTable = new int[actions.length][actions.length];
    public static Action[] actions = {Action.ROCK, Action.PAPER, Action.SCISSORS, Action.LIZARD, Action.SPOCK}; 

    
    public MarkovBot() {
        roundsCompleted = 0;
        this.prevMoves = new ArrayList<Action>();
        for (int i = 0; i < actions.length; i++) {
            for (int j = 0; j < actions.length; j++) {
                MarkovTable[i][j] = 0;
            }
        }
    }

    public void updateTable(int lastIndex, int nextIndex) {
        MarkovTable[lastIndex][nextIndex]++;
    }

    public Action randomMove() {
        double coinFlip = Math.random();

        if (coinFlip <= 1.0/5.0)
            return Action.ROCK;
        else if (coinFlip <= 2.0/5.0)
            return Action.PAPER;
        else if (coinFlip <= 3.0/5.0)
            return Action.SCISSORS;
        else if (coinFlip <= 4.0/5.0)
            return Action.LIZARD;
        else 
            return Action.SPOCK;
    }

    public Action counter(Action opponent) {
        double coinFlip = Math.random();
        if (opponent.equals(Action.ROCK)) {
            if (coinFlip <= 0.5) {
                return Action.PAPER;
            } else {
                return Action.SPOCK;
            }
        } else if (opponent.equals(Action.PAPER)) {
            if (coinFlip <= 0.5) {
                return Action.SCISSORS;
            } else {
                return Action.LIZARD;
            }
        } else if (opponent.equals(Action.SCISSORS)) {
            if (coinFlip <= 0.5) {
                return Action.ROCK;
            } else {
                return Action.SPOCK;
            }
        } else if (opponent.equals(Action.LIZARD)) {
            if (coinFlip <= 0.5) {
                return Action.ROCK;
            } else {
                return Action.SCISSORS;
            }
        } else {
            if (coinFlip <= 0.5) {
                return Action.PAPER;
            } else {
                return Action.LIZARD;
            }
        }
    }
    public Action getNextMove(Action lastOpponentMove) {
        

        this.prevMoves.add(lastOpponentMove);
        if (this.roundsCompleted == 0) {
            roundsCompleted++;
            return randomMove();
        } else {

            roundsCompleted++;

            /*
            int rockCount = 0;
            int paperCount = 0;
            int scissorsCount = 0;
            int lizardCount = 0;
            int spockCount = 0;

            for (int i = 0; i < prevMoves.size(); i++) {
                if (prevMoves.get(i).equals(Action.ROCK)) {
                    rockCount++;
                } else if (prevMoves.get(i).equals(Action.PAPER)) {
                    paperCount++;
                } else if (prevMoves.get(i).equals(Action.SCISSORS)) {
                    scissorsCount++;
                } else if (prevMoves.get(i).equals(Action.LIZARD)) {
                    lizardCount++;
                } else {
                    spockCount++;
                } 
            }
            */

            //int[] counts = {rockCount, paperCount, scissorsCount, lizardCount, spockCount};
            
            int nextIndex = 0;
            int prevIndex = Arrays.asList(actions).indexOf(lastOpponentMove);
            
            for (int i = 0; i < actions.length; i++) {
                if (MarkovTable[prevIndex][i] > MarkovTable[prevIndex][nextIndex]) {
                    nextIndex = i;
                }
            }

            Action predictedNext = actions[nextIndex];
            System.out.println(predictedNext);

            updateTable(prevIndex, nextIndex);

            return counter(predictedNext);
        } 
    } 
}