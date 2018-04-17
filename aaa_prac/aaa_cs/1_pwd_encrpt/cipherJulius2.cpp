//============================================================================
// Name        : encyptionAlgo.cpp
// Author      :
// Version     :
// Copyright   : Your copyright notice
// Description : Julius and Caesar cipher
//============================================================================
#include <iostream>
#include <vector>
#include<string>
using namespace std;

string juliusCipher(string plainText, int key)
{
	string cipher;
	for(unsigned int i=0 ; i<(plainText.length());i++)
	{
		//extract character at i
		char plainChar = plainText.at(i);
		char cipherChar = plainChar + key +1;
		int cipherCharValue = (int)cipherChar;

		//handling whitespace
		if(((int)plainText.at(i))==32)
		{
			cipher = cipher + plainText.at(i);
			continue;
		}

		//handling characters going beyond ASCII bounds
		//can be refractored to remove nested if
		if(cipherCharValue > 122 || (cipherCharValue > 90 && cipherCharValue < 97))
		{
			//for lowercase
			if (cipherCharValue > 122 )
			{
				cipherCharValue = cipherCharValue - 122;
				cipherChar = 96 + cipherCharValue;
			}
			//for upper case
			else if ( cipherCharValue > 90 )
			{
				cipherCharValue = cipherCharValue - 90;
				cipherChar = 64 + cipherCharValue;
			}

		}
		cipher = cipher + cipherChar;
	}

	return cipher;
}

char** generatePlayfairKeySquare(string key)
{
	//char keyMatrix[5][5];

	char** keyMatrix;
	keyMatrix = new char*[6];
	for(int k=0;k<5;k++)
	{
		keyMatrix[k] = new char[5];
	}

	int matrixItrRow = 0; int matrixItrCol = 0;
	int alphabetMap[27];

	//init all values in mapping to alphabet array to 1
	for(int j =1;j< 27;j++)
	{
		alphabetMap[j]=1;
	}
	//Step 1:
	//put key string into matrix
	for(unsigned int i=0;i<key.length();i++)
	{

		char ch = key.at(i);
		cout<<"Working with key char\t"<<ch<<"\n";

		alphabetMap[((int)ch - 96)] = 0;


		keyMatrix[matrixItrRow][matrixItrCol] = ch;
		cout<<"Setting to \t "<<keyMatrix[matrixItrRow][matrixItrCol]<<"\n";
		matrixItrCol++;
		if(matrixItrCol > 4)
		{
			matrixItrRow++;
			//what is row number goes beyond 4? (beyond size of 5*5 matrix)
			matrixItrCol = 0;
		}

	}
	int mapPtr=1;

	//Step 2
	//Fill remaining matrix slots with alphabets
	int u=0; int v=0;

	for(u = matrixItrRow;u<5; u++)
	{
		for(v=matrixItrCol;v<5; v++)
		{
			while(alphabetMap[mapPtr] == 0)
			{
				cout<<"Incrementing mapPtr\n";
				mapPtr++;
			}

			//handling i and j condition
			if(mapPtr==10)
			{
				mapPtr++;
				//continue;
			}
			keyMatrix[u][v] = ((char)(96 + mapPtr));
			//cout<<"u is "<<u<<" v is "<<v<<" alphabet is "<<keyMatrix[u][v]<<"\n";
			mapPtr++;
		}
		matrixItrCol=0;
	}
	return keyMatrix;
}

int* getRowCol(char ch, char** key)
{
	int* posArray = new int[2];
	for(int row=0;row<5;row++)
	{
		for(int col=0;col<5;col++)
		{
			if (key[row][col] == ch)
			{
				posArray[0]=row;
				posArray[1]=col;
			}
		}
	}
	return posArray;
}
bool sameRowCheck(string str, char** key)
{
	int *position1= new int[2];
	int *position2= new int[2];

	char ch1=str[0];
	char ch2=str[1];
	position1 = getRowCol(ch1, key);
	position2 = getRowCol(ch2, key);

	if(position1[0] == position2[0])
	{
		return true;
	}
	else
		return false;

}
bool sameColumnCheck(string str, char** key)
{
	int *position1= new int[2];
	int *position2= new int[2];

	char ch1=str[0];
	char ch2=str[1];
	position1 = getRowCol(ch1, key);
	position2 = getRowCol(ch2, key);


	if(position1[1]==position2[1])
	{
		return true;

	}
	else
		return false;


}
bool diagCheck(string str, char** key)
{
	int *position1= new int[2];
	int *position2= new int[2];

	position1 = getRowCol(str[0], key);
	position2 = getRowCol(str[1], key);

	if(position1[0] != position2[0])
	{
		if(position1[1] != position2[1])
		{
			return true;
		}
		else
			return false;
	}
	else
		return false;
}

