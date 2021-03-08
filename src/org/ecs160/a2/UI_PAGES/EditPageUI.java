package org.ecs160.a2.UI_PAGES;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import org.ecs160.a2.Storage.Store;
import org.ecs160.a2.model.Task;
import static com.codename1.ui.CN.getCurrentForm;

public class EditPageUI
{
    Form scaffold;
    TextField taskName;
    TextField taskDescription;
    TextField taskSize;
    private Form current;
    private Resources theme;
    Store store = new Store();
    public static EditPageUI editPage = new EditPageUI();
    private Task currentTask;
    public void startUI(Task task) {
        currentTask = task;
        if(current != null){
            current.show();
            return;
        }
        setUpPageLayout(task);
        setUpButtons();
        scaffold.show();
    }




    private void backBtn(Task task) {
        InfoPageUI.infoPage.startUI(task);
        stopUI();
    }

    private void updateBtn() {
    }

    public void stopUI() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
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
        Button startButton = new Button("update");
        Button Back = new Button("Back");

        scaffold.add(taskName)
                .add(taskDescription)
                .add(taskSize)
                .add(startButton)
                .add(Back);

        startButton.addActionListener((e) -> updateBtn());
        Back.addActionListener((e) -> backBtn(currentTask));
    }
}
