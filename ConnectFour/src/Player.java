
public class Player {
	private int token;
	private String playerName;
	private Board board;
	private int colIn; 
	
	
	public Player(int token, String playerName){
		this.token = token;
		this.playerName = playerName;
		this.board = board;
	}
	public int getToken() {
		return token;
	}
	public int getPlayerColMove() {
		return colIn;
	}
	public int getPlayerRowMove() {
		//top to bottom = 0 to 5
		return board.getLowestCell(colIn,token)+1;
		
	}
	//getLowestCell
	public void play(Board board){
		this.board = board;
		
		do {
			System.out.print("Player " + playerName + ": ");
			colIn = TextIO.getlnInt()-1;
		}while (!board.validInputOut(colIn));
		

		board.setLowestCell(colIn,token);
	}

	

}
