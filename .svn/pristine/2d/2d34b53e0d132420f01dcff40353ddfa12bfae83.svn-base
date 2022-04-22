package pedidos;

import java.util.List;

import com.sale.cadastro.R;
import com.sale.cadastro.ListaPessoasAbrirVenda;
import com.sale.cadastro.task.EnviaVendaTask;
import com.sale.cadastro.util.ConnectionDetector;
import com.sale.dao.SaleDAO;
import com.sale.modelo.ControleSincronizacao;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

public class PedidosFragment extends Activity {
	ActionBar.Tab tab1, tab2;
	Fragment fragmentTab1 = new PedidosFragmentEmOrcamento();
	Fragment fragmentTab2 = new PedidosFragmentEmitidos();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_test);

		ActionBar actionBar = getActionBar();

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Pedidos");

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#FFFFFF")));
		tab1 = actionBar.newTab().setText(getText(R.string.pedidos_fragment_em_orcamento));
		tab2 = actionBar.newTab().setText(getText(R.string.pedidos_fragment_emitidos));

		tab1.setTabListener(new MyTabListener(fragmentTab1));
		tab2.setTabListener(new MyTabListener(fragmentTab2));

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Em 15-02 troquei o onCreateOptionsMenu pelo onPrepare, pois o
		// onCreate s� carrega uma vez.
		// A classe INFLATE o arquivo xml de menu e joga na tela

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.menu_incluir_venda, menu);

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		int itemClicado = item.getItemId();

		if (itemClicado == android.R.id.home) {

			finish();

		}

		switch (itemClicado) {
		case R.id.menu_incluir_venda:

			Intent irParaFormulario = new Intent(PedidosFragment.this,
					ListaPessoasAbrirVenda.class);

			startActivity(irParaFormulario);

			break;

		case R.id.enviar_clientes:
			// Menu para enviar vendas ao servidor
			ConnectionDetector detectaConexao = new ConnectionDetector(
					PedidosFragment.this);

			Boolean conexao = detectaConexao.isConnectingToInternet();

			// S� deixa sincronizar as vendas se houver conex�o

			if (conexao == false) {
				String mensagem = null;

				mensagem = "Seu aparelho est� sem conex�o com internet. Ative a conex�o para sincronizar.";

				AlertDialog.Builder alertDialogTrocar = new AlertDialog.Builder(
						PedidosFragment.this);
				alertDialogTrocar.setMessage(mensagem);
				alertDialogTrocar.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {
								// TODO Auto-generated method stub

							}
						});

				AlertDialog alertDialogNovo = alertDialogTrocar.create();
				alertDialogNovo.show();

				// Mudar a cor do bot�o
				((Button) alertDialogNovo.findViewById(android.R.id.button2))
						.setBackgroundResource(R.color.botao_picker);
				((Button) alertDialogNovo.findViewById(android.R.id.button1))
						.setBackgroundResource(R.color.botao_picker);
				((Button) alertDialogNovo.findViewById(android.R.id.button1))
						.setTextColor(getResources().getColor(R.color.branco));
				((Button) alertDialogNovo.findViewById(android.R.id.button2))
						.setTextColor(getResources().getColor(R.color.branco));
			} else {
				SaleDAO dao = new SaleDAO(PedidosFragment.this);
				List<ControleSincronizacao> idVendaclienteSincronizar = dao
						.vendaClienteParaSincronizar();
				EnviaVendaTask task = new EnviaVendaTask(PedidosFragment.this,
						idVendaclienteSincronizar, "enviar_todas_as_vendas");

				// EnviaClientesTask task = new EnviaClientesTask(this);
				task.execute();
			}

			// Fim verifica��o internet

			break;

		}

		return super.onOptionsItemSelected(item);
	}

}