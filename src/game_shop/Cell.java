package game_shop;

import javax.microedition.lcdui.Graphics;

public class Cell {

    Vector2d position = new Vector2d(0, 0);

    int value = 3;
    int value_max = 8;

    int type = Types.EMPTY;

    void drawProgress(Graphics g, Vector2d position,
            int cell_width, int cell_height) {

        if (type == Types.CHECKOUT || type == Types.SHELF) {

            float progress = (float) value / (float) value_max;

            g.setColor(0, 0, 0);
            g.fillRect((int) position.x + cell_width / 4,
                    (int) position.y + cell_height / 2,
                    32, 4);
            g.setColor(255, 0, 0);
            g.fillRect((int) position.x + cell_width / 4 + 1,
                    (int) position.y + cell_height / 2 + 1,
                    (int) (30 * progress), 2);
        }
    }

    void drawFloor(Graphics g, Images images, Vector2d position) {
        g.drawImage(images.cell_0,
                (int) position.x, (int) position.y,
                Graphics.TOP | Graphics.LEFT);
    }

    void draw(Graphics g, Images images, Vector2d position, int cell_height) {

        switch (type) {
            case Types.CHECKOUT:
                g.drawImage(images.checkout,
                        (int) position.x, (int) position.y - cell_height * 2,
                        Graphics.TOP | Graphics.LEFT);
                break;
            case Types.SHELF:
                g.drawImage(images.shelf_0,
                        (int) position.x, (int) position.y - cell_height * 2,
                        Graphics.TOP | Graphics.LEFT);
                break;
        }
    }
}
