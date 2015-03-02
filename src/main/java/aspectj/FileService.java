package aspectj;

/**
 * Created by mallika on 3/2/15.
 */
public class FileService implements IFileService {

    @Override
    public void shareFile(String userId, String targetUserId, String filePath) {
    }

    @Override
    public void unshareFile(String userId, String targetUserId, String filePath) {

    }

    @Override
    public byte[] readFile(String userId, String filePath) {
        return new byte[0];
    }


}
