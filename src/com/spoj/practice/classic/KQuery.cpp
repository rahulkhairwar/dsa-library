#include <bits/stdc++.h>
using namespace std;
#define MAX 30001

int bit[30009];

void add (int index)
{
    while(index <= MAX)
    {
        bit[index] += 1;
        index += (index & -index);
    }
}

int query (int index)
{
    int answer = 0;

    while(index > 0)
    {
        answer += bit[index];
        index -= index & -index;
    }

    return answer;
}

struct Element
{
    int value , pos;
};

struct Query
{
    int left, right, k, pos;
};

bool compare (const Element &a , const Element &b)
{
    return a.value > b.value;
}

bool cmp(const Query &a , const Query &b)
{
    return a.k > b.k;
}

int main()
{
    int n;
    scanf("%d", &n);
    Element elements[n + 1];

    for(int i = 1; i <= n; i++)
	{
		scanf("%d", &elements[i].value);
        elements[i].pos = i;
    }

    sort(elements + 1, elements + n + 1, compare);

    int q;
    scanf("%d",&q);
    Query queries[q];

    for(int i = 0; i < q; i++)
    {
    	scanf("%d%d%d", &queries[i].left, &queries[i].right, &queries[i].k);
    	queries[i].pos = i;
    }

    int result[q];
    sort(queries, queries + q, cmp);

    int ans[q];
    int i = 1;

    for (int j = 0; j < q; j++)
    {
    	while (i <= n && elements[i].value > queries[j].k)
    	{
    		add(elements[i].pos);
    		i++;
    	}

    	ans[queries[j].pos] = query(queries[j].right) - query(queries[j].left - 1);
   	}

   	for (int i = 0; i < q; i++)
   		printf("%d\n", ans[i]);

    return 0;
}