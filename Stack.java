


import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

interface IStack {

    /*** Removes the element at the top of stack and returnsthat element.
     * @return top of stack element, or through exception if empty
     */

    public Object pop();

    /*** Get the element at the top of stack without removing it from stack.
     * @return top of stack element, or through exception if empty
     */

    public Object peek();

    /*** Pushes an item onto the top of this stack.
     * @param object to insert*
     */

    public void push(Object element);

    /*** Tests if this stack is empty
     * @return true if stack empty
     */
    public boolean isEmpty();

    public int size();
}



public class MyStack implements IStack {
    public class Node {
        Object element;
        Node next;

        public Node(Object i, Node n) {
            element = i;
            next = n;
        }
    }
    Node top;
    int size;
    public MyStack()
    {
        top = null;
        size = 0;
    }


    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        if(top==null)
            return true;
        return false;
    }

    public void push(Object element)
    {
        Node v = new Node(element,top);
        top = v;
        size += 1;

    }

    public Object peek()
    {
        return top.element;
    }

    public Object pop()
    {
        Object temp = top.element;
        top = top.next;
        size -= 1;
        return temp;
    }

   public void printL()
    {
        Node v = top;
        int i = 0;
        System.out.print("[");
        while (i < size)
        {
            if (v != top)
            {
                System.out.print(", ");
            }
            System.out.print(v.element);
            v = v.next;
            i++;
        }
        System.out.print("]");
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. */
        Scanner sc = new Scanner(System.in);
        MyStack ll = new MyStack();
        String temp = sc.nextLine().replaceAll("\\[|\\]", "");
        String[] s = temp.split(", ");
        int[] arr = new int[s.length];
        if (s.length == 1 && s[0].isEmpty())
            arr = new int[]{};
        else {
            for (int i = 0; i < s.length; ++i)
                arr[i] = Integer.parseInt(s[i]);
            for (int i = s.length - 1; i >= 0; i--) {
                ll.push(arr[i]);
            }
        }
        String command = sc.nextLine();
        int element;
        switch (command)
        {
            case "push":
                element = sc.nextInt();
                ll.push(element);
                ll.printL();
                break;
            case "pop":
                if (ll.isEmpty())
                {
                    System.out.println("Error");
                    return;
                }
                ll.pop();
                ll.printL();
                break;
            case "peek":
                if (ll.isEmpty())
                {
                    System.out.println("Error");
                    return;
                }
                System.out.println(ll.peek());
                break;
            case "isEmpty":
                if (ll.isEmpty())
                {
                    System.out.println("True");
                }
                else
                {
                    System.out.println("False");
                }
                break;
            case "size":
                System.out.println(ll.size());
                break;
            default:
                System.out.println("Error");
        }
    }

}