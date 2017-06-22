package com.database.ProjectDB.exceptions.repair;

import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;

/**
 * Created by seba on 2017-05-25.
 */
public class UpdateRepairException extends Exception {
    public UpdateRepairException(String info) {
        WindowComponents.createAlert(AlertType.WARNING, info);
        WindowComponents.sendConsoleMessage(" WARNING: " + info);
        return;
    }
}
