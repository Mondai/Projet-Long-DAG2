package solver.commun;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A Java implementation of the Numerical Recipies random number generator
 * source :
 * http://www.javamex.com/tutorials/random_numbers/numerical_recipes.shtml
 * 
 * 
 */
public class HighQualityRandom extends Random {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Lock l = new ReentrantLock();
	private long u;
	private long v = 4101842887655102017L;
	private long w = 1;

	public HighQualityRandom() {
		this(System.nanoTime());
	}

	public HighQualityRandom(long seed) {
		l.lock();
		u = seed ^ v;
		nextLong();
		v = u;
		nextLong();
		w = v;
		nextLong();
		l.unlock();
	}

	public long nextLong() {
		l.lock();
		try {
			u = u * 2862933555777941757L + 7046029254386353087L;
			v ^= v >>> 17;
			v ^= v << 31;
			v ^= v >>> 8;
			w = 4294957665L * (w & 0xffffffff) + (w >>> 32);
			long x = u ^ (u << 21);
			x ^= x >>> 35;
			x ^= x << 4;
			long ret = (x + v) ^ w;
			return ret;
		} finally {
			l.unlock();
		}
	}

	protected int next(int bits) {
		return (int) (nextLong() >>> (64 - bits));
	}
	
	public int nextInt(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be positive");

		if ((n & -n) == n)  // i.e., n is a power of 2
			return (int)((n * (long)next(31)) >> 31);

		int bits, val;
		do {
			bits = next(31);
			val = bits % n;
		} while (bits - val + (n-1) < 0);
		return val;
	}

}
