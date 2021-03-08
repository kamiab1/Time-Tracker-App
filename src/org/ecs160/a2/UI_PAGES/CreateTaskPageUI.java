package org.ecs160.a2.UI_PAGES;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.table.TableLayout;
import org.ecs160.a2.Storage.LocalStorage;
import org.ecs160.a2.model.Task;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static com.codename1.ui.CN.getCurrentForm;

public class CreateTaskPageUI
{
    public static CreateTaskPageUI createTaskPage = new CreateTaskPageUI();

    private final LocalStorage store = new LocalStorage();
    private Form scaffold;
    private TextField taskName;
    private TextField taskDescription;
    private TextField taskSize;
    private Form current;


    public void startUI() {
        if(current != null){
            current.show();
            return;
        }
        setUpPageLayout();
        setUpButtons();
        scaffold.show();
    }


    /*************** General functions ****************/

    private void createBtn() {
        String nameText = taskName.getText();
        String descriptionText = taskDescription.getText();
        String sizeText = taskSize.getText();

        Map<String, String> taskMap = new HashMap<>();
        taskMap.put("size", sizeText);
        taskMap.put("description",  descriptionText);
        taskMap.put("startTime",  new Date().toString());
        taskMap.put("endTime", new Date().toString());
        Task task = new Task(nameText,taskMap);
        store.addTask(task);

        clearFields();
    }

    private void clearFields() {
        taskName.clear();
        taskDescription.clear();
        taskSize.clear();
    }

    private void backBtn() {
        MainPageUI.mainPage.startUI();
        stopUI();
    }



    /*************** UI functions ****************/

    private void setUpPageLayout() {
        scaffold = new Form("Create a new Task", new BorderLayout());
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
        taskName = new TextField("", "Task name", 20, TextArea.ANY);
        taskDescription = new TextField("", "Description", 40, TextArea.ANY);
        taskSize = new TextField("", "Task Size", 20, TextArea.ANY);
        Button createButton = new Button("Create");
        Button Back = new Button("Back");

        scaffold.add(taskName)
                .add(taskDescription)
                .add(taskSize)
                .add(createButton)
                .add(Back);

        createButton.addActionListener((e) -> createBtn());
        Back.addActionListener((e) -> backBtn());
    }




    /*************** Clean up ****************/

    private void stopUI() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }



}