package nl.kennisnet.arena.formats;

import java.util.ArrayList;
import java.util.List;

import nl.kennisnet.arena.model.Positionable;

public class Arena {

	private String stats;
	
	private enum STATS{
		OK, NO_OBJECTS		
	}
	
	private List<Result> results = new ArrayList<Result>();

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public void checkStats(){
		if(results.size() > 0){
			stats = STATS.OK.toString();
		}
		else{
			stats = STATS.NO_OBJECTS.toString();
		}
	}
	
	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
	
	public void addResult(Result result){
		results.add(result);
	}

	public void addPositionResults(List<Positionable> positionables){
		for(Positionable positionable : positionables){
			Result result = new Result();
			result.setId(positionable.getId());
			result.setLat((float)positionable.getLocation().getPoint().getY());
			result.setLng((float)positionable.getLocation().getPoint().getX());
			result.setTitle(positionable.getName());
			result.setHasDetailPage(true);
			result.setWebpage("http://www.google.com");
			results.add(result);
		}		
	}
	
	public int getNumResults(){
		return results.size();
	}
	
	@Override
	public String toString() {
		String value = "{   "+
			surroundStringWithQuotations("stats") + ":" +  surroundStringWithQuotations(getStats()) +","+
			surroundStringWithQuotations("num_results") + ":" +  surroundStringWithQuotations(getNumResults()) +",";
		
		value += surroundStringWithQuotations("results") + ": [";
		for(Result result : results){
			value += result.toString() + ",";				
		}
		//remove the last comma
		value = value.substring(0, value.length() -1);
		value += "] }";
		return value;
	}

	private String surroundStringWithQuotations(String value){
		return "\""+ value + "\"";		
	}
	
	private String surroundStringWithQuotations(int value){
		return "\""+ value + "\"";		
	}
}
