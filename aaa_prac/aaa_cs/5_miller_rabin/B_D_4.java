

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;

/*
It is essential for the security of DSA that the parameter tuple is valid, i.e. that

    both p and q are primes;
    p is a 1024 bit number and q a 160 bit number;
    q is a divisor of p-1;
    g has order q i.e. gq mod p = 1 and g > 1.

*/
public class B_D_4 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
    keyGen.initialize(1024);
    KeyPair keypair = keyGen.genKeyPair();
    DSAPrivateKey privateKey = (DSAPrivateKey) keypair.getPrivate();
    DSAPublicKey publicKey = (DSAPublicKey) keypair.getPublic();

    DSAParams dsaParams = privateKey.getParams();
    BigInteger p = dsaParams.getP();
    
    BigInteger q = dsaParams.getQ();
    BigInteger g = dsaParams.getG();
    BigInteger x = privateKey.getX();
    BigInteger y = publicKey.getY();

      System.out.println();
      System.out.println("DSA Key Parameters: ");
      System.out.println("p = "+p);
      System.out.println("q = "+q);
      System.out.println("g = "+g);
      System.out.println("x = "+x);
      System.out.println("y = "+y);
        System.out.println("P length is "+p.bitLength());
        System.out.println("q length is "+p.bitLength());
      System.out.println();
      System.out.println("DSA Key Verification: ");
      System.out.println("What's key size? "+p.bitLength());
      System.out.println("Is p a prime? "+p.isProbablePrime(0));
      System.out.println("Is q a prime? "+q.isProbablePrime(0));
      System.out.println("Is p-1 mod q == 0? " +p.subtract(BigInteger.ONE).mod(q));
      System.out.println("Is g**q mod p == 1? "+g.modPow(q,p));
      System.out.println("Is q > x? "+(q.compareTo(x)==1));
      System.out.println("Is g**x mod p == y? "+g.modPow(x,p).equals(y));
          
    }
}
/*
 Output
 ================
 ➜  BD4 java B_D_4
 
 DSA Key Parameters:
 p = 178011905478542266528237562450159990145232156369120674273274450314442865788737020770612695252123463079567156784778466449970650770920727857050009668388144034129745221171818506047231150039301079959358067395348717066319802262019714966524135060945913707594956514672855690606794135837542707371727429551343320695239
 q = 864205495604807476120572616017955259175325408501
 g = 174068207532402095185811980123523436538604490794561350978495831040599953488455823147851597408940950725307797094915759492368300574252438761037084473467180148876118103083043754985190983472601550494691329488083395492313850000361646482644608492304078721818959999056496097769368017749273708962006689187956744210730
 x = 307419702159331239999612859293141527351456912300
 y = 157111791463886735460042182849436748566121955237365865627836441514011077109649387732111167248254934384433910488276722160077745617217662156349516290666126225338237834741951224366454985873439062251516221098861841781764478204224288188796026269024532742718112105065369069668503099131055240256657259060671181026016
 P length is 1024
 q length is 1024
 
 DSA Key Verification:
 What's key size? 1024
 Is p a prime? true
 Is q a prime? true
 Is p-1 mod q == 0? 0
 Is g**q mod p == 1? 1
 Is q > x? true
 Is g**x mod p == y? true
 */
