import java.util.NoSuchElementException;
public class LinkedList<E> {
	 Node<E> first;
	
	public void printList() {
		Node<E> n = first;
		while(n!=null) {
			System.out.print(n.data + " ");
			n = n.next;
		}
	}
	public boolean isEmpty() {
		if(first == null) {
			return true;
		}
		else {
			return false;
		}
	}
	public void addFirst(E x) {
		Node<E> newLink = new Node<E>();
		newLink.data = x;
		newLink.next = first;
		first = newLink;
	}
	public E removeFirst() {
		if(first == null) {
			throw new NoSuchElementException();
		}
		E data = first.data;
		first = first.next;
		return data;
	}
	
	public E peek() {
		if(isEmpty()) {
			return null;
			
		} else {
			return first.data;
		}
	}
	public String toString() {
		String result = "";
		Node<E> current = first;
		while(!(current == null)) {
			result += current.element().toString() + " ";
			current = current.next;
		}
		return result;
	}
}
