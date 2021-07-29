
package com.login;

import com.dao.DaoImpl;
import com.model.Users;
import com.page.PageController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameTF;
    @FXML
    private Label loginWarningLbl;
    @FXML
    private Button loginBtn;
    @FXML
    private Button CreateAccountBtn;
    @FXML
    private Label warningUsernameLbl;
    @FXML
    private Label warningPasswordLbl;
    @FXML
    private PasswordField passwordTF;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void usernameTFonKeyReleased(KeyEvent event) {
        loginWarningLbl.setText("");
        if (usernameTF.getText().trim().equalsIgnoreCase("")) {
            warningUsernameLbl.setVisible(true);
        } else {
            warningUsernameLbl.setVisible(false);
            if (usernameTF.getText().trim().contains(",")) {
                loginWarningLbl.setText("Icerisinde vergul var!");
                warningUsernameLbl.setVisible(true);
            }
            if (usernameTF.getText().trim().contains("!")) {
                loginWarningLbl.setText("Icerisinde nida var!");
                warningUsernameLbl.setVisible(true);
            }
        }
    }
    
    DaoImpl dao = new DaoImpl();

    @FXML
    private void loginBtnOnAction(ActionEvent event) {
        String username = usernameTF.getText().trim();
        String password = passwordTF.getText().trim();
        if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            loginWarningLbl.setText("Please,fill in empty fields");
        }else{
            Users user = dao.checkUser(username, password);
            if (user == null) {
                loginWarningLbl.setText("Invalid username or password!!!");
            }else{
                try {
                    Stage s = new Stage();
                    s.setTitle("Main Page");
                    s.getIcons().add(new Image("/com/image/mainPage.png"));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/page/Page.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    s.setScene(scene);
                    s.show();
                    PageController pc = loader.getController();
                    pc.setUser(user);
                    
                    Stage oldStage = (Stage) loginBtn.getScene().getWindow();
                    oldStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void CreateAccountBtnOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Create Account Page");
            stage.getIcons().add(new Image("/com/image/createIcon.png"));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/createAccount/CreateAccount.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void passwordTFonKeyReleased(KeyEvent event) {
        loginWarningLbl.setText("");
        if (passwordTF.getText().trim().equalsIgnoreCase("")) {
            warningPasswordLbl.setVisible(true);
        } else {
            warningPasswordLbl.setVisible(false);
            if (passwordTF.getText().trim().contains(",")) {
                loginWarningLbl.setText("Icerisinde vergul var!");
                warningPasswordLbl.setVisible(true);
            }
            if (passwordTF.getText().trim().contains("!")) {
                loginWarningLbl.setText("Icerisinde nida var!");
                warningPasswordLbl.setVisible(true);
            }
        }
    }
    
}
