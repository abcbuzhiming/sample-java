package com.youming.demoshiro.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.lang.Strings;

/**
 * Json Web Token的工具类
 */
public class JsonWebTokenUtils {
	// 日志生成器
	private static final Logger logger = LoggerFactory.getLogger(JsonWebTokenUtils.class);

	public static final char SEPARATOR_CHAR = '.'; // JWT的分隔符
	// 密匙池，未来要做成写入配置文件，定期更新的形式
	public static final String[] SECRET_KEY_POOL = { "CGls309Gb1rP7WGQ", "cX0EgfdjkFBK0Kwh", "E8xJtz46xIZDuonV" };

	/**
	 * 产生JWT字符串
	 * 
	 */
	public static String creatCompact(String secretkey, Map<String, Object> claimsMap, Date createTime,
			Date expireTime) {
		if (secretkey == null) {
			logger.error("secretKey is null");
			return null;
		}

		Date nowDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.DATE, 30);// num为增加的天数，可以改变的

		String compact = Jwts.builder().setClaims(claimsMap) // 自定义载荷属性
				// .setId(UUID) //唯一id
				.setSubject("youming") // 委托人(非必须)
				// .setIssuer("mba") //提出者
				.setIssuedAt(createTime) // 产生日期
				.setExpiration(calendar.getTime()) // 设置过期时间
				.signWith(SignatureAlgorithm.HS512, secretkey) // 密钥
				.compact();
		return compact;
	}

	/**
	 * JWT的内容时以BASE64UrlEncode的字符串，解码后就是明码，能得到载荷内容
	 * 读取载荷中的userId，可以根据userId来使用不同的加密key，提高破解难度，不过这里我们没用上这个
	 */
	public static String getPayload(String accessToken) {
		if (accessToken == null) {
			return null;
		}
		String base64UrlEncodedHeader = null; // 头部
		String base64UrlEncodedPayload = null; // 有效载荷
		int delimiterCount = 0;
		StringBuilder sb = new StringBuilder(128);
		for (char c : accessToken.toCharArray()) {
			if (c == SEPARATOR_CHAR) {
				CharSequence tokenSeq = Strings.clean(sb);
				String token = tokenSeq != null ? tokenSeq.toString() : null;
				if (delimiterCount == 0) {
					base64UrlEncodedHeader = token;
				} else if (delimiterCount == 1) {
					base64UrlEncodedPayload = token;
				}
				delimiterCount++;
				sb.setLength(0);
			} else {
				sb.append(c);
			}
		}
		// base64解码，获取payload

		byte[] decode = Base64.getUrlDecoder().decode(base64UrlEncodedPayload);
		return new String(decode);
	}

	/**
	 * @throws Exception
	 *             通过userId反推加密密匙,目前采取的策略是，生成n个不同的随机密匙，编成一个数组
	 *             用userId去除这个n，得到余数，余数就是这个数组的下标，下标对应的数组值就是密匙，n的值越大强度越高，还可以定期更换 @throws
	 */
	public static String getSecretKey(int userId) {
		int index = userId % SECRET_KEY_POOL.length;
		String baseKey = SECRET_KEY_POOL[index];
		String base64SecretKey;
		try {
			base64SecretKey = Base64.getEncoder().encodeToString(baseKey.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return base64SecretKey;
	}

	/**
	 * 根据用户类型，userId生成对应的JWT
	 * @param userType 用户类型：student,学生；teacher,老师；admin,管理员;support,客服
	 * @param userId 用户的id，用户表的主键
	 * @param createTime 创建时间，精确到秒
	 * @param expireTime 过期时间，精确到秒
	 */
	public static String getJsonWebToken(String userType, int userId,String device, Date createTime, Date expireTime)
			 { // 设置用户ID
		Map<String, Object> claimsMap = new HashMap<String, Object>();
		
		claimsMap.put("userType", userType);
		claimsMap.put("userId", userId); // 获取JWT String secretKey
		String secretKey = JsonWebTokenUtils.getSecretKey(userId);
		String compact = JsonWebTokenUtils.creatCompact(secretKey, claimsMap, createTime, expireTime);
		return compact;
	}

	/**
	 * 从cookie中获取access_token,先从uri参数中获取 如没有 再从cookie中获取
	 * 
	 * @param request
	 * @return
	 */
	public static String getAccessToken(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		String accessToken = request.getParameter("ACCESS_TOKEN");
		if (accessToken != null && accessToken.length() > 0) {
			return accessToken;
		}
		Cookie[] cookies = request.getCookies();
		if (null == cookies || cookies.length == 0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("ACCESS_TOKEN")) {
				accessToken = cookie.getValue();
				break;
			}
		}
		return accessToken;
	}

	/**
	 * 解码JWT 从payload中获取用户自定义数据 注意，这个数据是没经过校验的，只是为了获得userId，进一步拿到解密的密匙
	 * 
	 * @param accessToken
	 * @return
	 */
	public static Claims getClaimsUnSign(String accessToken) {
		if (accessToken == null) {
			return null;
		}

		String payload = JsonWebTokenUtils.getPayload(accessToken); // 对JWT进行解码后获得有效载荷
		ObjectMapper objectMapper = new ObjectMapper();
		Claims claimsMap = null;
		try {
			claimsMap = objectMapper.readValue(payload, new TypeReference<HashMap<String, Object>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return claimsMap;
	}

	/**
	 * 解码JWT并校验，然后从payload中获取用户自定义数据，如果校验失败返回空
	 * 
	 * @param accessToken
	 * @return
	 */
	public static Claims getClaimsSign(String accessToken) {
		if (accessToken == null) {
			return null;
		}
		String payload = JsonWebTokenUtils.getPayload(accessToken); // 对JWT进行解码后获得有效载荷
		logger.debug("payload: " + payload);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> claimsUnSign = null;
		try {
			claimsUnSign = objectMapper.readValue(payload, new TypeReference<HashMap<String, Object>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		int userId = 0;
		Object value = claimsUnSign.get("userId");
		if (value != null) {
			if (value instanceof Integer) {
				userId = (int) value;
			} else if (value instanceof String) {
				userId = Integer.valueOf((String) value);
			}
		}

		// 获取用户的密钥
		String secretKey = JsonWebTokenUtils.getSecretKey(userId);
		if (secretKey == null || secretKey.length() < 1) {
			logger.error(userId + "用户无法得到加密密匙");
			return null;
		}

		Claims claimsSign = null;
		try {
			claimsSign = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
		} catch (SignatureException e) { // SignatureException 签名错误,JWT可能被篡改
			// TODO: handle exception
			return null;
		} catch (ExpiredJwtException e) { // ExpiredJwtException JWT已过期
			// TODO: handle exception
			return null;
		}
		return claimsSign;

	}
	
	/**
	 * 从JWT推出redis中jwt的key值
	 */
	public static String getRedisJWTKeyFromClientJWT(String accessToken) {
		String payload = JsonWebTokenUtils.getPayload(accessToken);
		ObjectMapper objectMapper_JWT = new ObjectMapper();
		Map map = null;
		try {
			map = objectMapper_JWT.readValue(payload, LinkedHashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String userType = (String) map.get("userType");
		Integer userId = (Integer) map.get("userId");
		String device = (String) map.get("device");
		Integer iat = (Integer) map.get("iat");
		String redisJWTKey = "token_"+userType+":"+userId+":"+device+":"+iat+"";
		return redisJWTKey;
		
		/*Claims claims = JsonWebTokenUtils.getClaimsSign(accessToken);
		if (claims == null) {
			throw new IncorrectCredentialsException("token校验未通过或已过期");
		}
		String device = null;
		String userType = null;
		long userId = 0;
		try {
			device  = claims.get("device").toString();
			userType = claims.get("userType").toString();
			userId = Long.parseLong(claims.get("userId").toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (device == null || userType == null || userId == 0) {
			throw new IncorrectCredentialsException("token校验未通过,关键数据为空");
		}
		if(!device.equals("web") && !device.equals("android") && !device.equals("ios")) {
			throw new IncorrectCredentialsException("token校验未通过，设备类型数据错误:" + device);
		}
		if (!userType.equals("visitor") && !userType.equals("student") && !userType.equals("teacher") && !userType.equals("support")
				&& !userType.equals("admin")) { // 用户类型不对
			throw new Incorrec tCredentialsException("token校验未通过,用户类型数据错误：" + userType);
		}
		String keyPattern = "token_" + userType + ":" + userId + ":" + device + ":" + (claims.getIssuedAt().getTime() / 1000);
		return keyPattern;*/
	}
	
}
