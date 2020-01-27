
public class Voter {
	//[-10, 10]
	private double economicBeliefs;
	private double socialBeliefs;
	
	public Voter()
	{
		randomizeBeliefs();
	}
	
	public Voter(double econ, double social)
	{
		if (econ >= -10 && econ <= 10)
		{
			economicBeliefs = econ;
		}
		else if (economicBeliefs < -10)
		{
			economicBeliefs = -10;
		}
		else
		{
			economicBeliefs = 10;
		}
		if (social >= -10 && social <= 10)
		{
			socialBeliefs = social;
		}
		else if (social < -10)
		{
			socialBeliefs = -10;
		}
		else
		{
			socialBeliefs = 10;
		}
	}
	
	public double getEconomicBeliefs()
	{
		return economicBeliefs;
	}
	
	public double getSocialBeliefs()
	{
		return socialBeliefs;
	}
	
	private void randomizeBeliefs()
	{
		economicBeliefs = (Math.random() * 20) - 10;
		socialBeliefs = (Math.random() * 20) - 10;
	}
	
	public double findDistance(Candidate c)
	{
		double difX = c.getEconomicPlatform() - economicBeliefs;
		double difY = c.getSocialPlatform() - socialBeliefs;
		double sum = Math.pow((difX), 2) + Math.pow((difY), 2);
		return Math.sqrt(sum);
	}
	
	public String toString()
	{
		return "Economic Beliefs: " + economicBeliefs + "\nSocial Beliefs: " + socialBeliefs;
	}
}
