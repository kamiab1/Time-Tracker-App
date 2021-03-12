package org.ecs160.a2.UI_PAGES;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import org.ecs160.a2.Storage.Storage;
import org.ecs160.a2.Model.Task;
import org.ecs160.a2.Theme.CustomTheme;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import static com.codename1.ui.CN.getCurrentForm;


public class InfoPageUI {

    //TODO: This is used to style the page/groupings of data such as the Time
    // Analysis and the name/desc/size
    private class InfoPageContainer extends Container
    {
        InfoPageContainer()
        {
            super(BoxLayout.x());
            super.getStyle().setBgColor(0x6ec6ff);
            super.getStyle().setBgColor(0x002f6c);
            super.getStyle().setBgTransparency(255);
            super.setWidth(scaffold.getWidth());
            super.setY(scaffold.getHeight());
        }
    }

    private class LabelTextComponent extends Label
    {
        LabelTextComponent(String text)
        {
            super(text);
            super.getStyle().setFgColor(0x000000);
            super.getStyle().setFont(CustomTheme.smallBoldSystemFont);
        }
    }

    private class ValueTextComponent extends Label
    {
        ValueTextComponent(String text)
        {
            super(text);
            super.getStyle().setFgColor(0x000000);
            super.getStyle().setFont(CustomTheme.smallPlainSystemFont);
        }
    }

    public static InfoPageUI infoPage = new InfoPageUI();
    private final Storage storage = Storage.instanse();
    private Form scaffold;
    private final LabelTextComponent
            statusLabel = new LabelTextComponent("Status: ");
    private final ValueTextComponent statusValue = new ValueTextComponent("");
    private final LabelTextComponent nameLabel = new LabelTextComponent("Name: ");
    private final ValueTextComponent nameValue = new ValueTextComponent("");
    private final LabelTextComponent
            descriptionLabel = new LabelTextComponent("Description: ");
    private final ValueTextComponent
            descriptionValue = new ValueTextComponent("");
    private final LabelTextComponent sizeLabel = new LabelTextComponent("Size: ");
    private final ValueTextComponent sizeValue = new ValueTextComponent("");
    private final LabelTextComponent
            taskDataLabel = new LabelTextComponent("Time Analysis");
    private final LabelTextComponent
            minTimeLabel = new LabelTextComponent("Min Time: ");
    private final ValueTextComponent minTimeValue = new ValueTextComponent("");
    private final LabelTextComponent
            maxTimeLabel = new LabelTextComponent("Max Time: ");
    private final ValueTextComponent maxTimeValue = new ValueTextComponent("");
    private final LabelTextComponent
            avgTimeLabel = new LabelTextComponent("Avg Time: ");
    private final ValueTextComponent avgTimeValue = new ValueTextComponent("");
    private final Button startButton = new Button("Start");
    private final Button stopButton = new Button("Stop");
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

        scaffold = new Form("Summary", new BorderLayout());

        initLayout();
        initButtonListeners();

        Command editCommand = new Command("Edit")
        {
            public void actionPerformed(ActionEvent e) {
                EditPageUI.editPage.startUI(currentTask);
            }
        };

        Command deleteAction = new Command("Delete")
        {
            public void actionPerformed(ActionEvent e) {
                deleteCurrentTask();
            }
        };

        ToolbarInitializer.initBackButton(scaffold, (b) -> loadPreviousPage());
        ToolbarInitializer.addTopRightAction(scaffold, editCommand);
        ToolbarInitializer.addTopRightAction(scaffold, deleteAction);

