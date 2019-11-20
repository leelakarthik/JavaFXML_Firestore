package sample.Controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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
import java.util.concurrent.ExecutionException;

public class CHomeController {
    public JFXTextField CcardNumber;
    public JFXPasswordField CPin;
    public Label ClabelForgotPin;
    public Label UsernameLogin;
    public JFXButton Clogin;
    public JFXButton CSignUpForm;
    public ImageView CloseImg;

    private Firestore db1;
    private static String Username = "";

    public void UsernameLoginAction(MouseEvent mouseEvent) throws IOException {

        Stage stage = (Stage) UsernameLogin.getScene().getWindow();
        stage.close();
        Stage SignUpStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Home.fxml"));
        SignUpStage.setTitle("Home");
        SignUpStage.initStyle(StageStyle.UNDECORATED);
        SignUpStage.setScene(new Scene(root));
        SignUpStage.show();
    }

    public void CSignInButtonAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException, IOException {

        db1 = Main.ShareDB();
        String ucard = CcardNumber.getText();
        String upin = CPin.getText();
        String ufire = ucard+upin;
        System.out.println(ufire);

        DocumentReference credenDocRef = db1.collection("Credentials").document(ufire);
        ApiFuture<DocumentSnapshot> future = credenDocRef.get();
        DocumentSnapshot document = future.get();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (document.exists()) {
            //System.out.println("Document data: " + document.getData());
            String ucardF = document.getString("cardNumber");
            String upinF = document.getString("pin");
            Username = document.getString("username");
            if (ucard.equals(ucardF) && upin.equals(upinF)) {
                System.out.println("sucess");
                alert.setTitle("Info");
                alert.setHeaderText("");
                alert.setContentText("Login Sucess");
                alert.showAndWait();

                Stage stage = (Stage) Clogin.getScene().getWindow();
                stage.close();
                Stage SignUpStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Login.fxml"));
                SignUpStage.setTitle("Homepage");
                SignUpStage.initStyle(StageStyle.UNDECORATED);
                SignUpStage.setScene(new Scene(root));
                SignUpStage.show();

            } else {
                System.out.println("Invalid login credentials!");
                alert.setTitle("Login failed");
                alert.setHeaderText("");
                alert.setContentText("Invalid login credentials!");
                alert.showAndWait();
            }
            //System.out.println("Balance is: "+document.getLong("Balance"));
        } else {
            System.out.println("Invalid login credentials!");
            alert.setTitle("Login failed");
            alert.setHeaderText("");
            alert.setContentText("Invalid login credentials!");
            alert.showAndWait();
        }
    }

    public void CSignUpButtonAction(ActionEvent actionEvent) throws IOException {
        Alert Ealert = new Alert(Alert.AlertType.ERROR);
        Ealert.setTitle("Warning");
        Ealert.setHeaderText("Every Filed is Mandatory");
        Ealert.setContentText("Fill all Fields");
        Ealert.showAndWait();

        Stage stage = (Stage) CSignUpForm.getScene().getWindow();
        stage.close();
        Stage SignUpStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/SignUp.fxml"));
        SignUpStage.setTitle("SignUp");
        SignUpStage.initStyle(StageStyle.UNDECORATED);
        SignUpStage.setScene(new Scene(root));
        SignUpStage.show();
    }
    public void CCloseAction(MouseEvent event){
        if(event.getSource()==CloseImg)
            System.exit(0);
    }
    public static String UnameReturn(){
        return Username;
    }

}
