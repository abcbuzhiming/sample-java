package com.youming.alipay.utils.alipayUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ALiProperty {
	private static final Logger logger = LoggerFactory.getLogger(ALiProperty.class);

	private static Properties props;
	static {
		loadProps();
	}

	synchronized static private void loadProps() {
		props = new Properties();
		InputStream in = null;

		try {
			in = ALiProperty.class.getClassLoader().getResourceAsStream("aliinfo.properties");
			props.load(in);
		} catch (FileNotFoundException e) {
			logger.error("payment.properties文件未找到");
		} catch (IOException e) {
			logger.error("出现IOException");
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("payment.properties文件流关闭出现异常");
			}
		}
	}

	// 读取key对应的value
	public static String get(String key) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key);
	}

}
