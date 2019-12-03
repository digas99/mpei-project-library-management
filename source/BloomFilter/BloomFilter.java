import static java.lang.System.*;

public class BloomFilter 
{
	private int set[];
	private int numHashs;
	
	public BloomFilter(int N, int numHashs) 
	{
		set = new int[optimalSize(N)];
		this.numHashs = numHashs;
	}
	
	public void insert(String elem) 
	{
		String str = elem;
		
		for(int i = 1; i <= numHashs; i++) 
		{
			str += i;
			set[hash(str, numHashs)]++;
		}
	}
		
	public boolean isMember(String elem) 
	{
		String str = elem;
		boolean member = true;

		for (int i=1; i <= numHashs; i++) 
		{
			str += i;
			if(set[hash(str, numHashs)] == 0) 
			{
				member = false;
				break;
			}
		}
		return member;
	}
	
	public int count(String elem) 
	{
		int min = Integer.MAX_VALUE;
		
		for (int i=1; i <= numHashs; i++) 
		{
			elem += i;
			int h = hash(elem, numHashs);
			if (set[h] < min) 
			{
				min = set[h];
			}
		}
		
		return min;
	}
	
	private int hash(String elem, int humHashs) 
	{
		int hash = Math.abs(elem.hashCode());
		return Math.abs(hash % set.length);
	}

	private int optimalSize(int m) 
	{  
		return (int) Math.ceil(((((m * Math.log(0.0005)) / Math.log(1 / Math.pow(2, Math.log(2))))))); 
	}
}