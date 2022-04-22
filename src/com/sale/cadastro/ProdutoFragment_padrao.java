package com.sale.cadastro;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sale.adapter.ListaProdutosCadastradosIncluirVendaAdapter;
import com.sale.dao.SaleDAO;
import com.sale.modelo.Produto;

public class ProdutoFragment_padrao extends ListFragment {

	public String cliente;
	String idvendaclienteInsere = null;
	public Double totalProduto = 0.0;
	ListaProdutosCadastradosIncluirVendaAdapter adapter;
	String tabProduto;
	Long idVendaClienteParaInserir;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		final Bundle bundle = this.getArguments();
		cliente = (String) bundle.get("cliente");
		tabProduto = bundle.getString("tabProduto");
		idVendaClienteParaInserir = bundle.getLong("idVendaCliente");
		SaleDAO dao = new SaleDAO(getActivity().getBaseContext());

		List<Produto> produto = dao.getListaProdutoTab(tabProduto.toString());
		dao.close();

		int layout = android.R.layout.simple_list_item_1;
			
		adapter = new ListaProdutosCadastradosIncluirVendaAdapter(produto, getActivity(),
				getActivity().getBaseContext());

		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int posicao, long id) {
				final SaleDAO dao = new SaleDAO(ProdutoFragment_padrao.this
						.getActivity());

				final Produto produto = (Produto) adapter.getItem(posicao);
				// dao.salvaVendaNew();

				// POPUP

				LayoutInflater factory = LayoutInflater.from(getActivity().getBaseContext());
				final View alertDialogView = factory.inflate(
						R.layout.total_produto_venda, null);
				
				
				

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						ProdutoFragment_padrao.this.getActivity());

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(alertDialogView);
				alertDialogBuilder.setTitle("Quantidade do Produto");

				
				alertDialogBuilder
						.setCancelable(false)

						.setPositiveButton("Confirmar",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {

										// Log.e("","New Quantity Value : "+userInput.getValue());
										//totalProduto = userInput.getValue();
									EditText valor_informado = (EditText) alertDialogView
												.findViewById(R.id.total_produto_informado);
									
									if (valor_informado.getText().toString().trim().length()  == 0) {
										
										
											
											Toast.makeText(ProdutoFragment_padrao.this.getActivity(), "Informe a quantidade do produto!", Toast.LENGTH_SHORT).show();
											
										} else {
										
											totalProduto = Double.parseDouble(valor_informado.getText().toString());

											
								
								
									
										
										// final String idvendacliente =
										// dao.consultaVendaAberta(cliente);
										idvendaclienteInsere = idVendaClienteParaInserir
												.toString();

										if (idvendaclienteInsere.equals(null)) { // SE
																			// N�O
																			// TIVER
																			// NENHUM
																			// NA
																			// SITUACAO
											// 2 (PENDENTE), INSERE UMA NOVA
											// VENDA
											dao.salvaVendaCliente(cliente);
											idvendaclienteInsere = dao
													.consultaVendaAberta(cliente);

										}
										
									
										// CONTROLE DE SINCRONIZA��O: S� IR�
										// SINCRONIZAR SE ESSE VALOR ESTIVER
										// PREENCHIDO.
										Long idVendaClienteConroleSincronzacao = dao
												.existeControleSincronizacaoParaVenda(idvendaclienteInsere);
										if (idVendaClienteConroleSincronzacao == null) {
											dao.salvaControleSincronizacaoCliente(idvendaclienteInsere);
										}

										// /
										dao.salvaVendaNew(produto, cliente,
												idvendaclienteInsere,
												totalProduto);
										dao.close();
										getActivity().finish();
										
									} 
									}
								})
						.setNegativeButton("Cancelar",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// Ativando o Alert Dalog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// Mosta o Alert Dialog
				alertDialog.show();
				alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

				// Mudar a cor do bot�o
				((Button) alertDialog.findViewById(android.R.id.button2))
						.setBackgroundResource(R.color.botao_picker);
				((Button) alertDialog.findViewById(android.R.id.button1))
						.setBackgroundResource(R.color.botao_picker);
				((Button) alertDialog.findViewById(android.R.id.button1))
						.setTextColor(getResources().getColor(R.color.branco));
				((Button) alertDialog.findViewById(android.R.id.button2))
						.setTextColor(getResources().getColor(R.color.branco));

				// FIM POPUP

			}
		});

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		Produto produto = new Produto();
		super.onActivityCreated(savedInstanceState);

		// Toast.makeText(getActivity(), "Fragment"+getActivity().getTitle(),
		// Toast.LENGTH_LONG).show();

	}

}
