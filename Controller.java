public class Controller 
{  
    View view;
    Model model;
    int gameSpeed;
    
    void startGame() 
    {
        view = new View();
        model = new Model(view);
        model.thread = new Thread(); 
        model.addNewFruit(); 
        model.gameActive = true;
        gameSpeed = model.snakeSpeed;
        view.updateScore(0);
        while(model.gameActive) 
        {          
                try {
                    Thread.sleep(gameSpeed);
                } catch (InterruptedException ex) 
                {
                    ex.printStackTrace();
                }
           model.freeTheSnake();

        }
    }   
}
