package bowling;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

public class BowlingTest {
	
	static BowlingGame bow;
	int result;
	
	@Before
	public void setup() {
		bow = new BowlingGame();
		result = 0;
	}
	
	@Test
	public void pointOperation() {
		result = bow.pointCalculate();
		assertThat(result, is(notNullValue()));
	}
	
	@After
	public void endGame() {
		bow.endGame(result);
	}
}
