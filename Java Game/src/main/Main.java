package main;


import javax.swing.*;

public class Main {
    public static void main(String[] args){

        // Frame set up
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Treasure Hunt Adventure");

        //Add game panel to window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // to view panel

        window.setLocationRelativeTo(null); // frame will appear in the middle of the computer screen once start
        window.setVisible(true);

        gamePanel.setUpGame(); // call this to load the object when the game start
        gamePanel.startGameThread();
    }
}