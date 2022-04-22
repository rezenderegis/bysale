package com.sale.cadastro;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.sale.cadastro.R;
import com.sale.modelo.Pessoa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

public class FormularioHelper {

	private EditText editNome;
	private EditText editTelefone;
	private EditText editEndereco;
	// private RatingBar ratinNota;
	private EditText editSite;
	private ImageView foto;
	private Pessoa pessoa;
	private EditText editEmail;
	private EditText editCPF_CNPJ;
	private EditText editObservacao;
	
	public FormularioHelper(Formulario formulario) {

		// TODO Auto-generated constructor stub
		// BUSCAR OS ELEMENTOS CRIADOS NO LAYOUT

		editNome = (EditText) formulario.findViewById(R.id.nome);
		editSite = (EditText) formulario.findViewById(R.id.site);
		editTelefone = (EditText) formulario.findViewById(R.id.telefone);
		editEndereco = (EditText) formulario.findViewById(R.id.endereco);
		// ratinNota = (RatingBar) formulario.findViewById(R.id.nota);
		foto = (ImageView) formulario.findViewById(R.id.foto);
		editCPF_CNPJ = (EditText) formulario.findViewById(R.id.cpf_cnpj);
		editEmail = (EditText) formulario.findViewById(R.id.e_mail);
		editObservacao = (EditText) formulario.findViewById(R.id.observacao);
		pessoa = new Pessoa();
	}

	public Pessoa pegaAlunoDoFormulario() {
		// TODO Auto-generated method stub

		pessoa.setNome(editNome.getText().toString());
		pessoa.setSite(editSite.getText().toString());
		pessoa.setTelefone(editTelefone.getText().toString());
		pessoa.setEndereco(editEndereco.getText().toString());
		pessoa.setCpf_cnpj(editCPF_CNPJ.getText().toString());
		pessoa.setEmail(editEmail.getText().toString());
		pessoa.setObservacao(editObservacao.getText().toString());
		// pessoa.setNota(Double.valueOf(ratinNota.getRating()));

		return pessoa;
	}

	public void colocaAlunoNoFormulario(Pessoa alunoParaSerAlterado) {
		pessoa = alunoParaSerAlterado;
		editNome.setText(alunoParaSerAlterado.getNome());
		editSite.setText(alunoParaSerAlterado.getSite());
		editTelefone.setText(alunoParaSerAlterado.getTelefone());
		editEndereco.setText(alunoParaSerAlterado.getEndereco());
		// ratinNota.setRating(alunoParaSerAlterado.getNota().floatValue());
		editCPF_CNPJ.setText(alunoParaSerAlterado.getCpf_cnpj());
		editEmail.setText(alunoParaSerAlterado.getEmail());
		editObservacao.setText(alunoParaSerAlterado.getObservacao());
		if (pessoa.getFoto() != null) {

			carregaImagem(alunoParaSerAlterado.getFoto());
		}
	}

	// FOI CRIADO PARA QUE DO FORMUL�RIO POSSAMOS ACESSAR A FOTO DO ALUNO
	public ImageView getFoto() {
		return foto;
	}

	public void carregaImagem(String caminhoArquivo) {
		// TODO Auto-generated method stub
		// try {
		pessoa.setFoto(caminhoArquivo);

		Bitmap imagem = BitmapFactory.decodeFile(caminhoArquivo);

		Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100,
				true);

		foto.setImageBitmap(imagemReduzida);
		// } catch (Exception e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	/*
	 * public void carregaImagem(String caminhoArquivo) { // TODO Auto-generated
	 * method stub pessoa.setFoto(caminhoArquivo);
	 * 
	 * Bitmap imagem = BitmapFactory.decodeFile(caminhoArquivo);
	 * 
	 * Matrix matrix = new Matrix(); matrix.postRotate(90);
	 * 
	 * 
	 * Bitmap imagemReduzida = Bitmap.createBitmap(imagem, 0, 0,
	 * imagem.getWidth(), imagem.getHeight(), matrix, true);
	 * 
	 * imagemReduzida.compress(Bitmap.CompressFormat.PNG, 100, out)
	 * foto.setImageBitmap(imagemReduzida);
	 * 
	 * 
	 * }
	 */
}
