package com.sale.cadastro;

import java.util.List;
import com.sale.cadastro.R;
import com.sale.dao.SaleDAO;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Produto;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;

public class ListaProdutoActivity extends Activity {

	Number totalProduto = 0;

	public Produto produto;
	public Pessoa alunoParaSerAlterado;
	String idvendaclienteInsere = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.listagem_alunos);

		
		SaleDAO dao = new SaleDAO(this);

		List<Produto> produto = dao.getListaProduto();
		dao.close();

		int layout = android.R.layout.simple_list_item_1;

		ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(this, layout,
				produto);

		ListView lista = (ListView) findViewById(R.id.lista);

		View header = (View) this.getLayoutInflater().inflate(R.layout.listagem_produtos_cabecalho, null);
		lista.addHeaderView(header);
		lista.setAdapter(adapter);

		registerForContextMenu(lista);

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				// TODO Auto-generated method stub

				Intent intent = getIntent();
				alunoParaSerAlterado = (Pessoa) intent
						.getSerializableExtra("clienteSelecionado");

				final SaleDAO dao = new SaleDAO(ListaProdutoActivity.this);
				dao.close();
				final Produto produto = (Produto) adapter
						.getItemAtPosition(posicao);
				// dao.salvaVendaNew();

				final String idvendacliente = dao
						.consultaVendaAberta(alunoParaSerAlterado.getId());

				if (idvendacliente == null) { // SE NÃO TIVER NENHUM NA SITUACAO
												// 2 (PENDENTE), INSERE UMA NOVA
												// VENDA

					dao.salvaVendaCliente(alunoParaSerAlterado.getId());
				}

				idvendaclienteInsere = dao
						.consultaVendaAberta(alunoParaSerAlterado.getId());

				// POPUP

				LayoutInflater li = LayoutInflater
						.from(ListaProdutoActivity.this);
				View promptsView = li.inflate(R.layout.quantidade, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						ListaProdutoActivity.this);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final NumberPicker userInput = (NumberPicker) promptsView
						.findViewById(R.id.numberPicker1);
				userInput.setMaxValue(10); // max value 100
				userInput.setMinValue(1); // min value 0
				// set dialog message
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {

										// Log.e("","New Quantity Value : "+userInput.getValue());
										totalProduto = userInput.getValue();

										// CONTROLE DE SINCRONIZAÇÃO: SÓ IRÁ
										// SINCRONIZAR SE ESSE VALOR ESTIVER
										// PREENCHIDO.
										dao.salvaControleSincronizacaoCliente(idvendacliente);
										// /

										/*dao.salvaVendaNew(produto,
												alunoParaSerAlterado.getId(),
												idvendaclienteInsere,
												totalProduto);*/
										dao.close();

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

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

				// FIM POPUP

				// finish();
			}

		});

	}

}
