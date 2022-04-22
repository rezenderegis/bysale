package com.sale.cadastro;

import java.util.List;

import com.sale.dao.SaleDAO;
import com.sale.modelo.Produto;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/** This is a listfragment class */
public class PetiscosFragment extends ListFragment {

	/** An array of items to display in ArrayList */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/** Creating array adapter to set data in listview */

		SaleDAO dao = new SaleDAO(getActivity().getBaseContext());

		List<Produto> produto = dao.getListaProdutoTab("3");

		dao.close();

		int layout = android.R.layout.simple_list_item_1;

		ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(getActivity()
				.getBaseContext(), layout, produto);

		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(getActivity().getBaseContext(),
		// android.R.layout.simple_list_item_1, android_versions);

		/** Setting the array adapter to the listview */

		/** Setting the array adapter to the listview */
		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();

		/** Setting the multiselect choice mode for the listview */
		getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
	}

}
