import java.util.Scanner;
import java.math.*;
import java.util.Scanner;

interface ILinkedList {

    public void add(int index, Object element);

    public void add(Object element);

    public Object get(int index);

    public void set(int index, Object element);

    public void clear();

    public boolean isEmpty();

    public void remove(int index);

    public int size();

    public ILinkedList sublist(int fromIndex, int toIndex);

    public boolean contains(Object o);
}

interface IPolynomialSolver {
    /**
     * Set polynomial terms (coefficients & exponents)
     * @param poly: name of the polynomial
     * @param terms: array of [coefficients][exponents]
     */
    void setPolynomial(char poly, int[][] terms);
    /**
     * Print the polynomial in ordered human readable representation
     * @param poly: name of the polynomial
     * @return: polynomial in the form like 27x^2+x-1
     */
    String print(char poly);
    /**
     * Clear the polynomial
     * @param poly: name of the polynomial
     */
    void clearPolynomial(char poly);
    /**
     * Evaluate the polynomial
     * @param poly: name of the polynomial
     * @param value: the polynomial constant value
     * @return the value of the polynomial
     */
    float evaluatePolynomial(char poly, float value);
    /**
     * Add two polynomials
     * @param poly1: first polynomial
     * @param poly2: second polynomial
     * @return the result polynomial
     */
    int[][] add(char poly1, char poly2);
    /**
     * Subtract two polynomials
     * @param poly1: first polynomial
     * @param poly2: second polynomial
     * @return the result polynomial
     */
    int[][] subtract(char poly1, char poly2);
    /**
     * Multiply two polynomials
     * @param poly1: first polynomial
     * @param poly2: second polynomial
     * @return: the result polynomial
     */
    int[][] multiply(char poly1, char poly2);
}

public class Solution implements IPolynomialSolver{

    public class PolynomialTerm {
        int coefficient;
        int exponent;

        PolynomialTerm(int coefficient, int exponent){
            this.coefficient = coefficient;
            this.exponent = exponent;
        }
    }

    public class Node {
        Object element;
        Node next;

        public Node(Object i, Node n) {
            element = i;
            next = n;
        }
    }

    public class SingleLinkedList implements ILinkedList {
        Node head;
        int size;

        public SingleLinkedList() {
            head = null;
            size = 0;
        }

        public void add(int index, Object element) {
            Node v = new Node(element, null);
            Node temp_v = head;
            Node temp_u = head;
            if (index == 0 && size == 0) {
                head = v;
                size += 1;
                return;
            } else if (index == 0) {
                v.next = head;
                head = v;
                size += 1;
                return;
            }
            for (int i = 0; i < index && temp_v.next != null; i++) {
                temp_u = temp_v;
                temp_v = temp_v.next;
            }
            temp_u.next = v;
            v.next = temp_v;
            size += 1;
        }

        public void add(Object element) {
            Node v = new Node(element, null);
            if (size == 0) {
                head = v;
            } else {
                Node temp_v = head;
                while (temp_v.next != null) {
                    temp_v = temp_v.next;
                }
                temp_v.next = v;
            }
            size += 1;
        }

        public Object get(int index) {
            Node v = head;
            for (int i = 0; i < index && v.next != null; i++) {
                v = v.next;
            }
            return v.element;
        }

        public void set(int index, Object element) {
            Node v = head;
            for (int i = 0; i < size && v.next != null; i++) {
                if (i == index) {
                    v.element = element;
                    return;
                }
                v = v.next;
            }
        }

        public void clear() {
            head = null;
            size = 0;
        }

        public boolean isEmpty() {
            return (size == 0);
        }

        public void remove(int index) {
            Node y = head;
            Node v = head;
            if (index == 0) {
                head = head.next;
                v.next = null;
                size = size - 1;
                return;
            }
            for (int i = 0; i < index && y.next != null; i++) {
                v = y;
                y = y.next;
            }
            v.next = y.next;
            y.next = null;
            size = size - 1;
        }

