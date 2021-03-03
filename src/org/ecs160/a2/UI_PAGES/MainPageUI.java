package org.ecs160.a2.UI_PAGES;

import com.codename1.io.Log;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Label;

import java.util.Arrays;

import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.updateNetworkThreadCount;

public class MainPageUI
{
    Form skeleton;
    Label[] listOfTasks = {}; //Array of labels. Labels will be task names here.
    public static MainPageUI mainPage = new MainPageUI();

    public void loadMainPageUI() {
        skeleton = new Form("Task List", new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));

        TableLayout tl;
        int         spanButton = 2;
        if(Display.getInstance().isTablet()) {
            tl = new TableLayout(7, 2);
        } else {
            tl = new TableLayout(14, 1);
            spanButton = 1;
        }
        tl.setGrowHorizontally(true);
        skeleton.setLayout(tl);

        listOfTasks = Arrays.copyOf(listOfTasks, listOfTasks.length + 1);
        listOfTasks[listOfTasks.length - 1] = new Label("Studying");
        skeleton.add(listOfTasks[0]);

        // Below is intended for the future when listOfTasks is compatible with the database of tasks.
        /* for (int i = 0; i < listOfTasks.length; ++i) {
            skeleton.add(listOfTasks[i]);
        } */

        skeleton.show();
    }
}
