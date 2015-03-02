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
    private List<String> sharedWith;
    private List<String> unsharedWith;

    public File(String filePath, String owner, String fileName) {
        this.fileName = fileName;
        this.owner = owner;
        this.filePath = filePath;
        List<String> sharedWith = new ArrayList<String>();
        List<String> unsharedWith = new ArrayList<String>();
    }

    private String getFilePath() {
        return filePath;
    }

    private String getOwner() {
        return owner;
    }

    public String getFileName() {
        return fileName;
    }

    public void shareTheFile(String sharer, String targetUser) {


    }
       /* for (int i = 0; i < sharedWith.size(); i++) {
            if (targetUser.equals(sharedWith.get(i))) {
                return 0;
            }
        }
        if (sharer.equals(this.owner)) {
            sharedWith.add(targetUser);
            return 1;
        }
        for (String user : sharedWith) {
            if (user.equals(targetUser)) {
                sharedWith.add(targetUser);
                return 1;
            }
        }
        return -1;

    }
    */

    public void unshareTheFile(String unsharer, String target){


    }

    /*
        boolean isShared = false;
        for (int i = 0; i < sharedWith.size(); i++) {
            if (target.equals(sharedWith.get(i))) {
                isShared = true;
                break;
            }
        }
        if (!isShared) {
            return 0;
        } else {
            if (unsharer.equals(this.owner)) {
                unsharedWith.add(target);
                return 1;
            } else {
                return 2;
            }
        }

    }
    */

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

    public List<String> getUnsharedWith() {
        return unsharedWith;
    }
}
