package game_shop;

import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Admin
 */
class DialogItem {

    String text = "";
    int condition = Conditions.NULL;
    boolean switchFocus = false;

    DialogItem(String text, int condition, boolean switchFocus) {
        this.text = text;
        this.condition = condition;
        this.switchFocus = switchFocus;
    }
}

public class Dialog extends View {

    Font font;
    Vector items = new Vector();

    int n = 0;
    int itemIndex = 0;
    int index = 0;
    int current_line_index = 0;

    int screenWidth = 120;
    boolean canGoNext = true;

    float arrow_pos = 0;

    boolean switchFocus = false;

    Vector current_lines = new Vector();
    Vector lines = new Vector();

    public Dialog(GameCanvas gameCanvas, Font font, int screenWidth) {
        this.font = font;
        this.parent = gameCanvas;
        this.screenWidth = screenWidth - 69; 
        populate();
        jumpToNextItem();

    }

    void populate() {
        //Conditions are useless!
        items.addElement(new DialogItem(
                "Привет, я буду помогать тебе с магазином"
                + " (Нажми кнопку ОК чтобы продолжить)",
                Conditions.NULL, false));
        items.addElement(new DialogItem(
                "В нашем магазине, еще нет места, давай разрушим 2 стены.",
                Conditions.NULL, false));
        
        items.addElement(new DialogItem(
        "Зайди в меню строительства (левая кнопка действия)" +
                        ". Выбери разрушить стену и нажми кнопку ОК",
                Conditions.NULL, true));
        
        items.addElement(new DialogItem(
                "Отлично. Теперь построй полку и кассу",
                Conditions.NULL, true));
        
        items.addElement(new DialogItem(
                "Давай теперь объясню как это работает",
                Conditions.NULL, false));
        
        items.addElement(new DialogItem(
                "Первым делом нужно заполнить полку играми, это будет стоить " +
                        String.valueOf(Prices.game * 3) + "$" + //3 is shelf first level max value
                        ". Наведись на полку и нажми кнопку ОК", 
                Conditions.NULL, true));
        
         items.addElement(new DialogItem(
                "Затем посетитель берёт игру и встает в очередь (Число справо сверху). "
               + "Если очередь заполнена, то никто не будет брать игры" +
                ". Из этой очереди посетитель переходит на кассу", 
                Conditions.NULL, false));
         items.addElement(new DialogItem(
                "Для того чтобы обслужить посетителя, нажми кнопку ОК на кассе" +
                        ". Если посетитель будет ждать слишком долго, то он уйдет"
                 + " не заплатив", 
                Conditions.NULL, true));
          items.addElement(new DialogItem(
                "Хорошо! Думаю теперь ты готов к суровой жизни хозяина магазина. " +
                       "Ладно, я пойду. Пока набери 3000$",
                Conditions.NULL, true));

    }

    public void jumpToNextItem() {
        if (itemIndex < items.size() && canGoNext) {
            visible = true;
            current_lines.removeAllElements();
            DialogItem dialogItem = (DialogItem) items.elementAt(itemIndex);
            String text = dialogItem.text;
            lines = wrapText(text);
            this.switchFocus = dialogItem.switchFocus;

            for (int i = 0; i < lines.size(); i++) {
                current_lines.addElement("");
            }
            itemIndex++;
            current_line_index = 0;
            canGoNext = false;
            
            
            
            //canGoNext = true; jumpToNextItem(); //skip all dialog;
        }
    }

    void keyPressed(int keyCode) {
        if (hasFocus) {
            if (keyCode == Keys.KEY_CENTER) {
                if (current_line_index >= current_lines.size() - 1) {
                    if (switchFocus == true) {
                        if (canGoNext) {
                            parent.changeFocusTo(parent.world);
                            visible = false;
                        }
                    } else {
                        jumpToNextItem();
                    }
                }
            }
        }
    }

    Vector wrapText(String text) {
        Vector lines = new Vector();
        String line = "";
        int width = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            int char_width = font.charWidth(c);
            width += char_width;
            if (width >= screenWidth) {
                lines.addElement(line);
                System.out.println(line);
                System.out.println(width);

                width = 0;
                line = "";
            }
            line += c;
        }
        lines.addElement(line);

        return lines;
    }

    void update() {
        arrow_pos += 0.25;
        if (arrow_pos > 3) {
            arrow_pos = -3;
        }

        if (current_line_index < current_lines.size()) {
            String line = (String) lines.elementAt(current_line_index);
            String current_line = (String) current_lines.elementAt(current_line_index);
            current_lines.setElementAt(current_line + line.charAt(index), current_line_index);
            index++;

            if (index >= line.length()) {
                index = 0;
                current_line_index++;
            }

            if (current_line_index == current_lines.size()) {
                canGoNext = true;
            }

        }

    }

    void draw(Graphics g, Images images, Font font) {
        if (!visible) {
            return;
        }
        g.setColor(255, 255, 255);

        for (int i = 0; i < current_lines.size(); i++) {
            g.drawString((String) current_lines.elementAt(i),
                    images.fox.getWidth(), i * font.getHeight() + g.getClipHeight()
                    - font.getHeight() * current_lines.size() - 15,
                    Graphics.TOP | Graphics.LEFT);

        }

        if (canGoNext) {
            String lastLine = (String) current_lines.elementAt(
                    current_lines.size() - 1);

            g.drawImage(images.arrow_down,
                    font.charsWidth(lastLine.toCharArray(), 0, lastLine.length())
                    + images.fox.getWidth(),
                    (current_lines.size() - 1) * font.getHeight() + g.getClipHeight()
                    - font.getHeight() * current_lines.size() - 15 + (int) arrow_pos,
                    Graphics.TOP | Graphics.LEFT);
        }
        
        
         g.drawImage(images.fox,
                   0,
                   g.getClipHeight() - images.fox.getHeight(),
                    Graphics.TOP | Graphics.LEFT);

    }

}
