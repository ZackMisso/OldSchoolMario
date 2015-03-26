/**
 *
 * @author Zackary Misso
 * 
 */
package gameState;
//import tilesAndGraphics.TileMap;
//import tilesAndGraphics.Background;
//import tilesAndGraphics.ManualLevel;
import collectables.Collectable;
import core.GamePanel;
import core.GlobalController;
import enemies.Enemy;
//import enemies.Goomba;
import entities.Block;
import entities.Floor;
import entities.GameEntity;
import entities.Mario;
import gameIO.GameReader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import neuroevolution.MarioAI;
import testtools.CMDTester;
public class Level1State extends GameState{
    private PlayerState playerState; // this will probably be moved later
    //private TileMap tileMap;
    private Mario player;
    private MarioAI ann;
    //private Background background;
    // TODO :: merge these four arraylists
    private ArrayList<Enemy> enemies;
    private ArrayList<Block> blocks;
    private ArrayList<Block> blksOnScreen;
    //private ArrayList<Projectile> projectiles;
    private ArrayList<Collectable> collectables;
    private double xOffset;
    private double yOffset;
    private double totalPast; // THIS SHOULD NOT BE NEEDED
    private int timer; // temporary
    private int timerMax; // also temporary
    
    public Level1State(GameStateManager param){
        setGSM(param);
        blocks=new ArrayList<>();
        enemies=new ArrayList<>();
        //projectiles=new ArrayList<>();
        blksOnScreen=new ArrayList<>();
        collectables=new ArrayList<>();
        init();
    }
    
    public void init(){
        timer=0;
        //tileMap=ManualLevel.createMap();
        timerMax=3000;
        //tileMap.setPosition(0,0);
        // load the tile map
        //player=new Mario(tileMap);
        player=new Mario();
        player.setReference(this);
        player.setPosition(100,100);
        totalPast=100;
        xOffset=0;
        yOffset=0;
        if(GlobalController.testInput){
            readFromFile();
        }
        else{
            initBlocks();
            initEnemies();
        }
    }
    
    private void readFromFile(){
        new GameReader(this);
    }

    public void replaceAI(MarioAI param){
        ann=param;
        player.setANN(param);
    }
    
    private void initBlocks(){
        blocks.clear();
        blocks.add(new Block(180,120,16,16));
        blocks.add(new Block(50,120,16,16));
        blocks.add(new Block(88,160,16,16));
        blocks.add(new Block(88+16,160,16,16));
        blocks.add(new Block(88+32,160,16,16));
        int y=208;
        for(int i=0;i<31;i++){
            blocks.add(new Floor(i*16,y,16,16));
            blocks.add(new Floor(i*16,y+16,16,16));
        }
        int x=34*16;
        for(int i=0;i<16;i++){
            blocks.add(new Floor(i*16+x,y,16,16));
            blocks.add(new Floor(i*16+x,y+16,16,16));
        }
        x+=(16+7)*16;
        for(int i=0;i<38;i++){
            blocks.add(new Floor(i*16+x,y,16,16));
            blocks.add(new Floor(i*16+x,y+16,16,16));
        }
        x+=(38+4)*16;
        for(int i=0;i<32;i++){
            blocks.add(new Floor(i*16+x,y,16,16));
            blocks.add(new Floor(i*16+x,y+16,16,16));
        }
        x+=(32+4)*16;
        for(int i=0;i<20;i++){
            blocks.add(new Floor(i*16+x,y,16,16));
            blocks.add(new Floor(i*16+x,y+16,16,16));
        }
        x=39*16;
        for(int i=0;i<4;i++){
            blocks.add(new Block(i*16+x,208-4*16,16,16));
        }
        x+=4*16;
        for(int i=0;i<8;i++){
            blocks.add(new Block(i*16+x,208-7*16,16,16));
        }
    }
    
    // this is only used for testing
    private void initEnemies(){
//        System.out.println("Initializing Enemies");
        // Temporarily commenting out
        //Goomba goom=new Goomba(240.0,100.0);
        //goom.addEnemyToList(this);
        //enemies.add(new Goomba(240.0,100.0));
    }
    
    public ArrayList<GameEntity> getAllEntities(){
        ArrayList<GameEntity> entities=new ArrayList<>();
        for(int i=0;i<enemies.size();i++)
            entities.add(enemies.get(i));
        for(int i=0;i<blocks.size();i++)
            entities.add(blocks.get(i));
        // TODO :: MAKE COLLECTABLE CLASS ABSTRACT
        //for(int i=0;i<collectables.size();i++)
        //    entities.add(collectables.get(i));
        return entities;
    }
    
    public void update(){
        updateActual();
    }
    
