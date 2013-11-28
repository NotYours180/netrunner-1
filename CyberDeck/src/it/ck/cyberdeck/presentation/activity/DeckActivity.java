package it.ck.cyberdeck.presentation.activity;

import it.ck.cyberdeck.R;
import it.ck.cyberdeck.model.CardEntry;
import it.ck.cyberdeck.model.DeckStatus;
import it.ck.cyberdeck.presentation.BaseDeckActivity;
import it.ck.cyberdeck.presentation.DeckView;
import it.ck.cyberdeck.presentation.adapter.CardEntryListViewAdapter;
import it.ck.cyberdeck.presentation.fragment.CardDetailFragment;

import java.util.List;

import com.fortysevendeg.android.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.android.swipelistview.SwipeListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DeckActivity extends BaseDeckActivity implements DeckView {

	private TextView identityName;
	private TextView deckStatusLine;
	private SwipeListView cardList;
	private CardEntryListViewAdapter listViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck);

		identityName = (TextView) findViewById(R.id.deck_identity);
		deckStatusLine = (TextView) findViewById(R.id.deckStatusLine);
		
		listViewAdapter = new CardEntryListViewAdapter(
				this.getApplicationContext(), getDeck());

		cardList = (SwipeListView) findViewById(R.id.deck_cards);

		cardList.setAdapter(listViewAdapter);
		cardList.setSwipeListViewListener(new BaseSwipeListViewListener(){
			@Override
            public void onDismiss(int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) {
                    removeAll(position);
                }
            }
		});
		presenter.publish();
		cardList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CardEntry entry = listViewAdapter.getItem(position);
				Intent detailIntent = new Intent(DeckActivity.this,
						DeckDetailActivity.class);
				detailIntent.putExtra(BaseDeckActivity.DECK_ARG_ID,
						presenter.getDeck());
				detailIntent.putExtra(CardDetailFragment.ARG_ITEM_ID, entry);
				startActivity(detailIntent);

			}
		});

		registerForContextMenu(cardList);

		Button addButton = (Button) findViewById(R.id.add_card_button);

		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DeckActivity.this,
						AddCardActivity.class);
				intent.putExtra(DECK_ARG_ID, presenter.getDeck());
				startActivityForResult(intent, REQUEST_CODE);
			}

		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.deck_popoup_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.removeCard:
			removeCard(info.position);
			return true;
		case R.id.reoveAll:
			removeAll(info.position);
			return true;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void removeCard(int position) {
		CardEntry cardEntry = getEntry(position);
		presenter.remove(cardEntry.getCard());
		presenter.publish();
	}

	private void removeAll(int position) {
		CardEntry cardEntry = getEntry(position);
		presenter.removeAll(cardEntry.getCard());
		presenter.publish();
	}

	private CardEntry getEntry(int position) {
		return presenter.get(position);
	}

	@Override
	public void publishIdentityName(String identityName) {
		this.identityName.setText(identityName);
	}

	@Override
	public void publishDeckName(String deckName) {
		this.setTitle(deckName);
	}

	@Override
	public void publishEntryList(List<CardEntry> cards) {
		listViewAdapter.clear();
		for (CardEntry entry : cards){
			listViewAdapter.add(entry);
		}
	}

	@Override
	public void publishDeckStatus(DeckStatus deckStatus) {

		String statusLine = "";
		statusLine += "Card count: "+ String.valueOf(deckStatus.cardCount()) + "/" + deckStatus.minDeckSize();
		statusLine += "\t\tRep: " + deckStatus.getReputation() + "/" + deckStatus.getReputationCap();
		if(presenter.getDeck().isCorpDeck())
			statusLine += "\nAgenda points: " + String.valueOf(deckStatus.getAgendaPoints()) + " " + deckStatus.getAgendaRange(); 
		deckStatusLine.setText(statusLine);
		
	}

}
