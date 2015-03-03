package aspectj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mallika on 3/2/15.
 */
public class FileList {
    private static List<File> fileList = null;

    private FileList() {

    }

    public static List<File> getFileList() {
        if (fileList == null) {
            fileList = new ArrayList<File>();
        }
        return fileList;
    }

}
