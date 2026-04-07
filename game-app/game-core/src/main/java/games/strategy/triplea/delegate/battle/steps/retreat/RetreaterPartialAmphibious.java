package games.strategy.triplea.delegate.battle.steps.retreat;

import static games.strategy.triplea.delegate.battle.BattleState.Side.OFFENSE;
import static games.strategy.triplea.delegate.battle.BattleState.UnitBattleFilter.ALIVE;

import games.strategy.engine.data.CompositeChange;
import games.strategy.engine.data.Territory;
import games.strategy.engine.data.Unit;
import games.strategy.triplea.delegate.Matches;
import games.strategy.triplea.delegate.battle.BattleState;
import games.strategy.triplea.delegate.battle.MustFightBattle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import org.triplea.java.collections.CollectionUtils;

@AllArgsConstructor
class RetreaterPartialAmphibious implements Retreater {

  private final BattleState battleState;

  @Override
  public Collection<Unit> getRetreatUnits() {
    return CollectionUtils.getMatches(
            battleState.filterUnits(ALIVE, OFFENSE), Matches.unitWasNotAmphibious());
  }

  @Override
  public Collection<Territory> getPossibleRetreatSites(final Collection<Unit> retreatUnits) {
    final Collection<Territory> allRetreatTerritories = battleState.getAttackerRetreatTerritories();
    return retreatUnits.stream().anyMatch(Matches.unitIsLand())
            ? CollectionUtils.getMatches(allRetreatTerritories, Matches.territoryIsLand())
            : new ArrayList<>(allRetreatTerritories);
  }

  @Override
  public MustFightBattle.RetreatType getRetreatType() {
    return MustFightBattle.RetreatType.PARTIAL_AMPHIB;
  }

  @Override
  public RetreatChanges computeChanges(final Territory retreatTo) {
    final Collection<Unit> retreatUnits = getRetreatUnits();

    final CompositeChange change = new CompositeChange();
    final List<RetreatHistoryChild> historyChildren = new ArrayList<>();

    RetreaterUtil.applyRetreatChanges(retreatTo, retreatUnits, battleState, change, historyChildren);

    return RetreatChanges.of(change, historyChildren);
  }
}