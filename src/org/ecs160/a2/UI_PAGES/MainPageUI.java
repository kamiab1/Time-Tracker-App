package org.ecs160.a2.UI_PAGES;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.components.MultiButton;
import com.codename1.ui.events.ActionEvent;
import org.ecs160.a2.Storage.Store;
import org.ecs160.a2.model.Task;

import java.util.ArrayList;
import java.util.List;

import static com.codename1.ui.CN.*;

public class MainPageUI
{
    private Store store = new Store();
    private Form scaffold;
    private Resources theme;
    public static MainPageUI mainPage = new MainPageUI();

    public void initUI(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);
    }

    public void startUI() {
        // NOT SURE NEEDS MORE TESTS
//        if(scaffold != null){
//            scaffold.show();
//            return;
//        }

        List<Task> taskList = getAllTasks();
        scaffold = new Form("Task List", new BorderLayout());
        toolBarSetup();
        Container taskListView = createListView();

        // show the list of all tasks
        taskList.forEach( eachTask -> {
            Container taskContainer = makeTaskContainer();
            MultiButton taskButton = makeTaskBtn(eachTask);

            taskContainer.addComponent(taskButton);
            taskListView.addComponent(taskContainer);
        });

        scaffold.show();
    }


    /*************** General functions ****************/

    private List<Task> getAllTasks() {
        return store.getAllTasks();
    }

    private void goToInfoPage(Task task) {
        System.out.print("pressed :" + task.name);
        InfoPageUI.infoPage.startUI(task);
    }


    /*************** UI functions ****************/

    private void toolBarSetup() {
        Command createTaskCommand = new Command("Create Task") {
            public void actionPerformed(ActionEvent e) {
                CreateTaskPageUI.createTaskPage.startUI();
            }
        };
        scaffold.getToolbar().addCommandToRightBar(createTaskCommand);
    }

    private Container createListView() {
        Container taskListView = new Container(BoxLayout.y());
        taskListView.setScrollableY(true);
        scaffold.add(CENTER, taskListView);
        return  taskListView;
    }

    private MultiButton makeTaskBtn(Task eachTask) {
        MultiButton taskButton = new MultiButton(eachTask.name);
        taskButton.setWidth(scaffold.getWidth());
        taskButton.addActionListener((e)-> goToInfoPage(eachTask));
        return taskButton;
    }

    private Container makeTaskContainer() {
        Container container = new Container(BoxLayout.x());
        container.setWidth(scaffold.getWidth());
        container.setY(scaffold.getHeight());
        return container;
    }



    /*************** Clean up ****************/

    public void stopUI() {
        scaffold = getCurrentForm();
        if(scaffold instanceof Dialog) {
            ((Dialog) scaffold).dispose();
            scaffold = getCurrentForm();
        }
    }

    public void destroyUI() {

    }
}
