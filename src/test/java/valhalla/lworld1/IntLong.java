package valhalla.lworld1;

/**
 * This must be compiled with a JDK that supports lworld1 java.valhalla prototype
 * Run with same JRE with flag -XX:+EnableValhalla
 */
public __ByValue class IntLong {

    int _int; // implicit final
    long _long; // implicit final

    /**
     * @implNote Compiles to {@code static IntLong $makeValue$()} but is required at source for default value constructor.
     * If not provided then *final* fields are left uninitialised when using {@code __MakeDefault}.
     * However {@code makedefault} bytecode ignores the values set, defaulting to 0
     */
    IntLong() {
        _int = 100;
        _long = 100;
    }

    /**
     * @implNote Compiles to {@code static IntLong $makeValue$(int, long)} with identical bytecode to {@code static IntLong create(int, long)}
     */
    IntLong(int x, long y) {
        _int = x;
        _long = y;
    }

    /**
     * @implNote bytecode is identical to compiled 2-arg constructor.
     */
    static IntLong create(int x, long y) {
        IntLong intLong = __MakeDefault IntLong();
        intLong = __WithField(intLong._int, x);
        intLong = __WithField(intLong._long, y);
        return intLong;
    }

    /**
     * @implNote bytecode saves two instructions compared to 2-arg constructor
     */
    static IntLong createBetter(int x, long y) {
        IntLong intLong = __MakeDefault IntLong();
        intLong = __WithField(intLong._int, x);
        return __WithField(intLong._long, y);
    }

    IntLong withInt(int update) {
        return __WithField(_int, update);
    }

    IntLong withLong(long update) {
        return __WithField(_long, update);
    }
}
