package sdu.shoppinglistapp.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
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


    public String getUserid(String email) {
        final String[] ret = {""};
        Query query = fdb.collection("users").whereEqualTo("email", email);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                    if (doc.exists()) {
                        ret[0] = doc.getId();
                        Log.d("***DEBUG", "onComplete: Document exists!");
                    } else {
                        Log.d("***DEBUG", "onComplete: No such document!");
                    }
                } else {
                    Log.d("***DEBUG", "onComplete: getUserId failed with: " + task.getException());
                }
            }
        });

        Log.d("***DEBUG", "getUserid returns this id-string: " + ret[0]);
        return ret[0];
    }

    /*
    public String getUserScreenname(String email) {
        String ret = "";
        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st = conn.prepareStatement("SELECT users.screen_name FROM users WHERE users.email = ?");
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                ret = rs.getString("screen_name");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    */

    public ShopItem addItem(ShopList slist, ShopItem item) {
        final Map<String, Object> map = new HashMap<>();
        map.put("name", item.getItemString());
        map.put("added_by", item.getAddedBy());
        map.put("checkmarked", item.isCheckmarked());

        fdb.collection("lists").document(slist.getId())
                .collection("items").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        map.put("id", documentReference.getId());
                    }
                });

        return new ShopItem((String)map.get("name"), (boolean)map.get("checkmarked"), (String)map.get("added_by"), (String)map.get("id"));
    }

    /*
    public void removeItem(ShopList slist, ShopItem item) {
        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st = conn.prepareStatement("DELETE FROM items WHERE items.item_id = ?");
            st.setInt(1, item.getId());
            st.executeQuery();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     */

    public void checkmark(ShopList slist, ShopItem item) {
        fdb.collection("lists").document(slist.getId())
                .collection("items").document(item.getId())
                .update("checkmarked", item.isCheckmarked());
    }

    public void addUserToList(ShopList slist, String userid) {
        fdb.collection("users").document(userid)
                .update("items", FieldValue.arrayUnion(slist.getId()));
    }

    /*
    public void removeUserFromList(ShopList slist, int userid) {
        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st = conn.prepareStatement("DELETE FROM subscribed_to WHERE subscribed_to.list_id = ? AND subscribed_to.user_id = ?");
            st.setInt(1, slist.getId());
            st.setInt(2, userid);
            st.executeQuery();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ShopList getShopList(int slistid) {
        ShopList slist = null;
        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st = conn.prepareStatement("SELECT * FROM lists WHERE lists.list_id = ?");
            st.setInt(1, slistid);
            ResultSet res = st.executeQuery();

            while (res.next()) {
                slist = new ShopList(res.getInt("list_id"), res.getString("list_name"), res.getLong("last_updated"), getItemsOnList(slistid), getUsersOnList(slistid));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return slist;
    }

    private HashMap<Integer, String> getUsersOnList(int slistid) {
        HashMap<Integer, String> users = new HashMap<>();

        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st1 = conn.prepareStatement("SELECT subscribed_to.user_id FROM subscribed_to WHERE subscribed_to.list_id = ?");
            st1.setInt(1, slistid);
            ResultSet res1 = st1.executeQuery();

            ArrayList<Integer> userids = new ArrayList<>();
            while (res1.next()) {
                userids.add(res1.getInt("user_id"));
            }

            for (int u: userids) {
                PreparedStatement st2 = conn.prepareStatement("SELECT users.screen_name FROM users WHERE users.user_id = ?");
                st2.setInt(1, u);
                ResultSet res2 = st2.executeQuery();

                while (res2.next()) {
                    users.put(u, res2.getString("screen_name"));
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    private ArrayList<ShopItem> getItemsOnList(int slistid) {
        ArrayList<ShopItem> items = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st = conn.prepareStatement("SELECT * FROM items WHERE items.list_id = ?");
            st.setInt(1, slistid);
            ResultSet res = st.executeQuery();

            while (res.next()) {
                items.add(new ShopItem(res.getString("name"), res.getBoolean("checkmarked"), res.getString("added_by"), res.getInt("item_id")));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return items;
    }

    public boolean hasShopListChanged(ShopList slist) {
        boolean retBool = false; // prob should be true but to test that query actually works its set a false

        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st = conn.prepareStatement("SELECT lists.last_updated FROM lists WHERE lists.list_id = ?");
            st.setInt(1, slist.getId());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                retBool = slist.getTime()!=rs.getLong("last_updated");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retBool;
    }

    public ShopList createShopList(ShopList slist) {
        Map.Entry<Integer, String> entry = slist.getUsers().entrySet().iterator().next();
        int userid = entry.getKey();

        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st = conn.prepareStatement("INSERT INTO lists(list_name, last_updated) VALUES(?, ?) RETURNING lists.list_id");
            st.setString(1, slist.getName());
            st.setLong(2, slist.getTime());
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                slist.setId(rs.getInt(rs.getInt("list_id")));
            }

            PreparedStatement st1 = conn.prepareStatement("INSERT INTO subscribed_to(user_id, list_id) VALUES(?, ?)");
            st1.setInt(1, userid);
            st1.setInt(2, slist.getId());
            st1.executeQuery();


        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return slist;
    }

    public User registerUser(User user) {
        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st = conn.prepareStatement("INSERT INTO users(screen_name, email, password) VALUES(?, ?, ?) RETURNING users.user_id");
            st.setString(1, user.getName());
            st.setString(2, user.geteMail());
            st.setString(2, user.getPassword());
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                user.setId(rs.getInt("user_id"));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public boolean doesEmailExist(String email) {
        boolean retBool = false;

        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st = conn.prepareStatement("SELECT users.email FROM users WHERE users.email = ?");
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            retBool = rs.next();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retBool;
    }

    public List<ShopList> getShoplistOverview(User user) {
        ArrayList<ShopList> retList = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st = conn.prepareStatement("SELECT subscribed_to.list_id FROM subscribed_to WHERE subscribed_to.user_id = ?");
            st.setInt(1, user.getUserID());
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                retList.add(getShopList(rs.getInt("list_id")));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retList;
    }

    public User checkCredentials(String email, String pw) {
        User ret = null;

        try(Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)){
            Class.forName("org.postgresql.Driver");

            PreparedStatement st = conn.prepareStatement("SELECT users.user_id, users.screen_name FROM users WHERE users.email = ? AND users.password = ?");
            st.setString(1, email);
            st.setString(2, pw);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                ArrayList<ShopList> lists = new ArrayList<>();
                int userid = rs.getInt("user_id");

                PreparedStatement st1 = conn.prepareStatement("SELECT subscribed_to.list_id FROM subscribed_to WHERE subscribed_to.user_id = ?");
                st1.setInt(1, userid);
                ResultSet rs1 = st.executeQuery();

                while (rs1.next()) {
                    lists.add(getShopList(rs1.getInt("list_id")));
                }

                ret = new User(rs.getString("screen_name"), email, userid, lists);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        // in case of mismatch, return a null object
        return ret;
    }

     */
}
