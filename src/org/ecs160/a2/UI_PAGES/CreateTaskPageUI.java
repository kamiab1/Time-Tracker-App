package org.ecs160.a2.UI_PAGES;

import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import org.ecs160.a2.Data;

import static com.codename1.ui.CN.*;
import static com.codename1.ui.CN.getCurrentForm;

public class CreateTaskPageUI
{
    Form scaffold;
    TextField taskName;
    TextField description;
    TextField taskSize;
    private Form current;
    private Resources theme;

    public void initUI(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }

    public void startUI() {
        if(current != null){
            current.show();
            return;
        }
        scaffold = new Form("Welcome", new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));

        TableLayout tl;
        int         spanButton = 2;
        if(Display.getInstance().isTablet()) {
            tl = new TableLayout(7, 2);
        } else {
            tl = new TableLayout(14, 1);
            spanButton = 1;
        }
        tl.setGrowHorizontally(true);
        scaffold.setLayout(tl);

        taskName = new TextField("", "taskName", 20, TextArea.ANY);
        description = new TextField("", "Description", 40, TextArea.ANY);
        taskSize = new TextField("", "Task Size", 20, TextArea.ANY);
        Button startButton = new Button("Create");

        Label l = new Label(" ");

        l.setUIID("Separator");

        TableLayout.Constraint cn = tl.createConstraint();
        cn.setHorizontalSpan(spanButton);
        cn.setHorizontalAlign(Component.RIGHT);
        scaffold.add(taskName)
                .add(description)
                .add(taskSize)
                .add(startButton)
                .add(l);


        l.setUIID("Separator");

        startButton.addActionListener((e) -> startBtnPressed());

        ToolbarUI toolbarUI = new ToolbarUI(scaffold);
        scaffold.show();
    }


    private void startBtnPressed() {
        Data d = new Data();
        //d.addTask("studying");
        d.getTaskMap("studying");

        System.out.println("start");
    }

    public void stopUI() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroyUI() {

    }



}