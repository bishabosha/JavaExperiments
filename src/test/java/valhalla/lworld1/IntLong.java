package valhalla.lworld1;

/**
 * This must be compiled with a JDK that supports lworld1 java.valhalla prototype
 * Run with same JRE with flag -XX:+EnableValhalla
 */
public __ByValue class IntLong {

    int _int; // implicit final
    long _long; // implicit final

    /**
     * Compiles to static $makeValue$ but is required at source for default value constructor,
     * whereby each field receives its JVM default value from {@code Object.<init>}
     */
    IntLong() {
        _int = 100;
        _long = 100;
    }

    /**
     * @implNote Compiles to overloaded static {@code $makeValue$(int, long)} with identical bytecode to {@code create(int, long)}
     */
    IntLong(int x, long y) {
        _int = x;
        _long = y;
    }

    /**
     * @implNote bytecode is identical to compiled 2 arg constructor.
     */
    static IntLong create(int x, long y) {
        IntLong intLong = __MakeDefault IntLong();
        intLong = __WithField(intLong._int, x);
        intLong = __WithField(intLong._long, y);
        return intLong;
    }
}
