package main.framenet;

import java.io.*;
import java.util.*;

import service.svm_predict;
import service.svm_train;

/**
 * Libsvm for java.  A test for Target Identification. only use lemma feature.
 * @author chenyadong
 */
public class Main
{

	/**
	 * generator lexicon from train file
	 * @param out
	 */
	public static HashMap<String, Integer> lexicon(PrintStream out) throws IOException
	{
		out.println("generate lexicon...");

		HashMap<String, Integer> lexiconMap = new HashMap<String, Integer>();
		int index = 0;
		BufferedReader br = new BufferedReader(new FileReader("corpus/train.txt"));
		String line = null;
		while ((line = br.readLine()) != null)
		{
			if (line.startsWith("Sen.No."))
			{
				// fileName = line.substring(line.indexOf("|| ") + 3);
				continue;
			}
			else if (line.equals(""))
			{
				continue;
			}
			
			String[] strs = line.split(" \\|\\| ");
			String isPunt = strs[1].replaceAll("[\\pP‘’“”]", "");
			if (isPunt.equals(""))
			{
				continue;
			}
			
			String lemma = strs[3];
			if (lexiconMap.containsKey(lemma) == false)
			{
				lexiconMap.put(lemma, ++index);
			}
		}
		br.close();
		out.println("done\n");

		return lexiconMap;
	}

	/**
	 * change to libsvm format
	 * @param inputFile
	 * @param outputFile
	 * @param lexicon
	 * @param out
	 */
	public static void format(String inputFile, String outputFile, HashMap<String, Integer> lexicon, PrintStream out) throws IOException
	{
		out.println("begin formating...");
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		String line = null;
		FileWriter fw = new FileWriter(outputFile);
		while ((line = br.readLine()) != null)
		{
			if (line.startsWith("Sen.No."))
			{
				// fileName = line.substring(line.indexOf("|| ") + 3);
				continue;
			}
			else if (line.equals(""))
			{
				continue;
			}
			
			String[] strs = line.split(" \\|\\| ");
			String isPunt = strs[1].replaceAll("[\\pP‘’“”]", "");
			if (isPunt.equals(""))
			{
				continue;
			}

			String isTarget = strs[strs.length - 1];
			if (isTarget.equals("NULL"))
			{
				fw.write("-1");
			} 
			else 
			{
				fw.write("1");
			}

			String lemma = strs[3];
			if (lexicon.containsKey(lemma) == true)
			{
				fw.write(" " + lexicon.get(lemma) + ":1");
			}
			fw.write("\n");
		}
		br.close();
		fw.close();
		out.println("done\n");
	}

	/**
	 * svm train and predict
	 * @param out
	 */
	public static void svmClassify(PrintStream out) throws IOException
	{
		// path : train file, model file
		String[] arg = { "-t", "0", "trainfile/train.txt", "trainfile/model.txt" };
		String[] parg = { "trainfile/test.txt", "trainfile/model.txt", "trainfile/out.txt" };

		
		long startTime = System.currentTimeMillis();
		out.println("begin training...");
		
		svm_train.main(arg);
		
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		out.println("Total training time: " + totalTime);
		out.println("done\n");
		
		out.println("begin testing...");
		svm_predict.main(parg);
		out.println("done\n");
	}

	/**
	 * precision
	 * @param textFile
	 * @param outFile
	 * @param out
	 */
	public static void precision(String textFile, String outFile, PrintStream out) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(outFile));
		String line = null;
		ArrayList<Integer> list = new ArrayList<Integer>();
		while ( (line = br.readLine()) != null ) {
			int index = (int) (Double.parseDouble(line));
			list.add(index);
		}
		br.close();

		br = new BufferedReader(new FileReader(textFile));
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		while ( (line = br.readLine()) != null ) {
			String[] strs = line.split(" ");
			list2.add(Integer.parseInt(strs[0]));
		}
		br.close();

		int cnt = 0, cnt2 = 0, num2 =0, num = 0, p = 0, q = 0;
		for (int i = 0; i < list.size(); ++i) {
			int a = list.get(i);
			int b = list2.get(i);
			if (a == 1) { // self 1
				++cnt;
			}
			if (a == -1) {
				++cnt2;
			}
			if (a == 1 && b == 1) {
				++p;
			}
			if (b == 1) { // b must be right 1
				++num;
			}
			if (b == -1) {
				++num2;
			}
			if (b == -1 && a == -1) {
				++q;
			}
		}
		double P = (double)p/cnt;
		double R = (double)p/num;
		out.println(p + " : " + (cnt-p));
		out.println((num-p) + " : " + (num2-cnt+p));
		out.println("P : " + P);
		out.println("R : " + R);
		out.println("F : " + 2*P*R/(P+R));
	}
	
	public static void main(String[] args) throws IOException
	{
//		PrintStream out = new PrintStream("log.txt");
		PrintStream out = System.out;
		
		HashMap<String, Integer> lexicon = lexicon(out);
		format("./corpus/train.txt", "trainfile/train.txt", lexicon, out);
		format("./corpus/test.txt", "trainfile/test.txt", lexicon, out);
		svmClassify(out);
		precision("trainfile/test.txt", "trainfile/out.txt", out);
		out.println("end");
		System.exit(0);
	}

}
