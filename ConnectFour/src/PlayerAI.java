import java.util.ArrayList;

public class PlayerAI extends Player {
	private int token;
	private String playerName;
	private int turns;
	private int maxNumHorz;
	private int maxNumVert;
	private CheckWin threeInARow = new CheckWin(3,1);
	private  ArrayList<PlayerMove> playerOneMoves = new ArrayList<PlayerMove>(); // look for patterns in the moves

	public PlayerAI(int token, String playerName) {
		super(token, playerName);
		this.token = token;
	}
	public void setPlayerOneMove(int playerOneRowMove,int playerOneColMove) {
		playerOneMoves.add(new PlayerMove(playerOneRowMove,playerOneColMove));
	}
	public void printPlayerOneMove() {
		System.out.print("player one moves: ");
		for (int i = 0; i<playerOneMoves.size(); i++) {
			System.out.print("["+(playerOneMoves.get(i)).getRow()+ ", "+ (playerOneMoves.get(i)).getCol()+ "]; ");
		}
		System.out.println("");
	}
	public void play(Board board){
		int colIn = smartChoice(board);
		board.setLowestCell(colIn, getToken());
		System.out.println("computer move: " + colIn);
		//printPlayerOneMove(); 
		turns ++;
	}
	
	public int dumbChoice(Board board) {
		int colChoice;
		do {
			colChoice = (int)(Math.random()*7 );
		}while (!board.validInput(colChoice));
		System.out.println("dumb choice: " + colChoice);
		return colChoice;
	}
	public int smartChoice(Board board) {
		int colChoice = -1;
		int addSubRand = (int)(Math.random()*2);
		//	during the first 4 turns, copies where ever the max number of player one tokens are (playing defensively vertically)
		// this is the typical first few moves of most players
		// later make something to track horizontal movement
		// later look for instances of three in a row and fill in
		if(turns<4) { // change this
			findMaxVertical();
			findMaxHorizontal();
			//				System.out.println("find max num Vert= " + maxNumVert);
			//				System.out.println("find max num Horz = " + maxNumHorz);

			if(maxNumVert >= maxNumHorz){
				//System.out.println("find max = " + findMaxVertical());
				colChoice = findMaxVertical()+1;
			}
			else if(maxNumVert < maxNumHorz){
				if(findMaxHorizontal()==5) { // if still in the first row
					colChoice =  (playerOneMoves.get(playerOneMoves.size()-1)).getCol();
					if(colChoice < 6 && colChoice > 0 ) {
						if(addSubRand == 0) {
							colChoice = (playerOneMoves.get(playerOneMoves.size()-1)).getCol()+1;//findMaxHorizontal();
						}
						if(addSubRand == 1) {
							colChoice = (playerOneMoves.get(playerOneMoves.size()-1)).getCol()-1;//findMaxHorizontal();
						}
					}else { // figure out what to do if not in the same row
						do { 
							colChoice = (int)(Math.random()*7 );
						}while(!board.validInput(colChoice));
					}
				}
			}
			turns ++;
		}
		else {
			return threeInARow.blockPotentialPlayerOne(board);
		}

		if(!board.validInput(colChoice)) {
			do{
				colChoice = (int)(Math.random()*7);
			}while(!board.validInput(colChoice));
		}
		// System.out.println("computer choice: " + (colChoice +1));
		return colChoice;
	}
	public void setMaxNumHorz(int val) {
		maxNumHorz = val; // number of tokens in the max row
	}
	public void setMaxNumVert(int val) {
		maxNumVert  = val; // number of tokens in the max col
	}
	public int findMaxVertical() {
		int [] max = new int [7];
		int maxCol = 0; // col with the most tokens from player 1
		int maxVal = 0; // number of tokens in the max col
		int value = 0;
		for (int i = 0; i < playerOneMoves.size(); i++) {
			value = (playerOneMoves.get(i)).getCol(); 
			max[value] ++;
		}
		for(int i = 0; i<7; i++) {
			if (max[i]>maxVal) {
				maxVal = max[i];
				maxCol = i;
			}
		}
		maxNumVert = maxVal;
		return maxCol;
	}
	public int findMaxHorizontal() {
		int [] max = new int [6];
		int maxRow= 0; 
		int maxVal = 0;
		int value = 0;
		for (int i = 0; i < playerOneMoves.size(); i++) {
			value = (playerOneMoves.get(i)).getRow(); 
			max[value] ++;
		}
		for(int i = 0; i<6; i++) {
			if (max[i]>maxVal) {
				maxVal = max[i];
				maxRow = i;
			}
		}
		maxNumHorz = maxVal;
		return maxRow;
	}
}