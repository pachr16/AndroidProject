package sdu.shoppinglistapp.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import sdu.shoppinglistapp.business.ShopItem;
import sdu.shoppinglistapp.business.ShopList;

public class DbHandler implements Serializable {
    private static DbHandler instance = null; // instance of singleton class
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();

    // getinstance for singleton instance
    public static DbHandler getInstance() {
        if (instance == null) {
            instance = new DbHandler();
        }
        return instance;
    }

    // private constructor for singleton purposes
    private DbHandler() { }


    public void createUser(String userId, String screenName) {
        Map<String, String> user = new HashMap<>();
        user.put("ScreenName", screenName);

        fdb.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("createUser", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("createUser", "Error Writing document", e);
                    }
                });
    }

    public String[] getUser(String userId) {
        final String[] screenName = new String[1];
        DocumentReference user = fdb.collection("users").document(userId);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("getUser", "DocumentSnapshot data: " + document.getData().get("ScreenName").toString());
                        screenName[0] = document.getData().get("ScreenName").toString();
                        Log.d("getUser", screenName[0]);
                    } else {
                        Log.d("getUser", "No such document");
                    }
                } else {
                    Log.d("getUser", "get failed with ", task.getException());
                }
            }
        });
        return screenName;
    }
}
