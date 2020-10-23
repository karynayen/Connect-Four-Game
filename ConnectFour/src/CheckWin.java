import java.util.ArrayList;

public class CheckWin {
	//make a separate class to help with organization
	//todo next, figure out a way to check the board
	private Board board; //ok?
	private Player player1;
	private Player player2;
	private int rows = 6;
	private int cols = 7;
	private int goalValue;
	int token;
	private ArrayList<PlayerMove> playerOneMoves;
	int[] doneVertical = new int [7];

	public CheckWin (Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		goalValue = 4;
	}
	// created to check possible player one moves
	public CheckWin (int goalValue, int token) {
		this.goalValue = goalValue; // would be three
		this.token = token; // should be one
	}

	public int blockPotentialPlayerOne(Board board) {
		this.board = board;
		//finish creating something checks for a three in a row, and outputs a move to block this
		// make sure board.valid input
		//this.playerOneMoves = playerOneMoves;
		int toReturn = -1;

		//System.out.println("does this work twoONe:"+ horizontalTwoOneBlock(token) );

		if(verticalCheck(token)) {
			//include a beyond repair block array of ints, if the int is 3, that means that you can no longer
			//insert anything into the file
			toReturn = blockVertical();
		}
		else if(horizontalCheck(token)) {
			toReturn = blockHorizontal();
		}
		else if(horizontalTwoOneBlock(token)>-1) {
			toReturn =  horizontalTwoOneBlock(token);
		}
		if(!board.validInput(toReturn)) {
			do { 
				toReturn = (int)(Math.random()*7 );
				//toReturn ++;
			}while(!board.validInput(toReturn));

		}
		return toReturn;
	}
	public int blockRightDiagonal() {
		//figure out how to check right and left later...
		//check every valid diagonal
		int count = 0;
		int col = 0;
		int row = 0;
		//start row = 3, col = 0, increase col and row by one
		for(int j = 3; j<6; j++) {
			count = 0;
			col = 0;
			for(int i = j; i >= 0; i--) {
				if(board.getCell(i, col) == token) {
					count ++;
				}
				else {
					count = 0;
				}
				if(count == goalValue) //stuff
					col++;
			}
		}
		//start at col 3, row zero, and then decrease the column
		for(int j = 3; j>0; j--) {
			count = 0;
			row = 5;
			for(int i = j; i < cols; i++) {
				if(board.getCell(row,i) == token) {
					count ++;
				}
				else {
					count = 0;
				}
				if(count == goalValue) //stuff
					row--;
			}
		}

		return 0;
	}
	public int horizontalTwoOneBlock(int token) {
		//this method checks to see if there is a set of three of the same tokens separated by an EMPTY spot, if already blocked, move on...
		int count = 0;
		int toReturn = -1;
		for(int i = 0; i <rows; i++) {
			count = 0;
			for(int j = 0; j < cols; j++) {
				if(board.getCell(i,j) == token) {
					count ++;
					if(count == 2) {
						if (j >= 4 && board.getCell(i,j-3) == token && board.getCell(i,j-2)!= 2 ) {
							toReturn = j-2;
							
						}

					}
					else if (j < 4 && board.getCell(i,j+3) == token &&  board.getCell(i,j+2)!= 2) {
						toReturn = j+2;
						
					}
				}
				else {
					count = 0;
				}
			}
		}
		return toReturn;
	}
	public int blockHorizontal() {
		//check each row
		int count = 0;
		int toReturn = -1;
		for(int i = rows-1; i >=0; i--) {
			count = 0;
			for(int j = 0; j < cols; j++) {
				if(board.getCell(i,j) == token) {
					count ++;
					if(count == goalValue) {
						//********************* if blocked both sides, check the next row
						if(j<6 && j>3 && i < 5) {
							if(board.getCell(i+1,j+1)!=0 && (board.getCell(i+1,j-4)!=0)) { //token in next col and row below
								break;
							}
						}
						toReturn = j+1;
						if(!board.validInput(toReturn)) {
							toReturn = toReturn - 4; // check
							if(!board.validInput(toReturn)) { // make sure returned value is valid
								do {
									toReturn = (int)(Math.random()*7);
								} while(!board.validInput(toReturn));
							}
						}
						if(i<5){ // if not in first row
							if(board.getCell((i+1),toReturn) == 0) {
								if(toReturn>3 && board.getCell((i+1),toReturn-4) != 0) {
									toReturn = toReturn - 4; //change?
								}
							}
						}
						if(!board.validInput(toReturn)) {
		
							do {
								toReturn = (int)(Math.random()*7);
							} while(!board.validInput(toReturn));
						}
					}		
				}else {
					count = 0;
				}
			}
		}
		//System.out.println("horizontal block: " + toReturn);
		return toReturn;
	}
	public int blockVertical() {
		//return a column that blocks a vertical move
		// check to see if the max number matches the goalValue (which should be three in the first case)
		int toReturn = -1;
		int count = 0;
		for(int i = 0; i < cols; i++) {
			count = 0;
			for(int j = 0; j < rows; j++) {
				if(board.getCell(j, i) == token) {
					count ++;
					if(count == goalValue) {
						toReturn = i;
						doneVertical[i]=3;
					}
				}else {
					count = 0;
				}
			}
		}
		if(!board.validInput(toReturn)) {
			do {
				toReturn = (int)(Math.random()*7);
			}while(!board.validInput(toReturn));
		}
		return toReturn;
	}

