package org.pacific_emis.surveys.core.utils;

public class Constants {
    public static final String FILE_MIME_TYPE = "text/xml";
    public static final String AUTHORITY_FILE_PROVIDER = "org.pacific_emis.surveys.fileprovider";
    public static final String DEFAULT_ROOT_FOLDER = "Root";
    public static final int SIZE_THUMB_PICTURE = 200;
    public static final int SECONDS_TO_LONG_LOADING_INDICATION = 5;
    public static final int INDEX_NOT_FOUND = -1;

    public class Errors {
        public static final String EXPORT_FOLDER_NOT_SPECIFIED = "Export folder not specified";
        public static final String DRIVE_RESOURCE_CLIENT_IS_NULL = "DriveResourceClient is null";
        public static final String DRIVE_CLIENT_IS_NULL = "DriveClient is null";
        public static final String NO_ACTIVITIES = "No activities is currently running";
        public static final String AUTH_FAILED = "Authentication failure";
        public static final String AUTH_DECLINED = "Authentication declined";
        public static final String WRONG_INTENT = "Activity started with wrong intent";
        public static final String NOT_AUTHORIZED = "User not authorized";
        public static final String WRONG_FRAGMENT_ARGS = "Fragment should be created with static create() method";
        public static final String NEGATIVE_ANSWERS_COUNT = "Answers count cannot be lower than zero";
    }
}
