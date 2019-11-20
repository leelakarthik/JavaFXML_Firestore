import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import java.util.*;
import java.util.concurrent.ExecutionException;

public class DashboardController implements Initializable {
    public ImageView CloseImg;
    public JFXButton LogoutButton;
    public JFXButton LoginTransactions;
    public JFXButton LoginHome;
    public JFXButton LoginAccountDetails;
    public JFXButton LoginBalance;
    public Label UsernameLabel;
    public AnchorPane HomePane;
    public AnchorPane AccountPane;
    public AnchorPane TransactionPane;
    public AnchorPane BalancePane;
    public AnchorPane FundPane;
    public AnchorPane SidePane;
    public JFXButton LoginFund;
    public Label usernameLabel;
    public Label profilePasswordLabel;
    public AnchorPane ProfilePasswordPane;
    public JFXButton setProfilePasswordButton;
    public JFXPasswordField reProfilePasswordField;
    public JFXCheckBox showProfilePasswordCheck;
    public JFXPasswordField profilePasswordField;
    public ImageView PPCloseimage;
    public JFXTextField ShowPassField;
    public JFXButton viewDetailsButton;
    public JFXButton DeleteAccountButton;
    public JFXButton updateDetailsButton;
    public JFXButton ResetPasswordButton;
    public AnchorPane viewDetailsPane;
    public JFXTextField Sname;
    public JFXTextField Sfname;
    public JFXTextField SGender;
    public JFXTextField SDOB;
    public JFXTextField SMarital;
    public JFXTextField SAddress;
    public JFXTextField Scity;
    public JFXTextField Sstate;
    public JFXTextField Spincode;
    public JFXTextField Smobilenumber;
    public JFXTextField Semail;
    public JFXTextField Saadharnumber;
    public JFXTextField Spannumber;
    public JFXTextField SCategory;
    public JFXTextField SReligion;
    public JFXTextField Soccupation;
    public JFXTextField SIncome;
    public JFXTextField SEducation;
    public JFXTextField Susername;
    public JFXButton BackViewDetailsButton;
    public JFXTextField SCardNumber;
    public JFXTextField SAccountNumber;
    public AnchorPane ProfilePassVerifyPane;
    public JFXButton verifyProfilePassButton;
    public JFXPasswordField VProfilePassPassField;
    public AnchorPane updateDetailsPane;
    public JFXTextField newUsername;
    public JFXTextField newMobile;
    public JFXTextField newEmail;
    public JFXTextField newVerifyCode;
    public JFXButton updateVerifyButton;
    public JFXButton SendVcodeButton;
    private Firestore db = Loader.ShareDB();
    private String Username = "";
    private String UID = "";
    private int vv = 1;
    private int viewDetails = 1;
    private String verificationCode;

    public void CloseAction(MouseEvent event) throws IOException {
        if(event.getSource()==CloseImg) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exiting");
            alert.setHeaderText("");
            alert.setContentText("Application will be closed");
            //alert.showAndWait();
            Optional<ButtonType> bb = alert.showAndWait();
            if(bb.orElse(null)==ButtonType.OK) {
                System.exit(0);
            }
        }
        else if (event.getSource()==PPCloseimage){
            reProfilePasswordField.setText("");
            profilePasswordField.setText("");
            ProfilePasswordPane.setVisible(false);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UID = HomeController.UIDReturn();
        Username = HomeController.UnameReturn();
        UsernameLabel.setText(Username);
        usernameLabel.setText(Username);
    }

    private void LogoutButtonAction() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LoggingOut");
        alert.setHeaderText("Are you Sure ?");
        alert.setContentText("");
        Optional<ButtonType> bb = alert.showAndWait();

