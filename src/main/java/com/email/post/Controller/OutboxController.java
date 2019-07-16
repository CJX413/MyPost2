package com.email.post.Controller;

import com.email.post.Data.UserData;
import com.email.post.Model.PostModel;
import com.email.post.Tools.MyTools;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;


public class OutboxController {

    @FXML
    public ListView listView;
    @FXML
    public void handleMouseClick(MouseEvent arg) {
        String id=new String();
        String str=listView.getSelectionModel().getSelectedItem().toString();
        String s[] = str.split("---|、");
        id=s[1];
        System.out.println(str);
        System.out.println(id);
        Integer i = null;
        if(id!=null){
            i = Integer.parseInt(id);
        }
        PostModel postModel=UserData.dao.selectByid(i);
        new MyTools().Windowschange("Mail.fxml","已发邮件",850,700,postModel);

    }
}
