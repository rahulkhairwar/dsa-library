/*
ID: hepic
PROG: sabotage
LANG: C++11
*/
#include <bits/stdc++.h>

#define FOR(i, a, b) for(auto i=a; i<=b; ++i)
#define REP(i, a, b) for(auto i=a; i<b; ++i)
#define FORI(i, a, b) for(auto i=a; i!=b+1-2*(a>b); i+=1-2*(a>b))
#define REPI(i, a, b) for(auto i=a-(a>b); i!=b-(a>b); i+=1-2*(a>b))
#define ALL(v) v.begin(),v.end()
#define mp(a, b) make_pair(a, b)
#define pb(a) push_back(a)
#define pf(a) push_front(a)
#define eb(a, b) emplace_back(a, b)
#define fir first
#define sec second
#define what_is(x) cout<<#x<<" is "<<x<<endl;
#define type(x) typeid(x).name()
#define ms(arr, val) memset(arr, val, sizeof(arr))
#define min3(a,b,c) min(min(a,b),c)
#define max3(a,b,c) max(max(a,b),c)
#define PI acos(-1)
#define open_read1 freopen("a.txt", "r", stdin)
#define open_write1 freopen("b.xt", "w", stdout)
#define open_read freopen("team.in", "r", stdin)
#define open_write freopen("team.out", "w", stdout)

using namespace std;

typedef long long LL;
typedef long double LD;
typedef unsigned long long ULL;
typedef pair<double, double> PDD;
typedef pair<int, int> PII;
typedef pair<int, PII> PIPII;
typedef pair<PII, PII> PPIIPII;
typedef pair<LL, LL> PLL;
typedef pair<ULL, ULL> PUU;
typedef pair<LL, PLL> PLPLL;
typedef pair<PLL, PLL> PPLLPLL;
typedef pair<int, LL> PIL;
typedef pair<LL, int> PLI;


template<typename T, typename T1>
ostream& operator << (ostream &out, pair<T, T1> obj)
{
    out<<"("<<obj.first<<","<<obj.second<<")";
    return out;
}


template<typename T, typename T1>
ostream& operator << (ostream &out, map<T, T1> cont)
{
    typename map<T, T1>::const_iterator itr = cont.begin();
    typename map<T, T1>::const_iterator ends = cont.end();

    for(; itr!=ends; ++itr)
        out<<*itr<<" ";
    out<<endl;

    return out;
}


template<typename T>
ostream& operator << (ostream &out, set<T> cont)
{
    typename set<T>::const_iterator itr = cont.begin();
    typename set<T>::const_iterator ends = cont.end();

    for(; itr!=ends; ++itr)
        out<<*itr<<" ";
    out<<endl;

    return out;
}


template<typename T>
ostream& operator << (ostream &out, multiset<T> cont)
{
    typename multiset<T>::const_iterator itr = cont.begin();
    typename multiset<T>::const_iterator ends = cont.end();

    for(; itr!=ends; ++itr)
        out<<*itr<<" ";
    out<<endl;

    return out;
}


template<typename T, template<typename ELEM, typename ALLOC=allocator<ELEM> > class CONT>
ostream& operator << (ostream &out, CONT<T> cont)
{
    typename CONT<T>::const_iterator itr = cont.begin();
    typename CONT<T>::const_iterator ends = cont.end();

    for(; itr!=ends; ++itr)
        out<<*itr<<" ";
    out<<endl;

    return out;
}


template<typename T, unsigned int N, typename CTy, typename CTr>
typename enable_if<!is_same<T, char>::value, basic_ostream<CTy, CTr> &>::type
operator << (basic_ostream<CTy, CTr> &out, const T (&arr)[N])
{
     REP(i, 0, N)
        out<<arr[i]<<" ";
    out<<endl;

    return out;
}


template<typename T>
T gcd(T a, T b)
{
    T min_v = min(a, b);
    T max_v = max(a, b);

    while(min_v)
    {
        T temp = max_v % min_v;
        max_v = min_v;
        min_v = temp;
    }

    return max_v;
}


template<typename T>
T lcm(T a, T b)
{
    return (a*b) / gcd(a, b);
}


template<typename T>
T fast_exp_pow(T base, T exp, T mod)
{
    LL res = 1;

    while(exp)
    {
        if(exp&1)
        {
            res *= base;
            res %= mod;
        }

        exp >>= 1;
        base *= base;
        base %= mod;

    }

    return res % mod;
}

/*#################################################################################################################
#################################################################################################################
#################################################################################################################
#################################################################################################################*/

#define SIZE 100010
#define MAXN 1e16

int T, N;
LL L, A, B, answer;
LL positions[SIZE], memo[SIZE][2];


LL solve(LL ind, int type, LL Tbeg, LL Tend)
{
    //cout<<ind<<" "<<type<<" "<<Tbeg<<" "<<Tend<<endl;
    if(Tend > B  ||  Tbeg < A)
        return MAXN;

    if(ind == N-1)
        return 0;

    LL &ret = memo[ind][type];

    if(ret != -1)
        return ret;

    if(Tend > positions[ind+1])
    {
        LL diff = Tend - positions[ind+1];
        LL ret_1 = (ind+1)*diff + solve(ind+1, 0, Tbeg-diff, positions[ind+1]+L);
        LL ret_2 = diff + solve(ind+1, 1, Tbeg, Tend+L);
        ret = min(ret_1, ret_2);
    }
    else
    {
        LL diff = positions[ind+1] - Tend;
        LL ret_1 = (ind+1)*diff + solve(ind+1, 0, Tbeg+diff, positions[ind+1]+L);
        LL ret_2 = diff + solve(ind+1, 1, Tbeg, Tend+L);
        ret = min(ret_1, ret_2);
    }

    return ret;
}



int main()
{
    scanf("%d", &T);

    while(T--)
    {
        scanf("%d%lld%lld%lld", &N, &L, &A, &B);

        answer = 0;
        ms(memo, -1);

        REP(i, 0, N)
            scanf("%lld", positions+i);

        sort(positions, positions+N);

        REP(i, 1, N-1)
        {
            if(positions[i]+L > positions[i+1]  &&  positions[i+1]-L >= positions[i-1]+L)
            {
                answer += (positions[i]+L - positions[i+1]);
                positions[i] = positions[i+1] - L;
            }
        }

        REP(i, 0, N)
        {
            if(positions[i] < A + i*L)
            {
                answer += (A + i*L) - positions[i];
                positions[i] = A + i*L;
            }

            if(positions[i] > B - (N-i)*L)
            {
                answer += positions[i] - (B - (N-i)*L);
                positions[i] = B - (N-i)*L;
            }
        }

        if(N == 1)
        {
            printf("%lld\n", answer);
            continue;
        }

        positions[0] += L;

        LL diff = abs(positions[0] - positions[1]);
        LL ret_1 = diff + solve(1, 0, positions[1]-L, positions[1]+L);
        LL ret_2 = diff + solve(1, 1, positions[0]-L, positions[0]+L);
        answer += min(ret_1, ret_2);

        printf("%lld\n", answer);
    }

    return 0;
}