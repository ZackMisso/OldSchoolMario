/**
 *
 * @author Zackary Misso
 * 
 */
package entities;
import collectables.Collectable;
import core.GlobalController;
import enemies.Enemy;
import gameState.Level1State;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import neuroevolution.MarioAI;
import neuroevolution.connections.Connection;
import neuroevolution.networks.HVplusPit;
import neuroevolution.networks.HorizontalAndVerticalNetworkHardcoded;
import neuroevolution.networks.NeuralNetwork;
import neuroevolution.neurons.Neuron;
import neuroevolution.neurons.hardcoded.DistanceThresholdChecker;
import neuroevolution.neurons.hardcoded.PitChecker;
import projectiles.Projectile;
import testtools.CMDTester;
import tilesAndGraphics.ImageCache;
import tilesAndGraphics.TileMap;
public class Mario extends GameEntity{
    private Level1State reference; // this should be removed later
    private MarioAI ann; // this should be moved
    //private int health;
    private boolean jumped = false;
    private boolean spaceup = true;
    private boolean facingright = true;
    private boolean dead;
    private boolean onRight;
    //private boolean flinching;
    //private long flinchTime;
    private double rectx;
    private double recty;
    private int rectw;
    private int recth;
    private double rectx2;
    private double recty2;
    private int rectw2;
    private int recth2;
    private int num; // what is this?

    public Mario(){
        this(null);
        onRight=false;
    }
    
    public Mario(TileMap tm){
        ann=null;
        //super(tm);
        setWidth(16);
        setHeight(32);
        setCWidth(16);
        setCHeight(32);
        //setMoveSpeed(.3);
        //setMaxSpeed(1.6);
        //setStopSpeed(.4);
        setFallSpeed(.15);
        setMaxFallSpeed(4.0);
        setJumpStart(-4.8);
        setStopJumpSpeed(.3);
        //setFacingRight(true);
        //health=1;
        setImage(ImageCache.mario);
        setOnScreen(true);
        setFalling(true);
        //setDy(1);
        //setDrawC(true);
    }
    
    public void update(ArrayList<Block> list,Level1State state){
        outer: if((GlobalController.aiRun||GlobalController.evolving)){
            {
                if(ann==null){
                    System.out.println("ANN is null :: Mario 68");
                    break outer;
                }
                else if (ann.isReadytoPropogate()){
                    ann.propogate(reference);
                    //System.out.println(""+ann.movesRight()+ann.movesLeft()+ann.jumps());
                    //try{
                        setRight(ann.movesRight());
                    //}
                    //catch(IndexOutOfBoundsException e){
                    //    System.out.println("There was an IndexOutOfBounds Exception :: Mario");
                    //    new CMDTester(ann.getNet());
                    //}
                    setLeft(ann.movesLeft());
                    setJumping(ann.jumps());
                    spaceup = !ann.jumps();
                    //if(!ann.jumps())
                    //    System.out.println("Network not jumping.");
                }
            }
        }
        double tempppp=getXpos();
        // update position
        //getNextPosition();
        //checkTileMapCollision();
        //setPosition(getXTemp(),getYTemp());
        if(getFalling())
            setDy(getDy()+.11);
        if(getRight()){
            setDx(1);
        }
        else if(getLeft()){
            setDx(-1);
        }
        //else if(getUp()){
        //    setDy(-1);
        //}
        //else if(getDown()){
        //    setDy(1);
        //}
        else{
            setDx(0);
            //setDy(0);
        }
        //setXTemp(getXpos()+getDx());
        //System.out.println(getDx());
        
        setXTemp(100+state.getXOffset()+getDx());
        //System.out.print(getXTemp()+" ");
        setYTemp(getYpos()+getDy());
        //if(getTileMap()==null)
            //System.out.println("Tile Map NULL :: Mario");
        //if(GlobalController.tilePhysics)
        //    checkMovementOnSurroundings();
        //else
        checkCollisionsWithBlocks(list,state);
        //System.out.println(getXTemp());
        //System.out.println(getDx());
        if(getJumping()&&!getFalling()&&!jumped){
            setDy(getJumpStart());
            jumped = true;
            setJumping(false);
            setFalling(true);
            setYTemp(getYpos()+getDy());
        }
        //setXTemp(getXpos()+getDx());
        //setYTemp(getYpos()+getDy());
        if(GlobalController.aiRun||GlobalController.evolving){
            //ann.propagate(reference);
            //System.out.println("THIS SHOULD NOT BE RUNNING");
            setRight(false);
            setLeft(false);
            setJumping(false);
        }
    }
    
