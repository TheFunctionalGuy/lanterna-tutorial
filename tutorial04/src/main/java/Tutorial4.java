import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class Tutorial4 {
    public static void main(String[] args) {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;

        try {
            // Initialize
            screen = terminalFactory.createScreen();
            screen.startScreen();

            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            final Window window = new BasicWindow("My Root Window");
            Panel contentPanel = new Panel(new GridLayout(2));

            GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
            gridLayout.setHorizontalSpacing(3);

            // Build label which spans two columns
            Label title = new Label("This is a label that spans two columns");
            title.setLayoutData(GridLayout.createLayoutData(
                    GridLayout.Alignment.BEGINNING,
                    GridLayout.Alignment.BEGINNING,
                    true,
                    false,
                    2,
                    1
            ));
            contentPanel.addComponent(title);

            // Add TextBox with label
            contentPanel.addComponent(new Label("Text Box (aligned)"));
            contentPanel.addComponent(
                    new TextBox()
                        .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER))
            );

            // Add TextBox with password mask and label
            contentPanel.addComponent(new Label("Password Box (right aligned)"));
            contentPanel.addComponent(
                    new TextBox()
                        .setMask('*')
                        .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER))
            );


            // Add two ComboBoxes
            contentPanel.addComponent(new Label("Read-only Combo Box (forced size)"));
            List<String> timezonesAsStrings = new ArrayList<>();
            for (String id: TimeZone.getAvailableIDs()) {
                timezonesAsStrings.add(id);
            }
            ComboBox<String> readOnlyComboBox = new ComboBox<>(timezonesAsStrings);
            readOnlyComboBox.setReadOnly(true);
            readOnlyComboBox.setPreferredSize(new TerminalSize(20, 1));
            contentPanel.addComponent(readOnlyComboBox);

            contentPanel.addComponent(new Label("Editable Combo Box (filled)"));
            contentPanel.addComponent(
                    new ComboBox<>("Item #1", "Item #2", "Item #3", "Item #4")
                            .setReadOnly(false)
                            .setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1)));

            contentPanel.addComponent(new Label("Button (centered)"));
            contentPanel.addComponent(new Button("Button", new Runnable() {
                @Override
                public void run() {
                    MessageDialog.showMessageDialog(textGUI, "MessageBox", "This is a message box", MessageDialogButton.OK);
                }
            }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)));

            contentPanel.addComponent(
                    new EmptySpace()
                            .setLayoutData(
                                    GridLayout.createHorizontallyFilledLayoutData(2)));
            contentPanel.addComponent(
                    new Separator(Direction.HORIZONTAL)
                            .setLayoutData(
                                    GridLayout.createHorizontallyFilledLayoutData(2)));
            contentPanel.addComponent(
                    new Button("Close", new Runnable() {
                        @Override
                        public void run() {
                            window.close();
                        }
                    }).setLayoutData(
                            GridLayout.createHorizontallyEndAlignedLayoutData(2)));

            window.setComponent(contentPanel);
            textGUI.addWindowAndWait(window);
        } catch (IOException e) {
            e.printStackTrace();

            System.exit(1);
        } finally {
            if (screen != null) {
                try {
                    screen.stopScreen();

                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();

                    System.exit(1);
                }
            }
        }
    }
}
