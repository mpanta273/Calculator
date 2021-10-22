/*
 * Programmer: Manish Panta
 * Programmer: Kushal Gautam
 * NEDID     : mpanta
 * NETID     : kgautam2
 * OUTPUT
 * 
 */
 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Arrays;

public class InfixCalculator {

	// SHUNTING YRD ALGORITHM
// Check if it is double or integer
	public static Double checkdouble(String c) {
		try {
			return Double.parseDouble(c + "");
		} catch (Exception e) {
			return -1.0;
		}
	}

	// Check which operator
	public static int checkoperator(String a) {
		if (a.equals("(")) {
			return 10;
		} else if (a.equals(")")) {
			return 9;
		} else if (a.equals("sin") || a.equals("cos") || a.equals("tan")) {
			return 8;
		} else if (a.equals("^")) {
			return 7;
		} else if (a.equals("*") || a.equals("/") || a.equals("%")) {
			return 6;
		} else if (a.equals("+") || a.equals("-")) {
			return 5;
		} else if (a.equals("<") || a.equals(">") || a.equals("=")) {
			return 4;
		} else if (a.equals("!")) {
			return 3;
		} else if (a.equals("&")) {
			return 2;
		} else if (a.equals("|")) {
			return 1;
		} else {
			return -1;
		}
	}

// For multi-digit numbers
	public static String[] splitString(String str) { // Split the strings
		String[] arr = str.split(" ");
		return arr;
	}

	// CHAGE the Queue into String for postfix evaluation
	public static String queueToString(URQueue<String> q) {
		String toReturn = "";
		while (!q.isEmpty()) {
			toReturn += q.dequeue() + " ";
		}
		return toReturn;
	}

//=========================================================================================
	// POST FIX EVALUATION
// checks to see if the character is a number
	public static double isOperand(String post) {
		try {
			return Double.parseDouble(post + "");
		} catch (Exception e) {
			return -1;
		}
	}

//Function to Calculate for the expression     
	public static double calc(String post) {
		URStack<Double> a = new URStack<>();
		String[] split = splitString(post);

		for (String temp : split) {
			double possibleInt = isOperand(temp);
			if (possibleInt != -1) {
				a.push(possibleInt);
			} else {
				double first, second;
				switch (temp) { // Check for all the operators and perform operation
				case "+":
					a.push(a.pop() + a.pop());
					break;
				case "-":
					first = a.pop();
					second = a.pop();
					a.push(second - first);
					break;
				case "*":
					a.push(a.pop() * a.pop());
					break;
				case "/":
					first = a.pop();
					second = a.pop();
					a.push(second / first);
					break;
				case "<":
					first = a.pop();
					second = a.pop();
					if (second < first) {
						a.push(1.00);
					} else {
						a.push(0.00);
					}
					break;
				case ">":
					first = a.pop();
					second = a.pop();
					if (second > first) {
						a.push(1.00);
					} else {
						a.push(0.00);
					}
					break;
				case "=":
					first = a.pop();
					second = a.pop();
					if (first == second) {
						a.push(1.00);

					} else {
						a.push(0.00);

					}
					break;
				case "&":
					first = a.pop();
					second = a.pop();
					if (second == 1 && first == 1) {
						a.push(1.00);
						;
					} else {
						a.push(0.00);
					}
					break;
				case "|":
					first = a.pop();
					second = a.pop();
					if (second == 0 && first == 0) {
						a.push(0.00);
						;
					} else {
						a.push(1.00);
						;
					}
					break;
				case "!":
					first = a.pop();
					if (first == 1) {
						a.push(0.00);
					} else if (first == 0) {
						a.push(1.00);
					}
					break;
				case "^":
					first = a.pop();
					second = a.pop();
					a.push(Math.pow(second, first));
					break;
				case "%":
					first = a.pop();
					second = a.pop();
					a.push(second % first);
					break;
				case "sin":
					first = a.pop();
					a.push(Math.sin(first));
					break;
				case "cos":
					first = a.pop();
					a.push(Math.cos(first));
					break;
				case "tan":
					first = a.pop();
					a.push(Math.tan(first));
					break;
				}
			}
		}
		return a.pop();
	}

	// ===========================================================================================================
	// MAIN METHOD
	public static void main(String[] args) throws IOException {
		URStack<String> theStack = new URStack<String>(); // Stack
		URQueue<String> q = new URQueue<String>(); // Queue

		// GEt the file from the console
		String name = args[0];
		String output = args[1];
		// Open the file
		FileInputStream fstream = new FileInputStream(name);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		// Output
		File file = new File(output);
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);

		String strLine1;
		String strLine;
		String line = null;

		// Read File Line By Line
		while ((strLine1 = br.readLine()) != null) {
			// Check for white spaces
			if (strLine1.isEmpty()) {
				// System.out.println("exit");
				continue;
			}
			// Replace parenthesis
			strLine = strLine1.replaceAll("\\(", " ( ");
			strLine = strLine.replaceAll("\\)", " ) ");
			strLine = strLine.replaceAll("\\!", " ! ");
//			System.out.println("I am here");
			String[] a = splitString(strLine); // change to String array
			System.out.println("Array for evaluation: " + Arrays.deepToString(a)); // Print the arrays

			// Check for the conditions
			for (int i = 0; i < a.length; i++) {
				String c = a[i];
				Double possibleInt = checkdouble(c); // Check of it is integers or float
				int operator_value = checkoperator(c); // check if it is a double

				if (possibleInt != -1) { // if its an integer keep it in the queue
					q.enqueue(c);
				}

				else if (operator_value != -1) { // Else check for operators
					if (theStack.isEmpty()) {
						theStack.push(c);
					} else if (checkoperator(theStack.peek()) == 10) { // Check for parenthesis
						theStack.push(c);
					} else if (operator_value == 9) {
						while (checkoperator(theStack.peek()) != 10) {
							String popped = theStack.pop();
							q.enqueue(popped);
						}
						theStack.pop();

					}

					// if not check the value for operator having higher precedence
					else if (checkoperator(theStack.peek()) >= operator_value) {
						String popped = theStack.pop(); // pop
						q.enqueue(popped);// put
						// CHECK AGAIN to see if the precedence are equal
						if (!theStack.isEmpty()) {
							if (checkoperator(theStack.peek()) == operator_value) {
								System.out.println("HERE: ");
								theStack.print();
								while (checkoperator(theStack.peek()) == operator_value) {
									String pop = theStack.pop(); // pop
									q.enqueue(pop);// put

								}
							}
						}
						theStack.push(c); // push

					} else {
						theStack.push(c); // Just push the operator into the Stack
					}
				}

			}

			while (theStack.peek() != null) { // pop everything in the stack at the end
				String popped = theStack.pop(); // pop
				q.enqueue(popped);// put
			}

			String postFixExp = queueToString(q);
			System.out.println("Postfix Expression: " + postFixExp); // print the expression that needs to be evaluated

			// POSTFIX EVALUATION
			DecimalFormat df = new DecimalFormat("0.00");
			System.out.print("PostFix Evaluation: ");

			System.out.print(df.format(calc(postFixExp))); // Calculate the post fix
			System.out.println(" ");
			System.out.println(" ");

			pw.println(df.format(calc(postFixExp)));

		}
		// Close the input stream
		fstream.close();
		pw.close();
	}
}