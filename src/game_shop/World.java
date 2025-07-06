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

    final int CELL_WIDTH = 48;
    final int CELL_HEIGHT = 24;
    final int CELL_HALF_WIDTH = CELL_WIDTH / 2;
    final int CELL_HALF_HEIGHT = CELL_HEIGHT / 2;

    Vector cells = new Vector();
    Hashtable cells_map = new Hashtable();

    int money = 800;

    World(int width, int height, GameCanvas gameCanvas) {
        this.parent = gameCanvas;
        camera = new Camera(new Vector2d(
                width / 2 - CELL_HALF_WIDTH,
                height / 2 - CELL_HALF_HEIGHT)
        );

        for (int x = 0; x < 0; x++) {
            for (int y = 0; y < 0; y++) {
                Cell cell = new Cell();
                cell.position.x = x * CELL_WIDTH;
                cell.position.y = y * CELL_HEIGHT;
                cells.addElement(cell);

                cells_map.put(String.valueOf(x * CELL_WIDTH) +
                        "|" + String.valueOf(y * CELL_HEIGHT), cell);
                cell.type = Types.CHECKOUT;
                cell = new Cell();
                cell.position.x = x * CELL_WIDTH + CELL_HALF_WIDTH;
                cell.position.y = y * CELL_HEIGHT + CELL_HALF_HEIGHT;

                cells_map.put(String.valueOf(x * CELL_WIDTH + CELL_HALF_WIDTH) +
                        "|" +
                        String.valueOf(y * CELL_HEIGHT + CELL_HALF_HEIGHT), cell);
                cells.addElement(cell);

            }
        }
    }

    void substractMoney(int val) {
        money -= val;
    }

    void receiveAction(int action) {
        switch (action) {
            case Actions.EXPAND:
                substractMoney(Prices.expand);

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
                substractMoney(Prices.build_checkout);
                getCell().type = Types.CHECKOUT;
                break;

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
        camera.setTargetPosition(new Vector2d(
                (int) cursor_position_index.x * CELL_HALF_WIDTH,
                (int) cursor_position_index.y * CELL_HALF_HEIGHT)
        );
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

            }

        }
    }
}
