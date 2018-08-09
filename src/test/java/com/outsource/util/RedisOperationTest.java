package com.outsource.util;

import com.outsource.AppApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class RedisOperationTest {
    @Autowired
    RedisOperation redisOperation;

    @Test
    public void testZSetSize(){
        String key = "zzzz";
        Long size = redisOperation.zSetSize(key);
        System.out.println(size);
    }
}
