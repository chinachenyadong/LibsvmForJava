package frame.svm;

import java.io.*;
import java.util.*;

import libsvm.service.svm_predict;
import libsvm.service.svm_train;


/**
 * Libsvm for java.  A test for Target Identification. only use lemma feature.
 * @author chenyadong
 */
public class Main
{
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
		
		String trainRawFile = "./corpus/train.txt";
		String testRawFile = "./corpus/test.txt";
		String trainInputFile = "./corpus/train.input";
		String testInputFile = "./corpus/test.input";
		String trainSvmFile = "./trainfile/train.txt";
		String testSvmFile = "./trainfile/test.txt";
		
		String modelFile = "./trainfile/model.txt";
		String outSvmFile = "./trainfile/out.txt";
		
		// preprocess
//		Preprocess.preprocess(trainRawFile, trainInputFile, out);
//		Preprocess.preprocess(testRawFile, testInputFile, out);
//		HashMap<String, Integer> lexicon = Preprocess.lexicon(trainInputFile, out);
//		Preprocess.svmFormat(trainInputFile, trainSvmFile, lexicon, out);
//		Preprocess.svmFormat(testInputFile, testSvmFile, lexicon, out);
		
		// svm classify
		SvmClassify.svmClassify(trainSvmFile, testSvmFile, modelFile, outSvmFile ,out);
		precision(testSvmFile, outSvmFile, out);
		
		out.println("end");
		System.exit(0);
	}

}