string playfairEncryption(string plainText, char** playfairKey)
{
	string newText=plainText;
	vector<string> pairVector;
	unsigned int i =0;
	/*
	 * Step 1: Replace double letter occurrences by x
	 * Q: What if two x's are present?
	 */
	for(i=0;i<newText.length()-1;i++)
	{
		char ch = newText[i];
		if(ch == newText[i+1])
		{
			newText[i+1]='x';
		}
		else
			continue;
	}

	/*
	 * Step 2: if length is odd then append 'x'
	 */
	if(newText.length() % 2!=0)
	{
		cout<<"Length of plain text is odd, appending x";
		newText.append("x");

		cout<<"\nNew text is "<<newText;
	}

	/*
	 * Step 3: Split into pairs
	 */
	for(i=0;i<newText.length()-1;i+=2)
	{
		string pairStr = newText.substr(i, 2);
		cout<<"\npair is "<<pairStr<<"\n";
		pairVector.push_back(pairStr);
	}

	/*
	 * Step 4: Now encrypting
	 */
	newText.clear();
	for(i=0;i<pairVector.size();i++)
	{
		string pair = pairVector[i];

		/*
		 * 4.1 If letters are on same column
		 */
		if(sameColumnCheck(pair, playfairKey))
		{
			/*
			* rotating back to 0 if column limit is exhausted
			*/
			int *position1= new int[2];
			int *position2= new int[2];
			position1 = getRowCol(pair[0], playfairKey);
			position2 = getRowCol(pair[1], playfairKey);
			pair[0]=playfairKey[(position1[0]+1)%5][position1[1]];
			pair[1]=playfairKey[(position2[0]+1)%5][position2[1]];
		}

		/*
		 * 4.2 If letters are on the same row
		 */
		else if(sameRowCheck(pair, playfairKey))
		{
			/*
			* rotating back to 0 if row limit is exhausted
			*/
			int *position1= new int[2];
			int *position2= new int[2];
			position1 = getRowCol(pair[0], playfairKey);
			position2 = getRowCol(pair[1], playfairKey);
			pair[0]=playfairKey[position1[0]][(position1[1]+1)%5];
			pair[1]=playfairKey[position2[0]][(position2[1]+1)%5];
		}

		/*
		 * 4.3 Diagonal replacement of letters
		 */
		else if(diagCheck(pair, playfairKey))
		{
			int *position1= new int[2];
			int *position2= new int[2];
			position1 = getRowCol(pair[0], playfairKey);
			position2 = getRowCol(pair[1], playfairKey);
			pair[0]=playfairKey[position1[0]][position2[1]];
			pair[1]= playfairKey[position2[0]][position1[1]];
		}
		newText.append(pair);
	}
	return newText;
}



// Refer http://practicalcryptography.com/ciphers/playfair-cipher/ for Playfair explanation
string playfairCipher(string text)
{
	string playfairKey;
	cout<<"\nEnter key for Playfair cipher generation\n";
	cin>>playfairKey;

	//char plKeySquare[5][5];

	char** plKeySquare = generatePlayfairKeySquare(playfairKey);

	cout<<"\nKey matrix is\n\n";
	for(int i=0;i<=4;i++)
	{
		for(int j=0;j<=4;j++)
		{
			cout<<plKeySquare[i][j]<<" ";
		}
		cout<<"\n";
	}

	string cipherText = playfairEncryption(text, plKeySquare);
	return cipherText;
}
string encryptPT(string text)
{
	int choice;
	cout<<"\n1. Julius Cipher\n2. Playfair\n";
	cin>>choice;
	int key=3;
	string cipher;
	switch(choice)
	{
	case 1:
			cout<<"\nEnter key; Default value = 3\n";
			cin>>key;
			cipher = juliusCipher(text, key);
			return cipher;
			break;

	case 2: cipher = playfairCipher(text);
			return cipher;
			break;

	default: cout<<"\nInvalid input\n";
	}

}
int main()
{
	string plainText;

	cout<<"\nEnter plaintext\n";
	//cin>>plainText;
	getline(cin, plainText, '\n');

	string cipherText = encryptPT(plainText);

	cout<<"\nCipher:\n";
	cout<<cipherText;

	return 0;
}
