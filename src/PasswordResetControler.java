import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class PasswordResetControler implements Initializable {


    public AnchorPane PassPane;
    public ToggleGroup PassRadio;

    public JFXPasswordField newPassField;
    public JFXTextField reNewTextField;
    public JFXButton SetButton;
    public JFXButton cancelButton;
    public JFXPasswordField reNewPassField;
    public JFXTextField NewPassUnameTextField;
    public JFXRadioButton NewPassMobileRadio;
    public JFXRadioButton NewPassEmailRadio;
    public JFXTextField Mobile_Email_TextField;
    public JFXButton sendVerifyCode;
    public JFXTextField verifyCodeTextField;
    public JFXButton verifyCode;
    public JFXButton newPassBackButton;
    public AnchorPane setNewPasswordPane;
    public JFXRadioButton NewPassUnameRadio;
    public ToggleGroup PassSelectRadio;
    public JFXRadioButton NewPassCardNoRadio;

    private Firestore db = Loader.ShareDB();

    private String Avail;
    private String UID;
    private String verificationCode;
    private String one;
    private String two;
    private String Email;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewPassCardNoRadio.setSelectedColor(Color.valueOf("#ffce00") );
        NewPassEmailRadio.setSelectedColor(Color.valueOf("#ffce00") );
        NewPassUnameRadio.setSelectedColor(Color.valueOf("#ffce00") );
        NewPassMobileRadio.setSelectedColor(Color.valueOf("#ffce00") );
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        PassPane.setVisible(true);
        PassPane.toFront();
        setNewPasswordPane.setVisible(false);
        //CloseImg.toFront();
    }

    public void SetButtonAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        one = newPassField.getText();
        two = reNewPassField.getText();
        if(one.equals(two)){
            CollectionReference collectionReference = db.collection("users").document(UID).collection(UID + "Details");
            DocumentReference documentReference = collectionReference.document("Account");
            Map<String, Object> profilePassM = new HashMap<>();
            profilePassM.put("password", two);
            ApiFuture<WriteResult> result;
            result = documentReference.update(profilePassM);
            result.get();
            ApiFuture<DocumentSnapshot> a = documentReference.get();
            DocumentSnapshot dd = a.get();
            Email = dd.getString("emailID");
            String msg = "Dear "+UID+" you have sucessfully reset your Password.";
            Message_Mail.Mail(Email,"Password Reset",msg);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Password is reset!");
            alert.setContentText("");
            alert.showAndWait();

            setNewPasswordPane.setVisible(false);

        }
    }

    public void sendVerifyCodeAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        Random random = new Random();
        boolean RUname = NewPassUnameRadio.isSelected();
        boolean RCardNo = NewPassCardNoRadio.isSelected();
        String newPassUname = NewPassUnameTextField.getText();
        //System.out.println(newPassUname);
        //System.out.println(RCardNo);
        boolean RMobile = NewPassMobileRadio.isSelected();
        boolean REmail = NewPassEmailRadio.isSelected();
        String newPassRMobile = Mobile_Email_TextField.getText();
        if(!newPassUname.isEmpty()) {
            if (RUname) {
                int check = UsernameCheck(newPassUname);
                System.out.println(check);
                if (!newPassRMobile.isEmpty() && check == 1) {
                    verificationCode = String.format("%06d", random.nextInt(100000));
                    DocumentReference dd = db.collection("users").document(newPassUname).collection(newPassUname + "Details").document("Account");
                    if (RMobile) {
                        ApiFuture<DocumentSnapshot> af = dd.get();
                        DocumentSnapshot ds = af.get();
                        String Mobile = ds.getString("mobileNumber");
                        UID = ds.getString("username");
                        newPassRMobile = "+91" +newPassRMobile;
                        if (newPassRMobile.equals(Mobile)) {
                            Message_Mail.VerifyMessage(Mobile, newPassUname, verificationCode);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Sent");
                            alert.setHeaderText("Verification code set to Your Mobile Number");
                            alert.setContentText("Enter code below to reset password");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Invalid Mobile");
                            alert.setHeaderText("Mobile Number Not Matching");
                            alert.setContentText("");
                            alert.showAndWait();
                        }                    }
                    if (REmail) {
                        ApiFuture<DocumentSnapshot> af = dd.get();
                        DocumentSnapshot ds = af.get();
                        String Email = ds.getString("emailID");
                        if (newPassRMobile.equals(Email)) {
                            Message_Mail.VerifyEmail(Email, newPassUname, verificationCode);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Sent");
                            alert.setHeaderText("Verification code set to Your Email ID");
                            alert.setContentText("Enter code below to reset password");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Invalid EMail");
                            alert.setHeaderText("Email ID Not Matching");
                            alert.setContentText("");
                            alert.showAndWait();
                        }
                    }
                }
            }
            if(RCardNo){
                int check = CardNoCheck(newPassUname);
                System.out.println(check);
                if (!newPassRMobile.isEmpty() && check == 1) {
                    DocumentReference dd = db.collection("Credentials").document(Avail);
                    ApiFuture<DocumentSnapshot> af = dd.get();
                    DocumentSnapshot ds = af.get();
                    System.out.println(ds.getData());
                    UID = ds.getString("username");
                    //System.out.println("card "+card);
                    verificationCode = String.format("%06d", random.nextInt(100000));
                    System.out.println(verificationCode);
                    if (UID != null) {
                        dd = db.collection("users").document(UID).collection(UID + "Details").document("Account");
                    }
                    if (RMobile) {
                        af = dd.get();
                        ds = af.get();
                        String Mobile = ds.getString("mobileNumber");
                        newPassRMobile = "+91" +newPassRMobile;
                        if (newPassRMobile.equals(Mobile)) {
                            Message_Mail.VerifyMessage(Mobile, UID, verificationCode);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Sent");
                            alert.setHeaderText("Verification code set to Your Mobile Number");
                            alert.setContentText("Enter code below to reset password");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Invalid Mobile");
                            alert.setHeaderText("Mobile Number Not Matching");
                            alert.setContentText("");
                            alert.showAndWait();
                        }
                    }
                    if (REmail) {
                        af = dd.get();
                        ds = af.get();
                        Email = ds.getString("emailID");
                        if (newPassRMobile.equals(Email)) {
                            Message_Mail.VerifyEmail(Email, UID, verificationCode);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Sent");
                            alert.setHeaderText("Verification code set to Your Email ID");
                            alert.setContentText("Enter code below to reset password");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Invalid EMail");
                            alert.setHeaderText("Email ID Not Matching");
                            alert.setContentText("");
                            alert.showAndWait();
                        }
                    }
                }
            }
        }
    }

    private int UsernameCheck(String Username) throws ExecutionException, InterruptedException {
        int check = 0;
        DocumentReference docRef1 = db.collection("users").document(Username);//.collection(Username+"Details").document("Account");
        ApiFuture<DocumentSnapshot> future1 = docRef1.get();
        DocumentSnapshot document1 = future1.get();
        if (document1.exists()) {
            System.out.println("Document data: " + document1.getData());
            String u = document1.getString("username");
            if(u.equals(Username)){
                check = 1;
            }
            //System.out.println("Yes");
        }
        else
            System.out.println("No");
        return check;
    }

    private int CardNoCheck(String cardNo) throws ExecutionException, InterruptedException {
        int check = 0;
        CollectionReference docRef12 = db.collection("Credentials");
        Query query = docRef12.whereEqualTo("cardNumber", cardNo);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Avail = document.getId();
            if(!Avail.isEmpty())
                check = 1;
        }
        return check;
    }

    public void verifyCodeAction(ActionEvent actionEvent) {
        String code = verifyCodeTextField.getText();
        if(!code.isEmpty()){
        if(verificationCode.equals(code)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Verification code valid");
            alert.setContentText("");
            alert.showAndWait();
            setNewPasswordPane.setVisible(true);
            setNewPasswordPane.toFront();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setHeaderText("Verification code invalid");
            alert.setContentText("");
            alert.showAndWait();
        }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setHeaderText("Please enter verification code");
            alert.setContentText("");
            alert.showAndWait();
        }
    }

    public void newPassBackButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) newPassBackButton.getScene().getWindow();
        stage.close();
        Stage SignUpStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Home.fxml"));
        SignUpStage.setTitle("WELCOME");
        SignUpStage.initStyle(StageStyle.UNDECORATED);
        SignUpStage.setScene(new Scene(root));
        SignUpStage.show();
    }

    public void emailRadio(ActionEvent actionEvent) {
        Mobile_Email_TextField.setPromptText("Enter Email ID");
    }

    public void cardRadio(ActionEvent actionEvent) {
        NewPassUnameTextField.setPromptText("Enter Card Number");
    }

    public void mobileRadio(ActionEvent actionEvent) {
        Mobile_Email_TextField.setPromptText("Enter Mobile Number");
    }

    public void nameRadio(ActionEvent actionEvent) {
        NewPassUnameTextField.setPromptText("Enter Username");
    }
}
