package Server.Test.MainTests;

import Server.Main.BoardPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by jakub on 12/10/16.
 */
public class BoardPointTest {
	BoardPoint obj1,obj2;

	@Before
	public void setUp() throws Exception {
		obj1=new BoardPoint('B',10);
		obj2=new BoardPoint(1,9);
	}

	@After
	public void tearDown() throws Exception {
		obj1=null;
		obj2=null;
	}

	@Test
	public void getHorizontal() throws Exception {
		assertEquals(10,obj1.getHorizontal());
		assertEquals(10,obj2.getHorizontal());
	}

	@Test
	public void getIntegerVertical() throws Exception {
		assertEquals(2,obj1.getIntegerVertical());
		assertEquals(2,obj2.getIntegerVertical());
	}

	@Test
	public void getVertical() throws Exception {
		assertEquals('B',obj1.getVertical());
		assertEquals('B',obj2.getVertical());
	}

	@Test
	public void equals() throws Exception {
		assertTrue(obj1.equals(obj2));
	}

}