package utils.panic;

@SuppressWarnings("serial")
public final class Panic extends RuntimeException {
    public static void panic() {
        throw new Panic();
    }

    public static void panic(String msg) {
        throw new Panic(msg);
    }

    public Panic() {
        super();
    }

    public Panic(String msg) {
        super("panicked at '" + msg + '\'');
    }
}
