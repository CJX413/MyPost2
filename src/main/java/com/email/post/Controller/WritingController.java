package com.email.post.Controller;


import com.email.post.Data.UserData;
import com.email.post.Model.PostModel;
import com.email.post.Tools.MyTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class WritingController {
    @FXML
    private TextField addressee;
    @FXML
    private TextField topical;
    @FXML
    private HTMLEditor content;
    @FXML
    public void sendemail(ActionEvent event){
        PostModel postModel=new PostModel();
        postModel.setReceiver(addressee.getText());
        postModel.setSender(UserData.USER_NAME);
        postModel.setTopical(topical.getText());
        postModel.setContent(content.getHtmlText());
        if(MyTools.isEmail(addressee.getText())) {
            try {
                Session session = MyTools.getSessionMail();
                Transport transport = session.getTransport();
                /* 3. 创建一封邮件 */
                // 3.1. 创建邮件对象
                MimeMessage message = new MimeMessage(session);
                // 3.2. From: 发件人
                message.setFrom(new InternetAddress(UserData.USER_NAME, "UTF-8"));
                // 3.3. To: 收件人
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressee.getText(), "UTF-8"));
                // 3.4. Subject: 邮件主题
                message.setSubject(topical.getText(), "UTF-8");
                // 3.5. Content: 邮件内容
                message.setContent(content.getHtmlText(), "text/html;charset=UTF-8");
            /*  使用 邮箱账号 和 密码 连接邮件服务器
                这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错*/
                transport.connect(UserData.USER_NAME, UserData.USER_PASSWORD);
                //发送邮件
                transport.sendMessage(message, message.getAllRecipients());
                // 关闭连接
                transport.close();
            } catch (NoSuchProviderException e) {
                //e.printStackTrace();
                System.out.println("发送失败！");
            } catch (MessagingException e) {
                //e.printStackTrace();
                System.out.println("发送失败！");
            } catch (UnsupportedEncodingException e) {
                //e.printStackTrace();
                System.out.println("发送失败！");
            }  catch (Exception e){
                System.out.println("发送失败！");
            }
            new MyTools().Windowschange("Warning.fxml","成功",415,247,"发送成功！！！");
            UserData.dao.addemail(postModel);
        } else {
            new MyTools().Windowschange("Warning.fxml","警告",415,247,"请输入正确的邮件格式！！！");
        }
    }
}
