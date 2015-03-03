package aspect;


import aspectj.File;
import aspectj.FileList;
import aspectj.UnauthorizedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

/**
 * Created by mallika on 3/2/15.
 */

@Aspect
public class Authorize {

    List<File> fileList = FileList.getFileList();
//    @Before("execution(public* aspectj.FileService.readFile(..))")
//    public Object doAccessCheck() throws Throwable {
////    public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
////        if (1==1) { throw new UnauthorizedException(); }
//        //Object retVal = pjp.proceed();
//       // return retVal;
//        System.err.println("------> Before access check!!!");
//        return new Object();
//    }

    @Around("execution(public* aspectj.FileService.readFile(..))")
    public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args=pjp.getArgs();
        String userId = (String) args[0];
        String filePath = (String) args[1];
        File file = null;
        boolean authorized = false;
        for (File file1 : fileList) {
            if (file1.getFilePath().equals(filePath)) {
                file = file1;
                break;
            }
        }
        if (file != null) {
            if (userId == file.getOwner()) {
                Object retVal = pjp.proceed();
                System.out.println(userId+" read file "+filePath);
                authorized = true;
                return retVal;

            } else {
                for (String user : file.getSharedWith()) {
                    if (userId.equals(user)) {
                        Object retVal = pjp.proceed();
                        System.out.println(userId + " read file " + filePath);
                        authorized = true;
                        return retVal;
                    }
                }
            }
        }
        if (!authorized) {
            System.out.println(userId+" cannot read file "+filePath);
            throw new UnauthorizedException();
        }
        return null;
    }

    @Around("execution(public* aspectj.FileService.shareFile(..))")
    public Object doShareAccess (ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        String userId = (String) args[0];
        String targetUserId = (String) args[1];
        String filePath = (String) args[2];
        File file = null;
        boolean authorized = false;
        List<String> shared = null;
        for (File file1 : fileList) {
            if (filePath.equals(file1.getFilePath())) {
                file = file1;
                break;
            }
        }
        if (file != null) {
            shared = file.getSharedWith();
            for (String user : shared) {
                if (targetUserId.equals(user)) {
                    System.out.println("File "+filePath+" is already shared with user "+targetUserId);
                    return null;
                }
            }
            if (userId.equals(file.getOwner())) {
                Object retVal = pjp.proceed();
                System.out.println(userId+" shared file "+filePath+" with "+targetUserId);
                authorized = true;
                return retVal;
            } else {
                for (String user : shared) {
                    if (userId.equals(user)) {
                        Object retVal = pjp.proceed();
                        System.out.println(userId + " shared file " + filePath + " with " + targetUserId);
                        authorized = true;
                        return retVal;
                    }
                }
            }
            if (!authorized) {
                System.out.println(userId+" cannot share file "+filePath);
                throw new UnauthorizedException();
            }
        }
        return null;
    }

    @Around("execution(public* aspectj.FileService.unshareFile(..))")
    public Object doUnshareAccess(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        String userId = (String) args[0];
        String targetUserId = (String) args[1];
        String filePath = (String) args[2];
        File file = null;
        boolean authorized = false;
        List<String> shared = null;
        for (File file1 : fileList) {
            if (filePath.equals(file1.getFilePath())) {
                file = file1;
                break;
            }
        }
        if (file != null) {
            if (userId.equals(file.getOwner())) {
                Object retVal = pjp.proceed();
                System.out.println(userId+" unshared file "+filePath+" with "+targetUserId);
                authorized = true;
                return retVal;
            }
        }
        return null;
    }


}
