package aspectj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mallika on 3/2/15.
 */
public class File {
    private String fileName;
    private String filePath;
    private String owner;
    private List<String> sharedWith; // FIXME replace with hashtable


    public File(String filePath, String owner, String fileName) {
        this.fileName = fileName;
        this.owner = owner;
        this.filePath = filePath;
        this.sharedWith = new ArrayList<String>();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getOwner() {
        return owner;
    }

    public String getFileName() {
        return fileName;
    }

    public File shareTheFile(String targetUser) {
        sharedWith.add(targetUser);
        return this;
    }

    public File unshareTheFile(String targetUser) {
        sharedWith.remove(targetUser);
        return this;
    }
    public boolean canRead(String target, String filePath) {
        for (String user : sharedWith) {
            if (user.equals(target)) {
                return true;
            }
        }
        return false;
    }
    private String extractOwner(String filePath) {

        int count = 0;
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < filePath.length(); i++) {
            if (filePath.charAt(i) == '/') {
                count++;
            }
            if (count == 2) {
                startIndex = i;
            }
            if (count == 3) {
                endIndex = i;
                break;
            }
        }
        return filePath.substring(startIndex+1, endIndex);
    }

    private String extractFileName(String filePath) {

        int count = 0;
        int startIndex = 0;
        for (int i = 0; i < filePath.length(); i++) {
            if (filePath.charAt(i) == '/') {
                count++;
            }
            if (count == 4) {
                startIndex = i;
                break;
            }

        }
        return filePath.substring(startIndex+1);
    }

    public List<String> getSharedWith() {
        return sharedWith;
    }
}
