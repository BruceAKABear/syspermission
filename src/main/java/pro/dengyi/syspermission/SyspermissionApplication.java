package pro.dengyi.syspermission;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"pro.dengyi.syspermission.dao"})
public class SyspermissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyspermissionApplication.class, args);
    }

}
