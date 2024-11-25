package entity;

import main.GamePanel;
import main.KeyHandler;
import main.Utility_Tool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// Use PISKEL to create Sprite 16x16 save as png
public class Player extends Entity{

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int spriteCounter2 = 0;

    public int originalSpeed = 4;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth /2 - (gp.tileSize / 2);
        screenY = gp.screenHeight/2- (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;


        setDefaultValue();
        getPlayerImage();
    }

    public void setDefaultValue(){
        // player position on world Map
         worldX = gp.tileSize * 23;
         worldY = gp.tileSize * 21;
         speed = 4;
         direction="down";

         //PLAYER STATUS
        maxLife = 6; //2 life = 1 heart
        life = maxLife;



    }

    public void getPlayerImage(){
        up1 = setup("lila_up_1");
        up2 = setup("lila_up_2");
        down1 = setup("lila_down_1");
        down2 = setup("lila_down_2");
        left1 = setup("lila_left_1");
        left2 = setup("lila_left_2");
        right1 = setup("lila_right_1");
        right2 = setup("lila_right_2");
    }

public BufferedImage setup(String imageName){
    Utility_Tool uTool = new Utility_Tool();
    BufferedImage image = null;

    try{
        image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/" + imageName + ".png"));
        image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);
    } catch(IOException e) {
        e.printStackTrace();
    }
    return image;

}
    public void update(){

        if(keyH.upPressed || keyH.downPressed ||
                keyH.leftPressed || keyH.rightPressed || keyH.enterPressed == true){

            // Updating  player X, Y position
            if(keyH.upPressed){
                direction = "up";


            } else if (keyH.downPressed) {
                direction = "down";


            } else if (keyH.leftPressed) {
                direction = "left";


            } else if(keyH.rightPressed){
                direction = "right";

            }

            // Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // check obj collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //Check NPC collision
            int npcIndex= gp.cChecker.checkEntity(this, gp.npc);
           interactNPC(npcIndex);

           // Chack Monster Collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //Check Event
            gp.eHandler.checkEvent();

            // if collision is false player can move
            if (collisionOn == false || keyH.enterPressed == false){
                switch(direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            gp.keyH.enterPressed = false;




            // Sprite counter will only change when you pressed keys
            //everytime we hit a key, sprite counter will count frame per 60 frames FPS
            // When sprite counter hit 15 it will change image to create walking effect
            spriteCounter++; // player image changed in every 15 frames
            if(spriteCounter > 12){
                if(spriteNum == 1){
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
            //return sprite to stand still pos
            spriteCounter2++;

            if(spriteCounter2 == 20 ){
                spriteNum = 1;
                spriteCounter2 = 0;
            }

        }
        // Outside of Key if statement
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void pickUpObject(int i){
        if (i != 999) {
        }


    }


    public void interactNPC(int i) {
        if (i != 999) {
            if (gp.keyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {

            if (invincible == false) {
                life -= 1;
                invincible = true;
            }
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {

            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;

            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;

            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;

            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;

        }

        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screenX, screenY, null);

        // Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


//        //Debug
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.WHITE);
//        g2.drawString("Invincible:" + invincibleCounter, 10, 400);

        // DEBUG Player solid Area
        if (keyH.checkSolidArea == true) {
            g2.setColor(Color.RED);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}
