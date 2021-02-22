package student_player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.SaboteurCard;

import Saboteur.cardClasses.SaboteurTile;
import boardgame.Move;

public class MyTools {
	

	
	private static class MoveAndPathLen{
		public SaboteurMove aMove =null;
		public int aPathLenSoFar = 0;
		
		public MoveAndPathLen(SaboteurMove pMove, int pPathLenSoFar ) {
			aMove = pMove;
			aPathLenSoFar= pPathLenSoFar;
		}
		
	}
	
	
	

	
 public static boolean isTargetFound(SaboteurTile[][] pBoard, int currentX, int currentY) {
	 
	if (currentX < 0 || currentX >= SaboteurBoardState.BOARD_SIZE) {
		return false;
	}
	if (currentY < 0 || currentY >= SaboteurBoardState.BOARD_SIZE) {
		return false;
	}
	if(pBoard[currentX][currentY]==null) {
		return false;
	}
	 
	 return pBoard[currentX][currentY].getIdx().equalsIgnoreCase("nugget") 
			 ||     (currentX == 12 && (currentY == 3 || currentY == 5 || currentY == 7) 
			 && pBoard[currentX][currentY].getIdx().equalsIgnoreCase("8"));
	 
 }
 
 
 public static boolean isMovePossible(int x, int y, HashSet<Integer> visitedTiles, 
		 SaboteurTile[][] pBoard, int xApproach, int yApproach) {
 	if (x < 0 || x >= SaboteurBoardState.BOARD_SIZE) {
		return false;
	}
	if (y < 0 || y >= SaboteurBoardState.BOARD_SIZE) {
		return false;
	}
	if (visitedTiles.contains(x * SaboteurBoardState.BOARD_SIZE + y)) {
		return false;
	}
	SaboteurTile tileOnBoard = pBoard [x][y];
	if(tileOnBoard != null && tileOnBoard.getPath()[xApproach][yApproach] != 1) {
		return false;
	}
	
	return true;
 }
	
