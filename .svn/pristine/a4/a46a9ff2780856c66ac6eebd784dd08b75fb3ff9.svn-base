package com.sale.adapter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import com.sale.cadastro.R;
import com.sale.dao.SaleDAO;
import com.sale.login.Usuario;
import com.sale.modelo.Pessoa;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaPessoasCadastradasAdapter extends ArrayAdapter<Pessoa> {
    static final int LAYOUT = R.layout.lista_pessoas_cadastradas;

	private List<Pessoa> pessoas;
	private Activity activity;
	Double precoTotalVenda;
	Context context;

	public ListaPessoasCadastradasAdapter(List<Pessoa> pessoas,
			Activity activity, Context context) {
        super(context, LAYOUT, pessoas);

		this.pessoas = pessoas;
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.context = context;
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup partent) {

		/*
		 * M�todo mais importante da classe Explicar para o Android que, dada
		 * uma posi��o, devemos mostrar o pessoa na tela.
		 */
		Pessoa pessoa = pessoas.get(position);

		// colocar os dados do pessoa no layout. Mas precisamos, primeiro,
		// trasformar as telas em objetos do android. Quem faz isso � o
		// inflater.

		LayoutInflater inflater = activity.getLayoutInflater(); // � atrav�s do
																// inflater que
																// transformamos
																// uma s�rie de
																// tags e
																// transformamos
																// em objetos do
																// android.
		// A partir do inflater, conseguimos atrav�s do arquivo de layout
		// instanciar um objeto
		View linha = inflater.inflate(R.layout.lista_pessoas_cadastradas, null);

		
		//Só entrar se tiver o fluxo de validação
		//if (pessoa.getSituacao_cadastro().equals(1)) {
			
	//	} 
		
		SaleDAO dao = new SaleDAO(context);
		Usuario dadosUsuarioDispositivo = dao.getDadosUsuarioDispositivo();
		String empresaPossuiFluxoAprovacaoCadastroUsuario = dadosUsuarioDispositivo.getFluxo_aprovacao_cadastro();
		
		String mensagem_mostrar = "";
		//Somente altera as mensagens do usuário se a empresa possuir fluxo de aprovação
		if (empresaPossuiFluxoAprovacaoCadastroUsuario.equals("S")) {
				
				if (pessoa.getSituacao_cadastro() != null) {
					if (pessoa.getSituacao_cadastro().equals("1")) {
						mensagem_mostrar = " - Aguardando Aprovação";
					} else if (pessoa.getSituacao_cadastro().equals("2")) {
						mensagem_mostrar = " - Cadastro Aprovado";
			
					}else if (pessoa.getSituacao_cadastro().equals("3")) {
						mensagem_mostrar = " - Cadastro Não Aprovado";
			
					}
				} else {
					mensagem_mostrar = " - Aguardando Aprovação";
				}
				
		}
		
		
		
		if (empresaPossuiFluxoAprovacaoCadastroUsuario.equals("S") && pessoa.getSituacao_cadastro().equals("3") ) {
			linha.setBackgroundColor(activity.getResources().getColor(
					R.color.vermelho_cliente_nao_aprovado));
				} else if (empresaPossuiFluxoAprovacaoCadastroUsuario.equals("S") && pessoa.getSituacao_cadastro().equals("1") ) {
					linha.setBackgroundColor(activity.getResources().getColor(
							R.color.azul_aguardando_aprovacao));
						} else {
			
				if (position % 2 == 0) {
					
					linha.setBackgroundColor(activity.getResources().getColor(
							R.color.linha_par));
		
				} else {
					linha.setBackgroundColor(activity.getResources().getColor(
							R.color.linha_impar));
		
				}
		}
		TextView nome = (TextView) linha.findViewById(R.id.nome);
		
		
		nome.setText(pessoa.getNome()+mensagem_mostrar);

		ImageView foto = (ImageView) linha.findViewById(R.id.foto);

		TextView totalVenda = (TextView) linha.findViewById(R.id.totalvenda);

		dao.close();

		if (pessoa.getFoto() != null) {

			Bitmap fotoAluno = BitmapFactory.decodeFile(pessoa.getFoto());
			Bitmap fotoReduzida = Bitmap.createScaledBitmap(fotoAluno, 100, 90,
					true); // REDUZINDO A FOTO

			Matrix matrix = new Matrix();
			// matrix.postRotate(270);

			ExifInterface ei;
			try {
				ei = new ExifInterface(pessoa.getFoto());
				int orientation = ei.getAttributeInt(
						ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_NORMAL);

				if (orientation == 8) { // 90 graus
					matrix.postRotate(270);

				} else if (orientation == 6) { // 180 graus
					matrix.postRotate(90);
				}

				// etc.

				Bitmap rotated = Bitmap.createBitmap(fotoReduzida, 0, 0,
						fotoReduzida.getWidth(), fotoReduzida.getHeight(),
						matrix, true);

				Bitmap imagem = colocarBrilhoImagem(rotated, 60);

				foto.setImageBitmap(imagem);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Drawable semFoto = activity.getResources().getDrawable(
					R.drawable.ic_no_image);
			foto.setImageDrawable(semFoto);

		}
		/*
		 * DESATIVEI O LAYOUT LANDSCAPE DA LISTA DE CLIENTES. PARA VOLTAR � S�
		 * COLOCAR O LAYOUT NOVAMENTE E REATIVAR ESSA LISTA TextView telefone =
		 * (TextView) linha.findViewById(R.id.telefone); if (telefone != null) {
		 * //VERIFICA SE O ELEMENTO TELEFONE � DIFERENTE DE NULO, POIS S� SER�
		 * MOSTRADO NA POSI��O LANDSCAPE telefone.setText(pessoa.getTelefone());
		 * }
		 * 
		 * TextView site = (TextView) linha.findViewById(R.id.site); if (site !=
		 * null) { site.setText(pessoa.getSite()); }
		 */
		return linha;
	}

	public Bitmap colocarBrilhoImagem(Bitmap src, int value) {
		// src - imagem de origem, value - quantidade de brilho

		// original image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// color information
		int A, R, G, B;
		int pixel;

		// scan through all pixels
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);

				// increase/decrease each channel
				R += value;
				if (R > 255) {
					R = 255;
				} else if (R < 0) {
					R = 0;
				}

				G += value;
				if (G > 255) {
					G = 255;
				} else if (G < 0) {
					G = 0;
				}

				B += value;
				if (B > 255) {
					B = 255;
				} else if (B < 0) {
					B = 0;
				}

				// apply new pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

}
