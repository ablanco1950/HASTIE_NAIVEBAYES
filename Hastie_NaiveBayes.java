
//
// Author Alfonso Blanco García, Julio 2021
//
// Implementación de ADABOOST siguiendo el pseudocodigo de la figura 18.34 
// usa el algoritmo empleado en Susy_Weighted
// 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Math;

public class Hastie_NaiveBayes {
	public static void main(String args[]) {
		//
		// Are received as parameters: the name of the training file:C:\Hastie10_2.csv
		// the name of the test file:C:\Hastie10_2.csv
		// Susy.csv,and the margin of records of training and test file
		//
		String fichero = args[0];
		String ficheroTest = args[1];
		Double TrainingStart = Double.parseDouble(args[2]);
		Double TrainingEnd = Double.parseDouble(args[3]);
		Double TestStart = Double.parseDouble(args[4]);
		Double TestEnd = Double.parseDouble(args[5]);
		String ficheroOutTraining = args[6];
		String ficheroOutTest = args[7];
		int N = 0;
		long Inicio = System.nanoTime();

		Double Conta = 0.0;
		int Cont = -1;
		int NumClases = 2;

		Double ContLe = 0.0;

		int NumCampos = 11;
		Double[] Max = new Double[NumCampos];
		Double[] Min = new Double[NumCampos];
		Double[] ContClases = new Double[NumClases];

		Double Maximo = 0.0;

		int TopeMemoria = 304;

		Double[][][] Tabvotos = new Double[NumClases][NumCampos][TopeMemoria];

		/////////////////////////////////////////////////////
		// The maximum and minimum values of each field are calculated
		////////////////////////////////////////////////////////////////// ________________________________________________________________________________________

		try {

			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);

			String linea;
			Conta = 0.0;
			Cont = -1;

			ContLe = 0.0;

			for (int z = 1; z < NumCampos; z++) {
				Max[z] = -9.9999E+16;
				Min[z] = 9.9999E+16;
			}
			while ((linea = br.readLine()) != null) {

				Conta++;
				if ((Conta >= TrainingStart) && (Conta <= TrainingEnd)) {
					ContLe++;
					Cont++;
					N++;
					String lineadelTrain[] = linea.split(";");

					for (int z = 1; z < NumCampos; z++) {
						if (Double.parseDouble(lineadelTrain[z]) > Max[z]) {
							Max[z] = Double.parseDouble(lineadelTrain[z]);
						}
						if (Double.parseDouble(lineadelTrain[z]) < Min[z]) {
							Min[z] = Double.parseDouble(lineadelTrain[z]);
						}
					} // end for

				} // end if conta

			} // end while
		} // end try
		catch (Exception e) {
			System.out.println("Exception when writing the table of minimum and maximum values " + ": " + e);
		}

		// ****************************************************************************

		System.out.println("Start Hastie_NaiveBayes.java");

		for (int i = 0; i < NumClases; i++) {
			for (int j = 0; j < NumCampos; j++) {
				for (int l = 0; l < TopeMemoria; l++) {
					Tabvotos[i][j][l] = 0.0;

				}
			}
		}
		for (int i = 0; i < NumClases; i++) {
			ContClases[i] = 0.0;
		}
		Conta = 0.0;
		Cont = -1;

		ContLe = 0.0;

		// ***************************************************************************************
		// The same steps are followed as in the previous SUSY_WEIGHTED project:
		// according to the values of each field, they are associated with indices
		// with which the frequencies of each index, field and class are established.
		// According to these frequencies, in a later step, the probabilities are
		// established
		// and by means of the Naive Bay method, the probability of relevance to each
		// class is established.

		Maximo = 0.0;

		try {
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);

