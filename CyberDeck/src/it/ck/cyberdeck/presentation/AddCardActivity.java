package it.ck.cyberdeck.presentation;

import it.ck.cyberdeck.R;
import it.ck.cyberdeck.app.DeckService;
import it.ck.cyberdeck.model.*;
import it.ck.cyberdeck.presentation.adapter.CardLibraryExpandableListAdapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import android.widget.ExpandableListView.OnChildClickListener;

public class AddCardActivity extends Activity {

	private Deck deck;
	private DeckService deckService;
	private CardLibraryExpandableListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expandable_card);
		
		deckService = ((CyberDeckApp)getApplication()).getDeckService();
		
		ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
		CardLibrary cl = ((CyberDeckApp) getApplication()).getDeckService().loadCardLibrary();
		
		deck = (Deck) getIntent()
				.getSerializableExtra("deck");
		
		List<CardGroup> values = cl.getCardGroups(deck.getIdentity());
		adapter = new CardLibraryExpandableListAdapter(this, values);
		expListView.setAdapter(adapter);
		

    expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
      public boolean onChildClick(ExpandableListView parent, View v,
          int groupPosition, int childPosition, long id) {
	      
			  Card cardToBeAdded = (Card) getListAdapter().getChild(groupPosition, childPosition);
			  try{
			  	deck.add(cardToBeAdded);
			  	deckService.saveDeck(deck);
			  	Toast toast = Toast.makeText(AddCardActivity.this, "Card " + cardToBeAdded.getName() + " added succesfully", Toast.LENGTH_SHORT);
			  	toast.show();
			  }catch (DeckException e){
			  	Toast toast = Toast.makeText(AddCardActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
			  	toast.show();
			  }
				return true;
      }
    });
	}

	protected CardLibraryExpandableListAdapter getListAdapter() {
		return adapter;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_expandable_card, menu);
		return true;
	}
	
	@Override
  public void onBackPressed() {
    Intent intent = new Intent();
    intent.putExtra("deck", deck);
    setResult(RESULT_OK, intent);  
	  super.onBackPressed();
  }

}