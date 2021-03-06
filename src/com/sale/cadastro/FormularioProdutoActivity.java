package com.sale.cadastro;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sale.adapter.MySpinnerAdapter;
import com.sale.cadastro.R;
import com.sale.dao.SaleDAO;
import com.sale.modelo.CategoriaProduto;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Produto;
import com.sale.modelo.ProdutoDTO;

public class FormularioProdutoActivity extends Activity implements
		OnItemSelectedListener {

	private FormularioProdutoHelper helper;
	private ProdutoDTO produto;
	private Spinner spinner;
	CategoriaProduto categoriaSelecionada;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.formulario_produto);
		getActionBar().setTitle("Cadastro de Novo Produto");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		
		// � o intent que nos faz chegar na activity
		Intent intent = getIntent();
		 final ProdutoDTO produtoParaEditar = (ProdutoDTO) intent
				.getSerializableExtra("produtoParaEditar");

		// Primeira parte da adi��o do combo box, que se chama spinner
		spinner = (Spinner) findViewById(R.id.spinner);
		// Fim da Primeira parte

		// Pegar os par�metros do formul�rio
		helper = new FormularioProdutoHelper(this);

		Button botao = (Button) findViewById(R.id.botaoNovoProduto);

		// Segunda parte do combo box
		spinner.setOnItemSelectedListener(this);

		trazerDadosComboCategoria(produtoParaEditar);
		CategoriaProduto categoriaProdutoEdicao = (CategoriaProduto) spinner
				.getSelectedItem();

		if (produtoParaEditar != null) {

			botao.setText("Alterar");
			// spinner.setSelection(2);

			helper.editarProduto(produtoParaEditar);
			// produto.setId(idEdicao);
			String idEdicao = categoriaProdutoEdicao.getIdCategoria();

		}

		botao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ProdutoDTO produto = helper.pegaProdutoFormulario();
				SaleDAO dao = new SaleDAO(FormularioProdutoActivity.this);
				String idCategoria = categoriaSelecionada.getIdCategoria();

				// Validar se o nome e o preco vieram preenchidos
				if (produto.getNomeProduto().isEmpty()
						|| produto.getPrecoProduto() == 0.0) {
					Toast.makeText(FormularioProdutoActivity.this,
							"Informe todos os dados!", Toast.LENGTH_SHORT)
							.show();
				} else {
					/*
					 * Produto novo. o par�metro produtoParaEditar vem nulo.
					 * Essa vari�vel � preenchida pelo m�todo
					 * onItemSelectedListener.
					 */
					if (produtoParaEditar == null) {

						dao.salvaProduto(produto, idCategoria);
					} else {
						// Edi��o de produto.
						produto.setIdProduto(produto.getIdProduto());

						dao.alteraProduto(produto, idCategoria);
					}

					dao.close();
					finish();
				}
			}
		});

	}

	private void trazerDadosComboCategoria(final ProdutoDTO produto) {
		// TODO Auto-generated method stub
		SaleDAO db = new SaleDAO(getApplicationContext());

		// Spinner Drop down elements
		List<CategoriaProduto> lables = db.trazObjetoCategoriaCombo();

		// Creating adapter for spinner

		MySpinnerAdapter dataAdapter = new MySpinnerAdapter(
				FormularioProdutoActivity.this, R.layout.spinner_layout, lables);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);
		spinner.setOnItemSelectedListener(onItemSelectedListener);

		if (produto != null) {
			// No caso de ser edi��o, essa fun��o preenche como padr�o o que
			// est� cadastrado no banco de dados.
			spinner.setSelection(lables.indexOf(new CategoriaProduto(produto
					.getIdCategoria(), "")));
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		// CategoriaProduto categoria = new CategoriaProduto();
		// On selecting a spinner item
		// categoria = (CategoriaProduto) parent.getSelectedItem();
		String label = parent.getItemAtPosition(position).toString();

		// categoria = (CategoriaProduto) parent.getItemAtPosition(position);

		Long idItem = parent.getItemIdAtPosition(position);
		// Showing selected spinner item
		Toast.makeText(parent.getContext(), "You selected: " + label,
				Toast.LENGTH_LONG).show();

	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		int itemClicado = item.getItemId();
		if ( itemClicado == android.R.id.home) {
			finish();
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub

			// Quando o usu�rio seleciona a categoria, setamos o valor dessa
			// vari�vel de inst�ncia
			categoriaSelecionada = (CategoriaProduto) (parent
					.getItemAtPosition(position));

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

}
