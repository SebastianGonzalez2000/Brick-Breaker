package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private HUD hud;
    private int button_x = game.width/3;
    private int button_y = game.height/2;
    private int button_width = 200;
    private int button_height = 64;

    public Menu(Game game, Handler handler, HUD hud) {
        this.game = game;
        this.handler = handler;
        this.hud = hud;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (game.gameState == Game.STATE.MENU) {
            if (mouseOver(mx, my, button_x, button_y, button_width, button_height)) {
                game.gameState = Game.STATE.GAME;
                handler.addObject(new Player(Game.width / 2 - 50, 4 * Game.height / 5, ID.PLAYER, handler));
                handler.addObject(new Ball(Game.width / 2 - 50, 4 * Game.height / 9, ID.BALL, handler, hud));

                for (int i = 0; i < 17; i++) {
                    handler.addObject(new Brick(24 + (i * 35), 1 * Game.height / 15, ID.BRICK, handler));
                }
                for (int i = 0; i < 14; i++) {
                    handler.addObject(new Brick(78 + (i * 35), (1 * Game.height / 15) + 25, ID.BRICK, handler));
                }
                for (int i = 0; i < 10; i++) {
                    handler.addObject(new Brick(149 + (i * 35), (1 * Game.height / 15) + 50, ID.BRICK, handler));
                }
                for (int i = 0; i < 6; i++) {
                    handler.addObject(new Brick(219 + (i * 35), (1 * Game.height / 15) + 75, ID.BRICK, handler));
                }
                for (int i = 0; i < 2; i++) {
                    handler.addObject(new Brick(289 + (i * 35), (1 * Game.height / 15) + 100, ID.BRICK, handler));
                }
                return;
            }
        }
        if (game.gameState == Game.STATE.END || game.gameState == Game.STATE.WON) {
            if (mouseOver(mx, my, button_x, button_y, button_width, button_height)) {
                game.gameState = Game.STATE.MENU;
                hud.setLives(3);
                hud.setScore(0);
            }
        }
    }

    public void mouseReleased (MouseEvent e) {}
    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {return true;}
            else return false;
        } else return false;
    }

    public void render(Graphics g) {
        if (game.gameState == Game.STATE.MENU) {
            Font title = new Font("arial", 1, 50);
            Font button = new Font("arial", 1, 30);
            g.setFont(title);
            g.setColor(Color.white);
            g.drawString("Brick Breaker", game.width/4, game.height/4);
            g.setFont(button);
            g.setColor(Color.white);
            g.drawRect(button_x, button_y, button_width, button_height);
            g.drawString("Start", button_x + (button_width/3), button_y + (2*button_height/3));
        } else if (game.gameState == Game.STATE.END) {
            Font header = new Font("arial", 1, 50);
            Font button = new Font("arial", 1, 30);
            g.setFont(header);
            g.setColor(Color.white);
            g.drawString("Game Over", 15+ 1*game.width/4, game.height/4);
            g.setFont(button);
            g.setColor(Color.white);
            g.drawRect(button_x, button_y, button_width, button_height);
            g.drawString("Try Again", button_x -25 + 2*(button_width/7), button_y -10+ (4*button_height/5));
        } else if (game.gameState == Game.STATE.WON) {
            Font header = new Font("arial", 1, 50);
            Font button = new Font("arial", 1, 30);
            g.setFont(header);
            g.setColor(Color.white);
            g.drawString("You Win!", 35+ 1*game.width/4, game.height/4);
            g.setFont(button);
            g.setColor(Color.white);
            g.drawRect(button_x, button_y, button_width, button_height);
            g.drawString("Back", button_x +5  + 2*(button_width/7), button_y -10+ (4*button_height/5));
        }
    }
}
