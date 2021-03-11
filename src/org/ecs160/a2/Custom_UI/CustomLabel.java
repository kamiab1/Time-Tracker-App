package org.ecs160.a2.Custom_UI;

import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Label;

public class CustomLabel {

    public Label createForFont( String s) {
        int fontSize = Display.getInstance().convertToPixels(3);
//        Font ttfFont = Font.createTrueTypeFont("Handlee", "Handlee-Regular.ttf").
//                derive(fontSize, Font.STYLE_PLAIN);

        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        Label l = new Label(s);
        l.getUnselectedStyle().setFont(fnt);
        return l;
    }

}
