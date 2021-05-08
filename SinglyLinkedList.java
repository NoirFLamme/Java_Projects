
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

interface ILinkedList {
    /**
     * Inserts a specified element at the specified position in the list.
     * @param index
     * @param element
     */
    public void add(int index, Object element);
    /**
     * Inserts the specified element at the end of the list.
     * @param element
     */
    public void add(Object element);
    /**
     * @param index
     * @return the element at the specified position in this list.
     */
    public Object get(int index);

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * @param index
     * @param element
     */
    public void set(int index, Object element);
    /**
     * Removes all of the elements from this list.
     */
    public void clear();
    /**
     * @return true if this list contains no elements.
     */
    public boolean isEmpty();
    /**
     * Removes the element at the specified position in this list.
     * @param index
     */
    public void remove(int index);
    /**
     * @return the number of elements in this list.
     */
    public int size();
    /**
     * @param fromIndex
     * @param toIndex
     * @return a view of the portion of this list between the specified fromIndex and toIndex, inclusively.
     */
    public ILinkedList sublist(int fromIndex, int toIndex);
    /**
     * @param o
     * @return true if this list contains an element with the same value as the specified element.
     */
    public boolean contains(Object o);
}



public class SingleLinkedList implements ILinkedList {
    public class Node{
        Object element;
        Node next;
        public Node(Object i, Node n){
            element = i;
            next = n;
        }
    }

    Node head;
    int size;
    public SingleLinkedList()
    {
        head = null;
        size = 0;
    }

    public void add(int index, Object element)
    {
        Node v = new Node(element, null);
        Node temp_v = head;
        Node temp_u = head;
        if (index == 0 && size == 0)
        {
            head = v;
            size += 1;
            return;
        }
        else if(index == 0)
        {
            v.next = head;
            head = v;
            size += 1;
            return;
        }
        for (int i = 0; i < index && temp_v.next != null; i++)
        { 
            temp_u = temp_v;
            temp_v = temp_v.next;
        }
        temp_u.next = v;
        v.next = temp_v;
        size += 1;
    }

    public void add(Object element) {
        Node v = new Node(element, null);
        if (size == 0)
        {
            head = v;
            size += 1;
        }
        else{
            Node temp_v = head;
            while (temp_v.next != null)
            {
                temp_v = temp_v.next;
            }
            temp_v.next = v;
            size += 1;
        }
    }

    public Object get(int index)
    {
        Node v = head;
        for (int i = 0; i < index && v.next != null ; i++)
        {
            v = v.next;
        }
        return v.element;
    }

    public void set(int index, Object element)
    {
        Node v = head;
        for (int i = 0; i < size && v.next != null; i++)
        {
                if (i == index)
                {
                    v.element = element;
                    return;
                }
                v = v.next;
        }
    }

    public void clear()
    {
        // Node v = head;
        // while(v.next!=null)
        // {
        //     head = head.next;
        //     v.next = null;
        //     v = head;
        // }
        head = null;
        size = 0;
    }


    public boolean isEmpty()
    {
       return (size == 0);
    }


    public void remove(int index)
    {
        Node y = head;
        Node v = head;
        if (index == 0)
        {
            head = head.next;
            v.next = null;
            size = size - 1;
            return;
        }
        for(int i = 0; i < index && y.next != null; i++)
        {
                v = y;
                y = y.next;
        }
        v.next = y.next;
        y.next = null;
        size = size - 1;
    }

    public int size()
    {
        return size;
    }

    public ILinkedList sublist(int fromIndex, int toIndex)
    {
        SingleLinkedList sub = new SingleLinkedList();
        Node v = head;
        Node u;
        for (int i = fromIndex; i <= toIndex; i++)
        {
            sub.add(this.get(i));
        }
        return sub;
    }

    public boolean contains(Object o)
    {
        Node v = head;

        for (int i = 0; i < size; i++)
        {
            if (v.element.equals(o))
            {
                return true;
            }
            v = v.next;  
        }
        return false;
    }

    public void printL()
    {
        Node v = head;
        int i = 0;
        System.out.print("[");
        while (i < size)
        {
            if (v != head)
            {
                System.out.print(", ");
            }
            System.out.print(v.element);
            v = v.next;
            i++;
        }
        System.out.print("]");
    }

    /* Implement your linked list class here*/
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. */
        Scanner sc = new Scanner(System.in);
        SingleLinkedList ll = new SingleLinkedList();
        String temp = sc.nextLine().replaceAll("\\[|\\]", "");
        String[] s = temp.split(", ");
        int[] arr = new int[s.length];
        if (s.length == 1 && s[0].isEmpty())
            arr = new int[]{};
        else {
            for(int i = 0; i < s.length; ++i)
                arr[i] = Integer.parseInt(s[i]);
            for (int i = 0; i < s.length; i++)
            {
                ll.add(arr[i]);

            }
        }

        String command = sc.nextLine();
        int element;
        int index;
        Object res;
        switch (command)
        {
            case "add":
                element = sc.nextInt();
                ll.add(element);
                ll.printL();
                break;
             case "addToIndex":
                index = sc.nextInt();
                if (index > ll.size - 1 || index < 0)
                {
                    System.out.print("Error");
                    break;
                }
                element = sc.nextInt();
                ll.add(index, element);
                ll.printL();
                break;
            case "get":
                index = sc.nextInt();
                if (index > ll.size - 1 || index < 0)
                {
                    System.out.print("Error");
                    break;
                }
                res = ll.get(index);
                if (res == null)
                    break;
                System.out.println(res);
                break;
            case "set":

                index = sc.nextInt();
                if (index > ll.size - 1 || index < 0)
                {
                    System.out.print("Error");
                    break;
                }
                element = sc.nextInt();
                ll.set(index, element);
                ll.printL();
                break;
            case "clear":
                // if (ll.size == 0)
                // {
                //     System.out.println("Error");
                //     break;
                // }
                ll.clear();
                ll.printL();
                break;
            case "isEmpty":
                if(ll.isEmpty())
                {
                    System.out.println("True");
                }
                else
                {
                    System.out.println("False");
                }
                break;
            case "remove":
                index = sc.nextInt();
                if (index > ll.size - 1 || index < 0)
                {
                    System.out.print("Error");
                    break;
                }
                ll.remove(index);
                ll.printL();
                break;
            case "sublist":
                int frontIndex = sc.nextInt();
                int backIndex = sc.nextInt();
                if (frontIndex > ll.size - 1 || backIndex > ll.size - 1 || frontIndex > backIndex || frontIndex < 0 || backIndex < 0)
                {
                    System.out.print("Error");
                    break;
                }
                
                ILinkedList subList = ll.sublist(frontIndex,backIndex);
                if (subList == null)
                    break;
                int n = subList.size();
                System.out.print("[");
                for (int i = 0; i < n; i++)
                {
                    if (i != 0)
                    {
                        System.out.print(", ");
                    }
                    System.out.print(subList.get(i));
                }
                System.out.print("]");
                break;
            case "contains":
                element = sc.nextInt();
                if(ll.contains(element))
                {
                    System.out.println("True");
                }
                else
                {
                    System.out.println("False");
                }
                break;
            case "size":
                System.out.print(ll.size());
                break;
            default:
                System.out.println("Error");
        }
    }
}
