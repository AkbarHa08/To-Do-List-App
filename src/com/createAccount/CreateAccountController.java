/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.createAccount;

import com.dao.Dao;
import com.dao.DaoImpl;
import com.model.Users;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Akubar
 */
public class CreateAccountController implements Initializable {
    
    DaoImpl dao = new DaoImpl();

    @FXML
    private Label createAccountWarningLbl;
    @FXML
    private Button SaveBtn;
    @FXML
    private Button CancelBtn;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField surnameTF;
    @FXML
    private TextField usernameTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField addressTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private PasswordField confirmPasswordTF;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void SaveBtnOnAction2(ActionEvent event) {
        String name = nameTF.getText().trim();
        String surname = surnameTF.getText().trim();
        String username = usernameTF.getText().trim();
        String password = passwordTF.getText().trim();
        String mail = emailTF.getText().trim();
        String address = addressTF.getText().trim();
        String confirmPassword = confirmPasswordTF.getText().trim();
        
        if (name.equalsIgnoreCase("") || surname.equalsIgnoreCase("") || username.equalsIgnoreCase("") ||
                password.equalsIgnoreCase("") || mail.equalsIgnoreCase("") || address.equalsIgnoreCase("") ||
                confirmPassword.equalsIgnoreCase("")) {
            createAccountWarningLbl.setText("Please fill in empty fields!");
        } else {
            if (password.equalsIgnoreCase(confirmPassword)) {
                if (dao.checkUserByUsername(username)) {
                    createAccountWarningLbl.setText("Username already exists!");
                } else {
                    Users user = new Users(0, name, surname, username, password, mail, address);
                    boolean isAdded = dao.createAccount(user);
                    if (isAdded) {
                        createAccountWarningLbl.setText("Succesfully created account!");
                        Stage oldStage = (Stage) SaveBtn.getScene().getWindow();
                        oldStage.close();
                    } else {
                        createAccountWarningLbl.setText("Not created account!");
                    }
                }
            } else {
                createAccountWarningLbl.setText("Invalid Password");
            }
        }
    }

    @FXML
    private void CancelBtnOnAction(ActionEvent event) {
        nameTF.setText("");
        surnameTF.setText("");
        usernameTF.setText("");
        passwordTF.setText("");
        confirmPasswordTF.setText("");
        emailTF.setText("");
        addressTF.setText("");
    }
    
}
