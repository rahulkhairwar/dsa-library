#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <algorithm>

#define MAXN 3000000
#define MODM 1000000007LL
#define ll long long

using namespace std;

int t, n, a[MAXN+5], b[MAXN+5], tot_b;
ll ans, tmp, tmp2, x, y, d, cnt[MAXN+5], fact[MAXN+15], df[MAXN+15];

void f( ll a, ll b ) {

    if ( b == 0 ) {
        d = a;
        x = 1;
        y = 0;
    }else {
        f( b, a % b );
        tmp = x;
        x = y;
        y = tmp - ( (ll)a/(ll)b) * y;
    }

}

ll pow( ll x, ll b, ll m ) {

    ll ret = 1LL;

    while ( b > 0LL ) {
        if ( b % 2LL == 1LL ) {
            ret = ( ret * x ) % m;
        }
        x = ( x * x ) % m;
        b /= 2LL;
    }

    return ( ret );


}

ll modInverse( ll a, ll m ) {
    //f(a,m);
    //return ( ( x%m + m ) % m );
    return pow( a, m-2, m );
}

ll calc( ll tmp2 ) {
    ll ans;

    ans = (tmp2 * ( (tmp2-1+MODM) % MODM ) ) % MODM;
    ans = (ans * modInverse(2,MODM)) % MODM;

    return ans;
}

ll c( ll n, ll r ) {
    ll ret = 0;

    ret = fact[n];
    ret = (ret * modInverse(fact[r], MODM)) % MODM;
    ret = ((ret%MODM) * (modInverse(fact[n-r] % MODM,MODM)) ) % MODM;
    ret = ( ret%MODM + MODM ) % MODM;

    return ( ret );
}

ll sn( ll n ) {
    //return df[n];
    ll a = fact[n];
    ll b = modInverse( fact[n/2LL], MODM );
    ll c = pow( modInverse(2LL,MODM), n/2LL, MODM);
    //ll c = modInverse( pow( 2LL, n/2LL, MODM), MODM );
    ll tmp = ( (a%MODM) * (b%MODM) ) % MODM;
    tmp = ( (tmp%MODM) * (c%MODM) ) % MODM;
    return ( tmp );
}

ll sn2( ll n ) {
    //return df[n];
    ll a = fact[n];
    ll b = modInverse( fact[n/2LL], MODM );
    // /ll c = pow( modInverse(2LL,MODM), n/2LL, MODM);
    ll c = modInverse( pow( 2LL, n/2LL, MODM), MODM );
    ll tmp = ( (a%MODM) * (b%MODM) ) % MODM;
    tmp = ( (tmp%MODM) * (c%MODM) ) % MODM;
    return ( tmp );
}


int main( ) {

    fact[0] = 1;
    df[0] = 1;
    df[1] = 1;

    for ( int i = 1; i <= MAXN+5; ++i ) {
        fact[i] = ( (fact[i-1]%MODM) * (i%MODM) ) % MODM;
        fact[i] = ( fact[i]%MODM + MODM ) % MODM;
    }

    ll val = 1;

    for ( int i = 1; 2*i <= MAXN+5; ++i ) {
        df[2*i-1] = val;  //;( (fact[i-1];%MODM) * (i%MODM) ) % MODM;
        df[2*i] = val;
        ll tmp = (2*i+1);
        val = ( (val%MODM) * (tmp%MODM) ) % MODM;
        val = ( val%MODM + MODM ) % MODM;
    }

    /*//Test if they are same
    for ( int i = 0; i <= MAXN; ++i ) {
        cout << sn(i) << " " << sn2(i) << endl;
        if ( sn(i) != sn2(i) ) {
            cout << "do not match :(\n";
            break;
        }

    }

    exit(0);
    */

    scanf( "%d", &t );

    while ( t-- ) {

        scanf( "%d", &n );

        for ( int i = 1; i <= n; ++i ) {
            scanf( "%d", &a[i] );
            ++cnt[a[i]];
        }

        sort( a+1, a+n+1 );

        /*
        for ( int i = 1; i <= n;++i)
            cout << sn(i) << endl;
        cout << endl;
        */
        //(n!) / ( (n/2)!   * (2^(n/2) ) )
        int cur = 0;
        tot_b = 0;
        for ( int i = 1; i <= n; ++i ) {
            if ( i == 1 || a[cur] != a[i] ) {
                cur = i;
                b[++tot_b] = a[i];
            }
        }

        ans = 1;
        //cnt[0] = 1;

        for ( int i = tot_b; i >= 1; --i ) {
            if ( cnt[ b[i] ] % 2 == 0 ) {
                ans = ( (ans%MODM) * (sn( cnt[ b[i] ] )%MODM) ) % MODM;
            }else {
                //cnt[ current_value ] * cnt[ next_value ] * calc( cnt[next_value] - 1)?
                ans = ( (ans%MODM) * (cnt[ b[i] ]%MODM) )%MODM;
                ans = ( (ans%MODM) * (cnt[ b[i-1] ]%MODM) ) % MODM;
                cnt[ b[i-1] ] = max( 0LL, cnt[ b[i-1] ]-1LL );
            }
        }

        ans = ( (ans%MODM) + MODM ) % MODM;

        cout << ans << endl;

        for ( int i = 1; i <= n; ++i ) {
            cnt[ a[i] ] = 0;
        }

        //exit(0);

    }

    return 0;

}
