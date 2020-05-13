package sdu.shoppinglistapp.businessFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.activities.ShoppingActivity;
import sdu.shoppinglistapp.activities.ShoppingListContent;
import sdu.shoppinglistapp.business.OverViewAdapter;
import sdu.shoppinglistapp.business.ShoppingList;
import sdu.shoppinglistapp.services.ListService;

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
        final ArrayList<ShoppingList> shoplistOverview = new ArrayList<>();

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
                    subList.add(strArray[0]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //OverViewAdapter is a custom overwritten class for displaying specific object attributes on the listView
        final OverViewAdapter arrayAdapter = new OverViewAdapter((ShoppingActivity) getActivity(), android.R.layout.simple_list_item_1, shoplistOverview);

        listView.setAdapter(arrayAdapter);


        myRef = database.getReference("shoppingLists");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("test", "onChildAdded: " + dataSnapshot.child("listName").getValue());

                if (subList.contains(dataSnapshot.child("listName").getValue())) {
                    Log.d("test", "onChildAdded: snapshot: " + dataSnapshot.getKey());

                    ArrayList<String> subscribers = getSubscribers(dataSnapshot.getKey());
                    ArrayList<String> content = getContent(dataSnapshot.getKey());

                    shoplistOverview.add(new ShoppingList(dataSnapshot.getKey(), dataSnapshot.child("listName").getValue().toString(), subscribers, content));
                    arrayAdapter.notifyDataSetChanged();

                }
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText((ShoppingActivity)getActivity(), shoplistOverview.get(position).getName(), Toast.LENGTH_SHORT).show();


                String tmpId = shoplistOverview.get(position).getId();
                Log.d("EventBus", "onItemClick: tmpId = " + tmpId);
                ListService.setActiveList(tmpId);
                Intent intent = new Intent((ShoppingActivity)getContext(), ShoppingListContent.class);
                intent.putExtra("listId", tmpId);
                startActivity(intent);

            }
        });



        return view;
    }

    private ArrayList<String> getSubscribers(String listName) {
        final ArrayList<String> subscribers = new ArrayList<>();
        DatabaseReference tmpRef = database.getReference("shoppingLists/" + listName + "/subscribers");

        tmpRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Toast.makeText((ShoppingActivity)getActivity(), dataSnapshot.getValue() + "", Toast.LENGTH_SHORT).show();
                    subscribers.add((String) snapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return subscribers;
    }

    private ArrayList<String> getContent(String listName) {
        final ArrayList<String> content = new ArrayList<>();

        DatabaseReference tmpRef = database.getReference("shoppingLists/" + listName + "/content");

        tmpRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Toast.makeText((ShoppingActivity)getActivity(), dataSnapshot.getValue() + "", Toast.LENGTH_SHORT).show();
                    content.add((String) snapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return content;
    }
}
