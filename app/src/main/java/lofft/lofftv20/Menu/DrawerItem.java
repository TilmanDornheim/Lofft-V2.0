package lofft.lofftv20.Menu;

import android.view.ViewGroup;

import lofft.lofftv20.Adapters.DrawerAdapter;

/**
 * Created by tilma on 2017-07-20.
 */

public abstract class DrawerItem<T extends DrawerAdapter.ViewHolder> {

	protected boolean isChecked;

	public abstract T createViewHolder(ViewGroup parent);

	public abstract void bindViewHolder(T holder);

	public DrawerItem setChecked(boolean isChecked) {
		this.isChecked = isChecked;
		return this;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public boolean isSelectable() {
		return true;
	}

}