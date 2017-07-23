package lofft.lofftv20.Util;

import android.util.Log;

import java.util.ArrayList;

import lofft.lofftv20.Models.Category;
import lofft.lofftv20.Models.Post;

/**
 * Created by tilma on 2017-07-21.
 */

public class LogUtil {

	public static void logArrayList(ArrayList<Category> array){

		for(Category o : array){

			Log.d("Object",o.toString());

		}
	}

	public static void logArrayListPost(ArrayList<Post> array){

		for(Post p : array){

			Log.d("Post",p.getName() + " by " + StringUtil.getFullName(p.getUser()));

		}

	}
}
