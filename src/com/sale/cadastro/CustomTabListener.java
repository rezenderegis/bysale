package com.sale.cadastro;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.sale.modelo.Pessoa;

public class CustomTabListener<T extends Fragment> implements TabListener {
	private Fragment mFragment;
	private final Activity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	private String tabProduto;
	private Long idVendaCliente;
	private String clienteVenda;
	public CustomTabListener(Activity activity, String tag, Class<T> clz,
			String tabProduto, Long idVendaCliente, String clienteVenda) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		this.tabProduto = tabProduto;
		this.idVendaCliente = idVendaCliente;
		this.clienteVenda = clienteVenda;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Nothing special to do here for this application
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		if (mFragment == null) {
			final Pessoa pessoaSelecionada = (Pessoa) mActivity.getIntent()
					.getSerializableExtra("clienteSelecionado");

			final Bundle paramters = new Bundle();
			paramters.putSerializable("cliente", clienteVenda);
			paramters.putString("tabProduto", tabProduto);
			paramters.putLong("idVendaCliente", idVendaCliente);
			mFragment = Fragment.instantiate(mActivity, mClass.getName());
			mFragment.setArguments(paramters);

			ft.add(android.R.id.content, mFragment, mTag);

		} else {
			ft.attach(mFragment);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (mFragment != null)
			ft.detach(mFragment);
	}
}