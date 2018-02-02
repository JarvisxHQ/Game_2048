import java.lang.Math;
import java.io.*;
import java.util.*;

public class Game2048 
{
  //=== *** Provided class variables (Don't delete this section) *** ===//
  final public static int LEFT_INPUT  = 0;
  final public static int DOWN_INPUT  = 1;
  final public static int RIGHT_INPUT = 2;
  final public static int UP_INPUT  = 3;
  final public static int S_INPUT  = 5; // Saving the Game
  final public static int R_INPUT  = 6; // Rest the Save File
  final public static int H_INPUT  = 7; // Display HighScore
  final public static int K_INPUT  = 8; // Hack
  
  final public static int VALUE_GRID_SETTING  = 0;
  final public static int INDEX_GRID_SETTING = 1;
  
  private String GAME_CONFIG_FILE = "game_config.txt";
  
  private Game2048GUI gui;
  
  /* position [0][0] represents the Top-Left corner and
   * position [max][max] represents the Bottom-Right corner */
  private int grid [][];
  
  //=== *** Your class variables can be added starting here *** ===//
  private final int EMPTY_SLOT = -1;
  private int x = 0;
  private int y = 0;
  
  
  private int winningLevel;  
  private long currentScore;
  private int currentLevel;
  private long high;
  /**
   * Constructs Game2048 object.
   *
   * @param gameGUI The GUI object that will be used by this class.
   */   
  public Game2048(Game2048GUI gameGUI)
  {
    Random rand = new Random();
    gui = gameGUI;
    grid = new int [4][4];
    int q = 0;
    gui.helpPannel(); 
    try {
      BufferedReader in = new BufferedReader(new FileReader("load.txt"));
      BufferedReader wl = new BufferedReader(new FileReader(GAME_CONFIG_FILE));
      BufferedReader hs = new BufferedReader(new FileReader("highScore.txt"));
      high = Integer.parseInt(hs.readLine());
      hs.close();
      wl.readLine();
      winningLevel = Integer.parseInt(wl.readLine());
      wl.close();
      q = Integer.parseInt(in.readLine());      
      if (q == 1) {  
        int r = gui.loadGame();        
        if (r == 0){ 
          for (int i = 0; i < 4;i++){
            for (int w = 0; w < 4; w++){
              grid[i][w] = Integer.parseInt(in.readLine());
              if (grid[i][w] > 0) gui.setNewSlotBySlotIndex(i, w, grid[i][w]);
            }}
          currentScore = Integer.parseInt(in.readLine());
          gui.setScore(currentScore);
        } else newStart();
        in.close();}
      else newStart();
      
    } catch (IOException iox) {
      System.out.println("Problem Writer");
    }}
  public void newStart() {
    Random rand = new Random();
    try {
      BufferedReader rt = new BufferedReader(new FileReader(GAME_CONFIG_FILE));
      BufferedReader hs = new BufferedReader(new FileReader("highScore.txt"));
      high = Integer.parseInt(hs.readLine());
      hs.close();
      rt.readLine();
      winningLevel = Integer.parseInt(rt.readLine());      
      rt.close();
    } catch (IOException iox) {
      System.out.println("Problem Writer");
    }
    currentScore = 0;
    gui.setScore(currentScore);
    currentLevel = 0;
    x =  rand.nextInt(grid.length);
    y = rand.nextInt(grid[x].length);
    int a = rand.nextInt(2)+1;
    grid[x][y] = a;
    gui.setNewSlotBySlotIndex(x, y, a);
  }
  
  
  
  
  
  // TO DO:  
  // - create and initialize the grid array
  // - initialize the variables 
  //      - winningLevel (value read from text file)
  //      - currentLevel
  //      - currentScore
  // - insert the first number tile
  
  
  
