package pedidos;

import java.util.List;

import com.sale.adapter.ListaPessoasAdapter;
import com.sale.adapter.ListaPessoasPedidosEmitidosAdapter;
import com.sale.cadastro.R;
import com.sale.cadastro.ListaVendas;
import com.sale.cadastro.ListaVendasAbertas;
import com.sale.dao.SaleDAO;
import com.sale.modelo.VendasDTO;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PedidosFragmentEmitidos extends Fragment {

	private String tipo_vendas_enviar = null; // Registra se as vendas a serem
	// enviadas ser‹o totais ou
	// parciais

	private ListView lista;
	public VendasDTO vendaSelecionada;

	Long tipoContaEmpresaUsuario = null;
	Long tipoEmpresa = null;
	String usuario_master = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// setContentView(R.layout.listagem_alunos);

		// setContentView(R.layout.listagem_alunos);
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setTitle("Pedidos");

		SaleDAO dao = new SaleDAO(PedidosFragmentEmitidos.this.getActivity());
		List<VendasDTO> vendasAbertas = dao.getClientesVendasAbertas2(1);
		// List<Pessoa> pessoas = dao.getClientesVendasAbertas();
		dao.close(); // TODA VEZ QUE INSTANCIARMOS O DAO ELE DEVE SER FECHADO

		View tela = inflater
				.inflate(R.layout.listagem_alunos, container, false);

		lista = (ListView) tela.findViewById(R.id.lista);

		ListaPessoasPedidosEmitidosAdapter adapter = new ListaPessoasPedidosEmitidosAdapter(
				vendasAbertas, this, PedidosFragmentEmitidos.this.getActivity());
		// VINCULAR ADAPTER A LISTAGEM

		lista.setAdapter(adapter);

		// AVISAR AO AONDROID QUE PRECISA PASSAR UM MENU COM OPÇÕES
		// registerForContextMenu(lista);
		return tela;

		/*
		 * View view = inflater.inflate(R.layout.tab1, container, false);
		 * TextView textview = (TextView) view.findViewById(R.id.tabtextview);
		 * textview.setText("Um");
		 */
		// return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		carregaLista();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
		carregaLista();

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				// TODO Auto-generated method stub

				vendaSelecionada = (VendasDTO) adapter
						.getItemAtPosition(posicao);
				Intent irListaVendas = new Intent(PedidosFragmentEmitidos.this
						.getActivity(), ListaVendas.class); // IR PARA A TELA DE
															// EDIÇÃO

				// PASSAR O PARÂMETRO DO ID DO CLIENTE PARA CONSULTAR

				irListaVendas.putExtra(
						"vendaSelecionadaFromListaVendasAbertas",
						vendaSelecionada.getIdvendacliente());// AlunoSelecionado
				// é um
				// apelido
				irListaVendas.putExtra("clienteSelecionadoFromListaPessoasAbrirVenda", vendaSelecionada.getIdCliente());

				startActivity(irListaVendas);

			}
		});
	}

	private void carregaLista() {
		// TODO Auto-generated method stub
		// Mostrar o pessoa na tela
		SaleDAO dao = new SaleDAO(PedidosFragmentEmitidos.this.getActivity());
		List<VendasDTO> vendasAbertas = dao.getClientesVendasAbertas2(1);
		// List<Pessoa> pessoas = dao.getClientesVendasAbertas();
		dao.close(); // TODA VEZ QUE INSTANCIARMOS O DAO ELE DEVE SER FECHADO

		ListaPessoasPedidosEmitidosAdapter adapter = new ListaPessoasPedidosEmitidosAdapter(
				vendasAbertas, this, PedidosFragmentEmitidos.this.getActivity());
		// VINCULAR ADAPTER A LISTAGEM
		lista.setAdapter(adapter);
	}

}
