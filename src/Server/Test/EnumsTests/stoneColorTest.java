package Server.Test.EnumsTests;

import Server.Enums.stoneColor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by jakub on 12/7/16.
 */
public class stoneColorTest {
	private stoneColor temp0,temp1,temp2,temp3,temp4;
	@Before
	public void setUp() throws Exception {
		temp1=null;
		temp0=null;
		temp2=stoneColor.WHITE;
		temp3=stoneColor.UNDEFINED;
		temp4=stoneColor.BLACK;
	}

	@Test
	public void stoneTest() throws  Exception{
		assertEquals(true,temp1==temp0);
		assertEquals(false,temp1==temp3);
		assertEquals(false,temp1==stoneColor.WHITE);
		assertEquals(false,temp4==temp3);
		assertEquals(false,temp3==temp2);
	}

	@Test
	public void opposite() throws Exception {
		assertTrue(temp4.opposite()==stoneColor.WHITE);
		assertTrue(temp2.opposite()==stoneColor.BLACK);
		assertTrue(temp3.opposite()==stoneColor.UNDEFINED);
	}

	@After
	public void tearDown() throws Exception {
		temp1=null;
		temp2=null;
		temp2=null;
		temp3=null;
		temp4=null;
	}

}