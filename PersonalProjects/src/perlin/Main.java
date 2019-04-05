package perlin;

import perlin.computation.Perlin;

/**
 * Main class for the Perlin Noise program.
 * @author Nordryd
 */
public class Main
{
	public static void main(String[] args) {
		final int Rows = 8;
		final int Cols = 8;

		double[][] noise = Perlin.generateNoise(Rows, Cols);
		
		for(double[] row : noise) {
			for(double value : row) {
				System.out.printf("%.3f ", value);
			}
			System.out.print("\n");
		}
	}
}
