import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

interface IExpressionEvaluator {

    /**
     * Takes a symbolic/numeric infix expression as input and converts it to
     * postfix notation. There is no assumption on spaces between terms or the
     * length of the term (e.g., two digits symbolic or numeric term)
     *
     * @param expression infix expression
     * @return postfix expression
     */

    public String infixToPostfix(String expression);


    /**
     * Evaluate a postfix numeric expression, with a single space separator
     * @param expression postfix expression
     * @return the expression evaluated value
     */

    public int evaluate(String expression);

}

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
     /* @param object to insert*
     */

    public void push(Object element);

    /*** Tests if this stack is empty
     * @return true if stack empty
     */
    public boolean isEmpty();

    public int size();
}


public class Evaluator implements IExpressionEvaluator {

    //Array of a,b,and c
    int [] var = new int[3];

    //Returns an integer that corresponds to 
    //the precedence of an operator
    int precedence (char c)
    {
        switch (c)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }
    
    
    public String infixToPostfix(String expression)
    {
        String exp = expression;
        
        //Checks whether the expression starts with '--' and replaces it with ''
        //eg: --a = a
        
        if (exp.charAt(0) == '-' && exp.charAt(1) == '-')
        {
            exp = exp.substring(0,3).replace("--","") + exp.substring(3, expression.length());
        }
        
        //Checks whether the expression starts with '+' and replaces it with ''
        //eg: +a = a
        if(exp.charAt(0) == '+')
        {
            exp = exp.substring(0,1).replace("+", "") + exp.substring(1, expression.length());
        }
        
        //Replaces any Operator-- by Operator
        exp = exp.replace("*--", "*");
        exp = exp.replace("/--", "/");
        exp = exp.replace("+--", "+");
        exp = exp.replace("^--", "^");
        exp = exp.replace("---", "-");
        exp = exp.replace("--", "+");
        
        
        String res = new String("");

        MyStack s = new MyStack();
        
        //Loop over each char in the expression
        for (int i = 0; i < exp.length(); ++i)
        {
            char ch = exp.charAt(i);
            
            //If it is a variable then push it to the stack
            if (Character.isLetterOrDigit(ch)) {
                res += ch;
            }
            //If it is a '(' push it to the stack
            else if (ch == '(') {

                s.push(ch);
            }
            //If it is a ')', the stack isn't empty, and peek isn't '('
            //then pop everything till you find a '('
            else if (ch == ')')
            {
                while (!s.isEmpty() && (char) s.peek() != '(')
                    res += s.pop();
        
                if (s.size() == 0)
                {
                    return "Error";
                }

                s.pop();
            }
            else
            {
                //If there is an operator at the start
                //And it isn't '-', return Error
                if (i == 0 && ch != '-')
                {
                    return "Error";
                }
                else if (i != exp.length() - 1)
                {
                    //If two operators are after each other '*/'
                    //return Error
                    if(isOperator(ch) && isOperator(exp.charAt(i + 1)))
                        return "Error";
                }
                //If the last char is an operator
                //return Error
                 else if(i == exp.length() - 1)
                {
                    return "Error";
                }
                //Pop operators from the stack according to their precedence
                while (!s.isEmpty() && precedence(ch) <= precedence((char)s.peek())){

                    res += s.pop();
                }
                s.push(ch);
            }

        }
        
        //Check if there is a dangling bracket
        while (!s.isEmpty()){
            if((char)s.peek() == '(')
            {
                return "Error";
            }
            res += s.pop();
        }
        return res;
    }

    public int evaluate(String expression)
    {
        MyStack x = new MyStack();

        for(int i = 0; i < expression.length(); i++)
        {
            char ch = expression.charAt(i);

            // If the scanned character is an operand (number/variable),
            // push it to the stack.
            if(ch == 'a' || ch == 'b' || ch == 'c')
                x.push(var[ch - 'a']);
            else if (Character.isDigit(ch))
            {
                x.push(ch - '0');
            }

            //  If the scanned character is an operator, pop two
            // elements from stack apply the operator
            else
            {
                //in case of 'a-'
                if (ch == '-' && x.size() == 1) {
                    x.push((int) x.pop() * -1);
                    continue;
                }
                
                int v1 = (int) x.pop();
                
                int v2 = (int) x.pop();
                

                switch(ch)
                {
                    case '+':
                        x.push(v2 + v1);
                        break;

                    case '-':
                        x.push(v2 - v1);
                        break;

                    case '/':
                        x.push(v2 / v1);
                        break;

                    case '*':
                        x.push(v2 * v1);
                        break;
                     case '^':
                        x.push((int) Math.pow((double) v2, (double) v1));
                        break;
                    default:   
                        System.out.println("Error"); 
                        break;
                }
            }
               
            
        }
        return (int)x.pop();
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
    }
     public boolean isOperator(char operator)
    {
        if(operator == '+' || operator == '-' ||
                operator == '*' || operator == '/' ||
                operator == '^')
        {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. */
        
        Evaluator inf = new Evaluator();
        Scanner sc = new Scanner(System.in);
        String infix = sc.nextLine();
         
        //Check if the input has any invalid characters
        for(int i = 0; i < infix.length(); i++)
        {
            if(infix.charAt(i) != 'a' && infix.charAt(i) != 'b' && infix.charAt(i) != 'c' &&
                    infix.charAt(i) != '(' && infix.charAt(i) != ')' &&
                    infix.charAt(i) != '+' && infix.charAt(i) != '-' &&
                    infix.charAt(i) != '*' && infix.charAt(i) != '/' &&
                    infix.charAt(i) != '^' &&!Character.isDigit(infix.charAt(i)))
            {
                System.out.println("Error");
                return;
            }
        }

        
        //Stores our 3 variables
        try{
        for (int i = 0; i < 3; i++)
        {
            String number = sc.nextLine().replaceAll("[a|b|c|=]+", "");
           
                inf.var[i] = Integer.parseInt(number);
            }
            
        }
        catch (Exception e)
            {
                System.out.println("Error");
                return;
            }
       

        String res = inf.infixToPostfix(infix);
        if(res == "Error")
        {
            System.out.println(res);
            return;
        }
        System.out.println(res);
        System.out.println(inf.evaluate(res));
    }


}