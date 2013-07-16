package it.ck.cyberdeck.presentation;

import java.util.List;

import it.ck.cyberdeck.R;
import it.ck.cyberdeck.R.layout;
import it.ck.cyberdeck.R.menu;
import it.ck.cyberdeck.model.*;
import it.ck.cyberdeck.presentation.adapter.CardLibraryExpandableListAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class AddExpandableCardActivity extends Activity {

	private Deck deck;
	private CardLibraryExpandableListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expandable_card);
		
		ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
		CardLibrary cl = ((CyberDeckApp) getApplication()).getDeckService().loadCardLibrary();
		
		deck = (Deck) getIntent()
				.getSerializableExtra("deck");
		
		List<CardGroup> values = cl.getCardGroups(deck.getIdentity().side());
		adapter = new CardLibraryExpandableListAdapter(this, values);
		expListView.setAdapter(adapter);
		

    expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
      public boolean onChildClick(ExpandableListView parent, View v,
          int groupPosition, int childPosition, long id) {
	      
			  Card cardToBeAdded = (Card) getListAdapter().getChild(groupPosition, childPosition);
			  deck.add(cardToBeAdded);
				return true;
      }
    });
	}

	protected CardLibraryExpandableListAdapter getListAdapter() {
		return adapter;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
