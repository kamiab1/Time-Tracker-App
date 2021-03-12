package org.ecs160.a2;

import org.ecs160.a2.UI_PAGES.MainPageUI;

public class UI {
    MainPageUI mainPageUI = new MainPageUI();

    public void initUI(Object context) { mainPageUI.initUI(context); }

    public void startUI()
    {
        mainPageUI.startUI();
    }

    public void stopUI()
    {
        mainPageUI.stopUI();
    }

    public void destroyUI()
    {
        mainPageUI.destroyUI();
    }
}
