package aspectj;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class HelloApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        IFileService service = (IFileService) context.getBean("aspectBean");
        System.out.println(service.readFile("Alice", "/home/Alice/shared/Af1.txt"));
    }
}

