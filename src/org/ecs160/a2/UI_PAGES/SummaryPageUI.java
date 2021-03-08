package org.ecs160.a2.UI_PAGES;
import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import java.util.Arrays;
import static com.codename1.ui.CN.CENTER;


public class SummaryPageUI {

    Form skeleton;
    public static SummaryPageUI infoPage = new SummaryPageUI();

    public void loadSummaryPageUI(String taskName) {
        skeleton = new Form("Info: " + taskName, new BorderLayout());

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

        Button editBtn = new Button("Edit");
        editBtn.addActionListener((e) -> EditPageUI.editPage.loadEditPageUI(taskName));

        skeleton.add(editBtn);

        skeleton.show();
    }
}
