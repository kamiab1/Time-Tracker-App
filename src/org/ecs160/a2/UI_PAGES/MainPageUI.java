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

import java.util.List;

import static com.codename1.ui.CN.*;

public class MainPageUI
{
    public static MainPageUI mainPage = new MainPageUI();

    private final Store store = new Store();
    private Form scaffold;
    private Resources theme;

    public void initUI(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);
    }

    public void startUI() {
        // NOT SURE NEEDS MORE TESTS
        if(scaffold != null){
            scaffold.show();
            return;
        }

        setUpLayout();
        toolBarSetup();
        Container taskListView = createListViewContainer();

        List<Task> taskList = getAllTasks();

        // this shows the list of all tasks
        taskList.forEach( eachTask -> {
            Container taskContainer = makeTaskContainer();
            MultiButton taskButton = makeTaskBtn(eachTask);

            taskContainer.addComponent(taskButton);
            // TODO: Add more components

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

    private void setUpLayout() {
        scaffold = new Form("Task List", new BorderLayout());
    }

    private void toolBarSetup() {
        Command createTaskCommand = new Command("Create Task") {
            public void actionPerformed(ActionEvent e) {
                CreateTaskPageUI.createTaskPage.startUI();
            }
        };
        scaffold.getToolbar().addCommandToRightBar(createTaskCommand);
    }

    private Container createListViewContainer() {
        Container taskListView = new Container(BoxLayout.y());
        taskListView.setScrollableY(true);
        scaffold.add(CENTER, taskListView);
        return taskListView;
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
