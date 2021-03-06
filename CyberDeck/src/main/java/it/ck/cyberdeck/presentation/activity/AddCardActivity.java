package it.ck.cyberdeck.presentation.activity;

import it.ck.cyberdeck.R;
import it.ck.cyberdeck.model.Card;
import it.ck.cyberdeck.model.CardLibrary;
import it.ck.cyberdeck.model.group.ElementGroup;
import it.ck.cyberdeck.presentation.BaseDeckActivity;
import it.ck.cyberdeck.presentation.CyberDeckApp;
import it.ck.cyberdeck.presentation.adapter.CardLibraryExpandableListAdapter;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class AddCardActivity extends BaseDeckActivity {

	private CardLibraryExpandableListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expandable_card);

		ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
		CardLibrary cl = ((CyberDeckApp) getApplication()).getDeckService()
				.loadCardLibrary();

		List<ElementGroup<Card>> values = cl
				.getCardGroupsWithoutIdentities(getDeck().getIdentity());
		adapter = new CardLibraryExpandableListAdapter(this, values);
		expListView.setAdapter(adapter);

		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				Card cardToBeAdded = (Card) getListAdapter().getChild(
						groupPosition, childPosition);
				presenter.addCard(cardToBeAdded);
				return true;
			}

		});
	}

	protected CardLibraryExpandableListAdapter getListAdapter() {
		return adapter;
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra(BaseDeckActivity.DECK_ARG_ID, getDeck());
		setResult(RESULT_OK, intent);
		super.onBackPressed();
	}

}
