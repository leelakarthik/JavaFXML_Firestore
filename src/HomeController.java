import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
    public JFXCheckBox ShowPassCheck;
    public JFXTextField ShowPassField;
    public Label ClabelForgotPin;
    public JFXButton Clogin;
    public JFXTextField CcardNumber;
    public JFXPasswordField CPin;
    public JFXButton CSignUpForm;
    public JFXTextField CShowPassField;
    public JFXCheckBox CShowPassCheck;
    public Label CUsernameLogin;
    public AnchorPane UPane;
    public AnchorPane CPane;
    private Firestore db1;
    private static String Uname = "";
    private static String UID = "";
    private static String Mnumber = "";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CloseImg.toFront();
    }


    public void CloseAction(MouseEvent event){

        if(event.getSource()==CloseImg){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exiting");
            alert.setHeaderText("");
            alert.setContentText("Application will be closed");
            Optional<ButtonType> bb = alert.showAndWait();
            if(bb.orElse(null)==ButtonType.OK) {
                System.exit(0);
            }
        }
    }
    public void SignInButtonAction(MouseEvent mouseEvent) throws ExecutionException, InterruptedException, IOException {

        UID = Username.getText();
        String upass = Password.getText();
        String ufire = UID + "Details";


        if (UID.isEmpty() || upass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Enter Both Username and Password");
            alert.showAndWait();
        } else {
            db1 = Loader.ShareDB();


            CollectionReference colRef = db1.collection("users").document(UID).collection(ufire);
            DocumentReference docRef = colRef.document("Account");
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (document.exists()) {
                //System.out.println("Document data: " + document.getData());
                String unameF = document.getString("username");
                String upassF = document.getString("password");
                Uname = document.getString("name");
                //UsernameCheck(UID);
                Mnumber = document.getString("mobileNumber");
                if (UID.equals(unameF) && upass.equals(upassF)) {
                    //Message_Mail.LoginMessage(Uname,Mnumber,0);
                    System.out.println("sucess");
                    alert.setTitle("Info");
                    alert.setHeaderText("");
                    alert.setContentText("Login Sucess");
                    alert.showAndWait();

                    Stage stage = (Stage) login.getScene().getWindow();
                    stage.close();
                    Stage SignUpStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Dashboard.fxml"));
                    SignUpStage.setTitle("Homepage");
                    SignUpStage.initStyle(StageStyle.UNDECORATED);
                    SignUpStage.setScene(new Scene(root));
                    SignUpStage.show();

                } else {
                    //Message_Mail.LoginMessage(Uname,Mnumber,1);
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

        CPane.setVisible(true);
        CPane.toFront();
        CloseImg.toFront();
        UPane.setVisible(false);
        /*
        Stage stage = (Stage) CardLogin.getScene().getWindow();
        stage.close();
        Stage SignUpStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/CHome.fxml"));
        SignUpStage.setTitle("Home");
        SignUpStage.initStyle(StageStyle.UNDECORATED);
        SignUpStage.setScene(new Scene(root));
        SignUpStage.show();

         */
    }
    public static String UnameReturn(){
        return Uname;
    }
    public static String UIDReturn(){
        return UID;
    }
    public void ShowPassAction(ActionEvent actionEvent) {

        ShowPassField.managedProperty().bind(ShowPassCheck.selectedProperty());
        ShowPassField.visibleProperty().bind(ShowPassCheck.selectedProperty());
        ShowPassField.textProperty().bindBidirectional(Password.textProperty());
    }

    public void CUsernameLoginAction(MouseEvent mouseEvent) {
        UPane.setVisible(true);
        UPane.toFront();
        CloseImg.toFront();
        CPane.setVisible(false);
    }

    public void CSignInButtonAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException, IOException {
        db1 = Loader.ShareDB();
        String ucard = CcardNumber.getText();
        String upin = CPin.getText();
        String ufire = ucard + upin;
        //System.out.println(ufire);

        if (ucard.isEmpty() || upin.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Enter Both Card Number and Pin");
            alert.showAndWait();
        } else {
            DocumentReference credenDocRef = db1.collection("Credentials").document(ufire);
            ApiFuture<DocumentSnapshot> future = credenDocRef.get();
            DocumentSnapshot document = future.get();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (document.exists()) {
                //System.out.println("Document data: " + document.getData());
                String ucardF = document.getString("cardNumber");
                String upinF = document.getString("pin");
                UID = document.getString("username");
                if (ucard.equals(ucardF) && upin.equals(upinF)) {
                    DocumentReference credenDocRef1 = db1.collection("users").document(UID).collection(UID + "Details").document("Account");
                    ApiFuture<DocumentSnapshot> future1 = credenDocRef1.get();
                    DocumentSnapshot document1 = future1.get();
                    Uname = document1.getString("name");
                    Mnumber= document1.getString("mobileNumber");
                    Message_Mail.LoginMessage(Uname,Mnumber,0);
                    System.out.println("sucess");
                    alert.setTitle("Info");
                    alert.setHeaderText("");
                    alert.setContentText("Login Sucess");
                    alert.showAndWait();

                    Stage stage = (Stage) Clogin.getScene().getWindow();
                    stage.close();
                    Stage Dashboard = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Dashboard.fxml"));
                    Dashboard.setTitle("Homepage");
                    Dashboard.initStyle(StageStyle.UNDECORATED);
                    Dashboard.setScene(new Scene(root));
                    Dashboard.show();

                } else {
                    Message_Mail.LoginMessage(Uname,Mnumber,1);
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

    public void CShowPassAction(ActionEvent actionEvent) {
        CShowPassField.managedProperty().bind(CShowPassCheck.selectedProperty());
        CShowPassField.visibleProperty().bind(CShowPassCheck.selectedProperty());
        CShowPassField.textProperty().bindBidirectional(CPin.textProperty());
    }

    public void ForgotpasswordAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) labelForgotPassword.getScene().getWindow();
        stage.close();
        Stage SignUpStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/PassPinReset.fxml"));
        SignUpStage.setTitle("");
        SignUpStage.initStyle(StageStyle.UNDECORATED);
        SignUpStage.setScene(new Scene(root));
        SignUpStage.show();
    }
}
