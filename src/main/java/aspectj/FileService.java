package aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by mallika on 3/2/15.
 */

@Component
public class FileService implements IFileService {
    List<File> fileList = FileList.getFileList();

    private void insertFileRecord(File file) {
        for (File file1 : fileList) {
            if (file.getFilePath() == file1.getFilePath()) {
                System.out.println("File already present");
                return;
            }
        }
        fileList.add(file);
    }

    private File searchForFile(String filePath) {
        for (File file : fileList) {
            if (file.getFilePath().equals(filePath)) {
                return file;
            }
        }
        return null;
    }

    private void updateFileDetails(File file) {
        boolean found = false;
        File file2 = null;
        for (File file1 : fileList) {
            if (file.getFilePath().equals(file1.getFilePath())) {
                found = true;
                file2 = file1;
                break;
            }
        }
        if (found) {
            fileList.remove(file2);
            fileList.add(file);
        }
    }


    @Override
    public void shareFile(String userId, String targetUserId, String filePath) {
        File file = searchForFile(filePath);
        file = file.shareTheFile(targetUserId);
        updateFileDetails(file);
    }

    @Override
    public void unshareFile(String userId, String targetUserId, String filePath) {
        File file = searchForFile(filePath);
        file = file.unshareTheFile(targetUserId);
        updateFileDetails(file);
    }

    @Override
    public byte[] readFile(String userId, String filePath) {
        return new byte[0];
    }
}
