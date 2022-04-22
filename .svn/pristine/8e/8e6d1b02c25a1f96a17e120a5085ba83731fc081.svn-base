package com.sale.cadastro;

import com.sale.cadastro.R;
import com.sale.dao.SaleDAO;
import com.sale.modelo.CategoriaProduto;
import com.sale.modelo.Pessoa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FormularioCategoriaActivity extends Activity {

	private FormularioCategoriaHelper helperCategoria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.formulario_categoria_produto);
		getActionBar().setTitle("Nova Categoria");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// Pegar o produto que ser‡ editato

		Intent intent = getIntent();
		final CategoriaProduto categoriaParaEditar = (CategoriaProduto) intent
				.getSerializableExtra("categoriaParaEditar");

		helperCategoria = new FormularioCategoriaHelper(this);

		Button botao = (Button) findViewById(R.id.botao_salva_categoria_produto);

		if (categoriaParaEditar != null) {

			botao.setText("Editar Categoria");
			helperCategoria.preencherFormularioCategoria(categoriaParaEditar);
		}

		botao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CategoriaProduto categiriaProdutoSalvar = helperCategoria
						.trazCategoriaFormulario();
				SaleDAO dao = new SaleDAO(FormularioCategoriaActivity.this);

				if (categiriaProdutoSalvar.getNomeCategoria().isEmpty()) {
					Toast.makeText(FormularioCategoriaActivity.this,
							"Informe o nome da categoria!", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (categoriaParaEditar == null) {
						dao.salvaCategoria(categiriaProdutoSalvar);
					} else {

						dao.alterarCategoria(categiriaProdutoSalvar);
					}

					dao.close();

					// VOLTAR PARA A TELA DE LISTAGEM
					finish(); // VOLTA PARA A LISTAGEM
				}

			}
		});

	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		int itemClicado = item.getItemId();

		if (itemClicado == android.R.id.home) {

			finish();
		}

		

		return super.onOptionsItemSelected(item);
	}

}
