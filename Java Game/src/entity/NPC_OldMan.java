package entity;

import main.GamePanel;
import main.Utility_Tool;

import java.awt.*;
import java.util.Random;


public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("npc/oldman_up_1");
        up2 = setup("npc/oldman_up_2");
        down1 = setup("npc/oldman_down_1");
        down2 = setup("npc/oldman_down_2");
        left1 = setup("npc/oldman_left_1");
        left2 = setup("npc/oldman_left_2");
        right1 = setup("npc/oldman_right_1");
        right2 = setup("npc/oldman_right_2");
    }

    public void setDialogue() {
        dialogues[0] = "Hello there, lad";
        dialogues[1] = "So you have finally come for the \nlost treasure!";
        dialogues[2] = "I used to be a great wizard...\nBut I lost my mana to that \ndamn dragon.";
        dialogues[3] = "If you help me look for the \n100-year mana flower!" +
                " \nI will reward you greatly!";
    }

    //Override set Action in Entity
    @Override
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick from 1 to 100, without +1 pick 0-99

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
            // once change directions it won't change again for the next 120 frames
        }
    }

    public void speak() {
    super.speak();

    }

}