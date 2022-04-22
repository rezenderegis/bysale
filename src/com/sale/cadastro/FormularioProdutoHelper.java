package com.sale.cadastro;

import com.sale.cadastro.R;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Produto;
import com.sale.modelo.ProdutoDTO;

import android.widget.EditText;

public class FormularioProdutoHelper {

	private EditText editNomeProduto;
	private EditText editPreco;
	private EditText editCategoria;

	private ProdutoDTO produto;

	public FormularioProdutoHelper(FormularioProdutoActivity formularioProduto) {

		// CONSTRUTOR - FAZENDO COM QUE O
		editNomeProduto = (EditText) formularioProduto
				.findViewById(R.id.nomeProduto);
		editPreco = (EditText) formularioProduto
				.findViewById(R.id.precoProduto);
		// editCategoria = (EditText)
		// formularioProduto.findViewById(R.id.spinner);
		produto = new ProdutoDTO();
	}

	public ProdutoDTO pegaProdutoFormulario() {

		String precoAntes = editPreco.getText().toString();

		if (precoAntes.isEmpty()) {
			precoAntes = "0.0";
		}
		// Long categoria = Long.parseLong(editCategoria.getText().toString());
		// Long categoria = Long.parseLong(editCategoria.getText().toString());
		Double preco = Double.parseDouble(precoAntes);
		produto.setNomeProduto(editNomeProduto.getText().toString());
		produto.setPrecoProduto(preco);
		// produto.setCategoria(categoria);
		return produto;

	}

	public void editarProduto(ProdutoDTO produtoEditar) {
		produto = produtoEditar;
		editNomeProduto.setText(produtoEditar.getNomeProduto());
		editPreco.setText(produto.getPrecoProduto().toString());
		// editCategoria.setText(produtoEditar.getCategoria().toString());
		// editNomeProduto.setText(produtoEditar.getNomeProduto());
		// editNomeProduto.setText(produtoEditar.getNomeProduto());

	}

}
