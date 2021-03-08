package org.ecs160.a2.Custom_UI;
import com.codename1.ui.Button;

public class CustomButton extends Button {
    private final Button button;

    public CustomButton(String name) {
        button = new Button(name);
    }

    public Button getButton() {
        return button;
    }
}
