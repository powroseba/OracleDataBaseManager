package com.database.ProjectDB.DAO;

import com.database.ProjectDB.Controller;
import com.database.ProjectDB.tools.WindowComponents;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by seba on 2017-05-08.
 */
@Getter
@Setter
public class ServiceDAO {
    public static boolean isConnected = false;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private static ServiceDAO ourInstance = new ServiceDAO();
    public static ServiceDAO getInstance() {
        return ourInstance;
    }
    private Controller controller = Controller.getControllerInstance();


    public void connectToDatabase() {
        WindowComponents.sendConsoleMessage("Connecting to database ...");
        entityManagerFactory = Persistence.createEntityManagerFactory("myDBConfiguration");
        entityManager = entityManagerFactory.createEntityManager();
        controller.getConnectDiode().setVisible(true);
        controller.getDisconnectDiode().setVisible(false);
        WindowComponents.sendConsoleMessage("Connected to database");
        isConnected = true;
    }

    public EntityManager beginTransaction(){
        WindowComponents.sendConsoleMessage("Transaction started");
        if (!entityManager.getTransaction().isActive()) entityManager.getTransaction().begin();
        return entityManager;

    }

    public void commitTransaction(){
        entityManager.getTransaction().commit();
        WindowComponents.sendConsoleMessage("Transaction commited");
    }


    public void closeConnection(){
        entityManager.close();
        entityManagerFactory.close();
        controller.getDisconnectDiode().setVisible(true);
        controller.getConnectDiode().setVisible(false);
        isConnected = false;
        WindowComponents.sendConsoleMessage("Disconnected from the database");
    }
}