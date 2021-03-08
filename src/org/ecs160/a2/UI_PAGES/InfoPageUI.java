package org.ecs160.a2.UI_PAGES;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.table.TableLayout;
import org.ecs160.a2.Storage.Store;
import org.ecs160.a2.model.Task;
import static com.codename1.ui.CN.getCurrentForm;


public class InfoPageUI {
    private Form scaffold;
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
    private Store store = new Store();

    private Task currentTask;
    public static InfoPageUI infoPage = new InfoPageUI();

    public void startUI(Task task) {
        currentTask = task;
        System.out.print(" \n received data :" + currentTask.name);
        if(scaffold != null){
            initData(); // needs testing
            scaffold.show();
            return;
        }
        setUpLayout(currentTask);
        setUpButtons(currentTask);
        initData();
        scaffold.show();
    }




    /*************** General functions ****************/

    private void initData() {
        System.out.print(" \n called to show data \n ");
        statusLabel.setText("Activity status: " + currentTask.isRunning);
        descriptionLabel.setText("Description: "+ currentTask.description);
        sizeLabel.setText("size: "+ currentTask.size);
    }

    private void goBack() {
        MainPageUI.mainPage.startUI();
        //stopUI();
    }



    /*************** UI functions ****************/

    public void setUpLayout(Task task)
    {

        scaffold = new Form(task.name, new BorderLayout());
        TableLayout tl = new TableLayout(11, 2);
        tl.setGrowHorizontally(true);
        scaffold.setLayout(tl);

        scaffold.
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

    private void setUpButtons(Task task) {
        editButton.addActionListener((e) -> EditPageUI.editPage.startUI(task));
        backButton.addActionListener((e) -> goBack());
        deleteButton.addActionListener((e) -> deleteThisTask());
    }

    private void deleteThisTask() {
        store.deleteTask(currentTask);
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
}
