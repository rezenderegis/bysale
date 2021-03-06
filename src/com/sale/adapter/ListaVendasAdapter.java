package com.sale.adapter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import pedidos.PedidosFragment;

import com.sale.cadastro.R;
import com.sale.cadastro.ListaPagamentos;
import com.sale.cadastro.ListaPessoas;
import com.sale.cadastro.ListaVendas;
import com.sale.cadastro.ProdutoActivity;
import com.sale.cadastro.util.MenuInicial;
import com.sale.dao.SaleDAO;
import com.sale.login.Usuario;
import com.sale.login.VerificaLogin;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Vendas;
import com.sale.modelo.VendasDTO;
import com.sale.negocio.NegocioUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ListaVendasAdapter extends BaseAdapter {
	private List<VendasDTO> produto;
	private Activity activity;
	Double precoTotalVenda;
	Integer totalproduto;
	Context context;
	Long clienteVenda;
	Long idVendaClienteDaListaVendas = null;
	VendasDTO produtos;
	String clienteSelecionadoFromListaPessoasAbrirVenda;
	String empresaPossuiFluxoAprovacaoCadastroUsuario = null;
	
	public ListaVendasAdapter(List<VendasDTO> vendasDTO, Activity activity,
			Double precoTotalVenda, Context context, Long idVendaCliente,
			Double precoTotalVenda2, Long alunoParaSerAlterado, String clienteSelecionadoFromListaPessoasAbrirVenda) {

		this.produto = vendasDTO;
		this.precoTotalVenda = precoTotalVenda;
		this.activity = activity;
		this.context = context;
		this.idVendaClienteDaListaVendas = idVendaCliente;
		this.clienteVenda = alunoParaSerAlterado;
		this.clienteSelecionadoFromListaPessoasAbrirVenda = clienteSelecionadoFromListaPessoasAbrirVenda;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return produto.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return produto.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return produto.get(position).getIdvendacliente();
	}

	
	
	
	@Override
	public View getView(int position, View converView, ViewGroup partent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = activity.getLayoutInflater();
		View linha = null;

		SaleDAO dao = new SaleDAO(context);
		VendasDTO dadosVenda = new VendasDTO();
		dadosVenda = dao.getVendaPorIdVendaCliente(idVendaClienteDaListaVendas);
		DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");
		
	
		if (position == 0) {
			converView = inflater.inflate(R.layout.listagem_vendas_cabecalho, null);
		
		}
		
		
		if (position < (getCount() - 1)) {
			
			
			
			
			
			produtos = produto.get(position);
			
			
			
			converView = inflater.inflate(R.layout.listagem_vendas, null);
			TextView nomeproduto_relatoriovenda = (TextView) converView
					.findViewById(R.id.nomeproduto_relatoriovenda);
			nomeproduto_relatoriovenda.setText(produtos.getNomeProduto());

			TextView totalProduto = (TextView) converView
					.findViewById(R.id.quantidadeproduto);
			
			DecimalFormat decimal = new DecimalFormat();
			decimal.setDecimalSeparatorAlwaysShown(false);
			String totalProdutoVenda;
			if (produtos.getTotalProduto() % 1 == 0) {
				 totalProdutoVenda = decimal.format(produtos.getTotalProduto()).toString();

			} else {
			 totalProdutoVenda = produtos.getTotalProduto().toString();
			}
			totalProduto.setText(totalProdutoVenda);

			String precoVendaFormatado = "0,00";

			if (produtos.getPreco() != 0.0) {
				precoVendaFormatado = precoFormatado
						.format(produtos.getPreco());
			}

			TextView preco_relatoriovenda = (TextView) converView
					.findViewById(R.id.preco_relatoriovenda);
			// String precoVenda = produtos.getPreco().toString();
			preco_relatoriovenda.setText("R$ "+precoVendaFormatado);

		} else {
			//

			String precoTotalFormatado = "0,00";
			DecimalFormat mascaraFormatacaoPrecoTotal = new DecimalFormat("####,###,###.00");
			if (produtos != null) {
				if (produtos.getTotal() != null) {
					precoTotalFormatado = mascaraFormatacaoPrecoTotal
							.format(produtos.getTotal());
				}
			}
			converView = inflater.inflate(R.layout.listagem_vendas_total, null);
			TextView total_relatoriovenda = (TextView) converView
					.findViewById(R.id.total_relatoriovenda);
			total_relatoriovenda.setText("R$ " + precoTotalFormatado);
			
			Button botaoEmitirPedido = (Button) converView.findViewById(R.id.emitir_pedido);
			
			Button botaoFecharVenda = (Button) converView
					.findViewById(R.id.fechar_venda);

			Button botao_incluir_produto_venda = (Button) converView
					.findViewById(R.id.botao_incluir_produto_venda);

			botao_incluir_produto_venda
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							Intent irParaProdutos = new Intent(context,
									ProdutoActivity.class);

							// Continuar aqui
							irParaProdutos.putExtra("idVendaCliente",
									idVendaClienteDaListaVendas);
							irParaProdutos.putExtra("clienteSelecionadoFromListaPessoasAbrirVenda", clienteSelecionadoFromListaPessoasAbrirVenda);
							context.startActivity(irParaProdutos);

						}
					});

			if (produto.size() < 1) {
				botaoFecharVenda.setEnabled(false);
				botaoFecharVenda.setBackgroundResource(R.color.cinza_claro);

			}

			botaoFecharVenda.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Log.e("error", "asdf");

					Intent abrirTelaFechamentoVenda = new Intent(context,
							ListaPagamentos.class);
					abrirTelaFechamentoVenda.putExtra("idVendaCliente",
							idVendaClienteDaListaVendas);
					abrirTelaFechamentoVenda.putExtra("precoTotalVenda",
							precoTotalVenda);
					// abrirTelaFechamentoVenda.putExtra("clienteVenda",
					// clienteVenda);

					context.startActivity(abrirTelaFechamentoVenda);

				}
			});
			
			Usuario dadosUsuarioDispositivo = dao.getDadosUsuarioDispositivo();
			 empresaPossuiFluxoAprovacaoCadastroUsuario = dadosUsuarioDispositivo.getFluxo_aprovacao_cadastro();
			
			
			
			botaoEmitirPedido
			.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					SaleDAO dao = new SaleDAO(context);
					NegocioUtil negocioUtil = new NegocioUtil();
					Double percentualDesconto = negocioUtil.valorDescontoVenda(context, idVendaClienteDaListaVendas);
					Double totalPagamento = dao.getTotalPagamento(idVendaClienteDaListaVendas);

					Double valoRestanteParaPagamento = negocioUtil.calcularValorRestanteNaFormaPagamento(precoTotalVenda, percentualDesconto, totalPagamento);
					NumberFormat precoFormatado = new DecimalFormat("##,###,###,##0.00",
							new DecimalFormatSymbols(new Locale("pt", "BR")));
					String calculoValorRestanteFormatado = "0,00";
					if (valoRestanteParaPagamento != 0) {
						calculoValorRestanteFormatado = precoFormatado
								.format(valoRestanteParaPagamento);
					}

					
					String mensagem = "";
					SaleDAO daoConsulta = new SaleDAO(context);
					Pessoa pessoa = daoConsulta.getDadosPessoa(produtos.getIdCliente());
					String situacaoCadastroCliente = pessoa.getSituacao_cadastro();
					daoConsulta.close();
					if (empresaPossuiFluxoAprovacaoCadastroUsuario.equals("S") && (situacaoCadastroCliente.equals("1") || situacaoCadastroCliente.equals("3")
							|| situacaoCadastroCliente.equals("4"))) {
						
						

						if (situacaoCadastroCliente.equals("1") ) {
							mensagem = context.getString(R.string.mensagem_aguardando_analise);

						} else if (situacaoCadastroCliente.equals("3")) {
							mensagem = context.getString(R.string.mensagem_recusado);

						} else if (situacaoCadastroCliente.equals("4")) {
							mensagem = context.getString(R.string.mensagem_impossivel_venda);

						}
					
					}else if (!calculoValorRestanteFormatado.equals("0,00")) {
						mensagem = context.getString(R.string.lista_vendas_adapter_mensagem_informar_forma_pagamento);
					} 
					
							
						if (!mensagem.equals("")) {
							
							AlertDialog.Builder alertDialogTrocar = new AlertDialog.Builder(
									context);
							alertDialogTrocar.setMessage(mensagem);
							alertDialogTrocar.setPositiveButton("Entendido",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int id) {
											// TODO Auto-generated method stub

										}
									});

							AlertDialog alertDialogNovo = alertDialogTrocar.create();
							alertDialogNovo.show();

							// Mudar a cor do bot???o
							((Button) alertDialogNovo.findViewById(android.R.id.button2))
									.setBackgroundResource(R.color.botao_picker);
							((Button) alertDialogNovo.findViewById(android.R.id.button1))
									.setBackgroundResource(R.color.botao_picker);
							((Button) alertDialogNovo.findViewById(android.R.id.button1))
									.setTextColor(context.getResources().getColor(R.color.branco));
							((Button) alertDialogNovo.findViewById(android.R.id.button2))
									.setTextColor(context.getResources().getColor(R.color.branco));
					
						
						
					} else {
					
					
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					alertDialogBuilder
							.setMessage("Deseja emitir o pedido?");
					alertDialogBuilder.setPositiveButton("Sim",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(
										DialogInterface dialog, int id) {
									// TODO Auto-generated method stub

									SaleDAO dao = new SaleDAO(context);
									dao.fecharVenda(idVendaClienteDaListaVendas.toString());
									dao.close();

									// CONTROLE DE SINCRONIZA??????O: S??? IR???
									// SINCRONIZAR SE ESSE VALOR ESTIVER
									// PREENCHIDO.
									dao.salvaControleSincronizacaoCliente(idVendaClienteDaListaVendas.toString());
									dao.close();

									Intent irParaTelaInicial = new Intent(
											context,
											PedidosFragment.class);
									context.startActivity(irParaTelaInicial);

								}
							}).setNegativeButton("N??o",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(
										DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});

					AlertDialog alertDialog = alertDialogBuilder
							.create();
					alertDialog.show();

					// Mudar a cor do bot???o
					((Button) alertDialog
							.findViewById(android.R.id.button2))
							.setBackgroundResource(R.color.botao_picker);
					((Button) alertDialog
							.findViewById(android.R.id.button1))
							.setBackgroundResource(R.color.botao_picker);
					((Button) alertDialog
							.findViewById(android.R.id.button1))
							.setTextColor(Color.parseColor("#FFFFFF"));
					((Button) alertDialog
							.findViewById(android.R.id.button2))
							.setTextColor(Color.parseColor("#FFFFFF"));

				}
				
			}
			});
			
			
			
			

		}
		// linha = inflater.inflate(R.layout.botao_fechar_venda, null);
		return converView;

	}

}
