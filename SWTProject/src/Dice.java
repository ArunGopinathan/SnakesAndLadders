
import java.awt.image.BufferedImage;





import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.wb.swt.SWTResourceManager;


public class Dice {

	protected Shell shell;
	private Text DiceField1;
	private Text DiceField2;
	private int dice1;
	BufferedImage image1,image2;
	String DiceImg1="",DiceImg2="";
	private Text roll_text;
	Label[][] board = new Label[10][10]; 
	
	boolean won=false;
	String[] PlayerName={"","",""};
	int[] PlayerThere={0,0,0};
	
	String[] playerColor={"red","green","blue"};
	
	String setBack="";
	
	String[][] boardPicture={
			
			
			
			{"BLANK.png","snake5_15.png","BLANK.png","BLANK.png","snake1_9.png","BLANK.png","BLANK.png","ladder1_3.png","ladder1_4.png","BLANK.png"},
			{"BLANK.png","ladder1_2.png","ladder1_1.png","BLANK.png","BLANK.png","snake1_6.png","snake1_5.png","snake1_4.png","snake5_14.png","BLANK.png"},
			{"BLANK.png","snake5_13.png","snake1_1.png","snake1_2.png","snake1_3.png","ladder3_9.png","ladder3_8.png","snake2_9.png","BLANK.png","BLANK.png"},
			{"snake2_4.png","snake2_5.png","snake2_6.png","ladder3_5.png","ladder3_6.png","BLANK.png","BLANK.png","snake5_12.png","ladder5_4.png","ladder1_3.png"},
			{"ladder1_1.png","ladder5_2.png","snake5_11.png","snake3_7.png","snake3_8.png","BLANK.png","ladder3_2.png","ladder3_1.png","BLANK.png","snake2_1.png"},
			{"BLANK.png","BLANK.png","BLANK.png","BLANK.png","BLANK.png","snake3_6.png","snake3_5.png","ladder_center.png","snake5_8.png","BLANK.png"},
			{"snake5_5.png","snake5_6.png","ladder_center.png","snake3_3.png","snake3_4.png","BLANK.png","BLANK.png","BLANK.png","BLANK.png","snake4_7.png"},
			{"snake4_6.png","ladder6_8.png","ladder6_9.png","BLANK.png","BLANK.png","snake3_head.png","snake3_2.png","ladder_center.png","BLANK.png","snake5_4.png"},
			{"snake5_3.png","snake5_2.png","ladder_center.png","BLANK.png","BLANK.png","BLANK.png","BLANK.png","snake4_5.png","snake4_4.png","snake4_3.png"},
			{"ladder6_1.png","snake4_1.png","snake4_2.png","BLANK.png","BLANK.png","BLANK.png","BLANK.png","ladder_center.png","snake5_1.png","BLANK.png"},
	};
	
	
	
	int CurrentPlayer=-1;
	int PreviousPlayer=-1;
	
	int[] PlayerLocation={0,0,0};
	int[] PlayerPrevLocation={0,0,0};
	
	int[] SnakeBite={99,93,76,50,23};
	int[] SnakeTail={2,68,45,28,5};
	int[] DownLadder={9,26,39,58,73};
	int[] ToUpLadder={13,48,41,98,91};
	
	String BackColorHex="#d4d0c8";
	/**
	 * Launch the application.
	 * @param args
	 */
	
