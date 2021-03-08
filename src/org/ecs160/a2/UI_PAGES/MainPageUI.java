package org.ecs160.a2.UI_PAGES;

import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.components.MultiButton;
import com.codename1.ui.events.ActionEvent;
import org.ecs160.a2.Storage.Store;
import org.ecs160.a2.model.Task;

import java.util.Arrays;
import java.util.List;

import static com.codename1.ui.CN.*;

public class MainPageUI
{
    Store store = new Store();
    Form skeleton;
    private Resources theme;
    private Form current;
    public static MainPageUI mainPage = new MainPageUI();

    public void initUI(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }

    public void loadMainPageUI() {
        if(current != null){
            current.show();
            return;
        }

        skeleton = new Form("Task List", new BorderLayout());
        Command createTask = new Command("Create Task") {
            public void actionPerformed(ActionEvent e) {
                CreateTaskPageUI.createTaskPage.startUI();
            }
        };

        skeleton.getToolbar().addCommandToRightBar(createTask);

        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        skeleton.add(CENTER, list);

        List<Task> taskList = store.getAllTasks();
        Container[] listOfTasks = {}; //Array of Buttons. Buttons will be task names here. Need to access database.
        listOfTasks = Arrays.copyOf(listOfTasks, listOfTasks.length + taskList.size()); // Copies same size as database.

//        for (Container taskContainer : listOfTasks ) {
//            taskContainer
//            taskContainer = new Container(BoxLayout.x());
//            taskContainer.setWidth(skeleton.getWidth());
//            taskContainer.setY(skeleton.getHeight());
//
//            MultiButton taskButton = new MultiButton(taskName);
//            taskButton.addActionListener((e)->infoBtnPressed(task));
//            taskButton.setWidth(skeleton.getWidth());
//
//            list.addComponent(0,taskContainer);
//        }

        for (int j = 0; j < listOfTasks.length; ++j) {
            Task task = taskList.get(j);

            listOfTasks[j] = new Container(BoxLayout.x());
            listOfTasks[j].setWidth(skeleton.getWidth());
            listOfTasks[j].setY(skeleton.getHeight());

            MultiButton taskButton = new MultiButton(task.name);
            taskButton.addActionListener((e)->infoBtnPressed(task));
            taskButton.setWidth(skeleton.getWidth());

            listOfTasks[j].addComponent(taskButton);
            list.addComponent(0,listOfTasks[j]);
            //listOfTasks[j].getAllStyles().setBgColor(14737632);
        }

        // Random line to push again, because of some github problems?
        // Random line to push again, because of some github problems? AGAIN
        // Testing github commit issues not detecting my commits on the graph.

        skeleton.show();
    }

    public void infoBtnPressed(Task task) {
        InfoPageUI.infoPage.startUI(task);
    }

    public void stopUI() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroyUI() {

    }
}
