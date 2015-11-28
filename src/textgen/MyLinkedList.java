package textgen;

import java.util.AbstractList;


/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    int size;

    /**
     * Create a new empty LinkedList
     */
    public MyLinkedList() {
        head = tail = new LLNode<>(null);
    }

    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     */
    public boolean add(E element) {
        if (element == null)
            throw new NullPointerException("Null element");
        if (size > 0) {
            LLNode node = new LLNode(element);
            node.prev = tail;
            tail.next = node;
            tail = node;
        } else {
            head.data = element;
        }
        size++;
        return true;
    }

    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {
        validateBounds(index);
        LLNode node = head;
        while (index-- > 0) {
            node = node.next;
        }
        return (E) node.data;
    }

    /**
     * Add an element to the list at the specified index
     *
     * @param index   where the element should be added
     * @param element The element to add
     */
    public void add(int index, E element) {
        if (element == null)
            throw new NullPointerException("Null element");
        if (index < 0 || (index > size))
            throw new IndexOutOfBoundsException("Check out of bounds");

        if (size == 0) {
            head.data = element;
        } else {
            LLNode node = head;
            while (index-- > 0) {
                node = node.next;
            }
            LLNode newNode = new LLNode(element);
            if (node.prev != null) {
                newNode.prev = node.prev;
                newNode.prev.next = newNode;
                newNode.next = node;
                node.prev = newNode;
            } else {
                head.prev = newNode;
                newNode.next = head;
                head = newNode;
            }
        }
        size++;
    }


    /**
     * Return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     */
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Check out of bounds");
        E data;
        if (size == 1) {
            data = head.data;
            head.data = null;
        } else {
            LLNode node = head;
            while (index-- > 0) {
                node = node.next;
            }
            data = (E) node.data;
            if (node.next != null)
                node.next.prev = node.prev;
            if (node.prev != null)
                node.prev.next = node.next;
            else
                head = node.next;
        }
        size--;

        return data;
    }

    private void validateBounds(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Check out of bounds");
    }

    /**
     * Set an index position in the list to a new element
     *
     * @param index   The index of the element to change
     * @param element The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E set(int index, E element) {
        validateBounds(index);
        if (element == null)
            throw new NullPointerException("Null element");
        E replaced;
        if (size < 2) {
            replaced = head.data;
            head.data = element;
        } else {
            LLNode node = head;
            while (index-- > 0) {
                node = node.next;
            }
            replaced = (E) node.data;
            node.data = element;
        }
        return replaced;
    }

    /*@Override
    public String toString() {
        if (size > 0) {
            StringBuilder sb = new StringBuilder("forward: ");
            LLNode node = head;
            do {
                sb.append(node.data).append("->");
                node = node.next;
            } while (node != null);
            sb.append("\n").append("backward: ");
            node = tail;
            do {
                sb.append(node.data).append("->");
                node = node.prev;
            } while (node != null);
            return sb.toString();
        } else return "<EMPTY>";
    }*/
}

class LLNode<E> {
    LLNode<E> prev;
    LLNode<E> next;
    E data;

    // TODO: Add any other methods you think are useful here
    // E.g. you might want to add another constructor

    public LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }

}
