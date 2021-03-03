package org.ecs160.a2.UI_PAGES;

import com.codename1.ui.Command;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;

public class ToolbarUI
{
    Toolbar toolbar = new Toolbar();

    public ToolbarUI(Form form)
    {
        toolbar.setOnTopSideMenu(false);
        form.setToolbar(toolbar);
        initSideMenu();

    }

    public void initSideMenu()
    {
        Command taskListMenu = new Command("View Task List");
        toolbar.addCommandToSideMenu(taskListMenu);

        Command createTaskMenu = new Command("Create Task");
        toolbar.addCommandToSideMenu(createTaskMenu);

        Command viewTaskMenu = new Command("View Task");
        toolbar.addCommandToSideMenu(viewTaskMenu);

        Command editTaskMenu = new Command("Edit Task");
        toolbar.addCommandToSideMenu(editTaskMenu);

        Command taskSummaryMenu = new Command("Summary");
        toolbar.addCommandToSideMenu(taskSummaryMenu);

    }
}
