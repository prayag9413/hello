import java.util.Scanner;

class Basic{
	String allChar="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	boolean indexOfChar(char c)
	{
		for(int i=0;i < allChar.length();i++)
		{
			if(allChar.charAt(i)==c)
				    return true;       
		}
		return false;
	}
}
class Crypt{

	Basic b=new Basic();
	public static String selectedKey;
	public static char   sortedKey[];
	public static int    sortedKeyPos[];
	char keyMatrix[][]=new char[5][5];  
	boolean repeat(char c)
	{
	   if(!b.indexOfChar(c))
	   {
	       return true;
	   }
		for(int i=0;i < keyMatrix.length;i++)
		{
		    for(int j=0;j < keyMatrix[i].length;j++)
		    { 
		        if(keyMatrix[i][j]==c || c=='J')
		            return true;
		    }
		}
		return false;
	}

	void insertKey(String key)
	{
	    key=key.toUpperCase();
	    key=key.replaceAll("J", "I");
	    key=key.replaceAll(" ", "");
	    int a=0,b=0;
	    
	    for(int k=0;k < key.length();k++)
	    {
		    if(!repeat(key.charAt(k)))
		    {
		        keyMatrix[a][b++]=key.charAt(k);
		        if(b>4)
		        {
		            b=0;
		            a++;
		        }
		    }
	    }
	   
	    char p='A';
	    
	    while(a < 5)
	    {
		   while(b < 5)
		   {
		        if(!repeat(p)) 
		        {
		            keyMatrix[a][b++]=p;
		            
		        }
		      p++;
		   }
		   b=0;
		   a++;
	    }
	    System.out.println();
	    System.out.print("-------------------------Key Matrix-------------------");
	    for(int i=0;i < 5;i++)
	    {
		System.out.println();
		for(int j=0;j < 5;j++)
		{
		    System.out.print("\t"+keyMatrix[i][j]);
		}
	    }
	    System.out.println("\n---------------------------------------------------------");
	    System.out.println();
	}

	int rowPos(char c)
	{
	     for(int i=0;i < keyMatrix.length;i++)
		{
		    for(int j=0;j < keyMatrix[i].length;j++)
		    { 
		        if(keyMatrix[i][j]==c)
		            return i;
		    }
		}
	     return -1;
	}

	int columnPos(char c)
	{
	     for(int i=0;i < keyMatrix.length;i++)
		{
		    for(int j=0;j < keyMatrix[i].length;j++)
		    { 
		        if(keyMatrix[i][j]==c)
		            return j;
		    }
		}
	     return -1;
	}

	String encryptChar(String plain)
	{
	   plain=plain.toUpperCase();
	   char a=plain.charAt(0),b=plain.charAt(1);
	   String cipherChar="";
	   int r1,c1,r2,c2;
	   r1=rowPos(a);
	   c1=columnPos(a);
	   r2=rowPos(b);
	   c2=columnPos(b);

	   if(c1==c2)
	   {
		++r1;
	       ++r2;
	       if(r1>4)
		   r1=0;
	       
	       if(r2>4)
		   r2=0;
	       cipherChar+=keyMatrix[r1][c2];
	       cipherChar+=keyMatrix[r2][c1];
	   }
	   else if(r1==r2)
	   {    
	       ++c1;
	       ++c2;
	       if(c1>4)
		   c1=0;
	       
	       if(c2>4)
		   c2=0;
	       cipherChar+=keyMatrix[r1][c1];
	       cipherChar+=keyMatrix[r2][c2];
	       
	   }
	   else{
	       cipherChar+=keyMatrix[r1][c2];
	       cipherChar+=keyMatrix[r2][c1];
	   }
	   return cipherChar;
	}
	// To reorder data do the sorting on selected key
	public static void doProcessOnKey()
	{
		// Find position of each character in selected key and arrange it on
		// alphabetical order
		int min, i, j;
		char orginalKey[] = selectedKey.toCharArray();
		char temp;
		// First Sort the array of selected key
		for (i = 0; i < selectedKey.length(); i++)
		{
		    min = i;
		    for (j = i; j < selectedKey.length(); j++)
		    {
			if (sortedKey[min] > sortedKey[j])
			{
			    min = j;
			}
		    }
		    if (min != i)
		    {
			temp = sortedKey[i];
			sortedKey[i] = sortedKey[min];
			sortedKey[min] = temp;
		    }
		}
		// Fill the position of array according to alphabetical order
		for (i = 0; i < selectedKey.length(); i++)
		{
		    for (j = 0; j < selectedKey.length(); j++)
		    {
			if (orginalKey[i] == sortedKey[j])
			    sortedKeyPos[i] = j;
		    }
		}
    	}
    
