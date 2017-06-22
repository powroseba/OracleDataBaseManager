package com.database.ProjectDB.exceptions.address;

import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;

/**
 * Created by seba on 2017-05-25.
 */
public class DeleteAddressException extends Exception {
    public DeleteAddressException(String info) {
        WindowComponents.createAlert(AlertType.WARNING, info);
        WindowComponents.sendConsoleMessage(" WARNING: " + info);
        return;
    }
}
