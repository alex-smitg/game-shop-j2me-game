package game_shop;

import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class ActionsMenu extends View {

    Menu menu = new Menu();
    Cell selectedCell = null;

    String text_name = "";
    Vector description = new Vector();

    ActionsMenu(GameCanvas gameCanvas) {
        this.parent = gameCanvas;
        menu.position = new Vector2d(20, 90);

    }

    void show() {
        description.removeAllElements();
        text_name = "";
        menu.removeAllItems();
        menu.addItem(new MenuItem("Назад", Actions.BACK));

        if (selectedCell == null) {
            text_name = "Стена";
            description.addElement("Неизвестно, что");
            description.addElement("находится за ней...");
            description.addElement("Можно разрушать стены");
            description.addElement("для расширения магазина");
                   
            menu.addItem(new MenuItem("Разрушить стену", Actions.EXPAND,
                    Prices.expand));
        }
        
        menu.position = new Vector2d(20, description.size() * 30 + 50);

        super.show();
    }

    void keyPressed(int keyCode) {
        if (hasFocus) {
            menu.keyPressed(keyCode);

            if (keyCode == Keys.KEY_CENTER) {
                MenuItem menuItem = menu.select();
                switch (menuItem.action) {
                    case (Actions.BACK):
                        parent.changeFocusTo(parent.world);
                        parent.world.show();
                        hide();
                        break;

                }

            }
        }
    }

    void update() {
        menu.hasFocus = hasFocus;
        menu.update();
    }

    void draw(Graphics g, Images images, Font font) {
        if (visible) {
            menu.draw(g, images, font);

            g.setColor(255, 255, 255);

            g.drawString(text_name,
                    0, 30,
                    Graphics.TOP | Graphics.LEFT);

            for (int i = 0; i < description.size(); i++) {
                g.drawString((String) description.elementAt(i),
                        0, 60 + i * font.getHeight(),
                        Graphics.TOP | Graphics.LEFT);
            }
        }
    }

}
