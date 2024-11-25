package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    public boolean checkDrawTime = false;
    public boolean checkSolidArea = false;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // return number of the key that is pressed
        int code = e.getKeyCode();

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            if (gp.ui.titleScreenState == 0) {
                gp.playSE(1);
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                        // Wrap around to the last option if going out of bounds
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;  // Wrap around to the first option if going out of bounds
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.ui.titleScreenState = 1;
                    }
                    if (gp.ui.commandNum == 1) {

                    }
                    if (gp.ui.commandNum == 2) {
                        System.exit(0);
                    }
                    if (gp.ui.commandNum == 3) {
                        gp.ui.titleScreenState = 2;
                    }

                }
            } else if (gp.ui.titleScreenState == 2) { // CONTROL GUIDE
                if (code == KeyEvent.VK_ESCAPE) {
                    gp.ui.titleScreenState = 0;
                    gp.ui.commandNum = 0;
                }
            } else if (gp.ui.titleScreenState == 1) { // CLASS SELECTION
                gp.playSE(1);
                if (code == KeyEvent.VK_ESCAPE) {
                    gp.ui.titleScreenState = 0;
                    gp.ui.commandNum = 0;
                }
                if (code == KeyEvent.VK_W) {// select class
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;  // Wrap around to the last option if going out of bounds
                    }
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;
                        // Wrap around to the first option if going out of bounds
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        System.out.println("Do Fighter things");
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 1) {
                        System.out.println("Do Healer things");
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    if (gp.ui.commandNum == 2) {
                        System.out.println("Do Sorcerer things");
                        gp.gameState = gp.playState;
                        gp.playMusic(0);

                    }
                    if (gp.ui.commandNum == 3) {
                        gp.gameState = gp.ui.titleScreenState = 0;
                        gp.ui.commandNum = 0;
                        gp.playSE(1);
                    }
                }
            }


        }
        // Play state controls
        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }

        // Check for pause toggle globally
        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.stopMusic();
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.playMusic(0);
                gp.gameState = gp.playState;
            }
        }

        // Pause state controls
        if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.ui.titleScreenState = 0;
                gp.ui.commandNum = 0;
            }
        }

        // Dialogue state controls
        if (gp.gameState == gp.dialogueState && code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }

        // Debug controls
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
        if (code == KeyEvent.VK_Y) {
            checkSolidArea = !checkSolidArea;
        }


    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

}