	public String encrypt(String plainText, int key)
   	{
    		plainText = plainText.toUpperCase();
    		char[] plainTextChar = plainText.toCharArray();
    		for(int i=0;i<plainTextChar.length;i++) {
        		plainTextChar[i] = (char)(((int)plainTextChar[i]+key-65)%26 + 65);
    		}
    		return String.valueOf(plainTextChar);
   	} 
   	
   	
   	public String encrypt(String plainText, String key)
   	{
   		selectedKey=key;
   		sortedKeyPos = new int[selectedKey.length()];
		sortedKey = selectedKey.toCharArray();
   		int min, i, j;
   		
		char orginalKey[] = selectedKey.toCharArray();
		char temp;
		doProcessOnKey();
		int row = plainText.length() / selectedKey.length();
		int extrabit = plainText.length() % selectedKey.length();
		int exrow = (extrabit == 0) ? 0 : 1;
		int rowtemp = -1, coltemp = -1;
		int totallen = (row + exrow) * selectedKey.length();
		char pmat[][] = new char[(row + exrow)][(selectedKey.length())];
		char encry[] = new char[totallen];
		int tempcnt = -1;
		row = 0;
		for (i = 0; i < totallen; i++)
		{
		    coltemp++;
		    if (i < plainText.length())
		    {
		        if (coltemp == (selectedKey.length()))
		        {
		            row++;
		            coltemp = 0;
		        }
		        pmat[row][coltemp] = plainText.charAt(i);
		    }
		    else
		    { // do the padding ...
		        pmat[row][coltemp] = '*';
		    }
		}
		int len = -1, k;
		for (i = 0; i < selectedKey.length(); i++)
		{
		    for (k = 0; k < selectedKey.length(); k++)
		    {
		        if (i == sortedKeyPos[k])
		        {
		            break;
		        }
		    }
		    for (j = 0; j <= row; j++)
		    {
		        len++;
		        encry[len] = pmat[j][k];
		    }
		}
		String p1 = new String(encry);
		return (new String(p1)); 		
	   }   
	   
	public String encrypt(String plainText, String key, int s)
   	{
   		insertKey(key);
		String cipherText="";
		plainText=plainText.replaceAll("j", "i");
		plainText=plainText.replaceAll(" ", "");
		plainText=plainText.toUpperCase();
		int len=plainText.length();
		// System.out.println(plainText.substring(1,2+1));
		if(len/2!=0)
		{
		plainText+="X";
		++len;
		}

		for(int i=0;i < len-1;i=i+2)
		{
		cipherText+=encryptChar(plainText.substring(i,i+2));
		cipherText+=" "; 
		}
		return cipherText;
   	}
}
class A6{
       public static void main(String args[])throws Exception
       {
            Crypt c=new Crypt();
            Scanner scn=new Scanner(System.in);
            String key,cipherText,plainText;
            int choice,key1;
           
            System.out.println("Enter plaintext:");
            plainText=scn.nextLine();
            plainText=plainText.replaceAll("\\s+","");
            plainText=plainText.toUpperCase();
            System.out.println();
			while(true)
			{
            System.out.println("Enter encrypting method:");
            System.out.println("1. Caesar Cipher");
            System.out.println("2. Transposition Cipher");
            System.out.println("3. PlayFair Cipher");
			System.out.println("4. Exit");
            System.out.println("Enter your choice:");
            choice=scn.nextInt();
            System.out.println();
            switch(choice){
            	case 1: System.out.println("Enter key:");
            		key1=scn.nextInt();
            		cipherText=c.encrypt(plainText,key1);
            		System.out.println();
            		System.out.println("Encrypted text:");
            		System.out.println(cipherText);
            		break;
            	
            	case 2: System.out.println("Enter Key:");
            		scn.nextLine();
            		key=scn.nextLine();
            		cipherText=c.encrypt(plainText,key);
            		System.out.println();
            		System.out.println("Encrypted text:");
            		System.out.println(cipherText);
            		break;
            	
            	case 3: System.out.println("Enter Key:");
            		scn.nextLine();
            		key=scn.nextLine();
            		cipherText=c.encrypt(plainText,key,3);
            		System.out.println();
            		System.out.println("Encrypted text:");
            		System.out.println(cipherText);
            		break;
				case 4:System.exit(0);
            }
			}

       }
}
