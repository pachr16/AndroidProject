package sdu.shoppinglistapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import sdu.shoppinglistapp.R;
import sdu.shoppinglistapp.business.ShopItem;
import sdu.shoppinglistapp.business.ShopList;
import sdu.shoppinglistapp.business.User;

public class MainActivity extends AppCompatActivity {
    private User user = null;
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fdb.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc: task.getResult()) {
                                Log.d("***DEBUG", "onComplete: Succeeded in GET. Got this id: " + doc.getId() + ", with: " + doc.getData());
                            }
                        } else {
                            Log.d("***DEBUG", "onComplete: Failed in GET");
                        }
                    }
                });

        /*
        // FOR TESTING PURPOSES:
        ArrayList<ShopList> slist = new ArrayList<>();
        ArrayList<ShopItem> ilist = new ArrayList<>();
        ilist.add(new ShopItem("thisisanitem", false, "ThisisaScreenName", 0));
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "ThisisaScreenName");
        slist.add(new ShopList(0, "thisisalist", 109850923, ilist, map));
        user = new User("Patrick", "email", 0, slist);

         */

        // END OF TESTING PURPOSES

        Log.d("MyTag", "onCreate: Main found");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // checks if the user is logged in (i.e. not null), and sends them to login if no User object is found. Otherwise redirects to the shoppingActivity.
        if (user == null) {
            Log.d("***DEBUG", "onResume: User was NULL, going to login activity");
            Intent logIntent = new Intent(this, LoginActivity.class);
            startActivity(logIntent);
        } else {
            Log.d("***DEBUG", "onResume: User was: " + user.getName() + ", ref: " + user);
            Intent shopIntent = new Intent(this, ShoppingActivity.class);
            shopIntent.putExtra("User", user);
            startActivity(shopIntent);
        }
    }

    // use to set the user after login
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
