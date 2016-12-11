package Server.Main;

import Server.Enums.BoardSize;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by jakub on 12/11/16.
 */
public class GameGOTest {
	private GameGO game;
	@Before
	public void setUp() throws Exception {
		game=new GameGO(BoardSize.MEDIUM);
	}

	@After
	public void tearDown() throws Exception {
		game=null;
	}

	@Test
	public void getScore() throws Exception {
		assertEquals("0.5-5.0",game.getScore("ENDPROPOSITION-BLACK-N-2-M-2-N-1-M-1-WHITE-A-1-A-2-BLACKP-D-7-G-5-J-5-WHITEP-H-10-F-8-J-7"));
	}

}