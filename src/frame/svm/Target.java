package frame.svm;

import java.util.*;

/**
 * This class represent target with features
 * @author chenyadong
 */
public class Target
{
	// word features
	String lemma;
	String pos;
	
	// context features
	ArrayList<String> contextLemmas;
	ArrayList<String> contextPos;
	ArrayList<String> contextNers;
	
	// dependency features
	int depNum;
	ArrayList<String> childDeps;
	ArrayList<String> fatherDeps;
	ArrayList<String> deps;
	
	// sematic features
	String ner;
	int topic;
	
	boolean isTarget; 
	
	public ArrayList<String> getContextPos()
	{
		return contextPos;
	}

	public void setContextPos(ArrayList<String> contextPos)
	{
		this.contextPos = contextPos;
	}

	public ArrayList<String> getContextNers()
	{
		return contextNers;
	}

	public void setContextNers(ArrayList<String> contextNers)
	{
		this.contextNers = contextNers;
	}

	public int getDepNum()
	{
		return depNum;
	}

	public void setDepNum(int depNum)
	{
		this.depNum = depNum;
	}

	public ArrayList<String> getChildDeps()
	{
		return childDeps;
	}

	public void setChildDeps(ArrayList<String> childDeps)
	{
		this.childDeps = childDeps;
	}

	public ArrayList<String> getFatherDeps()
	{
		return fatherDeps;
	}

	public void setFatherDeps(ArrayList<String> fatherDeps)
	{
		this.fatherDeps = fatherDeps;
	}

	public ArrayList<String> getDeps()
	{
		return deps;
	}

	public void setDeps(ArrayList<String> deps)
	{
		this.deps = deps;
	}

	public String getNer()
	{
		return ner;
	}

	public void setNer(String ner)
	{
		this.ner = ner;
	}

	public int getTopic()
	{
		return topic;
	}

	public void setTopic(int topic)
	{
		this.topic = topic;
	}

	
	public ArrayList<String> getContextLemmas()
	{
		return contextLemmas;
	}

	public void setContextLemmas(ArrayList<String> contextLemmas)
	{
		this.contextLemmas = contextLemmas;
	}

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
