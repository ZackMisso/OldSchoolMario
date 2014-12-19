/**
 *
 * @author Zackary Misso
 * 
 */
package gameIO;
import core.GlobalController;
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
import tilesAndGraphics.TileMap;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
public class GameReader {
	// TODO :: MAKE A BETTER QUERY SYSTEM
    public GameReader(Level1State state){
    	loadLevel1(state);
    }
    
    // TODO :: make more abstract method later
    private void loadLevel1(Level1State state){
        try{
            boolean mario=false;
            Scanner scanner=new Scanner(new File(GlobalController.levelFile));
            while(scanner.hasNextLine()){
                String string=scanner.nextLine();
                if(string.equals("Tile"))
                    //readInTileMap(state,scanner);
                if(string.equals("mario")&&!mario){
                    readInMario(state,scanner);
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
    
    // Tile Map Settings ::
    // Tile
    // startX
    // startY
    // width
    // height
    // data array
    // Depreciated :: Zack
//    private void readInTileMap(Level1State state,Scanner scanner){
//        scanner.nextLine();
//        scanner.nextLine();
//        int width=scanner.nextInt();
//        //System.out.println(width+" :: Width :: GameReader");
//        int height=scanner.nextInt();
//        //System.out.println(height+" :: HEIGHT :: GameReader");
//        int[][] tileMap=new int[height][width];
//        for(int i=0;i<height;i++){
//            //Scanner scan=new Scanner(scanner.nextLine());
//            for(int f=0;f<width;f++){
//                tileMap[i][f]=scanner.nextInt();
//            }
//        }
//        //System.out.println(tileMap.length+" :: GameReader");
//        //state.makeTileMap(new TileMap(16,tileMap));
//    }

    private void readInMario(Level1State state,Scanner scanner){
    	String string;
        Mario mario=new Mario();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                mario.setXpos(scanner.nextInt());
            if(string.equals("startY"))
                mario.setYpos(scanner.nextInt());
        }while(!string.equals("End"));
        mario.setXpos(-100);
        state.setPlayer(mario);
    }

    private void readInFloor(Level1State state,Scanner scanner){
    	String string;
        Floor floor=new Floor();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                floor.setXpos(scanner.nextInt());
            if(string.equals("startY"))
                floor.setYpos(scanner.nextInt());
        }while(!string.equals("End"));
        floor.updateC();
        state.getBlocks().add(floor);
    }

    private void readInBlock(Level1State state,Scanner scanner){
    	String string;
        int cnt=0;
        Block block=new Block();
        do{
            cnt++;
            string=scanner.next();
            //int num=scanner.nextInt();
            //System.out.println(num);
            if(string.equals("startX")){
                int num=scanner.nextInt();
                block.setXpos(num);
                block.setStartX(num);
                //System.out.println(block.getXpos());
            }
            if(string.equals("startY")){
                //System.out.println("AADFASFDAFDas :: GameReader");
                block.setYpos(scanner.nextInt());
                //System.out.println(block.getYpos()+" :: GameReader");
            }
        }while(!string.equals("End"));
        //System.out.println();
        //System.out.println("Counter :: "+cnt);
        //System.out.println();
        block.updateC();
        state.getBlocks().add(block);
    }

    private void readInBreakableBlock(Level1State state,Scanner scanner){
    	String string;
        BreakableBlock breakableBlock=new BreakableBlock();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                breakableBlock.setXpos(scanner.nextInt());
            if(string.equals("startY"))
                breakableBlock.setYpos(scanner.nextInt());
        }while(!string.equals("End"));
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
                goomba.setXpos(scanner.nextInt());
            if(string.equals("startY"))
                goomba.setYpos(scanner.nextInt());
        }while(!string.equals("End"));
        state.getEnemies().add(goomba);
    }

    private void readInKoopa(Level1State state,Scanner scanner){
    	String string;
        Koopa koopa=new Koopa();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                koopa.setXpos(scanner.nextInt());
            if(string.equals("startY"))
                koopa.setYpos(scanner.nextInt());
            if(string.equals("size")){
                int size=scanner.nextInt();
                if(size==2)
                    koopa.setFlying(true);
            }
        }while(!string.equals("End"));
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
                piranhaPlant.setXpos(scanner.nextInt());
            if(string.equals("startY"))
                piranhaPlant.setYpos(scanner.nextInt());
        }while(!string.equals("End"));
        state.getEnemies().add(piranhaPlant);
    }

    private void readInFirePiranhaPlant(Level1State state,Scanner scanner){
    	String string;
        FirePiranhaPlant firePlant=new FirePiranhaPlant();
        do{
            string=scanner.next();
            if(string.equals("startX"))
                firePlant.setXpos(scanner.nextInt());
            if(string.equals("startY"))
                firePlant.setYpos(scanner.nextInt());
        }while(!string.equals("End"));
        state.getEnemies().add(firePlant);
    }
}