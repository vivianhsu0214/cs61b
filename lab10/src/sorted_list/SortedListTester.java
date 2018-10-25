package sorted_list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * SortedList(tm) Tester
 *
 * COPYRIGHT 2003 FLIK ENTERPRISES CORP.
 * ALL RIGHTS RESERVED
 */
public class SortedListTester {

	@Test
	public void testIsListSorted() {
		List<Integer> sortedList = Arrays.asList(-40, 1, 5, 7, 50, 494, 481293);
		List<Integer> unsortedList = Arrays.asList(48, 29, 491, 5, 2, -239);

		assertTrue(SortedListHelper.isListSorted(sortedList));
		assertFalse(SortedListHelper.isListSorted(unsortedList));
	}

	@Test(timeout = 1000)
	public void testInsertIntoSortedList() {
		// TODO Allan please add more tests

		List<Integer> list = new ArrayList<Integer>();
		list.add(-40);
		list.add(1);
		list.add(5);
		list.add(7);
		list.add(50);
		list.add(494);
		list.add(481293);

		SortedListHelper.insertIntoSortedList(list, 40);
		assertEquals(8, list.size());
		assertTrue(SortedListHelper.isListSorted(list));

		SortedListHelper.insertIntoSortedList(list, 51);
		assertEquals(9, list.size());
		assertTrue(SortedListHelper.isListSorted(list));
	}
}
