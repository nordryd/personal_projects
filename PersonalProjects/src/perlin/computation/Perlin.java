package perlin.computation;

import java.util.Random;

/**
 * Class to generate Perlin noise at a given point. (update later to return a
 * matrix?)
 * 
 * @author nordryd
 */
public class Perlin
{
	private static final int MAX = 255;
	private static final int DEFAULT_OCTAVES = 1;
	private static final double DEFAULT_PERSIST = 0.1;
	
	private static final double START_AMPLITUDE = 1;
	private static final double START_FREQUENCY = 1;

	// Hash lookup table as defined by Ken Perlin. This is a randomly arranged array
	// of all numbers from 0-255 inclusive.
	private static final int[] PERMUTATION = { 151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225,
			140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94,
			252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74,
			165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41,
			55, 46, 245, 40, 244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169,
			200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 5,
			202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 223, 183,
			170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 129, 22, 39, 253, 19, 98,
			108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12,
			191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204,
			176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195,
			78, 66, 215, 61, 156, 180 };

	// Used in a hash function to determine what gradient vector to use later on.
	private static final int[] P;
	private static final Random RAND;
	private static int repeat = PERMUTATION.length;

	/**
	 * Generates a matrix of a single octave of Perlin noise.
	 * 
	 * @param xLen The length of the x-axis starting from the origin.
	 * @param yLen The length of the y-axis starting from the origin.
	 * @return Perlin noise matrix.
	 */
	public static double[][] generateNoise(int xLen, int yLen) {
		return generateNoise(xLen, yLen, DEFAULT_OCTAVES, DEFAULT_PERSIST);
	}

	/**
	 * Generates a matrix of Perlin noise with a certain number of octaves.
	 * 
	 * @param xLen        The length of the x-axis starting from the origin.
	 * @param yLen        The length of the y-axis starting from the origin.
	 * @param octaves     The number of octaves of noise to generate.
	 * @param persistence The influence each successive octave has quantitatively.
	 * @return Perlin noise matrix.
	 */
	public static double[][] generateNoise(int xLen, int yLen, int octaves, double persistence) {
		double[][] matrix = new double[xLen][yLen];

		if (octaves <= DEFAULT_OCTAVES) {
			for (int row = 0; row < xLen; row++) {
				for (int col = 0; col < yLen; col++) {
					matrix[row][col] = perlin(row * RAND.nextDouble(), col * RAND.nextDouble(),
							(row + col) * RAND.nextDouble());
				}
			}
		}
		else {
			for (int row = 0; row < xLen; row++) {
				for (int col = 0; col < yLen; col++) {
					matrix[row][col] = perlin(row * RAND.nextDouble(), col * RAND.nextDouble(),
							(row + col) * RAND.nextDouble(), octaves, persistence);
				}
			}
		}

		return matrix;
	}

	private static double perlin(double inX, double inY, double inZ, int octaves, double persistence) {
		double total = 0;
		double frequency = START_FREQUENCY;
		double amplitude = START_AMPLITUDE;
		double maxValue = 0; // used for normalizing result to 0.0 - 1.0

		for (int octave = 0; octave < octaves; octave++) {
			total += perlin(inX * frequency, inY * frequency, inZ * frequency) * amplitude;

			maxValue += amplitude;

			amplitude *= persistence;
			frequency *= 2;
		}

		return (total / maxValue);
	}

	private static double perlin(double inX, double inY, double inZ) {
		double x = inX, y = inY, z = inZ;
		// If we have any repeat on, change the coordinates to their "local" repititions
		if (repeat > 0) {
			x = x % repeat;
			y = y % repeat;
			z = z % repeat;
		}

		// Calculate the "unit cube" that the point asked will be located in. The left
		// bound is ( [_x_], [_y_], [_z_] ) and the right bound is that plus 1. Next we
		// calculate the location (from 0.0 to 1.0) in that cube.
		int xi = ((int) x) & MAX;
		int yi = ((int) y) & MAX;
		int zi = ((int) z) & MAX;
		double xf = x - ((int) x);
		double yf = y - ((int) y);
		double zf = z - ((int) z);

		double u = fade(xf);
		double v = fade(yf);
		double w = fade(zf);

		// Hash function used by Perlin noise.
		// Hashes all 8 unit cubes surrounding the input coordinate. The result of the
		// hash function is a value between 0 and 255 inclusive.
		int aaa, aba, aab, abb, baa, bba, bab, bbb;
		aaa = P[P[P[xi] + yi] + zi];
		aba = P[P[P[xi] + inc(yi)] + zi];
		aab = P[P[P[xi] + yi] + inc(zi)];
		abb = P[P[P[xi] + inc(yi)] + inc(zi)];
		baa = P[P[P[inc(xi)] + yi] + zi];
		bba = P[P[P[inc(xi)] + inc(yi)] + zi];
		bab = P[P[P[inc(xi)] + yi] + inc(zi)];
		bbb = P[P[P[inc(xi)] + inc(yi)] + inc(zi)];

		// The gradient function calculates the dot product between a pseudorandom
		// gradient vector and the vector from the input coordinate to the 8 surrounding
		// points in its unit cube. This is all then lerped together as a sort of
		// weighted average based on the faded (u, v, w) values we made earlier.
		double x1, x2, y1, y2;
		x1 = lerp(grad(aaa, xf, yf, zf), grad(baa, xf - 1, yf, zf), u);
		x2 = lerp(grad(aba, xf, yf - 1, zf), grad(bba, xf - 1, yf - 1, zf), u);
		y1 = lerp(x1, x2, v);

		x1 = lerp(grad(aab, xf, yf, zf - 1), grad(bab, xf - 1, yf, zf - 1), u);
		x2 = lerp(grad(abb, xf, yf - 1, zf - 1), grad(bbb, xf - 1, yf - 1, zf - 1), u);
		y2 = lerp(x1, x2, v);

		return (lerp(y1, y2, w) + 1) / 2;
	}

	/**
	 * Fade function. This eases coordinate values so that they will ease towards
	 * integral values. This ends up smoothing the final output. 6t^5 - 15t^4 +
	 * 10t^3
	 */
	private static double fade(double t) {
		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	/**
	 * Increments numbers and make sure that the noise still repeats.
	 */
	private static int inc(int num) {
		return (repeat > 0) ? (num + 1) % repeat : num + 1;
	}

	/**
	 * Calculate the dot product of a randomly generated gradient vector and the 8
	 * location vectors.
	 */
	private static double grad(int hash, double x, double y, double z) {
		switch (hash & 0xF) {
		case 0x0:
			return x + y;
		case 0x1:
			return -x + y;
		case 0x2:
			return x - y;
		case 0x3:
			return -x - y;
		case 0x4:
			return x + z;
		case 0x5:
			return -x + z;
		case 0x6:
			return x - z;
		case 0x7:
			return -x - z;
		case 0x8:
			return y + z;
		case 0x9:
			return -y + z;
		case 0xA:
			return y - z;
		case 0xB:
			return -y - z;
		case 0xC:
			return y + x;
		case 0xD:
			return -y + z;
		case 0xE:
			return y - x;
		case 0xF:
			return -y - z;
		default:
			return 0; // never happens
		}
	}

	/**
	 * Linear interpolate (interpolate = insert)
	 */
	private static double lerp(double a, double b, double x) {
		return a + (x * (b - a));
	}

	static {
		RAND = new Random(System.currentTimeMillis());
		P = new int[PERMUTATION.length * 2];
		for (int i = 0; i < P.length; i++) {
			P[i] = PERMUTATION[i % (MAX + 1)];
		}
	}
}