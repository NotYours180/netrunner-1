package it.ck.cyberdeck;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import it.ck.cyberdeck.app.DeckService;
import it.ck.cyberdeck.app.DeckServiceImpl;
import it.ck.cyberdeck.fixtures.Fixtures;
import it.ck.cyberdeck.model.*;
import it.ck.cyberdeck.persistence.filesystem.FileSystemLibraryCardGateway;

import org.junit.Test;

public class DeckServiceTest {

	 
	
	private LibraryCardGateway gw = new FileSystemLibraryCardGateway(Fixtures.RAW_PATH);
	private DeckService ds = new DeckServiceImpl(gw);
	
	@Test
  public void withTheDeckServiceICanCreateADeck() throws Exception {
	  Identity identity = new Identity("identity", Side.RUNNER, Faction.ANARCH, 45, 15, null);
		String deckName = "testDeck";
		Deck deck = ds.createDeck(identity, deckName);
		assertThat(deck, equalTo(new Deck(identity, deckName)));
  }
	
}
