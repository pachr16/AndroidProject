package sdu.shoppinglistapp.businessFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.activities.ShoppingActivity;

public class FragmentShoppingListOverview extends Fragment {

    ListView listView;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private DatabaseReference subRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_overview, container, false);

        listView = view.findViewById(R.id.overViewListView);

        final ArrayList<String> subList = new ArrayList<>();
        final ArrayList<String> shoplistOverview = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        subRef = database.getReference("users/" + mAuth.getCurrentUser().getUid() + "/subscribed_to");

        subRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("sublist", "onDataChange: something");
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("sublist", "onDataChange: value: " + ds.getValue().toString());
                    String[] strArray = (ds.getValue().toString()).split("_");
                    subList.add(strArray[1]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final ArrayAdapter arrayAdapter = new ArrayAdapter((ShoppingActivity) getActivity(), android.R.layout.simple_list_item_1, shoplistOverview);

        listView.setAdapter(arrayAdapter);


        myRef = database.getReference("shoppingLists");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("test", "onChildAdded: " + dataSnapshot.child("listName").getValue());

                if (subList.contains(dataSnapshot.child("listName").getValue())) {
                    Log.d("test", "onChildAdded: snapshot: " + dataSnapshot.getKey());

                    shoplistOverview.add(dataSnapshot.child("listName").getValue().toString());
                    arrayAdapter.notifyDataSetChanged();

                }
//                String entryOnList = dataSnapshot.getValue(String.class);
//                String[] listNameParts = entryOnList.split("_");
//                String listName = listNameParts[1];
//                shoplistOverview.add(listName);
//                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String[] childToRemove = dataSnapshot.getKey().split("_");
                int index = shoplistOverview.indexOf(childToRemove[1]);
                shoplistOverview.remove(index);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
