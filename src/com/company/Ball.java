package com.company;

import java.awt.*;
import java.util.Random;

public class Ball extends GameObject {
    private int width = 16;
    private int height = 16;
    private Handler handler;
    private HUD hud;
    private Random r = new Random();

    public Ball(int x, int y, ID id, Handler handler, HUD hud) {
        super(x, y, id);
        this.handler = handler;
        this.hud = hud;
        velX = r.nextInt(2)+1;
        velY = r.nextInt(5)+3;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        if (x < 0 || x > Game.width - width) velX *= -1;
        if (y < 0 ) velY *= -1;
        collision();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void collision() {
        for (int i = 0 ; i < handler.objects.size() ; i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.PLAYER) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    velY *= -1;

                }
            }
            else if (tempObject.getId() == ID.BRICK) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    velY *= -1;
                    hud.setScore(hud.getScore() + 50);
                }
            }
        }
    }
}
