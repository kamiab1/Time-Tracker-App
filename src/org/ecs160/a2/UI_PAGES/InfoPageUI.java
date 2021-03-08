package org.ecs160.a2.UI_PAGES;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.table.TableLayout;
import org.ecs160.a2.model.Task;

import static com.codename1.ui.CN.getCurrentForm;


public class InfoPageUI {
    private Form skeleton;
    Button backButton = new Button("Back");
    Label statusLabel = new Label("Status");
    Button startButton = new Button("Start");
    Button stopButton = new Button("Stop");
    Label descriptionLabel = new Label("Description");
    Label sizeLabel = new Label("Size");
    Label taskDataLabel = new Label("Task Data");
    Label minTimeLabel = new Label("Min Time: ");
    Label maxTimeLabel = new Label("Max Time: ");
    Label avgTimeLabel = new Label("Avg Time: ");
    Button deleteButton = new Button("Delete");
    Button editButton = new Button("Edit");

    private Task currentTask;
    public static InfoPageUI infoPage = new InfoPageUI();

    public void startUI(Task task) {
        currentTask = task;
        System.out.print(" \n received data :" + currentTask.name);
        if(skeleton != null){
            initData();
            skeleton.show();
            return;
        }
        statusLabel.setText(" kir");
        initSummaryPage(task);
        initData();
        skeleton.show();
    }

    private void initData() {
        System.out.print(" \n called to show data \n ");
        statusLabel.setText("Activity status: " + currentTask.isRunning);
        descriptionLabel.setText("Description: "+ currentTask.description);
        sizeLabel.setText("size: "+ currentTask.size);
    }

    public void initSummaryPage(Task task)
    {
        skeleton = new Form(  task.name, new BorderLayout());
        TableLayout tl = new TableLayout(11, 2);
        tl.setGrowHorizontally(true);
        skeleton.setLayout(tl);
        editButton.addActionListener((e) -> EditPageUI.editPage.startUI(task));

        backButton.addActionListener((e) -> goBack());
        skeleton.
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), backButton).
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

    private void goBack() {
        MainPageUI.mainPage.startUI();
       //stopUI();
    }

    public void stopUI() {
        skeleton = getCurrentForm();
        if(skeleton instanceof Dialog) {
            ((Dialog)skeleton).dispose();
            skeleton = getCurrentForm();
        }
    }
}
