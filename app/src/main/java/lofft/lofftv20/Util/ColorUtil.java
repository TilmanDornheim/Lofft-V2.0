package lofft.lofftv20.Util;

/**
 * Created by tilma on 2017-07-21.
 */

public class ColorUtil {

	public static String getLowAlphaColor(String myString){

		String result = "";

		while (myString.charAt(0) == '#') { // Remove all the # chars in front of the real string
			myString = myString.substring(1, myString.length());
		}

		StringBuilder builder = new StringBuilder();

		builder.append("#");
		builder.append("66");
		builder.append(myString);

		result = builder.toString();



		return result;
	}

}
