package org.ecs160.a2.UI_PAGES;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.table.TableLayout;
import org.ecs160.a2.Storage.Storage;
import org.ecs160.a2.Model.Task;
import static com.codename1.ui.CN.getCurrentForm;

public class EditPageUI
{
    public static EditPageUI editPage = new EditPageUI();

    private final Storage storage = Storage.instanse();
    private Form scaffold;
    private Task currentTask;
    private TextField taskName;
    private TextField taskDescription;
    private TextField taskSize;
    public void startUI(Task task) {
        currentTask = task;
        if(scaffold != null){
            scaffold.show();
            return;
        }
        setUpPageLayout(task);
        setUpButtons();
        scaffold.show();
    }



    private void backBtn() {
        InfoPageUI.infoPage.startUI(currentTask);
        stopUI();
    }

    private void updateBtn() {
        String nameText = taskName.getText();
        String descriptionText = taskDescription.getText();
        String taskSizeText = taskSize.getText();

        Task newTask = currentTask;
        System.out.print("before to change " + newTask.name);
        if (!nameText.equals("")) {
            System.out.print("hereee");
            newTask.name = nameText;

        }
        if (!taskSizeText.equals("")) {
            newTask.size = taskSizeText;
        }
        if (!descriptionText.equals("")) {
            newTask.description = descriptionText;
        }
        System.out.print("\n about to change " + newTask.name);
        storage.editTask(newTask, currentTask);

        currentTask = newTask;
       // backBtn();
        scaffold.show();
        clearFields();
    }

    private void clearFields() {
        taskName.clear();
        taskDescription.clear();
        taskSize.clear();
    }



    private void setUpPageLayout(Task task) {
        scaffold = new Form("Editing Task: " + task.name, new BorderLayout());
        TableLayout tl;
        int spanButton = 1;
        tl = new TableLayout(14, 1);
        tl.setGrowHorizontally(true);
        scaffold.setLayout(tl);
        TableLayout.Constraint cn = tl.createConstraint();
        cn.setHorizontalSpan(spanButton);
        cn.setHorizontalAlign(Component.RIGHT);
    }

    private void setUpButtons() {
        taskName = new TextField("", "Re-name", 20, TextArea.ANY);
        taskDescription = new TextField("", "Description", 40, TextArea.ANY);
        taskSize = new TextField("", "Task Size", 20, TextArea.ANY);
        Button updateButton = new Button("update");
        Button Back = new Button("Back");

        scaffold.add(taskName)
                .add(taskDescription)
                .add(taskSize)
                .add(updateButton)
                .add(Back);

        updateButton.addActionListener((e) -> updateBtn());
        Back.addActionListener((e) -> backBtn());
    }

    public void stopUI() {
        scaffold = getCurrentForm();
        if(scaffold instanceof Dialog) {
            ((Dialog)scaffold).dispose();
            scaffold = getCurrentForm();
        }
    }
}
