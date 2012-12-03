package nl.kennisnet.arena.formats;

import com.google.gson.annotations.SerializedName;

public class Result {

	private long id;

	private float lat;

	private float lng;

	private int elevation;

	private String title;

	private double radius;
	
	@SerializedName("has_detail_page")
	private int hasDetailPage;
	
	private String webpage;
	
	@SerializedName("object_type")
	private String objectType;
	
	@SerializedName("object_url")
	private String objectUrl;
	
	private float schaal;
	private float rotX, rotY, rotZ;
	private int blended;
	
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
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public double getRadius() {
		return radius;
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

	public float getSchaal() {
		return schaal;
	}

	public void setSchaal(float schaal) {
		this.schaal = schaal;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public int getBlended() {
		return blended;
	}

	public void setBlended(int blended) {
		this.blended = blended;
	}
}