	public String getWinner() {
		if (checkPlayerOne()) {
			return "Player 1 wins";
		}
		if(checkPlayerTwo()) {
			return "Player 2 wins";
		}
		return "it's a tie!";
	}

	public boolean checkWinResult(Board board) { // get time run through, gets a fresh board to check (instead of from initialization)
		this.board = board; // does this work?
		// if player 1 wins, return player 1, if player 2 wins, return player 2
		//else, if no one wins, return false
		//is there a faster way to check everything?
		//if all 
		if(checkPlayerOne() || checkPlayerTwo()) {
			return true;
		}
		return false;
	}
	public boolean checkPlayerOne() { //separate??
		int token1 = player1.getToken();
		if(verticalCheck(token1)||horizontalCheck(token1)||rightDiagonalCheck(token1)||leftDiagonalCheck(token1)||checkTie()) {
			return true;
		}
		return false;
	}
	public boolean checkPlayerTwo() {
		int token2 = player2.getToken();
		if(verticalCheck(token2)||horizontalCheck(token2)||rightDiagonalCheck(token2)||leftDiagonalCheck(token2)||checkTie()) {
			return true;
		}
		return false;
	}
	public boolean checkTie() { // checks if the top row is entirely full
		int count = 0;
		for (int i = 0; i < 7; i++) {
			if (board.getCell(0,i)>0) {
				count ++;
			}
		}
		if(count == 7) {
			return true;
		}
		return false;
	}
	public boolean verticalCheck(int token) {
		int count = 0;
		for(int i = 0; i <cols; i++) {
			count = 0;
			for(int j = 0; j < rows; j++) {
				if(board.getCell(j,i) == token) {
					count ++;
					if(count == goalValue) {
						return true;
					}
				}else {
					count = 0;
				}
			}
		}

		return false;
	}
	//	public boolean alreadyBlockVertical() { /// fininish laterfhgarhs
	//		int count = 0;
	//		for(int i = 0; i <cols; i++) {
	//			count = 0;
	//			for(int j = 0; j < rows; j++) {
	//				if(board.getCell(j,i) == token) {
	//					count ++;
	//					if((count == goalValue)&& {
	//						return true;
	//					}
	//				}else {
	//					count = 0;
	//				}
	//			}
	//		}
	//		return true;
	//	}
	public boolean horizontalCheck(int token) {
		//check each row
		int count = 0;
		for(int i = 0; i <rows; i++) {
			count = 0;
			for(int j = 0; j < cols; j++) {
				if(board.getCell(i,j) == token) {
					count ++;
					if(count == goalValue) {
						return true;
					}
				}else {
					count = 0;
				}
			}
		}

		return false;
	}
	public boolean rightDiagonalCheck(int token) {
		//check every valid diagonal
		int count = 0;
		int col = 0;
		int row = 0;
		//start row = 3, col = 0, increase col and row by one
		for(int j = 3; j<6; j++) {
			count = 0;
			col = 0;
			for(int i = j; i >= 0; i--) {
				if(board.getCell(i, col) == token) {
					count ++;
				}
				else {
					count = 0;
				}
				if(count == goalValue) return true;
				col++;
			}
		}
		//start at col 3, row zero, and then decrease the column
		for(int j = 3; j>0; j--) {
			count = 0;
			row = 5;
			for(int i = j; i < cols; i++) {
				if(board.getCell(row,i) == token) {
					count ++;
				}
				else {
					count = 0;
				}
				if(count == goalValue) return true;
				row--;
			}
		}


		return false;
	}
	public boolean leftDiagonalCheck(int token) {
		//check every valid diagonal
		int count = 0;
		int col = 6;
		int row = 0;
		//start at row 3 col 6 and travel up
		for (int j = 3; j < rows; j++) {
			count = 0;
			col = 6;
			for(int i = j; i >= 0; i--) {
				if(board.getCell(i, col) == token) {
					count ++;
				}
				else {
					count = 0;
				}
				if(count == goalValue) return true;
				col--;
			}
		}
		//start at col 5 to 3, row 0, and move up the row, while decreasing the collumn
		for(int j = 5; j>=3; j--) {
			count = 0;
			col = 5;
			row = 5;
			for(int i = j; i >= 0; i--) {
				if(board.getCell(row,i) == token) {
					count ++;
				}
				else {
					count = 0;
				}
				if(count == goalValue) return true;
				row--;
			}
		}
		return false;
	}
}
