package game_shop;

import javax.microedition.lcdui.Graphics;

public class Cell {

    Vector2d position = new Vector2d(0, 0);

    int value = 0;
    int value_max = 0;

    int style = CellStyles.TILES;
    
    int type = Types.EMPTY;

    int waitTime = 0;
    int maxWaitTime = 300;

    void fill() {
        value = value_max;
    }

    void updateEveryFrame() {
        if (type == Types.CHECKOUT) {
            if (waitTime <= 0) {
                if (value > 0) {
                    value -= 1;
                }

            }
        }
        if (waitTime > 0) {
            waitTime -= 1;
        }

    }

    int updateOnce(int current_clients) {
        if (type == Types.SHELF && current_clients == 0) {
            if (value > 0) {
                value -= 1;
                return CellReturns.CLIENT_PICKED_GAME;
            }
        }

        return CellReturns.NONE;
    }

    void drawStatus(Graphics g, Vector2d position, Images images,
            int cell_width, int cell_height) {
        if (type == Types.CHECKOUT && value > 0) {
            position.x = position.x + cell_width / 4 + 8;
            position.y = position.y - cell_height / 2 + cell_height / 2;

            int n = maxWaitTime / 3;

            if (waitTime <= n) {
                g.drawImage(images.smile_angry,
                        (int) position.x,
                        (int) position.y,
                        Graphics.TOP | Graphics.LEFT);
            }

            if (waitTime > n && waitTime < 2 * n) {
                g.drawImage(images.smile_normal,
                        (int) position.x,
                        (int) position.y,
                        Graphics.TOP | Graphics.LEFT);
            }

            if (waitTime >= n * 2) {
                g.drawImage(images.smile_happy,
                        (int) position.x,
                        (int) position.y,
                        Graphics.TOP | Graphics.LEFT);
            }

        }

    }

    void drawProgress(Graphics g, Vector2d position,
            int cell_width, int cell_height) {

        if ((type == Types.CHECKOUT || type == Types.SHELF) & value != 0) {

            float progress = (float) value / (float) value_max;

            g.setColor(0, 0, 0);
            g.fillRect((int) position.x + cell_width / 4,
                    (int) position.y + cell_height / 2,
                    32, 4);

            g.setColor(255, 0, 0);
            if (progress > 0.60) {
                g.setColor(0, 255, 0);
            }
            if (progress > 0.40 && progress <= 0.60) {
                g.setColor(255, 255, 0);
            }
            g.fillRect((int) position.x + cell_width / 4 + 1,
                    (int) position.y + cell_height / 2 + 1,
                    (int) (30 * progress), 2);
        }
    }

    void drawFloor(Graphics g, Images images, Vector2d position) {
        switch (style) {
            case CellStyles.GRASS:
                g.drawImage(images.cell_0_grass,
                (int) position.x, (int) position.y,
                Graphics.TOP | Graphics.LEFT);
                break;
            case CellStyles.TILES:
                g.drawImage(images.cell_0,
                (int) position.x, (int) position.y,
                Graphics.TOP | Graphics.LEFT);
                break;
        }
        
        
    }

    void draw(Graphics g, Images images, Vector2d position, int cell_height) {

        switch (type) {
            case Types.CHECKOUT:
                if (value != 0) {
                    g.drawImage(images.checkout,
                            (int) position.x, (int) position.y - cell_height * 2,
                            Graphics.TOP | Graphics.LEFT);
                } else {
                    g.drawImage(images.checkout_empty,
                            (int) position.x, (int) position.y - cell_height * 2,
                            Graphics.TOP | Graphics.LEFT);
                }
                break;
            case Types.SHELF:
                if (value != 0) {
                    g.drawImage(images.shelf_0,
                            (int) position.x, (int) position.y - cell_height * 2,
                            Graphics.TOP | Graphics.LEFT);
                } else {
                    g.drawImage(images.shelf_0_empty,
                            (int) position.x, (int) position.y - cell_height * 2,
                            Graphics.TOP | Graphics.LEFT);
                }
                break;
            case Types.FLOWERPOT:
                g.drawImage(images.flower_pot,
                            (int) position.x, (int) position.y - cell_height * 2,
                            Graphics.TOP | Graphics.LEFT);
                break;
        }
    }
}
