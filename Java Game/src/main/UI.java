package main;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message ="";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: 1st screen, 1: selection screen, 2: Control guide

    Graphics2D g2;


    public UI(GamePanel gp) {
        this.gp = gp;

       // For fonts folder must have "/" before the folder name
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/fonts/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //CREATE HEART OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }



    public void showMessage(String text){
        message = text;
        messageOn = true;

    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);

        // TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        // PLAY STATE
        if(gp.gameState == gp.playState){
            drawPlayerLife();
        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();

        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();

        }

    }

    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // DRAW BLANK HEART
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x+= gp.tileSize;
        }

        // reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
         i = 0;

        // DRAW CURRENT LIFE
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x+= gp.tileSize;
        }
    }

    public void drawTitleScreen(){

        if(titleScreenState == 0) {

            //Background
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //Title name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Star Huntress";
            int x =  getXForCenteredText(text);
            int y = gp.tileSize*3;

            //Shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, x+5, y+5);

            //main color
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //BLUE BOY IMAGE
            x = gp.screenWidth /2 - (gp.tileSize*2)/2;
            y += gp.tileSize*1;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x =  getXForCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME";
            x =  getXForCenteredText(text);
            y += gp.tileSize* 1.2;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x =  getXForCenteredText(text);
            y += gp.tileSize* 1.2;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "CONTROL GUIDE";
            x =  getXForCenteredText(text);
            y += gp.tileSize* 1.2;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x - gp.tileSize, y);
            }


        }
        else if(titleScreenState == 1){ // select class
            //Background
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //Title name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 67F));
            String text = "SELECT YOUR CLASS";
            int x =  getXForCenteredText(text);
            int y = gp.tileSize*2;
            //Shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, x+5, y+5);
            //main color
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));

            text = "FIGHTER";
            x =  getXForCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "HEALER";
            x =  getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "SORCERER";
            x =  getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "BACK";
            x =  getXForCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x - gp.tileSize, y);
            }

        }
        else if(titleScreenState == 2){ // control guide
            //Background
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //Title name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Control Guide";
            int x =  getXForCenteredText(text);
            int y = gp.tileSize*3;

            //Shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, x+5, y+5);

            //main color
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);


        }


    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }


    public void drawDialogueScreen(){
        //WINDOW
        int x = gp.tileSize * 2;
        int y =  gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
        x += gp.tileSize;
        y += gp.tileSize;

        for( String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }

    }

public void drawSubWindow(int x , int y, int width, int height){
        Color c = new Color(0, 0, 0, 220);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width, height,35,35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10, height-10,25,25);
}

// Method to find x to center the test
    public int getXForCenteredText(String text){
        // Find the center of the screen to display text
        //(int)g2.getFontMetrics() => how long the text will be on screen
        //.getStringBounds(text, g2).getWidth() => return the width as length
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}

