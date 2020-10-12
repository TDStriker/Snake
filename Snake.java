package Snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

//import Wrappers.MyColor;

public class Snake extends JFrame implements KeyListener{
	final static int NORTH = 0;
	final static int EAST = 1;
	final static int WEST = 2;
	final static int SOUTH = 3;
	
	protected int mheight = 41;
	protected int mwidth = 51;

	protected boolean autodelay = true;  // delays automatically between drawdot
	    
	// graphical properties:
	protected static int bh = 24; 	// height of a graphical block
	protected static int bw = 24;	// width of a graphical block
	protected int ah, aw;	// height and width of graphical maze
	protected int yoff = 42;    // init y-cord of maze
	protected Graphics g;
	protected int dtime = 30;
	protected Color wallcolor = Color.black;
	protected Color pathcolor = Color.white;
	protected Color dotcolor = Color.yellow;
	protected Color pencolor = Color.red;    
	
	//Player 1 Snake
	private GameGrid board;
	private ArrayList<Record> snake;
	private int dir = WEST;
	private int prevDir = WEST;
	int x;
	int y;
	
	//Player 2 Snake
	private ArrayList<Record> snake2;
	private int dir2 = WEST;
	private int prevDir2 = WEST;
	int x2;
	int y2;
	
	public static void main(String[] args) {
		Snake game = new Snake(17,41,41);
		game.setup();
		
//		game.play();
		game.twoPlay();
	}
	
	public Snake(int bh0, int mh0, int mw0) {
		bh = bh0;  
		bw = bh0;
		mheight = mh0;  
		mwidth = mw0;
		ah = bh*mheight;
		aw = bw*mwidth;
		this.setBounds(0,0,aw,ah+yoff);	
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(this);
		g = getGraphics();
		
		board = new GameGrid(mheight,mwidth);
		snake = new ArrayList<Record>();
		x = mwidth/2;
		y = mheight/2;
		snake.add(new Record(x,y));
		
		snake2 = new ArrayList<Record>();
		x2 = (mwidth/2) - 1;
		y2 = (mheight/2) - 1;
		snake2.add(new Record(x,y));
	}
	
	public void paint(Graphics g) {} // override automatic repaint

	public void setup(){     
	     g.setColor(wallcolor);
	     g.fill3DRect(bh,yoff + bh,aw - 2 * bh,ah - 2 * bh,true);  // fill raised rectangle
	     g.setColor(pathcolor);
	     addFood();
	   }   

	public void delay(int ms)
	    {   
		try {Thread.sleep(ms);} catch(Exception e) {}
	    }
	
	public void drawblock(int y, int x, Color pathColor){
	g.setColor(pathColor);
	g.fillRect(x*bw,yoff+(y*bh),bw,bh);
	g.setColor(pencolor);
	}
	
	public void drawblock(int y, int x){
	g.setColor(pathcolor);
	g.fillRect(x*bw,yoff+(y*bh),bw,bh);
	g.setColor(pencolor);
	}
	public void drawSnake(int x, int y) {
		drawblock(y,x,Color.green);
	}
	public void drawSnake2(int x, int y) {
		drawblock(y,x,Color.cyan);
	}
	public void drawFood(int x, int y) {
		drawblock(y,x,Color.red);
	}
	public void drawdot(int y, int x){
		g.setColor(dotcolor);
		g.fillOval(x*bw,yoff+(y*bh),bw/2,bh/2);
    }
	public void drawMessage(String message) {
		g.drawString(message, bh, yoff);
	}
	public void addFood() {
		int x;
		int y;
		do {
			x = (int)(Math.random() * mwidth);
			y = (int)(Math.random() * mheight);
		}while(!board.spaceValid(x,y));
		board.setSpace(x, y, GameGrid.FOOD);
		drawFood(x,y);
	}
	
