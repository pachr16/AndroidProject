package sdu.shoppinglistapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.ContentAdapter;
import sdu.shoppinglistapp.business.ShopItem;

public class ShoppingListContent extends AppCompatActivity {

    ListView content;
    EditText amount;
    EditText product;
    EditText addPerson;
    Button btn_addToList;
    Button btn_addPerson;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    private String listId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_content);

        listId = getIntent().getExtras().getString("listId");
        Log.d("listId", "onCreate: " + listId);


        final ArrayList<ShopItem> contentList = new ArrayList<>();

        content = findViewById(R.id.content_listView);
        amount = findViewById(R.id.content_amount);
        product = findViewById(R.id.content_product);
        addPerson = findViewById(R.id.content_addPerson);
        btn_addToList = findViewById(R.id.btn_addToList);
        btn_addPerson = findViewById(R.id.btn_addPerson);

        final ContentAdapter contentAdapter = new ContentAdapter(ShoppingListContent.this, R.layout.content_view_layout, contentList);

        content.setAdapter(contentAdapter);

        myRef = database.getReference("shoppingLists/" + listId + "/content");
        Log.d("ItemOnList", "onCreateView: Sample text ");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.child("picked").getValue() != null) {
                    Log.d("something", "onChildAdded: picked = " + dataSnapshot.child("picked").getValue().toString());
                    if (product.getText().toString() == "" || amount.getText().toString() == "") {
                        Toast.makeText(ShoppingListContent.this, "Unable to add nothing to the list", Toast.LENGTH_SHORT).show();
                    } else {

                        ShopItem newItem = new ShopItem(dataSnapshot.child("amount").getValue().toString(), dataSnapshot.child("product").getValue().toString(), dataSnapshot.child("picked").getValue().toString());

                        Log.d("ItemOnList", "onChildAdded: entered added method");
                        contentList.add(newItem);
                        Log.d("ItemOnList", "onChildAdded: " + contentList.get(0).getProduct());
                        contentAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
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
                    Toast.makeText(ShoppingListContent.this, "Unable to create an entry without content", Toast.LENGTH_SHORT).show();
                } else {
                    addItemToList(listId, amount.getText().toString(), product.getText().toString());
                    Toast.makeText(ShoppingListContent.this, "Item has been added to the list", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(addPerson.getText().toString().equals("")) {
                    Toast.makeText(ShoppingListContent.this, "Please enter a screen name", Toast.LENGTH_SHORT).show();
                } else {

                    final String tmpUser = addPerson.getText().toString();
                    final String[] foundUser = {""};

                    myRef = database.getReference("users");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Log.d("test", "onDataChange: userId " + ds.child("screen_name").getValue().toString());
                                //Log.d("test", "onDataChange: tmpUser " + tmpUser);
                                //Log.d("test", "onDataChange: firebase " + ds.child("screen_name").getValue().toString());
                                if (ds.child("screen_name").getValue().toString().equals(tmpUser)) {
                                    Log.d("test", "onDataChange: User found");
                                    foundUser[0] = ds.child("user_id").getValue().toString().trim();
                                    Log.d("test", "onDataChange: userId " + foundUser[0]);
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    if(foundUser[0].equals("")) {
                        myRef = database.getReference("users/" + foundUser[0] + "/subscribed_to");
                        myRef.child(listId).setValue(listId);
                        addPerson.setText("");
                    } else {
                        Toast.makeText(ShoppingListContent.this, (foundUser[0] + " User not found"), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    public void addItemToList(String listId, String amount, String product) {
        myRef = database.getReference("shoppingLists/" + listId + "/content/" + product + "/amount");
        myRef.setValue(amount);

        myRef = database.getReference("shoppingLists/" + listId + "/content/" + product + "/product");
        myRef.setValue(product);

        myRef = database.getReference("shoppingLists/" + listId + "/content/" + product + "/picked");
        myRef.setValue("false");
    }
}
