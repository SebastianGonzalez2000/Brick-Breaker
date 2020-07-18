package com.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private boolean[] keyDown = new boolean[2];
    private Game game;

    public KeyInput(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
        keyDown[0] = false;
        keyDown[1] = false;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0 ; i < handler.objects.size() ; i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.PLAYER) {
                if (key == KeyEvent.VK_LEFT) {
                    tempObject.setVelX(-10);
                    keyDown[0] = true;
                }
                if (key == KeyEvent.VK_RIGHT) {
                    tempObject.setVelX(10);
                    keyDown[1] = true;
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0 ; i < handler.objects.size() ; i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.PLAYER) {
                if (key == KeyEvent.VK_LEFT) {
                    keyDown[0] = false;
                }
                if (key == KeyEvent.VK_RIGHT) {
                    keyDown[1] = false;
                }
                if (!keyDown[0] && !keyDown[1]) {
                    tempObject.setVelX(0);
                }
            }
        }
    }
}
