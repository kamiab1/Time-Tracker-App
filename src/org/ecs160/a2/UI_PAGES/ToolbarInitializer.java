package org.ecs160.a2.UI_PAGES;

import com.codename1.ui.Command;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;


public class ToolbarInitializer
{
    Toolbar toolbar = new Toolbar();

    public ToolbarInitializer(Form form)
    {
        Toolbar.setOnTopSideMenu(false);
        form.setToolbar(toolbar);

    }

    public static void initBackButton(Form scaffold,
                                      ActionListener<ActionEvent> action)
    {
        Toolbar toolbar = scaffold.getToolbar();
        toolbar.setBackCommand("Back", action);
    }

    public static void addTopRightAction(Form scaffold,
                                          Command command)
    {
        Toolbar toolbar = scaffold.getToolbar();
        toolbar.addCommandToRightBar(command);


    }
}
