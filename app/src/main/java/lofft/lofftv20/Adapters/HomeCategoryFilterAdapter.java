package lofft.lofftv20.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesurix.gesturerecycler.GestureAdapter;
import com.thesurix.gesturerecycler.GestureViewHolder;

import java.util.ArrayList;

import lofft.lofftv20.Interfaces.categoryFilterChangedListener;
import lofft.lofftv20.Models.Category;
import lofft.lofftv20.R;

/**
 * Created by tilma on 2017-07-21.
 */

public class HomeCategoryFilterAdapter extends GestureAdapter<ArrayList<Category>,HomeCategoryFilterAdapter.ViewHolder> {

	private Context context;
	private ArrayList<Category> categories;
	private categoryFilterChangedListener listener;
	private boolean clickable = true;

	public HomeCategoryFilterAdapter(Context context, ArrayList<Category> categories) {

		this.context = context;
		this.categories = categories;

		for(Category c : this.categories){


			c.setSelected(true);

		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_item,parent,false);


		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {


		Drawable drawable = context.getResources().getDrawable(categories.get(position).getDrawable());

		holder.icon.setImageDrawable(drawable);

		setCardLook(position,holder);



		holder.card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				if(clickable) {


					if (categories.get(position).getSelected()) {

						categories.get(position).setSelected(false);

					} else {

						categories.get(position).setSelected(true);

					}

					setCardLook(position, holder);

					ArrayList<Category> selected = getSelected();
					listener.filterUpdated(selected);

				}

				else {

					clickable = true;

				}

			}
		});




	}

	@Override
	public int getItemCount() {
		return categories.size();
	}


	public static class ViewHolder extends GestureViewHolder{


		private CardView card;
		private TextView title;
		private ImageView icon;


		public ViewHolder(View itemView) {
			super(itemView);

			card = (CardView)itemView.findViewById(R.id.home_categories_card);
			icon = (ImageView)itemView.findViewById(R.id.home_categories_icon);


		}

		@Override
		public boolean canDrag() {
			return true;
		}

		@Override
		public boolean canSwipe() {
			return false;
		}
	}

	public void setCardLook(int position, ViewHolder holder){



			if (categories.get(position).getSelected()) {

				holder.card.setCardBackgroundColor(Color.parseColor(categories.get(position).getColor()));
				holder.card.setAlpha(1f);
			} else {

				holder.card.setCardBackgroundColor(Color.parseColor(categories.get(position).getColor()));

				holder.card.setAlpha(0.3f);
			}








	}

	public void setFilterUpdatedListener(categoryFilterChangedListener listener){

		this.listener = listener;

	}

	public ArrayList<Category> getSelected(){

		ArrayList<Category> selected = new ArrayList<>();

		for(Category c : categories){

			if(c.getSelected()){

				selected.add(c);

			}

		}


		return selected;

	}

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public void selectAll(){

		for(Category c : categories){

			c.setSelected(true);

		}

		notifyDataSetChanged();

		listener.filterUpdated(categories);


	}


	public void singleItem(Category category, ViewHolder holder, int position){

		Log.d("LongPress","detected in Adapter");

		ArrayList<Category> singledList = new ArrayList<>();

		for(Category c : categories){

			if(c.getName().equals(category.getName())){

				singledList.add(c);
				Log.d("Selected Set", "True for " + c.getName() );

			}

			else{


				Log.d("Selected Set", "False for " + c.getName() );

			}

		}


		setSelection(singledList);


		notifyDataSetChanged();

		Log.d("LongPress","effected card look");

		listener.filterUpdated(singledList);

		Log.d("LongPress","listener singals filter update");


	}

	private void setSelection(ArrayList<Category> singledList) {

		for(Category c: categories){

			if(singledList.contains(c)){

				c.setSelected(true);

			}

			else{

				c.setSelected(false);

			}

		}

	}
}
