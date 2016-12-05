import java.util.Vector;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Arrays;
import java.lang.StringBuilder;
import java.io.IOException;

/**
 *This is a java implementation of problem 2 from project 1
 *
 */

class Problem2 {

  static Vector<Integer[]> state_table = new Vector<Integer[]>(); 
  static Vector<Integer> parent = new Vector<Integer>();
  static Vector<Integer> symbol = new Vector<Integer>();
  static Vector<Boolean> visited = new Vector<Boolean>();

  public Integer delta(int c_state, int symbol, int x) {
    return (c_state * 10 + symbol) % x;
  }


 public void make_table(Vector<Integer> symbol, int x) {
     for (int i = 0; i < x; ++i) {
	 Integer[] temp = new Integer[symbol.size()];
	 for (int j = 0; j < symbol.size(); ++j) {
	     temp[j] = delta(i, symbol.get(j), x);
	 }
	 state_table.add(temp);
     }
 }
    
    
  public void bfs(Vector<Integer> symbol_lst, int x) {
    for (int i = 0; i < x; ++i) {
      parent.add(null);
      symbol.add(null);
      visited.add(false);
    }
    LinkedList<Integer> q = new LinkedList<Integer>();
    q.add(0);
    while(true) {
      Integer out = q.removeFirst();
      for (Integer s: symbol_lst) {
        Integer next = delta(out, s, x);
        if (!visited.get(next)) {
          visited.set(next, true);
          parent.set(next, out);
          symbol.set(next, s);
          q.add(next);
        }
        if (next == 0){
          return;
        }
      }
    }
  }

  public static void main(String[] args) {
    System.out.println("Enter the symbols for the set (Example 3,4,5)");
    Vector<Integer> numbers = new Vector<Integer>();
    Scanner numScanner = new Scanner(System.in);
    String line = numScanner.nextLine();
    String[] tokens = line.split(",");
    for (String token : tokens){
      numbers.add(Integer.parseInt(token));
    }
    System.out.println("Enter a number for X");
    int x;
    x = numScanner.nextInt();
    numScanner.nextLine();
    long startTime = System.nanoTime();
    new Problem2().make_table(numbers, x);
    new Problem2().bfs(numbers, x);
    Integer current = 0;
    String str_var = Integer.toString(symbol.get(current));
    current = parent.get(current);
    while(current != 0) {
      str_var += Integer.toString(symbol.get(current));
      current = parent.get(current);
    }
    str_var = new StringBuilder(str_var).reverse().toString();
    long endTime = System.nanoTime();
    System.out.format("Shortest length string accepted by the DFA is %s\n", str_var);
    double duration = (double)(endTime - startTime);
    System.out.format("%f seconds", duration/1000000000);
  }
}
