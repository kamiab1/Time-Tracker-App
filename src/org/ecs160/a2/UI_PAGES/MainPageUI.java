package org.ecs160.a2.UI_PAGES;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.components.MultiButton;
import com.codename1.ui.events.ActionEvent;
import org.ecs160.a2.Storage.Storage;
import org.ecs160.a2.Model.Task;
import java.util.List;
import static com.codename1.ui.CN.*;

public class MainPageUI
{
    public static MainPageUI mainPage = new MainPageUI();

    private final Storage storage = Storage.instanse();
    private Form scaffold;
    private Resources theme;

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

        setUpLayout();
        toolBarSetup();
        Container taskListView = createListViewContainer();
        List<Task> taskList = getAllTasks();

        // this shows the list of all tasks
        taskList.forEach( eachTask -> {

            Container taskContainer = makeTaskContainer(eachTask);
            MultiButton taskButton = makeTaskBtn(eachTask);
            Label activityText = makeActivityLabel(eachTask);

            taskContainer.addComponent(taskButton);
            taskContainer.addComponent(activityText);

            taskListView.addComponent(taskContainer);
        });

        scaffold.show();
    }




    /*************** General functions ****************/

    private List<Task> getAllTasks() {
        return storage.getAllTasks();
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

    private Container makeTaskContainer(Task eachTask) {
        Container container = new Container(BoxLayout.x());
        container.setUIID(eachTask.name);
        container.getStyle().setBgColor(0xfcba03);
        container.getStyle().setBgTransparency(255);

        container.setWidth(scaffold.getWidth());
        container.setY(scaffold.getHeight());

        return container;
    }


    private MultiButton makeTaskBtn(Task eachTask) {
        MultiButton taskButton = new MultiButton(eachTask.name);
        taskButton.setWidth(scaffold.getWidth());
        taskButton.addActionListener((e)-> goToInfoPage(eachTask));
        return taskButton;
    }


    private Label makeActivityLabel(Task eachTask) {

        Label activity = new Label(eachTask.isRunning);
        activity.setUIID(eachTask.name + "activity");
        activity.getStyle().setBgTransparency(255, true);

        if (eachTask.getIsRunning()) {
            activity.setText("Active");
            activity.getStyle().setBgColor(0x00bf13);
        }
        else {
            activity.setText("Inactive");
            activity.getStyle().setBgColor(0xbf0026);
        }

        return activity;
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
