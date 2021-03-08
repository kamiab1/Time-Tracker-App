package org.ecs160.a2.UI_PAGES;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.components.MultiButton;
import com.codename1.ui.events.ActionEvent;
import org.ecs160.a2.Storage.Store;
import org.ecs160.a2.model.Task;

import java.util.ArrayList;
import java.util.List;

import static com.codename1.ui.CN.*;

public class MainPageUI
{
    Store store = new Store();
    private Form scaffold;
    private Resources theme;
    public static MainPageUI mainPage = new MainPageUI();

    public void initUI(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);
    }

    public void startUI() {
        // NOT SURE NEEDS MORE TESTS
//        if(skeleton != null){
//            skeleton.show();
//            return;
//        }

        scaffold = new Form("Task List", new BorderLayout());

        Command createTask = new Command("Create Task") {
            public void actionPerformed(ActionEvent e) {
                CreateTaskPageUI.createTaskPage.startUI();
            }
        };

        scaffold.getToolbar().addCommandToRightBar(createTask);
        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        scaffold.add(CENTER, list);

        List<Task> taskList = store.getAllTasks();
        final List<Container> contList = new ArrayList<Container>();

        taskList.forEach( eachTask -> {
            System.out.print("HERE \n");
            // build container
            Container container = new Container(BoxLayout.x());
            container.setWidth(scaffold.getWidth());
            container.setY(scaffold.getHeight());
            // build button
            MultiButton taskButton = new MultiButton(eachTask.name);
            taskButton.setWidth(scaffold.getWidth());
            taskButton.addActionListener((e)-> goToInfoPage(eachTask));
            // add to list
            container.addComponent(taskButton);
            contList.add(container);
            list.addComponent(container);
        });


        scaffold.show();
    }

    private void goToInfoPage(Task task) {
        System.out.print("pressed :" + task.name);
        InfoPageUI.infoPage.startUI(task);
    }

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
