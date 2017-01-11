#include <bits/stdc++.h>
using namespace std;

const int maxx = (int) 1e6;
int n, q, arr[maxx];
vector<int> tree[maxx];

void build(int node, int treeStart, int treeEnd)
{
    if (treeStart == treeEnd)
    {
        tree[node].push_back(arr[treeStart]);

        return;
    }

    int mid = treeStart + treeEnd >> 1;

    build(node << 1, treeStart, mid);
    build((node << 1) + 1, mid + 1, treeEnd);

    int l, r, x, y;

    l = (int) tree[node << 1].size();
    r = (int) tree[(node << 1) + 1].size();
    x = y = 0;

    while (x < l || y < r)
    {
        if (tree[node << 1][x] < tree[(node << 1) + 1][y])
        {
            tree[node].push_back(tree[node << 1][x++]);

            if (x == l)
            {
                while (y < r)
                    tree[node].push_back(tree[(node << 1) + 1][y++]);
            }
        }
        else
        {
            tree[node].push_back(tree[(node << 1) + 1][y++]);

            if (y == r)
            {
                while (x < l)
                    tree[node].push_back(tree[node << 1][x++]);
            }
        }
    }
}

int findBigger(int node, int val)
{
    int size, left, right, mid;

    size = (int) tree[node].size();
    left = 0;
    right = size - 1;

    while (left <= right)
    {
        mid = left + right >> 1;

        if (tree[node][mid] <= val)
        {
            if (mid == size - 1)
                return 0;

            if (tree[node][mid + 1] > val)
                return size - mid - 1;

            left = mid + 1;
        } else
        {
            if (mid == 0)
                return size;

            if (tree[node][mid - 1] <= val)
                return size - mid;

            right = mid - 1;
        }
    }

    return 0;
}

int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int val)
{
    if (treeStart > rangeEnd || treeEnd < rangeStart)
        return 0;

    if (treeStart >= rangeStart && treeEnd <= rangeEnd)
        return findBigger(node, val);

    int mid = treeStart + treeEnd >> 1;
    int left, right;

    left = query(node << 1, treeStart, mid, rangeStart, rangeEnd, val);
    right = query((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd, val);

    return left + right;
}

void computeIndices(vector<int> v)
{
    map<int, int> m;
    int counter = 1;

    for (int i = 0; i < n; i++)
        if (!m.count(v[i]))
            m[v[i]] = counter++;

    for (int i = 0; i < n; i++)
        arr[i] = m[arr[i]] - 1;
}

int main()
{
    scanf("%d%d", &n, &q);

    vector<int> v;

    for (int i = 0; i < n; i++)
    {
        scanf("%d", &arr[i]);
        v.push_back(arr[i]);
    }

    sort(v.begin(), v.end());
    computeIndices(v);
    build(1, 0, n - 1);

    while (q--)
    {
        int low, high, left, right, req, k, ans;

        low = 0;
        high = n - 1;
        scanf("%d%d%d", &left, &right, &k);
        left--;
        right--;
        req = right - left + 1 - k;
        ans = 0;

        while (low <= high)
        {
            int mid = low + high >> 1;
            int cnt = query(1, 0, n - 1, left, right, mid);

            if (cnt <= req)
            {
                ans = mid;
                high = mid - 1;
            } else
                low = mid + 1;
        }

        printf("%d\n", v[ans]);
    }

    return 0;
}