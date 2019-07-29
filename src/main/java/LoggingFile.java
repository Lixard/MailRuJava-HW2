import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LoggingFile implements LoggingInterface {

    @NotNull
    private final Counter counter;
    @NotNull
    private final String tag;

    @Inject
    public LoggingFile(@NotNull Counter counter, @NotNull String tag) {
        this.counter = counter;
        this.tag = tag;
        File file = new File("log.txt");
        try {
            boolean newFile = file.createNewFile();
//            if (newFile)
//                System.out.println("File 'log.txt' created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void writing(@NotNull String input) {
        try {
            FileWriter writer = new FileWriter("log.txt", true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(counter.increasing(addTag(input)));
            bufferWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @NotNull
    private String addTag(@NotNull String input) {
        return '<' + tag + '>' + input + '<' + '/' + tag + '>' + '\n';
    }
}
