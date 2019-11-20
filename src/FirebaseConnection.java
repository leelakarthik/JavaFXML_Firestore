import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.InputStream;
import java.net.URL;

public class FirebaseConnection {
    static Firestore openConnection(){
        InputStream serviceAccount;
        FirebaseOptions options = null;
        GoogleCredentials credentials;
        try {
            //URL url = new URL("https://s3.ap-south-1.amazonaws.com/leela21/javafx8-firebase-adminsdk.json");
            URL url = new URL("https://javafx8.firebaseapp.com/javafx8-firebase-adminsdk.json");
            serviceAccount = url.openStream();
            credentials = GoogleCredentials.fromStream(serviceAccount);
            options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FirebaseApp.initializeApp(options);
        return FirestoreClient.getFirestore();
    }
}