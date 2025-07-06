package game_shop;

import javax.microedition.lcdui.Graphics;

public class Cell {

    Vector2d position = new Vector2d(0, 0);

    int type = Types.EMPTY;

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
                        Graphics.TOP | Graphics.LEFT) ;
                break;
        }
    }
}
