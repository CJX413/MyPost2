package com.email.post.Tools;

import com.email.post.Controller.MailController;
import com.email.post.Controller.MainController;
import com.email.post.Controller.WarningController;
import com.email.post.Data.UserData;
import com.email.post.Model.PostModel;
import com.email.post.Model.ReceiveModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.mail.Session;
import javax.mail.Transport;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTools {
public static boolean check(String smtpType, String username, String userpassword ){
    //1、连接邮件服务器的参数配置
    Properties props = new Properties();
    //设置用户的认证方式
    props.setProperty("mail.smtp.auth", "true");
    //设置传输协议
    props.setProperty("mail.transport.protocol", "smtp");
    //设置发件人的SMTP服务器地址
    props.setProperty("mail.smtp.host", smtpType);
    Session session = Session.getDefaultInstance(props);
    try{
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(username, userpassword);
        //5、关闭邮件连接
        transport.close();
    } catch(Exception e) {
        e.printStackTrace();
        return false;
    }
    return true;
}
    public static boolean isEmail(String email) {
        if (email == null)
            return false;
        String rule = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(email);
        if (matcher.matches())
            return true;
        else
            return false;
    }
    public static Session getSessionMail() throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", UserData.SMTP);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        // 2. 创建会话
        Session session = Session.getDefaultInstance(props);
        return session;
    }

    public boolean Windowschange(String fxml, String title, int width, int height){
        Stage primaryStage=new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
            Scene scene = new Scene(root, width, height);
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean Windowschange(String fxml, String title, int width, int height, String s){
        Stage primaryStage=new Stage();
        Parent root = null;
        try {
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(fxml));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            root=fxmlLoader.load();
            WarningController warningController=fxmlLoader.getController();
            warningController.label.setText(s);
            Scene scene = new Scene(root, width, height);
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean Windowschange(String fxml, String title, int width, int height, PostModel model) {
        Stage primaryStage=new Stage();
        Parent root = null;
        try {
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(fxml));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            root=fxmlLoader.load();
            MailController mainController=fxmlLoader.getController();
            mainController.content.setHtmlText(model.getContent());
            mainController.topical.setText(model.getTopical());
            if(model.getReceiver().equals(UserData.USER_NAME)){
                mainController.addressee.setText(model.getSender());
            }else {
                mainController.addressee.setText(model.getReceiver());
            }
            Scene scene = new Scene(root, width, height);
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
