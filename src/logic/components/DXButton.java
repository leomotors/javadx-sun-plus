package logic.components;

import constant.Config;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class DXButton extends Button {
    public DXButton(String text) {
        super(text);

        // TODO No magic string
        this.setFont(new Font(Config.UI_FONT, 36));
    }
}
