package Server.Test;

import Server.Enums.BoardSize;
import Server.Enums.stoneColor;
import Server.Main.BoardPoint;
import Server.Main.GameLogicGO;
import Server.Main.WrongMoveException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by jakub on 12/4/16.
 */
public class GameLogicGOTest {
	private GameLogicGO GameToTest;
	@Before
	public void setUp() throws Exception {
		GameToTest=new GameLogicGO();
		GameToTest.setSize(BoardSize.MEDIUM);
	}

	@After
	public void tearDown() throws Exception {
		GameToTest=null;
	}



	@Test(expected = WrongMoveException.class)
	public void nextMove() throws Exception {
		GameToTest.nextMove(new BoardPoint('K',11),stoneColor.BLACK);
		GameToTest.nextMove(new BoardPoint('J',11),stoneColor.BLACK);
		GameToTest.nextMove(new BoardPoint('L',11),stoneColor.BLACK);
		GameToTest.nextMove(new BoardPoint('K',9),stoneColor.BLACK);
		GameToTest.nextMove(new BoardPoint('J',9),stoneColor.BLACK);
		GameToTest.nextMove(new BoardPoint('L',9),stoneColor.BLACK);
		GameToTest.nextMove(new BoardPoint('J',10),stoneColor.BLACK);
		GameToTest.nextMove(new BoardPoint('L',10),stoneColor.BLACK);
		GameToTest.nextMove(new BoardPoint('H',9), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('H',10), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('H',11), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('J',12), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('K',12), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('L',12), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('J',8), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('K',8), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('L',8), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('M',9), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('M',10), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('M',11), stoneColor.WHITE);
		GameToTest.DrawBoard();
		assertNotEquals(null,GameToTest.nextMove(new BoardPoint('K',10), stoneColor.WHITE));
		GameToTest.DrawBoard();

		GameToTest.nextMove(new BoardPoint('A',13), stoneColor.BLACK);
		GameToTest.nextMove(new BoardPoint('A',12), stoneColor.WHITE);
		GameToTest.DrawBoard();
		assertNotEquals(null,GameToTest.nextMove(new BoardPoint('B',13), stoneColor.WHITE));
		GameToTest.DrawBoard();

		GameToTest.nextMove(new BoardPoint('C',10), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('B',9), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('D',9), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('C',6), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('B',7), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('D',7), stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('C',7),stoneColor.BLACK);
		GameToTest.nextMove(new BoardPoint('C',9),stoneColor.BLACK);
		GameToTest.DrawBoard();
		assertNotEquals(null,GameToTest.nextMove(new BoardPoint('C',8), stoneColor.WHITE));
		GameToTest.DrawBoard();

		assertEquals(new BoardPoint('E',4),GameToTest.nextMove(new BoardPoint('E', 4), stoneColor.WHITE)[0]);
		assertEquals(new BoardPoint('E',5),GameToTest.nextMove(new BoardPoint('E', 5), stoneColor.BLACK)[0]);
		GameToTest.DrawBoard();

		GameToTest.nextMove(new BoardPoint('K',2),stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('J',3),stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('L',3),stoneColor.WHITE);
		GameToTest.nextMove(new BoardPoint('K',3),stoneColor.BLACK);
		GameToTest.DrawBoard();
		assertNotEquals(null,GameToTest.nextMove(new BoardPoint('K',4), stoneColor.WHITE));
		GameToTest.DrawBoard();

		GameToTest.nextMove(new BoardPoint('B', 13), stoneColor.BLACK);
	}
}