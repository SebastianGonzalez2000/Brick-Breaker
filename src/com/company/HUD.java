package com.company;

import java.awt.*;

public class HUD {

    private static int score;
    private static int lives = 3;

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawString("Score: " + score, 85*Game.width/100, 9*Game.height/10);
        g.drawString("Lives: " + lives, 85*Game.width/100, (9*Game.height/10) + 15);
    }

    public void setScore(int score) {this.score = score;}
    public int getScore() {return score;}

    public void setLives(int lives) {this.lives = lives;}
    public int getLives() {return lives;}
}
