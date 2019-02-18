import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;

import java.io.IOException;

public class Tutorial2 {
    private static TerminalSize lastTerminalSize;

    public static void main(String[] args) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal = null;

        try {
            terminal = defaultTerminalFactory.createTerminal();
            terminal.enterPrivateMode();
            terminal.clearScreen();
            terminal.setCursorVisible(false);

            final TextGraphics textGraphics = terminal.newTextGraphics();
            textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
            textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);

            textGraphics.putString(2, terminal.getTerminalSize().getRows() - 2, "Terminal class: ", SGR.BOLD);
            textGraphics.putString(2 + "Terminal class: ".length(), terminal.getTerminalSize().getRows() - 2, terminal.getClass().toString());
            textGraphics.putString(2, 1, "Lanterna Tutorial 2 - Press ESC to exit", SGR.BOLD);
            textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
            textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT);
            textGraphics.putString(5, 3, "Terminal Size: ", SGR.BOLD);
            textGraphics.putString(5 + "Terminal Size: ".length(), 3, terminal.getTerminalSize().toString());
            terminal.flush();

            lastTerminalSize = terminal.getTerminalSize();

            terminal.addResizeListener(new TerminalResizeListener() {
                @Override
                public void onResized(Terminal terminal, TerminalSize terminalSize) {
                    textGraphics.drawLine(5, 3, terminalSize.getColumns() - 1, 3, ' ');
                    textGraphics.putString(5, 3, "Terminal Size: ", SGR.BOLD);
                    textGraphics.putString(5 + "Terminal Size: ".length(), 3, terminalSize.toString());

                    try {
                        textGraphics.drawLine(2, lastTerminalSize.getRows() - 2, terminalSize.getColumns(), lastTerminalSize.getRows() - 2, ' ');
                        textGraphics.putString(2, terminal.getTerminalSize().getRows() - 2, "Terminal class: ", SGR.BOLD);
                        textGraphics.putString(2 + "Terminal class: ".length(), terminal.getTerminalSize().getRows() - 2, terminal.getClass().toString());
                        lastTerminalSize = terminal.getTerminalSize();
                        terminal.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            textGraphics.putString(5, 4, "Last Keystroke: ", SGR.BOLD);
            textGraphics.putString(5 + "Last Keystroke :".length(), 4, "<Pending>");
            terminal.flush();

            KeyStroke keyStroke = terminal.readInput();

            while (keyStroke.getKeyType() != KeyType.Escape) {
                textGraphics.drawLine(5, 4, terminal.getTerminalSize().getColumns() - 1, 4, ' ');
                textGraphics.putString(5, 4, "Last Keystroke: ", SGR.BOLD);
                textGraphics.putString(5 + "Last Keystroke: ".length(), 4, keyStroke.toString());
                terminal.flush();
                keyStroke = terminal.readInput();
            }
        } catch (IOException e) {
            e.printStackTrace();

            System.exit(1);
        } finally {
            if (terminal != null) {
                try {
                    terminal.close();

                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
