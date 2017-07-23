package lofft.lofftv20.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import lofft.lofftv20.R;

/**
 * Created by tilma on 2017-07-20.
 */

public class ProfileTagAdapter extends RecyclerView.Adapter<ProfileTagAdapter.ViewHolder> {

	private ArrayList<String> tags = new ArrayList<>();
	private Context context;
	private static String color;


	public ProfileTagAdapter(Context context, ArrayList<String> tags, String color){

		this.context = context;
		this.tags = tags;
		this.color = color;


	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_tag,null);

		return  new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		holder.name.setText(tags.get(position));

		holder.name.setTextColor(Color.parseColor("#FFFFFF"));

		Drawable tagbg = context.getResources().getDrawable(R.drawable.tag_bg);

		tagbg.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);


		holder.name.setBackground(tagbg);

	}

	@Override
	public int getItemCount() {
		return tags.size();
	}

	public static  class ViewHolder extends RecyclerView.ViewHolder  {

		private ProfileTagAdapter adapter;
		private TextView name;

		public ViewHolder(View itemView) {
			super(itemView);

			name = (TextView)itemView.findViewById(R.id.profile_tagName);





		}


	}
}
