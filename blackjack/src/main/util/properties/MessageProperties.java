package main.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

import main.util.exception.SystemException;

public class MessageProperties {
	private static Properties properties;
	private final static String FILE_PATH = "resources/properties/messages.properties";

	public static String getMessage(String resourceId) throws SystemException {

		//パラメーターチェック resourceId
		if (resourceId == null || resourceId.isEmpty()) {
			throw new SystemException(properties.getProperty("error.argument"));
		}

		//プロパティファイル初期化処理
		MessageProperties.init();

		//メッセージ取得処理
		String msg = properties.getProperty(resourceId);

		//メッセージチェック処理
		if (msg == null) {
			throw new SystemException(properties.getProperty("error.nodata", resourceId));
		}

		return msg;
	}
	public static String getMessage(String resourceId, String... arguments) throws SystemException {

		//パラメーターチェック resourceId,arguments
		if (resourceId == null || resourceId.isEmpty() || arguments == null || arguments.length == 0) {
			throw new SystemException(properties.getProperty("error.argument"));
		}

		//プロパティファイル初期化処理
		MessageProperties.init();

		//メッセージ取得処理
		String msg = properties.getProperty(resourceId);

		//メッセージチェック処理
		if (msg == null) {
			throw new SystemException(properties.getProperty("error.properties.nodata", resourceId));
		}
		return MessageFormat.format(msg, (Object[]) arguments);
	}

	public static String getMessage(String resourceId, int... arguments) throws SystemException {

		//パラメーターチェック resourceId,arguments
		if (resourceId == null || resourceId.isEmpty() || arguments == null || arguments.length == 0) {
			throw new SystemException(properties.getProperty("error.argument"));
		}

		//プロパティファイル初期化処理
		MessageProperties.init();

		//メッセージ取得処理
		String msg = properties.getProperty(resourceId);

		//メッセージチェック処理
		if (msg == null) {
			throw new SystemException(properties.getProperty("error.properties.nodata", resourceId));
		}

		Object[] array = new Object[arguments.length];
		for(int i = 0; i < array.length; i++) {
			array[i] = String.valueOf(arguments[i]);
		}
		return MessageFormat.format(msg, array);
	}

	private static void init() throws SystemException {
		try {
			if (MessageProperties.properties == null) {
				MessageProperties.properties = new Properties();
				InputStream is = MessageProperties.class.getClassLoader().getResourceAsStream(MessageProperties.FILE_PATH);
				MessageProperties.properties.load(new InputStreamReader(is,"UTF-8"));
			}

		} catch (IOException e) {
			//ファイルload処理を失敗した場合
			throw new SystemException(properties.getProperty("error.properties.load"));

		}
	}
}
