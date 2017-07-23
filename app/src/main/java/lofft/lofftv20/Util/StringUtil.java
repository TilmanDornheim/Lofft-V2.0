package lofft.lofftv20.Util;

import lofft.lofftv20.Models.User;

/**
 * Created by tilma on 2017-07-20.
 */

public class StringUtil {

	public static String getFullName(User user){

		String fullName;

		fullName = user.getFirstName() + " " + user.getLastName();

		return fullName;

		}

}
