/**
 *
 */
package textgen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author UC San Diego MOOC team
 */
public class MyLinkedListTester {

    private static final int LONG_LIST_LENGTH = 10;

    MyLinkedList<String> shortList;
    MyLinkedList<Integer> emptyList;
    MyLinkedList<Integer> longerList;
    MyLinkedList<Integer> list1;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Feel free to use these lists, or add your own
        shortList = new MyLinkedList<String>();
        shortList.add("A");
        shortList.add("B");
        emptyList = new MyLinkedList<Integer>();
        longerList = new MyLinkedList<Integer>();
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            longerList.add(i);
        }
        list1 = new MyLinkedList<Integer>();
        list1.add(65);
        list1.add(21);
        list1.add(42);

    }


    /**
     * Test if the get method is working correctly.
     */
    /*You should not need to add much to this method.
     * We provide it as an example of a thorough test. */
    @Test
    public void testGet() {
        //test empty list, get should throw an exception
        try {
            emptyList.get(0);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }

        // test short list, first contents, then out of bounds
        assertEquals("Check first", "A", shortList.get(0));
        assertEquals("Check second", "B", shortList.get(1));

        try {
            shortList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            shortList.get(2);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        // test longer list contents
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            assertEquals("Check " + i + " element", (Integer) i, longerList.get(i));
        }

        // test off the end of the longer array
        try {
            longerList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            longerList.get(LONG_LIST_LENGTH);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {
        }

    }


    /**
     * Test removing an element from the list.
     * We've included the example from the concept challenge.
     * You will want to add more tests.
     */
    @Test
    public void testRemove() {
        int a = list1.remove(0);
        assertEquals("Remove: check a is correct ", 65, a);
        assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
        assertEquals("Remove: check size is correct ", 2, list1.size());
        try {
            emptyList.remove(0);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }

        for (int i = 1; i < LONG_LIST_LENGTH; i++) {
            longerList.remove(1);
        }
        assertEquals("Remove: check size is correct ", 1, longerList.size());
        assertEquals("Remove: check element 0 is correct ", (Integer) 0, longerList.get(0));
        longerList.remove(0);
        assertEquals("Remove: check size is correct ", 0, longerList.size());
        // TODO: Add more tests here
    }

    /**
     * Test adding an element into the end of the list, specifically
     * public boolean add(E element)
     */
    @Test
    public void testAddEnd() {
        // TODO: implement this test
        emptyList.add(11); //size=1
        assertEquals(1, emptyList.size);
        emptyList.add(12); //size=2
        assertEquals(2, emptyList.size);
        emptyList.add(13); //size=3
        assertEquals(3, emptyList.size);
        emptyList.set(2, 14); //size=3
        assertEquals(3, emptyList.size);
        emptyList.remove(1); //size=2
        assertEquals(2, emptyList.size);
//        assertEquals("forward: 11->14->\n" +
//                "backward: 14->11->", emptyList.toString());
    }


    /**
     * Test the size of the list
     */
    @Test
    public void testSize() {
        // TODO: implement this test
        emptyList.add(0, 11); //size=1
        emptyList.add(12); //size=2
        emptyList.add(0, 13); //size=3
        emptyList.set(2, 14); //size=3
        emptyList.remove(1); //size=2
        assertEquals(2, emptyList.size());
    }


    /**
     * Test adding an element into the list at a specified index,
     * specifically:
     * public void add(int index, E element)
     */
    @Test
    public void testAddAtIndex() {
        // TODO: implement this test
        //test empty list, get should throw an exception
        emptyList.add(0, 11);
        assertEquals("Check first", (Integer) 11, emptyList.get(0));
        assertEquals("Check size", 1, emptyList.size);

        // test short list, first contents, then out of bounds
        shortList.add(0, "E");
        shortList.add(1, "F");
        System.out.println(shortList);

        assertEquals("Check first", "E", shortList.get(0));
        assertEquals("Check second", "F", shortList.get(1));
        assertEquals("Check third", "A", shortList.get(2));
        assertEquals("Check fourth", "B", shortList.get(3));
        assertEquals("Check size", 4, shortList.size);

        try {
            shortList.get(4);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            shortList.add(2, null);
            fail("Null element");
        } catch (NullPointerException e) {

        }
        // test longer list contents
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            longerList.add(i * 2, i);
        }

        for (int i = 0, j = 0; i < 2 * LONG_LIST_LENGTH; i++, j = i / 2) {
            assertEquals("Check " + i + " element", (Integer) j, longerList.get(i));
        }

        // test off the end of the longer array
        try {
            longerList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            longerList.get(2 * LONG_LIST_LENGTH);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {
        }


    }

    /**
     * Test setting an element in the list
     */
    @Test
    public void testSet() {
        // TODO: implement this test
        try {
            emptyList.set(1, 11);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }

        // test short list, first contents, then out of bounds
        assertEquals("Insert first", "A", shortList.set(0, "E"));
        assertEquals("Insert second", "B", shortList.set(1, "F"));

        try {
            shortList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            shortList.get(2);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            shortList.set(1, null);
            fail("Null element");
        } catch (NullPointerException e) {

        }
        // test longer list contents
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            assertEquals("Check " + i + " element", (Integer) i, longerList.set(i, i * 2));
        }
        assertEquals("Check size", LONG_LIST_LENGTH, longerList.size());
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            assertEquals("Check " + i + " element", (Integer) (i * 2), longerList.get(i));
        }
        // test off the end of the longer array
        try {
            longerList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            longerList.get(LONG_LIST_LENGTH);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {
        }

    }


    // TODO: Optionally add more test methods.

}
