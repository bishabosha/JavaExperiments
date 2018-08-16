package jdk8;

import org.junit.jupiter.api.Test;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
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
        Lookup lookup = MethodHandles.lookup();
        MethodType mt_int_int = MethodType.methodType(int.class, int.class);
        MethodType mt_IntUnaryOperator = MethodType.methodType(IntUnaryOperator.class);
        MethodHandle addOneHandle;
        try {
            addOneHandle = lookup.findStatic(LambdaTest.class, "addOne", mt_int_int);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new InstantiationError("addOneHandle");
        }

        IntUnaryOperator addOne;
        try {
            addOne = (IntUnaryOperator) LambdaMetafactory.metafactory(lookup, "applyAsInt", mt_IntUnaryOperator, mt_int_int, addOneHandle, mt_int_int)
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
