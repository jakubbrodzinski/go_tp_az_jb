package Server.Test;

import Server.BoardSize;
import Server.Main.BoardPoint;
import Server.Main.GameLogicGO;
import Server.stoneColor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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



	@Test
	public void nextMove() throws Exception {
		GameToTest.nextMove(new BoardPoint('K',10), stoneColor.WHITE);
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
		assertEquals(true,GameToTest.NoIdeaHowToNameThisMethod(new BoardPoint('K',10),stoneColor.WHITE));

	}


}