package com.youming.spring.boot.freemarker.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youming.spring.boot.freemarker.utils.JsonTransformUtils;

@Controller
@RequestMapping("/api")
public class ApiController {

	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@ResponseBody
	@RequestMapping("/authorization/GetAcCustomerNo")
	public String GetAcCustomerNo(String conid, String token) {
		logger.info("/api/authorization/GetAcCustomerNo;" + conid + "|" + token);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		map.put("msg", conid + "|" + token);
		return JsonTransformUtils.toJson(map);
	}

	@ResponseBody
	@RequestMapping("/order/initOrder")
	public String initOrder(Integer bpdId, Integer carType, Integer cmId, Integer cusId, Integer shopId, String token,
			String version) {
		logger.info("/api//order/initOrder;" + bpdId + "|" + carType + "|" + cmId + "|" + cusId + "|" + shopId + "|" + token);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		map.put("msg", bpdId + "|" + carType + "|" + cmId + "|" + cusId + "|" + shopId + "|" + token);
		return JsonTransformUtils.toJson(map);
	}
}
