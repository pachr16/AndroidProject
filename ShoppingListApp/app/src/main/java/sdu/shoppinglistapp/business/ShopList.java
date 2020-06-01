package sdu.shoppinglistapp.business;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sdu.shoppinglistapp.persistence.DbHandler;

public class ShopList implements Serializable {
    private String id = "";
    private String listName;
    private long time = 0;
    private HashMap<String, String> users = new HashMap<>();
    private ArrayList<ShopItem> items = new ArrayList<>();
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();


    /**
     * for creating a new empty shoplist that has not yet been given id, nor a name
     * @param userid
     */
    public ShopList(String userid, String screenname) {
        this.listName = "New Shopping List";
        this.time = System.currentTimeMillis();
        this.users.put(userid, screenname);
    }

    /**
     * for creating empty shoplist that has not yet been given id, but has been given a name
     * @param userid
     */
    public ShopList(String userid, String screenname, String listName) {
        this.listName = listName;
        this.time = System.currentTimeMillis();
        this.users.put(userid, screenname);     // id of creating user, add further users with dedicated method
    }

    /**
     * for creating an existing shoplist from the database with multiple users
     * @param id
     * @param listName
     * @param time
     * @param items
     * @param users
     */
    public ShopList(String id, String listName, long time, ArrayList<ShopItem> items, HashMap<String, String> users) {
        this.id = id;
        this.listName = listName;
        this.time = time;
        for (Map.Entry<String, String> entry: users.entrySet()) {
            String u = entry.getKey();
            String sn = entry.getValue();
            this.users.put(u, sn);
        }
        this.items = items;
    }

    // *** METHODS TO MUTATE LISTS / ITEMS ON LISTS BELOW HERE ***

    public void addUserToList(String email) {
        this.updateTimeStamp();

        fdb.collection("users").whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String doc_id = task.getResult().getDocuments().get(0).getId();
                    String screen_name = (String)task.getResult().getDocuments().get(0).get("screen_name");
                    users.put(doc_id, screen_name);

                    fdb.collection("users").document(doc_id)
                            .update("subscribed_to", FieldValue.arrayUnion(id));

                    Log.d("***DEBUG", "onComplete: we found user with id: " + doc_id);

                } else {
                    Log.d("***DEBUG", "onComplete: failed with exception: " + task.getException());
                }
            }
        });
    }

    public void removeUser(String userid) {
        this.updateTimeStamp();
        this.users.remove(userid);

        fdb.collection("users").document(userid)
                .update("subscribed_to", FieldValue.arrayRemove(id));
    }

    // add / remove items from list
    public void addItem(final ShopItem item) {
        this.updateTimeStamp();

        final Map<String, Object> map = new HashMap<>();
        map.put("added_by", item.getAddedBy());
        map.put("checkmarked", item.isCheckmarked());
        map.put("name", item.getItemString());

        fdb.collection("lists").document(id)
                .collection("items").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    items.add(new ShopItem(item.getItemString(), item.isCheckmarked(), item.getAddedBy(), task.getResult().getId()));
                } else {
                    Log.d("***DEBUG", "onComplete: addItem in ShopList failed with: " + task.getException());
                }
            }
        });
    }
    public void removeItem(ShopItem item) {
        this.updateTimeStamp();

        fdb.collection("lists").document(id)
                .collection("items").document(item.getId())
                .delete();

        this.items.remove(item);
    }

    public void checkmark(ShopItem item) {
        int index = this.items.indexOf(item);
        this.items.get(index).flipCheckmarked();
        this.updateTimeStamp();

        fdb.collection("lists").document(id)
                .collection("items").document(item.getId())
                .update("checkmarked", item.isCheckmarked());
    }


    /**
     * checks for updates for this id in the database and updates the current object to match
     */
    // er i gang med at lave denne om, men er i tvivl om den stadig er nødvendig
    /*
    public void update() {
        fdb.collection("lists").document(id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    listName = (String)task.getResult().get("list_name");
                    time = (long)task.getResult().get("last_updated");

                    ArrayList<ShopItem> newItems = new ArrayList<>();
                    Map<String, String> newUsers = new HashMap<>();

                    for (Object i: task.getResult().get("items")) {

                    }


                } else {
                    Log.d("***DEBUG", "onComplete: Failed updating shoplist with: " + task.getException());
                }
            }
        });


            ShopList newList = dbh.getShopList(this.id);
            this.items = newList.getItems();
            this.users = newList.getUsers();
            this.listName = newList.getName();
            this.time = newList.getTime();
    }

     */

    // *** GETTERS BELOW HERE ***

    // er ikke sikker på at denne skal bruges længere ???
    /*
    public boolean hasChanged() {
        boolean change = false;

        fdb.collection("lists").document(id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (time != (long)task.getResult().get("last_updated")) {
                        change = true;
                    }
                } else {
                    Log.d("***DEBUG", "onComplete: hasChanged() failed with: " + task.getException());
                }
            }
        });

        return change;
    }

     */


    public String getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public HashMap<String, String> getUsers() {
        return users;
    }

    public ArrayList<ShopItem> getItems() {
        return items;
    }

    public String getName() {
        return this.listName;
    }

    // *** SETTERS BELOW HERE ***
    public void setId(String id) {
        this.id = id;
    }

    // call whenever a list is mutated to update timestamp for last change in list
    private void updateTimeStamp() {
        this.time = System.currentTimeMillis();
    }
}
