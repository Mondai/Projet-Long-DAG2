package tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Test des fonctions approchees d'exponentiel

public class Test_exp {

	public static double exp1(double x) {
		x = 1.0 + x / 256.0;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		return x;
	}

	public static double exp2(double val) {
		final long tmp = (long) (1512775 * val + 1072632447);
		return Double.longBitsToDouble(tmp << 32);
	}

	public static double expf(double val) {
		if (val >= 0)
			return 1;
		if (val > -2.5)
			return exp1(val);
		if (val > -700)
			return exp2(val);
		else
			return 0;
	}

	public static void main(String[] args) throws IOException {

		double[] tab = { 0.00001, 0.0001, 0.001, 0.01, 0.1, 1, 10, 100, 1000 };

		// transforme les elements de tab en negatif
		for (int i = 0; i < tab.length; i++)
			tab[i] = -tab[i];

		int N = 1000;
		double[] x = new double[tab.length * 9];
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < 9; j++) {
				x[i * 9 + j] = tab[i] * (j + 1);
			}
		}
		double[] erreursExp1 = new double[x.length];
		double[] erreursExp2 = new double[x.length];
		double[] erreursExpf = new double[x.length];

		// calcul erreursExp
		for (int i = 0; i < x.length; i++) {
			double temp1 = 0;
			double temp2 = 0;
			double tempf = 0;
			for (int j = 0; j < N; j++) {
				double val = x[i];
				
				if (Math.exp(val) == Double.POSITIVE_INFINITY) {
					if (exp1(val) < 1e150)
						temp1 += Double.POSITIVE_INFINITY;
					if (exp2(val) < 1e150)
						temp2 += Double.POSITIVE_INFINITY;
					if (expf(val) < 1e150)
						tempf += Double.POSITIVE_INFINITY;
				} else if (Math.exp(val) <= 1e-300) {
					if (exp1(val) > 1e-5)
						temp1 += Double.POSITIVE_INFINITY;
					if (exp2(val) > 1e-5)
						temp2 += Double.POSITIVE_INFINITY;
					if (expf(val) > 1e-5)
						tempf += Double.POSITIVE_INFINITY;
				} else {
					temp1 += Math.abs(Math.exp(val) - exp1(val))
							/ Math.exp(val);
					temp2 += Math.abs(Math.exp(val) - exp2(val))
							/ Math.exp(val);
					tempf += Math.abs(Math.exp(val) - expf(val))
							/ Math.exp(val);
				}
			}
			// moyenne
			erreursExp1[i] = temp1 / N;
			erreursExp2[i] = temp2 / N;
			erreursExpf[i] = tempf / N;
		}

		// calcul du temps
		int iter = 100000;
		// Math.exp
		double[] temps0 = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			long startTime = System.nanoTime();
			for (int j = 0; j < iter; j++) {
				double val = (Math.random() * 9 + 1) * x[i];
				Math.exp(val);
			}
			long endTime = System.nanoTime();
			temps0[i] = (endTime - startTime) / ((double) iter);
		}
		// exp1
		double[] temps1 = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			long startTime = System.nanoTime();
			for (int j = 0; j < iter; j++) {
				double val = (Math.random() * 9 + 1) * x[i];
				exp1(val);
			}
			long endTime = System.nanoTime();
			temps1[i] = (endTime - startTime) / ((double) iter);
		}
		// exp2
		double[] temps2 = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			long startTime = System.nanoTime();
			for (int j = 0; j < iter; j++) {
				double val = (Math.random() * 9 + 1) * x[i];
				exp2(val);
			}
			long endTime = System.nanoTime();
			temps2[i] = (endTime - startTime) / ((double) iter);
		}
		// expf
				double[] tempsf = new double[x.length];
				for (int i = 0; i < x.length; i++) {
					long startTime = System.nanoTime();
					for (int j = 0; j < iter; j++) {
						double val = (Math.random() * 9 + 1) * x[i];
						expf(val);
					}
					long endTime = System.nanoTime();
					tempsf[i] = (endTime - startTime) / ((double) iter);
				}

		// ecrire dans un fichier txt
		String nomFichier = "SortiesGraphiques/test_exp.txt";
		File file = new File(nomFichier);
		PrintWriter pw = new PrintWriter(new BufferedWriter(
				new FileWriter(file)));
		pw.print("erreursExp1 = [");
		for (int i = 0; i < erreursExp1.length; i++) {
			pw.print(erreursExp1[i] + " ");
		}
		pw.println("];");
		pw.print("erreursExp2 = [");
		for (int i = 0; i < erreursExp2.length; i++) {
			pw.print(erreursExp2[i] + " ");
		}
		pw.println("];");
		pw.print("erreursExpf = [");
		for (int i = 0; i < erreursExpf.length; i++) {
			pw.print(erreursExpf[i] + " ");
		}
		pw.println("];");
		pw.print("x = [");
		for (int i = 0; i < x.length; i++) {
			pw.print(x[i] + " ");
		}
		pw.println("];");
		pw.print("t0 = [");
		for (int i = 0; i < x.length; i++) {
			pw.print(temps0[i] + " ");
		}
		pw.println("];");
		pw.print("t1 = [");
		for (int i = 0; i < x.length; i++) {
			pw.print(temps1[i] + " ");
		}
		pw.println("];");
		pw.print("t2 = [");
		for (int i = 0; i < x.length; i++) {
			pw.print(temps2[i] + " ");
		}
		pw.println("];");
		pw.print("tf = [");
		for (int i = 0; i < x.length; i++) {
			pw.print(tempsf[i] + " ");
		}
		pw.println("];");
		pw.close();

	}

}
