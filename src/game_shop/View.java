package game_shop;

import javax.microedition.lcdui.Graphics;

public abstract class View {

    final int KEY_LEFT = -3;
    final int KEY_RIGHT = -4;
    final int KEY_UP = -1;
    final int KEY_DOWN = -2;
    final int KEY_CENTER = -6;

    boolean hasFocus = false;
    boolean visible = true;

    abstract void keyPressed(int keyCode);

    abstract void update();

    abstract void draw(Graphics g, Images images);
}
