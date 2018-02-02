   import javax.swing.*;
   import javax.swing.border.*;

   import java.awt.*;
   import java.io.*;

/**
 * Game2048GUI class manages the displaying of the <b>logo</b>, <b>score</b> and the <b>grid</b>. It does not contain any logic of the game.
 */
    class Game2048GUI
   {
   /**
    * The number of rows the grid will be.
    */
      final static int NUM_ROW = 4;
   
   /**
    * The number of rows the grid will be.
    */   
      final static int NUM_COLUMN = 4;
   
   /**
    * The thickness of the border between the slots on the grid.
    */      
      private final int BORDER_THICKNESS = 2;
   
   /**
    * The individual size of each slot in pixel.
    */     
      private final int PIECE_SIZE = 50;
   
   /**
    * The <b>logo</b> width in pixel.
    */  
      private final int LOGO_WIDTH = (PIECE_SIZE * NUM_COLUMN) + (BORDER_THICKNESS * NUM_COLUMN * 2);
   
   /**
    * The <b>logo</b> height in pixel.
    */    
      private final int LOGO_HEIGHT = 150;
   
   /**
    * The <b>score</b> panel width in pixel.
    */    
      private final int SCORE_WIDTH = (PIECE_SIZE * NUM_COLUMN) + (BORDER_THICKNESS * NUM_COLUMN * 2);
   
   /**
    * The <b>score</b> panel height in pixel.
    */    
      private final int SCORE_HEIGHT = 30;
   
   /**
    * The <b>game</b> JFrame's width in pixel.
    */    
      private final int FRAME_WIDTH = (PIECE_SIZE * NUM_ROW) + (BORDER_THICKNESS * NUM_ROW * 2);
   
   /**
    * The <b>game</b> JFrame's height in pixel.
    */       
      private final int FRAME_HEIGHT = LOGO_HEIGHT + SCORE_HEIGHT + 25 + (PIECE_SIZE * NUM_ROW) + (BORDER_THICKNESS * NUM_ROW * 2);
   
   /**
    * The <b>grid</b> width in pixel.
    */    
      private final int GRID_WIDTH = NUM_COLUMN * PIECE_SIZE;
   
   /**
    * The <b>grid</b> height in pixel.
    */       
      private final int GRID_HEIGHT = NUM_ROW * PIECE_SIZE;
   
   /**
    * The settings for individual regular slot's line border.
    */    
      private final LineBorder OLD_SLOT_SETTINGS = new LineBorder (Color.gray, BORDER_THICKNESS, false);
   
   /**
    * The settings for newly randomized slot's line border.
    */       
      private final LineBorder NEW_SLOT_SETTINGS = new LineBorder (Color.gray, BORDER_THICKNESS, false);
   
   /**
    * Contains the filename of the GUI config file.
    */      
      private final String GUI_CONFIG_FILE = "gui_config.txt";
   
   /**
    * Filename of the <b>Logo</b> filename.
    */         
      private String logoIconFile;
   
       
   /**
    * An array that stores all the filename of the <i>slots</i> filename. Index 0 contains the filename of the image for value 2, index 1 contains the image for value 4 and etc.
    */         
      private String[] slotIconFile;
   
   /**
    * The background color of the grid.
    */     
      private Color gridBackgroundColor = Color.black;
   
   // The game's JFrame and JLabel
   /**
    * The <b>Main</b> JFrame of the game.
    */ 
      private JFrame mainFrame;
    
   /**
    * The grid that wraps around the slots. Changing each individual JLabel will alter the look of the grid.
    */ 
      private JLabel[][] guiGrid;
   
   /**
    * The JLabel that displays the score.
    */    
      private JLabel scoreLabel;
   
   //================= CONSTRUCTOR =================//
   /**
    * Constructor for the Game2048GUI class.
    */
       public Game2048GUI() 
      {
         this.initConfig();   // Initial config from the file config.txt
         this.initSlots();    // Initial each slots' visual appearance
         this.createFrame();  // Create the game grid
      }
   
   //================= PRIVATE METHODS ====================//
   /**
    * Initialize the GUI with the config file <code>gui_config.txt</code>.
    * If the file does not exist, it will throw an IOException error.
    */
       private void initConfig()
      {
         try {
         BufferedReader in = new BufferedReader(new FileReader(GUI_CONFIG_FILE));
         logoIconFile = in.readLine();
         int a = Integer.parseInt(in.readLine());
         slotIconFile = new String [a]; 
         String input1 = in.readLine();
         int i = 0;
         while (input1 != null){
           slotIconFile [i] = input1;
           input1 = in.readLine(); 
           i++; 
         }
         in.close();
         } catch (IOException iox) {
      System.out.println("Problem Writer");
    }  
      }
   
   /**
    * Initialize the individual slots that hold the numbers. 
    */   
       private void initSlots() 
      {
         guiGrid = new JLabel[NUM_ROW][NUM_COLUMN];
         for (int i = 0; i < NUM_ROW; i++) 
         {
            for (int j = 0; j < NUM_COLUMN; j++)
            {
               guiGrid[i][j] = new JLabel();
               guiGrid[i][j].setPreferredSize(new Dimension(PIECE_SIZE, PIECE_SIZE));
               guiGrid[i][j].setHorizontalAlignment (SwingConstants.CENTER);
               guiGrid[i][j].setBorder (OLD_SLOT_SETTINGS);       
            }
         }
      }
   
   /**
    * Create a <b>Logo</b> <i>JPanel</i> to be used in a <i>JFrame</i> or <i>JPanel</i>
    */   
       private JPanel createLogoPanel()
      {
         JPanel panel = new JPanel();
         panel.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));
         JLabel logo = new JLabel();
         logo.setIcon(new ImageIcon(logoIconFile));
         panel.add(logo);
         return panel;
      }
   
   /**
    * Create a <b>Score</b> <i>JPanel</i> to be used in a <i>JFrame</i> or <i>JPanel</i>
    */ 
       private JPanel createScorePanel()
      {
         JPanel panel = new JPanel();
         panel.setPreferredSize(new Dimension(SCORE_WIDTH, SCORE_HEIGHT));
      
         JLabel label = new JLabel("Your Score: ");
      
         panel.add(label);
         panel.add(this.scoreLabel);
      
         return panel;   
      }
   
   /**
    * Create a <b>Grid</b> <i>JPanel</i> to be used in a <i>JFrame</i> or <i>JPanel</i>.
    * <p>
    * The <b>grid</b> is the interface that contains the individual <b>slots</b>.
    */    
       private JPanel createGridPanel()
      {
         JPanel panel = new JPanel(); 
         panel.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
         panel.setBackground(gridBackgroundColor);
         panel.setLayout(new GridLayout(NUM_ROW, NUM_COLUMN));
      
         for (int i = 0; i < NUM_ROW; i++) 
         {
            for (int j = 0; j < NUM_COLUMN; j++) 
            {
               panel.add(guiGrid[i][j]);
            }
         }
         return panel;    
      }
      
   /**
    * Create the <b>Game Frame</b> and the <b>Game Over Frame</b>.
    * <p>
    * The <b>Game Frame</b> contains the following:
    * <ul>
    *  <li>mainPanel - The <b>main</b> JPanel that wraps around all the other sub JPanels
    *  <li>logoPanel - The JPanel that wraps around the <b>logo</b>.
    *  <li>scorePanel - The JPanel that wraps around the score to be displayed.
    *  <li>gridPanel - The JPanel that wraps around all the <b>slots</b>.
    * </ul>
    */        
       private void createFrame()
      {
         Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
         scoreLabel = new JLabel("0");
      
      // Create game frame
         mainFrame = new JFrame ("2048");
         mainFrame.setLocation( dim.width/2 - FRAME_WIDTH/2, dim.height/2 - FRAME_HEIGHT/2); 
      
         JPanel mainPanel = (JPanel)mainFrame.getContentPane();
         mainPanel.setLayout (new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
         mainPanel.setPreferredSize(new Dimension (FRAME_WIDTH, FRAME_HEIGHT));
      
      // Create the panel for the logo
         JPanel logoPanel = new JPanel();
         logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
         logoPanel.setPreferredSize(new Dimension (LOGO_WIDTH, LOGO_HEIGHT));
         logoPanel.add( createLogoPanel() );   
      
      // Create the panel for the logo
         JPanel scorePanel = new JPanel();
         scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
         scorePanel.setPreferredSize(new Dimension (SCORE_WIDTH, SCORE_HEIGHT));
         scorePanel.add( createScorePanel() );  
         
      
      // Create game panel 
         JPanel gridPanel = new JPanel();
         gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.X_AXIS));
         gridPanel.setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
         gridPanel.add( createGridPanel() );
      
      // Add all the panels to main panel  
         mainPanel.add(logoPanel);
         mainPanel.add(scorePanel);    
         mainPanel.add(gridPanel);
            
      // Show main frame
         mainFrame.setContentPane(mainPanel);
         mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
         mainFrame.setVisible(true);
         mainFrame.setResizable(false);
         mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      }
   
   /**
    * Change values of the slot to the index that represents the slot.
    * 
    * @param slotValue  The value of a slot.
    */   
       private int slotValueToIndex(int slotValue)
      {
         return (int)((Math.log10(slotValue) / Math.log10(2)) - 1);
      }
   
   /**
    * Delays the randomized slot to appear to simulate the animation effect.
    * 
    * @param time Time delaying represented in milliseconds. <i>(Example: 1000 = 1 second)</i>
    */   
       private void delay(int time)
      {
         try 
         {
            Thread.sleep(time);
         } 
             catch(Exception ex) 
            {
            }   
      }
   
   //================= PUBLIC METHODS ====================//
   /**
    * It displays the GameOver frame.
    */     
       public void showGameOver()
      {
         JOptionPane.showMessageDialog(null, "Game Over!" , "The game is finished", JOptionPane.PLAIN_MESSAGE);    
         System.exit (0);
      }
   
   /**
    * It displays the Game Win frame.
    */  
       public void showGameWon()
      {
         JOptionPane.showMessageDialog(null, "You Have Won!" , "The game is finished", JOptionPane.PLAIN_MESSAGE);   
         System.exit (0);
      }
       public void helpPannel()
      {
         JOptionPane.showMessageDialog(null, "The game is fairly simple, combine tiles to increase the score. You win when you get the tile 2048 \nPress S to save the game\nPress H to view the high score and current score\nPress R to reset the save game\n Good Luck !!!" , ">>> User Mannual >>>", JOptionPane.PLAIN_MESSAGE);
      }
       public int loadGame() {
         int load = JOptionPane.showConfirmDialog(null,"Load the game ?"," >>> Loading the Game >>>", JOptionPane.YES_NO_OPTION);
         return load; 
       }
       public int saveGame() {
         int save = JOptionPane.showConfirmDialog(null,"Save the Game ?"," >>> Saving the Game >>>", JOptionPane.YES_NO_OPTION);
         return save; 
       }
       public void restSave()
      {
         JOptionPane.showMessageDialog(null, "Reset Succesfull !!! " , ">>> Reset >>> ", JOptionPane.PLAIN_MESSAGE);   
      }
       public void highScore(long high, long currentScore)
      {
         JOptionPane.showMessageDialog(null, "High Score - "+ high +"\nCurrent Score - "+currentScore , "ScoreCard", JOptionPane.PLAIN_MESSAGE);   
      }
       
       
   
   /**
    * Add <i>Listener</i> to the <b>mainFrame</b> JFrame to capture user's input.
    *
    * @param listener The listener to add to <i>mainFrame</i>
    */   
       public void addListener (Game2048Listener listener) 
      {
         mainFrame.addKeyListener(listener);
      }
   
   /**
    * Set the score and have the GUI displays it.
    *
    * @param score The score to be displayed
    */      
       public void setScore(long score)
      {
         this.scoreLabel.setText(Long.toString(score));
      }
   
   /**
    * Set new slot to be appeared
    *
    * @param rowIndex   The row index on the grid represented by the <b>slots</b> array.
    * @param columnIndex  The column index on the grid represented by the <b>slots</b> array.
    * @param slotIndex   The index of the array that contains the <b>slotIconFile</b> filename.
    */        
       public void setNewSlotBySlotIndex(int rowIndex, int columnIndex, int slotIndex)
      {
         delay(1);
         guiGrid[rowIndex][columnIndex].setIcon(new ImageIcon(slotIconFile[slotIndex-1]));
         guiGrid[rowIndex][columnIndex].setBorder (NEW_SLOT_SETTINGS);   
      }
   
   /**
    * Set new slot to be appeared
    *
    * @param rowIndex   The row index on the grid represented by the <b>slots</b> array.
    * @param columnIndex  The column index on the grid represented by the <b>slots</b> array.
    * @param slotValue   The value of the new slot. Value must be one of the values in the game.
    */        
       public void setNewSlotBySlotValue(int rowIndex, int columnIndex, int slotValue)
      {
         delay(150);
      
         int slotIndex = slotValueToIndex(slotValue); 
         guiGrid[rowIndex][columnIndex].setIcon(new ImageIcon(slotIconFile[slotIndex]));
         guiGrid[rowIndex][columnIndex].setBorder (NEW_SLOT_SETTINGS);   
      } 
   
   /**
    * Clear a slot on the grid.
    *
    * @param rowIndex   The row index on the grid represented by the <b>slots</b> array.
    * @param columnIndex  The column index on the grid represented by the <b>slots</b> array.
    */          
       public void clearSlot(int rowIndex, int columnIndex)
      {
         guiGrid[rowIndex][columnIndex].setIcon(null);
      }
   
   /**
    * It takes in a grid array with slots represented by indexes and displays it.
    *
    * @param grid   The grid that contains all the slots with indexes of the current state of the game. 
    */         
       public void setGridByIndex(int [][] grid)
      {
         for( int i = 0; i < Game2048GUI.NUM_ROW; i++)
         {
            for( int j = 0; j < Game2048GUI.NUM_COLUMN; j++)
            {
               int slotValue = grid[i][j];
               if( slotValue >= 0)
               {
                  guiGrid[i][j].setIcon(new ImageIcon(slotIconFile[slotValue-1]));
               }
               else
               {
                  guiGrid[i][j].setIcon(null);
               }
               guiGrid[i][j].setBorder (OLD_SLOT_SETTINGS); 
            }
         } 
      }   
   
   /**
    * It takes in a grid array with slots represented by values and displays it.
    *
    * @param grid   The grid that contains all the slots with values of the current state of the game. 
    */         
       public void setGridByValue(int [][] grid)
      {
         for( int i = 0; i < Game2048GUI.NUM_ROW; i++)
         {
            for( int j = 0; j < Game2048GUI.NUM_COLUMN; j++)
            {
               int slotValue = grid[i][j];
               int slotIndex = slotValueToIndex(slotValue);
               if( slotValue >= 0)
               {
                  guiGrid[i][j].setIcon(new ImageIcon(slotIconFile[slotIndex]));
               }
               else
               {
                  guiGrid[i][j].setIcon(null);
               }
               guiGrid[i][j].setBorder (OLD_SLOT_SETTINGS); 
            }
         } 
      }    
   
   /**
    * Main of the program. It starts by initializing Game2048Gui, Game2048 and Game2048Listener object.
    */     
       public static void main(String[] args) 
      {
         Game2048GUI gui = new Game2048GUI();
         Game2048 game = new Game2048(gui);
         new Game2048Listener (game, gui);
      }
   }