package com.sale.adapter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import pedidos.PedidosFragmentEmitidos;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sale.cadastro.R;
import com.sale.dao.SaleDAO;
import com.sale.modelo.VendasDTO;

public class ListaPessoasPedidosEmitidosAdapter extends BaseAdapter {

	private List<VendasDTO> pessoas;
	private PedidosFragmentEmitidos activity;
	Double precoTotalVenda;
	Context context;

	public ListaPessoasPedidosEmitidosAdapter(List<VendasDTO> vendasAbertas,
			PedidosFragmentEmitidos fragmentTab2, Context context) {
		this.pessoas = vendasAbertas;
		// TODO Auto-generated constructor stub
		this.activity = fragmentTab2;
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// O getCount pede a quantidade de linhas que v�o aparecer na listagem.
		// A quantidade ser� o total de pessoas.
		// TODO Auto-generated method stub
		return pessoas.size();
	}

	@Override
	public Object getItem(int position) { // Precisa saber qual objeto
											// representa aquele
		// TODO Auto-generated method stub

		return pessoas.get(position);

	}

	@Override
	public long getItemId(int position) { // NO ANDROID, CADA VIEW PRECISA TER
											// UM ID. NO CASO, UTILIZAREMOS O
											// PR�PRIO ID DO ALUNO
		// TODO Auto-generated method stub
		return pessoas.get(position).getIdvendacliente(); // RETORNA O ALUNO DA
															// LINHA, QUE
		// PEGAREMOS O DI.
	}

	@Override
	public View getView(int position, View convertView, ViewGroup partent) {

		/*
		 * M�todo mais importante da classe Explicar para o Android que, dada
		 * uma posi��o, devemos mostrar o pessoa na tela.
		 */
		VendasDTO pessoa = pessoas.get(position);

		// colocar os dados do pessoa no layout. Mas precisamos, primeiro,
		// trasformar as telas em objetos do android. Quem faz isso � o
		// inflater.

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // � atrav�s
																	// do
		// inflater que
		// transformamos
		// uma s�rie de
		// tags e
		// transformamos
		// em objetos do
		// android.
		// A partir do inflater, conseguimos atrav�s do arquivo de layout
		// instanciar um objeto
		View linha = inflater.inflate(R.layout.linha_listagem, null);

		if (position % 2 == 0) {

			linha.setBackgroundColor(activity.getResources().getColor(
					R.color.linha_par));

		} else {
			linha.setBackgroundColor(activity.getResources().getColor(
					R.color.linha_impar));

		}

		TextView nome = (TextView) linha.findViewById(R.id.nome);
		nome.setText(pessoa.getNome());

		ImageView foto = (ImageView) linha.findViewById(R.id.foto);

		TextView totalVenda = (TextView) linha.findViewById(R.id.totalvenda);

		TextView data_venda = (TextView) linha.findViewById(R.id.data_venda);

		TextView situacao_venda = (TextView) linha
				.findViewById(R.id.situacao_venda);
		SaleDAO dao = new SaleDAO(context);

		// VendasDTO dadosVenda = dao.getDadosVenda(pessoa);

		if (pessoa.getSituacaoVenda() == 2) {

			situacao_venda.setText("Pedido #" + pessoa.getIdvendacliente()
					+ "(Em Orçamento)");
		} else {
			situacao_venda.setText("Pedido #" + pessoa.getIdvendacliente());
		}

		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

		String data = formatador.format(pessoa.getDtvenda());
		data_venda.setText(data);

		Double precoTotalVenda = pessoa.getTotal();
		dao.close();
		DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		String totalPrecoVenda = "0,00";

		if (precoTotalVenda != null) {
			totalPrecoVenda = precoFormatado.format(pessoa.getTotal());
		} else {
			totalPrecoVenda = "0,00";

		}

		dao.close();

		totalVenda.setText(totalPrecoVenda);

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
