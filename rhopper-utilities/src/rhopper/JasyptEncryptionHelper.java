package rhopper;

import java.lang.reflect.Method;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * http://www.jasypt.org/easy-usage.html
 * 
 * @author rhopper
 * 
 *         Make sure you are using the same version of jasypt*.jar as the Spring
 *         Boot project.
 * 
 *         For jasypt-spring-boot-starter 3.0.3, it is jasypt-1.9.3.jar
 * 
 *         Default Algorithm used by spring-boot-jasypt:
 *         PBEWITHHMACSHA512ANDAES_256 for 1.9.3
 *         PBEWithMD5AndDES for 1.9.2
 * 
 *         jasypt.encryptor.algorithm=PBEWITHHMACSHA512ANDAES_256
 *         jasypt.encryptor.key-obtention-iterations=1000
 *         jasypt.encryptor.pool-size=1
 *         jasypt.encryptor.salt-generator-classname=org.jasypt.salt.RandomSaltGenerator
 *         jasypt.encryptor.iv-generator-classname=org.jasypt.iv.RandomIvGenerator
 *         jasypt.encryptor.string-output-type=base64
 * 
 * 
 */
public class JasyptEncryptionHelper {

	public static void main(String argv[]) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		String environmentPw = argv[0];
		String textToEncrypt = argv[1];
		try {
			//does not exist in 1.9.2
			Method method = encryptor.getClass().getMethod("setIvGenerator");
			Class clazz = Class.forName("org.jasypt.iv.RandomIvGenerator");
			method.invoke(encryptor, clazz.getDeclaredConstructors()[0].newInstance());
			encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
		} catch (Exception e) {
			System.out.println("Exception setting RandomIvGeneartor: " + e.toString());
		}
		encryptor.setSaltGenerator(new org.jasypt.salt.RandomSaltGenerator());
		encryptor.setStringOutputType("base64");
		encryptor.setPassword(environmentPw);
		String myEncryptedText = encryptor.encrypt(textToEncrypt);
		System.out.println("Encrypted:  " + myEncryptedText);
		String plainText = encryptor.decrypt(myEncryptedText);
		System.out.println("Decrypted:  " + plainText);
		if (textToEncrypt.equalsIgnoreCase(plainText)) {
			System.out.println("PASS");
		} else {
			System.out.println("FAIL");
		}
	}
}
