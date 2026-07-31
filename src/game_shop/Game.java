package game_shop;

import javax.microedition.lcdui.*;

public class Game extends javax.microedition.lcdui.game.GameCanvas {

    private final Images images = new Images();

    final int MAP_SIZE = 32;
    final int TILE_SIZE = images.cell.getWidth();

    float cameraX = 0;
    float cameraY = 0;

    int cameraTargetX = 0;
    int cameraTargetY = 0;

    int cursorX = MAP_SIZE / 2 - 1;
    int cursorY = MAP_SIZE / 2 - 1;

    int screenWidth = 0;
    int screenHeight = 0;

    final int[][] mapArray = new int[MAP_SIZE][MAP_SIZE];

    void drawCell() {

    }

    //Returns x
    int transformIsometricX(int x, int y) {
        return (x - y) * (TILE_SIZE / 2);
    }

    //Returns y
    int transformIsometricY(int x, int y) {
        return (x + y) * (TILE_SIZE / 4);
    }

    //Returns x
    int transformCameraIsometricX(int x, int y) {
        return (x - y) * (TILE_SIZE / 2) - (int) cameraX;
    }

    //Returns y
    int transformCameraIsometricY(int x, int y) {
        return (x + y) * (TILE_SIZE / 4) - (int) cameraY;
    }

    protected void keyPressed(int keyCode) {
        if (keyCode == Keys.KEY_RIGHT) {
            if (cursorX != MAP_SIZE - 2) {
                cursorX += 1;
            }
        }

        if (keyCode == Keys.KEY_LEFT) {
            if (cursorX != 1) {
                cursorX -= 1;
            }
        }

        if (keyCode == Keys.KEY_UP) {
            if (cursorY != 1) {
                cursorY -= 1;
            }
        }

        if (keyCode == Keys.KEY_DOWN) {
            if (cursorY != MAP_SIZE - 2) {
                cursorY += 1;
            }
        }
        
        if (keyCode == Keys.KEY_CENTER) {
            mapArray[cursorX][cursorY] = 1;
        }
    }

    void initGame() {
        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                if (x == 0 || y == 0 || x == MAP_SIZE - 1 || y == MAP_SIZE - 1) {
                    mapArray[x][y] = 2;
                } else {
                    mapArray[x][y] = 0;
                }

            }
        }
    }

    public Game() {
        super(false);
        setFullScreenMode(true);
        screenWidth = getWidth();
        screenHeight = getHeight();
        initGame();
    }

    public void paint(Graphics g) {
        g.setColor(0x000000);
        g.fillRect(0, 0, getWidth(), getHeight());

        cameraX += (cameraTargetX - cameraX - g.getClipWidth() / 2 + TILE_SIZE / 2) / 5.0;
        cameraY += (cameraTargetY - cameraY - g.getClipHeight() / 2 + TILE_SIZE / 4) / 5.0;

        cameraTargetX = transformIsometricX(cursorX, cursorY);
        cameraTargetY = transformIsometricY(cursorX, cursorY);

        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                int tileScreenX = transformCameraIsometricX(x, y);
                int tileScreenY = transformCameraIsometricY(x, y);

                if (-TILE_SIZE < tileScreenX) {
                    if (-TILE_SIZE < tileScreenY) {
                        if (tileScreenX < screenWidth + TILE_SIZE) {
                            if (tileScreenY < screenHeight + TILE_SIZE) {
                                if (mapArray[x][y] == 1) {
                                    g.drawImage(images.cell,
                                            tileScreenX,
                                            tileScreenY,
                                            Graphics.TOP | Graphics.LEFT);
                                }
                                if (mapArray[x][y] == 2) {
                                    g.drawImage(images.tileBorder,
                                            tileScreenX,
                                            tileScreenY,
                                            Graphics.TOP | Graphics.LEFT);
                                }
                            }
                        }
                    }
                }
            }
        }

        g.drawImage(images.cursor,
                transformCameraIsometricX(cursorX, cursorY),
                transformCameraIsometricY(cursorX, cursorY),
                Graphics.TOP | Graphics.LEFT);
    }
}
