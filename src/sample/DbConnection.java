package sample;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 *
 * @author LeelaKarthik
 */
class DbConnection {
    static Firestore openConnection(){
        InputStream serviceAccount;
        GoogleCredentials credentials = null;
        try {
            serviceAccount = new FileInputStream("lib/javafx8-firebase-adminsdk-zvgdh-8bbb0cfa33.json");

            credentials = GoogleCredentials.fromStream(serviceAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FirebaseOptions options = null;
        if (credentials != null) {
            options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
        }
        assert options != null;
        FirebaseApp.initializeApp(options);
        return FirestoreClient.getFirestore();
    }
}