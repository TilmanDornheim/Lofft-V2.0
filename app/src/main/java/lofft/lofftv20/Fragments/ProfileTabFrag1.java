package lofft.lofftv20.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lofft.lofftv20.Models.User;
import lofft.lofftv20.R;
import lofft.lofftv20.Util.StringUtil;

/**
 * Created by tilma on 2017-07-21.
 */

public class ProfileTabFrag1 extends Fragment {

	private Context context;
	private View mView;

	private TextView textView;

	private User user;

	public static ProfileTabFrag1 newInstance(User user){


		ProfileTabFrag1 frag = new ProfileTabFrag1();
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

		mView = inflater.inflate(R.layout.profile_tab_one,container,false);

		textView = (TextView)mView.findViewById(R.id.tabOne_tv);

		textView.setText(StringUtil.getFullName(user));

		return mView;
	}


}
