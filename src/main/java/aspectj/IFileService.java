package aspectj;

/**
 * Created by mallika on 3/2/15.
 */
public interface IFileService {
    public void shareFile(String userId, String targetUserId, String filePath);
    public void unshareFile(String userId, String targetUserId, String filePath);
    public byte[] readFile(String userId, String filePath);
}
