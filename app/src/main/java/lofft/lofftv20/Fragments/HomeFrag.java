package lofft.lofftv20.Fragments;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.thesurix.gesturerecycler.DefaultItemClickListener;
import com.thesurix.gesturerecycler.GestureManager;
import com.thesurix.gesturerecycler.RecyclerItemTouchListener;
import com.yalantis.filter.animator.FiltersListItemAnimator;

import java.util.ArrayList;
import java.util.Arrays;

import lofft.lofftv20.Adapters.HomeCardAdapter;
import lofft.lofftv20.Adapters.HomeCategoryFilterAdapter;
import lofft.lofftv20.Interfaces.categoryFilterChangedListener;
import lofft.lofftv20.Interfaces.mapExpandedInPostListener;
import lofft.lofftv20.Interfaces.postItemExpandedListener;
import lofft.lofftv20.MainActivity;
import lofft.lofftv20.Models.Category;
import lofft.lofftv20.Models.Post;
import lofft.lofftv20.Models.User;
import lofft.lofftv20.R;
import lofft.lofftv20.Util.Categories;
import lofft.lofftv20.Util.CategoryUtil;
import lofft.lofftv20.Util.ColorUtil;
import lofft.lofftv20.Util.DummyTags;
import lofft.lofftv20.Util.LatLngUtils;
import lofft.lofftv20.Util.LogUtil;

/**
 * Created by tilma on 2017-07-20.
 */

public class HomeFrag extends Fragment implements categoryFilterChangedListener, postItemExpandedListener, mapExpandedInPostListener, OnMapReadyCallback {


	private View mView;
	private Context context;
	private RecyclerView recyclerView, categoryRecycler;
	private AppBarLayout barLayout;
	private SwipeRefreshLayout refresh;
	private RelativeLayout panel;
	private MapView mapView;
	private GoogleMap map;

	private boolean clickable;

	private HomeCardAdapter adapter2;
	private HomeCategoryFilterAdapter adapter;

	private LinearLayoutManager layoutManager;

	private SlidingUpPanelLayout slide;

	private SharedPreferences pref;


	private ArrayList<Post> posts = new ArrayList<>();
	private ArrayList<Category> categories = new ArrayList<>();
	private ArrayList<Category> selected = new ArrayList<>();


	private Category c1 = Categories.sportsLeisure;
	private Category c2 = Categories.gamesConsoles;
	private Category c3 = Categories.carsMotors;
	private Category c4 = Categories.electronics;
	private Category c5 = Categories.babyChild;
	private Category c6 = Categories.booksMusicMovies;
	private Category c7 = Categories.fashionAccessories;
	private Category c8 = Categories.homeGarden;

	private ArrayList<String> t1 = new ArrayList<>(Arrays.asList(DummyTags.tagsMichael));
	private ArrayList<String> t2 = new ArrayList<>(Arrays.asList(DummyTags.tagsTommaso));
	private ArrayList<String> t3 = new ArrayList<>(Arrays.asList(DummyTags.tagsRichard));


