import java.util.*;

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


public class DoubleLinkedList implements ILinkedList {
    /* Implement your linked list class here*/
    public static void main(String[] args) {
        // Read the list
        Scanner sc = new Scanner(System.in);
        DoubleLinkedList dll = new DoubleLinkedList();

        // Format the list
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
                dll.add(arr[i]);
            }
        }

        // Read command
        String command = sc.nextLine();
        int element;
        int index;
        Object res;
        switch (command)
        {
            case "add":
                element = sc.nextInt();
                dll.add(element);
                dll.printList();
                break;

            case "addToIndex":
                index = sc.nextInt();
                element = sc.nextInt();

                if (index > dll.size || index < 0){
                    System.out.println("Error");
                    break;
                }

                dll.add(index, element);
                dll.printList();
                break;

            case "get":
                // Read input
                index = sc.nextInt();

                res = dll.get(index);
                if (res == null)
                    break;

                System.out.println(res);
                break;

            case "set":
                // Read input
                index = sc.nextInt();
                element = sc.nextInt();

                if (index < 0 || index > dll.size-1) {
                    System.out.println("Error");
                    break;
                }

                dll.set(index, element);
                dll.printList();
                break;

            case "clear":
                dll.clear();
                dll.printList();
                break;

            case "isEmpty":
                if(dll.isEmpty())
                    System.out.println("True");
                else
                    System.out.println("False");

                break;

            case "remove":
                index = sc.nextInt();
                if (index < 0 || index > dll.size - 1)
                {
                    System.out.print("Error");
                    break;
                }
                dll.remove(index);
                dll.printList();
                break;

            case "sublist":
                // Get the indices
                int frontIndex = sc.nextInt();
                int backIndex = sc.nextInt();
                if (frontIndex < 0 || backIndex < 0 || backIndex > dll.size - 1 || frontIndex > backIndex) {
                    System.out.println("Error");
                    break;
                }
                // Make the sublist
                ILinkedList subList = dll.sublist(frontIndex, backIndex);
                int n = subList.size();

                // Print the subList
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
                if(dll.contains(element))
                {
                    System.out.println("True");
                }
                else
                {
                    System.out.println("False");
                }
                break;

            case "size":
                System.out.print(dll.size());
                break;

            default:
                System.out.println("Error");
        }
    }

    public class Node{
        Object value;
        Node next;
        Node prev;

        Node(Node prev, Object value, Node next){
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    Node head, trailer;
    private int size;

    public DoubleLinkedList(){
        head = new Node(null, null, trailer);
        trailer = new Node(head, null, null);
        size = 0;
    }

    @Override
    public void add(int index, Object element) {
        Node nNode = new Node(null, element, null);

        // Check if the added element is bigger than the (highest index + 1)
        if (index > size || index < 0){
            System.out.println("Error");
            return;
        }

        int i = 0;
        Node currNode = head;

        while (i < index && currNode.next != null){
            currNode = currNode.next;
            i++;
        }

        nNode.prev = currNode;
        nNode.next = currNode.next;

        currNode.next.prev = nNode;
        currNode.next = nNode;

        size++;
    }

    @Override
    public void add(Object element) {
        Node nNode = new Node(trailer.prev, element, trailer);
        trailer.prev.next = nNode;
        trailer.prev = nNode;
        size++;
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index > size-1) {   // Check for underflow
            System.out.println("Error");
            return null;
        }

        int i = 0;
        Node currNode = head.next;

        while (i < index && currNode.next != null){
            currNode = currNode.next;
            i++;
        }

        return currNode.value;
    }

    @Override
    public void set(int index, Object element) {
        if (index < 0 || index > size-1) {   // Check for underflow
            System.out.println("Error");
            return;
        }

        int i = 0;
        Node currNode = head.next;

        while (i < index && currNode.next != null){
            currNode = currNode.next;
            i++;
        }

        currNode.value = element;
    }

    @Override
    public void clear() {
        head.next = null;
        trailer.prev = head;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index > size-1) {   // Check for underflow
            System.out.println("Error");
            return;
        }

        int i = 0;
        Node currNode = head.next;

        while (i < index && currNode.next != null){
            currNode = currNode.next;
            i++;
        }

        currNode.next.prev = currNode.prev;
        currNode.prev.next = currNode.next;
        currNode.prev = null;
        currNode.next = null;

        size--;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ILinkedList sublist(int fromIndex, int toIndex) {
        DoubleLinkedList subDLL = new DoubleLinkedList();
        for (int i = fromIndex; i <= toIndex; i++){
            subDLL.add(this.get(i));
        }

        return subDLL;
    }

    @Override
    public boolean contains(Object o) {
        Node currNode = head.next;
        for (int i = 0; i < size; i++){
            if (currNode.value.equals(o))
                return true;

            currNode = currNode.next;
        }
        return false;
    }

    public void printList() {
        Node currNode = head.next;
        int i = 0;
        System.out.print("[");
        while (i < size)
        {
            if (currNode != head.next)
            {
                System.out.print(", ");
            }
            System.out.print(currNode.value);
            currNode = currNode.next;
            i++;
        }
        System.out.print("]");
    }

}