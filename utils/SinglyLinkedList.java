package utils;

public class SinglyLinkedList<E> {
  //---------------- nested Node class ----------------
  private static class Node<E> {
    private E element;
    // reference to the element stored at this node
    private Node<E> next;
    // reference to the subsequent node in the list
    public Node(E e, Node<E> n) {
      element = e;
      next = n;
    }
    public E getElement() {
      return element; 
    }
    public Node<E> getNext() {
      return next; 
    }
    public void setNext(Node<E> n) {
      next = n; 
    }
  } //----------- end of nested Node class -----------
  // instance variables of the SinglyLinkedList
  private Node<E> head = null;
  // head node of the list (or null if empty)
  private Node<E> tail = null;
  // last node of the list (or null if empty)
  private int size = 0;
  // number of nodes in the list
  public SinglyLinkedList() {} // constructs an initially empty list
                               // access methods
  public int size() { 
    return size; 
  }
  public boolean isEmpty() { 
    return size == 0; 
  }
  public E first() {
    // returns (but does not remove) the first element
    if (isEmpty()) return null;
    return head.getElement();
  }
  public E last() {
    // returns (but does not remove) the last element
    if (isEmpty()) return null;
    return tail.getElement();
  }
  // update methods
  public void addFirst(E e) {
    // adds element e to the front of the list
    head = new Node<>(e, head);
    // create and link a new node
    if (size == 0)
      tail = head;
    // special case: new node becomes tail also
    size++;
  }
  public void addLast(E e) {
    // adds element e to the end of the list
    Node<E> newest = new Node<>(e, null); // node will eventually be the tail
    if (isEmpty())
      head = newest;
    // special case: previously empty list
    else
      tail.setNext(newest);
    // new node after existing tail
    tail = newest;
    // new node becomes the tail
    size++;
  }
  public E removeFirst() {
    // removes and returns the first element
    if (isEmpty()) return null;
    // nothing to remove
    E answer = head.getElement();
    head = head.getNext();
    // will become null if list had only one node
    size--;
    if (size == 0)
      tail = null;
    // special case as list is now empty
    return answer;
  }

  public static void main(String[] args) {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
    System.out.println(list.getClass() == stringList.getClass());
  }
}










/* PRIMARY TAKEAWAY: TYPE ERASURE => COMPILE-TIME TYPE INSERTION => RUNTIME REMOVAL OF GENERICS FOR MEMORY EFFICIENCY AND SINGULAR CLASS OF BOUNDED OR OBJECT TYPES WITH TYPE CASTING TO HANDLE INSTANCES => JVM CANNOT DETECT FORMAL PARAMETER TYPES ON RUNTIME (CALLING METHODS IS DONE AT RUNTIME, AND SO IS CONSTRUCTING A CLASS -> TYPE ERASURE)
 *
 * From my understanding on Oracle's docs, 
 * {@see @link https://docs.oracle.com/javase/tutorial/java/generics/erasure.html}
 * Type Erasure replaces parameterized types in compile time with their bounded types (e.g., <T extends Bruh>, --> Bruh type), or Object if unbounded. "The produced bytecode therefore contains only ordinary classes, interfaces, and methods" which removes the need to produce multiple classes for parameterized types. So at runtime, you'll only have LinkedList types instead of class type LinkedList<E> where E is defined.
 * 
 * Recall that traditionally, before formal parameter types, the Object reference type was used in lieu of generics, and it is extremely prone to narrow type conversion which is both redundant as to deal explicitly, and ineffective as to potentially prune data ineffectively without the use of autoboxing or wrapper classes on primitive types. Generics, despite their grand beauty of solving this issue, declares the actual types (revisit polymorphism, e.g. A(formal/declarative) e = new B(informal/actual)) at runtime, not compile time, from within the Object instances.
 * 
 * In essence, type erasure is the removal of parametrized types on runtime to prevent the exception of multi-instance handling via multiple classes, causing the main class to eradicate parameter types and replace generics with their bounded types or Object if unbounded, then conducting type casts when necessary. However, the runtime classes or class instances have their types strictly defined, and thus the "classic nonparametrized" approach of passing superset class type Object to the equals gives access to the getClass method to return the metadata which includes all of the runtime classes' members, including types, to compare to. Due to type erasure, using generics in the passthrough type will just be of type LinkedList, preventing deepEquals.
 * 
 * @Revisited
 * Passing type Object doesn't matter, since type LinkedList is a subclass of Object, and adhering to the Object method `equals` is a necessary contract for the override process, and is anyway required to return falsiness if incomparable.
 * Type erasure is irrelevant to why the equals method accepts a parameter of type Object, but explains why you cannot check the parameterized types of the other SinglyLinkedList object, and so you'd have to work with nonparameterized types and do matches on iteration. As type erasure suggests, declared formal parameter types are removed on runtime (types assigned in compile time), but the equals method operates off the runtime instance.
 * 
 * "Generics incur no runetime overhead," no additional memory by creating new classes suchas XLinkedList or YLinkedList, instead LinkedList objects of the LinkedList class are created*
 *   public boolean equals(Object o) {
 *     if (o == null) return false;
 *     if (getClass() != o.getClass()) return false;
 *     SinglyLinkedList other = (SinglyLinkedList) o; // use nonparameterized type
 *     if (size != other.size) return false;
 *     Node walkA = head; // traverse the primary list
 *     Node walkB = other.head; // traverse the secondary list
 *     while (walkA != null) {
 *       if (!walkA.getElement().equals(walkB.getElement())) return false; //mismatch
 *       walkA = walkA.getNext();
 *       walkB = walkB.getNext();
 *     }
 *     return true;
 *     // if we reach this, everything matched successfully
 *   }
 */
