import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Tutorial {
    public static void main(String[] args) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal = null;

        try {
            // Printing Hello
            terminal = defaultTerminalFactory.createTerminal();
            String tutString1 = "Hello\n";
            putString(terminal, tutString1);
            terminal.flush();

            Thread.sleep(2000);

            // Moving cursor to (3, 2)
            TerminalPosition startPosition = terminal.getCursorPosition();
            terminal.setCursorPosition(startPosition.withRelativeColumn(3).withRelativeRow(2));
            terminal.flush();

            Thread.sleep(2000);

            // Setting text color and print text
            terminal.setBackgroundColor(TextColor.ANSI.BLUE);
            terminal.setForegroundColor(TextColor.ANSI.YELLOW);

            String tutString2 = "Yellow on blue";
            putString(terminal, tutString2);
            terminal.flush();

            Thread.sleep(2000);

            // Moving cursor to (3, 3)
            terminal.setCursorPosition(startPosition.withRelativeColumn(3).withRelativeRow(3));
            terminal.flush();

            Thread.sleep(2000);

            // Print bold text
            terminal.enableSGR(SGR.BOLD);
            putString(terminal, tutString2);
            terminal.flush();

            Thread.sleep(2000);

            // Reset
            terminal.resetColorAndSGR();
            terminal.setCursorPosition(terminal.getCursorPosition().withRelativeColumn(0).withRelativeRow(1));

            String tutString3 = "Done\n";
            putString(terminal, tutString3);
            terminal.flush();

            Thread.sleep(2000);

            // Beep and exit
            terminal.bell();
            terminal.flush();

            Thread.sleep(2000);
        } catch (IOException | InterruptedException e) {
            System.err.println("Whoops something went wrong!");
            e.printStackTrace();

            System.exit(1);
        } finally {
            if (terminal != null) {
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();

                    System.exit(1);
                }
            }
        }

        System.exit(0);
    }

    private static void putString(Terminal terminal, String string) throws IOException {
        for (int i = 0; i < string.length(); i++) {
            terminal.putCharacter(string.charAt(i));
        }
    }
}
