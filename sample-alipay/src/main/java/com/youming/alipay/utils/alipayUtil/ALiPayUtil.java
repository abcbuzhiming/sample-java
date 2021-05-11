package com.youming.alipay.utils.alipayUtil;

import java.text.DecimalFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;


/**
 * Description:支付宝支付工具类 Author:THP Date:2018/05/07 15:17
 */


public class ALiPayUtil {

	private static final Logger logger = LoggerFactory.getLogger(ALiPayUtil.class);

	/**
	 * 支付宝下单
	 *
	 * @param goodsInfo
	 *            商品信息
	 * @param goodsName
	 *            商品名
	 * @param amount
	 *            金额
	 * @param orderNumber
	 *            订单编号
	 */
	public String pay(String goodsInfo, String goodsName, String amount, String orderNumber) {
		//具体详情请查看开发平台https://docs.open.alipay.com/54/106370/
		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(ALiProperty.get("URL"), ALiProperty.get("APP_ID"),
				ALiProperty.get("private_key"), ALiProperty.get("FORMAT"), ALiProperty.get("input_charset"), ALiProperty.get("alipay_public_key"), ALiProperty.get("sign_type"));
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(goodsInfo);
		model.setSubject(goodsName);
		model.setOutTradeNo(orderNumber);
		model.setTimeoutExpress("30m");
		model.setTotalAmount(amount);
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl(ALiProperty.get("notify_url"));
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			System.out.println(response.getBody());// 就是orderString
													// 可以直接给客户端请求，无需再做处理。
			String orderStr = response.getBody();
		
		
			logger.info(orderStr);
			return orderStr;
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return "500";
	}

	/**
	 * 支付宝退款
	 *
	 * @param out_trade_no
	 *            订单支付时传入的商户订单号,不能和 trade_no同时为空。
	 * @param trade_no
	 *            支付宝交易号，和商户订单号不能同时为空
	 * @param refund_amount
	 *            需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
	 */
	public String alipayRefundRequest(String out_trade_no, String trade_no, double refund_amount) {
		// 发送请求
		String strResponse = null;
		AlipayClient alipayClient = new DefaultAlipayClient(ALiProperty.get("URL"), ALiProperty.get("APP_ID"),
				ALiProperty.get("private_key"), ALiProperty.get("FORMAT"), ALiProperty.get("input_charset"), ALiProperty.get("alipay_public_key"),
				ALiProperty.get("sign_type"));
		try {
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			// AlipayRefundInfo alidata = new AlipayRefundInfo();
			// alidata.setOut_trade_no(out_trade_no);
			// alidata.setRefund_amount(refund_amount);
			// alidata.setTrade_no(trade_no);

			DecimalFormat df = new DecimalFormat("0.00");
			String sa = df.format(refund_amount);
			refund_amount = Double.parseDouble(sa);

			request.setBizContent("{" + "\"out_trade_no\":\"" + out_trade_no + "\"," + "\"trade_no\":\"" + trade_no
					+ "\"," + "\"refund_amount\":" + refund_amount + "," + "\"refund_reason\":\"正常退款\","
					+ "\"out_request_no\":\"HZ01RF001\"" + "  }");
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			strResponse = response.getCode();
			if ("10000".equals(response.getCode())) {
				strResponse = "退款成功";
			} else {
				strResponse = response.getSubMsg();
			}
		} catch (Exception e) {

		}
		return strResponse;

	}

	/**
	 * 余额提现
	 *
	 * @param orderNo
	 *            商户订单号
	 * @param aliCode
	 *            支付宝账号
	 * @param name
	 *            收款人姓名
	 * @param amount
	 *            金额
	 * @return 成功失败
	 */
	public AlipayFundTransToaccountTransferResponse withDrawDeposit(String orderNo, String aliCode, String name,
			String amount) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(ALiProperty.get("URL"), ALiProperty.get("APP_ID"),
				ALiProperty.get("private_key"), ALiProperty.get("FORMAT"), ALiProperty.get("input_charset"), ALiProperty.get("alipay_public_key"),
				ALiProperty.get("sign_type"));
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		DecimalFormat df = new DecimalFormat("#.00");
		double am = Double.parseDouble(amount);
		amount = df.format(am);
		request.setBizContent("{" + "\"out_biz_no\":\"" + orderNo + "\"," + "\"payee_type\":\"ALIPAY_LOGONID\","
				+ "\"payee_account\":\"" + aliCode + "\"," + "\"amount\":\"" + amount + "\","
				+ "\"payer_show_name\":\"展示的短内容\"," + "\"payee_real_name\":\"" + name + "\"," + "\"remark\":\"这里是内容\""
				+ "}");
		AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
		// if (response.isSuccess()) {
		// System.out.println("成功");
		// } else {
		// System.out.println("失败");
		// }
		return response;
	}

	/**
	 * 查支付宝订单信息
	 *
	 * @param orderNo
	 *            商家订单号
	 * @param AliOrderNo
	 *            支付宝订单号
	 * @throws AlipayApiException
	 */
	public String queryAliOrderInfo(String orderNo, String AliOrderNo) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(ALiProperty.get("URL"), ALiProperty.get("APP_ID"),
				ALiProperty.get("private_key"), ALiProperty.get("FORMAT"), ALiProperty.get("input_charset"), ALiProperty.get("alipay_public_key"),
				ALiProperty.get("sign_type"));
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizContent(
				"{" + "\"out_trade_no\":\"" + orderNo + "\"," + "\"trade_no\":\"" + AliOrderNo + "\"" + "  }");
		AlipayTradeQueryResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		return response.toString();
	}

	/**
	 * 签名
	 *
	 * @param price
	 *            价格
	 * @param openid
	 *            商户订单号
	 * @param orderName
	 *            订单名
	 * @param orderType
	 *            订单类型 (业务需要)
	 * @return 订单签名后的数据
	 */
	public String getOrderInfo(double price, String openid, String orderName, String orderType) {
		DecimalFormat df = new DecimalFormat("#.00");
		String sa = df.format(price);
		price = Double.parseDouble(sa);
		Map<String, String> params = ALiOrderInfoUtil2.buildOrderParamMap(price, openid, orderName, orderType);
		String orderParam = ALiOrderInfoUtil2.buildOrderParam(params);
		String sign = ALiOrderInfoUtil2.getSign(params);
		return orderParam + "&" + sign;
	}

}