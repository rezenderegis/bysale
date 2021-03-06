package com.sale.dao;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sale.cadastro.util.Tools;
import com.sale.login.Usuario;
import com.sale.modelo.CategoriaProduto;
import com.sale.modelo.ControleSincronizacao;
import com.sale.modelo.Pagamento;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Produto;
import com.sale.modelo.ProdutoDTO;
import com.sale.modelo.VendaCliente;
import com.sale.modelo.Vendas;
import com.sale.modelo.VendasDTO;

/**
 * @author fabricio
 * 
 */
public class SaleDAO extends SQLiteOpenHelper {

	// private SQLiteDatabase database;

	private static final String DATABASE = "mysale.db";
	// Quando subir para a �rea beta tenho que mudar a vers�o para 18 e na
	// condi��o da tabela
	// pagamento, colocar < 18
	private static final int VERSAO = 122;
	// Vers�o de produ��o est� na 115 - 31-03-2015
	// UTILIZAR NA LISTAGEM NOVA
	public static final String TABLE_VENDAS = "Vendas";

	// ESSE CONSTRUTOR O PR�PRIO ECLIPSE RECLAMA DA CRIA��O
	public SaleDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
		// TODO Auto-generated constructor stub
	}

	public void salva(Pessoa pessoa) {
		// TODO Auto-generated method stub
		Tools tools = new Tools();

		Usuario usuarioDispositivo = getDadosUsuarioDispositivo();

		ContentValues values = new ContentValues();
		UUID uuid = UUID.randomUUID();

		values.put("id", uuid.toString());
		values.put("nome", pessoa.getNome());
		values.put("site", pessoa.getSite());
		values.put("endereco", pessoa.getEndereco());

		// values.put("nota", pessoa.getNota());
		values.put("foto", pessoa.getFoto());
		values.put("telefone", pessoa.getTelefone());
		values.put("email", pessoa.getEmail());
		values.put("cpf_cnpj", pessoa.getCpf_cnpj());
		values.put("observacao", pessoa.getObservacao());
		values.put("situacao_cadastro", "1");

		getWritableDatabase().insert("Alunos", null, values);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String ddl = "CREATE TABLE Alunos(id TEXT PRIMARY KEY, "
				+ "nome TEXT, telefone TEXT"
				+ ", endereco TEXT, site TEXT, foto TEXT, nota REAL, email TEXT, cpf_cnpj TEXT, observacao TEXT, situacao_cadastro TEXT);";

		String ddlVendas = "CREATE TABLE VENDAS (ID INTEGER PRIMARY KEY AUTOINCREMENT,CONTROLE INTEGER, IDCLIENTE INTEGER, IDPRODUTO TEXT, IDVENDACLIENTE INTEGER, TOTALPRODUTO INTEGER, DTVENDA DATE, SITUACAO INTEGER, PRECO_PRODUTO REAL);";

		String ddlProduto = "CREATE TABLE PRODUTO (ID TEXT PRIMARY KEY, NOMEPRODUTO TEXT, PRECO REAL, CATEGORIA TEXT);";

		String ddlVendaCliente = "CREATE TABLE VENDACLIENTE (IDVENDACLIENTE INTEGER PRIMARY KEY AUTOINCREMENT, IDCLIENTE TEXT, DTVENDA DATE, SITUACAOVENDA INTEGER, TOTALVENDA REAL, DESCONTOVENDA REAL, DT_VENDA_PARA_RELATORIO DATE);";

		String ddlUsuario = "CREATE TABLE USUARIO (IDUSUARIO INTEGER PRIMARY KEY, EMAIL TEXT, PASSWORD TEXT, IDEMPRESA INTEGER, TIPOCONTAEMPRESA  INTEGER, TIPOEMPRESA INTEGER, SITUACAOLOGIN TEXT, USUARIO_MASTER TEXT, SINCRONIZACAOREGISTROPRODUTO TEXT ,SINCRONIZACAOFECHAMENTOVENDA TEXT,SINCRONIZACAOENTRADAAPLICATIVO TEXT, DATA_ULTIMA_SINCRONIZACAO TEXT, FLUXO_APROVACAO_CADASTRO TEXT);";

		String ddlControleSincronizacao = "CREATE TABLE CONTROLESINCRONIZACAO (IDVENDACLIENTE INTEGER PRIMARY KEY);";

		String ddlCategoriaProduto = "CREATE TABLE CATEGORIAPRODUTO (IDCATEGORIA TEXT PRIMARY KEY, NOMECATEGORIA TEXT);";

		String ddlPagamento = "CREATE TABLE PAGAMENTO (IDPAGAMENTO INTEGER PRIMARY KEY AUTOINCREMENT, IDVENDACLIENTE INTEGER, VALOR_PAGAMENTO REAL, DATA_PAGAMENTO_INFORMADA DATE, FORMA_PAGAMENTO_INFORMADA INTEGER, SITUACAO_PAGAMENTO INTEGER, SITUACAO_PAGAMENTO_INFORMADO TEXT);";

		db.execSQL(ddl);
		db.execSQL(ddlVendas);
		db.execSQL(ddlProduto);
		db.execSQL(ddlVendaCliente);
		db.execSQL(ddlUsuario);
		db.execSQL(ddlControleSincronizacao);
		db.execSQL(ddlCategoriaProduto);
		db.execSQL(ddlPagamento);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		if (oldVersion < 116) {
			String ddlPagamento = "CREATE TABLE PAGAMENTO (IDPAGAMENTO INTEGER PRIMARY KEY AUTOINCREMENT, IDVENDACLIENTE INTEGER, VALOR_PAGAMENTO REAL, DATA_PAGAMENTO_INFORMADA DATE, FORMA_PAGAMENTO_INFORMADA INTEGER, SITUACAO_PAGAMENTO INTEGER);";
			db.execSQL("ALTER TABLE VENDACLIENTE ADD COLUMN DESCONTOVENDA REAL");
			db.execSQL(ddlPagamento);

		}

		if (oldVersion < 117) {
			db.execSQL("ALTER TABLE VENDACLIENTE ADD COLUMN DT_VENDA_PARA_RELATORIO DATE");

		}

		if (oldVersion < 118) {
			db.execSQL("ALTER TABLE PAGAMENTO ADD COLUMN SITUACAO_PAGAMENTO_INFORMADO TEXT");

		}

		if (oldVersion < 119) {
			db.execSQL("ALTER TABLE VENDAS ADD COLUMN PRECO_PRODUTO REAL");

		}

		if (oldVersion < 120) {
			db.execSQL("ALTER TABLE ALUNOS ADD COLUMN cpf_cnpj TEXT");
		}

		if (oldVersion < 121) {
			db.execSQL("ALTER TABLE ALUNOS ADD COLUMN observacao TEXT");

		}

	}

	public List<Pessoa> getLista() {
		// TODO Auto-generated method stub
		String[] colunas = { "id", "nome", "site", "telefone", "endereco",
				"foto", "email", "cpf_cnpj", "observacao", "situacao_cadastro" };

		// O GET WRITABLE DATABASE RETORNA UM CURSOR
		Cursor cursor = getWritableDatabase().query("ALUNOS", colunas, null,
				null, null, null, "nome");

		ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();

		while (cursor.moveToNext()) {

			Pessoa pessoa = new Pessoa();

			pessoa.setId(cursor.getString(0));
			pessoa.setNome(cursor.getString(1));
			pessoa.setSite(cursor.getString(2));
			pessoa.setTelefone(cursor.getString(3));
			pessoa.setEndereco(cursor.getString(4));
			pessoa.setFoto(cursor.getString(5));
			pessoa.setEmail(cursor.getString(6));
			pessoa.setCpf_cnpj(cursor.getString(7));
			pessoa.setObservacao(cursor.getString(8));
			pessoa.setSituacao_cadastro(cursor.getString(9));

			// pessoa.setNota(cursor.getDouble(6));
			pessoas.add(pessoa);
		}

		return pessoas;
	}

	public List<Pessoa> getClientesVendasAbertas() {

		String selectQuery = "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, ALUNOS.foto, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE  FROM  VENDACLIENTE, ALUNOS  WHERE ALUNOS.ID = VENDACLIENTE.IDCLIENTE AND VENDACLIENTE.SITUACAOVENDA = 2 ";
		// AND VENDACLIENTE.SITUACAOVENDA = 2 - retirei para mostrar todas

		SQLiteDatabase db = this.getReadableDatabase();

		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// NumberFormat fortamatarNumero = NumberFormat.getCurrencyInstance();
		// Double novo = 0.0;
		Cursor cursor = db.rawQuery(selectQuery, null);

		ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();

		while (cursor.moveToNext()) {

			Pessoa pessoa = new Pessoa();

			pessoa.setId(cursor.getString(0));
			pessoa.setNome(cursor.getString(1));
			pessoa.setSite(cursor.getString(2));
			pessoa.setTelefone(cursor.getString(3));
			pessoa.setEndereco(cursor.getString(4));
			pessoa.setFoto(cursor.getString(5));
			// pessoa.setNota(cursor.getDouble(6));
			pessoas.add(pessoa);
		}

		return pessoas;
	}

	public List<VendasDTO> getClientesVendasAbertas2(int situacao,
			String data_inicial, String data_final, int tipo_retorno,
			String pedidos_em_orcamento, String pedidos_emitidos) {

		Tools tools = new Tools();

		SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-yyyy");

		/*
		 * tipo_retorno = 1 => retorna os dados gerais tipo_retorno = 2 =>
		 * retorna todo o total das vendas pelo par�metro especificado
		 */
		String filtro_tipo_retorno = "";
		if (tipo_retorno == 1) {
			filtro_tipo_retorno = " GROUP BY VENDACLIENTE.IDVENDACLIENTE ";
		}
		String selectQuery;
		if (situacao == 2) {
			selectQuery = "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, ALUNOS.foto, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE, SUM(VENDAS.PRECO_PRODUTO * VENDAS.TOTALPRODUTO) AS TOTAL_VENDA, VENDACLIENTE.SITUACAOVENDA FROM  PRODUTO, VENDAS, VENDACLIENTE, ALUNOS  WHERE ALUNOS.ID = VENDACLIENTE.IDCLIENTE AND PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDAS.SITUACAO = 1 AND VENDACLIENTE.SITUACAOVENDA = 2 AND VENDACLIENTE.IDCLIENTE GROUP BY VENDACLIENTE.IDVENDACLIENTE ORDER BY VENDACLIENTE.DTVENDA DESC ";

		} else if (situacao == 1) {
			selectQuery = "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, ALUNOS.foto, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE, SUM(VENDAS.PRECO_PRODUTO * VENDAS.TOTALPRODUTO) AS TOTAL_VENDA, VENDACLIENTE.SITUACAOVENDA FROM  PRODUTO, VENDAS, VENDACLIENTE, ALUNOS  WHERE ALUNOS.ID = VENDACLIENTE.IDCLIENTE AND PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDAS.SITUACAO = 1 AND VENDACLIENTE.SITUACAOVENDA = 1 AND VENDACLIENTE.IDCLIENTE GROUP BY VENDACLIENTE.IDVENDACLIENTE ORDER BY VENDACLIENTE.DTVENDA DESC ";

		} else {
			String filtro = "";

			if (!data_inicial.equals("") && !data_final.equals("")) {
				// data_inicial = data_inicial.concat(" 00:01:00");
				// data_final = data_final.concat(" 23:59:00");
				data_inicial = tools.retorna_data_com_barra(data_inicial);
				data_inicial = tools.retorna_data_com_traco(data_inicial);

				data_final = tools.retorna_data_com_barra(data_final);
				data_final = tools.retorna_data_com_traco(data_final);

				try {
					data_inicial = tools
							.retorna_data_com_traco_yyyyMMdd(data_inicial);
					data_final = tools
							.retorna_data_com_traco_yyyyMMdd(data_final);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * filtro =
				 * "  (strftime('%d-%m-%Y',VENDACLIENTE.DT_VENDA_PARA_RELATORIO) >= '"
				 * + data_inicial +
				 * "'  strftime('%d-%m-%Y',VENDACLIENTE.DT_VENDA_PARA_RELATORIO) <= '"
				 * + data_final + "') ";
				 */

				filtro = "  (VENDACLIENTE.DT_VENDA_PARA_RELATORIO >= '"
						+ data_inicial
						+ "'  AND VENDACLIENTE.DT_VENDA_PARA_RELATORIO <= '"
						+ data_final + "') AND ";

			} else {

				if (!data_inicial.equals("")) {
					data_inicial = tools.retorna_data_com_barra(data_inicial);
					data_inicial = tools.retorna_data_com_traco(data_inicial);
					try {
						data_inicial = tools
								.retorna_data_com_traco_yyyyMMdd(data_inicial);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// data_inicial = data_inicial.concat(" 00:00:00");
					// DTVENDA
					// filtro =
					// " AND strftime('%d-%m-%Y',VENDACLIENTE.DT_VENDA_PARA_RELATORIO) >= '"
					// + data_inicial
					// + "'";

					filtro = " VENDACLIENTE.DT_VENDA_PARA_RELATORIO >=  '"
							+ data_inicial + "' AND";
				}
				if (!data_final.equals("")) {

					data_final = tools.retorna_data_com_barra(data_final);
					data_final = tools.retorna_data_com_traco(data_final);
					try {
						data_final = tools
								.retorna_data_com_traco_yyyyMMdd(data_final);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// data_final = data_final.concat(" 23:59:00");
					filtro = " VENDACLIENTE.DT_VENDA_PARA_RELATORIO <= '"
							+ data_final + "' AND";
				}
			}

			String filtro_situacao_padrao = "";

			if (pedidos_em_orcamento.equals("S")
					|| pedidos_emitidos.equals("S")) {
				// Se qualqeuer um dos filtros estiverem presentes, entra.

				if (pedidos_em_orcamento.equals("S")
						&& pedidos_emitidos.equals("S")) {
					// Se os dois estiverem preenchidos, traz os dois
					filtro_situacao_padrao = " AND (VENDACLIENTE.SITUACAOVENDA = 1 or VENDACLIENTE.SITUACAOVENDA = 2)";
				} else if (pedidos_em_orcamento.equals("S")) {
					// Se somente o filtro de pedidos em or�amento estiver
					// preenchido, mostra somente eles
					filtro_situacao_padrao = " AND VENDACLIENTE.SITUACAOVENDA = 2";
				} else if (pedidos_emitidos.equals("S")) {
					// Se somente o filtro de pedidos emitidos estiver
					// preenchido, mostra somente eles

					filtro_situacao_padrao = " AND VENDACLIENTE.SITUACAOVENDA = 1";
				}

			} else {
				filtro_situacao_padrao = " AND VENDACLIENTE.SITUACAOVENDA = 1";
			}

			selectQuery = "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, "
					+ "ALUNOS.foto, VENDACLIENTE.DTVENDA,  VENDACLIENTE.IDVENDACLIENTE, SUM(VENDAS.PRECO_PRODUTO * VENDAS.TOTALPRODUTO) AS TOTAL_VENDA,"
					+ " VENDACLIENTE.SITUACAOVENDA, strftime('%Y-%m-%d', VENDACLIENTE.DTVENDA) as DT_VENDA, VENDACLIENTE.DT_VENDA_PARA_RELATORIO FROM  PRODUTO, VENDAS, VENDACLIENTE, ALUNOS  WHERE "
					+ filtro
					+ " ALUNOS.ID = VENDACLIENTE.IDCLIENTE AND PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDAS.SITUACAO = 1"
					+ filtro_situacao_padrao
					+ filtro_tipo_retorno
					+ " ORDER BY VENDACLIENTE.DT_VENDA_PARA_RELATORIO DESC ";

		}

		// String selectQuery =
		// "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, ALUNOS.foto, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE  FROM  VENDACLIENTE, ALUNOS  WHERE ALUNOS.ID = VENDACLIENTE.IDCLIENTE";
		// AND VENDACLIENTE.SITUACAOVENDA = 2 - retirei para mostrar todas

		SQLiteDatabase db = this.getReadableDatabase();

		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// NumberFormat fortamatarNumero = NumberFormat.getCurrencyInstance();
		// Double novo = 0.0;
		Cursor cursor = db.rawQuery(selectQuery, null);

		ArrayList<VendasDTO> pessoas = new ArrayList<VendasDTO>();

		while (cursor.moveToNext()) {

			String dataVenda = (cursor.getString(cursor
					.getColumnIndex("DTVENDA")));
			String dt_venda = (cursor.getString(cursor
					.getColumnIndex("DT_VENDA")));

			String dt_venda_para_relatorio = (cursor.getString(cursor
					.getColumnIndex("DT_VENDA_PARA_RELATORIO")));
			// String dt_teste =
			// (cursor.getString(cursor.getColumnIndex("dt_teste")));

			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

			Date dtVenda = null;

			if (dataVenda != null) {

				try {
					dtVenda = formatador.parse(dataVenda);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			VendasDTO pessoa = new VendasDTO();

			pessoa.setIdCliente(cursor.getString(0));
			pessoa.setNome(cursor.getString(1));
			pessoa.setSite(cursor.getString(2));
			pessoa.setTelefone(cursor.getString(3));
			pessoa.setEndereco(cursor.getString(4));
			pessoa.setFoto(cursor.getString(5));
			pessoa.setDtvenda(dtVenda);
			pessoa.setSituacaoVenda(cursor.getLong(9));
			pessoa.setIdvendacliente(cursor.getLong(7));
			pessoa.setTotal(cursor.getDouble(8));
			// pessoa.setNota(cursor.getDouble(6));
			pessoas.add(pessoa);
		}

		return pessoas;
	}

	public List<VendasDTO> getClientesVendasAbertas2(int situacao) {
		String selectQuery;
		if (situacao == 2) {

			/*
			 * Anotação feita dia 24-01-2015 Percebi que algumas vendas não
			 * estavam sendo mostradas na tela de em orçamento. Coloquei a
			 * consulta no sqlite do firefox e realmente não vinham. Percebi que
			 * tinha esse trecho AND VENDACLIENTE.IDCLIENTE antes do group by.
			 * Não entendi por que estava lá.
			 */

			selectQuery = "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, ALUNOS.foto, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE, SUM(VENDAS.PRECO_PRODUTO * VENDAS.TOTALPRODUTO) AS TOTAL_VENDA, VENDACLIENTE.SITUACAOVENDA FROM  PRODUTO, VENDAS, VENDACLIENTE, ALUNOS  WHERE ALUNOS.ID = VENDACLIENTE.IDCLIENTE AND PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDAS.SITUACAO = 1 AND VENDACLIENTE.SITUACAOVENDA = 2  GROUP BY VENDACLIENTE.IDVENDACLIENTE ORDER BY VENDACLIENTE.DTVENDA DESC ";

		} else if (situacao == 1) {
			selectQuery = "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, ALUNOS.foto, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE, SUM(VENDAS.PRECO_PRODUTO * VENDAS.TOTALPRODUTO) AS TOTAL_VENDA, VENDACLIENTE.SITUACAOVENDA FROM  PRODUTO, VENDAS, VENDACLIENTE, ALUNOS  WHERE ALUNOS.ID = VENDACLIENTE.IDCLIENTE AND PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDAS.SITUACAO = 1 AND VENDACLIENTE.SITUACAOVENDA = 1 GROUP BY VENDACLIENTE.IDVENDACLIENTE ORDER BY VENDACLIENTE.DTVENDA DESC ";

		} else {
			selectQuery = "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, ALUNOS.foto, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE, SUM(VENDAS.PRECO_PRODUTO * VENDAS.TOTALPRODUTO) AS TOTAL_VENDA, VENDACLIENTE.SITUACAOVENDA FROM  PRODUTO, VENDAS, VENDACLIENTE, ALUNOS  WHERE ALUNOS.ID = VENDACLIENTE.IDCLIENTE AND PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDAS.SITUACAO = 1 AND VENDACLIENTE.IDCLIENTE GROUP BY VENDACLIENTE.IDVENDACLIENTE ORDER BY VENDACLIENTE.DTVENDA DESC ";

		}

		// String selectQuery =
		// "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, ALUNOS.foto, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE  FROM  VENDACLIENTE, ALUNOS  WHERE ALUNOS.ID = VENDACLIENTE.IDCLIENTE";
		// AND VENDACLIENTE.SITUACAOVENDA = 2 - retirei para mostrar todas

		SQLiteDatabase db = this.getReadableDatabase();

		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// NumberFormat fortamatarNumero = NumberFormat.getCurrencyInstance();
		// Double novo = 0.0;
		Cursor cursor = db.rawQuery(selectQuery, null);

		ArrayList<VendasDTO> pessoas = new ArrayList<VendasDTO>();

		while (cursor.moveToNext()) {

			String dataVenda = (cursor.getString(cursor
					.getColumnIndex("DTVENDA")));

			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

			Date dtVenda = null;
			try {
				dtVenda = formatador.parse(dataVenda);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			VendasDTO pessoa = new VendasDTO();

			pessoa.setIdCliente(cursor.getString(0));
			pessoa.setNome(cursor.getString(1));
			pessoa.setSite(cursor.getString(2));
			pessoa.setTelefone(cursor.getString(3));
			pessoa.setEndereco(cursor.getString(4));
			pessoa.setFoto(cursor.getString(5));
			pessoa.setDtvenda(dtVenda);
			pessoa.setSituacaoVenda(cursor.getLong(9));
			pessoa.setIdvendacliente(cursor.getLong(7));
			pessoa.setTotal(cursor.getDouble(8));
			// pessoa.setNota(cursor.getDouble(6));
			pessoas.add(pessoa);
		}

		return pessoas;
	}

	public VendasDTO getVendaPorIdVendaCliente(Long idVendaCliente) {
		/*
		 * AND VENDACLIENTE.IDCLIENTE
		 * 
		 * */
		String selectQuery = "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, ALUNOS.foto, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE, SUM(PRODUTO.PRECO * VENDAS.TOTALPRODUTO) AS TOTAL_VENDA, VENDACLIENTE.SITUACAOVENDA FROM  PRODUTO, VENDAS, VENDACLIENTE, ALUNOS  WHERE ALUNOS.ID = VENDACLIENTE.IDCLIENTE AND PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDAS.SITUACAO = 1  AND VENDACLIENTE.IDVENDACLIENTE = "
				+ idVendaCliente + " GROUP BY VENDACLIENTE.IDVENDACLIENTE";
		// String selectQuery =
		// "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, ALUNOS.foto, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE  FROM  VENDACLIENTE, ALUNOS  WHERE ALUNOS.ID = VENDACLIENTE.IDCLIENTE";
		// AND VENDACLIENTE.SITUACAOVENDA = 2 - retirei para mostrar todas

		SQLiteDatabase db = this.getReadableDatabase();

		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// NumberFormat fortamatarNumero = NumberFormat.getCurrencyInstance();
		// Double novo = 0.0;
		Cursor cursor = db.rawQuery(selectQuery, null);

		VendasDTO pessoa = new VendasDTO();

		while (cursor.moveToNext()) {

			String dataVenda = (cursor.getString(cursor
					.getColumnIndex("DTVENDA")));

			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

			Date dtVenda = null;
			try {
				dtVenda = formatador.parse(dataVenda);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// VendasDTO pessoa = new VendasDTO();

			pessoa.setIdCliente(cursor.getString(0));
			pessoa.setNome(cursor.getString(1));
			pessoa.setSite(cursor.getString(2));
			pessoa.setTelefone(cursor.getString(3));
			pessoa.setEndereco(cursor.getString(4));
			pessoa.setFoto(cursor.getString(5));
			pessoa.setDtvenda(dtVenda);
			pessoa.setSituacaoVenda(cursor.getLong(9));
			pessoa.setIdvendacliente(cursor.getLong(7));
			pessoa.setTotal(cursor.getDouble(8));
			// pessoa.setNota(cursor.getDouble(6));

		}

		return pessoa;
	}

	public void deletar(Pessoa pessoa) {
		// TODO Auto-generated method stub
		String[] args = { pessoa.getId().toString() };
	
		getWritableDatabase().delete("Alunos", "id=?", args);
	}

	public void altera(Pessoa pessoa) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();

		// String idpessoa = buscarProximoIdCliente();

		values.put("id", pessoa.getId());
		values.put("nome", pessoa.getNome());
		values.put("site", pessoa.getSite());
		values.put("endereco", pessoa.getEndereco());

		// values.put("nota", pessoa.getNota());
		values.put("foto", pessoa.getFoto());
		values.put("telefone", pessoa.getTelefone());
		values.put("email", pessoa.getEmail());
		values.put("cpf_cnpj", pessoa.getCpf_cnpj());

		String[] args = { pessoa.getId().toString() };
		getWritableDatabase().update("Alunos", values, "id=?", args);

	}

	// VENDA
	public void salvaVendas(Vendas vendas, Pessoa pessoa) {

		// EXPLICAR PAR AO ANDROID COMO INSERIR NO BANCO

		ContentValues values = new ContentValues();
		values.put("idproduto", vendas.getIdProduto());
		values.put("idcliente", pessoa.getId());

		getWritableDatabase().insert("Vendas", null, values);

	}

	public List<VendasDTO> getListaVendas(String idVendaCliente) {

		String alunoPesquisar = idVendaCliente.toString();

		// /String selectQuery =
		// "SELECT PRODUTO.ID AS ID, PRODUTO.NOMEPRODUTO  AS NOMEPRODUTO, PRODUTO.PRECO AS PRECO FROM  PRODUTO, VENDAS  WHERE PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDCLIENTE = "+alunoPesquisar;
		String selectQuery = "SELECT PRODUTO.ID AS ID, PRODUTO.NOMEPRODUTO  AS NOMEPRODUTO, (VENDAS.PRECO_PRODUTO * VENDAS.TOTALPRODUTO) AS PRECO,VENDAS.TOTALPRODUTO AS TOTALPRODUTO, VENDAS.ID AS IDVENDA, VENDACLIENTE.IDVENDACLIENTE AS IDVENDACLIENTE, VENDACLIENTE.IDCLIENTE FROM  PRODUTO, VENDAS, VENDACLIENTE  WHERE PRODUTO.ID = VENDAS.IDPRODUTO AND VENDACLIENTE.IDVENDACLIENTE = VENDAS.IDVENDACLIENTE AND VENDAS.SITUACAO = 1 AND VENDACLIENTE.IDVENDACLIENTE = "
				+ alunoPesquisar;

		// Log.i("SELECT", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();

		// Cursor cursor =
		// db.rawQuery("SELECT PRODUTO.IDPRODUTO AS IDPRODUTO FROM VENDAS INNER JOIN PRODUTO ON VENDAS.IDPRODUTO = PRODUTO.ID WHERE VENDAS.IDCLIENTE = ?",
		// argsAluno);
		Cursor c = db.rawQuery(selectQuery, null);
		List<VendasDTO> vendas = new ArrayList<VendasDTO>();
		VendasDTO venda = new VendasDTO();

		Double totalVenda = 0.0;

		while (c.moveToNext()) {
			venda = new VendasDTO();
			venda.setId(c.getLong((c.getColumnIndex("ID"))));
			venda.setNomeProduto(c.getString((c.getColumnIndex("NOMEPRODUTO"))));
			// venda.setPreco(c.getString(c.getColumnIndex("PRECO")));
			venda.setPreco(c.getDouble(c.getColumnIndex("PRECO")));
			// / Double totalCompra =
			// Double.valueOf(c.getString(c.getColumnIndex("PRECO")));

			venda.setTotalProduto(c.getDouble(c.getColumnIndex("TOTALPRODUTO")));
			// Float totalGeral = c.getFloat(c.getColumnIndex("PRECO"));
			totalVenda += c.getDouble(c.getColumnIndex("PRECO"));

			// Float totalPre = c.getFloat(c.getColumnIndex("PRECO"));
			venda.setTotal(totalVenda);

			venda.setIdvenda(c.getLong(c.getColumnIndex("IDVENDA")));
			venda.setIdvendacliente(c.getLong(c
					.getColumnIndex("IDVENDACLIENTE")));
			venda.setIdCliente(c.getString(c.getColumnIndex("IDCLIENTE")));
			vendas.add(venda);

		}

		return vendas;
	}

	public Double getTotalVendaCliente(Pessoa pessoa) {

		String alunoPesquisar = pessoa.getId().toString();

		String selectQuery = "SELECT SUM(PRODUTO.PRECO * VENDAS.TOTALPRODUTO) AS PRECO FROM  PRODUTO, VENDAS, VENDACLIENTE  WHERE PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDACLIENTE.SITUACAOVENDA = 2 AND VENDAS.SITUACAO = 1 AND VENDACLIENTE.IDCLIENTE = "
				+ alunoPesquisar;

		SQLiteDatabase db = this.getReadableDatabase();
		Double preco = 0.0;
		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// NumberFormat fortamatarNumero = NumberFormat.getCurrencyInstance();
		// Double novo = 0.0;
		Cursor c = db.rawQuery(selectQuery, null);
		while (c.moveToNext()) {
			preco = c.getDouble(c.getColumnIndex("PRECO"));
			// novo = fortamatarNumero.format(preco);
		}
		return preco;

	}

	public VendasDTO getDadosVenda(Pessoa pessoa) {

		String alunoPesquisar = pessoa.getId().toString();
		VendasDTO dadosVenda = new VendasDTO();
		String selectQuery = "SELECT SUM(PRODUTO.PRECO * VENDAS.TOTALPRODUTO) AS PRECO, VENDACLIENTE.DTVENDA, VENDACLIENTE.SITUACAOVENDA FROM  PRODUTO, VENDAS, VENDACLIENTE  WHERE PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDAS.SITUACAO = 1 AND VENDACLIENTE.IDCLIENTE = "
				+ alunoPesquisar;

		SQLiteDatabase db = this.getReadableDatabase();
		Double preco = 0.0;
		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// NumberFormat fortamatarNumero = NumberFormat.getCurrencyInstance();
		// Double novo = 0.0;
		Cursor c = db.rawQuery(selectQuery, null);
		while (c.moveToNext()) {

			String dataVenda = (c.getString(c.getColumnIndex("DTVENDA")));

			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

			Date dtVenda = null;
			try {
				dtVenda = formatador.parse(dataVenda);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dadosVenda.setDtvenda(dtVenda);

			preco = c.getDouble(c.getColumnIndex("PRECO"));
			dadosVenda.setTotal(preco);
			// dadosVenda.setSituacaoVenda(c.getColumnIndex("SITUACAOVENDA"));
			// novo = fortamatarNumero.format(preco);
		}
		return dadosVenda;

	}
	
	
	
	
	public VendasDTO getDadosVendaEspecifica(Long idVendaCliente) {

		
		String selectQuery = "SELECT SUM(VENDAS.PRECO_PRODUTO * VENDAS.TOTALPRODUTO) AS PRECO, VENDACLIENTE.DTVENDA AS DTVENDA, VENDACLIENTE.SITUACAOVENDA FROM  PRODUTO, VENDAS, VENDACLIENTE  WHERE PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDAS.SITUACAO = 1 AND VENDACLIENTE.IDVENDACLIENTE = "
				+ idVendaCliente;

		SQLiteDatabase db = this.getReadableDatabase();
		Double preco = 0.0;
		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// NumberFormat fortamatarNumero = NumberFormat.getCurrencyInstance();
		// Double novo = 0.0;
		Cursor c = db.rawQuery(selectQuery, null);
		
		VendasDTO dadosVenda = new VendasDTO();
		
		while (c.moveToNext()) {
			c.moveToFirst();
			String dataVenda = (c.getString(c.getColumnIndex("DTVENDA")));

			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

			Date dtVenda = null;
			if (dataVenda != null) {
				try {
					dtVenda = formatador.parse(dataVenda);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dadosVenda.setDtvenda(dtVenda);

			preco = c.getDouble(c.getColumnIndex("PRECO"));
			dadosVenda.setTotal(preco);
			// dadosVenda.setSituacaoVenda(c.getColumnIndex("SITUACAOVENDA"));
			// novo = fortamatarNumero.format(preco);
		}
		return dadosVenda;

	}
	

	public VendasDTO getDadosVenda(Long idVendaCliente) {
		Pessoa p = new Pessoa();
		VendasDTO dadosVenda = new VendasDTO();
		String selectQuery = "SELECT SUM(VENDAS.PRECO_PRODUTO * VENDAS.TOTALPRODUTO) AS PRECO, VENDACLIENTE.DTVENDA AS DTVENDA, VENDACLIENTE.SITUACAOVENDA FROM  PRODUTO, VENDAS, VENDACLIENTE  WHERE PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDAS.SITUACAO = 1 AND VENDACLIENTE.IDVENDACLIENTE = "
				+ idVendaCliente;

		SQLiteDatabase db = this.getReadableDatabase();
		Double preco = 0.0;
		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// NumberFormat fortamatarNumero = NumberFormat.getCurrencyInstance();
		// Double novo = 0.0;
		Cursor c = db.rawQuery(selectQuery, null);
			
		if (c.getCount() > 0) {
				c.moveToFirst();
				
				String dataVenda = (c.getString(c.getColumnIndex("DTVENDA")));

				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

				Date dtVenda = null;
				if (dataVenda != null) {
					try {
						dtVenda = formatador.parse(dataVenda);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				dadosVenda.setDtvenda(dtVenda);

				preco = c.getDouble(c.getColumnIndex("PRECO"));
				dadosVenda.setTotal(preco);
				
				}
			
			return dadosVenda;
		
		

			
			// dadosVenda.setSituacaoVenda(c.getColumnIndex("SITUACAOVENDA"));
			// novo = fortamatarNumero.format(preco);
		}
		

	

	public Integer getIdVendaCliente(Pessoa pessoa) {

		String alunoPesquisar = pessoa.getId().toString();

		String selectQuery = "SELECT VENDACLIENTE.IDVENDACLIENTE AS IDVENDACLIENTE  FROM  PRODUTO, VENDAS, VENDACLIENTE  WHERE PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND VENDACLIENTE.SITUACAOVENDA = 2 AND VENDAS.SITUACAO = 1 AND VENDACLIENTE.IDCLIENTE = "
				+ alunoPesquisar;

		SQLiteDatabase db = this.getReadableDatabase();
		Integer idVendaCliente = 0;
		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// NumberFormat fortamatarNumero = NumberFormat.getCurrencyInstance();
		// Double novo = 0.0;
		Cursor c = db.rawQuery(selectQuery, null);
		while (c.moveToNext()) {
			idVendaCliente = c.getInt(c.getColumnIndex("IDVENDACLIENTE"));
			// novo = fortamatarNumero.format(preco);
		}
		return idVendaCliente;
		// return Double.parseDouble(precoFormatado.format(preco));

		/*
		 * if (preco != 0) { return
		 * Double.parseDouble(precoFormatado.format(preco)); } else { return
		 * 0.0; }
		 */

		// return Double.parseDouble(precoFormatado.format(preco));

	}

	// PRODUTO
	public void salvaProduto(ProdutoDTO produto, String categoria) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();

		// String idproduto = buscarProximoIdProduto();
		UUID uuid = UUID.randomUUID();

		values.put("id", uuid.toString());
		values.put("nomeproduto", produto.getNomeProduto());
		values.put("preco", produto.getPrecoProduto().toString());
		values.put("categoria", categoria);
		getWritableDatabase().insert("Produto", null, values);
	}
	
	
	
	
	
		
		public void salvaProdutoECategoriaCadastroInicial() {
			// TODO Auto-generated method stub
			// String idCategoria = buscaProximoIdCategoria();
			
			UUID uuidCategoria = UUID.randomUUID();
			
			ContentValues values = new ContentValues();

			values.put("nomeCategoria", "Categoria-Demonstração");
			values.put("idCategoria", uuidCategoria.toString());

			getWritableDatabase().insert("CategoriaProduto", null, values);
			
			
			UUID uuidProduto = UUID.randomUUID();

			ContentValues valuesProduto = new ContentValues();

			valuesProduto.put("id", uuidProduto.toString());
			valuesProduto.put("nomeproduto", "Cx Cerveja");
			valuesProduto.put("preco", 10.0);
			valuesProduto.put("categoria", uuidCategoria.toString());

			getWritableDatabase().insert("Produto", null, valuesProduto);
		}
		
		
		
		

	public List<Produto> getListaProduto() {
		// TODO Auto-generated method stub

		String[] colunas = { "id", "nomeproduto", "preco", "categoria" };
		Cursor cursor = getWritableDatabase().query("Produto", colunas, null,
				null, null, null, null);

		ArrayList<Produto> produtos = new ArrayList<Produto>();

		while (cursor.moveToNext()) {
			Produto produto = new Produto();
			produto.setId(cursor.getString(0));
			produto.setNomeProduto(cursor.getString(1));
			produto.setPreco(cursor.getDouble(2));
			produto.setCategoria(cursor.getString(3));
			produtos.add(produto);

		}
		return produtos;

	}

	public List<ProdutoDTO> getListaProdutoComCategoria() {
		// TODO Auto-generated method stub

		//
		List<ProdutoDTO> produtoDTO = new ArrayList<ProdutoDTO>();

		String selectQuery = "SELECT p.id, p.nomeproduto, p.preco, p.categoria,"
				+ "c.idcategoria, c.nomecategoria from produto p left join categoriaproduto c"
				+ " on c.idcategoria =  p.categoria";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);
		while (c.moveToNext()) {
			ProdutoDTO dadosVenda = new ProdutoDTO();

			dadosVenda.setNomeProduto(c.getString(c
					.getColumnIndex("NOMEPRODUTO")));
			dadosVenda.setIdProduto(c.getString(c.getColumnIndex("ID")));
			dadosVenda
					.setIdCategoria(c.getString(c.getColumnIndex("CATEGORIA")));
			dadosVenda.setNomeCategoria(c.getString(c
					.getColumnIndex("NOMECATEGORIA")));
			dadosVenda.setPrecoProduto(c.getDouble(c.getColumnIndex("PRECO")));
			produtoDTO.add(dadosVenda);
		}

		return produtoDTO;

	}

	public List<Produto> getListaProdutoTab(String categoria) {
		// TODO Auto-generated method stub

		String[] colunas = { "id", "nomeproduto", "preco" };

		String[] args = { categoria };
		Cursor cursor = getWritableDatabase().query("Produto", colunas,
				"categoria=?", args, null, null, null);

		ArrayList<Produto> produtos = new ArrayList<Produto>();

		while (cursor.moveToNext()) {
			Produto produto = new Produto();
			produto.setId(cursor.getString(0));
			produto.setNomeProduto(cursor.getString(1));
			produto.setPreco(cursor.getDouble(2));
			produtos.add(produto);

		}
		return produtos;

	}

	public void salvaVendaNew(Produto produto, String alunoParaSerAlterado,
			String idvendaclienteInsere, Double totalProduto) {

		// public void salvaVendaNew() {
		// TODO Auto-generated method stub

		// DATA
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String dataAtual = dateFormat.format(cal.getTime());

		ContentValues values = new ContentValues();

		String controle = buscaUltimoId(idvendaclienteInsere);

		values.put("idproduto", produto.getId());
		values.put("idcliente", alunoParaSerAlterado);
		values.put("idvendacliente", idvendaclienteInsere);
		values.put("controle", controle);
		values.put("totalproduto", totalProduto.toString());
		values.put("dtvenda", dataAtual);
		values.put("situacao", 1);
		values.put("preco_produto", produto.getPreco());
		getWritableDatabase().insert("Vendas", null, values);

	}

	private String buscaUltimoId(String idvendaclienteInsere) {
		// TODO Auto-generated method stub

		// Cursor cursor =
		// getWritableDatabase().rawQuery("SELECT MAX(CONTROLE) FROM VENDAS WHERE IDVENDACLIENTE = ? ",
		// args);
		String vendaPesquisar = idvendaclienteInsere.toString();
		String selectQuery = "SELECT MAX(CONTROLE) AS CONTROLE FROM VENDAS WHERE IDVENDACLIENTE = "
				+ vendaPesquisar;

		SQLiteDatabase db = this.getReadableDatabase();
		Integer ultimo = 0;
		String ultimocontrole = null;

		Cursor c = db.rawQuery(selectQuery, null);
		// while (c.moveToNext()){ //HAVIA FEITO UTILIZANDO O WHILE, MAS PERCEBI
		// QUE ELE � MAIS LENTO.
		if (c.moveToLast()) {

			ultimocontrole = c.getString(c.getColumnIndex("CONTROLE"));
		}
		// }

		if (ultimocontrole != null) {
			ultimo = Integer.parseInt(ultimocontrole);
			ultimo = ultimo + 1;
		} else {

			ultimo = 1;

		}

		return ultimo.toString();

	}

	public void salvaVendaCliente(String alunoParaSerAlterado) {
		// TODO Auto-generated method stub

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");

		SimpleDateFormat data_formatada_para_relatorio = new SimpleDateFormat(
				"yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		String dataAtual = dateFormat.format(cal.getTime());

		String data_atual_para_relatorio = data_formatada_para_relatorio
				.format(cal.getTime());

		ContentValues values = new ContentValues();

		values.put("idcliente", alunoParaSerAlterado);
		values.put("dtvenda", dataAtual);
		values.put("situacaovenda", 2);
		values.put("dt_venda_para_relatorio", data_atual_para_relatorio);

		// values.put("dtvenda", "20/08/2015 17:21:46");
		// /values.put("dt_venda_para_relatorio", "2015-08-20");

		/*
		 * 
		 * values.put("dtvenda", "01/08/2015 17:21:46");
		 * values.put("situacaovenda", 2); values.put("dt_venda_para_relatorio",
		 * "2015-08-01");
		 */
		getWritableDatabase().insert("VendaCliente", null, values);
	}

	public String consultaVendaAberta(String alunoParaSerAlterado) {
		// TODO Auto-generated method stub

		String alunoPesquisar = alunoParaSerAlterado.toString();
		String selectQuery = "SELECT IDVENDACLIENTE FROM VENDACLIENTE WHERE SITUACAOVENDA = 2 AND IDCLIENTE = "
				+ alunoPesquisar;

		SQLiteDatabase db = this.getReadableDatabase();

		String idvendacliente = null;

		Cursor c = db.rawQuery(selectQuery, null);
		// while (c.moveToNext()){ //HAVIA FEITO UTILIZANDO O WHILE, MAS PERCEBI
		// QUE ELE � MAIS LENTO.
		if (c.moveToLast()) {

			idvendacliente = c.getString(c.getColumnIndex("IDVENDACLIENTE"));
		}
		// }

		return idvendacliente;

	}

	public String trazUltimaVendaCliente(Pessoa alunoParaSerAlterado) {
		// TODO Auto-generated method stub

		String alunoPesquisar = alunoParaSerAlterado.getId().toString();
		String selectQuery = "SELECT max(IDVENDACLIENTE) AS IDVENDACLIENTE FROM VENDACLIENTE WHERE IDCLIENTE = '"
				+ alunoPesquisar + "'";

		SQLiteDatabase db = this.getReadableDatabase();

		String idvendacliente = null;

		Cursor c = db.rawQuery(selectQuery, null);
		// while (c.moveToNext()){ //HAVIA FEITO UTILIZANDO O WHILE, MAS PERCEBI
		// QUE ELE � MAIS LENTO.
		if (c.moveToLast()) {

			idvendacliente = c.getString(c.getColumnIndex("IDVENDACLIENTE"));
		}
		// }

		return idvendacliente;

	}

	public void fecharVenda(String idvendacliente) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		VendaCliente venda = new VendaCliente();
		String situacao = "1";

		values.put("situacaovenda", situacao);
		String[] args = { idvendacliente };
		getWritableDatabase().update("VENDACLIENTE", values,
				"idvendacliente=?", args);

	}

	public boolean eCliente(String telefone) {
		// TODO Auto-generated method stub
		String[] args = { telefone };
		Cursor cursor = getWritableDatabase().rawQuery(
				"SELECT ID FROM ALUNOS WHERE TELEFONE = ? ", args);
		boolean existe = cursor.moveToFirst();
		return existe;
	}

	// LISTA VENDACLIENTE

	public List<VendaCliente> getListaVendaCliente(Long idvendacliente,
			Long idVendaclienteSincronizar) {

		String selectQuery = null;

		if (idvendacliente == null && idVendaclienteSincronizar == null) {

			selectQuery = "SELECT IDVENDACLIENTE,IDCLIENTE,DTVENDA,SITUACAOVENDA,TOTALVENDA, DESCONTOVENDA FROM  VENDACLIENTE";

		} else if (idVendaclienteSincronizar != null) {

			selectQuery = "SELECT IDVENDACLIENTE,IDCLIENTE,DTVENDA,SITUACAOVENDA,TOTALVENDA, DESCONTOVENDA FROM  VENDACLIENTE WHERE IDVENDACLIENTE = "
					+ idVendaclienteSincronizar;

		} else if (idvendacliente != null) {

			selectQuery = "SELECT IDVENDACLIENTE,IDCLIENTE,DTVENDA,SITUACAOVENDA,TOTALVENDA, DESCONTOVENDA FROM  VENDACLIENTE WHERE IDVENDACLIENTE = "
					+ idvendacliente;
		}

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);

		List<VendaCliente> vendasCliente = new ArrayList<VendaCliente>();
		VendaCliente venda = new VendaCliente();

		while (c.moveToNext()) {
			venda = new VendaCliente();
			venda.setIdvendacliente(c.getLong((c
					.getColumnIndex("IDVENDACLIENTE"))));
			venda.setIdcliente(c.getString((c.getColumnIndex("IDCLIENTE"))));
			// venda.setDtvenda(new Date(getColumnIndex("DTVENDA")));
			venda.setSituacaovenda(c.getInt((c.getColumnIndex("SITUACAOVENDA"))));
			// venda.setPreco(c.getString(c.getColumnIndex("PRECO")));
			venda.setTotalVenda(c.getDouble(c.getColumnIndex("TOTALVENDA")));
			venda.setDescontoVenda(c.getDouble(c
					.getColumnIndex("DESCONTOVENDA")));
			String dataVenda = (c.getString(c.getColumnIndex("DTVENDA")));

			SimpleDateFormat formatador = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss");

			Date dtVenda = null;
			try {
				dtVenda = formatador.parse(dataVenda);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			venda.setDtvenda(dtVenda);

			vendasCliente.add(venda);

		}

		return vendasCliente;
	}

	public List<Vendas> getListaVendasEnviarSite(Long idVendaclienteSincronizar) {


		String selectQuery = null;
		if (idVendaclienteSincronizar == null) {
			// Query para sincroniza��o manual. Coloquei em 18-11-2014 para a
			// sincroniza��o manual
			selectQuery = "SELECT ID, IDCLIENTE,IDPRODUTO,IDVENDACLIENTE,CONTROLE,TOTALPRODUTO,DTVENDA,SITUACAO FROM  VENDAS";
		} else {
			// Query para sincroniza��o no momento da venda
			selectQuery = "SELECT ID, IDCLIENTE,IDPRODUTO,IDVENDACLIENTE,CONTROLE,TOTALPRODUTO,DTVENDA,SITUACAO FROM  VENDAS WHERE IDVENDACLIENTE = "
					+ idVendaclienteSincronizar;
		}

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);

		List<Vendas> vendas = new ArrayList<Vendas>();
		Vendas venda = new Vendas();

		while (c.moveToNext()) {
			venda = new Vendas();
			venda.setId(c.getLong((c.getColumnIndex("ID"))));
			venda.setIdCliente(c.getString((c.getColumnIndex("IDCLIENTE"))));
			// venda.setDtvenda(new Date(getColumnIndex("DTVENDA")));
			venda.setIdProduto(c.getString((c.getColumnIndex("IDPRODUTO"))));
			venda.setIdVendaCliente(c.getLong(c
					.getColumnIndex("IDVENDACLIENTE")));
			venda.setControle(c.getLong(c.getColumnIndex("CONTROLE")));
			venda.setTotalProduto(c.getLong(c.getColumnIndex("TOTALPRODUTO")));
			venda.setSituacao(c.getLong(c.getColumnIndex("SITUACAO")));
			// DATA DA VENDA
			String dataVenda = (c.getString(c.getColumnIndex("DTVENDA")));

			SimpleDateFormat formatador = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss");

			Date dtVenda = null;
			try {
				dtVenda = formatador.parse(dataVenda);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			venda.setDtvenda(dtVenda);

			vendas.add(venda);

		}

		return vendas;
	}

	public void insereUsuario(List<Usuario> usuarioLogin) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();

		try {
			for (Usuario usuario : usuarioLogin) {
				values.put("email", usuario.getEmail());
				// values.put("password", usuario.getPassword());
				values.put("idusuario", usuario.getIdusuario());
				values.put("idempresa", usuario.getIdempresa());
				values.put("tipoContaEmpresa", usuario.getTipoContaEmpresa());
				values.put("tipoEmpresa", usuario.getTipoEmpresa());
				values.put("situacaologin", 1);
				values.put("usuario_master", usuario.getUsuario_master());
				values.put("sincronizacaoRegistroProduto",
						usuario.getSincronizacaoRegistroProduto());
				values.put("sincronizacaoFechamentoVenda",
						usuario.getSincronizacaoFechamentoVenda());
				values.put("sincronizacaoEntradaAplicativo",
						usuario.getSincronizacaoEntradaAplicativo());
				values.put("fluxo_aprovacao_cadastro",
						usuario.getFluxo_aprovacao_cadastro());

			}

			getWritableDatabase().insert("Usuario", null, values);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public String verificaUsuarioDispositivo(Integer tipo) {
		// TODO Auto-generated method stub
		// O tipo = 1 quer dizer que o usu�rio est� logado, tipo = 2, o usu�rio
		// n�o est� logado.

		String selectQuery = "SELECT EMAIL FROM USUARIO WHERE SITUACAOLOGIN="
				+ tipo;

		SQLiteDatabase db = this.getReadableDatabase();

		String email = null;
		Cursor c = db.rawQuery(selectQuery, null);
		// while (c.moveToNext()){ //HAVIA FEITO UTILIZANDO O WHILE, MAS PERCEBI
		// QUE ELE � MAIS LENTO.
		if (c.moveToLast()) {

			email = c.getString(c.getColumnIndex("EMAIL"));
		}

		return email;
	}

	/*
	 * M�todo para deletar a tabela de usu�rio ap�s a solicita��o de lgin feita
	 * pelo usu�rio
	 */
	public void FazerLogof() {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();
		VendaCliente venda = new VendaCliente();
		String situacao = "2";

		values.put("situacaologin", situacao);
		getWritableDatabase().update("USUARIO", values, null, null);

		/*
		 * String deletaUsuario = "DELETE FROM USUARIO";
		 * 
		 * SQLiteDatabase db = this.getReadableDatabase();
		 * 
		 * //DELE��O DOS DADOS AP�S FAZER O LOGOFF db.execSQL(deletaUsuario);
		 */
	}

	/*
	 * M�todo para deletar todas as tabelas ap�s a solicita��o de troca de
	 * usu�rio.
	 */
	public void mudarUsuario() {
		// TODO Auto-generated method stub

		String deletaUsuario = "DELETE FROM USUARIO";

		String deletaAluno = "DELETE FROM Alunos";

		String deletaVendas = "DELETE FROM VENDAS";

		String deletaProduto = "DELETE FROM PRODUTO";

		String deletaVendaCliente = "DELETE FROM VENDACLIENTE";
		
		String deletaPagamento = "DELETE FROM PAGAMENTO";

		String deletaControleSincronizacao = "DELETE FROM CONTROLESINCRONIZACAO";
		String deletarCategoriaProduto = "DELETE FROM CATEGORIAPRODUTO";

		String deletarSequenceVendas = "DELETE FROM SQLITE_SEQUENCE WHERE NAME  = 'VENDAS'";
		String deletarSequenceVendaCliente = "DELETE FROM SQLITE_SEQUENCE WHERE NAME  = 'VENDACLIENTE'";
		String deletarSequencePagamento = "DELETE FROM SQLITE_SEQUENCE WHERE NAME  = 'PAGAMENTO'";

		
		SQLiteDatabase db = this.getReadableDatabase();

		// DELE��O DOS DADOS AP�S FAZER O LOGOFF
		db.execSQL(deletaUsuario);
		db.execSQL(deletaAluno);
		db.execSQL(deletaVendas);
		db.execSQL(deletaProduto);
		db.execSQL(deletaVendaCliente);
		db.execSQL(deletaControleSincronizacao);
		db.execSQL(deletarCategoriaProduto);
		db.execSQL(deletarSequenceVendas);
		db.execSQL(deletarSequenceVendaCliente);
		db.execSQL(deletaPagamento);
		db.execSQL(deletarSequencePagamento);

		
	}

	// BUSCAR PROXIMO ID DE PRODUTO
	private String buscarProximoIdProduto() {
		// TODO Auto-generated method stub

		String selectQuery = "SELECT MAX(ID) AS ID FROM PRODUTO";

		SQLiteDatabase db = this.getReadableDatabase();
		Integer proximo = 0;
		String ultimocontrole = null;

		Cursor c = db.rawQuery(selectQuery, null);
		// while (c.moveToNext()){ //HAVIA FEITO UTILIZANDO O WHILE, MAS PERCEBI
		// QUE ELE � MAIS LENTO.
		if (c.moveToLast()) {

			ultimocontrole = c.getString(c.getColumnIndex("ID"));
		}
		// }

		if (ultimocontrole != null) {
			proximo = Integer.parseInt(ultimocontrole);
			proximo = proximo + 1;
		} else {

			proximo = 1;

		}

		return proximo.toString();

	}

	
	public List<Usuario> getDadosUsuario() {

		String selectQuery = "SELECT IDEMPRESA, IDUSUARIO FROM USUARIO";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);

		List<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();

		while (c.moveToNext()) {
			usuario = new Usuario();
			usuario.setIdempresa(c.getLong((c.getColumnIndex("IDEMPRESA"))));
			usuario.setIdusuario(c.getLong((c.getColumnIndex("IDUSUARIO"))));

			usuarios.add(usuario);

		}

		return usuarios;
	}

	public Usuario getDadosUsuarioDispositivo() {

		String selectQuery = "SELECT IDEMPRESA, IDUSUARIO,FLUXO_APROVACAO_CADASTRO,USUARIO_MASTER FROM USUARIO";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);

		// List<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();

		while (c.moveToNext()) {
			usuario.setIdempresa(c.getLong((c.getColumnIndex("IDEMPRESA"))));
			usuario.setIdusuario(c.getLong((c.getColumnIndex("IDUSUARIO"))));
			usuario.setFluxo_aprovacao_cadastro(c.getString(c
					.getColumnIndex("FLUXO_APROVACAO_CADASTRO")));
			usuario.setUsuario_master(c.getString(c
					.getColumnIndex("USUARIO_MASTER")));
		}

		return usuario;
	}

	public Pessoa getDadosPessoa(String idCliente) {

		String sql = "SELECT id,nome, telefone, endereco, site, foto, email, cpf_cnpj, observacao, situacao_cadastro from alunos where id = '"
				+ idCliente + "'";

		SQLiteDatabase sqLiteDatabase = getWritableDatabase();

		Cursor c = sqLiteDatabase.rawQuery(sql, null);
		Pessoa pessoa = new Pessoa();
		if (c.moveToNext()) {
			pessoa.setId(c.getString(c.getColumnIndex("id")));
			pessoa.setNome(c.getString(c.getColumnIndex("nome")));
			pessoa.setSituacao_cadastro(c.getString(c
					.getColumnIndex("situacao_cadastro")));
		}
		return pessoa;
	}

	public void salvaProdutoDoSite(List<Produto> produtos) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();

		try {
			for (Produto produto : produtos) {
				// popular os dados do produto
				values.put("id", produto.getId());
				// values.put("password", usuario.getPassword());
				values.put("nomeproduto", produto.getNomeProduto());
				values.put("preco", produto.getPreco());
				values.put("categoria", produto.getCategoria());

				if (produtoExiste(produto.getId()) == false) {
					// Inserir produtos do site
					getWritableDatabase().insert("Produto", null, values);
				} else {
					// Atualizar dados do produto
					String[] id_atualizar = { produto.getId().toString() };
					getWritableDatabase().update("Produto", values, "id=?",
							id_atualizar);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private boolean produtoExiste(String id) {
		// TODO Auto-generated method stub

		String selectQuery = "SELECT ID FROM PRODUTO WHERE ID = '" + id+"'";

		Boolean existe = false;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToLast()) {

			// String idproduto = c.getString(c.getColumnIndex("ID"));
			existe = true;
		} else {

			existe = false;
		}
		// }

		return existe;
	}

	// SALVA PRODUTO DO SITE
	public void salvaPessoasDoSite(List<Pessoa> pessoas) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();

		try {
			for (Pessoa pessoa : pessoas) {

				values.put("id", pessoa.getId());
				// values.put("password", usuario.getPassword());
				values.put("nome", pessoa.getNome());
				values.put("email", pessoa.getEmail());
				values.put("telefone", pessoa.getTelefone());
				values.put("endereco", pessoa.getEndereco());
				values.put("site", pessoa.getSite());
				values.put("cpf_cnpj", pessoa.getCpf_cnpj());
				values.put("observacao", pessoa.getObservacao());
				values.put("situacao_cadastro", pessoa.getSituacao_cadastro());

				if (clienteExiste(pessoa.getId()) == false) {

					getWritableDatabase().insert("Alunos", null, values);

				} else {
					String[] args = { pessoa.getId().toString() };
					getWritableDatabase()
							.update("Alunos", values, "id=?", args);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// SALVA CLIENTE DO SITE

	}

	private boolean clienteExiste(String id) {
		// TODO Auto-generated method stub

		String selectQuery = "SELECT ID FROM ALUNOS WHERE ID = '" + id + "'";

		Boolean existe = false;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToLast()) {

			// String idproduto = c.getString(c.getColumnIndex("ID"));
			existe = true;
		} else {

			existe = false;
		}
		// }

		return existe;
	}

	private boolean verificaCategoriaExiste(String id) {
		// TODO Auto-generated method stub

		String selectQuery = "SELECT IDCATEGORIA FROM CATEGORIAPRODUTO WHERE IDCATEGORIA = '"
				+ id + "'";

		Boolean existe = false;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToLast()) {

			// String idproduto = c.getString(c.getColumnIndex("ID"));
			existe = true;
		} else {

			existe = false;
		}
		// }

		return existe;
	}

	// Controle de sincroniza��o
	public void salvaControleSincronizacaoCliente(String idvendacliente) {
		// TODO Auto-generated method stub

		// VERIFICAR SE N�O EXISTE UM IDVENDACLIENTE NA TABELA, PARA EVITAR DE
		// SINCRONIZAR TODOS.

		ContentValues values = new ContentValues();
		values.put("idvendacliente", idvendacliente);
		getWritableDatabase().insert("CONTROLESINCRONIZACAO", null, values);

		// S� SINCRONIZAR O �LTIMO CLICADO
		// TODA VEZ QUE SINCRONIZAR GRAVAR A DATA

	}

	public Long existeAlunoClicado() {
		// TODO Auto-generated method stub

		String selectQuery = "SELECT IDVENDACLIENTE FROM CONTROLESINCRONIZACAO";

		Boolean existe = false;
		SQLiteDatabase db = this.getReadableDatabase();

		Long idcliente = null;
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToLast()) {
			idcliente = c.getLong(c.getColumnIndex("IDVENDACLIENTE"));
			// String idproduto = c.getString(c.getColumnIndex("ID"));

		}
		// }

		return idcliente;
	}

	public void deletarControle() {
		// TODO Auto-generated method stub
		getWritableDatabase().delete("CONTROLESINCRONIZACAO", null, null);
	}

	public Long tipoContaEmpresaUsuario() {
		// TODO Auto-generated method stub
		String consultaTipoEmpresa = "SELECT TIPOEMPRESA, TIPOCONTAEMPRESA FROM USUARIO";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(consultaTipoEmpresa, null);
		Long tipoContaEmpresa = null;
		if (c.moveToLast()) {
			tipoContaEmpresa = c.getLong(c.getColumnIndex("TIPOCONTAEMPRESA"));

		}

		return tipoContaEmpresa;

	}

	public String informacoesUsuario() {

		String sql = "SELECT USUARIO_MASTER FROM USUARIO";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(sql, null);
		String usuario_master = null;

		if (c.moveToLast()) {
			usuario_master = c.getString(c
					.getColumnIndexOrThrow("USUARIO_MASTER"));
		}

		if (usuario_master == null) {
			return "sem_usuario_no_dispositivo";
		} else {
			return usuario_master;
		}

	}

	public Long tipoEmpresa() {
		// TODO Auto-generated method stub
		String consultaTipoEmpresa = "SELECT TIPOEMPRESA, TIPOCONTAEMPRESA FROM USUARIO";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(consultaTipoEmpresa, null);
		Long tipoEmpresa = null;
		if (c.moveToLast()) {
			tipoEmpresa = c.getLong(c.getColumnIndex("TIPOEMPRESA"));
		}

		return tipoEmpresa;

	}

	public void deletarProdutoVenda(Long idvenda) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();
		Vendas venda = new Vendas();
		String situacao = "2";

		values.put("situacao", situacao);
		String[] args = { idvenda.toString() };
		getWritableDatabase().update("VENDAS", values, "id=?", args);

	}

	public void salvaCategoriaProdutoDoSite(List<CategoriaProduto> categorias) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();

		try {
			for (CategoriaProduto categoria : categorias) {

				values.put("idcategoria", categoria.getIdCategoria());

				values.put("nomecategoria", categoria.getNomeCategoria());

				if (verificaCategoriaExiste(categoria.getIdCategoria()) == false) {

					getWritableDatabase().insert("CategoriaProduto", null,
							values);

				} else {

					String[] categoria_atualizar = { categoria.getIdCategoria()
							.toString() };

					getWritableDatabase().update("CategoriaProduto", values,
							"idcategoria=?", categoria_atualizar);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public List<CategoriaProduto> getListaCategoriaProduto() {
		// TODO Auto-generated method stub
		String[] colunas = { "idcategoria", "nomecategoria" };
		Cursor cursor = getWritableDatabase().query("CategoriaProduto",
				colunas, null, null, null, null, null);

		ArrayList<CategoriaProduto> categorias = new ArrayList<CategoriaProduto>();

		while (cursor.moveToNext()) {
			categorias.add(new CategoriaProduto(cursor.getString(0), cursor
					.getString(1)));
			// categorias.add(new CategoriaProduto(9, "10"));

		}
		return categorias;
	}

	// //////////////////////////////////INICIO VENDAS

	public List<ControleSincronizacao> vendaClienteParaSincronizar() {
		// TODO Auto-generated method stub

		String selectQuery = "SELECT IDVENDACLIENTE FROM CONTROLESINCRONIZACAO";

		Boolean existe = false;
		SQLiteDatabase db = this.getReadableDatabase();

		Long idcliente = null;
		Cursor c = db.rawQuery(selectQuery, null);

		List<ControleSincronizacao> controles = new ArrayList<ControleSincronizacao>();
		ControleSincronizacao controleSincronizacao = new ControleSincronizacao();

		while (c.moveToNext()) {
			controleSincronizacao = new ControleSincronizacao();
			controleSincronizacao.setIdvendacliente(c.getLong(c
					.getColumnIndex("IDVENDACLIENTE")));
			controles.add(controleSincronizacao);
		}

		return controles;
	}

	/*
	 * Pega a lita de clientes para
	 */
	public List<VendaCliente> getListaVendaClienteSincronizar(
			Long idvendacliente,
			List<ControleSincronizacao> idVendaclienteSincronizar,
			String tipo_venda_sincronizar) {

		String selectQuery = null;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = null;
		String nova = "";
		Integer controle = 0;

		/*
		 * Inclus�o em 19-11-2014 Essa condi��o foi colocada para que esse
		 * m�todo fosse utilizado n�o s� para a sincroniza��o de vendas ap�s a
		 * venda atual, mas tamb�m para que o cliente possa sincronizar todas as
		 * vendas caso tenha algum erro.
		 */
		if (tipo_venda_sincronizar.endsWith("somente_vendas_pendentes")) {

			for (ControleSincronizacao controleSincronizacao : idVendaclienteSincronizar) {
				StringBuffer strBuff = new StringBuffer();

				controle++;
				String ids = controleSincronizacao.getIdvendacliente()
						.toString();

				strBuff.append(ids);

				if (controle < idVendaclienteSincronizar.size()) {
					strBuff.append(",");
				}
				if (strBuff.toString() != null) {
					nova += strBuff.toString();
				}
			}

			selectQuery = "SELECT IDVENDACLIENTE,IDCLIENTE,DTVENDA,SITUACAOVENDA,TOTALVENDA FROM  VENDACLIENTE WHERE IDVENDACLIENTE IN ("
					+ nova + ")";
		} else {
			selectQuery = "SELECT IDVENDACLIENTE,IDCLIENTE,DTVENDA,SITUACAOVENDA,TOTALVENDA FROM  VENDACLIENTE";
		}

		c = db.rawQuery(selectQuery, null);

		List<VendaCliente> vendasCliente = new ArrayList<VendaCliente>();
		VendaCliente venda = new VendaCliente();

		while (c.moveToNext()) {
			venda = new VendaCliente();
			venda.setIdvendacliente(c.getLong((c
					.getColumnIndex("IDVENDACLIENTE"))));
			venda.setIdcliente(c.getString((c.getColumnIndex("IDCLIENTE"))));
			// venda.setDtvenda(new Date(getColumnIndex("DTVENDA")));
			venda.setSituacaovenda(c.getInt((c.getColumnIndex("SITUACAOVENDA"))));
			// venda.setPreco(c.getString(c.getColumnIndex("PRECO")));
			venda.setTotalVenda(c.getDouble(c.getColumnIndex("TOTALVENDA")));
			String dataVenda = (c.getString(c.getColumnIndex("DTVENDA")));

			SimpleDateFormat formatador = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss");

			Date dtVenda = null;
			try {
				dtVenda = formatador.parse(dataVenda);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			venda.setDtvenda(dtVenda);

			vendasCliente.add(venda);

		}

		return vendasCliente;
	}

	public List<Vendas> getListaVendasEnviarSiteSincronizar(
			List<ControleSincronizacao> idVendaclienteSincronizar) {

		String nova = "";
		Integer controle = 0;

		for (ControleSincronizacao controleSincronizacao : idVendaclienteSincronizar) {
			StringBuffer strBuff = new StringBuffer();

			controle++;
			String ids = controleSincronizacao.getIdvendacliente().toString();
			// ids += ids;
			strBuff.append(ids);

			if (controle < idVendaclienteSincronizar.size()) {
				strBuff.append(",");
			}
			if (strBuff.toString() != null) {
				nova += strBuff.toString();
			}
		}

		// String selectQuery =
		// "SELECT ID, IDCLIENTE,IDPRODUTO,IDVENDACLIENTE,CONTROLE,TOTALPRODUTO,DTVENDA,SITUACAO FROM  VENDAS WHERE IDVENDACLIENTE IN ("+nova+")";
		String selectQuery = "SELECT ID, IDCLIENTE,IDPRODUTO,IDVENDACLIENTE,CONTROLE,TOTALPRODUTO,DTVENDA,SITUACAO FROM  VENDAS WHERE IDVENDACLIENTE IN ("
				+ nova + ")";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);

		List<Vendas> vendas = new ArrayList<Vendas>();
		Vendas venda = new Vendas();

		while (c.moveToNext()) {
			venda = new Vendas();
			venda.setId(c.getLong((c.getColumnIndex("ID"))));
			venda.setIdCliente(c.getString((c.getColumnIndex("IDCLIENTE"))));
			// venda.setDtvenda(new Date(getColumnIndex("DTVENDA")));
			venda.setIdProduto(c.getString((c.getColumnIndex("IDPRODUTO"))));
			venda.setIdVendaCliente(c.getLong(c
					.getColumnIndex("IDVENDACLIENTE")));
			venda.setControle(c.getLong(c.getColumnIndex("CONTROLE")));
			venda.setTotalProduto(c.getLong(c.getColumnIndex("TOTALPRODUTO")));
			venda.setSituacao(c.getLong(c.getColumnIndex("SITUACAO")));
			// DATA DA VENDA
			String dataVenda = (c.getString(c.getColumnIndex("DTVENDA")));

			SimpleDateFormat formatador = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss");

			Date dtVenda = null;
			try {
				dtVenda = formatador.parse(dataVenda);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			venda.setDtvenda(dtVenda);

			vendas.add(venda);

		}

		return vendas;
	}

	public Boolean existeVendaAtiva() {
		// TODO Auto-generated method stub

		String select = "SELECT IDVENDACLIENTE FROM VENDACLIENTE WHERE SITUACAOVENDA = 2";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(select, null);
		Long idvendacliente = null;
		if (c.moveToNext()) {

			// idvendacliente = c.getLong(c.getColumnIndex("IDVENDACLIENTE"));
			return true;
		} else {

			return false;
		}
		/*
		 * if (idvendacliente != null) { return true; } else {
		 * 
		 * return false; }
		 */

	}

	public List<String> trazCategoriaCombo() {
		// TODO Auto-generated method stub

		List<String> labels = new ArrayList<String>();

		// Select All Query
		String selectQuery = "SELECT  * FROM CATEGORIAPRODUTO";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				labels.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return labels;

	}

	public List<CategoriaProduto> trazObjetoCategoriaCombo() {
		// TODO Auto-generated method stub

		ArrayList<CategoriaProduto> labels = new ArrayList<CategoriaProduto>();

		// Select All Query
		String selectQuery = "SELECT  * FROM CATEGORIAPRODUTO";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				labels.add(new CategoriaProduto(cursor.getString(0), cursor
						.getString(1)));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return labels;

	}

	
	public List<CategoriaProduto> getListaProdutoCategoria() {
		// TODO Auto-generated method stub

		String[] colunas = { "idCategoria", "nomeCategoria" };

		// String [] args = {categoria};
		Cursor cursor = getWritableDatabase().query("CATEGORIAPRODUTO",
				colunas, null, null, null, null, null);

		ArrayList<CategoriaProduto> cat = new ArrayList<CategoriaProduto>();

		while (cursor.moveToNext()) {
			cat.add(new CategoriaProduto(cursor.getString(0), cursor
					.getString(1)));
		}
		return cat;

	}

	// //////////////////////////////////FIM VENDAS

	public void salvaCategoria(CategoriaProduto categoria) {
		// TODO Auto-generated method stub
		// String idCategoria = buscaProximoIdCategoria();
		UUID uuid = UUID.randomUUID();
		// EXPLICANDO PARA O ANDROID COMO INSERIR NO BANCO DE DADOS
		ContentValues values = new ContentValues();

		values.put("nomeCategoria", categoria.getNomeCategoria());
		values.put("idCategoria", uuid.toString());

		getWritableDatabase().insert("CategoriaProduto", null, values);
	}

	private String buscaProximoIdCategoria() {
		// TODO Auto-generated method stub

		String selectQuery = "SELECT MAX(IDCATEGORIA) AS ID FROM CATEGORIAPRODUTO";

		SQLiteDatabase db = this.getReadableDatabase();
		Integer proximo = 0;
		String ultimocontrole = null;

		Cursor c = db.rawQuery(selectQuery, null);
		// while (c.moveToNext()){ //HAVIA FEITO UTILIZANDO O WHILE, MAS PERCEBI
		// QUE ELE � MAIS LENTO.
		if (c.moveToLast()) {

			ultimocontrole = c.getString(c.getColumnIndex("ID"));
		}
		// }

		if (ultimocontrole != null) {
			proximo = Integer.parseInt(ultimocontrole);
			proximo = proximo + 1;
		} else {

			proximo = 1;

		}

		return proximo.toString();

	}

	public void alteraProduto(ProdutoDTO produto, String idCategoria) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();

		values.put("id", produto.getIdProduto());
		values.put("nomeproduto", produto.getNomeProduto());
		values.put("preco", produto.getPrecoProduto());
		values.put("categoria", idCategoria);

		String[] args = { produto.getIdProduto().toString() };
		getWritableDatabase().update("Produto", values, "id=?", args);

	}

	public void alterarCategoria(CategoriaProduto categiriaProdutoSalvar) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();

		values.put("nomeCategoria", categiriaProdutoSalvar.getNomeCategoria());

		String[] args = { categiriaProdutoSalvar.getIdCategoria().toString() };
		getWritableDatabase().update("CategoriaProduto", values,
				"idCategoria=?", args);

	}

	public Boolean existe_cliente_no_dispositivo() {

		String sql = "SELECT ID FROM ALUNOS";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor consulta = db.rawQuery(sql, null);

		if (consulta.moveToLast()) {

			// idvendacliente = c.getLong(c.getColumnIndex("IDVENDACLIENTE"));
			return true;
		} else {

			return false;
		}
	}

	/*
	 * PRODUTO,id CATEGORIAPRODUTO IDCATEGORIA
	 */
	public Boolean existe_produto_no_dispositivo() {

		String sql = "SELECT ID FROM PRODUTO";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor consulta = db.rawQuery(sql, null);

		if (consulta.moveToLast()) {

			// idvendacliente = c.getLong(c.getColumnIndex("IDVENDACLIENTE"));
			return true;
		} else {

			return false;
		}
	}

	public String verificaParametroSincronizacao(Integer tipo) {
		/*
		 * Tipo 1 = sincronizacaoEntradaAplicativo Tipo 2 =
		 * sincronizacaoFechamentoVenda
		 */

		String sql = "SELECT SINCRONIZACAOREGISTROPRODUTO,SINCRONIZACAOFECHAMENTOVENDA,SINCRONIZACAOENTRADAAPLICATIVO FROM USUARIO";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor consulta = db.rawQuery(sql, null);
		String sincronizacaoEntradaAplicativo = "N";
		String sincronizacaoFechamentoVenda = "N";
		String retorno = "S";
		if (consulta.moveToLast()) {
			sincronizacaoEntradaAplicativo = consulta.getString(consulta
					.getColumnIndex("SINCRONIZACAOENTRADAAPLICATIVO"));
			sincronizacaoFechamentoVenda = consulta.getString(consulta
					.getColumnIndex("SINCRONIZACAOFECHAMENTOVENDA"));
		}

		if (tipo == 1) {
			retorno = sincronizacaoEntradaAplicativo;
		} else if (tipo == 2) {
			retorno = sincronizacaoFechamentoVenda;
		}

		if (retorno == null) {
			retorno = "V";
		}
		return retorno;
	}

	public Boolean existe_categoria_no_dispositivo() {

		String sql = "SELECT IDCATEGORIA FROM CATEGORIAPRODUTO WHERE NOMECATEGORIA != 'Padr�o'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor consulta = db.rawQuery(sql, null);

		if (consulta.moveToLast()) {

			// idvendacliente = c.getLong(c.getColumnIndex("IDVENDACLIENTE"));
			return true;
		} else {

			return false;
		}
	}

	public void atualizarUsuario(Usuario usuarioAtualisar) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();

		values.put("sincronizacaoentradaaplicativo",
				usuarioAtualisar.getSincronizacaoEntradaAplicativo());
		values.put("usuario_master", usuarioAtualisar.getUsuario_master());

		String[] args = { usuarioAtualisar.getIdusuario().toString() };
		getWritableDatabase().update("Usuario", values, "idusuario=?", args);

	}

	public boolean dadosUsuarioBaixadosDoServidorHoje() {
		// TODO Auto-generated method stub

		String sql = "SELECT data_ultima_sincronizacao FROM usuario where idusuario is not null";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor consulta = db.rawQuery(sql, null);
		String data_ultima_sincronizacao = "00/00/0000";
		if (consulta.moveToLast()) {
			data_ultima_sincronizacao = consulta.getString(consulta
					.getColumnIndex("DATA_ULTIMA_SINCRONIZACAO"));

		}
		if (data_ultima_sincronizacao == null) {
			data_ultima_sincronizacao = "00/00/0000";
		}

		Tools tool = new Tools();
		String dataAtual = tool.trazDataAtual();

		if (data_ultima_sincronizacao.equals(dataAtual)) {
			return true;
		} else {
			return false;
		}

	}

	public void gravarSincronizacaoDataAtual() {
		// TODO Auto-generated method stub
		Tools tool = new Tools();
		String data = tool.trazDataAtual();

		// DATA_ULTIMA_SINCRONIZACAO
		// EXPLICANDO PARA O ANDROID COMO INSERIR NO BANCO DE DADOS
		ContentValues values = new ContentValues();
		values.put("data_ultima_sincronizacao", data);
		String[] args = { null };
		getWritableDatabase().update("Usuario", values, "idusuario is not?",
				args);

	}

	public void salvarPagamento(Pagamento pagamentoSalvar, Long idVendaCliente) {
		// TODO Auto-generated method stub
		// SimpleDateFormat dataStringNova = new SimpleDateFormat("dd-MM-yyyy");

		// SimpleDateFormat dateFormat = new SimpleDateFormat(
		// "dd/MM/yyyy HH:mm:ss");

		SimpleDateFormat dataStringNova = new SimpleDateFormat("yyyy-MM-dd");

		Date dataPagamento = pagamentoSalvar.getData_pagamento_informada();
		String data = dataStringNova.format(dataPagamento);
		ContentValues values = new ContentValues();
		values.put("data_pagamento_informada", data);
		values.put("valor_pagamento", pagamentoSalvar.getValor_pagamento());
		values.put("forma_pagamento_informada",
				pagamentoSalvar.getForma_pagamento_informada());
		values.put("idvendacliente", idVendaCliente);
		values.put("situacao_pagamento", 1);
		values.put("situacao_pagamento_informado", "D");
		getWritableDatabase().insert("Pagamento", null, values);

	}

	public List<Pagamento> getPagamento(Long idVendaCliente) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String[] colunas = { "valor_pagamento", "idpagamento",
				"data_pagamento_informada", "forma_pagamento_informada",
				"situacao_pagamento", "situacao_pagamento_informado" };

		// O GET WRITABLE DATABASE RETORNA UM CURSOR
		String[] args = { idVendaCliente.toString(), "1" };
		Cursor cursor = getWritableDatabase().query("PAGAMENTO", colunas,
				"idvendacliente=? and situacao_pagamento=?", args, null, null,
				null);

		ArrayList<Pagamento> pagamentos = new ArrayList<Pagamento>();

		while (cursor.moveToNext()) {
			String data = cursor.getString(2);

			Tools tools = new Tools();

			Pagamento pagamento = new Pagamento();
			pagamento.setIdpagamento(cursor.getLong(1));
			pagamento.setValor_pagamento(cursor.getDouble(0));
			pagamento.setData_pagamento_informada(tools
					.retorna_data_com_traco_ddmmyy(data));
			pagamento.setForma_pagamento_informada(cursor.getInt(3));
			pagamento.setSituacao_pagamento(cursor.getInt(4));
			pagamento.setSituacao_pagamento_informado(cursor.getString(5));
			pagamentos.add(pagamento);
		}

		return pagamentos;

	}

	public Double getTotalPagamento(Long idVendaCliente) {

		String id = idVendaCliente.toString();

		String selectQuery = "SELECT SUM(PAGAMENTO.VALOR_PAGAMENTO) AS TOTAL_PAGAMENTO FROM  PAGAMENTO  WHERE PAGAMENTO.SITUACAO_PAGAMENTO = 1 AND PAGAMENTO.IDVENDACLIENTE = "
				+ id;

		SQLiteDatabase db = this.getReadableDatabase();
		Double preco = 0.0;
		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// NumberFormat fortamatarNumero = NumberFormat.getCurrencyInstance();
		// Double novo = 0.0;
		Cursor c = db.rawQuery(selectQuery, null);
		while (c.moveToNext()) {
			preco = c.getDouble(c.getColumnIndex("TOTAL_PAGAMENTO"));
			// novo = fortamatarNumero.format(preco);
		}
		return preco;

	}

	public void aplicarDesconto(String idVendaCliente, Double descontoFormatado) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();
		VendaCliente venda = new VendaCliente();
		String situacao = "1";

		values.put("descontovenda", descontoFormatado);
		String[] args = { idVendaCliente };
		getWritableDatabase().update("VENDACLIENTE", values,
				"idvendacliente=?", args);

	}

	public Long existeControleSincronizacaoParaVenda(String idVendaCliente) {
		// TODO Auto-generated method stub

		String selectQuery = "SELECT IDVENDACLIENTE FROM CONTROLESINCRONIZACAO WHERE IDVENDACLIENTE = "
				+ idVendaCliente;

		Boolean existe = false;
		SQLiteDatabase db = this.getReadableDatabase();

		Long idcliente = null;
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToLast()) {
			idcliente = c.getLong(c.getColumnIndex("IDVENDACLIENTE"));
			// String idproduto = c.getString(c.getColumnIndex("ID"));

		}
		// }

		return idcliente;
	}

	public void deletarFormaPagamento(Long idpagamento) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();
		String situacao = "0";
		values.put("situacao_pagamento", situacao);
		String[] args = { idpagamento.toString() };
		getWritableDatabase()
				.update("PAGAMENTO", values, "idpagamento=?", args);

	}

	public List<VendasDTO> getRelatorioVendas() {

		// /String selectQuery =
		// "SELECT PRODUTO.ID AS ID, PRODUTO.NOMEPRODUTO  AS NOMEPRODUTO, PRODUTO.PRECO AS PRECO FROM  PRODUTO, VENDAS  WHERE PRODUTO.ID = VENDAS.IDPRODUTO AND VENDAS.IDCLIENTE = "+alunoPesquisar;
		String selectQuery = "SELECT PRODUTO.ID AS ID, PRODUTO.NOMEPRODUTO  AS NOMEPRODUTO, (PRODUTO.PRECO * VENDAS.TOTALPRODUTO) AS PRECO,VENDAS.TOTALPRODUTO AS TOTALPRODUTO, VENDAS.ID AS IDVENDA, VENDACLIENTE.IDVENDACLIENTE AS IDVENDACLIENTE, VENDACLIENTE.DTVENDA FROM  PRODUTO, VENDAS, VENDACLIENTE  WHERE PRODUTO.ID = VENDAS.IDPRODUTO AND VENDACLIENTE.IDVENDACLIENTE = VENDAS.IDVENDACLIENTE AND VENDAS.SITUACAO = 1"
				+ "				GROUP BY VENDACLIENTE.IDVENDACLIENTE";

		// Log.i("SELECT", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();

		// Cursor cursor =
		// db.rawQuery("SELECT PRODUTO.IDPRODUTO AS IDPRODUTO FROM VENDAS INNER JOIN PRODUTO ON VENDAS.IDPRODUTO = PRODUTO.ID WHERE VENDAS.IDCLIENTE = ?",
		// argsAluno);
		Cursor c = db.rawQuery(selectQuery, null);
		List<VendasDTO> vendas = new ArrayList<VendasDTO>();
		VendasDTO venda = new VendasDTO();

		Double totalVenda = 0.0;

		while (c.moveToNext()) {
			venda = new VendasDTO();

			String dataVenda = (c.getString(c.getColumnIndex("DTVENDA")));

			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

			Date dtVenda = null;
			try {
				dtVenda = formatador.parse(dataVenda);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			venda.setId(c.getLong((c.getColumnIndex("ID"))));
			venda.setNomeProduto(c.getString((c.getColumnIndex("NOMEPRODUTO"))));
			// venda.setPreco(c.getString(c.getColumnIndex("PRECO")));
			venda.setPreco(c.getDouble(c.getColumnIndex("PRECO")));
			// / Double totalCompra =
			// Double.valueOf(c.getString(c.getColumnIndex("PRECO")));

			venda.setTotalProduto(c.getDouble(c.getColumnIndex("TOTALPRODUTO")));
			// Float totalGeral = c.getFloat(c.getColumnIndex("PRECO"));
			totalVenda += c.getDouble(c.getColumnIndex("PRECO"));

			// Float totalPre = c.getFloat(c.getColumnIndex("PRECO"));
			venda.setTotal(totalVenda);

			venda.setIdvenda(c.getLong(c.getColumnIndex("IDVENDA")));
			venda.setIdvendacliente(c.getLong(c
					.getColumnIndex("IDVENDACLIENTE")));
			venda.setDtvenda(dtVenda);
			vendas.add(venda);

		}

		return vendas;
	}

	public void informar_pagamento_efetuado(Long idpagamento) {
		// TODO Auto-generated method stub

		ContentValues values = new ContentValues();
		String situacao = "P";
		values.put("situacao_pagamento_informado", situacao);
		String[] args = { idpagamento.toString() };
		getWritableDatabase()
				.update("PAGAMENTO", values, "idpagamento=?", args);

	}

	public List<VendasDTO> getRelatorioPagamentos(int situacao,
			String data_inicial, String data_final, int tipo_retorno,
			String pagamentos_pendentes, String pagamentos_efetuados) {

		Tools tools = new Tools();

		SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-yyyy");

		/*
		 * tipo_retorno = 1 => retorna os dados gerais tipo_retorno = 2 =>
		 * retorna todo o total das vendas pelo par�metro especificado
		 */
		String filtro_tipo_retorno = "";
		String selectQuery;
		String filtro = "";

		if (!data_inicial.equals("") && !data_final.equals("")) {
			// data_inicial = data_inicial.concat(" 00:01:00");
			// data_final = data_final.concat(" 23:59:00");
			data_inicial = tools.retorna_data_com_barra(data_inicial);
			data_inicial = tools.retorna_data_com_traco(data_inicial);

			data_final = tools.retorna_data_com_barra(data_final);
			data_final = tools.retorna_data_com_traco(data_final);

			try {
				data_inicial = tools
						.retorna_data_com_traco_yyyyMMdd(data_inicial);
				data_final = tools.retorna_data_com_traco_yyyyMMdd(data_final);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * filtro =
			 * "  (strftime('%d-%m-%Y',VENDACLIENTE.DT_VENDA_PARA_RELATORIO) >= '"
			 * + data_inicial +
			 * "'  strftime('%d-%m-%Y',VENDACLIENTE.DT_VENDA_PARA_RELATORIO) <= '"
			 * + data_final + "') ";
			 */

			filtro = "  (PAGAMENTO.DATA_PAGAMENTO_INFORMADA >= '"
					+ data_inicial
					+ "'  AND PAGAMENTO.DATA_PAGAMENTO_INFORMADA <= '"
					+ data_final + "') AND ";

		} else {

			if (!data_inicial.equals("")) {
				data_inicial = tools.retorna_data_com_barra(data_inicial);
				data_inicial = tools.retorna_data_com_traco(data_inicial);
				try {
					data_inicial = tools
							.retorna_data_com_traco_yyyyMMdd(data_inicial);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				filtro = " PAGAMENTO.DATA_PAGAMENTO_INFORMADA >=  '"
						+ data_inicial + "' AND";
			}
			if (!data_final.equals("")) {

				data_final = tools.retorna_data_com_barra(data_final);
				data_final = tools.retorna_data_com_traco(data_final);
				try {
					data_final = tools
							.retorna_data_com_traco_yyyyMMdd(data_final);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				filtro = " PAGAMENTO.DATA_PAGAMENTO_INFORMADA <= '"
						+ data_final + "' AND";
			}
		}

		String filtro_pagamentos_efetuados = "";
		String filtro_pagamentos_pendentes = "";

		if (pagamentos_efetuados.equals("S")
				&& pagamentos_pendentes.equals("S")) {
			filtro_pagamentos_efetuados = " AND (PAGAMENTO.SITUACAO_PAGAMENTO_INFORMADO = 'P' or PAGAMENTO.SITUACAO_PAGAMENTO_INFORMADO = 'D') ";

		} else {
			if (pagamentos_efetuados.equals("S")) {
				filtro_pagamentos_efetuados = " AND PAGAMENTO.SITUACAO_PAGAMENTO_INFORMADO = 'P' ";
			}

			if (pagamentos_pendentes.equals("S")) {
				filtro_pagamentos_pendentes = " AND PAGAMENTO.SITUACAO_PAGAMENTO_INFORMADO = 'D' ";
			}
		}
		// AND VENDACLIENTE.SITUACAOVENDA = 1
		selectQuery = "SELECT  VENDACLIENTE.IDVENDACLIENTE, ALUNOS.nome, PAGAMENTO.VALOR_PAGAMENTO, PAGAMENTO.DATA_PAGAMENTO_INFORMADA AS DT_PAGAMENTO_INFORMADA, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE, VENDACLIENTE.SITUACAOVENDA "
				+ " FROM  VENDACLIENTE, ALUNOS, PAGAMENTO"
				+ " WHERE "
				+ filtro
				+ " ALUNOS.ID = VENDACLIENTE.IDCLIENTE AND "
				+ " PAGAMENTO.IDVENDACLIENTE = VENDACLIENTE.IDVENDACLIENTE AND PAGAMENTO.SITUACAO_PAGAMENTO = 1 AND VENDACLIENTE.SITUACAOVENDA = 1 "
				+ filtro_pagamentos_efetuados
				+ filtro_pagamentos_pendentes
				+ "" + " ORDER BY VENDACLIENTE.DTVENDA DESC, ALUNOS.ID";

		// String selectQuery =
		// "SELECT ALUNOS.id, ALUNOS.nome, ALUNOS.site, ALUNOS.telefone, ALUNOS.endereco, ALUNOS.foto, VENDACLIENTE.DTVENDA, VENDACLIENTE.IDVENDACLIENTE  FROM  VENDACLIENTE, ALUNOS  WHERE ALUNOS.ID = VENDACLIENTE.IDCLIENTE";
		// AND VENDACLIENTE.SITUACAOVENDA = 2 - retirei para mostrar todas

		SQLiteDatabase db = this.getReadableDatabase();

		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// NumberFormat fortamatarNumero = NumberFormat.getCurrencyInstance();
		// Double novo = 0.0;
		Cursor cursor = db.rawQuery(selectQuery, null);

		ArrayList<VendasDTO> pessoas = new ArrayList<VendasDTO>();

		while (cursor.moveToNext()) {

			String dataVenda = (cursor.getString(cursor
					.getColumnIndex("DTVENDA")));
			String dt_venda = (cursor.getString(cursor
					.getColumnIndex("DT_PAGAMENTO_INFORMADA")));

			// String dt_venda_para_relatorio =
			// (cursor.getString(cursor.getColumnIndex("DT_VENDA_PARA_RELATORIO")));
			// String dt_teste =
			// (cursor.getString(cursor.getColumnIndex("dt_teste")));

			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

			Date dtVenda = null;
			if (dataVenda != null) {

				try {
					dtVenda = formatador.parse(dataVenda);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			VendasDTO pessoa = new VendasDTO();

			pessoa.setIdvendacliente(cursor.getLong(0));
			pessoa.setNome(cursor.getString(1));
			pessoa.setTotal(cursor.getDouble(2));
			pessoa.setDtvenda(dtVenda);
			pessoa.setDtPagamentoInformado(tools
					.retorna_data_com_traco_ddmmyy(cursor.getString(3)));
			pessoas.add(pessoa);
		}

		return pessoas;
	}

	public Boolean haPedidosVencidos() {
		// TODO Auto-generated method stub

		Tools tools = new Tools();
		String dataAtual = tools.trazDataAtual();
		Boolean retorno = false;
		List<VendasDTO> vendasDTO = getRelatorioPagamentos(0, "", dataAtual, 1,
				"S", "");
		if (vendasDTO.size() > 0) {
			retorno = true;
		}

		return retorno;
	}

	public Boolean verificaPessoaExiste(String cpf) {

		String selectQuery = "SELECT CPF_CNPJ FROM  ALUNOS WHERE CPF_CNPJ = '"
				+ cpf + "'";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery(selectQuery, null);
		Boolean existe = false;

		if (c.moveToLast()) {

			existe = true;
		}
		return existe;

	}

}
