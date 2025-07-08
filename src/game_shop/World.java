package game_shop;

import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class World extends View {

    Vector2d cursor_position_index = new Vector2d(0, 0);
    private final Random random = new Random();
    private final Camera camera;

    final int CELL_WIDTH = 64;
    final int CELL_HEIGHT = 32;
    final int CELL_HALF_WIDTH = CELL_WIDTH / 2;
    final int CELL_HALF_HEIGHT = CELL_HEIGHT / 2;

    int updateTime = 0;
    int time = 0;
    int timeSpeed = 70;

    Vector cells = new Vector();
    Hashtable cells_map = new Hashtable();

    Vector floatingTexts = new Vector();

    int current_clients = 0;
    int maxClients = 0;
    int money = 600;

    World(int width, int height, GameCanvas gameCanvas) {
        this.parent = gameCanvas;
        camera = new Camera(new Vector2d(
                width / 2 - CELL_HALF_WIDTH,
                height / 2 - CELL_HALF_HEIGHT)
        );

//        for (int x = 0; x < 3; x++) {
//            for (int y = 0; y < 3; y++) {
//                Cell cell = new Cell();
//                cell.position.x = x * CELL_WIDTH;
//                cell.position.y = y * CELL_HEIGHT;
//                cells.addElement(cell);
//
//                cells_map.put(String.valueOf(x * CELL_WIDTH)
//                        + "|" + String.valueOf(y * CELL_HEIGHT), cell);
//                cell = new Cell();
//                cell.position.x = x * CELL_WIDTH + CELL_HALF_WIDTH;
//                cell.position.y = y * CELL_HEIGHT + CELL_HALF_HEIGHT;
//
//                cells_map.put(String.valueOf(x * CELL_WIDTH + CELL_HALF_WIDTH)
//                        + "|"
//                        + String.valueOf(y * CELL_HEIGHT + CELL_HALF_HEIGHT), cell);
//                cells.addElement(cell);
//
//            }
//        }
    }

    void substractMoney(int val, Vector2d cursorPosition) {
        money -= val;

        String text = "-" + String.valueOf(val) + "$";

        Vector2d position = new Vector2d(
                cursorPosition.x * CELL_HALF_WIDTH + CELL_HALF_WIDTH,
                cursorPosition.y * CELL_HALF_HEIGHT
        );

        int[] color = {255, 0, 0};
        FloatingText floatingText = new FloatingText(position,
                40,
                text,
                color
        );
        floatingTexts.addElement(floatingText);
    }

    void addMoney(int val, Vector2d cursorPosition) {
        money += val;

        String text = "+" + String.valueOf(val) + "$";

        Vector2d position = new Vector2d(
                cursorPosition.x * CELL_HALF_WIDTH + CELL_HALF_WIDTH,
                cursorPosition.y * CELL_HALF_HEIGHT
        );

        int[] color = {0, 255, 0};
        FloatingText floatingText = new FloatingText(position,
                40,
                text,
                color
        );
        floatingTexts.addElement(floatingText);
    }

    void receiveAction(int action) {
        switch (action) {
            case Actions.EXPAND:
                substractMoney(Prices.expand, cursor_position_index);

                Objectives.expand_shop++;

                Cell cell = new Cell();
                cell.position.x = (int) (cursor_position_index.x * CELL_HALF_WIDTH);
                cell.position.y = (int) (cursor_position_index.y * CELL_HALF_HEIGHT);
                cells.addElement(cell);
                cell.type = Types.EMPTY;

                cells_map.put(
                        String.valueOf((int) cell.position.x)
                        + "|"
                        + String.valueOf((int) cell.position.y),
                        cell);

                break;
            case Actions.BUILD_CHECKOUT:
                substractMoney(Prices.build_checkout, cursor_position_index);
                cell = getCell();
                cell.type = Types.CHECKOUT;
                cell.value_max = 1;
                Objectives.checkouts++;
                break;
            case Actions.BUILD_SHELF:
                substractMoney(Prices.build_shelf, cursor_position_index);
                cell = getCell();
                cell.type = Types.SHELF;
                cell.value_max = 3;
                Objectives.shelves++;
                break;
            case Actions.CHANGE_CELL_COLOR_TO_GRASS:
                cell = getCell();
                cell.style = CellStyles.GRASS;
                break;
            case Actions.CHANGE_CELL_COLOR_TO_TILES:
                cell = getCell();
                cell.style = CellStyles.TILES;
                break;
            case Actions.BUILD_FLOWERPOT:
                cell = getCell();
                substractMoney(Prices.build_flowerpot, cursor_position_index);
                cell.type = Types.FLOWERPOT;
                Data.pots++;
                break;
            case Actions.DEMOLISH:
                cell = getCell();
                if (cell.type == Types.FLOWERPOT) {
                    Data.pots--;
                }
                if (cell.type == Types.CHECKOUT) {
                    Objectives.checkouts--;
                }
                if (cell.type == Types.SHELF) {
                    Objectives.shelves--;
                }
                cell.type = Types.EMPTY;
                cell.level = 0;
                break;
            case Actions.UPGRADE_SHELF_TO_LVL_2:
                cell = getCell();
                substractMoney((int) (Prices.build_shelf * Prices.upgrade_multiplier),
                        cursor_position_index);
                cell.level = 1;
                cell.value_max = 9;
                break;
            case Actions.BUILD_VENDING_MACHINE:
                cell = getCell();
                substractMoney((int) Prices.build_vending_machine,
                        cursor_position_index);
                cell.type = Types.VENDING_MACHINE;
                break;
            case Actions.UPGRADE_CHECKOUT_TO_LVL2:
                cell = getCell();
                substractMoney((int) (Prices.build_checkout * Prices.upgrade_multiplier),
                        cursor_position_index);
                cell.level++;
                cell.value_max += 1;
        }
    }

    boolean hasEnoughMoney(int money) {
        if (this.money >= money) {
            return true;
        }
        return false;
    }

    void keyPressed(int keyCode) {
        if (hasFocus) {
            if (keyCode == Keys.KEY_RIGHT) {
                cursor_position_index.x += 1;
                cursor_position_index.y += 1;
            }

            if (keyCode == Keys.KEY_LEFT) {
                cursor_position_index.y -= 1;
                cursor_position_index.x -= 1;
            }

            if (keyCode == Keys.KEY_UP) {
                cursor_position_index.y -= 1;
                cursor_position_index.x += 1;

            }

            if (keyCode == Keys.KEY_DOWN) {
                cursor_position_index.y += 1;
                cursor_position_index.x -= 1;
            }

            if (keyCode == Keys.KEY_A) {
                parent.changeFocusTo(parent.actionsMenu);
                parent.actionsMenu.selectedCell = getCell();
                hide();
                parent.actionsMenu.show();
            }

            if (keyCode == Keys.KEY_CENTER) {
                Cell cell = getCell();
                if (cell != null) {
                    switch (cell.type) {
                        case Types.SHELF:
                            if (hasEnoughMoney(Prices.game
                                    * (cell.value_max - cell.value))
                                    && cell.value != cell.value_max) {

                                substractMoney(Prices.game
                                        * (cell.value_max - cell.value),
                                        cursor_position_index);
                                cell.fill();
                                if (Objectives.shelf_filling_completed == false
                                        && Objectives.checkout_and_shelves_completed) {
                                    Objectives.shelf_filling_completed = true;
                                    parent.dialog.jumpToNextItem();
                                    parent.changeFocusTo(parent.dialog);
                                }

                            }
                            break;

                        case Types.CHECKOUT:
                            if (cell.value > 0) {
                                addMoney((int) (Prices.game
                                        * (Prices.sale_multipler + Data.pots * 0.1f)),
                                        cursor_position_index);
                                cell.value -= 1;

                                if (Objectives.serving_completed == false
                                        && Objectives.shelf_filling_completed) {
                                    Objectives.serving_completed = true;
                                    parent.dialog.jumpToNextItem();
                                    parent.changeFocusTo(parent.dialog);
                                }
                            }
                            break;
                    }
                }
            }

        }
    }

    void updateCells() {

        for (int i = 0; i < cells.size(); i++) {
            Cell cell = (Cell) cells.elementAt(i);
            
            

            
            if (current_clients > 0) {
                if (cell.type == Types.CHECKOUT) {
                    
                    
                    if (cell.value < cell.value_max) {
                        cell.value += 1;
                        current_clients -= 1;
                        cell.waitTime = cell.maxWaitTime;
                    }
                    
                }
            }

            int ret = cell.updateOnce(current_clients, maxClients);

            switch (ret) {
                case CellReturns.CLIENT_PICKED_GAME:
                    current_clients++;
                    break;
                case CellReturns.VENDING_MACHINE:
                    addMoney(3, new Vector2d(cell.position.x / CELL_HALF_WIDTH,
                            cell.position.y / CELL_HALF_HEIGHT));
                    break;
                case CellReturns.SERVED:
                    addMoney((int) (Prices.game * cell.value
                            * (Prices.sale_multipler + Data.pots * 0.1f)),
                            new Vector2d(cell.position.x / CELL_HALF_WIDTH,
                                    cell.position.y / CELL_HALF_HEIGHT));
                    cell.value = 0;
                    break;
            }
        }
    }

    public Cell getCell() {
        int pos_x = (int) cursor_position_index.x * CELL_HALF_WIDTH;
        int pos_y = (int) cursor_position_index.y * CELL_HALF_HEIGHT;
        String a = String.valueOf(pos_x) + "|" + String.valueOf(pos_y);

        if (!cells_map.containsKey(a)) {
            return null;
        } else {
            return (Cell) cells_map.get(a);

        }

    }

    void update() {
        maxClients = 0;
        camera.setTargetPosition(new Vector2d(
                (int) cursor_position_index.x * CELL_HALF_WIDTH,
                (int) cursor_position_index.y * CELL_HALF_HEIGHT)
        );

        if (money >= Objectives.MONEY_GOAL && !Objectives.money_goal_reached) {
            Objectives.money_goal_reached = true;
            parent.dialog.jumpToNextItem();
            parent.changeFocusTo(parent.dialog);
        }

        updateTime += 1;

        for (int i = 0; i < cells.size(); i++) {
            Cell cell = (Cell) cells.elementAt(i);
            cell.updateEveryFrame();
            
            if (cell.type == Types.CHECKOUT) {
                maxClients += (cell.level + 1);
            }
        }

        if (updateTime >= timeSpeed) {
            updateTime = 0;
            updateCells();
        }

        camera.update();
    }

    void draw(Graphics g, Images images, Font font) {
        if (visible) {

            for (int i = 0; i < cells.size(); i++) {
                Cell cell = (Cell) cells.elementAt(i);
                cell.drawFloor(g, images, new Vector2d(
                        cell.position.x - camera.getPosition().x,
                        cell.position.y - camera.getPosition().y
                ));

            }
            g.drawImage(
                    images.cell_selector,
                    (int) (cursor_position_index.x * CELL_HALF_WIDTH
                    - camera.getPosition().x),
                    (int) (cursor_position_index.y * CELL_HALF_HEIGHT
                    - camera.getPosition().y),
                    Graphics.TOP | Graphics.LEFT);

            for (int i = 0; i < cells.size(); i++) {
                Cell cell = (Cell) cells.elementAt(i);
                cell.draw(g, images, new Vector2d(
                        cell.position.x - camera.getPosition().x,
                        cell.position.y - camera.getPosition().y),
                        CELL_HEIGHT
                );
                cell.drawProgress(g, new Vector2d(
                        cell.position.x - camera.getPosition().x,
                        cell.position.y - camera.getPosition().y),
                        CELL_WIDTH, CELL_HEIGHT);
                
                cell.drawStatus(g, new Vector2d(
                        cell.position.x - camera.getPosition().x,
                        cell.position.y - camera.getPosition().y),
                        images,
                        CELL_WIDTH, CELL_HEIGHT);

            }

            for (int i = 0; i < floatingTexts.size(); i++) {
                FloatingText floatingText = (FloatingText) floatingTexts.elementAt(i);
                floatingText.update();
                floatingText.draw(g, font, camera.getPosition());
            }

            for (int i = floatingTexts.size() - 1; i >= 0; i--) {
                FloatingText floatingText = (FloatingText) floatingTexts.elementAt(i);
                if (floatingText.liveTime <= 0) {
                    floatingTexts.removeElementAt(i);
                }
            }

        }
    }
}
