package org.ecs160.a2.UI_PAGES;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.table.TableLayout;

import org.ecs160.a2.Storage.Storage;
import org.ecs160.a2.Model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codename1.ui.CN.getCurrentForm;


public class InfoPageUI {

    public static InfoPageUI infoPage = new InfoPageUI();

    private final Storage storage = Storage.instanse();
    private Form scaffold;
    private final Label statusLabel = new Label("Status");
    private final Button startButton = new Button("Start");
    private final Button stopButton = new Button("Stop");
    private final Label descriptionLabel = new Label("Description");
    private final Label sizeLabel = new Label("Size");
    private final Label taskDataLabel = new Label("Task Data");
    private final Label minTimeLabel = new Label("Min Time: ");
    private final Label maxTimeLabel = new Label("Max Time: ");
    private final Label avgTimeLabel = new Label("Avg Time: ");
    private final Button deleteButton = new Button("Delete");
    private final Button editButton = new Button("Edit");
    private Task currentTask;


    public void startUI(Task task) {
        currentTask = task;
        System.out.print(" \n received data :" + currentTask.name);
        if(scaffold != null){
            initData(); // needs testing
            scaffold.show();
            return;
        }
        scaffold = new Form("Current Task: " + currentTask.name, new BorderLayout());

        setUpLayout();
        setUpButtons();
        initBackButton();
        initData();
        scaffold.show();
    }





    /*************** UI functions ****************/

    public void setUpLayout()
    {

        //"Current Task: " + currentTask.name, new BorderLayout()
        TableLayout tl = new TableLayout(11, 2);
        tl.setGrowHorizontally(true);
        scaffold.setLayout(tl);

        scaffold.
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), statusLabel).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.LEFT), startButton).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.LEFT), stopButton).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), descriptionLabel).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), sizeLabel).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), new Label(" ")).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), taskDataLabel).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), minTimeLabel).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), maxTimeLabel).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), avgTimeLabel).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.LEFT), deleteButton).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.LEFT), editButton);
    }


    private void setUpButtons() {
        editButton.addActionListener((e) -> EditPageUI.editPage.startUI(currentTask));
        deleteButton.addActionListener((e) -> deleteThisTask());
        startButton.addActionListener((e) -> startThisTask());
        stopButton.addActionListener((e) -> stopThisTask());

    }



    /*************** General functions ****************/

    private void initData() {
        System.out.print(" \n called to show data \n ");
        statusLabel.setText("Activity status: " + currentTask.isRunning);
        descriptionLabel.setText("Description: "+ currentTask.description);
        sizeLabel.setText("size: "+ currentTask.size);

        minTimeLabel.setText("Min time: "+ durationToTimePassed(currentTask.getMinDuration()));
        maxTimeLabel.setText("Max time: "+ durationToTimePassed(currentTask.getMaxDuration()));
        avgTimeLabel.setText("Avg time: "+ durationToTimePassed(currentTask.getAvgDuration()));
    }

    private String durationToTimePassed(Date time){
        DateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(time);
    }

    private void goBack() {
        MainPageUI.mainPage.startUI();
        stopUI();
    }

    private void stopThisTask() {
        storage.stopTask(currentTask);
    }

    private void startThisTask() {
        storage.startTask(currentTask);
    }

    private void deleteThisTask() {
        storage.deleteTask(currentTask);
        goBack();
    }


    /*************** Clean up ****************/

    public void stopUI() {
        scaffold = getCurrentForm();
        if(scaffold instanceof Dialog) {
            ((Dialog) scaffold).dispose();
            scaffold = getCurrentForm();
        }
    }

    /*TODO: Abstract this method into ToolBarUI.
    We need to find out how to pass in the back() function as an argument in
    order to abstract this.
     */
    public void initBackButton()
    {
        Command backCommand = new Command("Back")
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                goBack();
            }
        };

        Toolbar toolbar = scaffold.getToolbar();
        toolbar.setBackCommand(backCommand);
    }
}
