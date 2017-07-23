package lofft.lofftv20.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;

import de.hdodenhof.circleimageview.CircleImageView;
import lofft.lofftv20.Interfaces.mapExpandedInPostListener;
import lofft.lofftv20.Interfaces.postItemExpandedListener;
import lofft.lofftv20.Interfaces.profileSelectedInPostListener;
import lofft.lofftv20.Models.Location;
import lofft.lofftv20.Models.Post;
import lofft.lofftv20.R;

/**
 * Created by tilma on 2017-07-20.
 */

public class HomeCardAdapter extends RecyclerView.Adapter<HomeCardAdapter.ViewHolder> {

	private ArrayList<Post> posts;
	private Context context;

	private final HashSet<MapView> mMaps = new HashSet<MapView>();

	private profileSelectedInPostListener listener;
	private postItemExpandedListener expandListener;
	private mapExpandedInPostListener mapListener;

	private RecyclerView recyclerView;

	private FragmentManager manager;

	private GoogleMap map;

	public HomeCardAdapter(Context context, ArrayList<Post> posts, FragmentManager manager,RecyclerView recyclerView) {

		this.context = context;
		this.posts = posts;
		this.manager = manager;
		this.recyclerView = recyclerView;

	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_post_card, parent, false);

		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position2) {


		int position = holder.getAdapterPosition();

		String color = posts.get(position).getCategory().getColor();


		holder.name.setText(posts.get(position).getUser().getFirstName());
		holder.description.setText(posts.get(position).getDescription());
		holder.title.setText(posts.get(position).getName());
		holder.ratingBar.setRating(posts.get(position).getUser().getRating());
		holder.categoryHeader.setBackgroundColor(Color.parseColor(posts.get(position).getCategory().getColor()));
		holder.categoryContent.setBackgroundColor(Color.parseColor(posts.get(position).getCategory().getColor()));
		holder.picture.setBorderColor(Color.parseColor(posts.get(position).getCategory().getColor()));
		holder.bid.setCardBackgroundColor(Color.parseColor(posts.get(position).getCategory().getColor()));

        //String builders

		StringBuilder builder = new StringBuilder();
		StringBuilder builder2 = new StringBuilder();

		//Tint Icons

		Drawable drawableMap = context.getResources().getDrawable(R.drawable.map_bg);

		Drawable copyMap = drawableMap.mutate();

		copyMap.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);

		holder.mapBG.setBackground(copyMap);

		// ----

		Drawable drawablePrice = context.getResources().getDrawable(R.drawable.home_card_money_big);

		Drawable copyPrice = drawablePrice.mutate();

		copyPrice.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);

		holder.priceIcon.setImageDrawable(copyPrice);

		// ----

		Drawable drawableDuration = context.getResources().getDrawable(R.drawable.home_card_clock_big);

		Drawable copyDuration = drawableDuration.mutate();

		copyDuration.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);

		holder.durationIcon.setImageDrawable(copyDuration);


		//Make Strings

		builder.append(posts.get(position).getPrice());
		builder.append(" / ");
		builder.append(posts.get(position).getPriceDateType());

		builder2.append(Integer.toString(posts.get(position).getDuration()));
		builder2.append(" ");
		builder2.append(posts.get(position).getPriceDateType());

		if(posts.get(position).getDuration() > 1){

			builder2.append("s");

		}

		String price = builder.toString();

		String duration = builder2.toString();



		//Set Text

		holder.price.setText(price);
		holder.duration.setText(duration);

		//Set TextColor

		holder.price.setTextColor(Color.parseColor(color));
		holder.duration.setTextColor(Color.parseColor(color));




		mMaps.add(holder.mapView);

		Picasso.with(context).load(posts.get(position).getUser().getPicture())
				.fit()
				.centerInside()
				.into(holder.picture);

		holder.card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				expandListener.itemExpanded(holder.getAdapterPosition());

				holder.expand.toggle();


			}
		});

		holder.picture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				listener.profileClicked(posts.get(holder.getAdapterPosition()).getUser());

			}
		});

		//Focus Map

		holder.zoomMap(posts.get(position).getLocation());





	}

	@Override
	public int getItemCount() {

		return posts.size();
	}

	public void setListener(profileSelectedInPostListener listener) {

		this.listener = listener;

	}


	class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

		private CardView card;
		private TextView name,title,description;
		private CircleImageView picture;
		private RatingBar ratingBar;
		private ExpandableRelativeLayout expand;
		private LinearLayout categoryHeader,categoryContent,mapBG;
		private GoogleMap map;
		private MapView mapView;
		private TextView price,duration;
		private CardView bid;
		private ImageView priceIcon,durationIcon;



		public ViewHolder(View itemView) {

			super(itemView);


			name = (TextView)itemView.findViewById(R.id.home_card_name);
			title = (TextView)itemView.findViewById(R.id.home_card_title);
			description = (TextView)itemView.findViewById(R.id.home_card_description);
			picture = (CircleImageView)itemView.findViewById(R.id.home_card_picture);
			ratingBar = (RatingBar)itemView.findViewById(R.id.home_card_rating);
			card = (CardView)itemView.findViewById(R.id.home_card);
			expand = (ExpandableRelativeLayout)itemView.findViewById(R.id.home_card_expandable);
			categoryHeader = (LinearLayout)itemView.findViewById(R.id.home_card_category_header);
			categoryContent = (LinearLayout)itemView.findViewById(R.id.home_card_category_content);
			mapView = (MapView)itemView.findViewById(R.id.home_card_content_map);
			price = (TextView)itemView.findViewById(R.id.home_card_content_price);
			duration = (TextView)itemView.findViewById(R.id.home_card_content_duration);
			bid = (CardView)itemView.findViewById(R.id.home_card_content_bid);
			mapBG = (LinearLayout)itemView.findViewById(R.id.home_card_content_mapBG);
			priceIcon = (ImageView)itemView.findViewById(R.id.home_card_content_price_icon);
			durationIcon = (ImageView)itemView.findViewById(R.id.home_card_content_duration_icon);

			initializeMapView();



		}

		public void initializeMapView() {

			if (mapView != null) {
				// Initialise the MapView
				mapView.onCreate(null);
				// Set the map ready callback to receive the GoogleMap object
				mapView.getMapAsync(this);
			}

			else{

				Toast.makeText(context, "MapView Null", Toast.LENGTH_SHORT).show();

			}
		}


		@Override
		public void onMapReady(GoogleMap googleMap) {
			map = googleMap;

			map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
				@Override
				public void onMapClick(LatLng latLng) {

					mapListener.mapOpened(posts.get(getAdapterPosition()));

				}
			});

			MapsInitializer.initialize(context);



			Location location = posts.get(getAdapterPosition()).getLocation();

			zoomMap(location);


		}

		public void zoomMap(Location location){





			CameraUpdate center = CameraUpdateFactory.newLatLng(location.getLatLng());

			CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);

			if(map!=null){

			map.clear();

			Circle circle = map.addCircle(new CircleOptions()
						.center(location.getLatLng())
						.radius(200)
						.strokeColor(Color.BLACK)
						.strokeWidth(1)
						.fillColor(Color.parseColor("#33CDDC39")));


			map.moveCamera(center);
			map.animateCamera(zoom);

			}


		}


	}

	public void clear(){

		posts.clear();
		notifyDataSetChanged();

	}

	public void addList(ArrayList<Post>posts){

		this.posts.addAll(posts);
		notifyDataSetChanged();

	}

	public void setExpandListener(postItemExpandedListener listener){

		expandListener = listener;

	}

	public void setMapListener(mapExpandedInPostListener listener){

		mapListener = listener;

	}


	public void scrollToPost(Post post){

		int count = 0;
		int position = 9999;

		for(Post p : posts){



			if(p.equals(post)){

				position = count;

			}

			count++;

		}

		if(position!=9999){


		recyclerView.getLayoutManager().scrollToPosition(position);

		}
	}

	public void singleOpenPost(int position){

		int count = 0;
		Log.d("Count", Integer.toString(count));

		for(Post p : posts){

			if(position == count){

				RecyclerView.ViewHolder Viewholder = recyclerView.findViewHolderForAdapterPosition(count);

				if(Viewholder instanceof ViewHolder){

					ViewHolder holder = (ViewHolder) Viewholder;

					holder.expand.expand();

				}



			}
			else{

				RecyclerView.ViewHolder Viewholder = recyclerView.findViewHolderForAdapterPosition(count);

				if(Viewholder instanceof ViewHolder){

					ViewHolder holder = (ViewHolder) Viewholder;

					holder.expand.collapse();

				}

				else{

					Log.d("Returned Holder","Not instance of Custom Holder");
				}


			}

			count++;



		}


	}
}
