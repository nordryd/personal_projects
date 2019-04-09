package smaller.josephus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit for the Josephus problem program.
 * @author nordryd
 */
public class JosephusTest
{
	@Test
	public void testJosephus() {
		int answer = Josephus.josephus(41);
		int expected = 19;
		
		System.out.println(answer);
		
		assertEquals(expected, answer);
	}
}
