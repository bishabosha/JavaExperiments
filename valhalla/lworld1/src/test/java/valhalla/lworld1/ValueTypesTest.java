package valhalla.lworld1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This must be compiled with a JDK that supports lworld1 java.valhalla prototype
 * Run with same JRE with flag -XX:+EnableValhalla
 */
public class ValueTypesTest {

    @Test
    public void test_stringDynamic() {
        assertEquals("[value class valhalla.lworld1.IntLong, 1, 2]", IntLong.create(1, 2).toString());
    }

    @Test
    public void test_Fields_withDefault() {
        // invokes IntLong() in class file; note the no arg constructor is required in source, but is not invoked here
        IntLong intLong = IntLong.default;

        assertEquals(0, intLong._int);
        assertEquals(0, intLong._long);
    }

    @Test
    public void test_Fields_withNew() {
        // calls static IntLong $makeValue$() in class file
        var intLong = new IntLong();
        assertEquals(100, intLong._int);
        assertEquals(100, intLong._long);
    }

    @Test
    public void test_Fields_withNewOverload() {
        // invokes static IntLong $makeValue$(int, long) in class file
        var intLong = new IntLong(1, 1);
        assertEquals(1, intLong._int);
        assertEquals(1, intLong._long);
    }

    @Test
    public void test_Fields_withCreate() {
        var intLong = IntLong.create(50, 50);
        assertEquals(50, intLong._int);
        assertEquals(50, intLong._long);
    }

    @Test
    public void test_Fields_withCreateBetter() {
        var intLong = IntLong.createBetter(50, 50);
        assertEquals(50, intLong._int);
        assertEquals(50, intLong._long);
    }

    @Test
    public void test_updateField_int() {
        var intLong = IntLong.create(1, 2);
        assertEquals(IntLong.create(1, 2), intLong);

        var intLongUpdate = intLong.withInt(5);
        assertEquals(IntLong.create(5, 2), intLongUpdate);
        assertEquals(IntLong.create(1, 2), intLong);
    }

    @Test
    public void test_updateField_long() {
        var intLong = IntLong.create(1, 2);
        assertEquals(IntLong.create(1, 2), intLong);

        var intLongUpdate = intLong.withLong(5);
        assertEquals(IntLong.create(1, 5), intLongUpdate);
        assertEquals(IntLong.create(1, 2), intLong);
    }

    /**
     * @implNote appears to be a bug, type inference allows Value Types to be generic witnesses without -XDallowGenericsOverValues
     */
    @Test
    public void test_generic_ValueParam_inferred() {
        var list = List.of(IntLong.create(1, 1), IntLong.create(2, 2));
        assertEquals("[[value class valhalla.lworld1.IntLong, 1, 1], [value class valhalla.lworld1.IntLong, 2, 2]]", list.toString());
    }

    /**
     * This is not allowed unless -XDallowGenericsOverValues is sent to javac
     */
    @Test
    public void test_generic_ValueParam_manifest() {
        List<IntLong> list = List.of(IntLong.create(1, 1), IntLong.create(2, 2));
        assertEquals("[[value class valhalla.lworld1.IntLong, 1, 1], [value class valhalla.lworld1.IntLong, 2, 2]]", list.toString());
    }
}
