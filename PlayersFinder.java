import java.awt.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.ArrayList;


interface IPlayersFinder {

    /**
     * Search for players locations at the given photo
     * @param photo
     *     Two dimension array of photo contents
     *     Will contain between 1 and 50 elements, inclusive
     * @param team
     *     Identifier of the team
     * @param threshold
     *     Minimum area for an element
     *     Will be between 1 and 10000, inclusive
     * @return
     *     Array of players locations of the given team
     */
    java.awt.Point[] findPlayers(String[] photo, int team, int threshold);
}

public class PlayersFinder implements IPlayersFinder{

    public java.awt.Point[] findPlayers(String[] photo, int team, int threshold)
    {
        ArrayList<java.awt.Point> points = new ArrayList<java.awt.Point>();

        for(int i = 0; i < photo.length; i++)
        {
            for(int j = 0; j < photo[0].length(); j++)
            {
                if(photo[i].charAt(j) == team + 48)
                {
                    java.awt.Point tempo = new Point(i, j);
                    points.add(tempo);
                }
            }
        }

        ArrayList<java.awt.Point> res = new ArrayList<java.awt.Point>();

        int success = 0;
        int z = 1;

        int area;
        boolean flag = false;
        while(points.size() > 0)
        {
           int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
           ArrayList<java.awt.Point> pChain = new ArrayList<>();
           pChain.add(points.get(0));
           points.remove(0);
           area = 4;
           flag = false;
           z = 1;
           // if (points.size() == 0 && area >= threshold)
           // {
           //     res.add(new Point(2 * (pChain.get(0).x) + 1, 2 * (pChain.get(0).y) + 1));
           //     success+=1;
           // }
           for (int i = 0; i < points.size(); i++){
               for (int j = 0; j < z; j++)
               {
                   if(((Math.abs(pChain.get(j).getX() - points.get(i).getX()) == 1) && (Math.abs(pChain.get(j).getY()
                           - points.get(i).getY()) == 0)) || ((Math.abs(pChain.get(j).getY() - points.get(i).getY()) == 1)
                            && (Math.abs(pChain.get(j).getX() - points.get(i).getX()) == 0))){
                        pChain.add(points.get(i));
                        points.remove(i);
                        area += 4;
                        z++;
                        if(area >= threshold)
                        {
                            flag = true;
                        }
                        i = -1;
                        break;
                   }
               }
           }
            if(pChain.size()==1 && area >= threshold) flag= true;
           int x = 0;
           if (flag == true)
           {
               for(java.awt.Point count:pChain)
               {
                   if(count.getX() < minX)
                   {
                       minX = count.x;
                   }
                   if(count.getX() >maxX)
                   {
                       maxX = count.x;
                   }
                   if(count.getY() < minY)
                   {
                       minY = count.y;
                   }
                   if(count.getY() > maxY)
                   {
                       maxY = count.y;
                   }
               }
               res.add(new Point((minX + maxX + 1), (minY + maxY + 1)));
               success+=1;
           }
        }
        for (int l = 0; l < success; l++)
        {
            for (int o = 1; o < success; o++)
            {
                if (res.get(o - 1).y > res.get(o).y)
                {
                    java.awt.Point swap = res.get(o - 1);
                    res.set(o - 1, res.get(o));
                    res.set(o, swap);
                }
                else if(res.get(o - 1).y == res.get(o).y)
                {
                    if (res.get(o - 1).x > res.get(o).x)
                    {
                        java.awt.Point swap = res.get(o - 1);
                        res.set(o - 1, res.get(o));
                        res.set(o, swap);
                    }
                }
            }
        }
        java.awt.Point[] aRes = new java.awt.Point[res.size()];
        aRes = res.toArray(aRes);
        return aRes;
    }

    public static void main(String[] args) {
    // write your code here
        Scanner s = new Scanner(System.in);

        String temp = s.nextLine();
        String[] temp2 = temp.split(", ");

        int x = Integer.parseInt(temp2[0]);
        int y = Integer.parseInt(temp2[1]);

        String[] photo = new String[x];

        for(int i = 0; i < x; i++)
        {
           photo[i] = s.nextLine();
        }

        int color = s.nextInt();
        int area = s.nextInt();

        java.awt.Point[] players = new PlayersFinder().findPlayers(photo, color, area);

        System.out.print("[");
        for (int l = 0; l < players.length; l++) {
            if(l != 0)
                System.out.print(", ");
            System.out.print("(" + players[l].y + ", " + players[l].x + ")");

        }
        System.out.print("]");
    }
}