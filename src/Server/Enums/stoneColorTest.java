package Server.Enums;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by jakub on 12/10/16.
 */
public class stoneColorTest {
	private stoneColor[] tab;
	@Before
	public void setTab(){
		tab=new stoneColor[3];
		tab[0]=stoneColor.BLACK;
		tab[1]=stoneColor.WHITE;
		tab[2]=stoneColor.UNDEFINED;
	}

	@After
	public void tearDown(){
		tab[0]=null;tab[1]=null;tab[2]=null;
	}

	@Test
	public void opposite() throws Exception {
		assertTrue(tab[0].opposite()==stoneColor.WHITE);
		assertTrue(tab[1].opposite()==stoneColor.BLACK);
		assertTrue(tab[2].opposite()==stoneColor.UNDEFINED);
	}

}