package game_shop;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public abstract class View {

    GameCanvas parent;

    boolean hasFocus = false;
    boolean visible = true;
    
    boolean isVisible() {
        return visible;
    }
    
    void show() {
        visible = true;
    }
    
    void hide() {
        visible = false;
    }

    abstract void keyPressed(int keyCode);

    abstract void update();

    abstract void draw(Graphics g, Images images, Font font);
}
