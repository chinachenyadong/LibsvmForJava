package frame.svm;

import java.util.*;

/**
 * This class represent raw one line
 * @author chenyadong
 */
public class Node
{
	String lemma;
	String pos;
	boolean isTarget; 
	

	public boolean isTarget()
	{
		return isTarget;
	}

	public void setTarget(boolean isTarget)
	{
		this.isTarget = isTarget;
	}

	public String getLemma()
	{
		return lemma;
	}

	public void setLemma(String lemma)
	{
		this.lemma = lemma;
	}

	public String getPos()
	{
		return pos;
	}

	public void setPos(String pos)
	{
		this.pos = pos;
	}

}
