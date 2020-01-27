package voting;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		//List<Voter> voters = Voting.generateVoterCircles(1000, 5, 2, 0, 0);
		//System.out.println(Voting.graphVoters(voters, 1));
		//System.out.println(voters.size());

		List<Voter> voters = new ArrayList<Voter>();
		for (int i = 0; i < 1000; i++)
		{
			voters.add(new Voter(0, 0));
		}
		
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		candidates.add(new Candidate("Left leaning candidate", -5, -5));
		candidates.add(new Candidate("Right leaning candidate", 5, 5));
		candidates.add(new Candidate("Spoiler candidate", 5.01, 4.99));
		candidates.add(new Candidate("Extremist candidate", 10, 10));
		candidates.add(new Candidate("Libertarian candidate", 5, -5));
		
		System.out.println("Instant Runoff Voting");
		System.out.println(Voting.instantRunoff(voters, candidates) + " wins.");
		System.out.println();
		for (int i = 0; i < candidates.size(); i++)
		{
			System.out.println(candidates.get(i) + " earned " + candidates.get(i).getNumVotes() + " votes.");
		}

		System.out.println();
		System.out.println("Condorcet voting:");
		System.out.println(Voting.condorcet(voters, candidates) + " wins.");
		System.out.println();
		for (int i = 0; i < candidates.size(); i++)
		{
			System.out.println(candidates.get(i) + " earned " + candidates.get(i).getNumVotes() + " votes.");
		}
		
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
		System.out.println(voters.get(0));
		System.out.println("First voter's ranking is:");
		List<Candidate> ranking = Voting.generateRanking(voters.get(0), candidates);
		for (Candidate c: ranking)
		{
			System.out.println(c);
		}
	}

}
