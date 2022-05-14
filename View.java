import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


public class View implements ActionListener
{
    // Game main frame 
    private JFrame mainWindowFrame;
    // buttons in the game panel
    private JButton btnUp= new JButton("\u2191");
    private JButton btnDown= new JButton("\u2193");
    private JButton btnRight= new JButton("\u2192");
    private JButton btnLeft= new JButton("\u2190");
    private JButton btnExit= new JButton("Exit");
    
   
    // label to count the snake length
    private JLabel score ,gameStatus;
   // private JLabel gameStatus;
    
    String direction;

    //https://www.youtube.com/watch?v=4PfDdJ8GFHI
    private JPanel infoBarPanel, gameFieldPanel,controlPanel,controlPanelLine1,controlPanelLine2;
    // the snake body
    ArrayList<JLabel> snakeBody = new ArrayList<>();
    // the area where the snake moves
    public JLabel[][] playingField = new JLabel[12][12];
    public static final Color VERY_LIGHT_BLUE = new Color(51,204,255);

    // constructor
    public View() 
    {   
        direction = "DOWN";
        // set the size, title, action of the main window frame
        //https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
        mainWindowFrame = new JFrame("Wellcome to Snake Game");
        mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindowFrame.setPreferredSize(new Dimension(700, 750));
        

        // infoBarPanel to show the score and game status// on the top of the window
        infoBarPanel = new JPanel(); 
        infoBarPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        infoBarPanel.setBackground(Color.lightGray);
        // set the place for the score counter and game status
        score = new JLabel("Your Score: ");
        gameStatus = new JLabel("Game is running   ");
        infoBarPanel.add(gameStatus);
        infoBarPanel.add(score); 
        
        infoBarPanel.add(btnExit,BorderLayout.CENTER);
        mainWindowFrame.add(infoBarPanel,BorderLayout.NORTH); 


        // panel for the game field , where the snake moves
        gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(12,12));
        // dividing the snake playing area as block using JLabel:
        for (int axisX = 0; axisX < playingField.length; axisX++)
            for (int axisY = 0; axisY < playingField[0].length; axisY++) 
            {
                JLabel block = new JLabel("");
                playingField[axisX][axisY] = block;
                block.setBorder(BorderFactory.createSoftBevelBorder(1,Color.MAGENTA,VERY_LIGHT_BLUE));
                block.setOpaque(true);
                gameFieldPanel.add(block);
            }
            mainWindowFrame.add(gameFieldPanel, BorderLayout.CENTER);
            
        // set the size of control buttons
        btnUp.setPreferredSize(new Dimension(60, 40));
        btnDown.setPreferredSize(new Dimension(60, 40));
        btnRight.setPreferredSize(new Dimension(60, 40));
        btnLeft.setPreferredSize(new Dimension(60, 40));
        //Add listener to the control buttons
        btnUp.addActionListener(this);
        btnDown.addActionListener(this);
        btnRight.addActionListener(this);
        btnLeft.addActionListener(this);
        btnExit.addActionListener(this);

        // control panel where buttons placed //  buttom part 1 and 2
        // first control panel for up button
        controlPanelLine1 = new JPanel(); 
        controlPanelLine1.add(btnUp);
        // second control panel for left, right,down buttons
        controlPanelLine2 = new JPanel(); 
        controlPanelLine2.add(btnLeft);
        controlPanelLine2.add(btnDown);
        controlPanelLine2.add(btnRight);
        // merage controlPanelLine1 and controlPanelLine2 in one panel
        controlPanel = new JPanel(); 
        controlPanel.setLayout( new BorderLayout());
        controlPanel.add(controlPanelLine1, BorderLayout.NORTH);
        controlPanel.add(controlPanelLine2, BorderLayout.SOUTH);  
        mainWindowFrame.add(controlPanel,BorderLayout.SOUTH);  
        mainWindowFrame.pack();
        mainWindowFrame.setVisible(true); 
            
        // set focus on main frame control game with arrow keys in the keyboard
        mainWindowFrame.setFocusable(true);
        //focusable flase for all other buttons 
        btnUp.setFocusable(false);
        btnDown.setFocusable(false);
        btnRight.setFocusable(false);
        btnLeft.setFocusable(false);
        btnExit.setFocusable(false);
      
