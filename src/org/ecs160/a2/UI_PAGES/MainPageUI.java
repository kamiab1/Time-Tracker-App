package org.ecs160.a2.UI_PAGES;

import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.codename1.components.MultiButton;

import java.util.Arrays;

import static com.codename1.ui.CN.*;

public class MainPageUI
{
    Form skeleton;
    MultiButton[] listOfTasks = {}; //Array of Buttons. Buttons will be task names here. Need to access database.
    public static MainPageUI mainPage = new MainPageUI();

    public void loadMainPageUI() {
        skeleton = new Form("Task List", new BorderLayout());

        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        skeleton.add(CENTER, list);

        listOfTasks = Arrays.copyOf(listOfTasks, listOfTasks.length + 7); // this line will not be needed once we have the database
        /*
        listOfTasks[listOfTasks.length - 7] = new MultiButton("Bug Check Again and Again");
        listOfTasks[listOfTasks.length - 6] = new MultiButton("Bug Check Again");
        listOfTasks[listOfTasks.length - 5] = new MultiButton("Bug Check");
        listOfTasks[listOfTasks.length - 4] = new MultiButton("Studying");
        listOfTasks[listOfTasks.length - 3] = new MultiButton("Testing");
        listOfTasks[listOfTasks.length - 2] = new MultiButton("Practicing");
        listOfTasks[listOfTasks.length - 1] = new MultiButton("Running"); */

        for (int j = 0; j < listOfTasks.length; ++j) {
            listOfTasks[j] = new MultiButton("Bug Testing " + j);
            list.addComponent(0,listOfTasks[j]);
            //listOfTasks[j].getAllStyles().setBgColor(14737632);
            listOfTasks[j].setWidth(skeleton.getWidth());
            listOfTasks[j].setY(skeleton.getHeight());
            String taskName = listOfTasks[j].getText();
            listOfTasks[j].addActionListener((e) -> editBtnPressed(taskName));
        }

        // Below is intended for the future when listOfTasks is compatible with the database of tasks.
        /* for (int i = 0; i < listOfTasks.length; ++i) {
            listOfTasks.getAllStyles().setFgColor(112);
            skeleton.add(listOfTasks[i]);
        } */


        skeleton.show();
    }

    public void editBtnPressed(String taskName) {
        // We'll use the taskName to ID which task we'll be editing.
        EditPageUI.editPage.loadEditPageUI(taskName);
    }
}
