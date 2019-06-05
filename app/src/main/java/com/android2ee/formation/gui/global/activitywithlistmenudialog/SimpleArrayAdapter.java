/**<ul>
 * <li>CocoaHeadsSimple</li>
 * <li>com.android2ee.tuto.simple.cocoaheads</li>
 * <li>3 mai 2012</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage except training and can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.android2ee.formation.gui.global.activitywithlistmenudialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to:
 *        <ul>
 *        <li>show a list view where even and odd lines are differents</li>
 *        </ul>
 */
public class SimpleArrayAdapter extends ArrayAdapter<String> {

	/**
	 * Even position for item
	 */
	private static final int TYPE_ITEM = 1;
	/**
	 * Odd position for item
	 */
	private static final int TYPE_ITEM_ODD = 0;
	/**
	 * The map of the item and their shape
	 */
	private Map<Integer, GradientDrawable> itemsShape;

	/******************************************************************************************/
	/** Constructors **************************************************************************/
	/******************************************************************************************/
	/**
	 * Constructor
	 * 
	 * @param context
	 * @param objects
	 */
	public SimpleArrayAdapter(Context context, List<String> objects) {
		super(context, R.layout.array_list, objects);
		// Instantiate the map
		itemsShape = new HashMap<Integer, GradientDrawable>();
		// fill it if needed

		itemsShape.put(0,
				(GradientDrawable) context.getResources().getDrawable(R.drawable.list_item_odd));

		itemsShape.put(1, (GradientDrawable) context.getResources().getDrawable(R.drawable.list_item));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Find the view
		TextView view = (TextView) super.getView(position, convertView, parent);
		// Set its background according to its position
		switch (getItemViewType(position)) {
		case TYPE_ITEM:
			view.setTextAppearance(getContext(), R.style.cocoaheadsList);
			view.setBackgroundDrawable(itemsShape.get(position%2));
			break;
		case TYPE_ITEM_ODD:
			view.setTextAppearance(getContext(), R.style.cocoaheadsListOdd);
			view.setBackgroundDrawable(itemsShape.get(position%2));
			break;
		}
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#add(java.lang.Object)
	 */
//	@Override
//	public void add(String object) {
//		super.add(object);
//		int i = getCount();
//		if (i % 2 == 0) {
//			itemsShape.put(Integer.valueOf(i),
//					(GradientDrawable) getContext().getResources().getDrawable(R.drawable.list_item_odd));
//		} else {
//			itemsShape.put(Integer.valueOf(i),
//					(GradientDrawable) getContext().getResources().getDrawable(R.drawable.list_item));
//		}
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		return (position % 2 == 0) ? TYPE_ITEM : TYPE_ITEM_ODD;
	}

}
