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
        System.out.println("Test : Dylan Cannot read Alice's file if it is not shared with him");
        System.out.println();
        String targetUserId = "Dylan";
        String filePath = "/home/Alice/shared/Af1.txt";
        File file = new File ("/home/Alice/shared/Af1.txt", "Alice", "Af1.txt");
        List<File> fileList = FileList.getFileList();
        fileList.add(file);
        System.out.println();
        try {
            service.readFile(targetUserId, filePath);
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
        //Alice can read her own file
        System.out.println("Test : Alice can read her own file");
        System.out.println();
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
        System.out.println();
    }

    @Test
    public void testUnauthorizedShare() {
        //Bob shares Carl's file with Alice and gets an exception
        System.out.println("Test : Bob cannot share Alice's file with Carl if the file has not been shared with Bob");
        System.out.println();
        String userId = "Bob";
        String filePath = "/home/Carl/shared/Cf1.txt";
        String targetUserId = "Alice";
        String owner = "Carl";
        String fileName = "Cf1.txt";
        List<File> fileList = FileList.getFileList();
        File file = new File(filePath, owner, fileName);
        fileList.add(file);
        try {
            service.shareFile(userId, targetUserId, filePath);
        } catch (UnauthorizedException e) {
            Assert.assertTrue(e instanceof UnauthorizedException);
        } catch (Throwable t) {
            Assert.assertFalse("Other exception caught", true);
            t.printStackTrace();
        }
        System.out.println();

    }

    @Test
    public void testReadWhenShared() {
        //Alice shares her file with Bob and Bob can read it
        System.out.println("Test : Alice shares her file with Bob and Bob can read it");
        System.out.println();
        String userId = "Alice";
        String filePath = "/home/Alice/shared/Af1.txt";
        String targetUserId = "Bob";
        String fileName = "Af1.txt";
        File file = new File (filePath, userId, fileName);
        List<File> fileList = FileList.getFileList();
        fileList.add(file);
        service.shareFile(userId, targetUserId, filePath);
        try {
            assertNotNull(service.readFile(targetUserId, filePath));
        } catch (Exception e) {
            assertFalse("Should not throw any exception", true);
        }
        System.out.println();

    }

    @Test
    public void testReadWhenThirdPersonShare() {
        //Meg shares her file with Dan, Dan can read it. Dan shares Meg's file with Ken, Ken can also read it
        System.out.println("Test : Meg shares her file with Dan and Dan can read it. Dan shares Meg's file with Ken, Ken can also read it");
        System.out.println();
        String userId = "Meg";
        String filePath = "/home/Meg/shared/Mf1.txt";
        String targetUserId = "Dan";
        String fileName = "Mf1.txt";
        File file = new File (filePath, userId, fileName);
        List<File> fileList = FileList.getFileList();
        fileList.add(file);
        service.shareFile(userId, targetUserId, filePath);
        try {
            assertNotNull(service.readFile(targetUserId, filePath));
        } catch (Exception e) {
            assertFalse("Should not throw any exception", true);
        }

        service.shareFile(targetUserId, "Ken", filePath);
        try {
            assertNotNull(service.readFile("Ken", filePath));
        } catch (Exception e) {
            assertFalse("Should not throw any exception", true);
        }
        System.out.println();

    }

    @Test
    public void testUnshare() {
        //John shares his file with Jane. Jane shares John's file with Harry. John unshares his file with Harry. Harry
        //cannot read John's file
        System.out.println("Test : John shares his file with Jane. Jane shares John's file with Harry. John unshares his file with Harry. Harry " +
                "cannot read John's file");
        String userId = "John";
        String filePath = "/home/John/shared/Jof1.txt";
        String targetUserId = "Jane";
        String fileName = "Jof1.txt";
        File file = new File (filePath, userId, fileName);
        List<File> fileList = FileList.getFileList();
        fileList.add(file);
        service.shareFile(userId, targetUserId, filePath);
        try {
            assertNotNull(service.readFile(targetUserId, filePath));
        } catch (Exception e) {
            assertFalse("Should not throw any exception", true);
        }

        service.shareFile(targetUserId, "Harry", filePath);
        try {
            assertNotNull(service.readFile("Harry", filePath));
        } catch (Exception e) {
            assertFalse("Should not throw any exception", true);
        }
        System.out.println();
        service.unshareFile(userId, "Harry", filePath);
        try {
            service.readFile("Harry", filePath);
        } catch (UnauthorizedException e) {
            assertTrue(e instanceof UnauthorizedException);
            return;
        } catch (Throwable t){
            assertFalse("Other exception caught", true);
            t.printStackTrace();
        }
    }

    @Test
    public void testUnshare2() {
        //Ann shares her file with Chad, Ann shared her file with Bill, Bill
        // shares Ann’s file with Chad, Ann unshares her file with Chad, and Chad cannot read Ann’s file.
        System.out.println("Ann shares her file with Chad, Ann shared her file with Bill, Bill" +
                        "shares Ann’s file with Chad, Ann unshares her file with Chad, and Chad cannot read Ann’s file.");
        String userId = "Ann";
        String filePath = "/home/Ann/shared/Annf1.txt";
        String targetUserId1 = "Chad";
        String targetUserId2 = "Bill";
        String fileName = "Annf1.txt";
        File file = new File (filePath, userId, fileName);
        List<File> fileList = FileList.getFileList();
        fileList.add(file);
        service.shareFile(userId, targetUserId1, filePath);
        service.shareFile(userId, targetUserId2, filePath);
        service.shareFile(targetUserId1, targetUserId2, filePath);
        service.unshareFile(userId, targetUserId2, filePath);
        try {
            service.readFile(targetUserId2, filePath);
        } catch (UnauthorizedException e) {
            assertTrue(e instanceof UnauthorizedException);
            return;
        } catch (Throwable t){
            assertFalse("Other exception caught", true);
            t.printStackTrace();
        }
    }

    @Test
    public void testUnshare3() {
        System.out.println();
        System.out.println("Sue shares her file with Tim, Tim shared her file with Sam, Sue" +
                "unshares her file with Tim, Tim shares her file with Cal, gets an exception");
        String userId = "Sue";
        String filePath = "/home/Sue/shared/Suef1.txt";
        String targetUserId1 = "Tim";
        String targetUserId2 = "Sam";
        String fileName = "Suef1.txt";
        File file = new File (filePath, userId, fileName);
        List<File> fileList = FileList.getFileList();
        fileList.add(file);
        service.shareFile(userId, targetUserId1, filePath);
        service.shareFile(targetUserId1, targetUserId2, filePath);
        service.unshareFile(userId, targetUserId1, filePath);
        System.out.println();
        try {
            service.shareFile(targetUserId1, "Cal", filePath);
        } catch (UnauthorizedException e) {
            Assert.assertTrue(e instanceof UnauthorizedException);
        } catch (Throwable t) {
            Assert.assertFalse("Other exception caught", true);
            t.printStackTrace();
        }
    }

    @Test
    public void testAccessToSpecificFile() {
        System.out.println();
        //Lisa shares her file1 with Ryan, Ryan tries to access Lisa’s file2 and gets an exception.
        System.out.println("Test : Lisa shares her file1 with Ryan, Ryan tries to access Lisa’s file2 and gets an exception.");
        String userId = "Lisa";
        String filePath1 = "/home/Lisa/shared/LisaFile1.txt";
        String targetUserId = "Ryan";
        String fileName1 = "LisaFile1.txt";
        String filePath2 = "/home/Lisa/shared/LisaFile2.txt";
        String fileName2 = "LisaFile2.txt";
        File file = new File (filePath1, userId, fileName1);
        List<File> fileList = FileList.getFileList();
        File file2 = new File (filePath2, userId, fileName2);
        fileList.add(file);
        fileList.add(file2);
        service.shareFile(userId, targetUserId, filePath1);
        try {
            service.readFile(targetUserId, filePath2);
        } catch (UnauthorizedException e) {
            assertTrue(e instanceof UnauthorizedException);
            return;
        } catch (Throwable t){
            assertFalse("Other exception caught", true);
            t.printStackTrace();
        }
        assertFalse("Expected exception was not thrown", true);
    }


}

//Assert.assertEquals(UnauthorizedException.class, service.readFile(userId, filePath));
