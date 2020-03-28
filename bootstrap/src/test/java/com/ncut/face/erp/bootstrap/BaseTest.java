package com.ncut.face.erp.bootstrap;

import com.ncut.face.erp.service.common.utils.EncodeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ErpApplication.class)
public class BaseTest {
    @Value("${public.key}")
    private String publicKey;
    @Value("${private.key}")
    private String privateKey;

    @Test
    public void encodeTest() throws Exception {
        String str = "123456";
        byte[] bytes = EncodeUtil.encryptByPublicKey(str, publicKey);
        System.out.println(EncodeUtil.encryptBASE64(bytes));
    }

    @Test
    public void decodeTest() throws Exception {
        String str = "l41Lmra/Ooc5rAChYlv4SbuS5IhziWBLLOn37gCPgBQYgIBdxy87TDjyBPgaHkLoG+hEV6t0zD1d9kyLpvMv0gl5n6y35og3PftPyj+0ScgZfg2igWqn8narOlt1xEenYT1TWCW5WM2EgWm6tCGVx4kHZF+DFVG2czprDRIiBK0=";
        System.out.println(EncodeUtil.decryptByPrivateKey(str, privateKey));
    }
}
