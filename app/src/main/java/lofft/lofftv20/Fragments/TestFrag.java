package lofft.lofftv20.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lofft.lofftv20.R;

/**
 * Created by tilma on 2017-07-20.
 */

public class TestFrag extends Fragment {

	private static final String EXTRA_TEXT = "text";

	public static TestFrag createFor(String text) {
		TestFrag fragment = new TestFrag();
		Bundle args = new Bundle();
		args.putString(EXTRA_TEXT, text);
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_text, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		String text = getArguments().getString(EXTRA_TEXT);
		TextView textView = (TextView) view.findViewById(R.id.text);
		textView.setText(text);
	}
}
