import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.ExecutionException;

public class Loader extends Application {

    private static Firestore db;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Home.fxml"));
        primaryStage.setTitle("WELCOME");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    static Firestore ShareDB(){
        return db;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try{
            db = FirebaseConnection.openConnection();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        launch(args);
    }

}