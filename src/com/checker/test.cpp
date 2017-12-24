#include <bits/stdc++.h>
using namespace std;

int mini(int a,int b,int c,int d,int e){
    return min(a,min(b,min(c,min(d,e))));
}

//int dp[d+1][h+1][5];
int Abc(int h,int d, int time[], int pe[],int res){
    int dp[d+1][h+1];

    for(int i = 0; i <= d; i++)
    {
        for(int j = 0; j <= h; j++)
        {
            if(i==0)
                dp[i][j] = 0;
            else if(i==1)
            {
                if(j<pe[4])
                    dp[i][j] = INT_MAX;
                else if(j<pe[3])
                    dp[i][j] = time[4];
                else if(j<pe[2])
                    dp[i][j] = time[3];
                else if(j<pe[1])
                    dp[i][j] = time[2];
                else if(j<pe[0])
                    dp[i][j] = time[1];
                else
                    dp[i][j] = time[0];
            }
            else
                dp[i][j] = INT_MAX;
        }
    }

    for(int i = 1;i<=d;i++)
    {
        for(int j = 1;j<=h;j++)
        {
            for(int k = 0 ;k<5;k++)
            {
                if(pe[k]<=j)
                {
                    if(dp[i-1][j-pe[k]]!=INT_MAX)
                        dp[i][j] = min(dp[i][j],dp[i-1][j-pe[k]]+time[k]);
                }
            }
        }
    }

    return dp[d][h];
}

int main()
{
    int t;

    cin>>t;

    for(int i = 1; i <= t; i++)
    {
        int h,d;

        cin>>h>>d;

        int time[5],pe[5];

        for(int i = 0;i<5;i++)
        {
            int mi,se;

            cin>>mi>>se>>pe[i];
            time[i] = mi*60 + se;
        }

        int res = Abc(h,d,time,pe,0);

        if (res == INT_MAX)
        	cout << "#" << t << " -1 -1" << endl;
        else
        	cout<<"#"<<t<<" "<<res/60<<"min "<<res%60<<"sec" << endl;
        //cout<<mini(4,5,2,3,41);
    }

//    cout << "int_max : " << INT_MAX << endl;
}