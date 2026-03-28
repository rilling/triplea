package games.strategy.triplea.delegate.power.calculator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestCombatUtils {
  public static void assertStrengthValue(int expected, int actual) {
    assertThat(
        "Strength starts at 3, friendly adds 3, enemy removes 2, territory adds 1: total 5",
        actual,
        is(expected));
  }
}
