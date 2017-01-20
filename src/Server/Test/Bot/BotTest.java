package Server.Test.Bot;

import Server.BotGO.Bot;
import Server.Enums.BoardSize;
import Server.Enums.stoneColor;
import Server.Main.BoardPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by jakub on 12/10/16.
 */
public class BotTest {
	private Bot[] ourBot;
	@Before
	public void setUp() throws Exception {
		ourBot=new Bot[3];
		ourBot[0]=new Bot(BoardSize.SMALL, stoneColor.WHITE);
		ourBot[1]=new Bot(BoardSize.MEDIUM, stoneColor.WHITE);
		ourBot[2]=new Bot(BoardSize.LARGE, stoneColor.WHITE);
	}

	@After
	public void tearDown() throws Exception {
		ourBot[0]=null;
		ourBot[1]=null;
		ourBot[2]=null;
	}

	@Test
	public void insertChanges() throws Exception {
		ourBot[0].insertChanges(new BoardPoint('C',7));
		ourBot[0].insertChanges(new BoardPoint('E',5));
		ourBot[1].insertChanges(new BoardPoint('D',10));
		ourBot[1].insertChanges(new BoardPoint('G',7));
		ourBot[2].insertChanges(new BoardPoint('D',16));
		ourBot[2].insertChanges(new BoardPoint('K',10));
		assertEquals(new BoardPoint('G',3),ourBot[0].nextBotMove());
		assertEquals(new BoardPoint('K',4),ourBot[1].nextBotMove());
		assertEquals(new BoardPoint('Q',4),ourBot[2].nextBotMove());


	}

	@Test
	public void nextBotMove() throws Exception {
		assertEquals(new BoardPoint('C',7),ourBot[0].nextBotMove());
		assertEquals(new BoardPoint('D',10),ourBot[1].nextBotMove());
		assertEquals(new BoardPoint('D',16),ourBot[2].nextBotMove());
		for(int i=0;i<80;i++){
			ourBot[0].nextBotMove();
			ourBot[1].nextBotMove();
			ourBot[2].nextBotMove();
		}
	}

}