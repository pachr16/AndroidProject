package sdu.shoppinglistapp.businessFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.activities.ShoppingActivity;
import sdu.shoppinglistapp.business.ContentAdapter;
import sdu.shoppinglistapp.business.FragmentCommunication;
import sdu.shoppinglistapp.business.ShopItem;

public class FragmentShoppingListContent extends Fragment{

    EventBus bus = EventBus.getDefault();

    ListView content;
    EditText amount;
    EditText product;
    Button btn_addToList;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    private String listId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list_content, container, false);

        bus.register(this);


        final ArrayList<ShopItem> contentList = new ArrayList<>();

        content = view.findViewById(R.id.content_listView);
        amount = view.findViewById(R.id.content_amount);
        product = view.findViewById(R.id.content_product);
        btn_addToList = view.findViewById(R.id.btn_addToList);

       final ContentAdapter contentAdapter = new ContentAdapter((ShoppingActivity)getActivity(), R.layout.content_view_layout, contentList);

      content.setAdapter(contentAdapter);

      myRef = database.getReference("shoppingLists/" + listId + "/content");
        Log.d("ItemOnList", "onCreateView: Sample text ");

      myRef.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              //if(!dataSnapshot.child("product").getValue().equals("")) {
              Log.d("ItemOnList", "onChildAdded: entered added method");
                  contentList.add(new ShopItem(dataSnapshot.child("amount").getValue().toString(), dataSnapshot.child("product").getValue().toString(), (Boolean) dataSnapshot.child("picked").getValue()));
                  Log.d("ItemOnList", "onChildAdded: " + contentList.get(0).getProduct());
                  contentAdapter.notifyDataSetChanged();
              //}
          }

          @Override
          public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              Log.d("ItemOnList", "onChildAdded: changed " + contentList.get(0).getProduct());
              contentAdapter.notifyDataSetChanged();
          }

          @Override
          public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

          }

          @Override
          public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

      btn_addToList.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(amount.equals("") || product.equals("")) {
                  Toast.makeText((ShoppingActivity)getContext(), "Unable to create an entry without content", Toast.LENGTH_SHORT).show();
              } else {
                 // ((ShoppingActivity) getActivity()).addItemToList(listId, amount.getText().toString(), product.getText().toString());
                  Toast.makeText((ShoppingActivity)getContext(), "Item has been added to the list", Toast.LENGTH_SHORT).show();
              }
          }
      });



        return view;
    }

    @Subscribe
    public void onEvent(FragmentCommunication event) {
         listId = event.getNewText();
        Log.d("EventBus", "onEvent: listId = " + listId);
    }

}
