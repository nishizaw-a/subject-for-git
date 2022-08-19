package main.util.keyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import main.util.exception.OverRangeException;
import main.util.exception.SystemException;
import main.util.properties.MessageProperties;

public class Keyboard {
	private static BufferedReader br;

	private static void createBufferedReaderInstance() {
		if (Keyboard.br == null) {
			Keyboard.br = new BufferedReader(new InputStreamReader(System.in));
		}
	}

	public static String getString() throws SystemException{
		Keyboard.createBufferedReaderInstance();
		try {
			return Keyboard.br.readLine();
		}catch(IOException e) {
			throw new SystemException(MessageProperties.getMessage("error.keybord"));
		}
	}

	public static int getInt() throws SystemException{
		int value;
		while(true) {
			try {
				value = Integer.parseInt(Keyboard.getString());
				break;
			}catch(NumberFormatException e) {
				System.out.println(MessageProperties.getMessage("error.non.number"));
			}
		}
		return value;
	}

	public static int getInt(int from, int to) throws SystemException{
		int value;
		while(true) {
			try {
				value = Keyboard.getInt();
				Keyboard.checkRange(value, from, to);
				break;
			}catch(OverRangeException e) {
				System.out.println(MessageProperties.getMessage("error.outside.range", from, to));
			}
		}
		return value;
	}

	private static void checkRange(int value, int from, int to) throws OverRangeException{
		if((value < from) || (value > to)) {
			throw new OverRangeException();
		}
	}


}
