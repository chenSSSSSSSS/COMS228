package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 * @author Chen Sang
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list. 
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }
  /**
   * return the size of in the list
   */
  @Override
  public int size()
  {
	  return this.size;
  }
  
  /**
   * Ensures that this collection contains the specified element (optional operation). 
   * Returns true if this collection changed as a result of the call. 
   * (Returns false if this collection does not permit duplicates and already contains the specified element.)
   */
  @Override
  public boolean add(E item)
  {
	  if(item == null) throw new NullPointerException();
	  if(this.size == 0) //  add start with the empty list
	  {
		  Node temp = new Node();
		  temp.addItem(item);
		  link(head,temp);
//		  temp.previous = head;
//		  temp.next     = tail;
//		  head.next     = temp;
//		  tail.previous = temp;
	  }
	  else if(tail.previous.count == nodeSize) // add element in new node since current node is full
	  {
		  Node temp = new Node();
		  temp.addItem(item);
		  link(tail.previous,temp);
//		  temp.previous      = tail.previous;
//		  temp.next          = tail;
//		  tail.previous.next = temp;
//		  tail.previous      = temp;
	  }
	  else
	  {
		  tail.previous.addItem(item); // add element in the available offset since current node is not full
	  }
	  ++size;
	  return true;
  }

  /**
   * Inserts the specified element at the specified position in this list (optional operation).
   */
  @Override
  public void add(int pos, E item)
  {
	  if(item == null) throw new NullPointerException();
	  
	  if(this.size == 0)
	  {
		  add(item);  // boolean add since it is an empty list just add to node and create new node
	  }
	  else 
	  {
		  NodeInfo n = find(pos);       // find where current node information to get current node and the offset
		  Node curr  = n.node;          // current node 
		  int off    = n.offset;
		  addHelp(curr,off,item);
	  }
	 ++size; 
  }

  /**
   * Removes the element at the specified position in this list (optional operation).
   */
  @Override
  public E remove(int pos)
  {
	  NodeInfo i = find(pos);
	  Node curr  = i.node;          // current node 
	  int off    = i.offset;        // offset 
	  E hh       = curr.data[off];  // this element at position
	 
	  if(tail.previous == curr && curr.count == 1)
	  {
		  unlink(curr);  // unlink the node
	  }
	  else if (tail.previous == curr && curr.count>=2)
	  {
		  curr.removeItem(off);// remove element
	  }
	  else 
	  {
		  curr.removeItem(off);
		  //if next node has more then M(nodesize)/2 elements, move the first node to the currentNode
		  if(curr.next.count >= nodeSize/2)
		  {
			  curr.addItem(curr.next.data[0]);// add the first element from next node to the current node
			  curr.next.removeItem(0);// then remove the first element in the next node
		  }
		  else // if next node has less than 2 elements
		  {
			  for(int j = 0; j < curr.next.count; j++)
			  {
				  E lol = curr.next.data[j]; // move all the element from next node to current node
				  curr.addItem(lol);
			  }
			 unlink(curr.next);	 
		  }
	  }
	  size--;
    return hh;
  }
  /**
   * unlink the node that is given
   * @param current node that is going to unlink
   */
  private void unlink(Node n)
  {
    n.previous.next = n.next;
    n.next.previous = n.previous;
  }
  /**
   * to link the new node
   * @param current node
   * @param add new node to be add
   */
  private void link(Node current, Node add)
  {
	  add.previous=current;
	  add.next=current.next;
	  current.next.previous=add;
	  current.next=add;
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
	  E[] list = (E[]) new Comparable[size];
	  Node n    = head.next;
	  int index = 0;   //track the index number within the array items, 
	  
	  while(n!=tail) {
		  for(int i = 0; i < n.count && n!= tail;i++)
		  {
			  list[index] = n.data[i];
			  index++;
		  }
		  unlink(n);  // after unlink the node0,  node1 will become node0
		  n = head.next;
	  }
	  
	 // thisComparator hh = new thisComparator();
	  insertionSort(list,new thisComparator()); 
	  n          = head; 
	  int offset = 0; // offset in this node
	  for(int i = 0;i < list.length; ++i)
	  {
		  if(i % nodeSize != 0)
		  {
			  n.data[offset]= list[i];
		  }
		  else // start with a new node when i % nodeSize == 0 
		  {
			  offset           = 0;  //  offset in each node from 0 to (nodeSize - 1)
			  Node temp        = new Node(); 
			  temp.data[offset]= list[i];
			  link(n, temp);           //link new node with previous node 
			  n                = temp;  // n becomes previous node
		  }
		  ++offset;
	  } 
//	  for (int i = 0; i < list.length; i++)  did not work 
//	  {
//		  add(list[i]);
//	  }
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
	  E[] items = (E[]) new Comparable[size];
	  Node n    = head.next;
	  int index = 0;   //track the index number within the array items
	  
	  while(n!=tail) {
		  for(int i = 0; i < n.count && n!= tail;i++)
		  {
			  items[index] = n.data[i];
			  index++;
		  }
		  unlink(n);  // after unlink the node0,  node1 will become node0?
		  n = head.next;
	  }
	  thisComparator hh = new thisComparator();
	  bubbleSort(items);
	  
	  n          = head; 
	  int offset = 0; // offset in this node
	  for(int i = 0;i < items.length; ++i)
	  {
		  if(i % nodeSize != 0)
		  {
			  n.data[offset]= items[i];
		  }
		  else // start with a new node when i % nodeSize == 0 
		  {
			  offset           = 0;  //  offset in each node from 0 to (nodeSize - 1)
			  Node temp        = new Node(); 
			  temp.data[offset]= items[i];
			  link(n, temp);           //link new node with previous node 
			  n                = temp;  // n becomes previous node
		  }
		  ++offset;
	  } 
//	  for (E item: items) // first try but it throw Index out of bound exception
//	  {
//		  add(item);
//	  }
  }
  
  @Override
  public Iterator<E> iterator()
  {
    return new StoutIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
    return new StoutListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index)
  {
    return new StoutListIterator(index);
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
    	if (count >= nodeSize)
    	{
    		return;
    	}
    	data[count++] = item;
    	//useful for debugging
    	System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      ++count;
      data[offset] = item;
      //useful for debugging 
      System.out.println("Added " + item.toString() + " at offset " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      E item = data[offset];
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      --count;
    }    
  }
 /**
  * Iterator<E> for this class 
  * @author Chen Sang
  *
  */
  private class StoutIterator implements Iterator<E>
  {
	  private Node currentN;//current  node
	  private int  index   ;//index  of the element
	  private int  off     ;//offset of the element
	 
	 
	@Override
	public boolean hasNext() {
		return this.index < size;
	}

	@Override
	public E next() {
		if (!hasNext()) throw new NoSuchElementException();

		if (currentN == null ) currentN = head.next; 
        
		E ret  = this.currentN.data[off];

		this.off ++;
		if(this.off == this.currentN.count) // if element is the last one in the node, next() should go to the next node
		{
			this.currentN = this.currentN.next;
			this.off      = 0;
		}
		
		//E ret  = this.currentN.data[off];
		this.index += 1;
		//this.off ++;
		return ret;
	}
  }
  
  /**
   * private help class to get information about the offset and the node
   * @author Chen Sang
   *
   */
  private class NodeInfo
  {
	  public Node node;
	  public int offset;
	  
	  public NodeInfo(Node node, int offset)
	  {
		  this.node   = node;
		  this.offset = offset;
	  }
  }
  /**
   * private help method to find the current node and the offset of this element 
   * @param pos
   * @return node information including offset and the current node
   */
  private NodeInfo find(int pos)
  {
//	  //if (pos < 0 || pos > size) throw new IndexOutOfBoundsException();
//	  int numElement = pos;
//	  Node n         = head.next;
//	  int count      = n.count -1;
//	  
//	  
//	  while(count < pos && numElement > 0)//current != null && count + current.count < pos)
//	  {
//		  numElement -= n.count; 
//		  n           = n.next;
//		  count      += n.count;
//	  }
//	  //int offset = n.count - numElement + pos;
//	  return new NodeInfo(n,numElement); //(n,pos-count)
	  
	  // asked professor Trent for help
	  int count = 0;
	  Node n    = head.next;
	  while(n != tail && count + n.count <= pos)
	  {
		  count += n.count;
		  n      = n.next;
	  }
	  return new NodeInfo(n, pos - count);
  }
  
  /**
   * private help add method for add in ListIterator and add(pos,item)
   * @param n       // current node
   * @param offset  // which offset i want to place the item
   * @param item    // item is going to add 
   * @return Node info
   */
  private void addHelp(Node n, int offset, E item)
  {
	  int number = n.count;    // number of element in current node
	  Node pre   = n.previous; //current's previous node 
	  

	  // if previous node is not head and has elements < nodeSize , add item to previous node  
	  if( offset == 0 && (pre!= head && pre.count < nodeSize)) 
	  {
		  pre.addItem(item);
		   
	  }
	  // if current node is tail and previous node is full, then create an new node add item to offset 0
	  else if( offset == 0 &&( n == tail && n.count == nodeSize)) 
	  {
		  Node temp = new Node();
		  temp.addItem(0,item);
		  temp.previous      = tail.previous;
		  temp.next          = tail;
		  tail.previous.next = temp;
		  tail.previous      = temp; 
	  }

	  // if there is space in the current node, add item at offset
	  else if(n.count < nodeSize)
	  {
		  n.addItem(offset, item);
	  }
	  // split operation, 
	  else
	  {
		  Node temp       = new Node();// create a new node , then link with current node
		  temp.previous   = n;
		  temp.next       = n.next;
		  n.next.previous = temp;
		  n.next          = temp;
		  // move the last half of the element into new node with. the index of the array start with nodeSize/2  
		  for(int i = nodeSize/2; i < nodeSize; i++)
		  {
			  // since removeItem shift array to the left, we have to make sure to stay in the same offset
			  temp.addItem(n.data[nodeSize/2]); 
			  n.removeItem(nodeSize/2);  
		  }
		  if(offset <= nodeSize/2)
		  {
			  n.addItem(offset, item);
		  }
		  else // offset > M/2
		  {
			  n.addItem(offset - nodeSize/2, item);
		  }
	  }  
  }
 
  private class StoutListIterator implements ListIterator<E>
  {
	  //points the element behind the cursor
	  private static final int BEHIND = -1;
	  
	  //points the element ahead the cursor 
	  private static final int AHEAD  = 1;
	  
	  // no direction
	  private static final int NONE   = 0;
      
	  // currentNode where cursor points at
	  private Node currentN;
	 
	  //cursor logic index position 
	  private int index;
	  
	  //cursor position
	  private int direction;
	  
	  //offset in the node
	  private int off;
	  
    /**
     * Default constructor 
     */
    public StoutListIterator()
    {
    	this(0);
    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
    	if (pos < 0 || pos > size) throw new IndexOutOfBoundsException();
    	NodeInfo info = find(pos);
    	this.currentN = info.node;
    	this.index    = pos;
    	this.direction= NONE;
    	this.off      = info.offset;
    }

    @Override
    public boolean hasNext()
    {
		return this.index < size;
    }

    @Override
    public E next()
    {
    	if (!hasNext()) throw new NoSuchElementException();

		if (currentN == null ) currentN = head.next; 
        
		E ret  = this.currentN.data[off];

		this.off ++;
		if(this.off == this.currentN.count) // if element is the last one in the node, next() should go to the next node
		{
			this.currentN = this.currentN.next;
			this.off      = 0;
		}
		
		//E ret  = this.currentN.data[off];
		this.index += 1;
		this.direction = BEHIND;
		//this.off ++;
		return ret;
    }

    @Override
    public void remove()
    {
    	if (direction == NONE) throw new IllegalStateException(); // next and previous has not been called
    	
    	if(direction == BEHIND) // remove the element that just returned from next
    	{
    		StoutList.this.remove(previousIndex());
    		previous();  // decrement cursor by 1 unit
    	}
    	else // direction == previous and remove the element that is removed from previous
    	{
    		StoutList.this.remove(nextIndex());
    	}
    	//this.index--;
    	this.direction = NONE;
    }

	@Override
	public boolean hasPrevious() {
		return this.index >0;
	}

	@Override
	public E previous() {

		if (!hasPrevious()) throw new NoSuchElementException();
		//E ret = null;
		//		this.off--;
		if(off != 0 )//&& currentN.previous != head) 
		{// if element is not at the first offset in the array
			off--; 
		}
		else
		{// if this element is at the offset 0 then the currentNode has to move to the current.previous
			this.currentN = this.currentN.previous;
			off = this.currentN.count - 1; // ABC count is 3 , c's offset is 2
		}
		this.index --;
		this.direction = AHEAD;
		return currentN.data[off];
	}

	@Override
	public int nextIndex() {
		
		return this.index;
	}

	@Override
	public int previousIndex() {
		
		return this.index-1;
	}

	@Override
	public void set(E e) {
		if (direction == NONE) throw new IllegalStateException();
		if (e == null) throw new NullPointerException();
		
	    if( direction == AHEAD)
		{
	    		currentN.data[off] = e;
		}
		else// direction is behind the cursor 
		{
			if(off == 0) // the first element in the node, set should go back the last element in the previous node
			{
				currentN.previous.data[currentN.previous.count - 1] = e;
			}
			else
			{
				currentN.data[off - 1] = e;
			}
		}
	}

	@Override
	public void add(E e) {
		 
		 StoutList.this.add(index, e);
		 ++index;
		 find(index);
		 off =  find(index).offset;
		 this.direction = NONE;
		 //StoutList.this.add(index, e);
//		if(size == 0)
//		{
//			Node temp = new Node();
//			temp.addItem(e);
//			temp.previous = head;
//			temp.next     = tail;
//			head.next     = temp;
//			tail.previous = temp;
//		}
//		else
//		{
////			NodeInfo n = find(index);       // find where current node information to get current node and the offset
////			Node curr  = n.node;          // current node 
////			int off    = n.offset;
//			addHelp(currentN,off,e);
//		}
//		 //this.off   ++;
//		 this.index ++;
//		 size       ++;
//		 this.direction = NONE; 
	}
    // Other methods you may want to add or override that could possibly facilitate 
    // other operations, for instance, addition, access to the previous element, etc.
    // 
    // ...
    // 
  }
  /**
   * comparator use for the insertion and bubble sort
   * @author Chen Sang
   *
   * @param <E>
   */
  class thisComparator<E extends Comparable<? super E>> implements Comparator<E>
  {
	@Override
	public int compare(E o1, E o2) {
		return o1.compareTo(o2);
	}  
  }

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  for(int i = 1; i < arr.length; i++)
		{
			E key = arr[i];
			int j = i - 1;  // element in front of the key
			while(j >= 0 && comp.compare(arr[j],key) > 0) 
			{
				arr[j + 1] = arr[j];
	            j         -= 1;
			}
			arr[j + 1] = key;
		}
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	  // Looked this part on https://www.geeksforgeeks.org/bubble-sort/
	  thisComparator hi = new thisComparator();
	  int n             = arr.length;
      
	  for (int i = 0; i < n - 1; i++)
          for (int j = 0; j < n - i - 1; j++)
              if (hi.compare(arr[j], arr[j + 1]) < 0) 
              {   // do the swap
                  E temp     = arr[j];
                  arr[j]     = arr[j + 1];
                  arr[j + 1] = temp;
              }
  }
}