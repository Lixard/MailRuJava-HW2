import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.matcher.Matchers;
import org.jetbrains.annotations.NotNull;

/**
 * Something written
 * @author Maksim Borisov
 * @version 1.0
 */
public class LoggingModule extends AbstractModule {
    /**
     * Just a tag
     */
    @NotNull public final String tag;
    /**
     * Argument from Main String args
     */
    @NotNull protected final String arg;
    /**
     * Just a counter
     */
    @NotNull private final Counter counter;
    /**
     * Injector for Juice
     * @param arg creating a arg
     * @param tag creating a tag
     */
    @Inject
    public LoggingModule(@NotNull String arg, @NotNull String tag) {
        this.arg = arg;
        this.tag = tag;
        this.counter = new Counter();
    }

    /**
     * Overrided method from Juice AbstractModule
     */
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

    /**
     * Pretty useless method!
     * @return Actually Nothing!!
     */
    public String UselessMethod() {
        return "Nothing!";
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