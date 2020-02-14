package voting;

public class Candidate {
	private double economicPlatform;
	private double socialPlatform;
	private int votes;
	private String name;
	
	public Candidate(String n)
	{
		randomizePlatform();
		votes = 0;
		name = n;
	}
	
	public Candidate(String n, double econ, double social)
	{
		name = n;
		if (econ >= -10 && econ <= 10)
		{
			economicPlatform = econ;
		}
		else
		{
			economicPlatform = 0;
		}
		if (social >= -10 && social <= 10)
		{
			socialPlatform = social;
		}
		else
		{
			socialPlatform = 0;
		}
		votes = 0;
	}

	public boolean equals(Candidate other)
	{
		return this.name.equals(other.name) && this.socialPlatform == other.socialPlatform && this.economicPlatform == other.economicPlatform;
	}

	public void addVotes(int toAdd)
	{
		votes += toAdd;
	}
	
	public int getNumVotes()
	{
		return votes;
	}
	
	public double getEconomicPlatform()
	{
		return economicPlatform;
	}
	
	public double getSocialPlatform()
	{
		return socialPlatform;
	}
	
	private void randomizePlatform()
	{
		economicPlatform = (Math.random() * 20) - 10;
		socialPlatform = (Math.random() * 20) - 10;
	}
	
	public double extremity()
	{
		return Math.sqrt(Math.pow((socialPlatform), 2) + Math.pow((economicPlatform), 2));
	}
	
	public void setVotes(int v)
	{
		votes = v;
	}
	
	public String toString()
	{
		return name;
	}
}
