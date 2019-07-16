package com.email.post;

import com.email.post.Data.UserData;
import com.email.post.Model.PostModel;
import com.email.post.MyMapper.Dao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.sqlite.core.DB;

import java.util.List;

@SpringBootApplication
public class PostApplication extends Application {
    @Autowired
    private Dao dao;
    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("电子邮件系统");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Bean
    CommandLineRunner sampleCommandLineRunner() {
        return args -> {
            UserData.dao=this.dao;
            System.out.println(this.dao.selectall(UserData.USER_NAME));
        };
    }
}