			String linea;
			//
			// Reading the training file
			//
			while ((linea = br.readLine()) != null) {

				Conta++;
				if ((Conta >= TrainingStart) && (Conta <= TrainingEnd)) {
					ContLe++;
					Cont++;

					String lineadelTrain[] = linea.split(";");

					int Clase = (int) Double.parseDouble(lineadelTrain[0]);

					if (Clase < 0)
						Clase = 0;

					ContClases[Clase]++;

					for (int z = 1; z < NumCampos; z++) {

						Double MaxIndice = TopeMemoria - 2.0;
						int indice = 0;
						Double ValorTrain = 0.0;

						ValorTrain = Double.parseDouble(lineadelTrain[z]);

						//
						// The memory index in which the frequencies of each field will be stored is
						// calculated based on the memory limit set to each field
						// and the maximum and minimum values of each field
						//

						Maximo = Max[z] - Min[z];

						ValorTrain = ValorTrain - Min[z];

						// The Memory Top field adjusts the margin for the sample values in
						// each field that are translated to an adjusted index.
						switch (z) {

						case 1:

							TopeMemoria = 104;
							break;
						case 2:
							
							TopeMemoria = 104;
							break;
						case 3:
							
							TopeMemoria = 74;
							break;
						case 4:
							
							TopeMemoria = 104;
							break;
						case 5:
							
							TopeMemoria = 54;
							break;
						case 6:
							
							TopeMemoria = 154;
							break;
						case 7:
							
							TopeMemoria = 104;
							break;
						case 8:
							
							TopeMemoria = 104;
							break;
						case 9:
							
							TopeMemoria = 104;
							break;
						case 10:
							
							TopeMemoria = 104;
							break;
						default:
							
							TopeMemoria = 104;
						}

						indice = (int) (((TopeMemoria - 2.0) * ValorTrain) / Maximo);

						if ((indice > (TopeMemoria - 2)) || (indice < 0)) {
							System.out.println(" index overflowed =" + indice + " in the field=" + z);
							indice = TopeMemoria - 2;
							System.out.println(" It is corrected by putting index =" + indice + " in the field=" + z);
						}
						//
						

						Double valor = 0.0;

						valor = Tabvotos[Clase][z][TopeMemoria - 1];
						valor++;
						Tabvotos[Clase][z][TopeMemoria - 1] = valor;
						valor = Tabvotos[Clase][z][indice];

						valor = valor + 1.0;
						Tabvotos[Clase][z][indice] = valor;

					} // end for z

					// --------------------------------------
				} // end if conta
			} // end while
			fr.close();
			System.out.println("Readed records " + fichero + ": " + ContLe);
		} // fin try
		catch (Exception e) {
			System.out.println("Exception reading file frecuencias " + fichero + ": " + e);
		}
		// ***************************************************************************
		// HITS AND ERRORS OF TRAINING
		// *************************************************************************************
		Double TotAciertos = 0.0;
		Double TotFallos = 0.0;
		Double TotValoresACero = 0.0;
		Conta = 0.0;
		ContLe = 0.0;
		Cont = -1;
	

		try {
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);
			FileWriter Salida = null;
			PrintWriter pw = null;
			Salida = new FileWriter(ficheroOutTraining);
			pw = new PrintWriter(Salida);
			String linea;

			while (((linea = br.readLine()) != null)) {
				Conta++;

				if ((Conta >= TrainingStart) && (Conta <= TrainingEnd)) {
					ContLe++;
					Cont++;
					String lineadelTraining[] = linea.split(";");

					Double[] P_indice_clase = new Double[NumClases];
					for (int i = 0; i < NumClases; i++)
						P_indice_clase[i] = 0.0;
					Double[] Producto_P_indice_clase = new Double[NumClases];
					for (int i = 0; i < NumClases; i++)
						Producto_P_indice_clase[i] = 1.0;

					int Clase = (int) Double.parseDouble(lineadelTraining[0]);
					if (Clase < 0)
						Clase = 0;
					

					for (int z = 1; z < NumCampos; z++) {
						Double MaxIndice = TopeMemoria - 2.0;
						int indice = 0;

						Double ValorTrain = Double.parseDouble(lineadelTraining[z]);

						//
						// The memory index in which the frequencies of each field will be stored is
						// calculated based on the memory limit set to each field and the maximum 
						// and minimum values of each field
						//

						ValorTrain = ValorTrain - Min[z];
						Maximo = Max[z] - Min[z];

						switch (z) {

						case 1:
							
							TopeMemoria = 104;
							break;
						case 2:
							
							TopeMemoria = 104;
							break;
						case 3:
							TopeMemoria = 74;
							break;
						case 4:
							
							TopeMemoria = 104;
							break;
						case 5:
							
							TopeMemoria = 54;
							break;
						case 6:
							
							TopeMemoria = 154;
							break;
						case 7:
							
							TopeMemoria = 104;
							break;
						case 8:
							
							TopeMemoria = 104;
							break;
						case 9:
							
							TopeMemoria = 104;
							break;
						case 10:
							
							TopeMemoria = 104;
							break;
						default:
							
							TopeMemoria = 204;
						}
						indice = (int) (((TopeMemoria - 2.0) * ValorTrain) / Maximo);
						if ((indice > (TopeMemoria - 2)) || (indice < 0)) {
							System.out.println(" Index overflowed==" + indice + " in the field=" + z);
							indice = TopeMemoria - 1;
						}
						Double[] valor = new Double[NumClases];
						for (int i = 0; i < NumClases; i++)
							valor[i] = Tabvotos[i][z][indice];

						//
						// The probabilities of each class are calculated by the Naive Bayes method,
						// given the independence of each of the  fields 
						//

						//
						// The probability of each index given a class is obtained
						//
						for (int i = 0; i < NumClases; i++) {
							if (ContClases[i] != 0.0)
								P_indice_clase[i] = valor[i] / ContClases[i];
							else
								P_indice_clase[i] = 0.0;
						}

						
						// The probability of each index of each field, given a class condition, is
						// multiplied by the probability of each of the indexes
						// of previous fields in the record
						//
						
						for (int i = 0; i < NumClases; i++)
							Producto_P_indice_clase[i] = Producto_P_indice_clase[i] * P_indice_clase[i];

					} // end for int z=0

					//
					// The probability of the class is calculated, which then is multiplied by the
					// result of the products of the probabilities of the indices of each field,
					// assuming belonging to one of the two classes
					//
					
					Double TotalContClases = 0.0;
					for (int i = 0; i < NumClases; i++)
						TotalContClases = TotalContClases + ContClases[i];

					for (int i = 0; i < NumClases; i++)
						Producto_P_indice_clase[i] = Producto_P_indice_clase[i] * ContClases[i] / TotalContClases;

					Double Total_Producto_P_indice_clase = 0.0;
					for (int i = 0; i < NumClases; i++)
						Total_Producto_P_indice_clase = Total_Producto_P_indice_clase + Producto_P_indice_clase[i];

					Double[] TotValor = new Double[NumClases];
					for (int i = 0; i < NumClases; i++)
						TotValor[i] = Producto_P_indice_clase[i] / Total_Producto_P_indice_clase;

					// The class with Maximum probability is established.
					
					Double MaxClase = 0.0;
					Double MaxPro = 0.0;

					for (int i = 0; i < NumClases; i++) {

						if (TotValor[i] > MaxPro) {

							MaxPro = TotValor[i];
							MaxClase = (double) i;
						}
					}
					
					if (Clase == MaxClase) {
						pw.println(linea + "0");
						TotAciertos++;
					} else {

						
						pw.println(linea + "1");
						TotFallos++;
					}

					

				} // end of if conta
			} // end while
			System.out.println("");
			System.out.println(" Total hits with the Training file while learning =  " + TotAciertos);
			System.out.println(" Total failures = " + TotFallos);

			System.out.println(" Assigned without foundation = = " + TotValoresACero);
			

			fr.close();
			pw.close();
			Salida.close();
			System.out.println("Records read Training file" + fichero + ": " + ContLe);
			Double FinalParcial = (System.nanoTime() - Inicio) / 1000000000.0;
			System.out.println("Total run time= " + FinalParcial);
		} catch (Exception e) {
			System.out.println("Exception reading file " + fichero + ": " + e);
		}
		// ***********************************************************************************
		// HITS AND ERRORS IN THE FILE TEST 
		//
		TotAciertos = 0.0;
		TotFallos = 0.0;
		TotValoresACero = 0.0;
		Conta = 0.0;
		ContLe = 0.0;
		Cont = -1;
	

		try {
			FileReader fr = new FileReader(ficheroTest);
			BufferedReader br = new BufferedReader(fr);
			FileWriter Salida = null;
			PrintWriter pw = null;
			Salida = new FileWriter(ficheroOutTest);
			pw = new PrintWriter(Salida);
			String linea;
			
			while (((linea = br.readLine()) != null)) {
				Conta++;

				if ((Conta >= TestStart) && (Conta <= TestEnd)) {
					ContLe++;
					Cont++;
					String lineadelTraining[] = linea.split(";");
					

					
					Double[] P_indice_clase = new Double[NumClases];
					for (int i = 0; i < NumClases; i++)
						P_indice_clase[i] = 0.0;
					Double[] Producto_P_indice_clase = new Double[NumClases];
					for (int i = 0; i < NumClases; i++)
						Producto_P_indice_clase[i] = 1.0;

					int Clase = (int) Double.parseDouble(lineadelTraining[0]);
					if (Clase < 0)
						Clase = 0;
					
					for (int z = 1; z < NumCampos; z++) {
						Double MaxIndice = TopeMemoria - 2.0;
						int indice = 0;

						Double ValorTrain = Double.parseDouble(lineadelTraining[z]);

						//
						// The memory index in which the frequencies of each field will be stored is
						// calculated based on the memory limit set to each field and the maximum and
						// minimum values of each field
						//

						
							ValorTrain = ValorTrain - Min[z];
							Maximo = Max[z] - Min[z];
						
						
						switch (z) {

						case 1:
							
							TopeMemoria = 104;
							break;
						case 2:
							
							TopeMemoria = 104;
							break;
						case 3:
							
							TopeMemoria = 74;
							break;
						case 4:
							
							TopeMemoria = 104;
							break;
						case 5:
							
							TopeMemoria = 54;
							break;
						case 6:
							
							TopeMemoria = 154;
							break;
						case 7:
							
							TopeMemoria = 104;
							break;
						case 8:
							
							TopeMemoria = 104;
							break;
						case 9:
							
							TopeMemoria = 104;
							break;
						case 10:
							
							TopeMemoria = 104;
							break;
						default:
							
							TopeMemoria = 304;
						}
						indice = (int) (((TopeMemoria - 2.0) * ValorTrain) / Maximo);
						if ((indice > (TopeMemoria - 2)) || (indice < 0)) {
							System.out.println(" Index overflowed==" + indice + " in the field=" + z);
							indice = TopeMemoria - 1;
						}
						Double[] valor = new Double[NumClases];
						for (int i = 0; i < NumClases; i++)
						{	valor[i] = Tabvotos[i][z][indice];
						    
						}

						//
						// The probabilities of each class are calculated by the Naive Bayes method,
						// given the independence of each  fields 
						//

						//
						// The probability of each index given a class is obtained
						//
						for (int i = 0; i < NumClases; i++) {
							if (ContClases[i] != 0.0)
								P_indice_clase[i] = valor[i] / ContClases[i];
							else
								P_indice_clase[i] = 0.0;
							
						}

						
						// The probability of each index of each field, given a class condition, is
						// multiplied by the probability of each of the indexes
						// of previous fields in the record
						//
						
						for (int i = 0; i < NumClases; i++)
							Producto_P_indice_clase[i] = Producto_P_indice_clase[i] * P_indice_clase[i];

					} // end for int z=0

					//
					// The probability of the class is calculated, which then is multiplied by the
					// result of the products of the probabilities of the indices of each field,
					// assuming belonging to one of the two classes
					//
					
					Double TotalContClases = 0.0;
					for (int i = 0; i < NumClases; i++)
						TotalContClases = TotalContClases + ContClases[i];

					for (int i = 0; i < NumClases; i++)
						Producto_P_indice_clase[i] = Producto_P_indice_clase[i] * ContClases[i] / TotalContClases;

					Double Total_Producto_P_indice_clase = 0.0;
					for (int i = 0; i < NumClases; i++)
						Total_Producto_P_indice_clase = Total_Producto_P_indice_clase + Producto_P_indice_clase[i];

					Double[] TotValor = new Double[NumClases];
					for (int i = 0; i < NumClases; i++)
						TotValor[i] = Producto_P_indice_clase[i] / Total_Producto_P_indice_clase;

					// The class with Maximum probability is established.
					
					Double MaxClase = 0.0;
					Double MaxPro = 0.0;

					for (int i = 0; i < NumClases; i++) {
						
						if (TotValor[i] > MaxPro) {

							MaxPro = TotValor[i];
							MaxClase = (double) i;
						}
					}
					
					// The concordances and discrepancies with the input test file are counted,
					// 
					// In the case of using a test file that does not have assigned classes,
					// this accounting will not make sense
					if (Clase == MaxClase) {

						TotAciertos++;
					} else {

						
						TotFallos++;
					}

					//
					// In the output file C: \\ HastieTestWithClassAsigned.txt, the records of the
					// test file are recorded with the assigned classes
					//
					
					if (MaxClase == 0)
						lineadelTraining[0] = "-1.0";
					else
						lineadelTraining[0] = "1.0";
					linea = String.join(";", lineadelTraining);
					pw.println(linea);

				} // end of if conta
			} // end while

			System.out.println("");
			System.out.println(" Total hits with the TEST FILE while learning =  " + TotAciertos);
			System.out.println(" Total failures with the TEST FILE = " + TotFallos);

			System.out.println(" Assigned without foundation = = " + TotValoresACero);
			
			fr.close();
			Salida.close();
			pw.close();
			
			System.out.println("Records read TEST FILE " + fichero + ": " + ContLe);
			Double FinalParcial = (System.nanoTime() - Inicio) / 1000000000.0;
			System.out.println("Total run time= " + FinalParcial);
		} catch (Exception e) {
			System.out.println("Exception reading file " + fichero + ": " + e);
		}
	} // end of main
}