        initData();
        scaffold.show();
    }


    /*************** UI functions ****************/

    public void initLayout()
    {

        //"Current Task: " + currentTask.name, new BorderLayout()
        TableLayout tl = new TableLayout(11, 1);
        tl.setGrowHorizontally(true);
        scaffold.setLayout(tl);

        Container taskInfoContainer = createTaskInfoContainer();
        Container taskDataContainer = createTaskDataContainer();
        Container startStopContainer = createStartStopContainer();

        taskDataLabel.getStyle().setFont(CustomTheme.mediumBoldSystemFont);
        statusValue.getStyle().setFont(CustomTheme.mediumBoldSystemFont);

        scaffold.
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.RIGHT), statusValue).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.LEFT), taskInfoContainer).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.CENTER), taskDataLabel).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.LEFT), taskDataContainer).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.CENTER), startStopContainer);
    }

    private Container createTaskInfoContainer()
    {
        Container taskInfoContainer = new Container(BoxLayout.y());
        taskInfoContainer.add(nameLabel);
        taskInfoContainer.add(nameValue);
        taskInfoContainer.add(descriptionLabel);
        taskInfoContainer.add(descriptionValue);
        taskInfoContainer.add(sizeLabel);
        taskInfoContainer.add(sizeValue);

        return taskInfoContainer;
    }

    private Container createTaskDataContainer()
    {
        Container taskDataContainer = new Container(BoxLayout.y());
        taskDataContainer.add(minTimeLabel);
        taskDataContainer.add(minTimeValue);
        taskDataContainer.add(maxTimeLabel);
        taskDataContainer.add(maxTimeValue);
        taskDataContainer.add(avgTimeLabel);
        taskDataContainer.add(avgTimeValue);

        return taskDataContainer;
    }

    private Container createStartStopContainer()
    {
        Container startStopButtonContainer = new Container(BoxLayout.x());
        startStopButtonContainer.add(startButton);
        startStopButtonContainer.add(stopButton);

        return startStopButtonContainer;
    }


    private void initButtonListeners() {
        startButton.addActionListener((e) -> startCurrentTask());
        stopButton.addActionListener((e) -> stopCurrentTask());
    }



    /*************** General functions ****************/

    private void initData() {

        scaffold.setTitle("Summary");
        setAllText();
    }


    public void setAllText()
    {
        setStatusText();
        setNameText();
        setDescriptionText();
        setSizeText();
        setMinTimeValueText();
        setMaxTimeValueText();
        setAvgTimeValueText();
    }

    public void setStatusText()
    {
        if (currentTask.getIsRunning())
        {
            statusValue.setText("Active");
            statusValue.getStyle().setFgColor(0x00bf13);
        }
        else
        {
            statusValue.setText("Inactive");
            statusValue.getStyle().setFgColor(0xbf0026);
        }
    }

    public void setNameText()
    {
        nameValue.setText(currentTask.name);
    }

    public void setDescriptionText()
    {
        descriptionValue.setText(currentTask.description);
    }

    public void setSizeText()
    {
        sizeValue.setText(currentTask.size);
    }

    public void setMinTimeValueText()
    {
        minTimeValue.setText(durationToTimePassed(currentTask.getMinDuration()));
    }

    public void setMaxTimeValueText()
    {
        maxTimeValue.setText(durationToTimePassed(currentTask.getMaxDuration()));
    }

    public void setAvgTimeValueText()
    {
        avgTimeValue.setText(durationToTimePassed(currentTask.getAvgDuration()));
    }




    private String durationToTimePassed(Date time){
        DateFormat format = new SimpleDateFormat("hh:mm:ss");
        return format.format(time);
    }

    private void loadPreviousPage() {
        MainPageUI.mainPage.startUI();
        stopUI();
    }


    /*Task Actions*/
    private void startCurrentTask() {

        storage.startTask(currentTask);
    }
    private void stopCurrentTask() {
        storage.stopTask(currentTask);
    }
    private void deleteCurrentTask() {
        storage.deleteTask(currentTask);
        loadPreviousPage();
    }


    /*************** Clean up ****************/

    public void stopUI() {
        scaffold = getCurrentForm();
        if(scaffold instanceof Dialog) {
            ((Dialog) scaffold).dispose();
            scaffold = getCurrentForm();
        }
    }
}
