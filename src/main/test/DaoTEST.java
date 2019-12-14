import com.hjc.sign.core.common.encrypt.PasswordEncrypt;
import com.hjc.utils.HJCKeyGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DaoTEST.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DaoTEST {

    @Test
    public void test() throws UnsupportedEncodingException {
        String s = PasswordEncrypt.encryptSHS256("9d964da5333cf9c4c06d0e5d2281e64a","wqwq");//密码加密
//        HJCKeyGenerator.init(0,0);
//        HJCKeyGenerator keyGenerator = new HJCKeyGenerator();
//        for (int i =0;i<10;i++)
//        {
            System.out.println(s);
            System.out.println("LdOKBGlW6zIZYGazIMtyfWOF/HIptc541S5wtZBdMdQ=");
//        }
    }
}
