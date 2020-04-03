/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetsecurite;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author marga
 */
public class Codage {

    Cipher cipher;
    SecretKey key;
    
    public Codage(SecretKey cle) {
         try {
            key = cle;
            cipher = Cipher.getInstance("AES");
            com.sun.org.apache.xml.internal.security.Init.init();
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public String cryptage(String message) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encode(cipher.doFinal(message.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public String decryptage(String message) {
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.decode(message)));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
