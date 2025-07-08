package game_shop;

import java.io.IOException;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.io.InputStream;
import java.util.Random;
import javax.microedition.media.*;
import javax.microedition.media.control.*;

public class Main extends javax.microedition.midlet.MIDlet implements Runnable {

    private final GameCanvas gameCanvas;
    private Display display;
    private Thread gameThread;
    private boolean running;
    
    

    private javax.microedition.media.Player musicPlayer;

    public Main() {
        gameCanvas = new GameCanvas(this);
    }

    protected void startApp() {
        try {
            InputStream midiStream = getClass().getResourceAsStream("/music/main_theme.mid");

            if (midiStream == null) {
                System.out.println("Cant found");
                return;
            }

            musicPlayer = Manager.createPlayer(midiStream, "audio/midi");
            musicPlayer.realize();

            VolumeControl volumeControl = (VolumeControl) musicPlayer.getControl("VolumeControl");
            if (volumeControl != null) {
                volumeControl.setLevel(80);
            }
            musicPlayer.setLoopCount(-1);

            musicPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        display = Display.getDisplay(this);
        display.setCurrent(gameCanvas);
        gameThread = new Thread(this);
        running = true;
        gameThread.start();
    }

    protected void pauseApp() {
        running = false;

        try {
            if (musicPlayer != null) {
                musicPlayer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void destroyApp(boolean unconditional) {
        running = false;

        try {
            if (musicPlayer != null) {
                musicPlayer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (running) {
            gameCanvas.updateGame();
            gameCanvas.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void initGame() {
    }

    public Display getDisplay() {
        return display;
    }
}
