/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_shop;

import java.io.IOException;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Admin
 */
public class Images {

    Image cell_0;
    Image cell_selector;
    Image arrow_right;

    Images() {
        try {
            this.cell_0 = Image.createImage("/images/cell.png");
            this.cell_selector = Image.createImage("/images/cursor.png");
            this.arrow_right = Image.createImage("/images/arrow.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
