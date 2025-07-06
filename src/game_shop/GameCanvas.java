package game_shop;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.lcdui.Graphics;
import java.util.Random;
import java.util.Vector;
import java.io.*;
import java.util.Hashtable;

public class GameCanvas extends javax.microedition.lcdui.game.GameCanvas {
    private Main main;

    private int last_clicked = 0;
    private Random random;
    private Images images;

    int menu_selected_index = 0;
    int max_menu_index = 1;
    Vector2d cursor_position_cell = new Vector2d(0, 0);

    Vector cells = new Vector();
    Hashtable cells_map = new Hashtable();
    String selected_cell_type = "null";
    //private Cell[] cells;

    boolean isWindowOpened = false;

    Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);

    public GameCanvas(Main main) {
        super(false);
        setFullScreenMode(true);
        this.main = main;
        main.initGame();
        random = new Random();
        images = new Images();

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
        //obstacle.move();
        // Проверка на столкновение
        // if (obstacle.checkCollision(game.getPlayer())) {
        //game.getPlayer().gameOver();

    }

    protected void keyPressed(int keyCode) {
        last_clicked = keyCode;
        if (keyCode == -4) { //right
            if (!isWindowOpened) {
                cursor_position_cell.x += 1;
                cursor_position_cell.y += 1;
                getCellType();
            }

        }
        if (keyCode == -3) {  // left
            if (!isWindowOpened) {
                cursor_position_cell.y -= 1;
                cursor_position_cell.x -= 1;
                getCellType();
            }
        }

        if (keyCode == -1) {  //up
            if (!isWindowOpened) {
                cursor_position_cell.y -= 1;
                cursor_position_cell.x += 1;
                getCellType();
            } else {
                if (menu_selected_index != 0) {
                    menu_selected_index -= 1;
                }
            }
        }

        if (keyCode == -2) {  //down
            if (!isWindowOpened) {
                cursor_position_cell.y += 1;
                cursor_position_cell.x -= 1;
                getCellType();
            } else {
                if (max_menu_index != menu_selected_index) {
                    menu_selected_index += 1;
                }
            }
        }

        if (keyCode == -6) { //1 UP 0
            if (!isWindowOpened) {
                isWindowOpened = true;
                menu_selected_index = 0;
            }
        }

        if (keyCode == -5) { //center
            if (isWindowOpened) {
                if (menu_selected_index == 0) {
                    isWindowOpened = false;

                }
                if (selected_cell_type.equals("null")) {
                    if (menu_selected_index == 1) {
                        if (hasEnoughMoney(100)) {
                            isWindowOpened = false;
                            //game.money -= 100;
                            createCell(cursor_position_cell);
                        }
                    }
                }

            }
        }
    }

    public void getCellType() {
        int pos_x = (int) cursor_position_cell.x * 24;
        int pos_y = (int) cursor_position_cell.y * 12;
        String a = String.valueOf(pos_x) + "|" + String.valueOf(pos_y);

        System.out.println(a);

        if (!cells_map.containsKey(a)) {
            selected_cell_type = "null";
        } else {
            selected_cell_type = "floor";

        }

    }

    public boolean hasEnoughMoney(int money) {

        return true;
    }

    public void createCell(Vector2d pos) {
        Cell cell = new Cell();
        cell.position.x = pos.x * 24;
        cell.position.y = pos.y * 12;
        cells.addElement(cell);
        System.out.println(String.valueOf((int) pos.x * 24) + "|"
                + String.valueOf((int) pos.y * 12));
        cells_map.put(String.valueOf((int) pos.x * 24) + "|"
                + String.valueOf((int) pos.y * 12), cell);
        getCellType();
    }

    public void updateGame() {

    }

    public void paint(Graphics g) {
        g.setColor(0x000000);  // Белый цвет
        g.fillRect(0, 0, getWidth(), getHeight());

        //g.drawImage(image, 0, horo_y, Graphics.TOP | Graphics.LEFT);
        //g.drawImage(image, 0, horo_y+image.getHeight(), Graphics.TOP | Graphics.LEFT);
        // g.drawImage(image, 0, horo_y-image.getHeight(), Graphics.TOP | Graphics.LEFT);
        //g.setColor(0xFF0000);
        //g.fillRect(game.getPlayer().getX(), game.getPlayer().getY(), game.getPlayer().getWidth(), game.getPlayer().getHeight());
        //g.setColor(0x00FF00);
        //Obstacle[] obstacles = game.getObstacles();
        if (isWindowOpened == false) {
            for (int i = 0; i < cells.size(); i++) {
                Cell cell = (Cell) cells.elementAt(i);
                g.drawImage(images.cell_0, (int) cell.position.x, (int) cell.position.y,
                        Graphics.TOP | Graphics.LEFT);
                //g.fillRect(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());

            }

            g.drawImage(images.cell_selector, (int) cursor_position_cell.x * 24,
                    (int) cursor_position_cell.y * 12,
                    Graphics.TOP | Graphics.LEFT);
        } else {
            String show_name = "";

            if (selected_cell_type.equals("null")) {
                show_name = "Пустота";
                max_menu_index = 1;
            }
            if (selected_cell_type.equals("floor")) {
                show_name = "Свободное пространство";
                max_menu_index = 5;
            }

            g.setFont(font);
            g.setColor(255, 255, 255);
            g.drawString(show_name,
                    0, 20, Graphics.TOP | Graphics.LEFT);

            g.drawImage(images.arrow_right, 0,
                    60 + menu_selected_index * 20,
                    Graphics.TOP | Graphics.LEFT);
            g.setColor(0, 255, 0);
            g.drawRect(0, 60 + menu_selected_index * 20, getWidth(), 20);

            g.setFont(font);
            g.setColor(0, 255, 0);
            g.drawString("Назад",
                    8, 60, Graphics.TOP | Graphics.LEFT);

            if ("Пустота".equals(show_name)) {//?????
                if (hasEnoughMoney(100)) {
                    g.setColor(0, 255, 0);
                } else {
                    g.setColor(255, 0, 0);
                }

                g.drawString("Расширить $100",
                        8, 80, Graphics.TOP | Graphics.LEFT);
            }

        }
        g.setColor(0, 0, 0);
        g.fillRect(UP, UP, getWidth(), 20);
        g.setFont(font);
        g.setColor(255, 255, 0);
        //g.drawString("Money: " + String.valueOf(game.money) + "$",
        //0, 0, Graphics.TOP | Graphics.LEFT);     
        g.drawString(String.valueOf(last_clicked),
                10, 100, Graphics.TOP | Graphics.LEFT);

        g.setColor(0, 0, 0);

    }

}
