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
        String str = "123";
        byte[] bytes = EncodeUtil.encryptByPublicKey(str, publicKey);
        System.out.println(EncodeUtil.encryptBASE64(bytes));
    }

    @Test
    public void decodeTest() throws Exception {
        String str = "q0MvNjrM7A60qVcRo+7/m0G70HITBxpGtHfyvTUPwUNw/LzeFI9q9EXgzHYFoYvE1uLH+95/V5bYAB9aA4R/a/bPIf97xUWne2LKUhNdRL+BGO7SfKt4r7axkPuNeML+p0ho1ZZfH81pdkTZSRil3Ih7OVDjpFElVB2LgKKtq6M=";
        byte[] bytes = EncodeUtil.decryptByPrivateKey(str, privateKey);
        System.out.println(new String(bytes));
    }
}
