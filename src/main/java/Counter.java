import com.google.inject.Inject;

public class Counter {

    private int number;

    @Inject
    public Counter() {
        number = 1;
    }

    public String increasing(String input) {
        String output = number + ": " + input;
        number++;
        return output;
    }
    int getNumber() {
        return number;
    }
}