	public static void main(String[] args) {
		try {
			
			
			 Dice window = new Dice();
			StartGame beginGame=window.new StartGame();
			beginGame.open();
			final Display display = Display.getDefault();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
	final Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	public boolean isSnakeBite(int location)
	{
		for(int i=0;i<SnakeBite.length;i++)
		{
			if(location==SnakeBite[i])
				return true;
		}
		return false;
	}
	public boolean isLadderMouth(int location)
	{
		for(int i=0;i<DownLadder.length;i++)
		{
			if(location==DownLadder[i])
				return true;
		}
		return false;
	}
	public boolean isOccupied(int location)
	{
		for(int i=0;i<PlayerLocation.length;i++)
		{
			if(location == PlayerLocation[i])
				return true;
		}
		return false;
	}
	public int WhereIsTheTail(int location){
		int index=-1;
		for(int i=0;i<SnakeBite.length;i++)
		{
			if(location==SnakeBite[i]){
			index=i;
				break;
			}
		}
		//if(index!=-1)
			return SnakeTail[index];
		
	}
	public int WhereIsTheTopOfTheLadder(int location)
	{
		int index=0;
		for(int i=0;i<DownLadder.length;i++)
		{
			if(location==DownLadder[i])
				{
				index=i;
				break;
				}
		}
		return ToUpLadder[index];
	}
	
	public int getNextPlayer(){
		
		if(!won)
		{
		if(CurrentPlayer+1<3)
		{
			if(PlayerThere[CurrentPlayer+1]==1)
			{
				
				
				CurrentPlayer=CurrentPlayer+1;
				return CurrentPlayer;
			}
			else
			{
				CurrentPlayer=CurrentPlayer+1;
				getNextPlayer();
			}
			
		}
		else
		{
			CurrentPlayer=0;
			if(PlayerThere[CurrentPlayer]==1)
			{
				return CurrentPlayer;
			}
			else
				getNextPlayer();
			
		}
		}
		else
		return 911;
		
		return 911;
		
	}
	
	
	public static int getBoardX(int number)
	{
		int xval=number;
		/*return xval;*/
		if(xval>=0 && xval<=10)
			return 0;
		else if(xval>=11 && xval<=20)
			return 1;
		else if(xval>=21 && xval<=30)
			return 2;
		else if(xval>=31 && xval<=40)
			return 3;
		else if(xval>=41 && xval<=50)
			return 4;
		else if(xval>=51 && xval<=60)
			return 5;
		else if(xval>=61 && xval<=70)
			return 6;
		else if(xval>=71 && xval<=80)
			return 7;
		else if(xval>=81 && xval<=90)
			return 8;
		else if(xval>=91 && xval<=100)
			return 9;
		else
			return 911;
		
	}
	public static int getBoardY(int number)
	{
		
		/*if((number/10)%2==0 )
		{
			if((number%10)!=0)
			return ((number%10)-1);
			else
				return number%10;
		}
		else if(number%10==0)
		{
			return 10-1;
		}
		else
		{
			return(10-(number%10));
		}*/
		if(number%10==0)
		return 10-1;
		else
			return number%10-1;
	}
	/*public static int getBoardX(int number)
	{
		
		return (number/10);
	}
	public static int getBoardY(int number)
	{
		if((number/10)%2==0)
		{
			return ((number%10));
		}
		else
		{
			return(9-(number%10));
		}
	}
	*/
	public void ClearPrevTile()
	
	{
	/*	if(prevLocation>0)
		{
		System.out.println("prev loc:"+prevLocation);
		int XIndex=getBoardX(prevLocation);
		int YIndex=getBoardY(prevLocation);
		System.out.println("XIndex:"+XIndex+"YIndex"+YIndex);
	
		board[XIndex][YIndex].setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
	}*/
		
		int clearLocation=PlayerPrevLocation[CurrentPlayer];
		int XIndex=getBoardX(clearLocation);
		int YIndex=getBoardY(clearLocation);
		//board[XIndex][YIndex].setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		System.out.println("Clear PRev Tile :["+ XIndex+"]["+YIndex);
		
		System.out.println("clear prev tile : "+clearLocation);
		if(setBack.equals(""))
		{
		board[XIndex][YIndex].setBackgroundImage(new Image(Display.getCurrent(),Dice.class.getResourceAsStream(boardPicture[XIndex][YIndex])));
		}
		else
		{
			ClearTileToPlayerColor(clearLocation);
		}
	//	board[XIndex][YIndex].redraw();
	}
	public void MoveGUI(int location)
	{
		//Changed PlayerLocation[CurrentPlayer] to location
		if(PlayerLocation[CurrentPlayer]>1)
		{
			//ClearPrevTile();
		}
		final int XIndex=getBoardX(location);
		final int YIndex=getBoardY(location);
		System.out.println("Current loc:"+PlayerLocation[CurrentPlayer]+"location in this :"+location);
		
	
		System.out.println("inside moveGui");
		System.out.println("board["+XIndex+"]["+YIndex+"]");
		
		//board[XIndex][YIndex].setForeground(getPlayerColor());
		board[XIndex][YIndex].setBackgroundImage(getPlayerImage());
		
		
		
		
	}
	public Image getImage(String color)
	{
		if(color.equals("red"))
			return new Image(Display.getCurrent(),Dice.class.getResourceAsStream("player_red.png"));
		else if(color.equals("green"))
			return new Image(Display.getCurrent(),Dice.class.getResourceAsStream("player_green.png"));
		else if(color.equals("blue"))
			return new Image(Display.getCurrent(),Dice.class.getResourceAsStream("player_blue.png"));
		else
			return new Image(Display.getCurrent(),Dice.class.getResourceAsStream("BLANK.png"));
	}
	public Image getPlayerImage()
	{
		if(playerColor[CurrentPlayer].equals("red"))
			return new Image(Display.getCurrent(),Dice.class.getResourceAsStream("player_red.png"));
		else if(playerColor[CurrentPlayer].equals("green"))
			return new Image(Display.getCurrent(),Dice.class.getResourceAsStream("player_green.png"));
		else if(playerColor[CurrentPlayer].equals("blue"))
			return new Image(Display.getCurrent(),Dice.class.getResourceAsStream("player_blue.png"));
		else
			return new Image(Display.getCurrent(),Dice.class.getResourceAsStream("BLANK.png"));
	}
	public void MoveGUI()
	{
		System.out.println("MoveGUI Current loc:"+PlayerLocation[CurrentPlayer]);
		//Changed PlayerLocation[CurrentPlayer] to location
		if(PlayerLocation[CurrentPlayer]>1)
		{
			//ClearPrevTile();
		}
		int XIndex=getBoardX(PlayerLocation[CurrentPlayer]);
		int YIndex=getBoardY(PlayerLocation[CurrentPlayer]);
		//System.out.println("Current loc:"+PlayerLocation[CurrentPlayer]);
		
	
	//	System.out.println("inside moveGui");
	//	System.out.println("board["+XIndex+"]["+YIndex+"]");
		board[XIndex][YIndex].setForeground(getPlayerColor());
		
		
	}
	
	public Color getPlayerColor(){
		if(playerColor[CurrentPlayer].equals("red"))
		{
			return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		}
		else if(playerColor[CurrentPlayer].equals("green"))
		{
			return Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
		}
		else if(playerColor[CurrentPlayer].equals("blue"))
		{
			return Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
		}
		else
		{
			return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
		}
	}
	public int getExistingPlayerIndex(int location)
	{
		int index=-1;
		for(int i=0;i<PlayerLocation.length;i++)
		{
			if(location==PlayerLocation[i])
			{
				index=i;
				break;
			}
		}
		return index;
	}
	public boolean checkWinner()
	{
		if(PlayerLocation[CurrentPlayer]==100)
		{
			won=true;
			return true;
		}
		return false;
	}
	public void ClearTile(int location)
	{
		System.out.println("clearing location "+location);
		int XIndex=0,YIndex=0;
		XIndex=getBoardX(location);
		YIndex=getBoardY(location);
		
		//board[Xindex][Yindex].setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		{board[XIndex][YIndex].setBackgroundImage(new Image(Display.getCurrent(),Dice.class.getResourceAsStream(boardPicture[XIndex][YIndex])));
		}
		
	}
	public void ClearTileToPlayerColor(int location)
	{
		int XIndex=0,YIndex=0;
		XIndex=getBoardX(location);
		YIndex=getBoardY(location);
	if(!setBack.equals(""))
		{
			
			board[XIndex][YIndex].setBackgroundImage(getImage(setBack));
			setBack="";
		}
		else
		{
			
		}
	}
	public void MoveCurrentPlayer()
	{
		final int DiceValue=dice1;
		final int curntPlyrLoc=PlayerLocation[CurrentPlayer];
		int finalPlyrLoc=0;
		if(curntPlyrLoc+DiceValue<=100)
		{
			
			finalPlyrLoc=curntPlyrLoc+DiceValue;
			/*if(isOccupied(finalPlyrLoc))
			{
				setBack=playerColor[getExistingPlayerIndex(finalPlyrLoc)];
				System.out.println("setBackColor: "+setBack);
			}*/
		//	ClearTile(PlayerPrevLocation[CurrentPlayer]);
			PlayerPrevLocation[CurrentPlayer]=curntPlyrLoc;
			System.out.println("PlayerPrevLocation[CurrentPlayer] : "+PlayerPrevLocation[CurrentPlayer]);
			PlayerLocation[CurrentPlayer]=finalPlyrLoc;
			/*for(int i=curntPlyrLoc;i<curntPlyrLoc+DiceValue;i++)
			{
				PlayerLocation[CurrentPlayer]=i;
			
			MoveGUI(finalPlyrLoc);
			PlayerPrevLocation[CurrentPlayer]=curntPlyrLoc;
			}
			*/
			/*for(int i=curntPlyrLoc;i<curntPlyrLoc+DiceValue;i++)
			{
				PlayerLocation[CurrentPlayer]=i;
				System.out.println("for i="+PlayerLocation[CurrentPlayer]);*/
				/*Display.getCurrent().asyncExec(new  Runnable() {
					public void run() {
						try {
							System.out.println("inside runnable");
							//Thread.sleep(1000);
							for(int i=curntPlyrLoc;i<curntPlyrLoc+DiceValue;i++)
							{
								PlayerLocation[CurrentPlayer]=i;
								ClearPrevTile();
							MoveGUI();
							Thread.sleep(1000);
							PlayerPrevLocation[CurrentPlayer]=curntPlyrLoc;
							}
							
						      
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
					});
				*/
			
			/*}*/
			
			
			MoveGUI(finalPlyrLoc);
			
			if(isSnakeBite(PlayerLocation[CurrentPlayer]))
					{
				ClearTile(PlayerPrevLocation[CurrentPlayer]);
				PlayerPrevLocation[CurrentPlayer]=PlayerLocation[CurrentPlayer];		
						
						int TailLocation=WhereIsTheTail(PlayerLocation[CurrentPlayer]);
						PlayerLocation[CurrentPlayer]=TailLocation;
						
						MoveGUI(TailLocation);
						
					}
			
			if(isLadderMouth(PlayerLocation[CurrentPlayer]))
			{
				ClearTile(PlayerPrevLocation[CurrentPlayer]);
				PlayerPrevLocation[CurrentPlayer]=PlayerLocation[CurrentPlayer];
				
				int LadderTopLocation=WhereIsTheTopOfTheLadder(PlayerLocation[CurrentPlayer]);
				PlayerLocation[CurrentPlayer]=LadderTopLocation;
				MoveGUI(LadderTopLocation);
				
			}
			/*for(int i=curntPlyrLoc;i<finalPlyrLoc;i++)
			{
				prevLocation=PlayerLocation[CurrentPlayer];
				PlayerLocation[CurrentPlayer]+=1;
				
				System.out.println("Current player:"+CurrentPlayer+"Current Location: "+PlayerLocation[CurrentPlayer]);
				System.out.println("board["+getBoardX(PlayerLocation[CurrentPlayer]) +"]["+getBoardY(PlayerLocation[CurrentPlayer])+"]" );
				
				MoveGUI();
			}*/
		}
		
		
	}
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.TITLE |SWT.MIN| SWT.CLOSE | SWT.BORDER);
		shell.setFullScreen(true);
		shell.setSize(800, 680);
		shell.setText("Snakes And Ladders");
		
