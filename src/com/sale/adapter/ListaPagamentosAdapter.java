package com.sale.adapter;

import java.util.Date;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import pedidos.PedidosFragment;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sale.cadastro.ListaPagamentos;
import com.sale.cadastro.R;
import com.sale.cadastro.PagamentoActivity;
import com.sale.dao.SaleDAO;
import com.sale.modelo.Pagamento;
import com.sale.modelo.VendaCliente;
import com.sale.modelo.VendasDTO;

public class ListaPagamentosAdapter extends BaseAdapter {
	public String calculoValorRestanteFormatado = "0,00";
	private Activity activity;
	Context context;
	private List<Pagamento> pagamento;
	private Double totalPagamento;
	private Double precoTotalVenda;
	private Long idVendaCliente;
	public Double calculoValorRestante;
	public Double calculoValorRestanteSemDesconto = null;

	public ListaPagamentosAdapter(List<Pagamento> pagamento,
			ListaPagamentos activity, ListaPagamentos context,
			Double totalPagamento, Double precoTotalVenda, Long idVendaCliente2) {
		// TODO Auto-generated constructor stub
		this.pagamento = pagamento;
		this.activity = activity;
		this.context = context;
		this.totalPagamento = totalPagamento;
		this.precoTotalVenda = precoTotalVenda;
		this.idVendaCliente = idVendaCliente2;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pagamento.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pagamento.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if (pagamento.size() == 0) {
			return 0;
		} else {
			return pagamento.get(position).getIdpagamento();
		}

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Double calculoValorRestanteSemDescontoMostrar;
		
		LayoutInflater inflater = activity.getLayoutInflater();
		View linha = null;
		calculoValorRestanteSemDesconto = precoTotalVenda - totalPagamento;
		NumberFormat precoFormatado = new DecimalFormat("##,###,###,##0.00",
				new DecimalFormatSymbols(new Locale("pt", "BR")));

		// DecimalFormat precoFormatado = new DecimalFormat("#.###,00");
		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		// linha = inflater.inflate(R.layout.listagem_pagamentos, null);
		// linha = inflater.inflate(R.layout.listagem_pagamentos_cabecalho,
		// null);
		if (position < (getCount() - 1)) {
			Pagamento pagamentoGravar = pagamento.get(position);
			// � atrav�s do inflater que transformamos uma s�rie de tags e
			// transformamos em objetos do android.

			linha = inflater.inflate(R.layout.listagem_pagamentos, null);

			TextView totalVenda = (TextView) linha
					.findViewById(R.id.listagem_pagamento_preco);
			TextView dataPagamento = (TextView) linha
					.findViewById(R.id.listagem_pagamento_data_pagamento);
			TextView forma_pagamento_informada = (TextView) linha
					.findViewById(R.id.listagem_pagamento_forma_pagamento_informada);
			
			TextView situacao_pagamento_informado = (TextView) linha.findViewById(R.id.situacao_pagamento_informado);
			
			situacao_pagamento_informado.setText(pagamentoGravar.getSituacao_pagamento_informado().toString());
			
			Number precoTotalVenda = pagamentoGravar.getValor_pagamento();

			String totalPrecoVenda = "0.0";

			if (!precoTotalVenda.equals(null)) {
				totalPrecoVenda = precoFormatado.format(precoTotalVenda);
			}

			totalVenda.setText("R$ "+totalPrecoVenda);

			Date data = (Date) pagamentoGravar.getData_pagamento_informada();
			String dataNova = null;
			
////
		
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
			/*
			String dateInString = "2013-07-05";
			Date date = null;
			try {

				 date = formatter.parse(dateInString);
				//System.out.println(date);
				System.out.println(formatter.format(date));
			*/
			
			////
			
			//dataNova = tools.retorna_data_com_traco_ddmmyy(formatar.format(data));
			dataNova = formatter.format(data);


			dataPagamento.setText(dataNova.toString());
			int forma_pagamento_informar = pagamentoGravar
					.getForma_pagamento_informada();
			String texto = context.getString(R.string.lista_pagamentos_adapter_texto_nao_informado);
			
			if (forma_pagamento_informar == 0) {
				texto = context.getString(R.string.lista_pagamentos_adapter_cartao_credito);
			} else if (forma_pagamento_informar == 1) {
				texto = context.getString(R.string.lista_pagamentos_adapter_dinheiro);
			}
			if (forma_pagamento_informar == 2) {
				texto = context.getString(R.string.lista_pagamentos_adapter_cheque);
			}
			if (forma_pagamento_informar == 3) {
				
				texto = context.getString(R.string.lista_pagamentos_adapter_boleto);

			}
			forma_pagamento_informada.setText(texto);

			
			
			
		} else {

			String precoTotalFormatado = "0,00";
			String precoTotalVendaFormatado = "0,00";
			if (totalPagamento != 0.0) {
				precoTotalFormatado = precoFormatado.format(totalPagamento);
			}

			if (precoTotalVenda != 0.0) {
				precoTotalVendaFormatado = precoFormatado
						.format(precoTotalVenda);
			}
			
			if (pagamento.size() == 0) {
				linha = inflater.inflate(
						R.layout.listagem_pagamentos_resumo_antes_inclusao, null);
			} else {
			linha = inflater.inflate(
					R.layout.listagem_pagamentos_resumo, null);
			}
			// Valor Pago
			TextView total_relatoriovenda = (TextView) linha
					.findViewById(R.id.valor_pago);
			total_relatoriovenda.setText("R$ " + precoTotalFormatado);

			// precoTotalVenda
			TextView precoTotalVendaMostrar = (TextView) linha
					.findViewById(R.id.valor_compra);
			precoTotalVendaMostrar.setText("R$ " + precoTotalVendaFormatado);

			TextView valor_desconto = (TextView) linha
					.findViewById(R.id.valor_desconto);
			// Pega o desconto cadastrado
			
			SaleDAO dao = new SaleDAO(context);
			List<VendaCliente> dadosVendaCliente = dao.getListaVendaCliente(
					Long.parseLong(idVendaCliente.toString()), null);
			Double totalDesconto = 0.0;
			Integer situacao_venda = 0;
			for (VendaCliente vendaCliente : dadosVendaCliente) {
				if (vendaCliente.getDescontoVenda() != null) {
					totalDesconto = vendaCliente.getDescontoVenda();
				}
				situacao_venda = vendaCliente.getSituacaovenda();
			}

			// Valor restante
			
			String valorDescontoFormatado = "0,00";
			// Double calculoValorRestante = precoTotalVenda - totalPagamento -
			// totalDesconto;
			Double valorDesconto = precoTotalVenda * totalDesconto / 100;
			calculoValorRestante = precoTotalVenda - totalPagamento
					- valorDesconto;
			if (calculoValorRestante != 0) {
				calculoValorRestanteFormatado = precoFormatado
						.format(calculoValorRestante);
			}

			if (valorDesconto != 0) {
				valorDescontoFormatado = precoFormatado.format(valorDesconto);
			}

			TextView valorRestanteMostrar = (TextView) linha
					.findViewById(R.id.valor_restante);
			valorRestanteMostrar.setText("R$ " + calculoValorRestanteFormatado);

			TextView descontoCadastrado = (TextView) linha
					.findViewById(R.id.desconto_informado);
			String valorMostrarDesconto = totalDesconto.toString();
			valorMostrarDesconto = valorMostrarDesconto.concat("%");

			// totalDesconto.toString().concat(valorDesconto.toString())

			descontoCadastrado.setText(valorMostrarDesconto);
			valor_desconto.setText("R$ " + valorDescontoFormatado);

			//Button botaoConfirmarFechamento = (Button) linha
				//	.findViewById(R.id.confirmar_fechamento);
			Button voltar_produtos_venda = (Button) linha.findViewById(R.id.voltar_produtos_venda);
			
			Button incluir_novas_formas_pagamento = (Button) linha
					.findViewById(R.id.incluir_novas_formas_pagamento);

			Button incluir_desconto = (Button) linha
					.findViewById(R.id.incluir_desconto);
			//if (!calculoValorRestanteFormatado.equals("0,00")) {
				//botaoConfirmarFechamento.setEnabled(false);
				//botaoConfirmarFechamento
					//	.setBackgroundResource(R.color.cinza_claro);

			//}
			
			if (situacao_venda == 1) {
				incluir_desconto.setEnabled(false);
				incluir_novas_formas_pagamento.setEnabled(false);
				//botaoConfirmarFechamento.setEnabled(false);
				
			}
			
			
			voltar_produtos_venda.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					

					((Activity)context).finish();
				}
			});
			
			
			
			incluir_desconto.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				if (calculoValorRestanteFormatado.equals("0,00")) {
					Toast.makeText(context, activity.getString(R.string.lista_pagamentos_adapter_nao_possivel_incluir_desconto), Toast.LENGTH_LONG).show();
				} else {
					
					LayoutInflater factory = LayoutInflater.from(context);
					final View alertDialogView = factory.inflate(
							R.layout.desconto, null);

					AlertDialog.Builder alert = new AlertDialog.Builder(context);
					
					
					
					alert.setView(alertDialogView);
					alert.setTitle("Desconto");
					
					//

					alert.setPositiveButton("Gravar",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									// TODO Auto-generated method stub
									TextView descontoInformado = (TextView) alertDialogView
											.findViewById(R.id.valor_desconto);
									
								
									String precoAntes = descontoInformado
											.getText().toString();

									if (precoAntes.isEmpty()) {
										precoAntes = "0.0";
									}
									// Long categoria =
									// Long.parseLong(editCategoria.getText().toString());
									// Long categoria =
									// Long.parseLong(editCategoria.getText().toString());

									SaleDAO dao = new SaleDAO(context);

									Double totalPagamento = dao
											.getTotalPagamento(idVendaCliente);

									Double descontoFormatado = Double
											.parseDouble(precoAntes);

									VendasDTO trazDadosVenda = dao
											.getVendaPorIdVendaCliente(idVendaCliente);
									// Double
									// calculoValorRestanteSemDescontoMostrar =
									// trazDadosVenda.getTotal() -
									// totalPagamento;
									Double calculoValorRestanteSemDescontoMostrar = trazDadosVenda
											.getTotal();
									Double valorDesconto = calculoValorRestanteSemDescontoMostrar
											* descontoFormatado / 100;
									Double valorTotalMenoPago = trazDadosVenda
											.getTotal() - totalPagamento;
									if (valorDesconto > valorTotalMenoPago) {
										Toast.makeText(
												context,
												"Valor do desconto ultrapassa valor restante",
												Toast.LENGTH_LONG).show();

									} else {

										dao.aplicarDesconto(
												idVendaCliente.toString(),
												descontoFormatado);
										dao.close();

										// CONTROLE DE SINCRONIZA��O: S� IR�
										// SINCRONIZAR SE ESSE VALOR ESTIVER
										// PREENCHIDO.

										Intent abrirTelaFechamentoVenda = new Intent(
												context, ListaPagamentos.class);
										abrirTelaFechamentoVenda.putExtra(
												"idVendaCliente",
												idVendaCliente);
										abrirTelaFechamentoVenda.putExtra(
												"precoTotalVenda",
												precoTotalVenda);

										context.startActivity(abrirTelaFechamentoVenda);
									}
								}
							}).setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});

					AlertDialog alertDialog = alert.create();
					
					//Comando para mostrar o teclado automaticamente
					alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
					
					alertDialog.show();

					// Mudar a cor do bot�o
					((Button) alertDialog.findViewById(android.R.id.button2))
							.setBackgroundResource(R.color.botao_picker);
					((Button) alertDialog.findViewById(android.R.id.button1))
							.setBackgroundResource(R.color.botao_picker);
					((Button) alertDialog.findViewById(android.R.id.button1))
							.setTextColor(Color.parseColor("#FFFFFF"));
					((Button) alertDialog.findViewById(android.R.id.button2))
							.setTextColor(Color.parseColor("#FFFFFF"));

					//

				}
			}
			});

			incluir_novas_formas_pagamento
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							
							
							// Notificação
							SaleDAO dao = new SaleDAO(context);
							VendasDTO trazDadosVenda = dao
									.getVendaPorIdVendaCliente(idVendaCliente);
							Double calculoValorRestanteSemDescontoMostrar = trazDadosVenda.getTotal();
							
						if (calculoValorRestanteFormatado.equals("0,00")) {
							AlertDialog.Builder alertDialogTrocar = new AlertDialog.Builder(
									context);
							alertDialogTrocar.setMessage(activity.getString(R.string.lista_pagamentos_adapter_mensagem_pagamento_completo));
							alertDialogTrocar.setPositiveButton("Entendido",
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
									.setTextColor(context.getResources().getColor(R.color.branco));
							((Button) alertDialogNovo.findViewById(android.R.id.button2))
									.setTextColor(context.getResources().getColor(R.color.branco));
					
						
							
							
							//
						} else {	
							
							
							Intent incluir_novas_formas_pagamento = new Intent(
									context, PagamentoActivity.class);
							incluir_novas_formas_pagamento.putExtra(
									"idVendaCliente", idVendaCliente);
							incluir_novas_formas_pagamento.putExtra(
									"calculoValorRestante",
									calculoValorRestante);
							context.startActivity(incluir_novas_formas_pagamento);
						}
						}
					});

			// Bot�o para confirmar o fechamento da venda
		/*	botaoConfirmarFechamento
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

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
											dao.fecharVenda(idVendaCliente
													.toString());
											dao.close();

											// CONTROLE DE SINCRONIZA��O: S� IR�
											// SINCRONIZAR SE ESSE VALOR ESTIVER
											// PREENCHIDO.
											dao.salvaControleSincronizacaoCliente(idVendaCliente
													.toString());
											dao.close();

											Intent irParaTelaInicial = new Intent(
													context,
													PedidosFragment.class);
											context.startActivity(irParaTelaInicial);

										}
									}).setNegativeButton("N�o",
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

							// Mudar a cor do bot�o
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
					});*/

		}
		return linha;

	}

	private void onRestar() {
		// TODO Auto-generated method stub
		Log.i("CICLO DE VIDA", "onRestart");

	}

	private void onResume() {
		// TODO Auto-generated method stub
		Log.i("CICLO DE VIDA", "onResume");

	}

	private void onStart() {
		// TODO Auto-generated method stub
		Log.i("CICLO DE VIDA", "onStart");

	}

}
