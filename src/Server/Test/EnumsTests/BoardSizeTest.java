package Server.Test.EnumsTests;

import Server.Enums.BoardSize;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jakub on 12/7/16.
 */
public class BoardSizeTest {
	private BoardSize[] tab;

	@Before
	public void setUp() throws Exception {
		tab=new BoardSize[3];
		tab[0]=BoardSize.SMALL;
		tab[1]=BoardSize.MEDIUM;
		tab[2]=BoardSize.LARGE;
	}

	@After
	public void tearDown() throws Exception {
		tab[0]=tab[2]=tab[1]=null;
	}

	@Test
	public void getSize() throws Exception {
		assertEquals(9,tab[0].getSize());
		assertEquals(13,tab[1].getSize());
		assertEquals(19,tab[2].getSize());
	}

}