	private User u1 = new User(0, "mwilf", "Michael", "Wilfert", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKNSIi432pJPG4i9-29ISCit084BKvyfDE1RkxS5gjQWDQYDx9", t1, 4.5f, new Category(Categories.electronics.getName(), Categories.electronics.getColor(), Categories.electronics.getDrawable()), true, LatLngUtils.london);
	private User u2 = new User(1, "tguer", "Tommaso", "Guerzoni", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKNSIi432pJPG4i9-29ISCit084BKvyfDE1RkxS5gjQWDQYDx9", t2, 5f, new Category(Categories.gamesConsoles.getName(), Categories.gamesConsoles.getColor(), Categories.gamesConsoles.getDrawable()), true, LatLngUtils.venice);
	private User u3 = new User(2, "rbong", "Richard", "Bongartz", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKNSIi432pJPG4i9-29ISCit084BKvyfDE1RkxS5gjQWDQYDx9", t3, 3.0f, new Category(Categories.babyChild.getName(), Categories.babyChild.getColor(), Categories.babyChild.getDrawable()), true, LatLngUtils.moronMountain);


	public static HomeFrag newInstance(Bundle args) {

		HomeFrag frag = new HomeFrag();
		frag.setArguments(args);

		return frag;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		context = getActivity();

		pref = PreferenceManager.getDefaultSharedPreferences(context);


		categories.add(c4);
		categories.add(c1);
		categories.add(c8);
		categories.add(c5);
		categories.add(c3);
		categories.add(c2);
		categories.add(c6);
		categories.add(c7);

		selected = categories;


	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.fragment_home, container, false);

		recyclerView = (RecyclerView) mView.findViewById(R.id.home_Recycler);
		categoryRecycler = (RecyclerView) mView.findViewById(R.id.home_categories_recycler);
		refresh = (SwipeRefreshLayout) mView.findViewById(R.id.home_pullRefresh);
		barLayout = (AppBarLayout) mView.findViewById(R.id.home_appbar);
		slide = (SlidingUpPanelLayout) mView.findViewById(R.id.home_map_slide);
		//mapView = (MapView) mView.findViewById(R.id.home_map_map);
		panel = (RelativeLayout)mView.findViewById(R.id.home_map_panel);

		//Set up Map
		//mapView.onCreate(null);
		SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.home_map_map);
		mapFragment.getMapAsync(this);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			StateListAnimator stateListAnimator = new StateListAnimator();
			stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(barLayout, "elevation", 10f));
			barLayout.setStateListAnimator(stateListAnimator);
		}

