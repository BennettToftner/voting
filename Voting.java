package voting;

import java.util.ArrayList;
import java.util.List;

public class Voting {
	
	//returns index of the largest number in the array
	private static int bigIndex(int[] arr)
	{
		int index = 0;
		int largest = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i] > largest)
			{
				largest = arr[i];
				index = i;
			}
		}
		return index;
	}

	private static int smallIndex(int[] arr)
	{
		int index = 0;
		int smallest = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i] < smallest && arr[i] >= 0)
			{
				smallest = arr[i];
				index = i;
			}
		}
		return index;
	}
	
	//move to voter class?
	public static Candidate favoriteCandidate(Voter v, List<Candidate> cList)
	{
		double minDistance = Double.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < cList.size(); i++)
		{
			double distance = v.findDistance(cList.get(i));
			if (distance < minDistance)
			{
				minDistance = distance;
				index = i;
			}
		}
		return cList.get(index);
	}
	
	public static String graphVoters(List<Voter> voters, double scale)
	{
		char[][] graph = new char[25][25];
		for (int r = 0; r < graph.length; r++)
		{
			for (int c = 0; c < graph[0].length; c++)
			{
				if (r == graph.length / 2 + 1 && c == graph[0].length / 2 + 1)
				{
					graph[r][c] = '+';
				}
				else if (r == graph.length / 2 + 1)
				{
					graph[r][c] = '-';
				}
				else if (c == graph[0].length / 2 + 1)
				{
					graph[r][c] = '|';
				}
				else
				{
					graph[r][c] = ' ';
				}
			}
		}
		for (Voter v: voters)
		{
			int econ = (int)(v.getEconomicBeliefs() * scale) + graph[0].length / 2;
			int social = (int)(v.getSocialBeliefs() * scale) + graph.length / 2;
			if (econ >= 0 && econ < graph.length && social >= 0 && social < graph.length)
			{
				graph[social][econ] = '█';
			}
		}
		String result = "";
		for (char[] r: graph)
		{
			for (char c: r)
			{
				result += c;
			}
			result += "\n";
		}
		return result;
	}

	public static List<Voter> generateRandomVoters(int numVoters)
	{
		ArrayList<Voter> voters = new ArrayList<Voter>();
		for (int i = 0; i < numVoters; i++)
		{
			voters.add(new Voter());
		}
		return voters;
	}
	
	public static List<Candidate> generateRandomCandidates(int numCands)
	{
		ArrayList<Candidate> cands = new ArrayList<Candidate>();
		for (int i = 0; i < numCands; i++)
		{
			cands.add(new Candidate("Candidate " + (i + 1)));
		}
		return cands;
	}
	
	/**
	 * Generates concentric circles of voters decreasing in amount as the circles expand.
	 * Results in a square of voters around the edge if voters go above 10 or below -10 in economic or social beliefs.
	 * 
	 * @param numVoters The total number of voters in all circles (Note: actual number will most likely be less than this number)
	 * @param reductionFactor By what factor voters decrease by as circles expand
	 * @param spacing The spacing between concentric circles
	 * @param economicCenter x coordinate of center
	 * @param socialCenter y coordinate of center
	 * @return A list of voters arranged in concentric circles
	 */
	public static List<Voter> generateVoterCircles(int numVoters, double reductionFactor, double spacing, double economicCenter, double socialCenter)
	{
		int curNum = (int)(numVoters / reductionFactor);
		double radius = spacing;
		List<Voter> voters = new ArrayList<Voter>();
		while (curNum > 0)
		{
			double degreeIncrement = 360.0 / curNum;
			double degrees = 0;
			while (degrees < 360)
			{
				voters.add(new Voter(Math.cos(degrees) * radius + economicCenter, Math.sin(degrees) * radius + socialCenter));
				degrees += degreeIncrement;
			}
			radius += spacing;
			curNum /= reductionFactor;
		}
		return voters;
	}
	
	/**
	 * Generates the ranking of a voter based on the candidates. A voter will choose candidates in order of proximity to their beliefs.
	 *  The candidate at index 0 is their favorite, and the last index is their least favorite.
	 * 
	 * @param v The voter to generate a ranking of
	 * @param cList The candidates in the election
	 * @return The ranking of the voter
	 */
	public static List<Candidate> generateRanking(Voter v, List<Candidate> cList)
	{
		List<Candidate> ranking = new ArrayList<Candidate>();
		while (cList.size() > 0)
		{
			Candidate favCandidate = favoriteCandidate(v, cList);
			ranking.add(favCandidate);
			cList.remove(favCandidate);
		}
		return ranking;
	}
	
	private static <T> List<T> copyList(List<T> l)
	{
		List<T> listCopy = new ArrayList<T>();
		for (int i = 0; i < l.size(); i++)
		{
			listCopy.add(l.get(i));
		}
		return listCopy;
	}
	
	private static String arrPrint(int[] arr)
	{
		String result = "[ ";
		for (int i = 0; i < arr.length; i++)
		{
			result += arr[i] + ", ";
		}
		result = result.substring(0, result.length() - 2);
		return result + " ]";
	}
	
	public static Candidate positiveNegative(List<Voter> vList, List<Candidate> cList)
	{
		List<List<Candidate>> rankings = new ArrayList<List<Candidate>>();
		for (Voter v: vList)
		{
			rankings.add(generateRanking(v, copyList(cList)));
		}
		int[] votes = new int[cList.size()];
		for (int i = 0; i < rankings.size(); i++)
		{
			Candidate pos = rankings.get(i).get(0);
			Candidate neg = rankings.get(i).get(rankings.get(i).size() - 1);
			votes[cList.indexOf(pos)]++;
			votes[cList.indexOf(neg)]--;
		}
		int bigIndex = bigIndex(votes);
		for (int i = 0; i < cList.size(); i++)
		{
			cList.get(i).setVotes(votes[i]);
		}
		return cList.get(bigIndex);
	}

	public static Candidate instantRunoff(List<Voter> vList, List<Candidate> cList)
	{
		List<List<Candidate>> rankings = new ArrayList<List<Candidate>>();
		for (Voter v: vList)
		{
			rankings.add(generateRanking(v, copyList(cList)));
		}
		int[] votes = new int[cList.size()];
		do {
			for (int i = 0; i < rankings.size(); i++)
			{
				//their preferred candidate
				int index = 0;
				for (int j = 0; j < rankings.get(i).size(); j++)
				{
					Candidate cur = rankings.get(i).get(j);
					if (votes[cList.indexOf(cur)] != -1)
					{
						index = j;
						break;
					}
				}
				Candidate c = rankings.get(i).get(index);
				votes[cList.indexOf(c)]++;
			}
			System.out.println(arrPrint(votes));
			if (votes[bigIndex(votes)] > (double)rankings.size() * 0.5)
			{
				break;
			}
			votes[smallIndex(votes)] = -1;
			for (int i = 0; i < votes.length; i++)
			{
				if (votes[i] != -1)
				{
					votes[i] = 0;
				}
			}
		} while(true);
		return cList.get(bigIndex(votes));
	}
	
	public static Candidate condorcet(List<Voter> vList, List<Candidate> cList)
	{
		List<List<Candidate>> rankings = new ArrayList<List<Candidate>>();
		for (Voter v: vList)
		{
			rankings.add(generateRanking(v, copyList(cList)));
		}
		int[] wins = new int[cList.size()];
		for (int i = 0; i < cList.size() - 1; i++)
		{
			for (int j = i + 1; j < cList.size(); j++)
			{
				int firstAbove = 0;
				int secondAbove = 0;
				for (List<Candidate> ranking: rankings)
				{
					if (ranking.indexOf(cList.get(i)) < ranking.indexOf(cList.get(j)))
					{
						firstAbove++;
					}
					else
					{
						secondAbove++;
					}
				}
				if (firstAbove > secondAbove)
				{
					wins[i]++;
					System.out.println("Voters prefer " + cList.get(i) + " over " + cList.get(j) + ".");
				}
				else if (secondAbove > firstAbove)
				{
					wins[j]++;
					System.out.println("Voters prefer " + cList.get(j) + " over " + cList.get(i) + ".");
				}
			}
		}
		int bigIndex = bigIndex(wins);
		for (int i = 0; i < cList.size(); i++)
		{
			cList.get(i).setVotes(wins[i]);
		}
		return cList.get(bigIndex);
	}
	
	public static Candidate approval(List<Voter> vList, List<Candidate> cList, double tolerance)
	{
		int[] votes = new int[cList.size()];
		for (Voter v: vList)
		{
			for (int i = 0; i < cList.size(); i++)
			{
				double distance = v.findDistance(cList.get(i));
				if (distance <= tolerance)
				{
					votes[i]++;
				}
			}
		}
		int bigIndex = bigIndex(votes);
		for (int i = 0; i < cList.size(); i++)
		{
			cList.get(i).setVotes(votes[i]);
		}
		return cList.get(bigIndex);
	}
	
	public static Candidate FPTP(List<Voter> vList, List<Candidate> cList)
	{
		int[] votes = new int[cList.size()];
		for (Voter v: vList)
		{
			votes[cList.indexOf(favoriteCandidate(v, cList))]++;
		}
		int bigIndex = bigIndex(votes);
		for (int i = 0; i < cList.size(); i++)
		{
			cList.get(i).setVotes(votes[i]);
		}
		return cList.get(bigIndex);
	}
}
