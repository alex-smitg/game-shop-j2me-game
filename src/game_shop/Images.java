package game_shop;

import java.io.IOException;
import javax.microedition.lcdui.Image;

public class Images {

    Image cell;
    Image cursor;
    Image tileBorder;
    
    Images() {
        try {
            this.cell = Image.createImage("/images/cell.png");
            this.cursor = Image.createImage("/images/cursor.png");
            this.tileBorder = Image.createImage("/images/tile_border.png");
            System.out.println("Images: OK");
        } catch (IOException e) {
            System.out.println("Images: ERROR");
            e.printStackTrace();
        }
    }
}
