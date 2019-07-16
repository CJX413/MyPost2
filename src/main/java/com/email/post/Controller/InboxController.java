package com.email.post.Controller;

import com.email.post.Data.UserData;
import com.email.post.Model.ReceiveModel;
import com.email.post.Tools.MyTools;
import com.email.post.Tools.ReceiveMailTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class InboxController {
    @FXML
    public ListView listView;
    @FXML
    public void handleMouseClick(MouseEvent arg) {
        String id=new String();
        String str=listView.getSelectionModel().getSelectedItem().toString();
        String s[] = str.split("---|、");
        id=s[1];
        Integer i = null;
        if(id!=null){
            i = Integer.parseInt(id);
        }
        ReceiveModel receiveModel= UserData.dao.selectByidR(i);
        new MyTools().Windowschange("Mail.fxml","已发邮件",850,700,receiveModel);
    }
    @FXML
    public void refresh() throws IOException {
        new ReceiveMailTools().receiveMail(UserData.USER_NAME,UserData.USER_PASSWORD);
        List<ReceiveModel> list = UserData.dao.selectallR(UserData.USER_NAME);
        ObservableList<String> data = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            data.add(i, "---" + list.get(i).getID() + "、" + list.get(i).getSender() + "    " + list.get(i).getTopical() + "    " + list.get(i).getReceiver());
        }
        listView.setItems(data);
    }
}
