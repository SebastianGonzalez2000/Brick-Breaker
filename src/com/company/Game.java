package com.company;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static final int height = 480;
    public static final int width = 640;
    public static STATE gameState = STATE.MENU;
    private Thread thread;
    private boolean running;
    private Handler handler;
    private HUD hud;
    private Menu menu;

    public enum STATE {
        MENU,
        GAME,
        WON,
        END
    }

    public static void main(String[] args) {
	    Game game = new Game();
    }

    public Game() {
        hud = new HUD();
        handler = new Handler(this);
        menu = new Menu(this, handler, hud);
        this.addKeyListener(new KeyInput(handler, this));
        this.addMouseListener(menu);
        new Window(width, height, "Brick Breaker", this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }

            if (running) {
                render();
                frames++;}

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        this.setVisible(true);
        stop();
    }

    private void tick() {
        handler.tick();
        for (int i = 0 ; i < handler.objects.size() ; i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.BALL) {
                if (tempObject.getY() >= height + 300) {
                    hud.setLives(hud.getLives() - 1);
                    handler.removeObject(tempObject);
                    handler.addObject(new Ball( width/2 - 50, height/2, ID.BALL, handler, hud));
                }
            }
        }
        if (hud.getLives() == 0) {
            gameState = STATE.END;
            handler.clear();
        }
        if (handler.noBricksLeft()) {
            gameState = STATE.WON;
            handler.clear();
        }
    }
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        handler.render(g);

        if (gameState == STATE.GAME) {hud.render(g);}
        else if (gameState == STATE.MENU || gameState == STATE.END || gameState == STATE.WON) {menu.render(g);}

        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min, int max) {
        if (var >= max) {
            return max;
        }
        else if (var <= min) {
            return min;
        }
        else {
            return var;
        }
    }
}
