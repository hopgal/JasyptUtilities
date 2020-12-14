package rhopper;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

/**
 * http://www.jasypt.org/easy-usage.html
 * 
 * @author rhopper
 * 
 * Make sure you are using the same version of jasypt*.jar as the Spring Boot project.
 * 
 * For jasypt-spring-boot-starter 3.0.3, it is jasypt-1.9.3.jar 
 * 
 * Default Algorithm used by spring-boot-jasypt:  PBEWITHHMACSHA512ANDAES_256
 * 
 * 
 * jasypt.encryptor.algorithm=PBEWITHHMACSHA512ANDAES_256
jasypt.encryptor.key-obtention-iterations=1000
jasypt.encryptor.pool-size=1
jasypt.encryptor.salt-generator-classname=org.jasypt.salt.RandomSaltGenerator
jasypt.encryptor.iv-generator-classname=org.jasypt.iv.RandomIvGenerator
jasypt.encryptor.string-output-type=base64

 * 
 *  */
public class JasyptEncryptionHelper {

	public static void main(String argv[]) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		String environmentPw = argv[0];
		String textToEncrypt = argv[1];
		encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
		encryptor.setIvGenerator(new org.jasypt.iv.RandomIvGenerator());
		encryptor.setSaltGenerator(new org.jasypt.salt.RandomSaltGenerator());
//		encryptor.setStringOutputType("base64");
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
