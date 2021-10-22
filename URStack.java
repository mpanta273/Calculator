import java.util.LinkedList;

public class URStack<E> implements Stack<E> {
	 LinkedList<E> theStack;
	
	public URStack() {
		theStack = new LinkedList<E>();
	}
	@Override
	public boolean isEmpty() {
		return theStack.isEmpty();
	}

	@Override
	public void push(E x) {
		theStack.addFirst(x);	
	}

	@Override
	public E pop() {
		if(isEmpty() == true) {
			return null;
		} else {
			return theStack.removeFirst();
		}
	}
	@Override
	public E peek() {
		return theStack.peek();
	}
	public void print() {
		System.out.println(theStack.toString());
	}

}
