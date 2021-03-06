package com.database.ProjectDB.exceptions.order;

import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;

/**
 * Created by seba on 2017-05-25.
 */
public class DeleteOrderException extends Exception {
    public DeleteOrderException(String info) {
        WindowComponents.createAlert(AlertType.WARNING, info);
        WindowComponents.sendConsoleMessage(" WARNING: " + info);
        return;
    }
}
