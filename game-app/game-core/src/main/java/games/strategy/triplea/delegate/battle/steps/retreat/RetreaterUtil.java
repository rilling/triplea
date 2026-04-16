package games.strategy.triplea.delegate.battle.steps.retreat;

import static games.strategy.triplea.delegate.battle.BattleState.Side.OFFENSE;

import games.strategy.engine.data.CompositeChange;
import games.strategy.engine.data.Territory;
import games.strategy.engine.data.Unit;
import games.strategy.engine.data.changefactory.ChangeFactory;
import games.strategy.triplea.delegate.Matches;
import games.strategy.triplea.delegate.battle.BattleState;
import games.strategy.triplea.formatter.MyFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.triplea.java.collections.CollectionUtils;

@UtilityClass
class RetreaterUtil {

    static void applyRetreatChanges(
            final Territory retreatTo,
            final Collection<Unit> retreatUnits,
            final BattleState battleState,
            final CompositeChange change,
            final List<Retreater.RetreatHistoryChild> historyChildren) {

        final Collection<Unit> airRetreating =
                CollectionUtils.getMatches(
                        retreatUnits,
                        Matches.unitIsAir().and(Matches.unitIsOwnedBy(battleState.getPlayer(OFFENSE))));

        if (!airRetreating.isEmpty()) {
            battleState.retreatUnits(OFFENSE, airRetreating);
            final String transcriptText = MyFormatter.unitsToText(airRetreating) + " retreated";
            historyChildren.add(
                    Retreater.RetreatHistoryChild.of(transcriptText, new ArrayList<>(airRetreating)));
        }

        final Collection<Unit> nonAirRetreating = new HashSet<>(retreatUnits);
        nonAirRetreating.removeAll(airRetreating);
        nonAirRetreating.addAll(battleState.getDependentUnits(nonAirRetreating));

        if (!nonAirRetreating.isEmpty()) {
            battleState.retreatUnits(OFFENSE, nonAirRetreating);
            historyChildren.add(
                    Retreater.RetreatHistoryChild.of(
                            MyFormatter.unitsToText(nonAirRetreating) + " retreated to " + retreatTo.getName(),
                            new ArrayList<>(nonAirRetreating)));
            change.add(ChangeFactory.moveUnits(battleState.getBattleSite(), retreatTo, nonAirRetreating));
        }
    }
}