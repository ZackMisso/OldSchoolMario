/**
 *
 * @author Zackary Misso
 * 
 */
package gameState;
public class PlayerState {
    private long score;
    private int coins;
    private int lives;
    
    public PlayerState(){
        lives=1;
        score=0;
        coins=0;
    }

    public void addPoints(int num){
        score+=num;
    }
    
    // getter methods
    public int getCoins(){return coins;}
    public int getLives(){return lives;}
    
    // setter methods
    public void setCoins(int param){coins=param;}
    public void setLives(int param){lives=param;}
}