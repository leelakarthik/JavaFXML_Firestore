package sample.Controllers;

import com.google.cloud.firestore.Firestore;
import com.jfoenix.controls.JFXButton;
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

public class LoginController implements Initializable {
    public ImageView CloseImg;
    public Label UsernameLabel;
    public JFXButton LogoutButton;
    private Firestore db = Main.ShareDB();
    private String Username = "";

    public void CloseAction(MouseEvent event){
        if(event.getSource()==CloseImg)
            System.exit(0);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Username = CHomeController.UnameReturn();
       // System.out.println(Username);
        if(Username.equals(""))
        Username = HomeController.UnameReturn();
        //System.out.println(Username);
        UsernameLabel.setText(Username);
    }

    public void LogoutButtonAction(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("LoggingOut");
        alert.setHeaderText("Are you Sure ?");
        alert.setContentText("");
        alert.showAndWait();

        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        stage.close();
        Stage LogoutStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Home.fxml"));
        LogoutStage.setTitle("Welcome");
        LogoutStage.initStyle(StageStyle.UNDECORATED);
        LogoutStage.setScene(new Scene(root));
        LogoutStage.show();
    }
    //    private ApiFuture<QuerySnapshot> querySnapshot = query.get();
}
