package com.sale.cadastro.util;
import com.sale.cadastro.R;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DialogCustomizado extends DialogFragment{

	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View rootView = inflater.inflate(R.layout.total_produtos, container, false);
	        getDialog().setTitle("Simple Dialog");
	        return rootView;
	    }
	
}
