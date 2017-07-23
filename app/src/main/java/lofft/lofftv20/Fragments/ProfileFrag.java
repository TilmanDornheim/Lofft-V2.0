package lofft.lofftv20.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import lofft.lofftv20.Adapters.ProfilePagerAdapter;
import lofft.lofftv20.Adapters.ProfileTagAdapter;
import lofft.lofftv20.Models.User;
import lofft.lofftv20.R;
import lofft.lofftv20.Util.ColorUtil;
import lofft.lofftv20.Util.StringUtil;

/**
 * Created by tilma on 2017-07-20.
 */

public class ProfileFrag extends Fragment {

	private View mView;
	private Context context;
	private RecyclerView tagsRecycler;
	private RelativeLayout background;
	private ArrayList<String> tags = new ArrayList<>();
	private User user;
	private TextView name;
	private ImageView star;
	private CircleImageView profilePic;
	private TabLayout tabLayout;
	private ViewPager pager;
	private Toolbar toolbar;



	public static ProfileFrag newInstance(User user){

		ProfileFrag frag = new ProfileFrag();
		Bundle args = new Bundle();

		args.putSerializable("User",user);

		frag.setArguments(args);

		return frag;

	}

	@Override
	public void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);

		user = (User) getArguments().getSerializable("User");

		tags = user.getTags();

		Log.d("Tags",tags.toString());


		context = getActivity();


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		mView = inflater.inflate(R.layout.fragment_profile,container,false);

		LinearLayoutManager layoutManager
				= new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

		tagsRecycler = (RecyclerView)mView.findViewById(R.id.profile_TagRecycler);

		tagsRecycler.setLayoutManager(layoutManager);

		ProfileTagAdapter adapter = new ProfileTagAdapter(context,tags,user.getFavorite().getColor());

		name = (TextView)mView.findViewById(R.id.profile_Name);

		name.setText(StringUtil.getFullName(user));

		profilePic = (CircleImageView) mView.findViewById(R.id.profile_Picture);

		Picasso.with(getActivity()).load(user.getPicture())
				.fit()
				.centerInside()
				.into(profilePic);

		tagsRecycler.setAdapter(adapter);





		Log.d("Color",ColorUtil.getLowAlphaColor(user.getFavorite().getColor()));
		//background.setBackgroundColor(Color.parseColor(ColorUtil.getLowAlphaColor(user.getFavorite().getColor())));
		String color = user.getFavorite().getColor();


		profilePic.setBorderColor(Color.parseColor(color));


		ImageView locationIcon = (ImageView)mView.findViewById(R.id.profile_location_icon);
		TextView locationText = (TextView)mView.findViewById(R.id.profile_location_text);

		locationText.setTextColor(Color.parseColor(color));
		locationText.setText(user.getHomeLocation().getName());

		Drawable icon = context.getResources().getDrawable(R.drawable.ic_location_on_white_36dp);
		icon.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);

		locationIcon.setImageDrawable(icon);



		star = (ImageView)mView.findViewById(R.id.profile_star);


		if(user.getStar()){


			star.setVisibility(View.VISIBLE);
			Drawable starDrawable = context.getResources().getDrawable(R.drawable.ic_stars_white_48dp);
			starDrawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);
			star.setImageDrawable(starDrawable);


		}

		else{

			star.setVisibility(View.GONE);

		}

		//Set up Tab Layout

		tabLayout = (TabLayout)mView.findViewById(R.id.profile_tab);
		pager = (ViewPager)mView.findViewById(R.id.profile_pager);

		ProfilePagerAdapter pagerAdapter = new ProfilePagerAdapter(getChildFragmentManager());
		pagerAdapter.addFragment(ProfileTabFrag1.newInstance(user),"Overview");
		pagerAdapter.addFragment(ProfileTabFrag2.newInstance(user),"Reviews");

		pager.setAdapter(pagerAdapter);
		tabLayout.setupWithViewPager(pager);

		tabLayout.setSelectedTabIndicatorColor(Color.parseColor(color));

		String black = "#000000";
		tabLayout.setTabTextColors(Color.parseColor(black),Color.parseColor(color));













		return mView;

	}

}