        // Get the direction from the arrow keys on the keyboard
        mainWindowFrame.addKeyListener(new KeyAdapter() 
        {
            public void keyPressed(KeyEvent e) 
            {     
              int keyCode = e.getKeyCode();
              if (keyCode == KeyEvent.VK_UP && direction != "DOWN") 
                direction ="UP";
              else if (keyCode == KeyEvent.VK_DOWN&& direction != "UP") 
                direction ="DOWN";
              else if (keyCode == KeyEvent.VK_LEFT && direction != "RIGHT") 
                direction = "LEFT";
              else if (keyCode == KeyEvent.VK_RIGHT && direction != "LEFT") 
                direction = "RIGHT";   
            }
          }); 
    }

    // Updates snake-length for score "update the text labl":
    void updateScore(int counter) 
    { 
        score.setText("Your Score is : " + counter);
    }

    // show game over on the infobar 
    void showGameStatus(boolean gameValid) 
    { 
        gameStatus.setText("Game Over :( ");
    }

    /// inster the snake to the game
    void generateSnake(int axisX, int axisY) 
    { 
        playingField[axisX][axisY].setBackground(Color.yellow);
        playingField[axisX][axisY].setText("¤");
        playingField[axisX][axisY].setHorizontalAlignment(JLabel.CENTER);
        snakeBody.add(0, playingField[axisX][axisY]);
    } 
    // empty the place where the snake was
    void EmptySnakePath(int axisX, int axisY) 
    { 
        if (playingField[axisX][axisY].getText().equals("¤")) 
        {
            playingField[axisX][axisY].setBackground(null);
            playingField[axisX][axisY].setText("");
            snakeBody.remove(playingField[axisX][axisY]);
        } 
    }

     // check if next block is a fruit
     boolean checkPlaceIfFruit(int axisX, int axisY) 
     { 
         return playingField[axisX][axisY].getText().equals("@");
     }

    // check if next block is a snake
    boolean checPlaceIfSnakeBody(int axisX, int axisY) 
    { 
        return playingField[axisX][axisY].getText().equals("¤");
    }

    //metod to chec if the next direction is a place for the snake body
    boolean checkNextIfSnakeBody(int axisX, int axisY,String nextDirection) 
    { 
        switch (nextDirection) 
        {
            case "UP":
            return playingField[axisX-1][axisY].getText().equals("¤");
            case "DOWN":
            return playingField[axisX+1][axisY].getText().equals("¤");
            case "LEFT":
            return playingField[axisX][axisY-1].getText().equals("¤");
            case "RIGHT":
            return playingField[axisX][axisY+1].getText().equals("¤");
            default:
            return false;
        }
    }

    //Check the snake movement, if it reaches the frame border 
    boolean checkNextIfBorder(int axisX, int axisY,String nextDirection) 
    { 
        // snake hits by wall
        if (axisX == playingField.length-1 && nextDirection == "DOWN" 
        || axisY == playingField[0].length-1 && nextDirection == "RIGHT"
        || axisX == 0 && nextDirection == "UP"
        || axisY == 0 && nextDirection == "LEFT")   
        {
            showGameStatus(false);
            return true;
        } 
        return false;
    }

    
    //Check the snake movement, if it reaches the fruit
    boolean reachToFruit(int axisX, int axisY, String nextDirection) 
    {  
          
        switch (nextDirection) 
        {
            case "UP":
            System.out.println("from Fruct : axis x = "+axisX +" , axisY = "+axisY);

            return playingField[axisX-1][axisY].getText().equals("@");

            case "DOWN":
            System.out.println("from Fruct : axis x = "+axisX +" , axisY = "+axisY);

             return playingField[axisX+1][axisY].getText().equals("@");
            case "LEFT":
            System.out.println("from Fruct : axis x = "+axisX +" , axisY = "+axisY);

             return playingField[axisX][axisY-1].getText().equals("@");
            case "RIGHT":
            System.out.println("from Fruct : axis x = "+axisX +" , axisY = "+axisY);

            return playingField[axisX][axisY+1].getText().equals("@");
            default:
            return false;
        }
    }

    //Insert fruit in the game area
    void insertFruit(int axisX, int axisY) 
    { 
        playingField[axisX][axisY].setText("@");
        playingField[axisX][axisY].setHorizontalAlignment(JLabel.CENTER);
        playingField[axisX][axisY].setForeground(Color.RED);
    }

        //Remove fruit from the game area
        void eraseFruit(int axisX, int axisY) 
        { 
            playingField[axisX][axisY].setForeground(null);
            playingField[axisX][axisY].setText("");
        }


        // control the game with arrow buttons on the form
    @Override
    public void actionPerformed(ActionEvent event) 
    {

        if (event.getSource() == btnExit) 
            System.exit(0);
         
        
        else if (event.getSource() == btnUp && direction != "DOWN" ) 
            direction = "UP";
            
        else if (event.getSource() == btnRight && direction != "LEFT") 
            direction = "RIGHT";
        
        else if (event.getSource() == btnLeft && direction != "RIGHT") 
            direction = "LEFT";
         
        else if (event.getSource() == btnDown && direction != "UP") 
             direction = "DOWN"; 
        
    }
 }