        if(bb.orElse(null)==ButtonType.OK) {
            Stage stage = (Stage) LogoutButton.getScene().getWindow();
            stage.close();
            Stage LogoutStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Home.fxml"));
            LogoutStage.setTitle("Welcome");
            LogoutStage.initStyle(StageStyle.UNDECORATED);
            LogoutStage.setScene(new Scene(root));
            LogoutStage.show();
        }
    }

    private void LoginAccountDetailsAction() {
        viewDetails = 0;
        ProfilePassVerifyPane.setVisible(true);
        ProfilePassVerifyPane.toFront();
        CloseImg.toFront();
    }

    private void LoginHomeAction() {
        HomePane.toFront();
        CloseImg.toFront();
    }

    private void LoginTransactionsAction() {
        ProfilePassVerifyPane.setVisible(true);
        ProfilePassVerifyPane.toFront();
        CloseImg.toFront();
    }

    private void LoginBalanceAction() {
        BalancePane.setVisible(true);
        BalancePane.toFront();
        CloseImg.toFront();
    }

    private void LoginFundAction() {
        FundPane.setVisible(true);
        FundPane.toFront();
        CloseImg.toFront();
    }

    public void handleLBoardButtons(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        if(actionEvent.getSource() == LoginHome) {
            ProfilePassVerifyPane.setVisible(false);
            LoginHomeAction();
        }
        if(actionEvent.getSource() == LoginAccountDetails) {
            LoginAccountDetailsAction();
        }
        if(actionEvent.getSource() == LoginTransactions) {
            LoginTransactionsAction();
        }
        if(actionEvent.getSource() == LoginFund) {
            LoginFundAction();
        }
        if(actionEvent.getSource() == LoginBalance) {
            LoginBalanceAction();
        }
        if(actionEvent.getSource() == LogoutButton){
            LogoutButtonAction();
        }
        if(actionEvent.getSource() == showProfilePasswordCheck){
            if(profilePasswordField.getText().equals(reProfilePasswordField.getText())){
                ShowPassField.managedProperty().bind(showProfilePasswordCheck.selectedProperty());
                ShowPassField.visibleProperty().bind(showProfilePasswordCheck.selectedProperty());
                ShowPassField.textProperty().bindBidirectional(reProfilePasswordField.textProperty());
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Passwords not matching");
                alert.setContentText("");
                alert.showAndWait();
                ShowPassField.managedProperty().bind(showProfilePasswordCheck.selectedProperty());
                ShowPassField.visibleProperty().bind(showProfilePasswordCheck.selectedProperty());
                ShowPassField.textProperty().bindBidirectional(reProfilePasswordField.textProperty());
            }
        }
        if(actionEvent.getSource() == setProfilePasswordButton){
            if(!profilePasswordField.getText().isEmpty() && !reProfilePasswordField.getText().isEmpty())
                setProfilePasswordButton();
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Password fields can't be empty");
                alert.setContentText("enter password to continue");
                alert.showAndWait();
            }
        }
        if(actionEvent.getSource() == DeleteAccountButton){
            if(viewDetails == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Seesion Expired");
                alert.setHeaderText("Re-directing to Home");
                alert.showAndWait();
                LoginHomeAction();
            }
            if(viewDetails == 2) {
                viewDetails = 1;
                DeleteAccount();
                Stage stage = (Stage) DeleteAccountButton.getScene().getWindow();
                stage.close();
                Stage SignUpStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Home.fxml"));
                SignUpStage.setTitle("Homepage");
                SignUpStage.initStyle(StageStyle.UNDECORATED);
                SignUpStage.setScene(new Scene(root));
                SignUpStage.show();
            }
        }
        if(actionEvent.getSource()==viewDetailsButton){
            if(viewDetails == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Seesion Expired");
                alert.setHeaderText("Re-directing to Home");
                //alert.setContentText("");
                alert.showAndWait();
                LoginHomeAction();
            }
            if(viewDetails == 2) {
                viewDetails = 1;
                viewDetailsPane.setVisible(true);
                viewDetailsAction();
            }
        }
        if(actionEvent.getSource()==verifyProfilePassButton) {
            if (ProfilePassVerifyPane.isVisible()) {
                vv = VerifyProfilePass();
                if (vv == 0) {
                    ProfilePassVerifyPane.setVisible(false);
                    VProfilePassPassField.setText("");
                    if (viewDetails == 0)
                        viewDetails = 2;
                }
                if (viewDetails == 2) {
                    AccountPane.setVisible(true);
                    AccountPane.toFront();
                    CloseImg.toFront();
                }
            }
        }
        if(actionEvent.getSource()==BackViewDetailsButton){
            viewDetailsPane.setVisible(false);
        }
        if(actionEvent.getSource()==updateDetailsButton){
            if(viewDetails == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Session Expired");
                alert.setHeaderText("Re-directing to Home");
                //alert.setContentText("");
                alert.showAndWait();
                LoginHomeAction();
            }
            if(viewDetails == 2) {
                viewDetails = 1;
                updateDetailsPane.setVisible(true);
            }
        }
        if(actionEvent.getSource()==SendVcodeButton){
            Random random = new Random();
            verificationCode = String.format("%06d", random.nextInt(100000));
            DocumentReference dd = db.collection("users").document(UID).collection(UID + "Details").document("Account");
            ApiFuture<DocumentSnapshot> af = dd.get();
            DocumentSnapshot ds = af.get();
            Message_Mail.VerifyMessage(ds.getString("mobileNumber"),UID, verificationCode);
        }
        if(actionEvent.getSource()==updateVerifyButton){
            updateDetailsAction();
        }
    }

    private void setProfilePasswordButton() throws ExecutionException, InterruptedException {
        String profilePass = profilePasswordField.getText();
        String reProfilePass = reProfilePasswordField.getText();
        if(profilePass.equals(reProfilePass)) {
            CollectionReference collectionReference = db.collection("users").document(UID).collection(UID + "Details");
            DocumentReference documentReference = collectionReference.document("Account");

            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();
            String proPass = "";
            String loPass = "";
            String Email = "";
            if (document.exists()){
                proPass = document.getString("profilePassword");
                loPass = document.getString("password");
                Email = document.getString("emailID");
            }
            //System.out.println("Profile"+proPass);
            //System.out.println("login"+loPass);
            if(profilePass.equals(loPass)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("You can't set same, Login and Profile Passwords!!");
                alert.setContentText("Kindly set different password");
                alert.showAndWait();
                profilePasswordField.setText("");
                reProfilePasswordField.setText("");
            }
            else {
                if (proPass.length() == 0) {
                    Map<String, Object> profilePassM = new HashMap<>();
                    profilePassM.put("profilePassword", profilePass);
                    ApiFuture<WriteResult> result;
                    result = documentReference.update(profilePassM);
                    result.get();
                    String msg = "Dear "+document.getString("name")+" you have sucessfully set Profile Password.";
                    Message_Mail.Mail(Email,"Profile Password",msg);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Profile Password is Set!");
                    alert.setContentText("");
                    alert.showAndWait();

                    profilePasswordField.setText("");
                    reProfilePasswordField.setText("");

                    ProfilePasswordPane.setVisible(false);

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Profile Password is already set!!!");
                    alert.setContentText("You can't set profile password here again!");
                    alert.showAndWait();
                    profilePasswordField.setText("");
                    reProfilePasswordField.setText("");
                }
            }
        }
    }

    private int VerifyProfilePass() throws ExecutionException, InterruptedException {
        DocumentReference doc = db.collection("users").document(UID).collection(UID+"Details").document("Account");
        ApiFuture<DocumentSnapshot> af = doc.get();
        DocumentSnapshot ds = af.get();
        String dbpass = ds.getString("profilePassword");
        String pass = VProfilePassPassField.getText();
        System.out.println("ONE "+dbpass+" TWO "+pass);
        int ch = 1 ;
        if(!pass.isEmpty()) {
            if (dbpass.equals(pass)) {
                ch = 0;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Verification");
                alert.setHeaderText("Profile Password Valid");
                //alert.setContentText("");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Verification");
                alert.setHeaderText("Profile Password Not-valid");
                alert.setContentText("Re-enter profile password");
                alert.showAndWait();
                VProfilePassPassField.setText("");
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Enter Profile Password");
            alert.setContentText("Password field can't be empty");
            alert.showAndWait();
        }
        return ch;
    }

    public void MouseClickEvents(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == profilePasswordLabel){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("You can set Profile Password Once here!");
            alert.setContentText("");
            alert.showAndWait();
            profilePasswordField.setText("");
            reProfilePasswordField.setText("");
            ProfilePasswordPane.setVisible(true);
            ProfilePasswordPane.toFront();
        }
    }

    private void viewDetailsAction() throws ExecutionException, InterruptedException {
        CollectionReference col = db.collection("users").document(UID).collection(UID+"Details");
        DocumentReference doc = col.document("Account");
        ApiFuture<DocumentSnapshot> af = doc.get();
        DocumentSnapshot ds = af.get();
        Sname.setText(ds.getString("name"));
        SAccountNumber.setText(ds.getString("accountNumber"));
        SCardNumber.setText(ds.getString("cardNumber"));
        Semail.setText(ds.getString("emailID"));
        Smobilenumber.setText(ds.getString("mobileNumber"));
        Susername.setText(ds.getString("username"));
        doc = col.document("Additional");
        af = doc.get();
        ds = af.get();
        SCategory.setText(ds.getString("category"));
        SEducation.setText(ds.getString("education"));
        SIncome.setText(ds.getString("income"));
        Soccupation.setText(ds.getString("occupation"));
        SReligion.setText(ds.getString("religion"));
        doc = col.document("Personal");
        af = doc.get();
        ds = af.get();
        SDOB.setText(ds.getString("DateOfBirth"));
        Saadharnumber.setText(ds.getString("aadharNumber"));
        SAddress.setText(ds.getString("address"));
        Scity.setText(ds.getString("city"));
        SGender.setText(ds.getString("gender"));
        SMarital.setText(ds.getString("maritalStatus"));
        Spannumber.setText(ds.getString("panNumber"));
        Sstate.setText(ds.getString("state"));
        Spincode.setText(ds.getString("pinCode"));

    }

    private void DeleteAccount() throws ExecutionException, InterruptedException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting Account");
        alert.setHeaderText("Are you Sure ?");
        alert.setContentText("");
        Optional<ButtonType> bb = alert.showAndWait();
        if (bb.orElse(null) == ButtonType.OK) {
            CollectionReference collection = db.collection("users").document(UID).collection(UID + "Details");
            ApiFuture<DocumentSnapshot> af = collection.document("Account").get();
            DocumentSnapshot ds = af.get();
            String docID = ds.getString("cardNumber") + ds.getString("cardPin");
            ApiFuture<WriteResult> writeResult = db.collection("Credentials").document(docID).delete();
            writeResult.get();
            int batchSize = 1;
            DeleteAccountAction(collection, batchSize);
            Alert aler = new Alert(Alert.AlertType.INFORMATION);
            aler.setTitle("Deleted Account");
            aler.setHeaderText("Feeling lonely!! comeback!! :( :(");
            aler.setContentText("Hope you join us soon!");
            aler.showAndWait();
        }
        else {
            Alert aler = new Alert(Alert.AlertType.INFORMATION);
            aler.setTitle("Account not Deleted");
            aler.setHeaderText("Thanks for your presence");
            aler.setContentText("Feeling happy, your presesnce");
            aler.showAndWait();
        }
    }
    private void DeleteAccountAction(CollectionReference collection, int batchSize) throws ExecutionException, InterruptedException {
        try {
            ApiFuture<QuerySnapshot> future = collection.limit(batchSize).get();
            int deleted = 0;
            // future.get() blocks on document retrieval
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                document.getReference().delete();
                ++deleted;
            }
            if (deleted >= batchSize) {
                // retrieve and delete another batch
                DeleteAccountAction(collection, batchSize);
            }
        } catch (Exception e) {
            System.err.println("Error deleting collection : " + e.getMessage());
        }
        ApiFuture<WriteResult> writeResult = db.collection("users").document(UID).delete();
        writeResult.get();
    }

    private void updateDetailsAction() {
        String vcode = newVerifyCode.getText();
        String cardPin = "";
        String newUID = newUsername.getText();
        Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
        aler.setTitle("Updating Account details");
        aler.setHeaderText("Are you Sure ?");
        aler.setContentText("click ok to proceed");
        Optional<ButtonType> bb = aler.showAndWait();
        if (bb.orElse(null) == ButtonType.OK) {
            if (!vcode.isEmpty()) {
                if (vcode.equals(verificationCode)) {
                    CollectionReference source = db.collection("users").document(UID).collection(UID + "Details");
                    CollectionReference destination = db.collection("users").document(newUID).collection(newUID + "Details");

                    DocumentReference pp = db.collection("users").document(newUID);
                    DocumentReference p = destination.document("Account");

                    ApiFuture<WriteResult> result;
                    ApiFuture<WriteResult> result1;
                    ApiFuture<WriteResult> result2;
                    ApiFuture<QuerySnapshot> api = source.get();
                    ApiFuture<DocumentSnapshot> cp = p.get();

                    DocumentSnapshot cpp = null;

                    Map<String, Object> profilePassM = new HashMap<>();
                    profilePassM.put("username", newUID);

                    try {
                        for (DocumentSnapshot ds : api.get()) {
                            DocumentReference ff = destination.document(ds.getId());
                            ff.set(ds.getData());
                        }
                        cpp = cp.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (cpp != null)
                        cardPin = cpp.getString("cardNumber") + cpp.getString("cardPin");
                    System.out.println(cardPin);
                    DocumentReference ppp = db.collection("Credentials").document(cardPin);

                    result = p.update(profilePassM);
                    result1 = pp.set(profilePassM, SetOptions.merge());
                    result2 = ppp.update("username", newUID);

                    try {
                        result.get();
                        result1.get();
                        result2.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Updating Details");
                    alert.setHeaderText("Re-directing to Home");
                    alert.setContentText("Sucessfully Updated Details");
                    alert.showAndWait();
                    LoginHomeAction();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Verification Code Error");
                alert.setHeaderText("Code can't be empty");
                alert.setContentText("Please Enter Verification Code");
                alert.showAndWait();
            }
        }
    }

    public void ResetPasswordAction(ActionEvent actionEvent) {
    }

    /*
    public void showProfilePasswordCheckAction(ActionEvent actionEvent) {
        if(profilePasswordField.getText().equals(reProfilePasswordField.getText())){
        ShowPassField.managedProperty().bind(showProfilePasswordCheck.selectedProperty());
        ShowPassField.visibleProperty().bind(showProfilePasswordCheck.selectedProperty());
        ShowPassField.textProperty().bindBidirectional(reProfilePasswordField.textProperty());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Passwords not matching");
            alert.setContentText("");
            alert.showAndWait();
            ShowPassField.managedProperty().bind(showProfilePasswordCheck.selectedProperty());
            ShowPassField.visibleProperty().bind(showProfilePasswordCheck.selectedProperty());
            ShowPassField.textProperty().bindBidirectional(reProfilePasswordField.textProperty());
        }
    }
     */
}
