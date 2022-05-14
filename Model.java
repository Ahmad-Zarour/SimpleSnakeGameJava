import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.Random;

public class Model 
{
    Thread thread;
    View view ;
    int snakeSpeed = 1500; //speed per milliseconds.
    Random random = new Random();
    int fruitCounter = 0;
    int snakeHeadAxisX,snakeTailAxisX, snakeHeadAxisY, snakeTailAxisY;
    ArrayList<JLabel> snakeBody = new ArrayList<>(); 
    String direction ;
    boolean gameActive = false;

    public Model(View v) 
    {
        //insert the snake to the game field :
        view = v;
        view.generateSnake(4, 4); 
        //first snake that will show in the game field , only one part , the head
        snakeHeadAxisX = snakeTailAxisX = snakeHeadAxisY = snakeTailAxisY= 4;      
    }

    // Makes 10 apples at random location (not duplicate and not on snake).
    void addNewFruit() 
    { 
        for (int i = 0; i < 5; i++) {
            int row = (int) random.nextInt(11 - 0 + 1) + 0;
            int col = (int) random.nextInt(11 - 0 + 1) + 0;

            while (view.checkPlaceIfFruit(row, col) || view.checPlaceIfSnakeBody(row, col)) 
            {
                row = (int) random.nextInt(11 - 0 + 1) + 0;
                col = (int) random.nextInt(11 - 0 + 1) + 0;
            }
            view.insertFruit(row,col);
        }
    }
    // insert the snake to the game
    void freeTheSnake() 
    {
        snakeBody = view.snakeBody; 
        direction = view.direction;
        // IF he snake hitt the the border or it's body
        if (view.checkNextIfBorder(snakeHeadAxisX, snakeHeadAxisY,direction)
            ||view.checkNextIfSnakeBody(snakeHeadAxisX, snakeHeadAxisY,direction) ) 
        {
            gameActive = false;
            view.showGameStatus(false);
        }  
        
        //If the snake moves in-game field correctly
        else
         { 
             // Snake get fruit to eat and grow
            if (view.reachToFruit(snakeHeadAxisX, snakeHeadAxisY, direction)) 
            letSnakeEatFruit(direction);

            //Empty place , snake just moving on the game field
            else  
            moveSnakeBody(direction);    
        }
    }

    // method to make the snake eat the fruit and grow
    void letSnakeEatFruit(String direction)
    {
        switch(direction)
        {
            case "UP":
            addFruit();
            view.eraseFruit(snakeHeadAxisX-1, snakeHeadAxisY);
            view.generateSnake(snakeHeadAxisX-1, snakeHeadAxisY);
            fruitCounter++;
            snakeHeadAxisX--;
            break;
            case "DOWN":
            view.eraseFruit(snakeHeadAxisX+1, snakeHeadAxisY);
            addFruit();
            fruitCounter++;
            view.generateSnake(snakeHeadAxisX+1, snakeHeadAxisY);
            snakeHeadAxisX++;
            break;
            case "LEFT":
            view.eraseFruit(snakeHeadAxisX, snakeHeadAxisY-1);
            addFruit();
            fruitCounter++;
            view.generateSnake(snakeHeadAxisX, snakeHeadAxisY-1);
            snakeHeadAxisY--;
            break;
            case "RIGHT":
            view.eraseFruit(snakeHeadAxisX, snakeHeadAxisY+1);
            addFruit();
            fruitCounter++;
            view.generateSnake(snakeHeadAxisX, snakeHeadAxisY+1);
            snakeHeadAxisY++;
            break;
            default:
            System.out.println("Something went wrong, game will stop");
            gameActive = false;
            break;
        }
        view.updateScore(fruitCounter);
    }

// method to move the snake from position to another
 void moveSnakeBody(String direction)
 {
    int[] currentPosition = new int[2];
    // check the direction of the snake
    switch(direction)
        {
            case "UP":
            view.EmptySnakePath(snakeTailAxisX, snakeTailAxisY);
            view.generateSnake(snakeHeadAxisX-1, snakeHeadAxisY);
            snakeHeadAxisX--;
            currentPosition = getSnakeTailPosition(snakeBody.get(snakeBody.size()-1));
            snakeTailAxisX = currentPosition[0];
            snakeTailAxisY = currentPosition[1];
            break;
            case "DOWN":
            view.generateSnake(snakeHeadAxisX+1, snakeHeadAxisY);
            view.EmptySnakePath(snakeTailAxisX, snakeTailAxisY);
            snakeHeadAxisX++;
            currentPosition = getSnakeTailPosition(snakeBody.get(snakeBody.size()-1));
            snakeTailAxisX = currentPosition[0];
            snakeTailAxisY = currentPosition[1];
            break;
            case "LEFT":
            view.generateSnake(snakeHeadAxisX, snakeHeadAxisY-1);
            view.EmptySnakePath(snakeTailAxisX, snakeTailAxisY);
            snakeHeadAxisY--;
            currentPosition = getSnakeTailPosition(snakeBody.get(snakeBody.size()-1));
            snakeTailAxisX = currentPosition[0];
            snakeTailAxisY = currentPosition[1];
            break;
            case "RIGHT":
            view.generateSnake(snakeHeadAxisX, snakeHeadAxisY+1);
            view.EmptySnakePath(snakeTailAxisX, snakeTailAxisY); // removes the last snaketile
            snakeHeadAxisY++;
            currentPosition = getSnakeTailPosition(snakeBody.get(snakeBody.size()-1)); // Finds last snaketile, this is now the new tail:
            snakeTailAxisX = currentPosition[0];
            snakeTailAxisY = currentPosition[1];
            break;
            default:
            System.out.println("Something went wrong, game will stop");
            gameActive = false;
            break;
        } 
 }

// Get the end of the snake , the tail
 int[] getSnakeTailPosition(JLabel tailPosition) 
    { 
    for (int asisX = 0; asisX < 12; asisX++) 
        for (int axisY = 0; axisY < 12; axisY++) 
            if (tailPosition == view.playingField[asisX][axisY]) 
                return new int[] {asisX,axisY};
                //in case of position not found
                System.out.println("Something went wrong, game will stop");
                gameActive = false;
      return new int[] {0,0};
    }

    // generate fruit to the game field
    void addFruit() 
    {      
      int asisX, asisY;
        do
        { 
            asisX = (int) random.nextInt(11 - 0 + 1) + 0;
            asisY = (int) random.nextInt(11 - 0 + 1) + 0;
        }
        while (view.checPlaceIfSnakeBody(asisX, asisY) || view.checkPlaceIfFruit(asisX, asisY)); 
        view.insertFruit(asisX, asisY);
    }

}