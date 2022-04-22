package com.sale.cadastro;

import com.sale.cadastro.R;
import com.sale.modelo.CategoriaProduto;

import android.widget.EditText;

public class FormularioCategoriaHelper {

	private EditText nomeCategoria;
	private CategoriaProduto categoriaProduto;

	public FormularioCategoriaHelper(
			FormularioCategoriaActivity formularioCategoriaActivity) {
		// TODO Auto-generated constructor stub

		nomeCategoria = (EditText) formularioCategoriaActivity
				.findViewById(R.id.categoria_produto);
		categoriaProduto = new CategoriaProduto("0", nomeCategoria.toString());
	}

	public CategoriaProduto trazCategoriaFormulario() {
		// TODO Auto-generated method stub
		categoriaProduto.setNomeCategoria(nomeCategoria.getText().toString());

		return categoriaProduto;
	}

	public void preencherFormularioCategoria(
			CategoriaProduto categoriaParaEditar) {
		// TODO Auto-generated method stub

		categoriaProduto = categoriaParaEditar;

		nomeCategoria.setText(categoriaParaEditar.getNomeCategoria());

	}

}
