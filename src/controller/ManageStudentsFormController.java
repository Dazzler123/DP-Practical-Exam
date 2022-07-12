package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.StudentDTO;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageStudentsFormController {
    public JFXTextField txtID;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public JFXTextField txtNIC;

    public void txtSearchStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<StudentDTO> list = new ArrayList<>();
        ResultSet set = CrudUtil.execute("SELECT * FROM Student WHERE student_id = ?", txtID.getText());
        if (set.next()) {
            //confirmation alert
            new Alert(Alert.AlertType.CONFIRMATION,"Student exists.").show();
            list.add(
                    new StudentDTO(
                            set.getString("student_id"),
                            set.getString("student_name"),
                            set.getString("email"),
                            set.getString("contact"),
                            set.getString("address"),
                            set.getString("nic")
                    ));

            //set student's details to textfields
            setData(list);
        }else {
            //refresh textfields
            clearTextFields();

            //error alert
            new Alert(Alert.AlertType.ERROR,"No such Student found!").show();
        }
    }

    private void setData(ArrayList<StudentDTO> list){
        for(StudentDTO studentDTO : list){
            txtName.setText(studentDTO.getStudentName());
            txtEmail.setText(studentDTO.getEmail());
            txtContact.setText(studentDTO.getContact());
            txtAddress.setText(studentDTO.getAddress());
            txtNIC.setText(studentDTO.getNic());
        }
    }

    private void clearTextFields(){
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
        txtNIC.clear();
    }

    public void btnAddNewStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StudentDTO studentDTO = new StudentDTO(
                txtID.getText(),txtName.getText(),txtEmail.getText(),
                txtContact.getText(),txtAddress.getText(),txtNIC.getText()
        );

        //save student
        if(CrudUtil.execute("INSERT INTO Student VALUES (?,?,?,?,?,?)",
                studentDTO.getStudentID(),studentDTO.getStudentName(),
                studentDTO.getEmail(),studentDTO.getContact(),
                studentDTO.getAddress(),studentDTO.getNic())){
            //confirmation alert
            new Alert(Alert.AlertType.CONFIRMATION,"Student saved.").show();
        }
    }

    public void btnUpdateStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StudentDTO studentDTO = new StudentDTO(
                txtID.getText(),txtName.getText(),txtEmail.getText(),
                txtContact.getText(),txtAddress.getText(),txtNIC.getText()
        );

        //update student
        if(CrudUtil.execute("UPDATE Student SET student_name = ?, email = ?, contact = ?, address = ?, nic = ? WHERE student_id = ?",
                studentDTO.getStudentName(),studentDTO.getEmail(),
                studentDTO.getContact(),studentDTO.getAddress(),
                studentDTO.getNic(),studentDTO.getStudentID())){
            //confirmation alert
            new Alert(Alert.AlertType.CONFIRMATION,"Student details updated.").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }
    }

    public void btnDeleteStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(CrudUtil.execute("DELETE FROM Student WHERE student_id = ?",txtID.getText())){
            //confirmation alert
            new Alert(Alert.AlertType.CONFIRMATION,"Student deleted.").show();

            //refresh textfields
            clearTextFields();
        }else{
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }
    }
}
