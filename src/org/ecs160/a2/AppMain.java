package org.ecs160.a2;

import static com.codename1.ui.CN.*;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UITimer;
import java.io.IOException;

import com.codename1.io.NetworkEvent;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class AppMain {
    Form scaffold;
    TextField taskName;
    TextField reNameTask;
    TextField description;
    TextField taskSize;
    private Form current;
    private Resources theme;

    public void init(Object context) {
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
    
    public void start() {
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
        reNameTask = new TextField("", "Re-name Task", 20, TextArea.ANY);
        description = new TextField("", "Description", 40, TextArea.ANY);
        taskSize = new TextField("", "Task Size", 20, TextArea.ANY);
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button saveButton = new Button("Save");
        TextArea txt = new TextArea("add");
        Label l = new Label(" ");

        l.setUIID("Separator");

        Button submit = new Button("Summary");
        TableLayout.Constraint cn = tl.createConstraint();
        cn.setHorizontalSpan(spanButton);
        cn.setHorizontalAlign(Component.RIGHT);
        scaffold.add(taskName)
                .add(startButton)
                .add(stopButton)
                .add(reNameTask)
                .add(description)
                .add(taskSize)
                .add(saveButton)
                .add(l)
                .add(cn, submit);

        l.setUIID("Separator");




        startButton.addActionListener((e) -> startBtnPressed());
        stopButton.addActionListener((e) -> stopBtnPressed());
        saveButton.addActionListener((e) -> saveBtnPressed());


        scaffold.show();
    }

    private void saveBtnPressed() {
        String input = reNameTask.getText();
        scaffold.add(input + " : changes have been saved \n" );
        scaffold.show();
        System.out.println("saved");
    }

    private void stopBtnPressed() {
        String input = reNameTask.getText();
        scaffold.add(input + " : Stopped \n" );

        taskName.setText("");
        reNameTask.setText("");
        description.setText("");
        taskSize.setText("");
        
        scaffold.show();
        System.out.println("stop");
    }

    private void startBtnPressed() {
        String input = taskName.getText();
        scaffold.add(input + " : is currently in progress \n" );
        scaffold.show();
        System.out.println("start");
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }

}
