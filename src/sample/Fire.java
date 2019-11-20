package sample;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.concurrent.ExecutionException;

public class Fire {
    public static void main(String[] args ) throws ExecutionException, InterruptedException {
        CollectionReference cc = Main.ShareDB().collection("users");//.document().collection("");
       // DocumentReference dd = cc
                        //.document("1234");
        Query query = cc.whereEqualTo("name", "jjj");
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            System.out.println("55555 "+ document.getId());
        }
        //System.out.println(querySnapshot);
    }
}
