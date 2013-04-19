package nl.kennisnet.arena.formats;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Arena {

	@SerializedName("stats")
	private String stats;

	@SerializedName("num_results")
	private int numResults = 0;

	private enum STATS {
		OK, NO_OBJECTS
	}

	@SerializedName("results")
	private List<Result> results = new ArrayList<Result>();

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public void checkStats() {
		if (results.size() > 0) {
			stats = STATS.OK.toString();
		} else {
			stats = STATS.NO_OBJECTS.toString();
		}
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public void addResult(Result result) {
		results.add(result);
	}

	public int getNumResults() {
		return results.size();
	}

}