		// Pull to Refresh Layout Set up
		refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {

				loadPosts(selected, true);

			}
		});



		//slide listeners and setup

		slide.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
			@Override
			public void onPanelSlide(View panel, float slideOffset) {



			}

			@Override
			public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

				if(newState.equals(SlidingUpPanelLayout.PanelState.EXPANDED)){

					barLayout.setExpanded(true);




				}

			}
		});


		slide.setDragView(panel);

		//Category Recycler Set up
		adapter = new HomeCategoryFilterAdapter(getActivity(), categories);

		LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

		adapter.setFilterUpdatedListener(this);


		categoryRecycler.setLayoutManager(manager);
		categoryRecycler.setAdapter(adapter);


		//Posts Recycler Set up
		adapter2 = new HomeCardAdapter(getActivity(), posts, getChildFragmentManager(),recyclerView);
		adapter2.setListener((MainActivity) getActivity());
		adapter2.setExpandListener(this);
		adapter2.setMapListener(this);

		layoutManager
				= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

		recyclerView.setItemAnimator(new FiltersListItemAnimator());
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter2);


		// Library Implementation

		GestureManager gestureManager = new GestureManager.Builder(categoryRecycler)
				.setSwipeEnabled(false)
				.setLongPressDragEnabled(false)
				.build();

		categoryRecycler.addOnItemTouchListener(new RecyclerItemTouchListener(getActivity(), new DefaultItemClickListener() {

			@Override
			public boolean onItemClick(final View view, final int position) {
				// return true if the event is consumed


				return false;
			}

			@Override
			public void onItemLongPress(final View view, final int position) {

				if (pref.getBoolean("pref_interface_longPress", true)) {

					adapter.setClickable(false);

					// Handle Long Press Singling

					HomeCategoryFilterAdapter.ViewHolder holder = (HomeCategoryFilterAdapter.ViewHolder) categoryRecycler.getChildViewHolder(view);

					adapter.singleItem(categories.get(position), holder, position);

					Log.d("Long Press", "detected in Fragment");

				}


			}


			@Override
			public boolean onDoubleTap(final View view, final int position) {
				// return true if the event is consumed


				if (pref.getBoolean("pref_interface_doubleClick", true)) {

					adapter.selectAll();

					return true;

				}

				return false;

			}


		}));


		loadPosts(selected, false);


		return mView;

	}

	@Override
	public void filterUpdated(ArrayList<Category> selected) {

		Log.d("Selection", "incoming");
		LogUtil.logArrayList(selected);

		this.selected = selected;
		refresh.setRefreshing(true);
		loadPosts(this.selected, true);
		updateMarkers();


	}

	public void loadPosts(ArrayList<Category> selected, boolean notify) {


		LogUtil.logArrayList(selected);

		posts.clear();


		// Dummy Posts

		Post p1 = new Post(0, u1, "", "Mountain Bike", 20, "Day", 4, "Hey, I need a mountain bike for the weekend", c1, u1.getHomeLocation());
		Post p2 = new Post(1, u2, "", "Playstation", 30, "Week", 1, "Hey, me and my friends need a playstation for a week", c2, u2.getHomeLocation());
		Post p3 = new Post(2, u3, "", "Vespa", 50, "Day", 1, "Hey, I need a Vespa... Bad.", c3, u3.getHomeLocation());
		Post p4 = new Post(3, u2, "", "Television", 60, "Week", 1, "I need a Tv.", c4, u2.getHomeLocation());


		posts.add(p1);
		posts.add(p2);
		posts.add(p4);
		posts.add(p3);
		posts.add(p1);
		posts.add(p4);


		ArrayList<Post> filteredPosts = new ArrayList<>();

		for (Post p : posts) {

			if (CategoryUtil.containsCategory(p, selected)) {

				filteredPosts.add(p);

			}

		}

		posts = filteredPosts;

		LogUtil.logArrayListPost(posts);

		if (notify) {


			adapter2.clear();
			adapter2.addList(posts);


		}

		refresh.setRefreshing(false);


	}

	@Override
	public void itemExpanded(int position) {

		//layoutManager.scrollToPosition(position);

	}

	@Override
	public void mapOpened(Post post) {


		slide.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
		mapFocusOnLocation(post.getLocation());

	}

	@Override
	public void onMapReady(GoogleMap googleMap) {

		map = googleMap;

		MapsInitializer.initialize(context);

		boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context,R.raw.style_json));

		if(!success){

			Toast.makeText(context, "Error, couldnt load map style", Toast.LENGTH_SHORT).show();
		}

		updateMarkers();

		map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
			@Override
			public void onCircleClick(Circle circle) {

				Toast.makeText(context, "Circle clicked", Toast.LENGTH_SHORT).show();

				Post post = getPostFromCircle(circle);

				slide.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

				adapter2.scrollToPost(post);

				Log.d("Post Position", Integer.toString(getPostPosition(post)));
				adapter2.singleOpenPost(getPostPosition(post));

			}
		});




		mapFocusOnMe();


	}

	public void updateMarkers(){

		map.clear();

		for(Post p : posts){

			lofft.lofftv20.Models.Location location = p.getLocation();

			Circle circle = map.addCircle(new CircleOptions()
					.clickable(true)
					.center(location.getLatLng())
					.radius(200)
					.strokeColor(Color.BLACK)
					.strokeWidth(1)
					.fillColor(Color.parseColor(ColorUtil.getLowAlphaColor(p.getCategory().getColor()))));





		}





	}


	public Post getPostFromCircle(Circle circle){


		LatLng latLng = circle.getCenter();

		for(Post p : posts){


			if(latLng.equals(p.getLocation().getLatLng())){

				return p;

			}

		}

		return null;


	}

	public void mapFocusOnMe() {


		LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);


		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			ActivityCompat.requestPermissions(getActivity(), new String[] {
							Manifest.permission.ACCESS_FINE_LOCATION,
							Manifest.permission.ACCESS_COARSE_LOCATION },1340);

		}

		else {
			//Android location, not model
			Location loc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			if(loc!=null) {

				LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());


				map.setMyLocationEnabled(true);
				map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
				map.animateCamera(CameraUpdateFactory.zoomTo(13));

			}

			else{

				map.setMyLocationEnabled(true);

				Toast.makeText(context, "Error, couldnt get location", Toast.LENGTH_SHORT).show();
			}

		}

	}

	public void mapFocusOnLocation(lofft.lofftv20.Models.Location location){

		map.moveCamera(CameraUpdateFactory.newLatLng(location.getLatLng()));
		map.animateCamera(CameraUpdateFactory.zoomTo(13));




	}


	public int getPostPosition(Post post) {

		int count = 0;

		for(Post p : posts){

			if(p.equals(post)){

				return count;

			}

		}

		return 9999;

	}
}
