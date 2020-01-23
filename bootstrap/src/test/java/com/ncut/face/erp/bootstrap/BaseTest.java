package com.ncut.face.erp.bootstrap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErpApplication.class)
public class BaseTest {
    @Test
    public void test() throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:lib/").getPath();
        System.out.println(path);
    }
}
