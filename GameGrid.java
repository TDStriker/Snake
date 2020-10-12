package Snake;

public class GameGrid {
	final static int EMPTY = 0;
	final static int SNAKE = 1;
	final static int FOOD = 2;
	
	private int[][] grid;
	
	public GameGrid(int rows, int cols) {
		grid = new int[rows][cols];
	}
	
	public int[][] getGrid() {
		return grid;
	}
	
	public boolean inBounds(int x, int y){
		return ((x > 0) && (y > 0) && (x < grid[1].length - 1) && (y < grid[0].length - 1));
	}
	
	public int getValue(int x, int y) {
		return grid[y][x];
	}
	
	public boolean spaceValid(int x, int y) {
		return inBounds(x,y) && (getValue(x,y) != SNAKE);
	}
	
	public boolean isFood(int x, int y) {
		return grid[y][x] == FOOD;
	}
	
	public void setSpace(int x, int y, int status) {
		grid[y][x] = status;
	}
}