        public int size() {
            return size;
        }

        public ILinkedList sublist(int fromIndex, int toIndex) {
            SingleLinkedList sub = new SingleLinkedList();
            Node v = head;
            Node u;
            for (int i = fromIndex; i <= toIndex; i++) {
                sub.add(this.get(i));
            }
            return sub;
        }

        public boolean contains(Object o) {
            Node v = head;

            for (int i = 0; i < size; i++) {
                if (v.element.equals(o)) {
                    return true;
                }
                v = v.next;
            }
            return false;
        }

        public void printL() {
            Node v = head;
            int i = 0;
            System.out.print("[");
            while (i < size) {
                if (v != head) {
                    System.out.print(", ");
                }
                System.out.print(v.element);
                v = v.next;
                i++;
            }
            System.out.print("]");
        }

    }

    int[][] A;
    int[][] B;
    int[][] C;

    public static void main(String[] args) {
        Solution main = new Solution();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {

            String command;
            boolean flag = true;
            command = sc.nextLine();

            switch (command){
                case "set":
                    // Get input
                    char variable = sc.nextLine().toUpperCase().charAt(0);
                    if(variable < 'A' || variable > 'C')
                    {
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    String list = sc.nextLine().replaceAll("\\[|\\]", ""); // [1,3,4]

                    // Format it into a 1D array
                    String[] tempArray = list.split(",");
                    int[] coefficients = new int[tempArray.length];
                    try{
                        for (int i = 0; i < tempArray.length; ++i)
                            coefficients[i] = Integer.parseInt(tempArray[i]);
                    }
                    catch(Exception e)
                    {
                        System.out.println("Error");
                        flag = false;
                        break;
                    }

                    // Format it into a 2D array
                    int[][] terms = new int[coefficients.length][2];
                    int polyDegree = coefficients.length - 1;

                    for (int i = 0; i < coefficients.length; i++) {
                        terms[i][0] = coefficients[i];
                        terms[i][1] = polyDegree - i; // exponent
                    }

                    // Set the polynomial
                    main.setPolynomial(variable, terms);
                    break;

                case "print":
                    variable = sc.nextLine().toUpperCase().charAt(0);
                    if(variable < 'A' || variable > 'C')
                    {
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    System.out.println(main.print(variable));
                    break;

                case "add":
                    char var1 = sc.nextLine().toUpperCase().charAt(0);
                    if(var1 < 'A' || var1 > 'C')
                    {
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    char var2 = sc.nextLine().toUpperCase().charAt(0);
                    if(var2 < 'A' || var2 > 'C')
                    {
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    int[][] R = main.add(var1,var2);

                    if (R == null){
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    else
                        main.printResult(R);
                    break;

                case "sub":
                    var1 = sc.nextLine().toUpperCase().charAt(0);
                    if(var1 < 'A' || var1 > 'C')
                    {
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    var2 = sc.nextLine().toUpperCase().charAt(0);
                    if(var2 < 'A' || var2 > 'C')
                    {
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    R = main.subtract(var1,var2);

                    if (R == null){
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    else
                        main.printResult(R);
                    break;

                case "mult":
                    var1 = sc.nextLine().toUpperCase().charAt(0);
                    if(var1 < 'A' || var1 > 'C')
                    {
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    var2 = sc.nextLine().toUpperCase().charAt(0);
                    if(var2 < 'A' || var2 > 'C')
                    {
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    R = main.multiply(var1,var2);

                    if (R == null){
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    else
                        main.printResult(R);
                    break;

                case "clear":
                    variable = sc.nextLine().toUpperCase().charAt(0);
                    if(variable < 'A' || variable > 'C')
                    {
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    main.clearPolynomial(variable);
                    System.out.print("[]");
                    break;

                case "eval":
                    variable = sc.nextLine().toUpperCase().charAt(0);
                    if(variable < 'A' || variable > 'C')
                    {
                        System.out.println("Error");
                        flag = false;
                        break;
                    }
                    float x = sc.nextFloat();
                    float result = main.evaluatePolynomial(variable, x);
                    System.out.println((int)result);
                    break;

                default:
                    System.out.println("Error");
                    flag = false;
                    break;
            }
            if(flag == false)
            {
                break;
            }
        }


    }

    @Override
    public void setPolynomial(char poly, int[][] terms) {
        // Save into one of the three 2D Arrays
        int length = terms.length;

        switch (poly) {
            case 'A':
                A = new int[length][2];
                for (int i = 0; i < length; i++) {
                    A[i][0] = terms[i][0];
                    A[i][1] = terms[i][1];
                }
                break;

            case 'B':
                B = new int[length][2];
                for (int i = 0; i < length; i++) {
                    B[i][0] = terms[i][0];
                    B[i][1] = terms[i][1];
                }
                break;

            case 'C':
                C = new int[length][2];
                for (int i = 0; i < length; i++) {
                    C[i][0] = terms[i][0];
                    C[i][1] = terms[i][1];
                }
                break;

            default:
                System.out.println("Error");
        }
    }

    @Override
    public String print(char poly) {
        String output = new String();
        int[][] terms = new int[0][];

        switch (poly){
            case 'A': terms = A;    break;
            case 'B': terms = B;    break;
            case 'C': terms = C;    break;
            default:    //TODO error-checking ;
        }

        if (terms == null)
            return null;

        for (int i = 0; i < terms.length; i++){
            // Add the coefficient
            if (terms[i][0] == 0) // if the coefficient is zero >> skip
                continue;
            else if (terms[i][0] == 1 || terms[i][0] == -1){    // if the coefficient is one >> don't add it
                if (terms[i][1] == 0) // special case where its a coefficient is one and its a constant
                    output += terms[i][0];
                else    // otherwise add nothing
                    ;
            }
            else{
                if (terms[i][0] < 0 && output.length() != 0){   // If its negative , remove the plus sign
                    output = output.substring(0, output.length()-1);
                }
                output += terms[i][0];
            }


            // Add the x^exponent
            if (terms[i][1] == 0) // Check if the term is the constant (exponent == 0)
                ;   // Add only the coefficient
            else if (terms[i][1] == 1) // if its (x ^ 1)
                output += "x" ;
            else
                output += "x" + "^" + terms[i][1];

            // Add the sign
            output +=  "+";

        }

        if (output.length() == 0)
            output += 0;

        // Remove the last sign if it exists alone
        char lastChar = output.charAt(output.length()-1);
        if (lastChar == '+' || lastChar == '-')
            output = output.substring(0, output.length()-1);


        return output;
    }

    @Override
    public void clearPolynomial(char poly) {
        switch (poly){
            case 'A' : A = new int[0][];    break;
            case 'B' : B = new int[0][];    break;
            case 'C' : C = new int[0][];    break;

            default:
                System.out.println("Error");
        }
    }

    @Override
    public float evaluatePolynomial(char poly, float value) {
        int[][] terms = new int[0][];
        float result = 0;

        switch (poly){
            case 'A': terms = A;    break;
            case 'B': terms = B;    break;
            case 'C': terms = C;    break;
            default:    //TODO error-checking ;
        }

        for (int i = 0; i < terms.length; i++ ) {
            int coefficient = terms[i][0];
            int exponent = terms[i][1];
            result += coefficient * Math.pow(value, exponent);
        }

        return result;
    }

    @Override
    public int[][] add(char poly1, char poly2) {
        SingleLinkedList firstPoly = new SingleLinkedList();
        SingleLinkedList secondPoly = new SingleLinkedList();

        int[][] firstArr = new int[0][];
        int[][] secondArr = new int[0][];

        switch (poly1){
            case 'A' : firstArr = A;    break;
            case 'B' : firstArr = B;    break;
            case 'C' : firstArr = C;    break;
            default: return null;
        }
        switch (poly2){
            case 'A' : secondArr = A;    break;
            case 'B' : secondArr = B;    break;
            case 'C' : secondArr = C;    break;
            default: return null;
        }

        // Make both linked lists match in terms on length (degree)
        if (firstArr.length > secondArr.length) {
            for (int i=0; (secondArr.length + i) < firstArr.length; i++){
                secondPoly.add(new PolynomialTerm(0, firstArr[i][1]));
            }
        }
        else if (firstArr.length < secondArr.length){
            for (int i=0; (firstArr.length + i) < secondArr.length; i++){
                firstPoly.add(new PolynomialTerm(0, secondArr[i][1]));
            }
        }

        // Transform original values into linked list
        for (int i = 0; i < firstArr.length; i++){
            firstPoly.add(new PolynomialTerm(firstArr[i][0], firstArr[i][1]));
        }
        for (int i = 0; i < secondArr.length; i++){
            secondPoly.add(new PolynomialTerm(secondArr[i][0], secondArr[i][1]));
        }

        int size = firstPoly.size;
        int[][] R = new int[size][2];

        Node currTerm1 = firstPoly.head;
        Node currTerm2 = secondPoly.head;

        for (int i = 0; i < size; i++){
            R[i][0] = ((PolynomialTerm) currTerm1.element).coefficient + ((PolynomialTerm) currTerm2.element).coefficient;
            R[i][1] = ((PolynomialTerm) currTerm1.element).exponent;

            currTerm1 = currTerm1.next;
            currTerm2 = currTerm2.next;
        }

        return R;
    }

    @Override
    public int[][] subtract(char poly1, char poly2) {

        SingleLinkedList firstPoly = new SingleLinkedList();
        SingleLinkedList secondPoly = new SingleLinkedList();

        int[][] firstArr = new int[0][];
        int[][] secondArr = new int[0][];

        switch (poly1){
            case 'A' : firstArr = A;    break;
            case 'B' : firstArr = B;    break;
            case 'C' : firstArr = C;    break;
            default: return null;
        }
        switch (poly2){
            case 'A' : secondArr = A;    break;
            case 'B' : secondArr = B;    break;
            case 'C' : secondArr = C;    break;
            default: return null;
        }

        // Make both linked lists match in terms on length (degree)
        if (firstArr.length > secondArr.length) {
            for (int i=0; (secondArr.length + i) < firstArr.length; i++){
                secondPoly.add(new PolynomialTerm(0, firstArr[i][1]));
            }
        }
        else if (firstArr.length < secondArr.length){
            for (int i=0; (firstArr.length + i) < secondArr.length; i++){
                firstPoly.add(new PolynomialTerm(0, secondArr[i][1]));
            }
        }

        // Transform original values into linked list
        for (int i = 0; i < firstArr.length; i++){
            firstPoly.add(new PolynomialTerm(firstArr[i][0], firstArr[i][1]));
        }
        for (int i = 0; i < secondArr.length; i++){
            secondPoly.add(new PolynomialTerm(secondArr[i][0], secondArr[i][1]));
        }

        int size = firstPoly.size;
        int[][] R = new int[size][2];

        Node currTerm1 = firstPoly.head;
        Node currTerm2 = secondPoly.head;

        for (int i = 0; i < size; i++){
            R[i][0] = ((PolynomialTerm) currTerm1.element).coefficient - ((PolynomialTerm) currTerm2.element).coefficient;
            R[i][1] = ((PolynomialTerm) currTerm1.element).exponent;

            currTerm1 = currTerm1.next;
            currTerm2 = currTerm2.next;
        }

        return R;
    }

    @Override
    public int[][] multiply(char poly1, char poly2) {
        SingleLinkedList firstPoly = new SingleLinkedList();
        SingleLinkedList secondPoly = new SingleLinkedList();
        SingleLinkedList result = new SingleLinkedList();

        int[][] firstArr; // Reference to whatever polynomial being used
        int[][] secondArr;

        switch (poly1){
            case 'A' : firstArr = A;    break;
            case 'B' : firstArr = B;    break;
            case 'C' : firstArr = C;    break;
            default: return null;
        }
        switch (poly2){
            case 'A' : secondArr = A;    break;
            case 'B' : secondArr = B;    break;
            case 'C' : secondArr = C;    break;
            default: return null;
        }

        // Check if its empty
        if (firstArr == null || secondArr == null){
            return null;
        }

        // Transform original values into linked list
        for (int i = 0; i < firstArr.length; i++){
            firstPoly.add(new PolynomialTerm(firstArr[i][0], firstArr[i][1]));
        }
        for (int i = 0; i < secondArr.length; i++){
            secondPoly.add(new PolynomialTerm(secondArr[i][0], secondArr[i][1]));
        }


        // Do multiplication
        Node currTerm1 = firstPoly.head;

        for (int i=0; i < firstPoly.size; i++){
            Node currTerm2 = secondPoly.head;
            for (int j=0; j < secondPoly.size; j++){
                int coefficient = ((PolynomialTerm) currTerm1.element).coefficient * ((PolynomialTerm) currTerm2.element).coefficient;
                int exponent = ((PolynomialTerm) currTerm1.element).exponent + ((PolynomialTerm) currTerm2.element).exponent;

                result.add(new PolynomialTerm(coefficient, exponent));
                currTerm2 = currTerm2.next;
            }
            currTerm1 = currTerm1.next;
        }

        // Simplify
        for (int i = 0; i < result.size; i++ ){
            PolynomialTerm currTerm = (PolynomialTerm) result.get(i);

            for (int j = i+1; j < result.size; j++){
                PolynomialTerm termCompared = (PolynomialTerm) result.get(j);

                if (currTerm.exponent == termCompared.exponent){ // If they both have the same exponent
                    // Add up the coefficients
                    currTerm.coefficient += termCompared.coefficient;
                    // Remove the second one
                    result.remove(j);
                }
            }
        }

        // Check if any of the coefficients is zero after simplifying
        for (int i = 0; i < result.size; i++ ) {
            PolynomialTerm currTerm = (PolynomialTerm) result.get(i);
            if (currTerm.coefficient == 0)
                result.remove(i);
        }

        // Transform the result into 2D array
        int[][] R = new int[result.size][2];

        for (int i = 0; i < result.size; i++ ) {
            PolynomialTerm currTerm = (PolynomialTerm) result.get(i);
            R[i][0] = currTerm.coefficient;
            R[i][1] = currTerm.exponent;
        }
        return R;
    }

    public void printResult(int[][] R){
        String output = new String();

        for (int i=0; i < R.length; i++){
            // Add the coefficient
            if (R[i][0] == 0) // if the coefficient is zero >> skip
                continue;
            else if (R[i][0] == 1) {   // if the coefficient is one >> don't add it
                if (R[i][1] == 0) // special case where its a coefficient is one and its a constant
                    output += R[i][0];
                else    // otherwise add nothing
                    ;
            }
            else {
                if (R[i][0] < 0 && output.length() != 0){   // If its negative , remove the plus sign
                    output = output.substring(0, output.length()-1);
                }
                output += R[i][0];
            }

            // Add the x^exponent
            if (R[i][1] == 0) // Check if the term is the constant (exponent == 0)
                ;   // Don't add anything
            else if (R[i][1] == 1) // if its (x ^ 1)
                output += "x" ;
            else
                output += "x" + "^" + R[i][1];

            // Add the sign
            output +=  "+";

        }

        if (output.length() == 0)
            output += 0;

        // Remove the last sign if it exists alone
        char lastChar = output.charAt(output.length()-1);
        if (lastChar == '+' || lastChar == '-')
            output = output.substring(0, output.length()-1);

        System.out.println(output);
    }

}