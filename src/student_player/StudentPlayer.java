package student_player;

import boardgame.Move;
import Saboteur.SaboteurPlayer;
import Saboteur.cardClasses.SaboteurBonus;
import Saboteur.cardClasses.SaboteurCard;
import Saboteur.cardClasses.SaboteurDrop;
import Saboteur.cardClasses.SaboteurMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;

/** A player file submitted by a student. */
public class StudentPlayer extends SaboteurPlayer {
    
    int numGoalsStates = 3;
    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260738004");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(SaboteurBoardState boardState) {
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...

        //update goals
        int [][] currGoalStates = MyTools.getGoals(boardState);
        
        //check num goals 
        int numGoals = 0;
        for(int i = 0; i<3; i++) {
            if(currGoalStates[i][0] < 20) {//if goal live
                numGoals++;
            }
        }
        
        ArrayList<SaboteurMove> legalMoves = boardState.getAllLegalMoves();
        ArrayList<SaboteurCard> cards = boardState.getCurrentPlayerCards();
        
        //if we are in a Malus
        if(boardState.getNbMalus(player_id)>=1) {
            for(SaboteurMove aMove: legalMoves ) {
                if (aMove.getCardPlayed() instanceof SaboteurBonus) {
                    return aMove;
                }
            }
            SaboteurCard Worst = MyTools.worstCard(cards, legalMoves, currGoalStates, boardState); 
            for (int i =0 ; i<cards.size(); i++) {
                if(cards.get(i).equals(Worst)) {
                    return new SaboteurMove(new SaboteurDrop(), i,0, player_id);
                }
            }
        }

        int mapIndex = MyTools.getMapIndex(legalMoves);
        if(mapIndex != -1 && numGoals>1) {
            for(int i = 0; i<3; i++) {
                if(currGoalStates[i][0] < 20) {//if goal live
                    SaboteurMove myMove = new SaboteurMove (new SaboteurMap(), 12, (3 + (2*i)), player_id);
                    return  myMove;
                }
            }
        }

        //get all tile moves if the legalmoves isn't empty
        ArrayList <SaboteurMove> tileMoves = new ArrayList<SaboteurMove>();
        if (!legalMoves.isEmpty()) {
            tileMoves = MyTools.getTileMoves(legalMoves);
        }
        
       ArrayList<SaboteurMove> connectingMoves = new ArrayList<>();

       for (SaboteurMove move : tileMoves) {
           if(MyTools.pathToEntrance(boardState.getHiddenBoard(), move)) {
               connectingMoves.add(move);
           }

       }
 
        int indexOfBestFirstTileMove = MyTools.getBestTile(connectingMoves, boardState, currGoalStates);

        //if there is no problem with a move, return that move      
        if(indexOfBestFirstTileMove == -1)    {
            Move myMove = boardState.getRandomMove();
            return myMove;

        }else{
            String name =connectingMoves.get(indexOfBestFirstTileMove).getCardPlayed().getName().split(":")[1];
            if(name.equalsIgnoreCase("1") || name.equalsIgnoreCase("2") || name.equalsIgnoreCase("2_flip") 
                    || name.equalsIgnoreCase("3") || name.equalsIgnoreCase("3_flip") || name.equalsIgnoreCase("11") || name.equalsIgnoreCase("11_flip")
                    || name.equalsIgnoreCase("13") || name.equalsIgnoreCase("14") || name.equalsIgnoreCase("14_flip") || name.equalsIgnoreCase("15")) {

                for (int i =0 ; i<cards.size(); i++) {
                    if(cards.get(i).equals(connectingMoves.get(indexOfBestFirstTileMove).getCardPlayed())) {
                        return new SaboteurMove(new SaboteurDrop(), i,0, player_id);
                    }
                }
            }else{

                return connectingMoves.get(indexOfBestFirstTileMove);
            }
        }

        //otherwise return random move
        Move myMove = boardState.getRandomMove();
        return myMove;
    }
}
