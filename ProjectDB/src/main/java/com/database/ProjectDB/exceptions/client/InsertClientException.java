package com.database.ProjectDB.exceptions.client;

import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;

/**
 * Created by seba on 2017-05-25.
 */
public class InsertClientException extends Exception {
    public InsertClientException(String info) {
        WindowComponents.createAlert(AlertType.WARNING, info);
        WindowComponents.sendConsoleMessage(" WARNING: " + info);
        return;
    }
}
