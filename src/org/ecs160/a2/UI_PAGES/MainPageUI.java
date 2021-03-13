package org.ecs160.a2.UI_PAGES;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.events.ActionEvent;
import org.ecs160.a2.Storage.Storage;
import org.ecs160.a2.Model.Task;
import org.ecs160.a2.Theme.CustomTheme;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
            Button taskButton = makeTaskBtn(eachTask);
            Label activityLabel = makeActivityLabel(eachTask);
            Label durationLabel = makeDurationLabel(eachTask);

            taskContainer.setLeadComponent(taskButton);
            taskContainer.addComponent(taskButton);
            taskContainer.addComponent(durationLabel);
            taskContainer.addComponent(activityLabel);


            taskListView.addComponent(taskContainer);
        });

        scaffold.show();
    }




    /*************** General functions ****************/

    private List<Task> getAllTasks() {
        return storage.getAllTasks();
    }

    private void goToInfoPage(Task task) {
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
        container.getStyle().setBgColor(0xffffff);
        container.getStyle().setBgTransparency(255);

        container.setWidth(scaffold.getWidth());
        container.setY(scaffold.getHeight());

        return container;
    }


    private Button makeTaskBtn(Task eachTask) {
        Button taskButton = new Button(eachTask.name);
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
            activity.getStyle().setFgColor(CustomTheme.ActiveThemeColor);
            activity.getStyle().setFont(CustomTheme.smallBoldSystemFont);
        }
        else {
            activity.setText("Inactive");
            activity.getStyle().setFgColor(0xbf0026);
            activity.getStyle().setFont(CustomTheme.smallBoldSystemFont);
        }

        return activity;
    }

    private Label makeDurationLabel(Task eachTask) {

        Label duration = new Label("Duration: " + durationToTimePassed(eachTask.getTotalDuration()));
        duration.setUIID(eachTask.name + "duration");
        duration.getStyle().setBgTransparency(255, true);
        duration.getStyle().setFgColor(0x000000);
        duration.getStyle().setFont(CustomTheme.smallPlainSystemFont);
        return duration;
    }


    /*************** Helper ****************/

    private String durationToTimePassed(Date time){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(time.getTime());
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