  /**
   * Place a new number tile on a random slot on the grid.
   * This method is called every time a key is released.
   */  
  public void newSlot(boolean moved, boolean check)
  {
    
    // Insert Win and Lose Situation
    if (high < currentScore) high = currentScore;
    if (moved == true && !(check)){
      Random rand = new Random();
      int slot, x, y;
      slot = rand.nextInt((4 * 4));
      x = slot % 4;
      y = slot / 4;
      for (int i = 0; i < 16; i++) {
        if (grid[y][x] == 0) {
          int a = rand.nextInt(5)+1;
          if (a == 1 || a == 4|| a == 3 || a == 5) a = 1;
          if (a == 2 ) a = 2;
          grid[y][x] = a;
          gui.setNewSlotBySlotIndex(y, x, grid[y][x]);
          break;
        } else if (x == 3) {
          x = 0;
          y++;
        } else
          x++;
        if (y == 4)
          y = 0;        
      }}}
  // TO DO: insert a new number tile on the grid
  // Make sure to r if a new tile should be inserted first
  // (a slide or a tile combination has occurred previously)
  
  
  
  /**
   * Plays the game by the direction specified by the user.     
   * This method is called every time a button is pressed
   */  
  public void play(int direction)
  {
    int temp = 0;   
    boolean moved = false;
    if (direction == LEFT_INPUT ) {
      for (int q = 0;q < 4; q++) {        
        for (int i = 0; i < grid.length;i++){              
          if (grid[i][q] > 0 && q != 0){           
            for (int sum = q - 1; sum >= 0; sum--) {
              if (sum >= 0 && grid[i][sum] == grid[i][q]) {
                gui.clearSlot(i, q);
                currentScore = (long) Math.pow(2, grid[i][q]) + currentScore;
                gui.setScore(currentScore);
                moved = true;
                gui.setNewSlotBySlotIndex(i, sum, ++grid[i][q]);
                if (grid[i][q] == winningLevel+1){ highS(high); gui.showGameWon();}
                temp = grid[i][q];
                grid[i][q] = 0;
                grid[i][sum] = temp;
                break;
              } else if (sum >= 0 && grid[i][sum] != 0){
                break;
              }}
            for (int r = 0; grid[i][q] > 0 && r < q;  r++){
              if (grid[i][r] == 0) {
                gui.clearSlot(i,q);
                gui.setNewSlotBySlotIndex(i,r,grid[i][q]);
                temp = grid[i][q];
                grid[i][q] = 0; 
                grid[i][r] = temp;
                moved = true;
                break;
              }}}}}    
      
    } else if (direction == DOWN_INPUT) {
      
      for (int i = grid.length - 1; i >= 0; i--) {
        for (int q = 0; q < grid[i].length; q++) {
          if (grid[i][q] > 0) {
            for (int sum = i + 1; sum <= 3; sum++) {
              if (sum >= 0 && grid[sum][q] == grid[i][q]) {
                gui.clearSlot(i, q);
                currentScore = (long) Math.pow(2, grid[i][q]) + currentScore;
                gui.setScore(currentScore);
                moved = true;
                gui.setNewSlotBySlotIndex(sum, q, ++grid[i][q]);
                if (grid[i][q] == winningLevel+1) {highS(high); gui.showGameWon();}
                temp = grid[i][q];
                grid[i][q] = 0;
                grid[sum][q] = temp;
                break;
              } else if (sum >= 0 && grid[sum][q] != 0)
                break;
            }
            for (int r = 3; grid[i][q] > 0 && r > i; r--)
              if (grid[r][q] == 0) {
              gui.clearSlot(i, q);
              gui.setNewSlotBySlotIndex(r, q, grid[i][q]);
              temp = grid[i][q];
              moved = true;
              grid[i][q] = 0;
              grid[r][q] = temp;
              break;
            }}}}     
      
    } else if (direction == RIGHT_INPUT){
      
      for (int q = 3; q >= 0; q--) {
        for (int i = 0; i < grid.length; i++) {
          if (grid[i][q] > 0) {
            for (int sum = q + 1; sum <= 3; sum++) {
              if (sum >= 0 && grid[i][sum] == grid[i][q]) {
                gui.clearSlot(i, q);
                currentScore = (long) Math.pow(2, grid[i][q]) + currentScore;
                gui.setScore(currentScore);
                moved = true;
                gui.setNewSlotBySlotIndex(i, sum, ++grid[i][q]);
                if (grid[i][q] == winningLevel+1) {highS(high); gui.showGameWon();}
                temp = grid[i][q];
                grid[i][q] = 0;
                grid[i][sum] = temp;
                break;
              } else if (sum >= 0 && grid[i][sum] != 0)
                break;
            }
            for (int r = 3; grid[i][q] > 0 && r > q; r--)
              if (grid[i][r] == 0) {
              gui.clearSlot(i, q);
              gui.setNewSlotBySlotIndex(i, r, grid[i][q]);
              temp = grid[i][q];
              moved = true;
              grid[i][q] = 0;
              grid[i][r] = temp;
              break;
            }}}}      
      
    } else if (direction == UP_INPUT) {
      
      for (int i = 0; i < grid.length; i++) {
        for (int q = 0; q < grid[i].length; q++) {
          if (grid[i][q] > 0) {
            for (int sum = i - 1; sum >= 0; sum--) {
              if (sum >= 0 && grid[sum][q] == grid[i][q]) {
                gui.clearSlot(i, q);
                currentScore = (long) Math.pow(2, grid[i][q]) + currentScore;
                gui.setScore(currentScore);
                moved = true;
                gui.setNewSlotBySlotIndex(sum, q, ++grid[i][q]);
                if (grid[i][q] == winningLevel+1) {highS(high); gui.showGameWon();}
                temp = grid[i][q];
                grid[i][q] = 0;
                grid[sum][q] = temp;
                break;
              } else if (sum >= 0 && grid[sum][q] != 0)
                break;
            }
            for (int r = 0; grid[i][q] > 0 && r < i; r++)
              if (grid[r][q] == 0) {
              gui.clearSlot(i, q);
              gui.setNewSlotBySlotIndex(r, q, grid[i][q]);
              temp = grid[i][q];
              moved = true;
              grid[i][q] = 0;
              grid[r][q] = temp;
              break;
            }}}}    
    } else if (direction == S_INPUT) {
      int save = gui.saveGame();
      if (save == 0) {
        try {
          BufferedWriter out = new BufferedWriter(new FileWriter("load.txt"));
          out.write("1");
          out.newLine();
          for (int i = 0; i < grid.length; i++){
            for (int q = 0; q < grid[i].length; q++){
              out.write(""+grid[i][q]);
              out.newLine();
            }}
          if (high == currentScore) highS(high);
          out.write(""+currentScore);
          out.close();
        } catch (IOException iox) {
          System.out.println("Problem with files" );
        }}}
    else if (direction == R_INPUT) {
      try {
        BufferedWriter out = new BufferedWriter(new FileWriter("load.txt"));
        out.write("0");
        out.newLine();
        for (int i = 0; i < grid.length; i++){
          for (int q = 0; q < grid[i].length; q++){
            out.write("0");
            out.newLine();
          }}
        out.write("0");
        out.close();
        gui.restSave();
      } catch (IOException iox) {
        System.out.println("Problem with files" );
      }}
    else if (direction == H_INPUT) {
      gui.highScore(high,currentScore);
    }
    else if (direction == K_INPUT){
      gui.clearSlot(3, 3);
      gui.clearSlot(3, 2);
      grid[3][3] = 10;
      grid[3][2] = 10;
      gui.setNewSlotBySlotIndex(3, 3, grid[3][3]);
      gui.setNewSlotBySlotIndex(3, 2, grid[3][2]);
    }
    
    int counter = 0;
    boolean check = false;
    if (moved == false) {
      for (int i = 0;i<grid.length;i++){
        for(int q =0; q<grid[i].length;q++){
          if (i != 0) if (grid[i][q] == grid[i-1][q]) {check = true; break;}  // Up
          if (q != 3) if (grid[i][q]==grid[i][q+1]){check = true; break;} // Right
          if (grid[i][q]>0) counter = counter + 1;
        }}
      for (int i = 3;i>=0;i--){
        for(int q =3; q>=0;q--){
          if (i != 0) if (grid[i][q] == grid[i-1][q]){check = true; break;} // Down
          if (q != 3) if (grid[i][q]==grid[i][q+1]) {check = true; break;} // Left
          if (grid[i][q]>0) counter = counter + 1;
        }}}
    if (counter == 32) {
      highS(high);
      gui.showGameOver();
    }
    
    newSlot(moved,check);
    // TO DO:  implement the action to be taken after an arrow key of the
    // specified direction is pressed.
  }
  public void highS(long high) {
    try {
        BufferedWriter out = new BufferedWriter(new FileWriter("highScore.txt"));
        out.write(""+high); 
        out.close();
      }catch (IOException iox) {
        System.out.println("Problem with files" );
      }}}