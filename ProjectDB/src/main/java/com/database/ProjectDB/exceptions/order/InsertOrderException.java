package com.database.ProjectDB.exceptions.order;

import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;

/**
 * Created by seba on 2017-05-24.
 */
public class InsertOrderException extends Exception {
    public InsertOrderException(String info) {
        WindowComponents.createAlert(AlertType.WARNING, info);
        WindowComponents.sendConsoleMessage(" WARNING: " + info);
        return;
    }
}
