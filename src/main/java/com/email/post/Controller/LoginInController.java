package com.email.post.Controller;


import com.email.post.Data.UserData;
import com.email.post.Model.PostModel;
import com.email.post.Model.ReceiveModel;
import com.email.post.Tools.MyTools;
import com.email.post.Tools.ReceiveMailTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class LoginInController {
    @FXML
    private AnchorPane inner;

    @FXML
    public void writing(ActionEvent event) throws IOException {
        Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("Writing .fxml"));
        inner.getChildren().setAll(root);
    }

    @FXML
    public void outbox() throws IOException {
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("Outbox.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        root = fxmlLoader.load();
        OutboxController outboxController = fxmlLoader.getController();
        List<PostModel> list = UserData.dao.selectall(UserData.USER_NAME);
        ObservableList<String> data = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            data.add(i, "---" + list.get(i).getID() + "、" + list.get(i).getSender() + "    " + list.get(i).getTopical() + "    " + list.get(i).getReceiver());
        }
        outboxController.listView.setItems(data);
        inner.getChildren().setAll(root);

    }

    @FXML
    public void inbox() throws IOException {
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("Inbox.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        root = fxmlLoader.load();
        InboxController inboxController = fxmlLoader.getController();
        List<ReceiveModel> list = UserData.dao.selectallR(UserData.USER_NAME);
        ObservableList<String> data = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            data.add(i, "---" + list.get(i).getID() + "、" + list.get(i).getSender() + "    " + list.get(i).getTopical() + "    " + list.get(i).getReceiver());
        }
        inboxController.listView.setItems(data);
        inner.getChildren().setAll(root);
    }

    @FXML
    public void logoff(ActionEvent event) {
        UserData.SMTP = "";
        UserData.USER_NAME = "";
        UserData.USER_PASSWORD = "";
        new MyTools().Windowschange("Main.fxml", "电子邮件系统", 600, 400);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
