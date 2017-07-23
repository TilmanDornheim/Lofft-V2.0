package lofft.lofftv20.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lofft.lofftv20.Models.User;
import lofft.lofftv20.R;

/**
 * Created by tilma on 2017-07-21.
 */

public class ProfileTabFrag2 extends Fragment {

	private Context context;
	private View mView;

	private User user;

	public static ProfileTabFrag2 newInstance(User user){


		ProfileTabFrag2 frag = new ProfileTabFrag2();
		Bundle args = new Bundle();

		args.putSerializable("User",user);
		frag.setArguments(args);


		return frag;

	}


	@Override
	public void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);

		user = (User) getArguments().getSerializable("User");

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		mView = inflater.inflate(R.layout.profile_tab_two,container,false);

		return mView;
	}


}
