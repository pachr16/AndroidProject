package sdu.shoppinglistapp.businessFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.ShopList;
import sdu.shoppinglistapp.business.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOverviewFragment extends Fragment {
    private ListView listView;
    private User user;

    public ListOverviewFragment() {
        // Required empty public constructor
    }

    public ListOverviewFragment(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<String> listNames = new ArrayList<String>();
        for (ShopList l: user.getSubscribedShopLists()) {
            listNames.add(l.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listNames);

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_list_overview, container, false);
        listView = layout.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DEBUG", "onItemClick: " + id);
                ShopList list = user.getSubscribedShopLists().get((int)id);     // id we are given should be id for row of the list that was clicked. We're hoping its 0-indexed.

                Fragment slf = new ShopListFragment(list);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.bitch_please, slf);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return layout;
    }
}
