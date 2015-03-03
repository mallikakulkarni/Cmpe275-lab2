package aspectj;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class SpringAppTests {

    @Autowired
    private FileService service;

    @Test
    public void testReadForUnauthorizedUser() {

        String userId = "Carl";
        String targetUserId = "Bob";
        String filePath = "/home/Alice/shared/Af1.txt";
        File file = new File ("/home/Alice/shared/Af1.txt", "Alice", "Af1.txt");
        List<File> fileList = FileList.getFileList();
        fileList.add(file);
        try {
            service.readFile(userId, filePath);
        } catch (UnauthorizedException e) {
            assertTrue(e instanceof UnauthorizedException);
            return;
        } catch (Throwable t){
            assertFalse("Other exception caught", true);
            t.printStackTrace();
        }
        assertFalse("Expected exception was not thrown", true);

    }

    @Test
    public void testReadForAuthorizedUser() {
        String userId = "Alice";
        String filePath = "/home/Alice/shared/Af1.txt";
        File file = new File ("/home/Alice/shared/Af1.txt", "Alice", "Af1.txt");
        List<File> fileList = FileList.getFileList();
        fileList.add(file);

        try {
            assertNotNull(service.readFile(userId, filePath));
        } catch (Exception e) {
            assertFalse("Should not throw any exception", true);
        }
    }
}

//Assert.assertEquals(UnauthorizedException.class, service.readFile(userId, filePath));
