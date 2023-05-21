package logic.components;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DXText extends Text {

    public DXText() {
        super();
        this.setFont(Font.loadFont(ClassLoader
                .getSystemResource("font/YuGothM.ttc").toString(),
                34));
    }

    public DXText(String text) {
        this();
        this.setText(text);
    }

    public DXText(Color color) {
        this();
        this.setFill(color);
    }

    public DXText(String text, Color color) {
        this(text);
        this.setFill(color);
    }

    public DXText(int number) {
        this(Integer.toString(number));
    }

    public void setFontSize(int fontSize) {
        this.setFont(Font.loadFont(ClassLoader
                .getSystemResource("font/YuGothM.ttc").toString(),
                fontSize - 3));
    }

    public void setText(int number) {
        super.setText(Integer.toString(number));
    }
}
