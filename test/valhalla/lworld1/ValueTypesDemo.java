package valhalla.lworld1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This must be compiled with a JDK that supports lworld1 valhalla prototype
 * Run with same JRE with flag -XX:+EnableValhalla
 */
public class ValueTypesDemo {

    @Test
    void stringDynamic() {
        assertEquals("[value class valhalla.lworld1.IntLong, 1, 2]", IntLong.create(1, 2).toString());
    }

    @Test
    void testFields_withDefault() {
        // invokes valhalla.lworld1.IntLong() in class file; note the no arg constructor is required in source, but is not invoked here
        IntLong intLong = __MakeDefault IntLong();

        assertEquals(0, intLong._int);
        assertEquals(0, intLong._long);
    }

    @Test
    void testFields_withNew() {
        // calls static valhalla.lworld1.IntLong $makeValue$() in class file
        var intLong = new IntLong();
        assertEquals(100, intLong._int);
        assertEquals(100, intLong._long);
    }

    @Test
    void testFields_withNewOverload() {
        // invokes static valhalla.lworld1.IntLong $makeValue$(int, long) in class file
        var intLong = new IntLong(1, 1); // equivalent to creating factory method
        assertEquals(1, intLong._int);
        assertEquals(1, intLong._long);
    }
}
