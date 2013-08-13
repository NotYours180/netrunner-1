package it.ck.cyberdeck.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class CardTest {

  @Test
  @Ignore //creation and control add a neutral card with reputation!!!
  public void aNeutralCardNeverAddToReputation() {
    Card card = new Card("Name", Side.RUNNER, Faction.NEUTRAL, 0, null);
    Identity identity = new Identity("name", Side.RUNNER, Faction.ANARCH, 45, 15);
    assertThat(card.calculateInfluenceCost(identity), is(0));
  }
  
  public void aNeutralCardAddToReputation() {
    Card card = new Card("Name", Side.RUNNER, Faction.NEUTRAL, 2, null);
    Identity identity = new Identity("name", Side.RUNNER, Faction.ANARCH, 45, 15);
    assertThat(card.calculateInfluenceCost(identity), is(2));
  }
  
  @Test
  public void aCardOfTheSameFactionAsTheIdentityNeverAddsToReputation(){
    Card card = new Card("Name", Side.RUNNER, Faction.ANARCH, 5, null);
    Identity identity = new Identity("name", Side.RUNNER, Faction.ANARCH, 45, 15);
    assertThat(card.calculateInfluenceCost(identity), is(0));
  }
  
  @Test 
  public void aCardOfDifferentFactionAddToReputation(){
    Card card = new Card("Name", Side.RUNNER, Faction.SHAPER, 5, null);
    Identity identity = new Identity("name", Side.RUNNER, Faction.ANARCH, 45, 15);
    assertThat(card.calculateInfluenceCost(identity), is(5));
  }

}
