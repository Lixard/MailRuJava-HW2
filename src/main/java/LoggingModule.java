import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.matcher.Matchers;
import org.jetbrains.annotations.NotNull;

public class LoggingModule extends AbstractModule {
    @NotNull
    private final String tag;
    @NotNull
    private final String arg;
    @NotNull
    private final Counter counter;

    @Inject
    public LoggingModule(@NotNull String arg, @NotNull String tag) {
        this.arg = arg;
        this.tag = tag;
        this.counter = new Counter();
    }

    @Override
    protected void configure() {

        switch (arg) {
            case "console":
                bind(LoggingInterface.class).to(LoggingConsole.class);
                break;
            case "file":
                bind(LoggingInterface.class).toProvider(new LoggingFileWithTag(tag, counter));
                break;
            case "composite":
                bind(LoggingInterface.class).toProvider(new LoggingCompositeWithTag(tag, counter));
                break;
            default:
                System.out.println("Invalid args!");
        }
        bind(Counter.class).toInstance(counter);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(LineChecker.class), new ThirdLineChecker(counter));
    }
}

final class LoggingFileWithTag implements Provider<LoggingFile> {
    @NotNull
    private final String tag;
    @NotNull
    private final Counter counter;

    @Inject
    LoggingFileWithTag(@NotNull String tag, @NotNull Counter counter) {
        this.tag = tag;
        this.counter = counter;
    }

    @NotNull
    @Override
    public LoggingFile get() {
        return new LoggingFile(counter, tag);
    }
}

final class LoggingCompositeWithTag implements Provider<LoggingComposite> {
    @NotNull
    private final String tag;
    @NotNull
    private final Counter counter;

    @Inject
    LoggingCompositeWithTag(@NotNull String tag, @NotNull Counter counter) {
        this.tag = tag;
        this.counter = counter;
    }

    @NotNull
    @Override
    public LoggingComposite get() {
        return new LoggingComposite(new LoggingConsole(counter), new LoggingFile(counter, tag));
    }
}