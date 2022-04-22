package com.sale.cadastro;

import java.io.File;

import com.sale.cadastro.R;
import com.sale.dao.SaleDAO;
import com.sale.modelo.Pessoa;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Formulario extends Activity {

	private FormularioHelper helper;
	private String caminhoArquivo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Vincula a activity ao layout
		setContentView(R.layout.formulario);
		getActionBar().setTitle("Novo Cliente");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// � o intent que nos faz chegar na activity
		Intent intent = getIntent();
		final Pessoa alunoParaSerAlterado = (Pessoa) intent
				.getSerializableExtra("alunoSelecionado");
		
		// Toast.makeText(this, "Pessoa: "+pessoa, Toast.LENGTH_LONG).show();
		
		helper = new FormularioHelper(this);

		
		//Desabilitar edição do CPF
		//if (alunoParaSerAlterado != null) {
			//EditText cpf = (EditText) findViewById(R.id.cpf_cnpj);
			//cpf.setEnabled(false);
		//}
		

		Button botao = (Button) findViewById(R.id.botao);

		if (alunoParaSerAlterado != null) {

			botao.setText("Alterar");

			// VAMOS COLOCAR OS ALUNOS NO FORMUL�RIO. QUEM MANIPULA A TELA � O
			// HELPER
			helper.colocaAlunoNoFormulario(alunoParaSerAlterado);

		}

		// PEGAR O ALUNO DO FORMULARIO E SALVAR NO BANCO DE DADOS

		botao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Pessoa pessoa = helper.pegaAlunoDoFormulario();
				SaleDAO dao = new SaleDAO(Formulario.this);
				
				
				// Validar se o nome e o preco vieram preenchidos
				if (pessoa.getNome().isEmpty()) {
					Toast.makeText(Formulario.this,
							"Informe o nome da pessoa!", Toast.LENGTH_SHORT)
							.show();
					} else if (pessoa.getCpf_cnpj().isEmpty()) {
						Toast.makeText(Formulario.this,
								"Informe o CPF/CNPJ!", Toast.LENGTH_SHORT)
								.show();
					}else {
						
						if (alunoParaSerAlterado == null) {
							if (dao.verificaPessoaExiste(pessoa.getCpf_cnpj()) == false) {
								dao.salva(pessoa);
								Toast.makeText(
										Formulario.this,
										"Pessoa " + pessoa.getNome()
												+ " criado com sucesso!",
										Toast.LENGTH_SHORT).show();
	
								dao.close();
								finish(); 
								
							} else {
							Toast.makeText(Formulario.this,
									"Pessoa já cadastrada no aplicativo!", Toast.LENGTH_SHORT)
									.show();
						}
						
					} else {
						pessoa.setId(alunoParaSerAlterado.getId());
						dao.altera(pessoa);
						Toast.makeText(
								Formulario.this,
								"Pessoa " + pessoa.getNome()
										+ " criado com sucesso!",
								Toast.LENGTH_SHORT).show();

						dao.close();
						finish(); 
					}
					

					// VOLTAR PARA A TELA DE LISTAGEM
					// VOLTA PARA A LISTAGEM
				}

			}
		});

		ImageView foto = helper.getFoto();

		foto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// CHAMAR A C�MERA DO ANDROID
				Intent irParaCamera = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);

				// /storage/sdcard0/bysale_img
				File diretorio_salvar_imagem = new File(Environment
						.getExternalStorageDirectory() + "/bysale_img");
				boolean success = true;
				if (!diretorio_salvar_imagem.exists()) {
					success = diretorio_salvar_imagem.mkdir();
				}

				caminhoArquivo = diretorio_salvar_imagem.toString() + "/"
						+ +System.currentTimeMillis() + ".png";
				// AVISAR ONDE EST� A FOTO E O NOME
				/*
				 * caminhoArquivo =
				 * Environment.getExternalStorageDirectory().toString()
				 * +"/"+System.currentTimeMillis()+".png"; //Utilizamos o
				 * currentTimeMillis para salvar o nome como um nome �nico
				 */
				File arquivo = new File(caminhoArquivo);

				Uri localImagem = Uri.fromFile(arquivo);

				irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localImagem);
				startActivityForResult(irParaCamera, 123); /*
															 * INICAR A ACTIVITY
															 * ESPERANDO UM
															 * RESULTADO. O
															 * N�MERO 123 FOI UM
															 * N�MERO ALEAT�RIO.
															 * ISSO FOI FEITO
															 * PARA QUE N�O
															 * OCORRA DO USU�RIO
															 * CLICAR EM TIRAR A
															 * FOTO E DEPOIS
															 * CANCELAR A
															 * OPERA��O E
															 * FICARMOS SEM A
															 * FOTO AL�M DISSO,
															 * QUANTO UTILIZADA
															 * A
															 * activityForResul
															 * devemos sobrepor
															 * o m�todo
															 * onActivityResult
															 */

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		try {

			if (requestCode == 123) { // ESSE requestCode == 123 � o par�metro
										// que passamos para a startActivity
										// para saber se a foto foi tirada
				if (resultCode == Activity.RESULT_OK) { // result_ok quer dizer
														// que ele clicou no
														// bot�o ok para a foto

					helper.carregaImagem(caminhoArquivo);
				} else { // SEN�O CLICOOU EM CANCELAR

					caminhoArquivo = null;

				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		super.onActivityResult(requestCode, resultCode, data);
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

	@Override
	public View findViewById(int id) {
		// TODO Auto-generated method stub
		return super.findViewById(id);
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // TODO
	 * Auto-generated method stub
	 * 
	 * //A classe INFLATE l� o arquivo xml de menu e joga na tela
	 * 
	 * MenuInflater inflater = getMenuInflater();
	 * 
	 * inflater.inflate(R.menu.menu_single, menu);
	 * 
	 * return super.onCreateOptionsMenu(menu);
	 * 
	 * }
	 */

}
