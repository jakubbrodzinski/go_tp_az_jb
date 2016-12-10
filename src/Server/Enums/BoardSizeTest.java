package Server.Enums;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by jakub on 12/10/16.
 */
public class BoardSizeTest {
	@Test
	public void getSize() throws Exception {
		assertTrue(BoardSize.MEDIUM.getSize()==13);
		assertTrue(BoardSize.SMALL.getSize()==9);
		assertTrue(BoardSize.LARGE.getSize()==19);
	}

}