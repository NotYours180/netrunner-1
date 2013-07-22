package it.ck.cyberdeck.app;

import java.util.List;

import it.ck.cyberdeck.model.*;

public class CachedDeckServiceImpl implements DeckService{
	
	private DeckService ds;

	private CardLibrary cl;
	
	public CachedDeckServiceImpl(DeckService ds) {
	  this.ds = ds;
  }

	@Override
  public Deck createDeck(Identity identity, String name) {
	  return ds.createDeck(identity, name);
  }

	@Override
  public CardLibrary loadCardLibrary() {
		if(cl == null){
			cl = ds.loadCardLibrary();
		}
	  return cl;
  }

	@Override
  public List<String> deckNames() {
	  return ds.deckNames();
  }

	
}