 public static int shortestPathLenght(SaboteurTile[][] pBoard, SaboteurMove pInitMove) {
	 
	 Queue<MoveAndPathLen> moveList = new LinkedList<>();
	 moveList.add(new MoveAndPathLen(pInitMove, 0));
	 HashSet<Integer> visitedTiles = new HashSet<>();
	 
	 SaboteurTile defaultTile = new SaboteurTile("8");
	 
	 while (!moveList.isEmpty()) {
		 MoveAndPathLen currentMoveAndPathLen = moveList.poll();
		 SaboteurMove currentMove = currentMoveAndPathLen.aMove;
		 int currentPathLen = currentMoveAndPathLen.aPathLenSoFar;
		 int currentX = currentMove.getPosPlayed()[0];
		 int currentY = currentMove.getPosPlayed()[1];
		 //TODO:maybe +1 for BOARD_SIZE 
		 visitedTiles.add( currentX * SaboteurBoardState.BOARD_SIZE + currentY);
		 
		 
		 
		
		 int[][] currentTile = ((SaboteurTile) currentMove.getCardPlayed()).getPath();
		 
		 if (currentTile[1][1] == 0){
				continue;
		 }
		 
		 
		 if(currentTile[0][1] == 1) {
			 
			 
			 if(isTargetFound(pBoard, currentX, currentY-1)){
				 return currentPathLen;
				 
			 }
			 if (isMovePossible(currentX, currentY - 1, visitedTiles, pBoard, 2, 1)) {
					moveList.add(new MoveAndPathLen(
					new SaboteurMove(pBoard[currentX][currentY - 1] == null ? defaultTile : pBoard[currentX] [currentY-1], currentX, currentY - 1, -1), 
					currentPathLen + 1));
			}
			 
			  
		 }
		 
		 
		 if(currentTile[1][0] == 1) {
			 
			 
			 if(isTargetFound(pBoard, currentX+1, currentY)){
				 return currentPathLen;
				 
			 }
			 if (isMovePossible(currentX+1, currentY, visitedTiles, pBoard, 1, 2)) {
					moveList.add(new MoveAndPathLen(
					new SaboteurMove(pBoard[currentX+1][currentY] == null ? defaultTile : pBoard[currentX+1] [currentY] , currentX+1, currentY, -1), 
					currentPathLen + 1));
			}
			 
			  
		 }
		 
		 
		 if(currentTile[1][2] == 1) {
			 
			 
			 if(isTargetFound(pBoard, currentX-1, currentY)){
				 return currentPathLen;
				 
			 }
			 if (isMovePossible(currentX-1, currentY, visitedTiles, pBoard, 1, 0)) {
					moveList.add(new MoveAndPathLen(
					new SaboteurMove(pBoard[currentX-1][currentY] == null ? defaultTile : pBoard[currentX-1] [currentY], currentX-1, currentY, -1), 
					currentPathLen + 1));
			}
			 
			  
		 }
		 
		 
		 if(currentTile[2][1] == 1) {
			 
			 
			 if(isTargetFound(pBoard, currentX, currentY+1)){
				 return currentPathLen;
				 
			 }
			 if (isMovePossible(currentX, currentY+1, visitedTiles, pBoard, 0, 1)) {
					moveList.add(new MoveAndPathLen(
					new SaboteurMove(pBoard[currentX][currentY+1] == null ? defaultTile : pBoard[currentX] [currentY+1] , currentX, currentY+1, -1), 
					currentPathLen + 1));
			}
			 
			  
		 }
		 

		 
	 }
	 
	 
	 return Integer.MAX_VALUE;
 }
    
    
    
    
    
    
	 public static boolean isEntranceFound(SaboteurTile[][] pBoard, int currentX, int currentY) {
		 
		 
		 
		 return currentX == 5 && currentY == 5;
//			if (currentX < 0 || currentX >= SaboteurBoardState.BOARD_SIZE) {
//				return false;
//			}
//			if (currentY < 0 || currentY >= SaboteurBoardState.BOARD_SIZE) {
//				return false;
//			}
//			if(pBoard[currentX][currentY]==null) {
//				return false;
//			}
//			 
//			 return pBoard[currentX][currentY].getIdx().equalsIgnoreCase("entrance");
			 
	 }
	 
	 
	 public static boolean isPathPossible(int x, int y, HashSet<Integer> visitedTiles, 
			 SaboteurTile[][] pBoard, int xApproach, int yApproach) {
	 	if (x < 0 || x >= SaboteurBoardState.BOARD_SIZE) {
			return false;
		}
		if (y < 0 || y >= SaboteurBoardState.BOARD_SIZE) {
			return false;
		}
		if (visitedTiles.contains(x * SaboteurBoardState.BOARD_SIZE + y)) {
			return false;
		}
		SaboteurTile tileOnBoard = pBoard [x][y];
		if(tileOnBoard == null || tileOnBoard.getPath()[xApproach][yApproach] != 1) {
			return false;
		}
		
		return true;
	 }
    
    
    
    
    
public static boolean pathToEntrance(SaboteurTile[][] pBoard, SaboteurMove pInitMove) {
	 
	 Queue<MoveAndPathLen> moveList = new LinkedList<>();
	 moveList.add(new MoveAndPathLen(pInitMove, 0));
	 HashSet<Integer> visitedTiles = new HashSet<>();
	 
//	 SaboteurTile defaultTile = new SaboteurTile("8");
	 
	 while (!moveList.isEmpty()) {
		 MoveAndPathLen currentMoveAndPathLen = moveList.poll();
		 SaboteurMove currentMove = currentMoveAndPathLen.aMove;
		 int currentPathLen = currentMoveAndPathLen.aPathLenSoFar;
		 int currentX = currentMove.getPosPlayed()[0];
		 int currentY = currentMove.getPosPlayed()[1];
		 //TODO:maybe +1 for BOARD_SIZE 
		 visitedTiles.add( currentX * SaboteurBoardState.BOARD_SIZE + currentY);
		 
		 
		 
		
		 int[][] currentTile = ((SaboteurTile) currentMove.getCardPlayed()).getPath();
		 
		 if (currentTile[1][1] == 0){
				continue;
		 }
		 
		 
		 if(currentTile[0][1] == 1) {
			 
			 
			 if(isEntranceFound(pBoard, currentX, currentY-1)){
				 return true;
				 
			 }
			 if (isPathPossible(currentX, currentY - 1, visitedTiles, pBoard, 2, 1)) {
					moveList.add(new MoveAndPathLen(
					new SaboteurMove(pBoard[currentX][currentY - 1], currentX, currentY - 1, -1), 
					currentPathLen + 1));
			}
			 
			  
		 }
		 
		 
		 if(currentTile[1][0] == 1) {
			 
			 
			 if(isEntranceFound(pBoard, currentX+1, currentY)){
				 return true;
				 
			 }
			 if (isPathPossible(currentX+1, currentY, visitedTiles, pBoard, 1, 2)) {
					moveList.add(new MoveAndPathLen(
					new SaboteurMove(pBoard[currentX+1][currentY], currentX+1, currentY, -1), 
					currentPathLen + 1));
			}
			 
			  
		 }
		 
		 
		 if(currentTile[1][2] == 1) {
			 
			 
			 if(isEntranceFound(pBoard, currentX-1, currentY)){
				 return true;
				 
			 }
			 if (isPathPossible(currentX-1, currentY, visitedTiles, pBoard, 1, 0)) {
					moveList.add(new MoveAndPathLen(
					new SaboteurMove(pBoard[currentX-1][currentY], currentX-1, currentY, -1), 
					currentPathLen + 1));
			}
			 
			  
		 }
		 
		 
		 if(currentTile[2][1] == 1) {
			 
			 
			 if(isEntranceFound(pBoard, currentX, currentY+1)){
				 return true;
				 
			 }
			 if (isPathPossible(currentX, currentY+1, visitedTiles, pBoard, 0, 1)) {
					moveList.add(new MoveAndPathLen(
					new SaboteurMove(pBoard[currentX][currentY+1], currentX, currentY+1, -1), 
					currentPathLen + 1));
			}
			 
			  
		 }
		 

		 
	 }
	 
	 
	 return false;
 } 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // returns the index of the card in legalMoves
    public static int containsCard(SaboteurCard pCard, ArrayList<SaboteurMove> legalMoves) {
    	for(int i = 0 ; i<legalMoves.size(); i++) {
    		if(legalMoves.get(i).getCardPlayed().equals(pCard)) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    
    //returns the biggest manhattan index among all moves
    // bakilmasi gerekiyor
    public static int biggestManhattan(ArrayList<SaboteurMove> legalMoves, int[][] goalStates) {
    	int maxDist = 0;
    	int pos =-1;
    	int i = 0;
    	
    	
    	
    	for(SaboteurMove move: legalMoves) {
    		if( move.getCardPlayed() instanceof SaboteurTile) {
    			
    			int dist = manhattan(move.getPosPlayed()[0], move.getPosPlayed()[1], goalStates);
    			
    			if(dist>maxDist && dist<32) {
    				maxDist = manhattan(move.getPosPlayed()[0], move.getPosPlayed()[1], goalStates);
    				pos = i;
    			}
    		}
    		
    		i++;
    	}
    	
    	
    	return pos;
    	
    }
    
    public static SaboteurCard worstCard(ArrayList<SaboteurCard> pCards, ArrayList<SaboteurMove> legalMoves, int[][] goalStates, SaboteurBoardState boardState) {
			
    	
    	for (SaboteurCard aCard : pCards) {
    		if(aCard instanceof SaboteurTile) {
    			SaboteurTile temp = (SaboteurTile) aCard;
    			//1st case return: the card that has a Wall in the middle
    			//2nd case return: it is a dead end
    			
    			if (temp.getPath()[1][1] == 0) {
    				return aCard;
    			} else if(temp.getIdx().equals("4") || temp.getIdx().equals("4_flip") || temp.getIdx().equals("12") || temp.getIdx().equals("12flip") ) {
    				return aCard;
    			} 
    				
    		} else if (containsCard(aCard, legalMoves)==-1){
    			//3rd case: it is not legal
				return aCard;
			}
    		
    	}
		//4th case biggest distance to the goal state
    	//bi daha bakilabilir
    	
    	if(biggestManhattan(legalMoves, goalStates) == -1) {
    		return boardState.getRandomMove().getCardPlayed();
    	}
		
    	return legalMoves.get(biggestManhattan(legalMoves, goalStates)).getCardPlayed();
	}
    
    
    
    
    

	public static Move evaluateTileDistanceToGoal(ArrayList<SaboteurMove> legalMoves) {
		// go-over all the tile moves, return the one that is closes to any of the goal states 
		
		return null;
	}
	public static ArrayList<SaboteurMove> getTileMoves (ArrayList<SaboteurMove> legalMoves){
		//returns tile moves 
		ArrayList <SaboteurMove> tileMoves = new ArrayList<SaboteurMove>();
		
		for(SaboteurMove move: legalMoves) {
			//get the name of card -> if tile "tile"  add to the tileMoves
			if (move.getCardPlayed() instanceof SaboteurTile) {
				tileMoves.add(move);		
			}
		}
		return tileMoves;
		
	}
	
	
	public static int getMapIndex (ArrayList<SaboteurMove> legalMoves) {
		for (SaboteurMove move: legalMoves){
			if(move.getCardPlayed().getName().split(":")[0].equalsIgnoreCase("map")) {
				return legalMoves.indexOf(move);
			}
		}
		return -1;
	}
	
	
	public static int getBestTile (ArrayList<SaboteurMove> tileMoves, 
			SaboteurBoardState boardState, int [][] goalStates) {
		
		SaboteurMove bestMove = null;
		int minDistance = Integer.MAX_VALUE;
		int indexOfMin = -1;
		//get currBoard 
		SaboteurTile[][] currBoard = boardState.getHiddenBoard();

		for(SaboteurMove move: tileMoves) {
			//get position played 
			int posPlayed[] = move.getPosPlayed();
			//get x, y 
			int x = posPlayed[0];
			int y = posPlayed[1];
		
			//gives the idx of the tile
			String currIdx = move.getCardPlayed().getName().split(":")[1];
			
			
			int currentDistance =  shortestPathLenght(currBoard, move);
			if(currentDistance < minDistance) {
				minDistance = currentDistance;
				indexOfMin = tileMoves.indexOf(move);
			}
			
			
			
			
			//gets the path structure of current tile
//			int [][] path =  new SaboteurTile (currIdx).getPath() ;
//			
//			
//			//we already have the curr board object 
//			//ortasi bos mu checki
//			if(path[1][1] == 0) {
//				continue;			
//			}
//			
//			double myDist = manhattan (x, y, goalStates);
			
			//check left			
//			if(path[0][1] == 1) {//tunnel sola aciliyor mu? 
//				//eger aciliyorsa, bu pozisyonun solu bos mu?
//				if ((y > 0) && (currBoard[x][y-1] == null
//						||  currBoard[x][y-1].getName().split(":")[1].toString().equalsIgnoreCase("nugget"))) {
//					double currMin = ((double) manhattan(x, y-1, goalStates))/myDist;
//						if(currMin < minDistance) {//if curr minimum gives better manhattan distance
//													// then update the minDistance, and the indexOfMin
//							minDistance = currMin;
//							indexOfMin = tileMoves.indexOf(move);
//							System.out.println("min distance: " + minDistance);
//							System.out.println("move made: " + move.getCardPlayed().getName());
//						}
//				}
//			}
			//check right
//			if(path[2][1] ==1) {//tunnel saga aciliyor mu
//				if( (y<14) && (currBoard[x][y+1] == null 
//						||  currBoard[x][y+1].getName().split(":")[1].toString().equalsIgnoreCase("nugget"))) {//tile"in solu bossa
//					double currMin = ((double) manhattan(x, y+1, goalStates))/ myDist;
//						if(currMin < minDistance) {//if curr minimum gives better manhattan distance
//						// then update the minDistance, and the indexOfMin
//							minDistance = currMin;
//							indexOfMin = tileMoves.indexOf(move);
//							System.out.println("min distance: " + minDistance);
//							System.out.println("move made: " + move.getCardPlayed().getName());
//						}					
//				}
//			}
//			//check above
//			if(path[1][2] == 1 ) {
//				if(x>0 && (currBoard[x-1][y] == null 
//						||  currBoard[x-1][y].getName().split(":")[1].toString().equalsIgnoreCase("nugget"))) {
//					double currMin = ((double) manhattan(x-1, y, goalStates))/myDist;
//					if(currMin < minDistance) {
//						minDistance = currMin;
//						indexOfMin = tileMoves.indexOf(move);
//						System.out.println("min distance: " + minDistance);
//						System.out.println("move made: " + move.getCardPlayed().getName());
//					}
//				}
//			}
//			
//			//check below
//			if(path[1][0] == 1 ) {
//				if(x < 14 && (currBoard[x+1][y] == null
//						||  currBoard[x+1][y].getName().split(":")[1].toString().equalsIgnoreCase("nugget"))) {
//					double currMin = ((double) manhattan(x+1, y, goalStates))/myDist;
//					if(currMin< minDistance) {
//						minDistance = currMin;
//						indexOfMin = tileMoves.indexOf(move);
//						System.out.println("min distance: " + minDistance);
//						System.out.println("move made: " + move.getCardPlayed().getName());
//					}	
//				}
//			}
		}
		return indexOfMin;
	}

	private static int manhattan(int x, int y, int[][] goalStates) {
		// TODO Auto-generated method stub
		int minDistance = 999999;
		
		for(int i = 0; i<3; i++) {//go over all goal states 
			int xDist = Math.abs(x - goalStates[i][0]);
			int yDist = Math.abs(y - goalStates[i][1]);
			if((xDist+yDist) < minDistance) {
				minDistance = xDist + yDist -1;
			}
		}

		return minDistance;
	}

	public static int[][] getGoals(SaboteurBoardState boardState) {
		// TODO Auto-generated method stub
		
		
		int [][] goalStates = new int [][] {{10000,10000},{100000,100000},{100000,100000}};
		SaboteurTile[][] currBoard = boardState.getHiddenBoard();
		
		
		
		if(currBoard[12][3].getName().split(":")[1].equalsIgnoreCase("nugget")) {
			goalStates[0] = new int [] {12,3};
			return goalStates;
			
		}else if (currBoard[12][5].getName().split(":")[1].equalsIgnoreCase("nugget")){
			goalStates[1] = new int [] {12,5}; 
			return goalStates;
			
		}else if (currBoard[12][7].getName().split(":")[1].equalsIgnoreCase("nugget")){
			goalStates[2] = new int [] {12,7}; 
			return goalStates;
		}
		
		
		
		
		//for 12,3
		if(currBoard[12][3].getName().split(":")[1].equalsIgnoreCase("8")) {
			goalStates[0] = new int [] {12,3};
		}
		
		
		//for 12,5 
		if(currBoard[12][5].getName().split(":")[1].equalsIgnoreCase("8")) {
			goalStates[1] = new int [] {12,5};
		}
		
		
		//for 12,7
		if(currBoard[12][7].getName().split(":")[1].equalsIgnoreCase("8")) {
			goalStates[2] = new int [] {12,7};
		}
		
		return goalStates;
	}
}
		
	/* public String getName(){
        return "Tile:"+this.idx;
    }
	 * 
	 * 
	 * 
	 * 
	 */
