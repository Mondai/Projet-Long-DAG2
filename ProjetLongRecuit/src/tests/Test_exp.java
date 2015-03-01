package tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Test des fonctions approchees d'exponentielle

public class Test_exp {

	public static double exp1(double x) {
		x = 1.0 + x / 256.0;
		x *= x;	x *= x;	x *= x;	x *= x;
		x *= x;	x *= x;	x *= x;	x *= x;
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

		double[] tab = {0, 0.00001, 0.0001, 0.001, 0.01, 0.1, 1, 10, 100, 1000 };

		// transforme les elements de tab en negatif
		for (int i = 0; i < tab.length; i++)
			tab[i] = -tab[i];

		Double[] x = new Double[tab.length * 9];
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < 9; j++) {
				x[i * 9 + j] = tab[i] * (j + 1);
			}
		}
		
		/*
		// renverser l'ordre de x si besoin
		List<Double> l = Arrays.asList(x);
		Collections.reverse(l);
		x = (Double[]) l.toArray();
		*/
		
		double[] erreursExp1 = new double[x.length];
		double[] erreursExp2 = new double[x.length];
		double[] erreursExpf = new double[x.length];

		// calcul erreursExp
		for (int i = 0; i < x.length; i++) {
			double temp1 = 0;
			double temp2 = 0;
			double tempf = 0;
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
				temp1 += Math.abs(Math.exp(val) - exp1(val)) / Math.exp(val);
				temp2 += Math.abs(Math.exp(val) - exp2(val)) / Math.exp(val);
				tempf += Math.abs(Math.exp(val) - expf(val)) / Math.exp(val);
			}

			erreursExp1[i] = temp1 ;
			erreursExp2[i] = temp2 ;
			erreursExpf[i] = tempf ;
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
		pw.close();
		
	}

}
