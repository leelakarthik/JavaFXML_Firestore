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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class SignUpController implements Initializable {


    public ToggleGroup Gender;
    public ImageView closeimgsignup;
    public ToggleGroup MaritalStatus;
    public JFXComboBox ComboBoxReligion;
    public JFXComboBox ComboBoxIncome;
    public JFXComboBox ComboBoxState;
    public JFXComboBox ComboBoxCategory;
    public JFXComboBox ComboBoxEdu;
    public JFXButton BackButtonSignUp;
    public JFXButton SubmitButton;
    public JFXTextField Soccupation;
    public JFXTextField Sname;
    public JFXTextField Sfname;
    public JFXDatePicker Sdate;
    public JFXRadioButton Smale;
    public JFXRadioButton Sfemale;
    public JFXTextField Semail;
    public JFXRadioButton Smarried;
    public JFXRadioButton Ssingle;
    public JFXTextArea Saddress;
    public JFXTextField Scity;
    public JFXTextField Spincode;
    public JFXTextField Smobilenumber;
    public JFXTextField Spannumber;
    public JFXTextField Saadharnumber;
    public JFXTextField Susername;
    public JFXCheckBox ShowPass;
    public JFXPasswordField Spassword;
    public JFXCheckBox ShowPassCheck;
    public JFXTextField ShowPassField;

    private Firestore db = Loader.ShareDB();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ComboBoxReligion.setItems(FXCollections.observableArrayList("Hindu"));
        //.forEach(System.out::println);
        Smale.setSelectedColor(Color.valueOf("#ffce00") );
        Sfemale.setSelectedColor(Color.valueOf("#ffce00") );
        Ssingle.setSelectedColor(Color.valueOf("#ffce00") );
        Smarried.setSelectedColor(Color.valueOf("#ffce00") );
        //Smale.setSelectedColor( );
        ComboBoxReligion.getItems().add("Hindu");
        ComboBoxReligion.getItems().add("Muslim");
        ComboBoxReligion.getItems().add("Christian");
        ComboBoxReligion.getItems().add("Sikh");

        ComboBoxIncome.getItems().add("100000");
        ComboBoxIncome.getItems().add("<100000");
        ComboBoxIncome.getItems().add("50000");
        ComboBoxIncome.getItems().add("Prefer Not To Say");

        ComboBoxCategory.getItems().add("OC");
        ComboBoxCategory.getItems().add("BC");
        ComboBoxCategory.getItems().add("SC");
        ComboBoxCategory.getItems().add("ST");

        ComboBoxEdu.getItems().add("10th Class");
        ComboBoxEdu.getItems().add("Intermediate");
        ComboBoxEdu.getItems().add("B.Tech");
        ComboBoxEdu.getItems().add("M.Tech");
        ComboBoxEdu.getItems().add("PhD");
        ComboBoxEdu.getItems().add("Doctor");

        ComboBoxState.getItems().add("AndhraPradesh");
        ComboBoxState.getItems().add("Telangana");
        ComboBoxState.getItems().add("Tamilnadu");
        ComboBoxState.getItems().add("Maharastra");
    }

    /*public void CssRadio(){
        JFXRadioButton.getCssMetaData().stream().map(CssMetaData::getProperty);
    }
     */

    public void closeimgClicked(MouseEvent event) {
        if(event.getSource()==closeimgsignup){
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

    public void BackButtonAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) BackButtonSignUp.getScene().getWindow();
        stage.close();
        Stage MainStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Home.fxml"));
        MainStage.setTitle("WELCOME");
        MainStage.initStyle(StageStyle.UNDECORATED);
        MainStage.setScene(new Scene(root));
        MainStage.show();
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
            System.out.println("Yes");
        }
        else
            System.out.println("No");

        return check;
    }

    public void SubmitButtonAction(MouseEvent mouseEvent) throws ExecutionException, InterruptedException, IOException {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        int Check = 0;

        String Name = Sname.getText();
        String FName = Sfname.getText();
        String Email = Semail.getText();
        String Address = Saddress.getText();
        String City = Scity.getText();
        String Pincode = Spincode.getText();
        String Mobile = "+91" + Smobilenumber.getText();
        String Pan = Spannumber.getText();
        String Aadhar = Saadharnumber.getText();
        String Occupation = Soccupation.getText();
        String Username = Susername.getText();
        String Password = Spassword.getText();

        String ufire = Username + "Details";
        String State = "";
        String Religion = "";
        String Category = "";
        String Income = "";
        String EduQualification = "";
        try {
            State= ComboBoxState.getValue().toString();
            Religion = ComboBoxReligion.getValue().toString();
            Category = ComboBoxCategory.getValue().toString();
            Income = ComboBoxIncome.getValue().toString();
            EduQualification = ComboBoxEdu.getValue().toString();
        } catch (Exception e) {
            Check = 1;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String Date = "";
        if (Sdate.getValue() != null)
            Date = Sdate.getValue().format(formatter);

        boolean RMale = Smale.isSelected();
        boolean RFemale = Sfemale.isSelected();
        String Gender = "";
        if (RMale) {
            Gender = Smale.getText();
        }
        if (RFemale) {
            Gender = Sfemale.getText();
        }

        boolean RSingle = Ssingle.isSelected();
        boolean RMarried = Smarried.isSelected();
        String MaritalStatus = "";
        if (RSingle) {
            MaritalStatus = Ssingle.getText();
        }
        if (RMarried) {
            MaritalStatus = Smarried.getText();
        }

        if (Name.equals("") || Gender.equals("") || MaritalStatus.equals("") || Occupation.equals("") ||
                FName.equals("") || Email.equals("") ||
                City.equals("") || Address.equals("") ||
                Pincode.equals("") || Mobile.equals("") ||
                Pan.equals("") || Aadhar.equals("") ||
                Username.equals("") || Password.equals("")||Date.equals("")) {
            Check = 1;
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Some fields are missing");
            alert.showAndWait();
        }

        //System.out.println(ucheck);
        if (Check == 0) {
            Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
            aa.setTitle("Confirmation");
            aa.setHeaderText("Click OK to Create Account");
            Optional<ButtonType> bb = aa.showAndWait();
            if (bb.orElse(null) == ButtonType.OK) {
                int ucheck = UsernameCheck(Username);
                if(ucheck==0){
                    Random rand = new Random();
                    long AccountNumber;
                    AccountNumber = Math.round(rand.nextFloat() * Math.pow(10, 11));
                    AccountNumber += (2 * Math.pow(10, 11));

                    String AccNumber = "" + AccountNumber;

                    long card;
                    card = Math.round(rand.nextFloat() * Math.pow(10, 11));
                    card += (4 * Math.pow(10, 11));

                    String card_no = "" + card;

                    String pin = String.format("%04d", rand.nextInt(10000));

                    java.util.Date date = new Date();
                    long time = date.getTime();
                    Timestamp timestamp = new Timestamp(time);
                    ApiFuture<WriteResult> result;
                    CollectionReference colRef = db.collection("users").document(Username).collection(ufire);
                    DocumentReference Ucheck = db.collection("users").document(Username);
                    Map<String, Object> uncheck = new HashMap<>();
                    uncheck.put("username",Username);
                    result = Ucheck.set(uncheck);
                    result.get();
                    DocumentReference AccountdocRef = colRef.document("Account");
                    Map<String, Object> Adata = new HashMap<>();
                    Adata.put("name", Name);
                    Adata.put("username", Username);
                    Adata.put("password", Password);
                    Adata.put("accountNumber", AccNumber);
                    Adata.put("balance", 0);
                    Adata.put("cardNumber", card_no);
                    Adata.put("cardPin", pin);
                    Adata.put("mobileNumber", Mobile);
                    Adata.put("emailID", Email);
                    Adata.put("accountCreatedOn", timestamp);
                    Adata.put("profilePassword", "");
                    //creates reference object to write data.
                    result = AccountdocRef.set(Adata);
                    //write's data to cloud firestore
                    result.get();

                    DocumentReference PersonaldocRef = colRef.document("Personal");
                    Map<String, Object> Pedata = new HashMap<>();
                    Pedata.put("accountNumber", AccNumber);
                    Pedata.put("name", Name);
                    Pedata.put("fatherName", FName);
                    Pedata.put("DateOfBirth", Date);
                    Pedata.put("gender", Gender);
                    Pedata.put("maritalStatus", MaritalStatus);
                    Pedata.put("address", Address);
                    Pedata.put("mobileNumber", Mobile);
                    Pedata.put("city", City);
                    Pedata.put("pinCode", Pincode);
                    Pedata.put("state", State);
                    Pedata.put("emailID", Email);
                    Pedata.put("aadharNumber", Aadhar);
                    Pedata.put("panNumber", Pan);
                    result = PersonaldocRef.set(Pedata);
                    result.get();

                    DocumentReference AdditionaldocRef = colRef.document("Additional");
                    Map<String, Object> Ardata = new HashMap<>();
                    Ardata.put("accountNumber", AccNumber);
                    Ardata.put("education", EduQualification);
                    Ardata.put("occupation", Occupation);
                    Ardata.put("religion", Religion);
                    Ardata.put("category", Category);
                    Ardata.put("income", Income);
                    result = AdditionaldocRef.set(Ardata);
                    result.get();

                    DocumentReference credenDocRef = db.collection("Credentials").document(card_no + pin);
                    Map<String, Object> Cdata = new HashMap<>();
                    Cdata.put("cardNumber", card_no);
                    Cdata.put("pin", pin);
                    Cdata.put("username", Username);
                    result = credenDocRef.set(Cdata);
                    result.get();

                    Alert aaa = new Alert(Alert.AlertType.INFORMATION);
                    aaa.setTitle("Sucess");
                    aaa.setHeaderText("Account Created");
                    //aaa.setContentText("");
                    aaa.showAndWait();
                    Message_Mail.SignUpMessage(Name, Username, Mobile);
                    String msg = "Dear " + Name +
                            ", an account has been successfully created in the Leela_Projects with username:" + Username + "\n here are the card number : " +
                            card_no +
                            " and pin : " +
                            pin +
                            " For your account and" +
                            "YOU CAN ALSO LOGIN WITH CARD & PIN ,Give it a try.";

                    Message_Mail.Mail(Email, "Account Registration in LeelaProjects", msg);
                    msg = "Dear " + Name + " Set profile password for your account for security";
                    Message_Mail.Mail(Email, "Create Profile Password (LeelaProjects)", msg);
                    Stage stage = (Stage) SubmitButton.getScene().getWindow();
                    stage.close();
                    Stage MainStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Home.fxml"));
                    MainStage.setTitle("WELCOME");
                    MainStage.initStyle(StageStyle.UNDECORATED);
                    MainStage.setScene(new Scene(root));
                    MainStage.show();
                }
                else{
                    Alert ala = new Alert(Alert.AlertType.ERROR);
                    ala.setTitle("Error");
                    ala.setHeaderText("Try another Username");
                    ala.setContentText("Username already taken!");
                    ala.showAndWait();
                }
            }
        }
        else {
            Alert aaa = new Alert(Alert.AlertType.INFORMATION);
            aaa.setTitle("Failed");
            aaa.setHeaderText("Account Not Created");
            aaa.setContentText("Some fields are not filled");
            aaa.showAndWait();
        }
    }

    public void ShowPassAction(ActionEvent actionEvent) {
        ShowPassField.managedProperty().bind(ShowPassCheck.selectedProperty());
        ShowPassField.visibleProperty().bind(ShowPassCheck.selectedProperty());
        ShowPassField.textProperty().bindBidirectional(Spassword.textProperty());
    }
}
