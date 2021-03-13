package org.ecs160.a2.UI_PAGES;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import org.ecs160.a2.Storage.Storage;
import org.ecs160.a2.Model.Task;
import org.ecs160.a2.Theme.CustomTheme;

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
    private Label editStatusLabel;
    public void startUI(Task task) {
        currentTask = task;
        if(scaffold != null){
            scaffold.show();
            return;
        }
        setUpPageLayout();
        setUpButtons();
        ToolbarInitializer.initBackButton(scaffold, (b) -> goBack());
        scaffold.show();
    }


    private void goBack() {
        InfoPageUI.infoPage.startUI(currentTask);
        editStatusLabel.setText("");
        stopUI();
    }

    private void updateAction() {
        String nameText = taskName.getText();
        String descriptionText = taskDescription.getText();
        String taskSizeText = taskSize.getText();

        Task newTask = null;
        try {
            newTask = (Task) currentTask.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (!nameText.equals("")) {
            newTask.name = nameText;
        }
        if (!taskSizeText.equals("")) {
            newTask.size = taskSizeText;
        }
        if (!descriptionText.equals("")) {
            newTask.description = descriptionText;
        }

        storage.editTask(newTask, currentTask);
        currentTask = newTask;

        if (!nameText.equals("") || !taskSizeText.equals("") || !descriptionText.equals(""))
        {
            editStatusLabel.getStyle().setFgColor(CustomTheme.ActiveThemeColor);
            editStatusLabel.setText("Edit Success!");
        }
        else
        {
            editStatusLabel.getStyle().setFgColor(CustomTheme.InactiveThemeColor);
            editStatusLabel.setText("You must specify a field entry.");
        }

        clearFields();
        scaffold.show();
    }

    private void clearFields() {
        taskName.clear();
        taskDescription.clear();
        taskSize.clear();
    }

    private void setUpPageLayout() {
        scaffold = new Form("Edit", new BorderLayout());
        scaffold.getStyle().setBgColor(CustomTheme.UIPageColor);
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
        Button updateButton = new Button("Update");

        editStatusLabel = new Label("");
        editStatusLabel.getStyle().setFont(CustomTheme.smallBoldSystemFont);
        Container statusLabelContainer = new Container(BoxLayout.xCenter());
        statusLabelContainer.add(editStatusLabel);

        scaffold
                .add(taskName)
                .add(taskDescription)
                .add(taskSize)
                .add(updateButton)
                .add(statusLabelContainer);

        updateButton.addActionListener((e) -> updateAction());
    }

    public void stopUI() {
        scaffold = getCurrentForm();
        if(scaffold instanceof Dialog) {
            ((Dialog)scaffold).dispose();
            scaffold = getCurrentForm();
        }
    }

}
