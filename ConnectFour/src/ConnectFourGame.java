
public class ConnectFourGame {
	//robust checking, make sure check for out of bound + already done
	private Board board = new Board();
	private Player player1 = new Player(1, "one");
	private Player player2=new Player(2, "two");
	private CheckWin checker = new CheckWin(player1,player2);
	private String input = "";
	private boolean isAIPlayer;


	//later give the choice between AI or additional player (by initializing inside the method)
	// scores?
	//play again?
	private boolean choosePlayer; // false codes for player one, and this switches to alternate player
	public void playRound() {

		System.out.println("Welcome to Connect4!");
		System.out.println("GOAL: to Make a straight line of four own pieces. The line can be vertical, horizontal or diagonal. ");
		System.out.println("To play, enter the column of where you want to insert your token");
		do {
			System.out.print("Would you like to play against an AI? (y)es or (n)o: ");
			input = TextIO.getlnString();
		}while(!input.equals("y") && !input.equals("n"));
		if(input.equals("n")) {
			player2 = new Player(2, "two");
		}
		else if(input.equals("y")) {
			player2 = new PlayerAI(2, "two");
			isAIPlayer= true;
		}
		System.out.print(board.toString());
		while (!checker.checkWinResult(board)) {
			if(!choosePlayer) {
				player1.play(board);
				System.out.println("");
				choosePlayer= true;
				// gets all of player one's moves
				if(isAIPlayer) {
					((PlayerAI) player2).setPlayerOneMove(player1.getPlayerRowMove(),player1.getPlayerColMove() );

				}
			}
			else {
				player2.play(board);
				choosePlayer = false;
			}

			System.out.print(board.toString());
		}
		System.out.println(checker.getWinner());
	}




}
