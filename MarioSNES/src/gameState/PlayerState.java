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
    
    // getter methods
    public long getScore(){return score;}
    public int getCoins(){return coins;}
    public int getLives(){return lives;}
    
    // setter methods
    public void setScore(long param){score=param;}
    public void setCoins(int param){coins=param;}
    public void setLives(int param){lives=param;}
}