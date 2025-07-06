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
    
    
    int money = 100;

    World(int width, int height, GameCanvas gameCanvas) {
        this.parent = gameCanvas;
        camera = new Camera(new Vector2d(
                width / 2 - CELL_HALF_WIDTH,
                height / 2 - CELL_HALF_HEIGHT)
        );

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Cell cell = new Cell();
                cell.position.x = x * 48;
                cell.position.y = y * 24;
                cells.addElement(cell);

                cells_map.put(String.valueOf(x * 48) + "|" + String.valueOf(y * 24), cell);

                cell = new Cell();
                cell.position.x = x * 48 + 24;
                cell.position.y = y * 24 + 12;

                cells_map.put(String.valueOf(x * 48 + 24) + "|" + String.valueOf(y * 24 + 12), cell);
                cells.addElement(cell);

            }
        }
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
                g.drawImage(images.cell_0,
                        (int) (cell.position.x - camera.getPosition().x),
                        (int) (cell.position.y - camera.getPosition().y),
                        Graphics.TOP | Graphics.LEFT);
            }

            g.drawImage(
                    images.cell_selector,
                    (int) (cursor_position_index.x * CELL_HALF_WIDTH
                    - camera.getPosition().x),
                    (int) (cursor_position_index.y * CELL_HALF_HEIGHT
                    - camera.getPosition().y),
                    Graphics.TOP | Graphics.LEFT);
        }
    }
}
