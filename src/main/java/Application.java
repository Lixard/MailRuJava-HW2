import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Application {
    @NotNull
    private final  LoggingInterface logger;

    @Inject
    public Application(@NotNull LoggingInterface logger) {
        this.logger = logger;
    }

    public static void main(@NotNull String[] args) {
        String tag = tagCreate(args);

        final Injector injector = Guice.createInjector(new LoggingModule(args[0], tag));
        injector.getInstance(Application.class).waitForInput();
    }

    public void waitForInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Waiting for new lines. Key in Ctrl+D to exit.");
            while (true) {
                logger.writing(scanner.nextLine());
            }
        } catch (IllegalStateException | NoSuchElementException ignored) {
        }
    }
    private static String tagCreate(@NotNull String[] args) {
        String tag;
        if (args.length > 1) {
            return tag = args[1];
        } else {
            return tag = "";
        }
    }
}
