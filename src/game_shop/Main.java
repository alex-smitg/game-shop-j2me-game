package game_shop;

import javax.microedition.lcdui.Display;

public class Main extends javax.microedition.midlet.MIDlet implements Runnable {

    public Display display;
    public final Game game;
    public Thread gameThread;
    public boolean running = false;

    public Main() {
        game = new Game();
    }

    protected void startApp() {
        display = Display.getDisplay(this);
        display.setCurrent(game);
        gameThread = new Thread(this);
        running = true;
        gameThread.start();
    }

    public void run() {
        while (running) {
            game.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void pauseApp() {
        running = false;
    }

    protected void destroyApp(boolean unconditional) {
        running = false;
    }

}
