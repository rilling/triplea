package games.strategy.triplea.ui.mapdata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Properties;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

final class MapDataTest {
  @Nested
  final class GetPropertyTest {
    private static final int DEFAULT_VALUE = 42;
    private static final String NAME = "name";

    private final Properties properties = new Properties();

    private int getProperty() {
      return MapData.getProperty(properties, NAME, () -> DEFAULT_VALUE, Integer::parseInt);
    }

    @Test
    void shouldReturnValueWhenPropertyExists() {
      properties.setProperty(NAME, "76");

      assertThat(getProperty(), is(76));
    }

    @Test
    void shouldReturnDefaultValueWhenPropertyDoesNotExist() {
      properties.remove(NAME);

      assertThat(getProperty(), is(DEFAULT_VALUE));
    }

    @Test
    void shouldReturnDefaultValueWhenPropertyExistsButIsMalformed() {
      properties.setProperty(NAME, "malformed");

      assertThat(getProperty(), is(DEFAULT_VALUE));
    }
  }

  @Nested
  final class ShouldShowPlayerTest {
    private static final String PROPERTY_KEY = "dont_show_players";

    private final Properties properties = new Properties();

    @Test
    void shouldShowPlayerWhenPropertyNotSet() {
      assertThat(
          MapData.isNotInCommaSeparatedProperty(properties, PROPERTY_KEY, "Germany"), is(true));
    }

    @Test
    void shouldHidePlayerWhenInExclusionList() {
      properties.setProperty(PROPERTY_KEY, "Weather,NaturalDisaster");

      assertThat(
          MapData.isNotInCommaSeparatedProperty(properties, PROPERTY_KEY, "Weather"), is(false));
    }

    @Test
    void shouldShowPlayerWhenNotInExclusionList() {
      properties.setProperty(PROPERTY_KEY, "Weather,NaturalDisaster");

      assertThat(
          MapData.isNotInCommaSeparatedProperty(properties, PROPERTY_KEY, "Germany"), is(true));
    }

    @Test
    void shouldHideMultiplePlayersInExclusionList() {
      properties.setProperty(PROPERTY_KEY, "Weather,NaturalDisaster,SpecialNeutral");

      assertThat(
          MapData.isNotInCommaSeparatedProperty(properties, PROPERTY_KEY, "NaturalDisaster"),
          is(false));
      assertThat(
          MapData.isNotInCommaSeparatedProperty(properties, PROPERTY_KEY, "SpecialNeutral"),
          is(false));
    }
  }
  @Nested
  final class ShouldShowInTechTest {
    private static final String PROPERTY_KEY = "player.doNotShowInTechTable";

    private final Properties properties = new Properties();

    @Test
    void shouldShowPlayerWhenPropertyNotSet() {
      assertThat(
          MapData.isNotInCommaSeparatedProperty(properties, PROPERTY_KEY, "Germany"), is(true));
    }

    @Test
    void shouldHidePlayerWhenInExclusionList() {
      properties.setProperty(PROPERTY_KEY, "Weather,NaturalDisaster");

      assertThat(
          MapData.isNotInCommaSeparatedProperty(properties, PROPERTY_KEY, "Weather"), is(false));
    }

    @Test
    void shouldShowPlayerWhenNotInExclusionList() {
      properties.setProperty(PROPERTY_KEY, "Weather,NaturalDisaster");

      assertThat(
          MapData.isNotInCommaSeparatedProperty(properties, PROPERTY_KEY, "Germany"), is(true));
    }
  }
}
