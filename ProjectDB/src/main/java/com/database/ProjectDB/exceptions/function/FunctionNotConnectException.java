package com.database.ProjectDB.exceptions.function;

import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;

/**
 * Created by seba on 2017-06-04.
 */
public class FunctionNotConnectException extends Exception {
    public FunctionNotConnectException(String info) {
        WindowComponents.createAlert(AlertType.ERROR, info);
        return;
    }
}
