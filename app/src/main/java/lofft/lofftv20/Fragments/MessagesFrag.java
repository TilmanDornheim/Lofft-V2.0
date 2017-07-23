package lofft.lofftv20.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lofft.lofftv20.R;

/**
 * Created by tilma on 2017-07-20.
 */

public class MessagesFrag extends Fragment {

	private View mView;
	private Context context;

	public static MessagesFrag newInstance(Bundle args){

		MessagesFrag frag = new MessagesFrag();
		frag.setArguments(args);

		return frag;

	}

	@Override
	public void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);

		context = getActivity();


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		mView = inflater.inflate(R.layout.fragment_messages,container,false);


		return mView;

	}

}
