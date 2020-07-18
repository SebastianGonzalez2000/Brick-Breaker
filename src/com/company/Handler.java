package com.company;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    LinkedList<GameObject> objects = new LinkedList<>();
    private Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public void tick() {
        for (int i = 0 ; i < objects.size() ; i++) {
            GameObject tempObject = objects.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0 ; i < objects.size() ; i++) {
            GameObject tempObject = objects.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object) {
        this.objects.add(object);
    }
    public void removeObject(GameObject object) {
        this.objects.remove(object);
    }

    public void clear() {
        for (int i = 0 ; i < objects.size() ; i++) {
            GameObject tempObject = objects.get(i);
            this.removeObject(tempObject);
        }
    }

    public boolean noBricksLeft() {
        if (game.gameState == Game.STATE.MENU || game.gameState == Game.STATE.END) {return false;}
        for (int i = 0 ; i < objects.size() ; i++) {
            GameObject tempObject = objects.get(i);
            if (tempObject.getId() == ID.BRICK && game.gameState == Game.STATE.GAME) {return false;}
        } return true;
    }
}
