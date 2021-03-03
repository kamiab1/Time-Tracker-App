package org.ecs160.a2.UI_PAGES;

import com.codename1.io.Log;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.updateNetworkThreadCount;

public class MainPageUI
{
    Form skeleton;

    public static MainPageUI mainPage = new MainPageUI();
    public void loadMainPageUI() {
        skeleton = new Form("Task List", new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));

        skeleton.show();
    }
}
