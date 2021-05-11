youming com.youming.spring.boot.api.version.condition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

/**
 * API版本条件筛选器
 */
public class ApiVesrsionCondition implements RequestCondition<ApiVesrsionCondition> {

	private static final Logger logger = LoggerFactory.getLogger(ApiVesrsionCondition.class);
	private String apiVersion;

	public String getApiVersion() {
		return apiVersion;
	}

	public ApiVesrsionCondition(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	/**
	 * 将不同的筛选条件合并
	 */
	@Override
	public ApiVesrsionCondition combine(ApiVesrsionCondition other) {
		// TODO Auto-generated method stub
		// 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
		logger.info("ApiVesrsionCondition.combine");
		return new ApiVesrsionCondition(other.getApiVersion());
	}

	/**
	 * 根据request查找匹配到的筛选条件
	 * */
	@Override
	public ApiVesrsionCondition getMatchingCondition(HttpServletRequest request) {
		// TODO Auto-generated method stub
		if (compare(getPathInfo(request)) >= 0) {
			return this;
		}
		return null;
	}

	/**
	 * 不同筛选条件比较,用于排序
	 * */
	@Override
	public int compareTo(ApiVesrsionCondition other, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// 优先匹配最新的版本号
		return compare("v" + other.getApiVersion() + "/");
	}

	/**
	 * 获取去掉上下文路径的url路径
	 */
	private String getPathInfo(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		if (contextPath != null && contextPath.length() > 0) {
			uri = uri.substring(contextPath.length());
		}
		return uri;
	}

	/**
	 * Description: 这里是版本的控制规则，不是很优雅，时间有限，写死了只识别vn-n的格式.<br/>
	 * 
	 * @param version @return @throws
	 */
	private int compare(String version) {
		// 路径中版本的前缀， 这里用 /v[0-9]-[0-9]-[0-9]/的形式
		Pattern req_patten = Pattern.compile("/v(\\d+)_(\\d+)_(\\d+)/");
		// api版本的前缀， 这里用 [0-9]-[0-9]-[0-9]的形式
		Pattern api_patten = Pattern.compile("(\\d+)_(\\d+)_(\\d+)");
		Matcher req_m = req_patten.matcher(version);
		Matcher api_m = api_patten.matcher(this.apiVersion); // 这里的apiVersion就是注解里的值

		if (!req_m.find() || !api_m.find()) { // 找不到版本，返回-1
			return -1;
		}

		Integer req_num_1 = Integer.valueOf(req_m.group(1));
		Integer api_num_1 = Integer.valueOf(api_m.group(1));
		Integer req_num_2 = Integer.valueOf(req_m.group(2));
		Integer api_num_2 = Integer.valueOf(api_m.group(2));
		Integer req_num_3 = Integer.valueOf(req_m.group(3));
		Integer api_num_3 = Integer.valueOf(api_m.group(3));
		// 如果请求的版本号大于配置版本号， 则满足
		if (req_num_1 > api_num_1) {
			return 1;
		} else if (req_num_1 == api_num_1) {
			if (req_num_2 > api_num_2) {
				return 1;
			} else if (req_num_2 == api_num_2) {
				if (req_num_3 > api_num_3) {
					return 1;
				} else if (req_num_3 == api_num_3) {
					return 0;
				}
			}
		}
		return -1;
	}
}
