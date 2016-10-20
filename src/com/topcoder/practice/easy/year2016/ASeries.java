package com.topcoder.practice.easy.year2016;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ASeries
{
    public static int longest(int[] values)
    {
        int n = values.length;
        int max = 1;
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < n; i++)
            set.add(values[i]);

        Arrays.sort(values);

        for (int i = 0; i < n - 1; i++)
        {
            for (int j = i + 1; j < n; j++)
            {
                if (values[j] == values[i])
                    continue;

                int curr = values[j] - values[i];
                int next = values[j] + curr;
                int size = 2;

                while (set.contains(next))
                {
                    next += curr;
                    size++;
                }

                max = Math.max(max, size);
            }
        }

        for (int i = 0; i < n;)
        {
            int curr = values[i];
            int count = 0;

            while (i < n && values[i] == curr)
            {
                i++;
                count++;
            }

            max = Math.max(max, count);
        }

        return max;
    }

}