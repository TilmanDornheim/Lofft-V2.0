package lofft.lofftv20.Util;

import java.util.ArrayList;

import lofft.lofftv20.Models.Category;
import lofft.lofftv20.Models.Post;

/**
 * Created by tilma on 2017-07-21.
 */

public class CategoryUtil {

	public static boolean containsCategory(Post post, ArrayList<Category> selected){


		for(Category c: selected){

			if(post.getCategory().getName().equals(c.getName())){

				return true;

			}

		}

		return false;
	}

}