	public void play() {
		drawSnake(x,y);
		snake.add(new Record(x,y));
		drawSnake(x + 1,y);
		snake.add(new Record(x + 1,y));
		
		snake2.add(new Record(x2,y2));
		snake2.add(new Record(x2 + 1,y2));
		
		int[] DX = {0,1,-1,0};
		int[] DY = {1,0,0,-1};
		while(board.spaceValid(x+DX[dir], y+DY[dir])) {
			x += DX[dir];
			y += DY[dir];
			
			snake.add(0,new Record(x,y));
			if(!board.isFood(x, y)) {
				drawblock(snake.get(snake.size() - 1).y,snake.get(snake.size() - 1).x,wallcolor);
				board.setSpace(snake.get(snake.size() - 1).x,snake.get(snake.size() - 1).y, GameGrid.EMPTY);
				snake.remove(snake.size() - 1);
			} else {
				addFood();
			}
			drawSnake(x,y);
			board.setSpace(x, y, GameGrid.SNAKE);

			delay(65);
		}
		drawMessage("You Lose");
	}
	
	public void twoPlay() {
		drawSnake(x,y);
		snake.add(new Record(x,y));
		drawSnake(x + 1,y);
		snake.add(new Record(x + 1,y));
		
		drawSnake(x2,y2);
		snake2.add(new Record(x2,y2));
		drawSnake2(x2 + 1,y2);
		snake2.add(new Record(x2 + 1,y2));
		
		int[] DX = {0,1,-1,0};
		int[] DY = {1,0,0,-1};
		while(board.spaceValid(x+DX[dir], y+DY[dir]) && board.spaceValid(x2+DX[dir2], y2+DY[dir2])) {
			//Player 1
			
			x += DX[dir];
			y += DY[dir];
			
			snake.add(0,new Record(x,y));
			if(!board.isFood(x, y)) {
				drawblock(snake.get(snake.size() - 1).y,snake.get(snake.size() - 1).x,wallcolor);
				board.setSpace(snake.get(snake.size() - 1).x,snake.get(snake.size() - 1).y, GameGrid.EMPTY);
				snake.remove(snake.size() - 1);
			} else {
				addFood();
			}
			drawSnake(x,y);
			board.setSpace(x, y, GameGrid.SNAKE);
			
			//Player 2
			
			x2 += DX[dir2];
			y2 += DY[dir2];
			
			snake2.add(0,new Record(x2,y2));
			if(!board.isFood(x2, y2)) {
				drawblock(snake2.get(snake2.size() - 1).y,snake2.get(snake2.size() - 1).x,wallcolor);
				board.setSpace(snake2.get(snake2.size() - 1).x,snake2.get(snake2.size() - 1).y, GameGrid.EMPTY);
				snake2.remove(snake2.size() - 1);
			} else {
				addFood();
			}
			drawSnake2(x2,y2);
			board.setSpace(x2, y2, GameGrid.SNAKE);

			delay(65);
		}
		if(board.spaceValid(x+DX[dir], y+DY[dir])) {
			drawMessage("Player 1 Wins!");
		}else {
			drawMessage("Player 2 Wins!");
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		switch(key) {
		//P1
		case 37:
			dir = WEST;
			break;
		case 38:
			dir = SOUTH;
			break;
		case 39:
			dir = EAST;
			break;
		case 40:
			dir = NORTH;
			break;	
		//P2
		case 87:
			dir2 = SOUTH;
			break;
		case 65:
			dir2 = WEST;
			break;
		case 83:
			dir2 = NORTH;
			break;
		case 68:
			dir2 = EAST;
			break;
		default:
			break;
		}
		
		int[] DX = {0,1,-1,0};
		int[] DY = {1,0,0,-1};
		
		if(x+DX[dir] == snake.get(1).x && y+DY[dir] == snake.get(1).y) {
			dir = prevDir;
		}else {
			prevDir = dir;
		}
		
		if(x2+DX[dir2] == snake2.get(1).x && y2+DY[dir2] == snake2.get(1).y) {
			dir2 = prevDir2;
		}else {
			prevDir2 = dir2;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
