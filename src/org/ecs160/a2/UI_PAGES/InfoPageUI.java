package org.ecs160.a2.UI_PAGES;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import org.ecs160.a2.Custom_UI.PrimaryThemeContainer;
import org.ecs160.a2.Custom_UI.SecondaryThemeContainer;
import org.ecs160.a2.Storage.Storage;
import org.ecs160.a2.Model.Task;
import org.ecs160.a2.Theme.CustomTheme;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


import static com.codename1.ui.CN.getCurrentForm;


public class InfoPageUI {



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
    private Task currentTask;


    public void startUI(Task task) {
        currentTask = task;
        if(scaffold != null){
            initData(); // needs testing
            scaffold.show();
            return;
        }

        scaffold = new Form("Summary", new BorderLayout());
        scaffold.getStyle().setBgColor(CustomTheme.UIPageColor);
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
        BoxLayout bl = new BoxLayout((BoxLayout.Y_AXIS));
        scaffold.setLayout(bl);

        PrimaryThemeContainer statusValueContainer = createStatusContainer();
        PrimaryThemeContainer taskDataLabelContainer = createTaskDataLabelContainer();
        SecondaryThemeContainer taskInfoContainer = createTaskInfoContainer();
        SecondaryThemeContainer taskDataContainer = createTaskDataContainer();
        SecondaryThemeContainer startStopContainer = createStartStopContainer();

        taskDataLabel.getStyle().setFont(CustomTheme.mediumBoldSystemFont);
        statusValue.getStyle().setFont(CustomTheme.mediumBoldSystemFont);

        scaffold.
        add(statusValueContainer).
        add(taskInfoContainer).
        add(taskDataLabelContainer).
        add(taskDataContainer).
        add(startStopContainer);

    }

    private PrimaryThemeContainer createStatusContainer()
    {
        PrimaryThemeContainer statusContainer =
                new PrimaryThemeContainer(BoxLayout.xCenter(), scaffold);
        statusContainer.add(statusValue);
        return statusContainer;
    }

    private PrimaryThemeContainer createTaskDataLabelContainer()
    {
        PrimaryThemeContainer taskDataLabelContainer =
                new PrimaryThemeContainer(BoxLayout.xCenter(), scaffold);
        taskDataLabelContainer.add(taskDataLabel);
        return taskDataLabelContainer;
    }


    private SecondaryThemeContainer createTaskInfoContainer()
    {
        SecondaryThemeContainer taskInfoContainer =
                new SecondaryThemeContainer(BoxLayout.y(), scaffold);
        taskInfoContainer.add(nameLabel);
        taskInfoContainer.add(nameValue);
        taskInfoContainer.add(descriptionLabel);
        taskInfoContainer.add(descriptionValue);
        taskInfoContainer.add(sizeLabel);
        taskInfoContainer.add(sizeValue);

        return taskInfoContainer;
    }

    private SecondaryThemeContainer createTaskDataContainer()
    {
        SecondaryThemeContainer taskDataContainer =
                new SecondaryThemeContainer(BoxLayout.y(), scaffold);
        taskDataContainer.add(minTimeLabel);
        taskDataContainer.add(minTimeValue);
        taskDataContainer.add(maxTimeLabel);
        taskDataContainer.add(maxTimeValue);
        taskDataContainer.add(avgTimeLabel);
        taskDataContainer.add(avgTimeValue);

        return taskDataContainer;
    }

    private SecondaryThemeContainer createStartStopContainer()
    {
        SecondaryThemeContainer startStopButtonContainer =
                new SecondaryThemeContainer(BoxLayout.xCenter(), scaffold);
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

    public void updateStatusText()
    {
        setStatusText();
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
            statusValue.getStyle().setFgColor(CustomTheme.ActiveThemeColor);
        }
        else
        {
            statusValue.setText("Inactive");
            statusValue.getStyle().setFgColor(CustomTheme.InactiveThemeColor);
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
        SimpleDateFormat durationFormatter = new SimpleDateFormat("HH:mm:ss");
        durationFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return durationFormatter.format(time.getTime());
    }

    private void loadPreviousPage() {
        MainPageUI.mainPage.startUI();
        stopUI();
    }


    /*Task Actions*/
    private void startCurrentTask() {
        storage.startTask(currentTask);
        updateUI();
    }

    private void stopCurrentTask() {
        storage.stopTask(currentTask);
        updateUI();
    }
    private void deleteCurrentTask() {
        storage.deleteTask(currentTask);
        loadPreviousPage();
    }

    private void updateUI() {
        currentTask = storage.getTask(currentTask.name);
        setStatusText();
        setAllText();
        scaffold.show();
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
