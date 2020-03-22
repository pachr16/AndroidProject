package sdu.shoppinglistapp.businessFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.ShopItem;
import sdu.shoppinglistapp.business.ShopList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopListFragment extends Fragment {
    private ShopList slist;
    private ListView itemView;

    public ShopListFragment() {
        // Required empty public constructor
    }

    public ShopListFragment(ShopList slist) {
        this.slist = slist;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<String> items = new ArrayList<>();
        for (ShopItem i: slist.getItems()) {
            items.add(i.getItemString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, items);

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_shop_list, container, false);

        itemView = layout.findViewById(R.id.itemView);
        itemView.setAdapter(adapter);

        itemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DEBUG", "ShopListFragment onItemClick id: " + id);
                slist.checkmark(slist.getItems().get((int)id));     // id we are given should be id for row of the item that was clicked. We're hoping its 0-indexed.
            }
        });

        return layout;
    }
}
