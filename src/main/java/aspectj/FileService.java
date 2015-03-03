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
        for (File file1 : fileList) {
            if (file.getFilePath().equals(file1.getFilePath())) {
                fileList.remove(file1);
                fileList.add(file);
            }
        }
    }


    @Override
    public void shareFile(String userId, String targetUserId, String filePath) {
        File file = searchForFile(filePath);
        if (file != null) {
            file = file.shareTheFile(targetUserId);
        } else {
            System.out.println("File does not exist!");
        }
        if (file != null) {
            updateFileDetails(file);
            System.out.println("Successfully shared file with "+targetUserId );
        } else {
            System.out.println("Could not share file with "+targetUserId );
        }
    }

    @Override
    public void unshareFile(String userId, String targetUserId, String filePath) {
        File file = searchForFile(filePath);
        if (file != null) {
            file = file.unshareTheFile(targetUserId);
        } else {
            System.out.println("File does not exist!");
        }
        if (file != null) {
            updateFileDetails(file);
            System.out.println("Successfully unshared file with "+targetUserId );
        } else {
            System.out.println("Could not unshare file with "+targetUserId );
        }
    }

    @Override
    public byte[] readFile(String userId, String filePath) {
        File file = searchForFile(filePath);
        if (file != null) {
            System.out.println(userId+" read file "+filePath);
            return new byte[0];
        } else {
            System.out.println("File does not exist!");
            return null;
        }
    }
}
