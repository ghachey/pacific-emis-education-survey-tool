package org.pacific_emis.surveys.core.data.exceptions;


import androidx.annotation.NonNull;

public class FileExportException extends ReasonableException {
    @NonNull
    @Override
    protected String getMainMessage() {
        return "Unable to export file";
    }

    public FileExportException(String reason) {
        setReason(reason);
    }
}
