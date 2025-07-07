package game_shop;

import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class Menu extends View {

    private int selected_index = 0;
    Vector items = new Vector();
    int gap = 25;
    Vector2d position = new Vector2d(0, 0);
    View parent;

    void addItem(MenuItem menuItem) {
        items.addElement(menuItem);
    }

    void keyPressed(int keyCode) {
        if (hasFocus) {
            if (keyCode == Keys.KEY_DOWN) {
                selected_index++;
                if (selected_index >= items.size()) {
                    selected_index = 0;
                }
            }
            if (keyCode == Keys.KEY_UP) {
                selected_index--;
                if (selected_index <= -1) {
                    selected_index = items.size() - 1;
                }
            }
        }
    }

    MenuItem select() {
        int temp = selected_index;
        selected_index = 0;
        return (MenuItem) items.elementAt(temp);     
    }

    void update() {

    }

    void removeAllItems() {
        items.removeAllElements();
    }

    void draw(Graphics g, Images images, Font font) {
        for (int i = 0; i < items.size(); i++) {
            g.setColor(255, 255, 255);
            if (i == selected_index) {
                g.setColor(0, 255, 0);
            }

            MenuItem menuItem = (MenuItem) items.elementAt(i);

            String priceText = "";
            if (menuItem.price != 0) {
                priceText += String.valueOf(menuItem.price) + "$";
          
            }
            String text = menuItem.text + " " + priceText;

            g.drawImage(images.arrow_right,
                    (int) position.x - 12,
                    (int) position.y + selected_index * gap ,
                    Graphics.TOP | Graphics.LEFT);

            g.drawString(text,
                    (int) position.x, (int) position.y + i * gap,
                    Graphics.TOP | Graphics.LEFT);
        }
    }

}
