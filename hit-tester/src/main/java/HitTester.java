import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.Random;

public class HitTester {
    public static void main(String[] args) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Screen screen = null;

        try {
            screen = defaultTerminalFactory.createScreen();
            screen.startScreen();
            screen.setCursorPosition(null);

            Random random = new Random();
            TerminalSize terminalSize = screen.getTerminalSize();

            for (int column = 0; column < terminalSize.getColumns(); column++) {
                for (int row = 0; row < terminalSize.getRows(); row++) {
                    screen.setCharacter(column, row, new TextCharacter('0'));
                }
            }
            screen.refresh();

            Thread.sleep(2000);

            boolean run = true;

            // Main hitter loop
            while (run) {
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null && (keyStroke.getKeyType() == KeyType.Escape || keyStroke.getKeyType() == KeyType.EOF)) {
                    run = false;
                }

                TerminalSize newSize = screen.doResizeIfNecessary();
                if (newSize != null) {
                    terminalSize = newSize;
                }

                final int charactersToHitPerLoop = 1;
                for (int i = 0; i < charactersToHitPerLoop; i++) {
                    TerminalPosition cellToHit = new TerminalPosition(
                            random.nextInt(terminalSize.getColumns()),
                            random.nextInt(terminalSize.getRows())
                    );
                    TextCharacter character = screen.getBackCharacter(cellToHit);
                    if (!(character.getCharacter() == '+')) {
                        if (character.getCharacter() == ' ') {
                            character = character.withCharacter('0');
                        } else {
                            int characterAsInt = Integer.parseInt("" + character.getCharacter());
                            characterAsInt++;
                            if (characterAsInt >= 10) {
                                character = character.withCharacter('+');
                            } else {
                                character = character.withCharacter(("" + characterAsInt).charAt(0));
                            }
                        }
                        screen.setCharacter(cellToHit, character);
                    }
                }
                screen.refresh();
            }
        } catch (IOException | InterruptedException e) {
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
