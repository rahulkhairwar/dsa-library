package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class AddComma
{
    public static void main(String[] args) throws IOException
    {
//        Scanner in = new Scanner(System.in);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Set<String> set = new HashSet<>();

        while (true)
        {
//            String s = in.next();
            String s = in.readLine();

            System.out.println("s : " + s);

            if (s.contains(" "))
                continue;

            if (s.equals("end"))
                break;

            set.add(s);
        }

        System.out.println("size : " + set.size());

        Iterator<String> iterator = set.iterator();

        while (iterator.hasNext())
        {
            System.out.print(iterator.next() + ", ");
        }
    }

}

/*

vd1997, akundusaltlake, codegeass1, rohit_12, Rohit.896, adilAsh, saipavan13, amruth27m, devikasudheer23, ksrikanthcnc, joshy.divya, thahseen, AlanThomas, AnupamAsok, dhivya_sree, archana, abbie97, ArunJoseph, hemanthmandava, rajeevrmenon97, ma6, Naviunni, jsnkrm, olivethomas, saahilk, mhmubarack, Abin_Das, Athulan, abythomas, MirrorX, uddhav2509, Akansk, Nandu, bhagyasri, csai, gottacodeemall, neelimaj, cijomjose, ameenp, archanan3

B150083CS
B150083CS
B150103CS
B150103CS
B150398CS
B150398CS
B150895CS
B150895CS
B150221CS
B150221CS
B150372CS
B150115CS
B150243CS
B150310CS
B151040CS
B150933CS
B150656CS
B150593CS
B150523CS
B150204CS
B150144CS
B150227CS
B150470CS
B150167CS
B150744CS
B150196CS
B150877CS
b150281cs
B150337CS
B150232CS
B150336CS
B150202CS
B150272cs
B150102CS
B150107CS
B150146CS
B150587CS
B150453CS
B150531CS
B150733CS
B150805CS
B150264CS
B150810CS
B150174CS
B150878CS
B150451CS
B150810CS
B150487CS
B150740CS
B150635CS
B150201CS
B150274CS
B150334CS
B150691CS
B150151EE
B150281CS

 */
