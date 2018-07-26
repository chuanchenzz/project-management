package com.outsource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 *
 * @author chuanchen
 */
@SpringBootApplication
@MapperScan("com.outsource.dao.*")
public class AppApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(AppApplication.class,args);
    }
}
