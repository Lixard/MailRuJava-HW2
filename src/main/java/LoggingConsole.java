import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;


public class LoggingConsole implements LoggingInterface {

    @NotNull
    private final Counter counter;

    @Inject
    public LoggingConsole(@NotNull Counter counter) {
        this.counter = counter;
    }

    @Override
    @LineChecker
    public void writing(@NotNull String input) {
        System.out.println(counter.increasing(input));
    }
}
