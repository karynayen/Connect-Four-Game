
public class Board {
	private int [][] gameBoard; 
	private int rows = 6;
	private int cols = 7;

	public Board() {
		gameBoard = new int[rows][cols];
	}
	public boolean validInput(int col) {
		if(col>=cols ||col < 0) {
			return false;
		}
		if(gameBoard[0][col] > 0) {
			return false;
		}
		return true;
	}
	public boolean validInputOut(int col) {
		if(col>=cols ||col < 0) {
			System.out.println("This column does not exist! Please re-enter!");
			return false;
		}
		if(gameBoard[0][col] > 0) {
			System.out.println("This column is full! Please enter a different one!");
			return false;
		}
		return true;
	}
	public String toString(){ 
		
		String toReturn = "";
		toReturn += String.format("\n");
		
		toReturn += String.format("%-3s" , "");
		for (int p = 1; p<=7; p++) {
			toReturn += String.format("%-3s", p);
		}
		toReturn += String.format("\n");
		toReturn += String.format("%-3s", "   -------------------   ");
		toReturn += String.format("\n");
		for (int i = 0; i < rows; i++) {
			toReturn += String.format("%-3s", ":");
			for (int j = 0; j < cols; j++) {
				
				toReturn += String.format("%-3s", getCell(i,j));
			}
			toReturn += String.format("%-1s", ":");
			toReturn += String.format("\n");
		}
		toReturn += String.format("%-3s", "   -------------------   ");
		toReturn += String.format("\n");
		toReturn += String.format("%-24s", ":");
		toReturn += String.format("%-1s", ":");
		toReturn += String.format("\n");
		return toReturn;
	}
	
	public int getCell(int row, int col){
		return gameBoard[row][col];
	}
	public void setCell(int row, int col, int value){
		gameBoard[row][col] = value;
	}
	public int getLowestCell(int col, int value){
		int row = rows-1;
		for(int i = row; i>=0; i--) {
			if (getCell(i,col) == 0){
				return i;
			}
		}
		return 0;
	}
	
	public void setLowestCell(int col, int value){
		int row = rows-1;
		for(int i = row; i>=0; i--) {
			if (getCell(i,col) == 0){
				setCell(i,col, value);
				break;
			}
		}
	}
}
