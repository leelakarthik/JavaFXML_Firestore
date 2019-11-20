import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.concurrent.ExecutionException;

public class Test {
    private static Firestore db = Loader.ShareDB();
    public static void main(String[] args) {
        CollectionReference source = db.collection("users").document("Test").collection("TestDetails");
        //CollectionReference destination = db.collection("users").document("Test21").collection("Test21Details");
        ApiFuture<QuerySnapshot> api = source.get();
        int i=0;
        try {
            for (DocumentSnapshot ds: api.get()) {
                i++;
                System.out.println("Document" + i +":"+ ds.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
