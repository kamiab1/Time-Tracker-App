package org.ecs160.a2.UI_PAGES;

import com.codename1.components.MultiButton;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;

import java.util.Arrays;

import static com.codename1.ui.CN.CENTER;

public class EditPageUI
{
    Form skeleton;
    public static EditPageUI editPage = new EditPageUI();

    public void loadEditPageUI(String taskName) {
        skeleton = new Form("Editing Task: " + taskName, new BorderLayout());

        TableLayout tl;
        int         spanButton = 2;
        if(Display.getInstance().isTablet()) {
            tl = new TableLayout(7, 2);
        } else {
            tl = new TableLayout(14, 1);
            spanButton = 1;
        }
        tl.setGrowHorizontally(true);
        skeleton.setLayout(tl);

        skeleton.show();
    }
}
