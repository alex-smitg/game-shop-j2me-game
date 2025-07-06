package game_shop;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.lcdui.Graphics;
import java.util.Random;
import java.util.Vector;
import java.io.*;
import java.util.Hashtable;

public class GameCanvas extends javax.microedition.lcdui.game.GameCanvas {

    private final Main main;
    private Images images = new Images();
    
    private int last_clicked = 0;

    
   
    Menu buildMenu = new Menu();
    
    void populateMenu () {
        buildMenu.addItem(
                new MenuItem("Back")
        );
        buildMenu.addItem(
                new MenuItem("Expand")
        );
        buildMenu.addItem(
                new MenuItem("What")
        );
        buildMenu.addItem(
                new MenuItem("Omg")
        );
    }
    
    World world = new World(getWidth(), getHeight());
    
    
    
    Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);

    public GameCanvas(Main main) {
        super(false);
        setFullScreenMode(true);
        this.main = main;
        main.initGame();
        world.hasFocus = false;
        buildMenu.hasFocus = true;
        
        populateMenu();
    }

    protected void keyPressed(int keyCode) {
        last_clicked = keyCode;
        world.keyPressed(keyCode);
        buildMenu.keyPressed(keyCode);
    }

    


    public void updateGame() {
        world.update();
    }

    public void paint(Graphics g) {
        g.setColor(0x000000);
        g.fillRect(0, 0, getWidth(), getHeight());

        world.draw(g, images);

        

        g.setColor(0, 0, 0);
        g.fillRect(UP, UP, getWidth(), 20);
        g.setFont(font);
        g.setColor(255, 255, 0);
        //g.drawString("Money: " + String.valueOf(game.money) + "$",
        //0, 0, Graphics.TOP | Graphics.LEFT);     
        g.drawString(String.valueOf(last_clicked),
                10, 100, Graphics.TOP | Graphics.LEFT);

        g.setColor(0, 0, 0);
        
        buildMenu.draw(g, images);

    }

}
