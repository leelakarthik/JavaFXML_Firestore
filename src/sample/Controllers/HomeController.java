package sample.Controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class HomeController implements Initializable {

    public JFXButton login;
    public JFXTextField Username;
    public Label labelForgotPassword;
    public JFXPasswordField Password;
    public ImageView CloseImg;
    public JFXButton SignUpForm;
    public Label CardLogin;
    private Firestore db1;

    private static String Uname = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        db = DbConnection.openConnection();

    }


    public void CloseAction(MouseEvent event){
        if(event.getSource()==CloseImg)
            System.exit(0);
    }
    /*
    public void insertDataToCloud_Firestore() throws ExecutionException, InterruptedException {
        // authentication to firebase and cloud-firestore.
        InputStream serviceAccount = new FileInputStream("javafx8-firebase-adminsdk-zvgdh-8bbb0cfa33.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
        Firestore db = FirestoreClient.getFirestore();

        String uname = Username.getText().toString();
        String upass = Password.getText().toString();
        String ufire = uname+"details";


        //creating collections
        CollectionReference colRef = db.collection("users").document(uname).collection(ufire);
        //creating documnents of user in collection
        DocumentReference docRef = colRef.document("Login");
        //using maps to create records to update in cloud-firestore
        Map<String, Object> data = new HashMap<>();
        data.put("username",uname);
        data.put("password",upass);
        //creates reference object to write data.
        ApiFuture<WriteResult> result = docRef.set(data);
        //write's data to cloud firestore
        result.get();
    }*/

    public void SignInButtonAction(MouseEvent mouseEvent) throws ExecutionException, InterruptedException, IOException {
        db1 = Main.ShareDB();
        String uname = Username.getText();
        String upass = Password.getText();
        String ufire = uname+"Details";
        //System.out.println(ufire);

        CollectionReference colRef = db1.collection("users").document(uname).collection(ufire);
        DocumentReference docRef = colRef.document("Account");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (document.exists()) {
            //System.out.println("Document data: " + document.getData());
            String unameF = document.getString("username");
            String upassF = document.getString("password");
            if(uname.equals(unameF) && upass.equals(upassF)){
                System.out.println("sucess");
                Uname = uname;
                alert.setTitle("Info");
                alert.setHeaderText("");
                alert.setContentText("Login Sucess");
                alert.showAndWait();

                Stage stage = (Stage) login.getScene().getWindow();
                stage.close();
                Stage SignUpStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Login.fxml"));
                SignUpStage.setTitle("Homepage");
                SignUpStage.initStyle(StageStyle.UNDECORATED);
                SignUpStage.setScene(new Scene(root));
                SignUpStage.show();

            }
            else {
                System.out.println("Invalid login credentials!");
                alert.setTitle("Login failed");
                alert.setHeaderText("");
                alert.setContentText("Invalid login credentials!");
                alert.showAndWait();
            }
            //System.out.println("Balance is: "+document.getLong("Balance"));
        }
        else {
            System.out.println("Invalid login credentials!");
            alert.setTitle("Login failed");
            alert.setHeaderText("");
            alert.setContentText("Invalid login credentials!");
            alert.showAndWait();
        }
    }

    public void SignUpButtonAction(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText("Every Filed is Mandatory");
        alert.setContentText("Fill all Fields");
        alert.showAndWait();

        Stage stage = (Stage) SignUpForm.getScene().getWindow();
        stage.close();
        Stage SignUpStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/SignUp.fxml"));
        SignUpStage.setTitle("SignUp");
        SignUpStage.initStyle(StageStyle.UNDECORATED);
        SignUpStage.setScene(new Scene(root));
        SignUpStage.show();
    }

    public void CardLoginAction(MouseEvent mouseEvent) throws IOException {

        Stage stage = (Stage) CardLogin.getScene().getWindow();
        stage.close();
        Stage SignUpStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/CHome.fxml"));
        SignUpStage.setTitle("Home");
        SignUpStage.initStyle(StageStyle.UNDECORATED);
        SignUpStage.setScene(new Scene(root));
        SignUpStage.show();
    }
    public static String UnameReturn(){
        return Uname;
    }
}
