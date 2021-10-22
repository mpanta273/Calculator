public interface Queue<E> {
	
	public boolean isEmpty();

	public void enqueue(E x);

	public E dequeue();

	public E peek();


}