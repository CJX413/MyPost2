package com.email.post.Controller;


import com.email.post.Data.UserData;

import com.email.post.Tools.MyTools;
import com.email.post.Tools.ReceiveMailTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

public class MainController {
    @FXML
    private ChoiceBox SMTP;
    @FXML
    private TextField username;
    @FXML
    private TextField userpassword;
    @FXML
    private Label warning;
    @FXML
    public void login(ActionEvent event) throws IOException {
        String smtpType=new String();
        if(SMTP.getValue()!=null){
            smtpType=(String)SMTP.getValue();
        }
        if(smtpType.equals("")||username.getText().equals("")||userpassword.getText().equals("")){
            warning.setText("请输入全部信息！");
        }else{
            if (!MyTools.isEmail(username.getText())){
                warning.setText("请输入正确的邮件格式！");
            }else{
                if(!MyTools.check(smtpType,username.getText(),userpassword.getText())){
                    warning.setText("密码或账号错误！");
                }else {
                    UserData.SMTP=smtpType;
                    UserData.USER_NAME=username.getText();
                    UserData.USER_PASSWORD=userpassword.getText();
                    String s=smtpType.replaceAll("smtp.","");
                    UserData.POP3="pop."+s;
                    new ReceiveMailTools().receiveMail(UserData.USER_NAME,UserData.USER_PASSWORD);
                    new MyTools().Windowschange("LoginIn.fxml","电子邮件系统",900,600);
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                }
            }
        }

    }
    @FXML
    public void reset(){
        username.setText("");
        userpassword.setText("");
        warning.setText("");
    }
}