    // THIS IS (WILL BE) THE DYSFUNCTIONAL PHYSICS
    public void updateLols(ArrayList<Block> list,Level1State state){
        if(ann!=null&&(GlobalController.aiRun||GlobalController.evolving)&&ann.isReadytoPropogate()){
            {
                ann.propogate(reference);
                //System.out.println(""+ann.movesRight()+ann.movesLeft()+ann.jumps());
                //CMDTester debug = new CMDTester(ann.getNet());
                setRight(ann.movesRight());
                setLeft(ann.movesLeft());
                setJumping(ann.jumps());
            }
        }
        if(getFalling())
            setDy(getDy()+.11);
        if(getRight()){
            setDx(1);
        }
        else if(getLeft()){
            setDx(-1);
        }
        else{
            setDx(0);
        }
        setXTemp(100+state.getXOffset()+getDx());
        setYTemp(getYpos()+getDy());
        //if(GlobalController.tilePhysics){
        //    if(GlobalController.lolsPhysics){
        //        System.out.println("Checking Lols :: Mario");
        //        lolsCheckMovementOnSurroundings();
        //    }
        //    else
        //        checkMovementOnSurroundings();
        //}else
        checkCollisionsWithBlocks(list,state);
        if(getJumping()&&!getFalling()){
            setDy(getJumpStart());
            setJumping(false);
            setFalling(true);
            setYTemp(getYpos()+getDy());
        }
        if(GlobalController.aiRun||GlobalController.evolving){
            setRight(false);
            setLeft(false);
            setJumping(false);
        }
    }
    
    public void finalizeMovement(Level1State state){
        //double changeX=getXTemp()-getXpos();
        //setXpos(getXTemp());
        double temmppp=state.getXOffset();
        state.setXOffset(getXTemp()-100);
        if(state.getXOffset()!=temmppp)
            onRight=false;
        setYpos(getYTemp());
        //System.out.println(state.getXOffset());
        if(getYpos()>330){
            //System.exit(0);
            state.end();
        }
    }

    public void finalizeMovementNew(Level1State state){
        //state.setXOffset(state.getXOffset()+getXTemp()-100);
        state.setXOffset(state.getXOffset()+getDx());
        // TODO :: 100 value will need to be changed to implement camera
        setYpos(getYTemp());
        if(getYpos()>330)
            reference.end();  // TODO :: CHANGE THIS
    }

    // how the player jumps off things after landing on top of them
    public void rebound(){
        // implement
    }
    
    // This will be kept even when tile physics is implemented
    public void checkCollisionsWithEnemies(ArrayList<Enemy> list,Level1State state){
        for(int i=0;i<list.size();i++){
            double w=.5*(list.get(i).getCWidth()+getWidth());
            double h=.5*(list.get(i).getCHeight()+getHeight());
            double dx=list.get(i).getCCenterX()-getCenterX();
            double dy=list.get(i).getCCenterY()-getCenterY();
            if(Math.abs(dx)<=w&&Math.abs(dy)<=h){
                //System.out.println("COLLISION :: MARIO");
                //System.out.println("Enemies Size :: "+list.size()+" :: Mario");
                boolean hack=false;
                double wy=w*dy;
                double hx=h*dx;
                if(wy>hx){
                    if(wy>-hx){
                        // Collision is on the top
                        //System.out.println("Hit on Top");
                        if(list.get(i).getKilledByTop()){
                            hack=true;
                            if(list.get(i).hit(state.getPlayerState(),this)){
                                //list.remove(i--);
                                // should this do anything?
                                //System.out.println("Wat :: Mario");
                            }
                        }
                        else{
                            //System.out.println("GOOMBA IS INVINCIBLE :: Mario");
                            hit();
                        }
                    }else{
                        // Collision is from the left
                        //System.out.println(hack);
                        if(!hack){
                            //System.out.println("Hit From Left :: Mario");
                            hit();
                        }
                    }
                }else{
                    if(wy>-hx){
                        // Collision is on the right
                        if(!hack){
                            //System.out.println("Hit From Right :: Mario");
                            hit();
                        }
                    }else{
                        // Collision is on the bottom
                        if(!hack){
                            hit();
                        }
                    }
                }
            }
        }
    }

