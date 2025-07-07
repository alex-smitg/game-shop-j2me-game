
package game_shop;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;





public class FloatingText {
    Vector2d start_position = new Vector2d(0,0);
    int liveTime = 100;
    String text = "";
    int[] color = {255, 255, 255};
    Vector2d position = new Vector2d(0,0);
    
    FloatingText(Vector2d start_position, int liveTime, String text, int[] color) {
        this.start_position = start_position;
        this.position.x = start_position.x;
        this.position.y = start_position.y;
        this.liveTime = liveTime;
        this.text = text;
        this.color[0] = color[0];
        this.color[1] = color[1];
        this.color[2] = color[2];
    }
    
    
    
    void draw(Graphics g, Font font, Vector2d cameraPosition) {
        g.setColor(color[0], color[1], color[2]);
        g.drawString(text,
                (int)( position.x - cameraPosition.x),
                (int) (position.y - cameraPosition.y),
                Graphics.TOP | Graphics.LEFT);
    }
    
    void update() {
        liveTime -= 1;
        position.y -= 0.3;
    }
}
