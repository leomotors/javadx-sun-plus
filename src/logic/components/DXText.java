package logic.components;

import constant.Config;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DXText extends Text {
    public DXText() {
        super();
        this.setFont(new Font(Config.UI_FONT, 36));
    }

    public DXText(String text) {
        this();
        this.setText(text);
    }

    public DXText(int number) {
        this(Integer.toString(number));
    }

    public void setFontSize(int fontSize) {
        this.setFont(new Font(Config.UI_FONT, fontSize));
    }

    public void setText(int number) {
        super.setText(Integer.toString(number));
    }
}
