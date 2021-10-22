

///*
// * Programmer: Manish Panta
// * Programmer: Kushal Gautam
// * NEDID     : mpanta
// * NETID     : kgautam2
// * URLinkedList
// * 
// */
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class URLinkedList<E> implements URList<E> {

	int size;
	URNode<E> head;
	URNode<E> tail;

	// Constructor
	public URLinkedList() {
		this.size = 0;
	}

	public static void main (String[] args) {
	//	URLinkedList <E> list = new URLinkedList<E>();
	}
	// Appends the specified element to the end of this list
	@Override
	public boolean add(E element) {
		if (size == 0) { //Check size 
			head = new URNode<E>(element, null, null);
		} else if (size == 1) {
			tail = new URNode<E>(element, head, null);
			head.setNext(tail);
		} else {
			tail = new URNode<E>(element, tail, null);//assing links
			tail.prev().setNext(tail);
		}
		size++;
		return true;
	}
	// Inserts the specified element at the specified position in this list
	@Override
	public void add(int index, E element) throws IndexOutOfBoundsException {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			head = new URNode<E>(element, null, head);
			head.next().setPrev(head);
		} else {
			URNode<E> ptr = this.getNode(index - 1);
			URNode<E> nextptr = this.getNode(index);
			URNode<E> newnode = new URNode<E>(element, ptr, nextptr);
			ptr.setNext(newnode);
			nextptr.setPrev(newnode);
		}
		size++;

	}
	// Appends all of the elements in the specified collection to the end of this
	// list
	// in the order that they are returned by the specified collection's iterator
	@Override 
	public boolean addAll(Collection<? extends E> collection) throws NullPointerException {
		if (collection.equals(null)) {
			throw new NullPointerException();
		}
		for (E element : collection) { //iterate and add
			this.add(element);
		}
		return true;
	}
	// Inserts all of the elements in the specified collection into this list
		// at the specified position
	@Override
	public boolean addAll(int index, Collection<? extends E> collection)
			throws IndexOutOfBoundsException, NullPointerException {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (collection.equals(null)) {
			throw new NullPointerException();
		}
		URLinkedList<E> addlist = new URLinkedList<E>();
		for (E element : collection) {
			addlist.add(element);
			size++;
		}
		if (index == 0) {
			addlist.tail.setNext(this.head);
			this.head.setPrev(addlist.tail);
			this.head = addlist.head;
		} else {
			this.getNode(index).setPrev(addlist.tail); //relink the nodes 
			addlist.tail.setNext(this.getNode(index));
			this.getNode(index - 1).setNext(addlist.head);
			addlist.head.setPrev(this.getNode(index - 1));
		}
		return true;
	}

	// Removes all of the elements from this list
	@Override
	public void clear() {
		head = new URNode<E>(null, null, null);
		size = 0;
	}

	// Returns true if this list contains the specified element.
	@Override
	public boolean contains(Object data) {
		Iterator<E> iterator = this.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(data)) {
				return true;
			}
		}
		return false;
	}

	// Returns true if this list contains all of the elements of the specified collection
	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object element : collection) {
			if (!this.contains(element)) {
				return false;
			}
		}
		return true;
	}

	// Compares the specified object with this list for equality. Returns true if both contain the same elements. Ignore capacity
	@Override
	public boolean equals(Object data) {
		if (data.equals(this)) {
			return true;
		}
		return false;
	}

	// Returns the element at the specified position in this list.
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		URNode<E> ptr = head; //pointer to keep counter 
		for (int i = 0; i < index; i++) {
			ptr = ptr.next();
		}
		return ptr.element();
	}


	// Returns the index of the first occurrence of the specified element in this
	// list,  or -1 if this list does not contain the element.
	@Override
	public int indexOf(Object data) {
		URNode<E> ptr = head;
		int index = 0;
		while (!(ptr == null)) { 
			if (ptr.element().equals(data)) {
				return index;
			}
			ptr = ptr.next();
			index++;
		}
		return -1;
	}
	// Returns true if this list contains no elements.
	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}
	// Returns an iterator over the elements in this list in proper sequence.
	@Override
	public Iterator<E> iterator() {
		return new URIterator<E>(this);
	}

	// Removes the element at the specified position in this list
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E returnelement = this.get(index);
		if (index != size - 1) {
			getNode(index + 1).setPrev(getNode(index - 1));
		} else {
			tail = getNode(index - 1);
		}
		if (index != 0) {
			getNode(index - 1).setNext(getNode(index + 1));
		} else {
			head = getNode(index + 1);
		}
		size--;
		return returnelement;
	}

	// Removes the first occurrence of the specified element from this list, if it is present
	@Override
	public boolean remove(Object data) {
		try {
			remove(indexOf(data));
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	// Removes from this list all of its elements that are contained in the specified collection
	@Override
	public boolean removeAll(Collection<?> collection) {
		if (!this.containsAtLeastOne(collection)) {
			return false;
		} else {
			for (Object element : collection) {
				remove(element);
			}
			return true;
		}
	}

	// Replaces the element at the specified position in this list with the specified element
	@Override
	public E set(int index, E element) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E returnelement = this.get(index);
		getNode(index).setElement(element);
		return returnelement;
	}

	// Returns the number of elements in this list.
	@Override
	public int size() {
		return size;
	}

	// Returns a view of the portion of this list
	// between the specified fromIndex, inclusive, and toIndex, exclusive.
	@Override
	public URList<E> subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException, IllegalArgumentException {
		if (0 > fromIndex || toIndex > size) {
			throw new IndexOutOfBoundsException();
		}
		if (fromIndex > toIndex) {
			throw new IllegalArgumentException();
		}
		URList<E> returnlist = new URLinkedList<E>();
		URNode<E> ptr = head;
		for (int i = 0; i < toIndex; i++) {
			if (i >= fromIndex) {
				returnlist.add(ptr.element());
			}
			ptr = ptr.next();
		}
		return returnlist;
	}

	// Returns an array containing all of the elements in this list
		// in proper sequence (from first to the last element).
	@Override
	public E[] toArray() {
		if (size == 0) {
			return null;
		}
		URNode<E> ptr = head;
		@SuppressWarnings("unchecked")
		E[] returnarray = (E[]) new Object[size];
		int i = 0;
		while (!ptr.equals(null)) {
			returnarray[i] = ptr.element();
			ptr = ptr.next();
			i++;
		}
		return returnarray;
	}

	//ADD TO THE FIRST 
	public void addFirst(E element) {
		add(0, element);
	}

	//ADD TO THE LAST
	public void addLast(E element) {
		add(element);
	}
//PEEK THE FIRST 
	public E peekFirst() {
		if (isEmpty()) {
			return null;
		} else {
			return head.element();
		}
	}
//PEEK THE LAST 
	public E peekLast() {
		if (isEmpty()) {
			return null;
		} else {
			return tail.element();
		}
	}
//REMOVE THE FIRST 
	public E pollFirst() {
		return remove(0);
	}
//REMOVE THE LAST
	public E pollLast() {
		return remove(size - 1);
	}

	// self-implemented methods for convenience.
	public URNode<E> getNode(int index) throws IndexOutOfBoundsException {
//		if (index < 0 || index > size) {  COOMMENTED OUT FOR QUEUE
//			System.out.println("Index: " + index);
//			System.out.println("SIZE: " + size);
//			throw new IndexOutOfBoundsException();
//		}
		URNode<E> ptr = head;
		for (int i = 0; i < index; i++) {
			ptr = ptr.next();
		}
		return ptr;
	}

	//TO STRING 
	public String toString() {
		String returnstring = "";
		URNode<E> ptr = head;
		while (!(ptr == null)) {
			returnstring += ptr.element().toString() + " ";
			ptr = ptr.next();
		}
		return returnstring;
	}

	public boolean containsAtLeastOne(Collection<?> collection) {
		for (Object data : collection) {
			if (this.contains(data)) {
				return true;
			}
		}
		return false;
	}

	//IMPLEMENTED URITERATOR 
	@SuppressWarnings("hiding")
	protected class URIterator<E> implements Iterator<E> {
		URLinkedList<E> list;
		int index;

		public URIterator(URLinkedList<E> list) {
			this.list = list;
			this.index = 0;
		}

		@Override
		public boolean hasNext() {
			if (index >= list.size()) {
				return false;
			}
			return true;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			return list.get(index++);
		}

		@Override
		public void remove() throws IllegalStateException {
			try {
				list.remove(index - 1);
			} catch (IndexOutOfBoundsException e) {
				throw new IllegalStateException();
			}
		}

	}
}
