import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;

public class LoggingComposite implements LoggingInterface {

    @NotNull
    private final LoggingConsole loggingConsole;
    @NotNull
    private final LoggingFile loggingFile;

    @Inject
    public LoggingComposite(@NotNull LoggingConsole loggingConsole,@NotNull LoggingFile loggingFile) {
        this.loggingConsole = loggingConsole;
        this.loggingFile = loggingFile;
    }

    @Override
    public void writing(@NotNull String input) {
        loggingConsole.writing(input);
        loggingFile.writing(input);
    }
}
