package nl.kennisnet.arena.model;

import javax.persistence.Entity;

@Entity
public class Video extends Positionable implements DomainObject {

	private String videoUrl;

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getThumbnailUrl() {		
		return "http://img.youtube.com/vi/"+getVideoId()+"/3.jpg";
	}

	/**
	 * Returns the video ID from the videoURL property The video ID is the
	 * parameter of v= in the URL.
	 */
	public String getVideoId() {
		int parameterLength = 3; // the length of &v= or ?v=
		int start = videoUrl.indexOf("&v=");
		if (start == -1) {
			start = videoUrl.indexOf("?v=");
		}
		if (start == -1) {
			parameterLength = 1;
			start = videoUrl.lastIndexOf("/");
		}

		String starting = videoUrl.substring(start + parameterLength);
		// find the end of the starting string:
		int end = starting.indexOf("&");
		if (end == -1) {
			end = starting.indexOf("#");
		}
		if (end == -1) {
			return starting;
		}
		return starting.substring(0, end);
	}
}
