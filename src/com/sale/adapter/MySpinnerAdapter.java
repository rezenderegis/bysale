package com.sale.adapter;

import java.util.List;

import com.sale.modelo.CategoriaProduto;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//custom adapter
public class MySpinnerAdapter extends ArrayAdapter<CategoriaProduto> {

	private Context context;
	private List<CategoriaProduto> myObjs;

	public MySpinnerAdapter(Context context, int textViewResourceId,
			List<CategoriaProduto> lables) {
		super(context, textViewResourceId, lables);
		this.context = context;
		this.myObjs = lables;
	}

	public int getCount() {
		return myObjs.size();
	}

	public CategoriaProduto getItem(int position) {
		return myObjs.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(context);
		label.setText(myObjs.get(position).getNomeCategoria());
		return label;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(context);
		label.setText(myObjs.get(position).getNomeCategoria());
		return label;
	}

}
