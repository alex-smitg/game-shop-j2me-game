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
        } else {
            if (selectedCell.type != Types.EMPTY) {
                menu.addItem(new MenuItem("Снести", Actions.DEMOLISH,
                        0));
            }
            switch (selectedCell.type) {

                case Types.EMPTY:
                    text_name = "Свободное место";
                    description.addElement("Здесь можно");
                    description.addElement("установить оборудование");

                    menu.addItem(new MenuItem("Построить кассу",
                            Actions.BUILD_CHECKOUT,
                            Prices.build_checkout));
                    menu.addItem(new MenuItem("Построить полку",
                            Actions.BUILD_SHELF,
                            Prices.build_shelf));
                    menu.addItem(new MenuItem("Построить растение",
                            Actions.BUILD_FLOWERPOT,
                            Prices.build_flowerpot));
                    menu.addItem(new MenuItem("Торговый автомат",
                            Actions.BUILD_VENDING_MACHINE,
                            Prices.build_vending_machine));
                    break;
                case Types.SHELF:
                    text_name = "Полка (Уровень "
                            + String.valueOf(selectedCell.level + 1) + ")";
                    description.addElement("Полка с играми");

                    if (selectedCell.level == 0) {
                        menu.addItem(new MenuItem("Улучшить полку до 2LVL",
                                Actions.UPGRADE_SHELF_TO_LVL_2,
                                (int) (Prices.build_shelf * Prices.upgrade_multiplier)));
                    }
                    break;
                case Types.CHECKOUT:
                    text_name = "Касса (Уровень "
                            + String.valueOf(selectedCell.level + 1) + ")";

                    if (selectedCell.level == 0) {
                        description.addElement("Простой стол и кассовый аппарат");
                        menu.addItem(new MenuItem("Улучшить кассу до 2LVL",
                                Actions.UPGRADE_CHECKOUT_TO_LVL2,
                                (int) (Prices.build_checkout * Prices.upgrade_multiplier)));
                    } else {
                        description.addElement("Касса с сотрудником");
                    }
                    break;
                case Types.FLOWERPOT:
                    text_name = "Растение";
                    description.addElement("Увеличивает множитель");
                    description.addElement("продажи игр на 0.05");
                    break;
                case Types.VENDING_MACHINE:
                    text_name = "Торговый автомат";
                    description.addElement("Приносит пассивный доход 1$");
                    break;
            }
            menu.addItem(new MenuItem("Изменить цвет: Травяной",
                    Actions.CHANGE_CELL_COLOR_TO_GRASS,
                    0));
            menu.addItem(new MenuItem("Изменить цвет: Плитка",
                    Actions.CHANGE_CELL_COLOR_TO_TILES,
                    0));
        }

        menu.position = new Vector2d(20, description.size() * 30 + 50);

        super.show();
    }

    void goBack() {
        parent.changeFocusTo(parent.world);

        //objectives 
        if (Objectives.expand_shop == 2
                && !Objectives.expand_completed) {
            parent.dialog.jumpToNextItem();
            parent.changeFocusTo(parent.dialog);
            Objectives.expand_completed = true;
        }

        if (Objectives.shelves >= 1
                && Objectives.checkouts >= 1
                && !Objectives.checkout_and_shelves_completed) {
            parent.dialog.jumpToNextItem();
            parent.changeFocusTo(parent.dialog);
            Objectives.checkout_and_shelves_completed = true;
        }
        parent.world.show();
        hide();
    }

    void keyPressed(int keyCode) {
        if (hasFocus) {
            menu.keyPressed(keyCode);

            if (keyCode == Keys.KEY_CENTER) {
                MenuItem menuItem = menu.select();
                if (menuItem.action == Actions.BACK) {
                    goBack();
                } else {
                    if (parent.world.hasEnoughMoney(menuItem.price)) {
                        parent.world.receiveAction(menuItem.action);
                        goBack();
                    }
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

            g.setColor(255, 255, 255);

            for (int i = 0; i < description.size(); i++) {
                g.drawString((String) description.elementAt(i),
                        0, 60 + i * font.getHeight(),
                        Graphics.TOP | Graphics.LEFT);
            }
        }
    }

}
