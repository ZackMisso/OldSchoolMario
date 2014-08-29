/**
 *
 * @author Zackary Misso
 * 
 */
package gameIO;
import gameState.Level1State;
import entities.Mario;
import entities.Floor;
import entities.Block;
import entities.BreakableBlock;
import entities.ItemBox;
import entities.Pipe;
import enemies.Goomba;
import enemies.Koopa;
import enemies.PiranhaPlant;
import enemies.FirePiranhaPlant;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
public class GameReader {
	// TODO :: MAKE A BETTER QUIRY SYSTEM
    public GameReader(Level1State state){
    	loadLevel1(state);
    }
    
    // TODO :: make more abstract method later
    private void loadLevel1(Level1State state){
        try{
        	private boolean mario;
        	Scanner scanner=new Scanner(new File("testLevel.txt"));
        	while(scanner.hasNextLine()){
        		String string=scanner.nextLine();
        		if(string.equals("mario")&&!mario){
        			readInMario();
        			mario=true;
        		}
        		if(string.equals("floor"))
        			readInFloor(state,scanner);
        		if(string.equals("block"))
        			readInBlock(state,scanner);
        		if(string.equals("breakableblock"))
        			readInBreakableBlock(state,scanner);
        		if(string.equals("pipe"))
        			readInPipe(state,scanner);
        		if(string.equals("goomba"))
        			readInGoomba(state,scanner);
        		if(string.equals("koopa"))
        			readInKoopa(state,scanner);
        		if(string.equals("itembox"))
        			readInItemBox(state,scanner);
        		if(string.equals("piranhaplant"))
        			readInPiranhaPlant(state,scanner);
        		if(string.equals("firepiranhaplant"))
        			readInFirePiranhaPlant(state,scanner);
        	}
        }catch(IOException e){System.out.println("Could not read file");}
    }

    private void readInMario(Level1State state,Scanner scanner){
    	String string;
        Mario mario=new Mario();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                mario.setXpos(string.nextInt());
            if(string.equals("startY"))
                mario.setYpos(string.nextInt());
        }while(string.equals("End"));
        state.setPlayer(mario);
    }

    private void readInFloor(Level1State state,Scanner scanner){
    	String string;
        Floor floor=new Floor();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                floor.setXpos(string.nextInt());
            if(string.equals("startY"))
                floor.setYpos(string.nextInt());
        }while(string.equals("End"));
        floor.updateC();
        state.getBlocks().add(floor);
    }

    private void readInBlock(Level1State state,Scanner scanner){
    	String string;
        Block block=new Block();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                block.setXpos(string.nextInt());
            if(string.equals("startY"))
                block.setYpos(string.nextInt());
        }while(string.equals("End"));
        block.updateC();
        state.getBlocks().add(block);
    }

    private void readInBreakableBlock(Level1State state,Scanner scanner){
    	String string;
        BreakableBlock breakableBlock=new BreakableBlock();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                breakableBlock.setXpos(string.nextInt());
            if(string.equals("startY"))
                breakableBlock.setYpos(string.nextInt());
        }while(string.equals("End"));
        breakableBlock.updateC();
        state.getBlocks().add(breakableBlock);
    }

    private void readInPipe(Level1State state,Scanner scanner){
    	// implement
    }

    private void readInGoomba(Level1State state,Scanner scanner){
    	String string;
        Goomba goomba=new Goomba();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                goomba.setXpos(string.nextInt());
            if(string.equals("startY"))
                goomba.setYpos(string.nextInt());
        }while(string.equals("End"));
        state.getEnemies().add(goomba);
    }

    private void readInKoopa(Level1State state,Scanner scanner){
    	String string;
        Koopa koopa=new Koopa();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                koopa.setXpos(string.nextInt());
            if(string.equals("startY"))
                koopa.setYpos(string.nextInt());
            if(string.equals("size")){
                if(size==2)
                    koopa.setFlying(true);
            }
        }while(string.equals("End"));
        state.getEnemies().add(koopa);
    }

    private void readInItemBox(Level1State state,Scanner scanner){
    	// implement
    }

    private void readInPiranhaPlant(Level1State state,Scanner scanner){
    	String string;
        PiranhaPlant piranhaPlant=new PiranhaPlant();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                piranhaPlant.setXpos(string.nextInt());
            if(string.equals("startY"))
                piranhaPlant.setYpos(string.nextInt());
        }while(string.equals("End"));
        state.getEnemies().add(piranhaPlant);
    }

    private void readInFirePiranhaPlant(Level1State state,Scanner scanner){
    	String string;
        FirePiranhaPlant firePlant=new FirePiranhaPlant();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                firePlant.setXpos(string.nextInt());
            if(string.equals("startY"))
                firePlant.setYpos(string.nextInt());
        }while(string.equals("End"));
        state.getEnemies().add(firePlant);
    }
}