    public void checkCollisionsWithCollectables(ArrayList<Collectable> list){
        // implement
    }

    public void checkCollisionsWithProjectiles(ArrayList<Projectile> list){
        // implement
    }
    
    // This will soon not be needed
    public void checkCollisionsWithBlocks(ArrayList<Block> list,Level1State state){
        setFalling(true);
        //System.out.print(getDx()+" ");
        if(list == null) //safety check
            return;
        double tempDx=getDx();
        double tempX=getXTemp();
        for(int i=0;i<list.size();i++){
            Block b = list.get(i);
            if(b==null)
                continue;
            boolean rgt=false;
            boolean lft=false;
            double w=.5*(b.getCWidth()+getWidth());
            double h=.5*(b.getCHeight()+getHeight());
            double dx=b.getCCenterX()-getCenterX();
            double dy=b.getCCenterY()-getCenterY();
            if(Math.abs(dx)<=w&&Math.abs(dy)<=h){
                double wy=w*dy;
                double hx=h*dx;
                if(wy>hx){
                    if(wy>-hx){
                        // Collision from the top
                        if(getDy()>0)
                            setDy(0);
                        setYTemp(b.getYpos()-getHeight());
                        setFalling(false);
                        if(spaceup)
                            jumped = false;
                        //System.out.println("TOP");
                    }else{
                        // Collision from the left
                        if(getDx()<0){
                            setDx(0);
                            //setXTemp(list.get(i).getXpos()+getWidth()+1);
                            //System.out.println("BROKEN2");
                        }
                        rectx2=b.getXpos();
                        recty2=b.getYpos();
                        rectw2=b.getWidth();
                        recth2=b.getHeight();
                        //setXTemp(list.get(i).getXpos()+getWidth()+1);
                        if(distanceBetweenCenters(b)>getMaxDistance(b)-2){
                            //setXTemp(list.get(i).getXpos()+getWidth()+1);
                            //System.out.print(distanceBetweenCenters(list.get(i))+" ");
                            //System.out.println(getMaxDistance(list.get(i)));
                            setDx(tempDx);
                            setXTemp(tempX);
                        }else{
                            setXTemp(b.getXpos()+getWidth());
                            //System.out.print(distanceBetweenCenters(b)+" ");
                            //System.out.println(getMaxDistance(b));
                        }
                        lft=true;
                        //setXTemp(list.get(i).getXpos()+getWidth()+1);
                        //System.out.println("LEFT");
                    }
                }else{
                    if(wy>-hx){
                        // Collision on the right
                        if(getDx()>0){
                            //System.out.println("BROKEN");
                            //setXTemp(list.get(i).getXpos()-getWidth()-1);
                            onRight=true;
                            setDx(0);
                        }
                        //System.out.println("MARIO :: "+getXpos()+" "+getYpos());
                        //System.out.println("BLOCK :: "+list.get(i).getXpos()+" "+list.get(i).getYpos());
                        rectx=list.get(i).getXpos();
                        recty=list.get(i).getYpos();
                        rectw=list.get(i).getWidth();
                        recth=list.get(i).getHeight();
                        setXTemp(list.get(i).getXpos()-getWidth()-1);
                        if(distanceBetweenCenters(list.get(i))>getMaxDistance(list.get(i))){
                            //setXTemp(list.get(i).getXpos()+getWidth()+1);
                            //System.out.print(distanceBetweenCenters(list.get(i))+" ");
                            //System.out.println(getMaxDistance(list.get(i)));
                            //System.out.println("YEA");
                            onRight=false;
                            setDx(tempDx);
                            setXTemp(tempX);
                        }
                        rgt=true;
                        //System.out.println("RIGHT");
                    }else{
                        // Collision on the bottom
                        boolean num=xLengthDistance(list.get(i))<getMaxDistanceByX(list.get(i))-2;
                        if(getDy()<0&&num){
                            
                            setDy(0);
                        }
                        if(num)
                            setYTemp(list.get(i).getYpos()+list.get(i).getHeight()+1);
                        
                        //System.out.println("BOT");
                    }
                }
                if(rgt&&lft){
                    setDx(tempDx);
                    setXTemp(tempX);
                    //System.out.println("ADFASDFADF");
                }
            }
        }
        //System.out.print(getDx());
    }

