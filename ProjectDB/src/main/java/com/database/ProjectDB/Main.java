package com.database.ProjectDB;


import com.database.ProjectDB.DAO.ServiceDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by seba on 2017-05-08.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/firstUI.fxml"));

        fxmlLoader.setController(Controller.getControllerInstance());

        Parent root = (Parent) fxmlLoader.load();
        primaryStage.setTitle("Repair Service Manager");

        primaryStage.setScene(new Scene(root, 700, 660));


        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(e -> {
            if(ServiceDAO.isConnected){
                ServiceDAO service = ServiceDAO.getInstance();
                service.closeConnection();
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
