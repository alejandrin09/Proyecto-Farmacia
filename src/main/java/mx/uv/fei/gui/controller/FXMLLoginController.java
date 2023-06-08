/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.Status;
import mx.uv.fei.logic.UserDAO;
import mx.uv.fei.logic.User;
import mx.uv.fei.logic.UserHolder;

/**
 * FXML Controller class
 *
 * @author alexs
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private Button btnSignIn;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    private UserDAO userDAO = new UserDAO();
    private User user = new User();
    UserHolder userHolder = UserHolder.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtUser.setStyle("-fx-control-inner-background: #36AB9C;-fx-prompt-text-fill: black;");
        txtPassword.setStyle("-fx-control-inner-background: #36AB9C;-fx-prompt-text-fill: black;");
    }

    @FXML
    void btnSignInOnAction(ActionEvent event) {
        try {
            user.setEmail(txtUser.getText());
            user.setPassword(txtPassword.getText());
            
            userHolder.setUser(user);
            invokeAuthenticateUser(txtUser.getText(), txtPassword.getText());
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        } catch (IllegalArgumentException | IOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }

    @FXML
    void loginOnKeyPassword(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSignInOnAction(new ActionEvent());
        }
    }

    @FXML
    void loginOnKeyUser(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnSignInOnAction(new ActionEvent());
        }
    }

    private void invokeAuthenticateUser(String email, String password) throws DAOException, IOException {
        switch (userDAO.loginUserByType(email, password)) {
            case ADMINISTRATOR:
                App.openNewWindow("/fxml/FXMLHomeAdministrator.fxml");
                break;
            case EMPLOYEE:
                App.openNewWindow("/fxml/FXMLHomeEmployee.fxml");
                break;
            case CUSTOMER:
                App.openNewWindow("/fxml/FXMLHomeCustomer.fxml");
                break;
            default:
                DialogGenerator.getDialog("No se pudo abrir la interfaz del usuario", Status.FATAL);
                break;
        }
    }
}
