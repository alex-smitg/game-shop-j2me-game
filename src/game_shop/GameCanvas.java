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

    ActionsMenu actionsMenu = new ActionsMenu(this);

    World world = new World(getWidth(), getHeight(), this);

    Vector views = new Vector();

    Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    long startTime = System.currentTimeMillis();
    long currentTime = startTime;

    Dialog dialog = new Dialog(this, font, getWidth());

    public GameCanvas(Main main) {
        super(false);
        setFullScreenMode(true);
        this.main = main;
        main.initGame();

        dialog.hasFocus = true;
        world.hasFocus = false;
        actionsMenu.visible = false;

        //DRAWING ORDER
        views.addElement(world);
        views.addElement(actionsMenu);
        views.addElement(dialog);
    }

    void changeFocusTo(View view) {
        for (int i = 0; i < views.size(); i++) {
            View _view = (View) views.elementAt(i);
            _view.hasFocus = false;
        }
        view.hasFocus = true;
    }

    protected void keyPressed(int keyCode) {
        last_clicked = keyCode;

        for (int i = 0; i < views.size(); i++) {
            View view = (View) views.elementAt(i);
            view.keyPressed(keyCode);
        }

    }

    public void updateGame() {
        currentTime = System.currentTimeMillis();
        for (int i = 0; i < views.size(); i++) {
            View view = (View) views.elementAt(i);
            view.update();
        }
    }

    public void paint(Graphics g) {
        g.setColor(0x000000);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(font);
        
        

        for (int i = 0; i < views.size(); i++) {
            View view = (View) views.elementAt(i);
            view.draw(g, images, font);
        }
        
        
        

        g.setColor(0, 0, 0);
        g.fillRect(UP, UP, getWidth(), 20);

        g.setColor(255, 255, 0);
        g.drawString(String.valueOf(world.money) + "$",
                0, 0,
                Graphics.TOP | Graphics.LEFT);

        g.setColor(255, 255, 255);

        String text = String.valueOf(world.current_clients) + "/"
                + String.valueOf(world.maxClients);

        g.drawString(text, getWidth()
                - font.charsWidth(text.toCharArray(),
                        0, text.length()), 0,
                Graphics.TOP | Graphics.LEFT);

        
        g.setColor(0, 100, 255);

        g.drawString(String.valueOf((currentTime-startTime)/1000),
                getWidth() / 2, 0,
                Graphics.TOP | Graphics.LEFT);
        //g.drawString(String.valueOf(last_clicked),
        // 10, 100, Graphics.TOP | Graphics.LEFT);
        g.setColor(0, 0, 0);
        
        

    }

}