    public ArrayList<Block> getBlocksOnScreen(){
        if(!blksOnScreen.isEmpty())
            return blksOnScreen;
        for(int i=0;i<blocks.size();i++)
            if(blocks.get(i).isOnScreen())
                blksOnScreen.add(blocks.get(i));
        if(blksOnScreen.isEmpty())
            System.out.println("GETBLOCKSONSCREENNOTWORKING!!!");
        return blksOnScreen;
    }
    
    public void updateActual(){
        timer++;
        if(player==null)
            System.out.println("Player is null! :: Level1State");
        player.update(blocks,this);
        player.finalizeMovementNew(this);
        double dx=player.getDx();
        for(int i=0;i<blocks.size();i++){
            // This line will not be needed once tile physics works
            if(blocks.get(i)==null)
                continue;
            blocks.get(i).setXpos(blocks.get(i).getStartX()-xOffset);
            if(blocks.get(i).isOnScreen())
                blocks.get(i).updateC();
        }
        for(int i=0;i<enemies.size();i++){
            // Something will need to be added here
            enemies.get(i).setXpos(enemies.get(i).getXpos()-dx);
            if(enemies.get(i).isOnScreen())
                enemies.get(i).update(blocks);
        }
        player.checkCollisionsWithEnemies(enemies,this);
        // temporarily commented out for debugging
        if(xOffset>3176) // flagpole
            end();
        if(timer>=timerMax&&!GlobalController.gameRunning)
            end();
        resetBlocksOnScreen();
    }
    
    private void resetBlocksOnScreen(){
        blksOnScreen.clear();
    }

    // Depreciated :: Zack
    //public void makeTileMap(TileMap param){
    //    System.out.println("Making the TileMap");
    //    System.out.println(param.getMap().length);
    //    tileMap=param;
    //    player.setTileMap(param);
    //}
    
    // THIS METHOD IS USED FOR EVOLVING AGENTS
    public void makeTimer(int generation){
        timerMax=3000;
    }
    
    public void draw(Graphics2D g){
        drawLols(g);
    }

    public void drawLols(Graphics2D g){
        g.setColor(Color.BLUE);
        g.fillRect(0,0,GamePanel.WIDTH*GamePanel.SCALE,GamePanel.HEIGHT*GamePanel.SCALE);
        for(int i=0;i<blocks.size();i++)
            if(blocks.get(i).getOnScreen())
                blocks.get(i).draw(g,this);
        for(int i=0;i<enemies.size();i++)
            if(enemies.get(i).getOnScreen())
                enemies.get(i).draw(g);
        player.draw(g);
    }

    public void end(){
        if(GlobalController.aiRun&&ann!=null){
            System.out.println("This AI's score was "+xOffset);
            new CMDTester(ann.getNet());
        }
        if(GlobalController.evolving&&ann!=null){
            if(ann==null)
                System.out.println("ERROR 1");
            else if(ann.getNet()==null)
                System.out.println("ERROR 2");
            ann.getNet().setFitness(xOffset);
            reset();
            GlobalController.running=false;
        }else{
            System.out.println("Fitness: " + xOffset + " :: Level1State 297");
            GlobalController.running=false;
        }
    }

    public void reset(){
        player=null;
        enemies.clear();
        blocks.clear();
        collectables.clear();
        player=new Mario();
        player.setReference(this);
        player.setPosition(100,100);
        totalPast=100;
        xOffset=0;
        yOffset=0;
        timer=0;
        init();
    }
    
    public void keyPressed(int k){
        if(k==KeyEvent.VK_SPACE)
            if(GlobalController.aiRun)
                end();
        if(player!=null)
            player.keyPressed(k);
    }
    
    public void keyReleased(int k){
        if(player!=null)
            player.keyReleased(k);
    }
    
    // getter methods
    public PlayerState getPlayerState(){return playerState;}
    //public ArrayList<Projectile> getProjectiles(){return projectiles;} // implement projectiles later
    public ArrayList<Block> getBlocks(){return blocks;}
    public ArrayList<Enemy> getEnemies(){return enemies;}
    public ArrayList<Collectable> getCollectables(){return collectables;}
    //public TileMap getTileMap(){return tileMap;}
    public Mario getPlayer(){return player;}
    //public Background getBackground(){return background;}
    public double getXOffset(){return xOffset;}
    public double getYOffset(){return yOffset;}
    public double getTotalPast(){return totalPast;}
    
    // setter methods
    //public void setTileMap(TileMap param){tileMap=param;}
    public void setANN(MarioAI param){ann=param;}
    public void setPlayer(Mario param){player=param;}
    //public void setBackground(Background param){background=param;}
    public void setXOffset(double param){xOffset=param;}
    public void setYOffset(double param){yOffset=param;}
    public void setTotalPast(double param){totalPast=param;}
}