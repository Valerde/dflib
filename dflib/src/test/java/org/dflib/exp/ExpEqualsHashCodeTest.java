package org.dflib.exp;

import org.dflib.Exp;
import org.dflib.NumExp;
import org.dflib.exp.num.IntScalarExp;
import org.junit.jupiter.api.Test;

import static org.dflib.Exp.$bool;
import static org.dflib.Exp.$col;
import static org.dflib.Exp.$int;
import static org.dflib.Exp.$str;
import static org.dflib.Exp.ifNull;
import static org.dflib.Exp.not;
import static org.dflib.Exp.rowNum;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ExpEqualsHashCodeTest {

    private void assertEqualsContract(Object x, Object y, Object z) {
        assertEquals(x, x, "Expression not equal to itself");
        assertNotEquals(x, null, "Equals should return false for null");

        if (y == null) {
            return;
        }
        assertEquals(x.equals(y), y.equals(x), "Symmetry failed");

        if (!x.equals(y)) {
            return;
        }
        assertEquals(x.hashCode(), y.hashCode(), "Equal expressions must have equal hashCodes");

        if (!y.equals(z)) {
            return;
        }
        assertEquals(x, z, "Transitivity failed");
    }

    @Test
    public void deepNestedExpressions() {
        Exp<?> e1 = Exp.ifExp(
                Exp.ifExp($bool("a"), $int(1), $int(2)).castAsBool(),
                Exp.ifExp($bool("b"), $int(3), $int(4)),
                $int(5)
        );
        Exp<?> e2 = Exp.ifExp(
                Exp.ifExp($bool("a"), $int(1), $int(2)).castAsBool(),
                Exp.ifExp($bool("b"), $int(3), $int(4)),
                $int(5)
        );

        assertEqualsContract(e1, e2, null);
    }

    @Test
    public void mixedNumericTypes() {
        NumExp<?> e1 = $int("a").add(5L);
        NumExp<?> e2 = $int("a").add(5L);
        NumExp<?> different = $int("a").add(5);

        assertEqualsContract(e1, e2, null);
        assertNotEquals(e1, different);
    }

    @Test
    public void asExp() {
        Exp<String> e1 = $str("test").as("a");
        Exp<String> e2 = $str("test").as("a");
        Exp<String> e3 = $str("test").as("a");
        Exp<String> different = $str("test").as("b");

        assertEqualsContract(e1, e2, e3);
        assertNotEquals(e1, different);
    }

    @Test
    public void column() {
        Exp<String> e1 = $col("a");
        Exp<String> e2 = $col("a");
        Exp<String> e3 = $col("a");
        Exp<String> different = $col("b");

        assertEqualsContract(e1, e2, e3);
        assertNotEquals(e1, different);
    }

    @Test
    public void exp0() {
        Exp<Integer> e1 = rowNum();
        Exp<Integer> e2 = rowNum();
        Exp<Integer> e3 = rowNum();

        assertEqualsContract(e1, e2, e3);
    }

    @Test
    public void exp1() {
        Exp<Boolean> e1 = $col("a").eq("test").not();
        Exp<Boolean> e2 = $col("a").eq("test").not();
        Exp<Boolean> e3 = $col("a").eq("test").not();
        Exp<Boolean> different = $col("b").eq("test").not();

        assertEqualsContract(e1, e2, e3);
        assertNotEquals(e1, different);
    }

    @Test
    public void exp2() {
        NumExp<?> e1 = $int(1).add($int(2));
        NumExp<?> e2 = $int(1).add($int(2));
        NumExp<?> e3 = $int(1).add($int(2));
        NumExp<?> different = $int(1).add($int(3));

        assertEqualsContract(e1, e2, e3);
        assertNotEquals(e1, different);
    }

    @Test
    public void exp3() {
        Exp<Integer> e1 = Exp.ifExp($col("a").eq("test"), $int(1), $int(2));
        Exp<Integer> e2 = Exp.ifExp($col("a").eq("test"), $int(1), $int(2));
        Exp<Integer> e3 = Exp.ifExp($col("a").eq("test"), $int(1), $int(2));
        Exp<Integer> different = Exp.ifExp($col("a").eq("test"), $int(1), $int(3));

        assertEqualsContract(e1, e2, e3);
        assertNotEquals(e1, different);
    }

    @Test
    public void ifExp() {
        Exp<Integer> e1 = Exp.ifExp($col("a").eq("test"), $int(1), $int(2));
        Exp<Integer> e2 = Exp.ifExp($col("a").eq("test"), $int(1), $int(2));
        Exp<Integer> e3 = Exp.ifExp($col("a").eq("test"), $int(1), $int(2));
        Exp<Integer> different1 = Exp.ifExp($col("b").eq("test"), $int(1), $int(2));
        Exp<Integer> different2 = Exp.ifExp($col("a").eq("test"), $int(2), $int(2));
        Exp<Integer> different3 = Exp.ifExp($col("a").eq("test"), $int(1), $int(3));

        assertEqualsContract(e1, e2, e3);
        assertNotEquals(e1, different1);
        assertNotEquals(e1, different2);
        assertNotEquals(e1, different3);
    }

    @Test
    public void ifNullExp() {
        Exp<Integer> e1 = ifNull($int("a"), $int(1));
        Exp<Integer> e2 = ifNull($int("a"), $int(1));
        Exp<Integer> e3 = ifNull($int("a"), $int(1));
        Exp<Integer> different1 = ifNull($int("b"), $int(1));
        Exp<Integer> different2 = ifNull($int("a"), $int(2));

        assertEqualsContract(e1, e2, e3);
        assertNotEquals(e1, different1);
        assertNotEquals(e1, different2);
    }

    @Test
    public void notCondition() {
        Exp<Boolean> e1 = not($col("a").eq("test"));
        Exp<Boolean> e2 = not($col("a").eq("test"));
        Exp<Boolean> e3 = not($col("a").eq("test"));
        Exp<Boolean> different = not($col("b").eq("test"));

        assertEqualsContract(e1, e2, e3);
        assertNotEquals(e1, different);
    }

    @Test
    public void scalarExp() {
        ScalarExp<Integer> e1 = new IntScalarExp(1);
        ScalarExp<Integer> e2 = new IntScalarExp(1);
        ScalarExp<Integer> e3 = new IntScalarExp(1);
        ScalarExp<Integer> different = new IntScalarExp(2);

        assertEqualsContract(e1, e2, e3);
        assertNotEquals(e1, different);

        // Test instanceof behavior with other class
        ScalarExp<Integer> scalarExp = new ScalarExp<>(1, Integer.class);
        assertEquals(e1, scalarExp, "ScalarExp subclass should equal its parent with same values");
        assertEquals(scalarExp, e1, "ScalarExp should equal its subclass instance with same values");
    }

    @Test
    public void shiftExp() {
        Exp<?> e1 = $col("a").shift(1);
        Exp<?> e2 = $col("a").shift(1);
        Exp<?> e3 = $col("a").shift(1);
        Exp<?> different1 = $col("b").shift(1);
        Exp<?> different2 = $col("a").shift(2);

        assertEqualsContract(e1, e2, e3);
        assertNotEquals(e1, different1);
        assertNotEquals(e1, different2);
    }
}
