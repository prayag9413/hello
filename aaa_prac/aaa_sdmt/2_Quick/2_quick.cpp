#include<bits/stdc++.h>
#include<omp.h>
using namespace std;


class Quick
{
  	vector<int> arr;

  public:
  	int n;
  	void getXML();
	void swap(int *, int *);
	int partition (int , int );
	void quickSort(int , int );
	void input();
	void printArray();
};

void Quick::getXML()
{
	int N;
	string line;
	ifstream in("2_input.xml");
	bool begin_tag = false;
	bool begin_tag1 = false;
	string tmp;
	while (getline(in,line))
	{
		tmp="";
		for (int i = 0; i < line.length(); i++)
		{
			if (line[i] == ' ' && tmp.size() == 0);
			else
			{
				tmp += line[i];
			}
		}
		if (tmp == "<Number>")
		{
			begin_tag1 = true;
			continue;
		}
		else if (tmp == "</Number>")
		{
			begin_tag1 = false;
		}
		if (begin_tag1)
		{
			n++;
			N=atoi(tmp.c_str());
			arr.push_back(N);
		}
	}
	n=arr.size();

}

void Quick::input()
{
	cout<<"\nEnter size:";
	cin>>n;
	for(int i=0;i<n;i++)
	{
		cin>>arr[i];
	}
}

void Quick::swap(int* a, int* b)
{
    int t = *a;
    *a = *b;
    *b = t;
}

int Quick::partition (int low, int high)
{
    int pivot = arr[high];    			// pivot
    int i = (low - 1);  				// Index of smaller element

    for (int j = low; j <= high- 1; j++)
    {
        // If current element is smaller than or equal to pivot
        if (arr[j] <= pivot)
        {
            i++;    					// increment index of smaller element
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[i + 1], &arr[high]);
    return (i + 1);
}

void Quick::quickSort(int low, int high)
{
    if (low < high)
    {
        int pi = partition(low, high);
 		#pragma omp parallel sections
 		{
 			#pragma omp section
		    quickSort(low, pi - 1);
		    #pragma omp section
		    quickSort(pi + 1, high);
		}
    }
}

void Quick::printArray()
{
    int i;
    for (i=0; i < n; i++)
        cout<< arr[i]<<" ";
    cout<<endl;
}

int main()
{
	Quick q;
	//q.input();
    q.getXML();
    q.printArray();
    q.quickSort(0, q.n-1);
    cout<<"Sorted array: ";
    q.printArray();
    return 0;
}

