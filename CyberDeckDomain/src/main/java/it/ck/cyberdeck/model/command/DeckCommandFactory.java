package it.ck.cyberdeck.model.command;

import it.ck.cyberdeck.model.Card;
import it.ck.cyberdeck.model.Deck;
import it.ck.cyberdeck.model.Notifier;

public interface DeckCommandFactory {

	Command createAddCardCommand(Card toAdd, Deck target,Notifier notifier);
	Command createRemoveCardCommand(Card toRemove, Deck target,Notifier notifier);
	Command createRemoveAllCommand(Card toRemove, Deck target,Notifier notifier);
}
