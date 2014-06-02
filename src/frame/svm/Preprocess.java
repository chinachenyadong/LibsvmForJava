package frame.svm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Preprocess
{

	/**
	 * convert sentence of nodes to feture representation 
	 * @param nodeList
	 * @return feature representation
	 */
	public static String selectFeature(ArrayList<Node> nodeList) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nodeList.size(); ++i) 
		{
			Node node = nodeList.get(i);
			if (node.isTarget == true) 
			{
				sb.append("1");
			} 
			else
			{
				sb.append("-1");
			}
			// add lemma, pos
			sb.append(" " + node.lemma + " " + node.pos);
			
			// add context lemma , size 2
//			for (int j = i-2; j <= i+2; ++j) 
//			{
//				if (i == j) 
//				{
//					continue;
//				}
//				
//				int index = j - i;
//				if (j < 0 || j >= nodeList.size()) 
//				{
//					sb.append(" lemma(" + index + ")/0");
//					continue;
//				}
//				else 
//				{
//					node contextnode = nodeList.get(j);
//					sb.append(" lemma(" + index + ")/" + contextnode.lemma);
//				}
//			}
			
			// add pos, size 1
			for (int j = i-1; j <= i+1; ++j) 
			{
				if (i == j) 
				{
					continue;
				}
				
				int index = j - i;
				if (j < 0 || j >= nodeList.size()) 
				{
					sb.append(" pos(" + index + ")/0");
					continue;
				}
				else 
				{
					Node contextnode = nodeList.get(j);
					sb.append(" pos(" + index + ")/" + contextnode.pos);
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * change raw file to node of feature represent
	 * @param inputFile
	 * @param outputFile
	 * @param out
	 */
	public static void preprocess(String inputFile, String outputFile, PrintStream out) throws IOException
	{
		out.println("change raw file to node of feature represent...");
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		FileWriter fw = new FileWriter(outputFile);
		String line = null;
		ArrayList<Node> nodeList = new ArrayList<Node>();
		while ((line = br.readLine()) != null)
		{
			if (line.startsWith("Sen.No."))
			{
				nodeList = new ArrayList<Node>();
				continue;
			}
			else if (line.equals(""))
			{
				// sentence to node feature representation
				String res = selectFeature(nodeList);
				fw.write(res);
				continue;
			}
			
			String[] strs = line.split(" \\|\\| ");
			String isPunt = strs[1].replaceAll("[\\pP‘’“”]", "");
			if (isPunt.equals(""))
			{
				continue;
			}
			
			// add label
			Node node = new Node();
			if (strs[strs.length-1].equals("NULL")) 
			{
				node.setTarget(false);
			}
			else
			{
				node.setTarget(true);
			}
			//add feature: pos, lemma
			String pos = strs[2];
			String lemma = strs[3];
			node.setLemma(lemma);
			node.setPos(pos);
			nodeList.add(node);
		}
		br.close();
		fw.close();
	}
	
	/**
	 * generator lexicon from trainInputFile
	 * @param trainInputFile
	 * @param out
	 */
	public static HashMap<String, Integer> lexicon(String trainInputFile, PrintStream out) throws IOException
	{
		out.println("generate lexicon...");

		HashMap<String, Integer> lexiconMap = new HashMap<String, Integer>();
		int index = 0;
		BufferedReader br = new BufferedReader(new FileReader(trainInputFile));
		String line = null;
		while ((line = br.readLine()) != null)
		{			
			String[] strs = line.split(" ");
			for (int i = 1; i < strs.length; ++i) {
				if (lexiconMap.containsKey(strs[i]) == false)
				{
					lexiconMap.put(strs[i], ++index);
				}
			}
		}
		br.close();
		
		return lexiconMap;
	}

	/**
	 * change to libsvm format
	 * @param inputFile
	 * @param outputFile
	 * @param lexicon
	 * @param out
	 */
	public static void svmFormat(String inputFile, String outputFile, HashMap<String, Integer> lexicon, PrintStream out) throws IOException
	{
		out.println("begin formating...");
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		String line = null;
		FileWriter fw = new FileWriter(outputFile);
		while ((line = br.readLine()) != null)
		{			
			String[] strs = line.split(" ");
			fw.write(strs[0]);
			for (int i = 1; i < strs.length; ++i) {
			if (lexicon.containsKey(strs[i]) == true)
				{
					fw.write(" " + lexicon.get(strs[i]) + ":1");
				}
			}
			fw.write("\n");
		}
		br.close();
		fw.close();
	}

}
