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
    Button backButton = new Button("Back");
    Label statusLabel = new Label("Status");
    Button startButton = new Button("Start");
    Button stopButton = new Button("Stop");
    Label nameLabel = new Label("Name");
    Label descriptionLabel = new Label("Description");
    Label sizeLabel = new Label("Size");
    Label taskDataLabel = new Label("Task Data");
    Label minTimeLabel = new Label("Min Time: ");
    Label maxTimeLabel = new Label("Max Time: ");
    Label avgTimeLabel = new Label("Avg Time: ");
    Button deleteButton = new Button("Delete");
    Button editButton = new Button("Edit");


    public static SummaryPageUI infoPage = new SummaryPageUI();

    public void loadSummaryPageUI(String taskName) {
        if (skeleton != null)
        {
            //TODO: Implement function to load task info and data (requires
            // backend to be finished)
            skeleton.show();
            return;
        }
        initSummaryPage(taskName);
        skeleton.show();
    }

    public void initSummaryPage(String taskName)
    {
        skeleton = new Form("Info: " + taskName, new BorderLayout());
        TableLayout tl = new TableLayout(11, 2);
        tl.setGrowHorizontally(true);
        skeleton.setLayout(tl);
        editButton.addActionListener((e) -> EditPageUI.editPage.loadEditPageUI(taskName));
        backButton.addActionListener((e) -> MainPageUI.mainPage.loadMainPageUI());
        skeleton.
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), backButton).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), statusLabel).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.LEFT), startButton).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.LEFT), stopButton).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), nameLabel).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), descriptionLabel).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), sizeLabel).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), new Label(" ")).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), taskDataLabel).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), minTimeLabel).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), maxTimeLabel).
        add(tl.createConstraint().horizontalSpan(2).horizontalAlign(Component.LEFT), avgTimeLabel).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.LEFT), deleteButton).
        add(tl.createConstraint().horizontalSpan(1).horizontalAlign(Component.LEFT), editButton);
    }
}
