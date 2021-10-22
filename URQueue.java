/*
 * Programmer: Manish Panta
 * Programmer: Kushal Gautam
 * NEDID     : mpanta
 * NETID     : kgautam2
 * URNode
 * 
 */
public class URQueue<E> implements Queue {

	URLinkedList<E> list; // Object List

	public URQueue() {
		list = new URLinkedList<E>();
	}

	@Override
	public boolean isEmpty() { // Check if the function

		return list.isEmpty();
	}

	@Override
	public void enqueue(Object x) { // Add the objects to the queue
		list.add((E) x);

	}

	@Override
	public Object dequeue() { // Remove the objects from the queue
		if (list.isEmpty()) {
			System.out.println("The Queue is Empty");
			return null;
		}
		return list.pollFirst();
	}

	@Override
	public Object peek() { // Peek the element at the top
		if (list.isEmpty()) {
			return true;
		} else {
			return list.peekFirst();
		}
	}

	public void print() { // Print the Queue
		System.out.println(list.toString());
	}




}
