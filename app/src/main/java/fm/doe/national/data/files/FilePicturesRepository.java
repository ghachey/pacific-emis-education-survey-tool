package fm.doe.national.data.files;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class FilePicturesRepository implements PicturesRepository {
    private static final String EXT_PICTURE = ".jpg";
    private static final String PATTERN_FILENAME = "IMG_%d";
    private final File externalPicturesDirectory;

    public FilePicturesRepository(Context context) {
        externalPicturesDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    public File createEmptyFile() throws IOException {
        String imageFileName = String.format(Locale.getDefault(), PATTERN_FILENAME, new Date().getTime());
        return File.createTempFile(imageFileName, EXT_PICTURE, externalPicturesDirectory);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void delete(File file) {
        file.delete();
    }
}