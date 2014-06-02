package frame.svm;

import java.io.*;
import java.util.*;

import libsvm.service.svm_predict;
import libsvm.service.svm_train;


/**
 * Libsvm for java.  A test for Target Identification. only use lemma feature.
 * @author chenyadong
 */
public class SvmClassify
{
	/**
	 * svm train and predict
	 * @param trainFile
	 * @param testFile
	 * @param modelFile
	 * @param outFile
	 * @param out
	 */
	public static void svmClassify(String trainFile, String testFile, String modelFile, String outFile, PrintStream out) throws IOException
	{
		// path : train file, model file,  -t 0 : linear kernel
		String[] arg = { "-t", "0", trainFile, modelFile };
		String[] parg = { testFile, modelFile, outFile };

		
		long startTime = System.currentTimeMillis();
		out.println("begin training...");
		
		svm_train.main(arg);
		
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		out.println("Total training time: " + totalTime);
	
		out.println("begin testing...");
		
		svm_predict.main(parg);
	}

}
