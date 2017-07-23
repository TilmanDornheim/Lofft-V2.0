package lofft.lofftv20;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.SlidingRootNavLayout;

import java.util.ArrayList;
import java.util.Arrays;

import lofft.lofftv20.Adapters.DrawerAdapter;
import lofft.lofftv20.Fragments.HomeFrag;
import lofft.lofftv20.Fragments.MessagesFrag;
import lofft.lofftv20.Fragments.ProfileFrag;
import lofft.lofftv20.Fragments.SettingsFrag;
import lofft.lofftv20.Interfaces.profileSelectedInPostListener;
import lofft.lofftv20.Menu.DrawerItem;
import lofft.lofftv20.Menu.SimpleItem;
import lofft.lofftv20.Menu.SpaceItem;
import lofft.lofftv20.Models.Category;
import lofft.lofftv20.Models.User;
import lofft.lofftv20.Util.Categories;
import lofft.lofftv20.Util.LatLngUtils;
import lofft.lofftv20.Util.StringUtil;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, profileSelectedInPostListener


{

	private ArrayList<String> screenTitles = new ArrayList<>();
	private ArrayList<Integer> screenDrawables = new ArrayList<>();
	private SlidingRootNav nav;
	private ImageView profilePic;
	private ImageView settings;
	private TextView name;
	private User currentUser;
	private ArrayList<String> tags = new ArrayList<>();
	private DrawerAdapter adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);


		tags.add("Experienced");
		tags.add("Reliable");
		tags.add("Responsive");
		currentUser = new User(0,"tilmando","Tilman","Dornheim","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKNSIi432pJPG4i9-29ISCit084BKvyfDE1RkxS5gjQWDQYDx9", tags,4.0f,new Category(Categories.carsMotors.getName(),Categories.carsMotors.getColor(),Categories.carsMotors.getDrawable()),true, LatLngUtils.leverkusen);



		setTitlesAndDrawables();

		SlidingRootNavBuilder builder = new SlidingRootNavBuilder(this)
				.withToolbarMenuToggle(toolbar)
				.withMenuOpened(false)
				.withSavedState(savedInstanceState)
				.withMenuLayout(R.layout.menu_left_drawer);



		nav = builder.inject();

		SlidingRootNavLayout layout = nav.getLayout();

		settings = (ImageView)layout.findViewById(R.id.menu_settings);

		profilePic = (ImageView)findViewById(R.id.menu_profilePic);
		name = (TextView)findViewById(R.id.menu_name);

		name.setText(StringUtil.getFullName(currentUser));

		Picasso.with(this).load(currentUser.getPicture())
				.fit()
				.centerCrop()
				.into(profilePic);



		 adapter = new DrawerAdapter(Arrays.asList(

				createItemFor(0).setChecked(true),
				createItemFor(1),
				createItemFor(2),
				new SpaceItem(48),
				createItemFor(3)

		));

		 settings.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {


			 	if(!(getCurrentFragment() instanceof SettingsFrag)){


			 		showFragment(new SettingsFrag());
			 		nav.closeMenu(true);


			    }

			    else{

			 		nav.closeMenu(true);

			    }

			 }
		 });

		adapter.setListener(this);

		RecyclerView list = (RecyclerView) findViewById(R.id.list);
		list.setNestedScrollingEnabled(false);
		list.setLayoutManager(new LinearLayoutManager(this));
		list.setAdapter(adapter);

		adapter.setSelected(0);



	}




	private Fragment getCurrentFragment() {

		return getSupportFragmentManager().findFragmentById(R.id.container);

	}

	@Override
	public void onItemSelected(int position) {



		switch (position){

			//Home
			case 0:

				if(!(getCurrentFragment() instanceof HomeFrag)){


					showFragment(HomeFrag.newInstance(null));
					break;


				}

				else{

					nav.closeMenu(true);
					break;
				}



			//Messages
			case 1:

				if(!(getCurrentFragment() instanceof  MessagesFrag)){

					showFragment(MessagesFrag.newInstance(null));
					break;

				}
				else{
					nav.closeMenu(true);
					break;

				}


			//Profile
			case 2:

				if(!(getCurrentFragment() instanceof ProfileFrag)){


					showFragment(ProfileFrag.newInstance(currentUser));
					break;


				}

				else{

					nav.closeMenu(true);
					break;

				}




			//Logout
			case 3:

				return;




		}



		nav.closeMenu(true);

	}



	private DrawerItem createItemFor(int position) {

		Drawable drawable = getResources().getDrawable(screenDrawables.get(position));

		return new SimpleItem(drawable, screenTitles.get(position))
				.withIconTint(color(R.color.textColorSecondary))
				.withTextTint(color(R.color.textColorPrimary))
				.withSelectedIconTint(color(R.color.colorAccent))
				.withSelectedTextTint(color(R.color.colorAccent));
	}


	@ColorInt
	private int color(@ColorRes int res) {
		return ContextCompat.getColor(this, res);
	}


	private void setTitlesAndDrawables(){

		screenDrawables.clear();
		screenTitles.clear();

		screenTitles.add("Home");
		screenTitles.add("Messages");
		screenTitles.add("Profile");
		screenTitles.add("Logout");

		screenDrawables.add(R.drawable.ic_home_white_24dp);
		screenDrawables.add(R.drawable.ic_chat_white_24dp);
		screenDrawables.add(R.drawable.ic_account_circle_white_24dp);
		screenDrawables.add(R.drawable.ic_exit_to_app_white_24dp);


	}

	private void showFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment)
				.addToBackStack(null)
				.commit();
	}


	@Override
	public void profileClicked(User user) {


		ProfileFrag frag = ProfileFrag.newInstance(user);



		showFragment(frag);





	}


}
