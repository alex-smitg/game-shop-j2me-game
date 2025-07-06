package game_shop;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public class Menu extends View {

    private int selected_index = 0;
    Vector items = new Vector();
    int gap = 25;
    Vector2d position = new Vector2d(0, 0);

    void addItem(MenuItem menuItem) {
        items.addElement(menuItem);
    }

    void keyPressed(int keyCode) {
        if (keyCode == KEY_DOWN) {
            selected_index++;
            if (selected_index >= items.size()) {
               selected_index = 0;
            }
        }
        if (keyCode == KEY_UP) {
            selected_index--;
            if (selected_index <= -1) {
                selected_index = items.size() - 1;
            }
        }
    }
    
    MenuItem select() {
        return (MenuItem) items.elementAt(selected_index);
    }

    void update() {

    }

    void draw(Graphics g, Images images) {
        for (int i = 0; i < items.size(); i++) {
            g.setColor(255, 255, 255);
            if (i == selected_index) {
                g.setColor(255, 255, 0);
            }
            
            g.drawString(String.valueOf(i) + " : "
                    + String.valueOf(((MenuItem) items.elementAt(i)).text),
                    (int) position.x, (int) position.y + i * gap,
                    Graphics.TOP | Graphics.LEFT);
        }
    }

}
