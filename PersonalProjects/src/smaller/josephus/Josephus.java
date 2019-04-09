package smaller.josephus;

/**
 * <p>
 * Class to represent the Josephus problem:
 * </p>
 * 
 * <p>
 * Suppose <i>n</i> people are in a circle facing each other, where <i>n</i>
 * &isin; <i>N</i>. Starting with the 1st participant, he kills the participant
 * next to him. The next living participant kills the participant next to him.
 * This continues until one participant is left alive (and is the winner).
 * </p>
 * 
 * <p>
 * For any <i>n</i> participants, where <i>n</i> &isin; <i>N</i>, where does one
 * have to stand in order to win?
 * </p>
 * 
 * @author nordryd
 */
public class Josephus
{
	public static int josephus(int participants) {
		int exponent = 0;
		while(Math.pow(2, exponent) < participants) {
			exponent++;
		}
		
		return (Math.pow(2, exponent) == participants) ? 1 : ((participants & (int)(Math.pow(2, exponent - 1) - 1)) << 1) | 1;
	}
	
}
