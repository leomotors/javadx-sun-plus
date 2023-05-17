package logic.components;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DXText extends Text {
    public DXText() {
        super();
        this.setFont(new Font("Helvetica", 36));
    }

    public DXText(String text) {
        this();
        this.setText(text);
    }

    public void setText(int number) {
        super.setText(Integer.toString(number));
    }
}