    public void hit(){
        //System.out.println("The Player Was Hit");
        die();
    }
    
    // what happens when the player dies
    public void die(){
        // implement
        reference.end();
    }
    
    public void keyPressed(int k){
        if(GlobalController.gameRunning){
            if(k==KeyEvent.VK_D)setRight(true);
            if(k==KeyEvent.VK_A)setLeft(true);
            if(k==KeyEvent.VK_SPACE){
                setJumping(true);
                spaceup = false;
            }
        }
    }
    
    public void keyReleased(int k){
        if(GlobalController.gameRunning){
            if(k==KeyEvent.VK_D)setRight(false);
            if(k==KeyEvent.VK_A)setLeft(false);
            if(k==KeyEvent.VK_SPACE)
            {
                setJumping(false);
                spaceup = true;
            }
        }
    }
    
    public void draw(Graphics2D g){
        //new CMDTester(ann.getNet());
        //g.drawImage(getImage(),(int)getXpos(),(int)getYpos(),null);
        super.draw(g);
        g.setColor(Color.magenta);
        g.drawRect((int)getXpos(),(int)getYpos(),getWidth(),getHeight());
        //if(ann.jumps()){
        //    System.out.println("The AI is jumping.");
        //}
        //else
        //    System.out.println("The AI is not jumping.");
        // the code below is for debugging purposes
        if(ann!=null){
            for(Neuron n : ann.getNet().getNeurons()){
                if(n instanceof DistanceThresholdChecker){
                    double xoffset = ((DistanceThresholdChecker)n).evalHorizontal();
                    double yoffset = ((DistanceThresholdChecker)n).evalVertical();
                    //System.out.println(xoffset + " " + yoffset + " :: Mario 428");
                    g.setColor(Color.WHITE);
                    g.drawRect((int)(getXpos()+xoffset),(int)(getYpos()),rectw,recth);
                    g.setColor(Color.BLUE);
                    g.drawRect((int)(getXpos()),(int)(getYpos()+yoffset),rectw,recth);
                }
                else if(n instanceof PitChecker){
                    g.setColor(Color.GREEN);
                    //System.out.println(n.evaluate());
                    g.drawRect((int)(getXpos()+n.evaluate()),(int)(getYpos()+getHeight()),rectw,recth);
                }
            }
        }
        g.setColor(Color.BLACK);
        g.drawRect((int)rectx,(int)recty,rectw,recth);
        g.setColor(Color.YELLOW);
        g.drawRect((int)rectx2,(int)recty2,rectw2,recth2);
        //g.drawRect((int)getXpos(),(int)getYpos(),getWidth(),getHeight());
    }
    
    // getter methods
    //public int getHealth(){return health;}
    //public boolean getDead(){return dead;}
    //public boolean getFlinching(){return flinching;}
    //public long getFlinchTime(){return flinchTime;}
    
    // setter methods
    //public void setHealth(int param){health=param;}
    //public void setDead(boolean param){dead=param;}
    //public void setFlinching(boolean param){flinching=param;}
    //public void setFlinchTime(long param){flinchTime=param;}
    public void setANN(MarioAI param){ann=param;}
    public void setReference(Level1State param){reference=param;}
}