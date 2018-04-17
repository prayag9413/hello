
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.KeySpec;

public class B_D_3 {
  public static void main(String[] argv) throws Exception {
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");////create instance of keypair offf keys(private aaand public)
   SecureRandom random = SecureRandom.getInstance("SHA1PRNG","SUN");// generate random keys //cryptographically strong random number generator (RNG)
    keyGen.initialize(1024,random);  //intialise keys size
    KeyPair keypair = keyGen.genKeyPair();  //get key pair
    DSAPrivateKey privateKey = (DSAPrivateKey) keypair.getPrivate();//get private key for DSA sig
    DSAPublicKey publicKey = (DSAPublicKey) keypair.getPublic();//get publickey for DSA sig

    DSAParams dsaParams = privateKey.getParams();  //get parameter for DSA
    BigInteger p = dsaParams.getP();
     // System.out.println("p length is "+p.bitLength());
    BigInteger q = dsaParams.getQ();
    BigInteger g = dsaParams.getG();
    BigInteger x = privateKey.getX();
    BigInteger y = publicKey.getY();
      //System.out.println("p = " + p);

    // Create the DSA key factory
    KeyFactory keyFactory = KeyFactory.getInstance("DSA");//Key factories are used to convert keys  into key specifications, and vice versa.
    // Create the DSA private key
    KeySpec privateKeySpec = new DSAPrivateKeySpec(x, p, q, g);// stored nature of privatekey
     KeySpec publicKeySpec = new DSAPublicKeySpec(x, p, q, g);//stored nature of public key
    PrivateKey privateKey1 = keyFactory.generatePrivate(privateKeySpec);//Generates a private key object from the provided key specification (key material).
   PublicKey publicKey1 = keyFactory.generatePublic(publicKeySpec);//Generates a public key object from the provided key specification (key material).

    byte[] buffer = new byte[1024];  //to stored input message
      //System.out.println(privateKey1.getAlgorithm());
    Signature sig = Signature.getInstance("SHA1withDSA", "SUN");//Returns a Signature object that implements the specified signature algorithm.
    sig.initSign(privateKey1);//Initialize this object for signing
    
    
     FileInputStream fis = new FileInputStream("a.txt");//get input file
        BufferedInputStream bufin = new BufferedInputStream(fis); 
       
        int len;
        
        while (bufin.available() != 0) {  // while if data exceed
            
          len = bufin.read(buffer);// input data pass to dsa object
        System.out.println(buffer.toString());   
          sig.update(buffer, 0, len);//Updates the data to be signed or verified by a byte.
        }
        ;

        bufin.close();

        /*
         * Now that all the data to be signed has been read in, generate
         * a signature for it
         */

        byte[] realSig = sig.sign();//sign data
//char s[]=realSig.toString().toCharArray();
        /* Save the signature in a file */
        FileOutputStream sigfos = new FileOutputStream("sig");
        sigfos.write(realSig);

        sigfos.close();

        /* Save the public key in a file */
        byte[] key = publicKey1.getEncoded();
        FileOutputStream keyfos = new FileOutputStream("suepk");
        keyfos.write(key);

        keyfos.close();
  }
}

/*
 Output
 ============
 ➜  BD3 java B_D_3
 [B@3d4eac69
*/
