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
    private DatabaseReference userRef;
    private DatabaseReference subRef;
    private DatabaseReference productRef;
    private DatabaseReference amountRef;
    private DatabaseReference pickedRef;

    private String listId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_content);

        listId = getIntent().getExtras().getString("listId");


        final ArrayList<ShopItem> contentList = new ArrayList<>();

        content = findViewById(R.id.content_listView);
        amount = findViewById(R.id.content_amount);
        product = findViewById(R.id.content_product);
        addPerson = findViewById(R.id.content_addPerson);
        btn_addToList = findViewById(R.id.btn_addToList);
        btn_addPerson = findViewById(R.id.btn_addPerson);

        final ContentAdapter contentAdapter = new ContentAdapter(ShoppingListContent.this, R.layout.content_view_layout, contentList);

        content.setAdapter(contentAdapter);

        DatabaseReference contentRef = database.getReference("shoppingLists/" + listId + "/content");

        contentRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.child("picked").getValue() != null) {
                    if (product.getText().toString() == "" || amount.getText().toString() == "") {
                        Toast.makeText(ShoppingListContent.this, "Unable to add nothing to the list", Toast.LENGTH_SHORT).show();
                    } else {

                        ShopItem newItem = new ShopItem(dataSnapshot.child("amount").getValue().toString(), dataSnapshot.child("product").getValue().toString(), dataSnapshot.child("picked").getValue().toString());

                        contentList.add(newItem);
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

                    DatabaseReference userRef = database.getReference("users");
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                if (ds.child("screen_name").getValue().toString().equals(tmpUser)) {
                                    foundUser[0] = ds.child("user_id").getValue().toString().trim();
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    if(foundUser[0].equals("")) {
                        DatabaseReference subRef = database.getReference("users/" + foundUser[0] + "/subscribed_to");
                        subRef.child(listId).setValue(listId);
                        addPerson.setText("");
                    } else {
                        Toast.makeText(ShoppingListContent.this, (foundUser[0] + " User not found"), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    public void addItemToList(String listId, String amount, String product) {
        DatabaseReference amountRef = database.getReference("shoppingLists/" + listId + "/content/" + product + "/amount");
        amountRef.setValue(amount);

        DatabaseReference productRef = database.getReference("shoppingLists/" + listId + "/content/" + product + "/product");
        productRef.setValue(product);

        DatabaseReference pickedRef = database.getReference("shoppingLists/" + listId + "/content/" + product + "/picked");
        pickedRef.setValue("false");
    }
}
