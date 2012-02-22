package nl.kennisnet.arena.formats;

public class Result {

	private long id;
	private float lat;
	private float lng;
	private int elevation;
	private String title;
	private float distance;
	private int hasDetailPage;
	private String webpage;
	private String objectType;
	private String objectUrl;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public float getLat() {
		return lat;
	}
	
	public void setLat(float lat) {
		this.lat = lat;
	}
	
	public float getLng() {
		return lng;
	}
	
	public void setLng(float lng) {
		this.lng = lng;
	}
	
	public int getElevation() {
		return elevation;
	}
	
	public void setElevation(int elevation) {
		this.elevation = elevation;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public int isHasDetailPage() {
		return hasDetailPage;
	}
	
	public void setHasDetailPage(boolean hasDetailPage) {
		if(hasDetailPage){
			this.hasDetailPage = 1;
		}
		else{
			this.hasDetailPage = 0;
		}
	}
	
	public String getWebpage() {
		return webpage;
	}
	
	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	
	public String getObjectType() {
		return objectType;
	}
	
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	public void setObjectUrl(String objectUrl) {
		this.objectUrl = objectUrl;
	}
	public String getObjectUrl() {
		return objectUrl;
	}
		
	@Override
	public String toString() {
		String value = "{   "+
			surroundStringWithQuotations("object_type") + ":" +  surroundStringWithQuotations(getObjectType()) +","+
			surroundStringWithQuotations("id") + ":" +  surroundStringWithQuotations(getId()) +","+
			surroundStringWithQuotations("object_url") + ":" +  surroundStringWithQuotations(getObjectUrl()) +","+
			surroundStringWithQuotations("lat") + ":" +  surroundStringWithQuotations(getLat()) +","+
			surroundStringWithQuotations("lng") + ":" +  surroundStringWithQuotations(getLng()) +","+
			surroundStringWithQuotations("elevation") + ":" +  surroundStringWithQuotations(getElevation()) +","+
			surroundStringWithQuotations("title") + ":" +  surroundStringWithQuotations(getTitle()) +","+
			surroundStringWithQuotations("distance") + ":" +  surroundStringWithQuotations(getDistance()) +","+
			surroundStringWithQuotations("has_detail_page") + ":" +  surroundStringWithQuotations(isHasDetailPage()) +","+
			surroundStringWithQuotations("webpage") + ":" +  surroundStringWithQuotations(getWebpage());		
		value += "}";
		return value;
	}

	private String surroundStringWithQuotations(String value){
		return "\""+ value + "\"";		
	}
	
	private String surroundStringWithQuotations(int value){
		return "\""+ value + "\"";		
	}
	
	private String surroundStringWithQuotations(long value){
		return "\""+ value + "\"";		
	}
	
	private String surroundStringWithQuotations(float value){
		return "\""+ value + "\"";		
	}
}
