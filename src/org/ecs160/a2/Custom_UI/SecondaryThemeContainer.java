package org.ecs160.a2.Custom_UI;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.Layout;
import org.ecs160.a2.Theme.CustomTheme;

public class SecondaryThemeContainer extends Container
{
    public SecondaryThemeContainer(Layout layout, Form form)
    {
        super(layout);
        super.getStyle().setBgColor(CustomTheme.SecondaryThemeColor);
        super.getStyle().setBgTransparency(255);
        super.setWidth(form.getWidth());
        super.setY(form.getHeight());
    }
}