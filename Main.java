package voting;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Voter> voters = new ArrayList<Voter>();
		voters = Voting.generateRandomVoters(10000);
		
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidates = Voting.generateRandomCandidates(5);
		System.out.println("The candidates:");
		for (Candidate c: candidates)
		{
			System.out.println(c + ": Economic Platform: " + c.getEconomicPlatform() + ", Social Platform: " + c.getSocialPlatform());
		}
		System.out.println();
		
		System.out.println();
		System.out.println("Instant Runoff voting:");
		System.out.println(Voting.instantRunoff(voters, candidates) + " wins.");
		System.out.println();

		System.out.println();
		System.out.println("Condorcet voting:");
		System.out.println(Voting.condorcet(voters, candidates) + " wins.");
		System.out.println();
		
		System.out.println();
		System.out.println("FPTP voting:");
		System.out.println(Voting.FPTP(voters, candidates) + " wins.");
		System.out.println();
		for (int i = 0; i < candidates.size(); i++)
		{
			System.out.println(candidates.get(i) + " earned " + candidates.get(i).getNumVotes() + " votes.");
		}
		
		System.out.println();
		System.out.println("Approval voting:");
		System.out.println(Voting.approval(voters, candidates, 7.5) + " wins.");
		System.out.println();
		for (int i = 0; i < candidates.size(); i++)
		{
			System.out.println(candidates.get(i) + " earned " + candidates.get(i).getNumVotes() + " votes.");
		}
		
		System.out.println();
		System.out.println("First voter:");
		System.out.println(voters.get(0));
		System.out.println("First voter's ranking is:");
		List<Candidate> ranking = Voting.generateRanking(voters.get(0), candidates);
		for (Candidate c: ranking)
		{
			System.out.println(c);
		}
	}

}
