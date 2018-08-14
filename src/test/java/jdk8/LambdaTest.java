package jdk8;

import org.junit.jupiter.api.Test;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.IntUnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LambdaTest {

    @Test
    public void test_lambda_usingBootstrap() {
        testAddOne(byLambda());
    }

    @Test
    public void test_lambda_usingMetaFactory() {
        testAddOne(byFactory());
    }

    private IntUnaryOperator byLambda() {
        return x -> x + 1;
    }

    private IntUnaryOperator byFactory() {
        var lookup = MethodHandles.lookup();
        var mt = MethodType.methodType(int.class, int.class);
        MethodHandle addOneHandle;
        try {
            addOneHandle = lookup.findStatic(LambdaTest.class, "addOne", mt);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new InstantiationError("ADD_ONE");
        }

        IntUnaryOperator addOne;
        try {
            addOne = (IntUnaryOperator) LambdaMetafactory.metafactory(lookup, "applyAsInt", MethodType.methodType(IntUnaryOperator.class), mt, addOneHandle, mt)
                .getTarget()
                .invokeExact();
        } catch (Throwable throwable) {
            throw new InstantiationError("addOne");
        }

        return addOne;
    }


    private static int addOne(int x) {
        return x + 1;
    }

    private void testAddOne(IntUnaryOperator addOne) {
        assertEquals(1, addOne.applyAsInt(0));
        assertEquals(2, addOne.applyAsInt(1));
        assertEquals(3, addOne.applyAsInt(2));
    }
}
