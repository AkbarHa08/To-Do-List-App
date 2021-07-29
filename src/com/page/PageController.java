/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.page;

import com.dao.Dao;
import com.dao.DaoImpl;
import com.model.Tasks;
import com.model.Users;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class PageController implements Initializable {
    
    Integer selectedId = 0;
    
    @FXML
    private TextField taskTF;
    @FXML
    private TextField dayTF;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private Button addCategoryBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField searchTF;
    @FXML
    private TextField minimalDayTF;
    @FXML
    private TextField maximalDayTF;
    @FXML
    private Button dayFilterBtn;
    @FXML
    private Button clearAllFilterBtn;
    @FXML
    private Button solveBtn;
    @FXML
    private Button filterByStatusBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private CheckBox solvedCheckBox;
    @FXML
    private CheckBox notSolvedCheckBox;
    @FXML
    private CheckBox allCheckBox;
    @FXML
    private Button showBtn;
    @FXML
    private Button hideBtn;
    @FXML
    private Label welcomeLbl;
    @FXML
    private Button logOutBtn;
    @FXML
    private TableView<Tasks> tableView;
    @FXML
    private TableColumn<Tasks, Integer> idColumn;
    @FXML
    private TableColumn<Tasks, String> statusColumn;
    @FXML
    private Label warningLbl;
    @FXML
    private TableColumn<Tasks, String> taskColumn;
    @FXML
    private TableColumn<Tasks, Integer> dayColumn;
    @FXML
    private TableColumn<Tasks, String> dateColumn;
    @FXML
    private TableColumn<Tasks, String> categoryColumn;
    
    
    
    DaoImpl dao = new DaoImpl();
    
    private Users user;
    
    public void setUser(Users selectedUser){
        this.user = selectedUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCategoryCB();
        
        tableView.setVisible(false);
        loadColumn();
        loadRows();
    }    


    @FXML
    private void addCategoryBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        String newCategory = JOptionPane.showInputDialog(null, "New Category");
        if (newCategory.equalsIgnoreCase("")) {
            warningLbl.setText("Not added!Category is empty!");
        } else{
            if (!categoryComboBox.getItems().contains(newCategory)) {
                if (categoryComboBox.getItems().add(newCategory)) {
                    warningLbl.setText("Category successfully added!");
                }
                        
            } else {
                warningLbl.setText("Not Added!Category already exists!");
            }
        }
    }
    
    public void refresh(){
        tableView.getItems().clear();
        tableView.getItems().addAll(dao.getAllTasks());
    }

    @FXML
    private void saveBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        if (!taskTF.getText().equalsIgnoreCase("") && !dayTF.getText().equalsIgnoreCase("")) {
            try {
                Tasks newTask = new Tasks();
                newTask.setTask(taskTF.getText());
                newTask.setDay(Integer.parseInt(dayTF.getText()));
                newTask.setCategory(categoryComboBox.getSelectionModel().getSelectedItem());
                if (dao.addTask(newTask)) {
                    warningLbl.setText("Task successfully added!");
                    refresh();
                } else {
                    warningLbl.setText("Task not added!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            warningLbl.setText("Please, fill in empty fields!");
        }
    }

    @FXML
    private void updateBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        if (!taskTF.getText().equalsIgnoreCase("") && !dayTF.getText().equalsIgnoreCase("")) {
            try {
                Tasks task = new Tasks();
                task.setTask(taskTF.getText());
                task.setDay(Integer.parseInt(dayTF.getText()));
                task.setCategory(categoryComboBox.getSelectionModel().getSelectedItem());
                task.setId(selectedId);
                if (dao.updateTask(task)) {
                    warningLbl.setText("Task successfully updated!");
                    refresh();
                } else {
                    warningLbl.setText("Task Not Updated");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            warningLbl.setText("Please, fill in empty fields!");
        }
    }

    @FXML
    private void searchTFOnKeyReleased(KeyEvent event) {
        String keyword = searchTF.getText().toUpperCase().trim();
        if (keyword.equalsIgnoreCase("")) {
            refresh();
        } else {
            List resultList = dao.search(keyword);
            tableView.getItems().clear();
            tableView.getItems().addAll(resultList);
        }
    }

    @FXML
    private void dayFilterBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        if (!minimalDayTF.getText().trim().equalsIgnoreCase("") && !maximalDayTF.getText().trim().equalsIgnoreCase("")) {
            Integer minDay = Integer.parseInt(minimalDayTF.getText().trim());
            Integer maxDay = Integer.parseInt(maximalDayTF.getText().trim());
            List<Tasks> resultList = dao.filterByDay(minDay, maxDay);
            tableView.getItems().clear();
            tableView.getItems().addAll(resultList);
        } else {
            warningLbl.setText("Please fill in empty fields!");
        }
    }

    @FXML
    private void clearAllFilterBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        searchTF.setText("");
        minimalDayTF.setText("");
        maximalDayTF.setText("");
        refresh();
    }

    @FXML
    private void solveBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        if (dao.solveTask(selectedId)) {
            refresh();
            warningLbl.setText("Task successfully solved!");
        } else {
            warningLbl.setText("Not solved!");
        }
    }

    @FXML
    private void filterByStatusBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        if (solvedCheckBox.isSelected()) {
            List<Tasks> solvedTasks = dao.filterByStatus("SOLVED");
            tableView.getItems().clear();
            tableView.getItems().addAll(solvedTasks);
        }
        if (notSolvedCheckBox.isSelected()) {
            List<Tasks> notSolvedTasks = dao.filterByStatus("NOT SOLVED");
            tableView.getItems().clear();
            tableView.getItems().addAll(notSolvedTasks);
        }
        if (allCheckBox.isSelected()) {
            refresh();
        }
    }

    @FXML
    private void deleteBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        if (dao.deleteTask(selectedId)) {
            refresh();
            warningLbl.setText("Task successfully deleted!");
        } else {
            warningLbl.setText("Not deleted!");
        }
    }

    @FXML
    private void showBtnOnAction(ActionEvent event) {
        tableView.setVisible(true);
        welcomeLbl.setText("Welcome, "+user.getName()+" "+user.getSurname());
    }

    @FXML
    private void hideBtnOnAction(ActionEvent event) {
        tableView.setVisible(false);
    }

    @FXML
    private void logOutBtnOnAction(ActionEvent event) {
        warningLbl.setText("");
        try {
            int response = JOptionPane.showConfirmDialog(null, "Do you want to log out?");
            if (response == JOptionPane.YES_OPTION) {
                Stage stage1 = (Stage) logOutBtn.getScene().getWindow();
                stage1.close();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Log in Page");
                stage.getIcons().add(new Image("/com/image/login.png"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/login/Login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else{
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tableViewOnMousePressed(MouseEvent event) {
        Tasks task = tableView.getSelectionModel().getSelectedItem();
        taskTF.setText(task.getTask());
        dayTF.setText(""+task.getDay());
        categoryComboBox.getSelectionModel().select(task.getCategory());
        selectedId = task.getId();
    }

    private void loadColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadRows() {
        tableView.getItems().addAll(dao.getAllTasks());
    }

    private void loadCategoryCB() {
        List<String> category = new ArrayList<String>();
        categoryComboBox.getItems().add("Health");
        categoryComboBox.getItems().add("Work");
        categoryComboBox.getItems().add("Write");
        categoryComboBox.getItems().add("Read");
        categoryComboBox.getSelectionModel().select("Health");
    }
    
}