		Group grpRoll = new Group(shell, SWT.NONE);
		grpRoll.setText("Roll");
		grpRoll.setBounds(672, 378, 120, 185);
		
		final Button btnRoll = new Button(grpRoll, SWT.PUSH);
		btnRoll.setBounds(5, 127, 102, 23);
		final Canvas CanvasDice1 = new Canvas(grpRoll, SWT.NONE);
		
		CanvasDice1.setBounds(5, 71, 48, 50);
		
		final Canvas canvasDice2 = new Canvas(grpRoll, SWT.NONE);
		canvasDice2.setVisible(false);
		canvasDice2.setBounds(59, 71, 48, 50);
		
		//Action listener for Roll button 
		btnRoll.addSelectionListener(new SelectionAdapter() {
			@Override
			
			public void widgetSelected(SelectionEvent e) {
				dice1=(int)(Math.random()*6)+1;
				DiceField1.setText(Integer.toString(dice1));
				/*
				dice1=3;
				DiceField1.setText(Integer.toString(dice2));*/
				
				Image image=null;
				try
				{
					 DiceImg1="dice"+dice1+".png";
				//	 DiceImg2="dice"+dice2+".png";

				//	 canvasDice2.redraw();
					 CanvasDice1.redraw();
					
				}
				catch(Exception ex){
					System.out.println("problem while loading image"+ex);
				}
				
				
				System.out.println("board picture"+boardPicture[0][1]);
				
				MoveCurrentPlayer();
				PreviousPlayer=CurrentPlayer;
				
				ClearPrevTile();
				
				if(!checkWinner())
				{
					
					
				getNextPlayer();
				System.out.println("Next player : "+CurrentPlayer);
				//roll message
				roll_text.clearSelection();
				String rollmsg=PlayerName[CurrentPlayer]+" please roll the dice!!";
				roll_text.setText(rollmsg);
				}
				else
				{
					MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK);
					dialog.setText("Winner");
					String winMsg=PlayerName[CurrentPlayer]+" wins !!";
					dialog.setMessage(winMsg);
					
					if(dialog.open()==SWT.OK)
					{
						
						btnRoll.setEnabled(false);
						roll_text.setText("");
					
					}	
				}
			

			        
			}
		});
		btnRoll.setText("Roll");
		DiceField1 = new Text(grpRoll, SWT.BORDER);
		DiceField1.setBounds(5, 156, 24, 19);
		DiceField1.setEditable(false);
		
		DiceField2 = new Text(grpRoll, SWT.BORDER);
		DiceField2.setVisible(false);
		DiceField2.setBounds(61, 156, 24, 19);
		DiceField2.setEditable(false);
		
		roll_text = new Text(grpRoll,SWT.WRAP| SWT.BORDER);
		roll_text.setEditable(false);
		roll_text.setBounds(5, 25, 102, 40);
		
		Group grpScores = new Group(shell, SWT.NONE);
		grpScores.setText("Game Updates");
		grpScores.setBounds(662, 10, 130, 362);
		
		ListViewer listViewer = new ListViewer(grpScores, SWT.H_SCROLL|SWT.BORDER | SWT.V_SCROLL);
		List list_game_log = listViewer.getList();
		list_game_log.setBounds(10, 20, 110, 332);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("File");
		
		Menu menu_1 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_1);
		
		
		//New Game Menu Item 
		MenuItem mntmNewGame = new MenuItem(menu_1, SWT.NONE);
		mntmNewGame.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				shell.dispose();
				Dice window = new Dice();
				StartGame beginGame=window.new StartGame();
				beginGame.open();
				final Display display = Display.getDefault();
				window.open();
				
			}
		});
		mntmNewGame.setText("New Game");
		
		
		//Exit Menu - Code to exit the application
		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
				dialog.setText("My info");
				dialog.setMessage("Do you really want to do EXIT ?");
				
				if(dialog.open()==SWT.OK)
				{
				System.exit(0);
				}
				
			}
		});
		mntmExit.setText("Exit");
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");
		
		Menu menu_2 = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_2);
		
		MenuItem mntmAbout = new MenuItem(menu_2, SWT.NONE);
		mntmAbout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox abtDialog=new MessageBox(shell,SWT.OK);
				abtDialog.setText("About");
				String msg="Snakes and Ladders v0.1\n" +
						   "\n "+
						   "Authors : Arun G \n" +
						   "\n "+
						   " Copyright 2012 :)\n" +
						   "\n"+
						   "All rights reserved ";
				abtDialog.setMessage(msg);
				abtDialog.open();
			}
		});
		mntmAbout.setText("About");
		
		Group grpSnakeAndLadders = new Group(shell, SWT.NONE);
		grpSnakeAndLadders.setText("Snake and Ladders");
		grpSnakeAndLadders.setBounds(10, 10, 646, 616);
		
		
		
		Label lblStart = new Label(grpSnakeAndLadders, SWT.BORDER);
		lblStart.setFont(SWTResourceManager.getFont("Tahoma", 12, SWT.NORMAL));
		lblStart.setBounds(10, 556, 50, 50);
		lblStart.setText("START");
		
		Label label_box2 = new Label(grpSnakeAndLadders, SWT.BORDER);
		label_box2.setVisible(false);
		label_box2.setText("START");
		label_box2.setBounds(10, 500, 50, 50);
		
		Label label_box3 = new Label(grpSnakeAndLadders, SWT.BORDER);
		label_box3.setVisible(false);
		label_box3.setText("START");
		label_box3.setBounds(10, 444, 50, 50);
		
		Label label_box4 = new Label(grpSnakeAndLadders, SWT.BORDER);
		label_box4.setVisible(false);
		label_box4.setText("START");
		label_box4.setBounds(10, 388, 50, 50);
		
		Label label_box5 = new Label(grpSnakeAndLadders, SWT.BORDER);
		label_box5.setVisible(false);
		label_box5.setText("START");
		label_box5.setBounds(10, 332, 50, 50);
		
		Label label_box6 = new Label(grpSnakeAndLadders, SWT.BORDER);
		label_box6.setVisible(false);
		label_box6.setText("START");
		label_box6.setBounds(10, 276, 50, 50);
		
		Label label_box7 = new Label(grpSnakeAndLadders, SWT.BORDER);
		label_box7.setVisible(false);
		label_box7.setText("START");
		label_box7.setBounds(10, 220, 50, 50);
		
		Label label_box8 = new Label(grpSnakeAndLadders, SWT.BORDER);
		label_box8.setVisible(false);
		label_box8.setText("START");
		label_box8.setBounds(10, 164, 50, 50);
		
		Label label_box9 = new Label(grpSnakeAndLadders, SWT.BORDER);
		label_box9.setVisible(false);
		label_box9.setText("START");
		label_box9.setBounds(10, 108, 50, 50);
		
		
		
		
		
		//box 1 second layer 1-10
		int box1size=13;
		
		   
		for( int index = 0; index < 10; index++ )    
		{   
			board[0][ index ] = new Label(grpSnakeAndLadders, SWT.BORDER);  // creates the current label   
			board[0][ index].setText( Integer.toString(index+1) );   
			board[0][index ].setVisible(true);
			board[0][index].setBounds(box1size+50, 556, 50, 50);
			board[0][index].setFont(new Font(Display.getCurrent(),"Tahoma", 14, SWT.BOLD ));

			box1size+=(50+3);
		}  
		
		
		//box 2 second layer 11-20
		int box2start=10;
		
		int box2size=13;
		Label[] box2 = new Label[ 10 ];  // creates the array   
		for( int index = 9; index >= 0; index-- )    
		{   
			board[1][ index ] = new Label(grpSnakeAndLadders, SWT.BORDER);  // creates the current label   
			board[1][ index].setText( Integer.toString(box2start+index+1) );   
			board[1][index ].setVisible(true);
			board[1][index].setBounds(box2size+50, 500, 50, 50);
			board[1][index].setFont(new Font(Display.getCurrent(),"Tahoma", 14, SWT.BOLD ));
			box2size+=(50+3);
		}  
		
		
		//box 3 third layer 21-30
		int box3start=20;
		
		int box3size=13;
		Label[] box3 = new Label[ 10 ];  // creates the array   
		for( int index = 0; index < 10; index++ )    
		{   
			board[2][ index ] = new Label(grpSnakeAndLadders, SWT.BORDER);  // creates the current label   
			board[2][ index].setText( Integer.toString(box3start+index+1) );   
			board[2][index ].setVisible(true);
			board[2][index].setBounds(box3size+50, 444, 50, 50);
			board[2][index].setFont(new Font(Display.getCurrent(),"Tahoma", 14, SWT.BOLD ));
			box3size+=(50+3);
		} 
		
		//box 4 fourth layer 31-40
		int box4start=30;
		
		int box4size=13;
		Label[] box4 = new Label[ 10 ];  // creates the array   
		for( int index = 9; index >= 0; index-- )    
		{   
			board[3][ index ] = new Label(grpSnakeAndLadders, SWT.BORDER);  // creates the current label   
			board[3][ index].setText( Integer.toString(box4start+index+1) );   
			board[3][index ].setVisible(true);
			board[3][index].setBounds(box4size+50, 388, 50, 50);
			board[3][index].setFont(new Font(Display.getCurrent(),"Tahoma", 14, SWT.BOLD ));
			box4size+=(50+3);
		}  
		
		//box 5 fifth layer 41-50
		int box5start=40;
		
		int box5size=13;
		Label[] box5 = new Label[ 10 ];  // creates the array   
		for( int index = 0; index < 10; index++ )    
		{   
			board[4][ index ] = new Label(grpSnakeAndLadders, SWT.BORDER);  // creates the current label   
			board[4][ index].setText( Integer.toString(box5start+index+1) );   
			board[4][index ].setVisible(true);
			board[4][index].setBounds(box5size+50, 332, 50, 50);
			board[4][index].setFont(new Font(Display.getCurrent(),"Tahoma", 14, SWT.BOLD ));
			box5size+=(50+3);
		} 
		
		//box 6 sixth layer 51-60
		int box6start=50;
		
		int box6size=13;
		Label[] box6 = new Label[ 10 ];  // creates the array   
		for( int index = 9; index >= 0; index-- )    
		{   
			board[5][ index ] = new Label(grpSnakeAndLadders, SWT.BORDER);  // creates the current label   
			board[5][ index].setText( Integer.toString(box6start+index+1) );   
			board[5][index ].setVisible(true);
			board[5][index].setBounds(box6size+50, 276, 50, 50);
			board[5][index].setFont(new Font(Display.getCurrent(),"Tahoma", 14, SWT.BOLD ));
			box6size+=(50+3);
		}  
		
		//box 7 seventh layer 61-70
		int box7start=60;
		
		int box7size=13;
		Label[] box7 = new Label[ 10 ];  // creates the array   
		for( int index = 0; index < 10; index++ )    
		{   
			board[6][ index ] = new Label(grpSnakeAndLadders, SWT.BORDER);  // creates the current label   
			board[6][ index].setText( Integer.toString(box7start+index+1) );   
			board[6][index ].setVisible(true);
			board[6][index].setBounds(box7size+50, 220, 50, 50);
			board[6][index].setFont(new Font(Display.getCurrent(),"Tahoma", 14, SWT.BOLD ));
			box7size+=(50+3);
		} 
		
		//box 8 eighth layer 71-80
		int box8start=70;
		
		int box8size=13;
		Label[] box8 = new Label[ 10 ];  // creates the array   
		for( int index = 9; index >= 0; index-- )    
		{   
			board[7][ index ] = new Label(grpSnakeAndLadders, SWT.BORDER);  // creates the current label   
			board[7][ index].setText( Integer.toString(box8start+index+1) );   
			board[7][index ].setVisible(true);
			board[7][index].setBounds(box8size+50, 164, 50, 50);
			board[7][index].setFont(new Font(Display.getCurrent(),"Tahoma", 14, SWT.BOLD ));
			box8size+=(50+3);
		}  
		
		//box 9 ninth layer 81-90
		int box9start=80;
		
		int box9size=13;
		Label[] box9 = new Label[ 10 ];  // creates the array   
		for( int index = 0; index < 10; index++ )    
		{   
			board[8][ index ] = new Label(grpSnakeAndLadders, SWT.BORDER);  // creates the current label   
			board[8][ index].setText( Integer.toString(box9start+index+1) );   
			board[8][index ].setVisible(true);
			board[8][index].setBounds(box9size+50, 108, 50, 50);
			board[8][index].setFont(new Font(Display.getCurrent(),"Tahoma", 14, SWT.BOLD ));
			box9size+=(50+3);
		} 
		
		//box 10 tenth layer 91-100
		int box10start=90;
		
		int box10size=13;
		Label[] box10 = new Label[ 10 ];  // creates the array   
		for( int index = 9; index >= 0; index-- )    
		{   
			board[9][ index ] = new Label(grpSnakeAndLadders, SWT.BORDER);  // creates the current label   
			board[9][ index].setText( Integer.toString(box10start+index+1) );   
			board[9][index ].setVisible(true);
			board[9][index].setBounds(box10size+50, 52, 50, 50);
			board[9][index].setFont(new Font(Display.getCurrent(),"Tahoma", 14, SWT.BOLD ));
			box10size+=(50+3);
		}  
		
		for(int i=0;i<3;i++)
		{
			String plyrClr="";
			if(PlayerThere[i]==1)
			{
				plyrClr=playerColor[i];
				if(plyrClr=="red")
				{
				Color color=Display.getCurrent().getSystemColor(SWT.COLOR_RED);
				lblStart.setBackground(color);
				}
				if(plyrClr=="green")
				{
					Color color=Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
					lblStart.setBackground(color);	
				}
				if(plyrClr=="blue")
				{
					Color color=Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
					lblStart.setBackground(color);
				}
			}
		}
		getNextPlayer();
		
		
		System.out.println("CurrentPlayer : "+CurrentPlayer);
		roll_text.clearSelection();
		String rollmsg=PlayerName[CurrentPlayer]+" please roll the dice!!";
		roll_text.setText(rollmsg);
		/*
		Label label = new Label(grpSnakeAndLadders, SWT.BORDER);
		label.setText("START");
		label.setBounds(58, 561, 45, 45);*/
		
		
		
		//Paint listener to paint the Canvas of Dice2
		canvasDice2.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(org.eclipse.swt.events.PaintEvent e) {
				// TODO Auto-generated method stub
			
				Image image=null;
				try
				{
					if(!DiceImg2.equals(""))
					{
					image =new Image(Display.getCurrent(),Dice.class.getResourceAsStream(DiceImg2));
					
					e.gc.drawImage(image,0,0);
					
					}

				}
				catch (Exception ex) {
					// TODO: handle exception
					//System.out.println("image canvas setting error"+ex);
					ex.printStackTrace();
				}
				

		        
		      
			}
		    });
		
		//paint listener for Dice1 Canvas
		CanvasDice1.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(org.eclipse.swt.events.PaintEvent e) {
				// TODO Auto-generated method stub
				Image image=null;
				try
				{
					if(!DiceImg1.equals(""))
					{
					image =new Image(Display.getCurrent(),Dice.class.getResourceAsStream(DiceImg1));
					
					e.gc.drawImage(image,0,0);
					
					}

				}
				catch (Exception ex) {
					// TODO: handle exception
					//System.out.println("image canvas setting error"+ex);
					ex.printStackTrace();
				}
				

		        
		      
			}
		    });
		for(int i=0;i<3;i++)
		{
			if(PlayerThere[i]==1)
			{
				String update=PlayerName[i]+" joined the game";
				list_game_log.add(update);
			}
		}
		
		
		Image headimg=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snakehead.png"));
		Image tailimg=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snaketail.png"));
		//snake 1 pair
		Image Snake1img1=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake1_1.png"));
		//board[2][2].setImage(Snake1img1);
		//board[2][2].setBackground(getPlayerColor());
		board[2][2].setImage(Snake1img1);
		
	
		Image Snake1img2=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake1_2.png"));
		board[2][3].setBackgroundImage(Snake1img2);
		
		Image Snake1img3=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake1_3.png"));
		board[2][4].setBackgroundImage(Snake1img3);
		
		Image Snake1img4=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake1_4.png"));
		board[1][7].setBackgroundImage(Snake1img4);
		
		Image Snake1img5=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake1_5.png"));
		board[1][6].setBackgroundImage(Snake1img5);
		
		Image Snake1img6=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake1_6.png"));
		board[1][5].setBackgroundImage(Snake1img6);
		
		Image Snake1img7=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake1_2.png"));
		board[0][2].setBackgroundImage(Snake1img7);
		
		Image Snake1img8=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake1_2.png"));
		board[0][3].setBackgroundImage(Snake1img8);
		
		
		Image Snake1img9=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake1_9.png"));
		board[0][4].setBackgroundImage(Snake1img9);
		
		//snake2 pair

		// loc1(4,9) loc2(4,8) loc3(4,7)
		// loc4(3,0) loc5(3,1) loc6(3,2)
		// loc7(2,9) loc8(2,8) loc9(2,7)
		Image Snake2img1=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake2_1.png"));
		board[4][9].setBackgroundImage(Snake2img1);
		Image Snake2img2=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("BLANK.png"));
		board[4][8].setBackgroundImage(Snake2img2);
		Image Snake2img3=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("BLANK.png"));
		board[4][7].setBackgroundImage(Snake2img3);
		
		Image Snake2img4=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake2_4.png"));
		board[3][0].setBackgroundImage(Snake2img4);
		Image Snake2img5=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake2_5.png"));
		board[3][1].setBackgroundImage(Snake2img5);
		Image Snake2img6=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake2_6.png"));
		board[3][2].setBackgroundImage(Snake2img6);
		
		Image Snake2img7=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("BLANK.png"));
		board[2][9].setBackgroundImage(Snake2img7);
		
		Image Snake2img8=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("BLANK.png"));
		board[2][8].setBackgroundImage(Snake2img8);
		
		Image Snake2img9=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake2_9.png"));
		board[2][7].setBackgroundImage(Snake2img9);
 		
		
		//snake3 pair
		Image Snake3headimg=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake3_head.png"));
		board[7][5].setBackgroundImage(Snake3headimg);
		Image Snake3img2=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake3_2.png"));
		board[7][6].setBackgroundImage(Snake3img2);
		Image Snake3img3=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake3_3.png"));
		board[6][3].setBackgroundImage(Snake3img3);
		Image Snake3img4=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake3_4.png"));
		board[6][4].setBackgroundImage(Snake3img4);
		Image Snake3img5=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake3_5.png"));
		board[5][6].setBackgroundImage(Snake3img5);
		Image Snake3img6=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake3_6.png"));
		board[5][5].setBackgroundImage(Snake3img6);
		Image Snake3img7=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake3_7.png"));
		board[4][3].setBackgroundImage(Snake3img7);
		Image Snake3img8=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake3_8.png"));
		board[4][4].setBackgroundImage(Snake3img8);
		
		//snake4 pair
		//loc1(9,1) loc2(9,2)
		//loc3(8,0) loc4(8,1) loc5(8,2)
		//loc6(7,0)
		//loc7(6,9)
		Image Snake4img1=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake4_1.png"));
		board[9][1].setBackgroundImage(Snake4img1);
		Image Snake4img2=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake4_2.png"));
		board[9][2].setBackgroundImage(Snake4img2);
		Image Snake4img3=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake4_3.png"));
		board[8][9].setBackgroundImage(Snake4img3);
		Image Snake4img4=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake4_4.png"));
		board[8][8].setBackgroundImage(Snake4img4);
		Image Snake4img5=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake4_5.png"));
		board[8][7].setBackgroundImage(Snake4img5);
		Image Snake4img6=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake4_6.png"));
		board[7][0].setBackgroundImage(Snake4img6);
		Image Snake4img7=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake4_7.png"));
		board[6][9].setBackgroundImage(Snake4img7);
		
		
		
		
		//board[9][2].setBackgroundImage(headimg);
		
		//board[6][7].setBackgroundImage(tailimg);
		//snake5 pair
		//loc1(9,8) 
		//loc2(8,1) loc3(8,0)
		//loc4(7,9)
		//loc5(6,0)
		Image Snake5img1=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_1.png"));
		board[9][8].setBackgroundImage(Snake5img1);
		Image Snake5img2=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_2.png"));
		board[8][1].setBackgroundImage(Snake5img2);
		Image Snake5img3=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_3.png"));
		board[8][0].setBackgroundImage(Snake5img3);
		Image Snake5img4=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_4.png"));
		board[7][9].setBackgroundImage(Snake5img4);
		Image Snake5img5=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_5.png"));
		board[6][0].setBackgroundImage(Snake5img5);
		Image Snake5img6=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_6.png"));
		board[6][1].setBackgroundImage(Snake5img6);
		
		Image Snake5img8=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_8.png"));
		board[5][8].setBackgroundImage(Snake5img8);
		Image Snake5img11=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_11.png"));
		board[4][2].setBackgroundImage(Snake5img11	);
		Image Snake5img12=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_12.png"));
		board[3][7].setBackgroundImage(Snake5img12	);
		Image Snake5img13=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_13.png"));
		board[2][1].setBackgroundImage(Snake5img13	);
		Image Snake5img14=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_14.png"));
		board[1][8].setBackgroundImage(Snake5img14	);
		Image Snake5img15=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("snake5_15.png"));
		board[0][1].setBackgroundImage(Snake5img15	);
		
		/*board[9][8].setBackgroundImage(headimg);

		board[0][1].setBackgroundImage(tailimg);
*/		
		
		Image ladderleftimg=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder_left.png"));
		Image ladderrightimg=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder_right.png"));
		Image laddercenterimg=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder_center.png"));
		
		//ladder 1 
		Image ladder1img4=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder1_4.png")); 
		board[0][8].setBackgroundImage(ladder1img4);
		Image ladder1img3=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder1_3.png"));
		board[0][7].setBackgroundImage(ladder1img3);
		
		Image ladder1img2=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder1_2.png"));
		board[1][1].setBackgroundImage(ladder1img2);
		Image ladder1img1=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder1_1.png"));
		board[1][2].setBackgroundImage(ladder1img1);
		
		//ladder 2
		board[4][7].setBackgroundImage(ladderrightimg);
		board[3][3].setBackgroundImage(ladderrightimg);
		board[2][5].setBackgroundImage(ladderrightimg);
		//ladder 3
		//loc1(4,7) loc2(4,6) loc3(4,5)
		//loc4(3,2) loc5(3,3) loc6(3,4)
		//loc7(2,7) loc8(2,6) loc9(2,5)
		Image ladder3img1=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder3_1.png"));
		board[4][7].setBackgroundImage(ladder3img1);
		Image ladder3img2=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder3_2.png"));
		board[4][6].setBackgroundImage(ladder3img2);
		Image ladder3img3=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("BLANK.png"));
		board[4][5].setBackgroundImage(ladder3img3);
		Image ladder3img4=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder3_4.png"));
		board[3][2].setBackgroundImage(ladder3img4);
		Image ladder3img5=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder3_5.png"));
		board[3][3].setBackgroundImage(ladder3img5);
		Image ladder3img6=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder3_6.png"));
		board[3][4].setBackgroundImage(ladder3img6);
		
	/*	Image ladder3img7=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("BLANK.png"));
		board[2][7].setBackgroundImage(ladder3img7);*/
		Image ladder3img8=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder3_8.png"));
		board[2][6].setBackgroundImage(ladder3img8);
		Image ladder3img9=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder3_9.png"));
		board[2][5].setBackgroundImage(ladder3img9);
		
		
		//ladder 6
		//loc1(9,0)  loc2(9,1)
		//loc4(8,9,) loc5(8,8) loc6(8,7)
		//loc7(7,0)  loc8(7,1) loc9(7,2)
		Image ladder6img1=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder6_1.png"));
		board[9][0].setBackgroundImage(ladder6img1);
		
		/*Image ladder6img3=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("BLANK.png"));
		board[9][2].setBackgroundImage(ladder6img3);*/
		
		/*Image ladder6img4=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder6_4.png"));
		board[8][9].setBackgroundImage(ladder6img4);*/
		
		/*Image ladder6img5=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder6_5.png"));
		board[8][8].setBackgroundImage(ladder6img5);*/
		
		/*Image ladder6img6=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder6_6.png"));
		board[8][7].setBackgroundImage(ladder6img6);*/
		
		/*Image ladder6img7=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("BLANK.png"));
		board[7][0].setBackgroundImage(ladder6img7);*/
		

		Image ladder6img8=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder6_8.png"));
		board[7][1].setBackgroundImage(ladder6img8);
		
		
		Image ladder6img9=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder6_9.png"));
		board[7][2].setBackgroundImage(ladder6img9);
		
		/*board[9][0].setBackgroundImage(ladderrightimg);
		//board[8][8].setBackgroundImage(ladderrightimg);
		board[7][2].setBackgroundImage(ladderrightimg);*/
		
		//ladder 4
		board[9][7].setBackgroundImage(laddercenterimg);
		board[8][2].setBackgroundImage(laddercenterimg);
		board[7][7].setBackgroundImage(laddercenterimg);
		board[6][2].setBackgroundImage(laddercenterimg);
		board[5][7].setBackgroundImage(laddercenterimg);
		//ladder 5
		
		
		Image ladder5img4=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder1_1.png")); 
		board[4][0].setBackgroundImage(ladder5img4);
		Image ladder5img3=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder5_2.png"));
		board[4][1].setBackgroundImage(ladder5img3);
		
		Image ladder5img2=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder1_3.png"));
		board[3][9].setBackgroundImage(ladder5img2);
		Image ladder5img1=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("ladder5_4.png"));
		board[3][8].setBackgroundImage(ladder5img1);
		
		
		
		Image BlankImg=new Image(Display.getCurrent(),Dice.class.getResourceAsStream("BLANK.png"));
		board[9][9].setBackgroundImage(BlankImg);
		board[9][6].setBackgroundImage(BlankImg);
		board[9][5].setBackgroundImage(BlankImg);
		board[9][4].setBackgroundImage(BlankImg);
		board[9][3].setBackgroundImage(BlankImg);
		
		board[8][3].setBackgroundImage(BlankImg);
		board[8][4].setBackgroundImage(BlankImg);
		board[8][5].setBackgroundImage(BlankImg);
		board[8][6].setBackgroundImage(BlankImg);
		
		board[7][8].setBackgroundImage(BlankImg);
		board[7][4].setBackgroundImage(BlankImg);
		board[7][3].setBackgroundImage(BlankImg);
		
		board[6][5].setBackgroundImage(BlankImg);
		board[6][6].setBackgroundImage(BlankImg);
		board[6][7].setBackgroundImage(BlankImg);
		board[6][8].setBackgroundImage(BlankImg);
		
		board[5][9].setBackgroundImage(BlankImg);
		board[5][4].setBackgroundImage(BlankImg);
		board[5][3].setBackgroundImage(BlankImg);
		board[5][2].setBackgroundImage(BlankImg);
		board[5][1].setBackgroundImage(BlankImg);
		board[5][0].setBackgroundImage(BlankImg);
		
		board[3][6].setBackgroundImage(BlankImg);
		board[3][5].setBackgroundImage(BlankImg);
		
		board[2][0].setBackgroundImage(BlankImg);
		
		board[1][9].setBackgroundImage(BlankImg);
		board[1][4].setBackgroundImage(BlankImg);
		board[1][3].setBackgroundImage(BlankImg);
		board[1][0].setBackgroundImage(BlankImg);
		
		board[0][0].setBackgroundImage(BlankImg);
		board[0][5].setBackgroundImage(BlankImg);
		board[0][6].setBackgroundImage(BlankImg);
		board[0][9].setBackgroundImage(BlankImg);
		
	}
	public class StartGame {

		protected Shell shlSnakesAndLadders;
		private Text text_Player1;
		private Text text_Player2;
		private Text text_Player3;
		
		//String[] PlayerName={"","",""};
		String[] PlayerColor={"red","green","blue"};
		
		/**
		 * Launch the application.
		 * @param args
		 */
		

		/**
		 * Open the window.
		 */
		public void open() {
			Display display = Display.getDefault();
			createContents();
			shlSnakesAndLadders.open();
			shlSnakesAndLadders.layout();
			while (!shlSnakesAndLadders.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		}

		/**
		 * Create contents of the window.
		 */
		
		public int PlayerCount(int[] PlayerArray)
		{
			int count=0;
			for(int i=0;i<PlayerArray.length;i++)
			{
				if(PlayerArray[i]==1)
					count++;
			}
			return count;
		}
		public boolean Players(int[] PlayerArray,String[] PlayerName)
		{
			
			int playrCnt=PlayerCount(PlayerArray);
			System.out.println("playrCnt : "+playrCnt);
			int readyCount=0;
			
			for(int i=0;i<3;i++)
			{
				if(PlayerArray[i]==1 && !PlayerName[i].equals(""))
					readyCount++;
			}
			System.out.println("readyCount : "+readyCount);
			if((readyCount==playrCnt) && (readyCount!=0) && (playrCnt!=0))
			{
				return true;
			}
			else
			{
				return false;
			}
			
			/*for(int i=0;i<3;i++)
			{
				if(PlayerArray[i]==1 && PlayerName[i].equals("") )
				{
					return false;
				}
				else if(PlayerArray[i]==1 && !PlayerName[i].equals("") )
				{
					continue;
				}
				else if(PlayerArray[i]==0)
				{
					continue;
				}
				else
					return false;
			}
			return true;*/
			
			
			
		}
		protected void createContents() {
			shlSnakesAndLadders = new Shell(SWT.TITLE | SWT.BORDER);
			shlSnakesAndLadders.setSize(306, 354);
			shlSnakesAndLadders.setText("Snakes And Ladders");
			
			Label lblPlayer_1 = new Label(shlSnakesAndLadders, SWT.NONE);
			lblPlayer_1.setBounds(10, 77, 49, 13);
			lblPlayer_1.setText("Player 1");
			
			text_Player1 = new Text(shlSnakesAndLadders, SWT.BORDER);
			text_Player1.setEnabled(false);
			text_Player1.setBounds(79, 74, 106, 19);
			final Button btnIncludeplayer1 = new Button(shlSnakesAndLadders, SWT.CHECK);
			btnIncludeplayer1.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
				
					if(btnIncludeplayer1.getSelection()==true)
					{
						text_Player1.setEnabled(true);
					}
					else
					{
						text_Player1.setEnabled(false);
					}
				}
			});
			btnIncludeplayer1.setBounds(10, 38, 85, 16);
			btnIncludeplayer1.setText("include");
			
			Label lblPlayer_2 = new Label(shlSnakesAndLadders, SWT.NONE);
			lblPlayer_2.setText("Player 2");
			lblPlayer_2.setBounds(10, 157, 49, 13);
			
			text_Player2 = new Text(shlSnakesAndLadders, SWT.BORDER);
			text_Player2.setEnabled(false);
			text_Player2.setBounds(79, 154, 106, 19);
			
			final Button btnIncludeplayer2 = new Button(shlSnakesAndLadders, SWT.CHECK);
			btnIncludeplayer2.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if(btnIncludeplayer2.getSelection()==true)
					{
						text_Player2.setEnabled(true);
					}
					else
					{
						text_Player2.setEnabled(false);
					}
				}
			});
			btnIncludeplayer2.setText("include");
			btnIncludeplayer2.setBounds(10, 109, 85, 16);
			
			Label lblPlayer_3 = new Label(shlSnakesAndLadders, SWT.NONE);
			lblPlayer_3.setText("Player 3");
			lblPlayer_3.setBounds(10, 236, 49, 13);
			
			text_Player3 = new Text(shlSnakesAndLadders, SWT.BORDER);
			text_Player3.setEnabled(false);
			text_Player3.setBounds(79, 230, 106, 19);
			
			final Button btnIncludeplayer3 = new Button(shlSnakesAndLadders, SWT.CHECK);
			btnIncludeplayer3.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if(btnIncludeplayer3.getSelection()==true)
					{
						text_Player3.setEnabled(true);
					}
					else
					{
						text_Player3.setEnabled(false);
					}
				}
			});
			btnIncludeplayer3.setText("include");
			btnIncludeplayer3.setBounds(10, 197, 85, 16);
			
			
			//begin game button
			Button btnBeginGame = new Button(shlSnakesAndLadders, SWT.NONE);
			btnBeginGame.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					
					
					if(btnIncludeplayer1.getSelection()==true)
					{
						PlayerThere[0]=1;
					
						if(text_Player1.getText().equals(""))
						{
							MessageBox abtDialog=new MessageBox(shlSnakesAndLadders,SWT.OK);
							String msg="Please enter Player1 Name";
							abtDialog.setMessage(msg);
							abtDialog.open();
							
						}
						else
						{
							PlayerThere[0]=1;
							PlayerName[0]=text_Player1.getText();
							
						}
					}
					else
					{
						PlayerThere[0]=0;	
					}
					
					if(btnIncludeplayer2.getSelection()==true)
					{
						PlayerThere[1]=1;
						if(text_Player2.getText().equals(""))
						{
							
							MessageBox abtDialog=new MessageBox(shlSnakesAndLadders,SWT.OK);
							String msg="Please enter Player2 Name";
							abtDialog.setMessage(msg);
							abtDialog.open();
							
						}
						else
						{
						PlayerThere[1]=1;	
						PlayerName[1]=text_Player2.getText();
						}
					}
					else
					{
						PlayerThere[1]=0;
					}
					
					
					if(btnIncludeplayer3.getSelection()==true)
					{
						PlayerThere[2]=1;
						if(text_Player3.getText().equals(""))
						{
							MessageBox abtDialog=new MessageBox(shlSnakesAndLadders,SWT.OK);
							String msg="Please enter Player3 Name";
							abtDialog.setMessage(msg);
							abtDialog.open();
							
							
						}
						else
						{
							PlayerThere[2]=1;
							PlayerName[2]=text_Player3.getText();
						}
					}
					else
					{
						PlayerThere[2]=0;
					}
					if(Players(PlayerThere,PlayerName))
					{
						for(int i=0;i<3;i++)
							System.out.println(PlayerThere[i]);
						shlSnakesAndLadders.close();
					}
					

					
				}
			});
			btnBeginGame.setBounds(79, 269, 113, 50);
			btnBeginGame.setText("Begin Game");
			
			
			//player 1 color as of now fixed to red
			final Canvas canvasColourPlayer1 = new Canvas(shlSnakesAndLadders, SWT.NONE);
			canvasColourPlayer1.addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent arg0) {
					Color color=Display.getCurrent().getSystemColor(SWT.COLOR_RED); 
					canvasColourPlayer1.setBackground(color);
				}
			});
			canvasColourPlayer1.setBounds(213, 66, 24, 29);
			
			//player 2 color as of  now fixed to green
			final Canvas canvasColourPlayer2 = new Canvas(shlSnakesAndLadders, SWT.NONE);
			canvasColourPlayer2.addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent arg0) {
					Color color=Display.getCurrent().getSystemColor(SWT.COLOR_GREEN); 
					canvasColourPlayer2.setBackground(color);
		
				}
			});
			canvasColourPlayer2.setBounds(213, 141, 24, 29);
			
			//player 3 color as of  now fixed to blue
			final Canvas canvasColourPlayer3 = new Canvas(shlSnakesAndLadders, SWT.NONE);
			canvasColourPlayer3.addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent arg0) {
					Color color=Display.getCurrent().getSystemColor(SWT.COLOR_BLUE); 
					canvasColourPlayer3.setBackground(color);	
				}
			});
			canvasColourPlayer3.setBounds(213, 220, 24, 29);
			
			

		}
	}
}


