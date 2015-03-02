package aspectj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mallika on 3/2/15.
 */
public class FileSystem {
    private static List<File> fileList = null;


    private FileSystem() {

    }

    public static List<File> getFileSystem() {
        if (fileList == null) {
            fileList = new ArrayList<File>();
        }
        return fileList;
    }


}
