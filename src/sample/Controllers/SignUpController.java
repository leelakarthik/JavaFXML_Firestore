package sample.Controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.jfoenix.controls.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main;

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
    public JFXTextField Spassword;
    public JFXTextField Susername;

    private Firestore db;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public void closeimgClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == closeimgsignup)
            System.exit(0);
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

    public void SubmitButtonAction(MouseEvent mouseEvent) throws ExecutionException, InterruptedException, IOException {

        db = Main.ShareDB();

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


        // try {
        String State = ComboBoxState.getValue().toString();
         /*   Check = 0;
        } catch (Exception e) {
            Check = 1;
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Select State");
            alert.showAndWait();
        }*/
        //try {
        String Religion = ComboBoxReligion.getValue().toString();
          /*  Check = 0;
        } catch (Exception e) {
            Check = 1;
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Select Religion");
            alert.showAndWait();
        }*/

        //try {
        String Category = ComboBoxCategory.getValue().toString();
        /*    Check = 0;
        } catch (Exception e) {
            Check = 1;
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Select Category");
            alert.showAndWait();
        }*/

        // try {
        String Income = ComboBoxIncome.getValue().toString();
        //   Check = 0;
        /*
        } catch (Exception e) {
            Check = 1
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Select Income");
            alert.showAndWait();
        }*/

        //try {
        String EduQualification = ComboBoxEdu.getValue().toString();
         /* //  Check = 0;
        } catch (Exception e) {
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Select Qualification");
            alert.showAndWait();
        }*/

        String Date = "";
        if (Date.isEmpty()) {
            Check = 1;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            Date = (Sdate.getValue()).format(formatter);
        }

        boolean RMale = Smale.isSelected();
        boolean RFemale = Sfemale.isSelected();
        String Gender = "";
        if (RMale) {
            Gender = Smale.getText();
        }
        if (RFemale) {
            Gender = Sfemale.getText();
        }

        boolean RSingle = Smale.isSelected();
        boolean RMarried = Sfemale.isSelected();
        String MaritalStatus = "";
        if (RSingle) {
            MaritalStatus = Smale.getText();
        }
        if (RMarried) {
            MaritalStatus = Sfemale.getText();
        }

        if (Name.equals("") || Gender.equals("") || MaritalStatus.equals("") || Occupation.equals("") ||
                FName.equals("") || Email.equals("") ||
                City.equals("") || Address.equals("") ||
                Pincode.equals("") || Mobile.equals("") ||
                Pan.equals("") || Aadhar.equals("") ||
                Username.equals("") || Password.equals("")) {
            Check = 1;
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Some fields are missing");
            alert.showAndWait();
        }
        Optional<ButtonType> option = Optional.empty();
        if (Check == 0) {
            Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
            aa.setTitle("Confirmation");
            aa.setHeaderText("Click OK to Create Account");

            ButtonType OK = new ButtonType("OK");
            ButtonType CANCEL = new ButtonType("CANCEL");
            aa.getButtonTypes().clear();
            aa.getButtonTypes().addAll(OK,CANCEL);
            option = aa.showAndWait();
        }
        if (option.orElse(null) == ButtonType.OK) {
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


            CollectionReference colRef = db.collection("users").document(Username).collection(ufire);

            //creating documnents of user in collection
            DocumentReference AccountdocRef = colRef.document("Account");
            //using maps to create records to update in cloud-firestore
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
            //creates reference object to write data.
            ApiFuture<WriteResult> result;
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

            Stage stage = (Stage) SubmitButton.getScene().getWindow();
            stage.close();
            Stage MainStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Home.fxml"));
            MainStage.setTitle("WELCOME");
            MainStage.initStyle(StageStyle.UNDECORATED);
            MainStage.setScene(new Scene(root));
            MainStage.show();
        } else if (option.orElse(null) == ButtonType.CANCEL) {
            Alert aaa = new Alert(Alert.AlertType.INFORMATION);
            aaa.setTitle("Failed");
            aaa.setHeaderText("Account Not Created");
            aaa.showAndWait();

        }
    }
}
