package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    // Screen settings
    final int originalTitleSize = 16; //16x16 tile (default size for retro game)
    final int scale = 3; // scaling (16 * 3 = 48 px to fit screen)

    public int tileSize = originalTitleSize * scale; //48x48 tile
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol; // 768 px
    public int screenHeight = tileSize * maxScreenRow; // 576 px

    // World Setting
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;


    //SYSTEM
    TileManager tileM = new TileManager(this);// Instantiate Tile Manager
    public KeyHandler keyH = new KeyHandler(this);  // Instantiate KeyHandler
    Sound music = new Sound();    //Instantiate Sound (bg music)
    Sound se = new Sound();  //Instantiate Sound (sound effects)
    public CollisionChecker cChecker = new CollisionChecker(this);// Instantiate Collision Checker
    public AssetSetter aSetter = new AssetSetter(this); // Instantiate Asset setter
    public UI ui = new UI(this);   // UI
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread; // Game Loop calling run method


    // Entity & Objects
    public Player player = new Player(this, keyH); // Instantiate Player
    public Entity obj[] = new Entity[10];    // creating up to 10 objects at the same time
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // better rendering
        this.addKeyListener(keyH); // add key handler to panel
        this.setFocusable(true);
    }

    public void setUpGame(){
        // This method is to add set up settings for objects
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        playMusic(0);
        stopMusic();
        gameState = titleState;


    }

    public void startGameThread(){
        gameThread = new Thread(this);
        // Passing this (Game panel graph) into Thread constructor
        // to instantiate it
        gameThread.start();
    }

    // Delta Method For Game Loop
    @Override
    public void run() {
        double drawInterval = (1000000000) / FPS; // 0.01666 s
        double delta = 0;
        double lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;


        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;

            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }


        }
    }

    public void update(){
        // Updating  player X, Y position
        // if the game State is not at pause
        if(gameState == playState){
            //PLAYER
            player.update();

            //NPC
            for(int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }

            //MONSTER
            for(int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    monster[i].update();
                }
            }
        }
        if (gameState == pauseState){
            //nothing
        }
    }

    // default paint component of JFrame
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //Convert to 2D
        Graphics2D g2 = (Graphics2D)g;

        //DEBUG set up
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);

        }
        else{
            //Tile
            tileM.draw(g2); // draw tile first before player


            //PLAYER
            entityList.add(player);

            //NPC
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            //OBJECT
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }

            //MONSTER
            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }

            //SORT CORRECTION
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //DRAW ENTITIES
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }

            // EMPTY ENTITIES LIST
          entityList.clear();

            // UI
            ui.draw(g2);

        }


        // DEBUG - Check how much time is passed
        if(keyH.checkDrawTime == true) {
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        g2.setColor((Color.WHITE));
        g2.drawString("Draw Time: " + passed, 10, 400);
        System.out.println("Draw Time " + passed);}



        g2.dispose(); // save memories
    }


    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    // Sound Effect
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
