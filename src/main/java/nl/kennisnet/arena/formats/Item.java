package nl.kennisnet.arena.formats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import nl.kennisnet.arena.client.elements.Element;
import nl.kennisnet.arena.model.Image;
import nl.kennisnet.arena.model.Information;
import nl.kennisnet.arena.model.Object3D;
import nl.kennisnet.arena.model.Positionable;
import nl.kennisnet.arena.model.Question;
import nl.kennisnet.arena.model.Type;
import nl.kennisnet.arena.model.Video;

public class Item {

	private String type = "";
	private String title;
	private String description;
	private String submitUrl;

	// question type attributes
	private String[] answers;

	// image or video type attribute:
	private String url;

	public Item(Type positionable, String submitUrl) {
		setType(positionable);
		this.submitUrl = submitUrl;
	}

	public void setType(Type type) {
		if (type instanceof Question) {
			fillWithQuestion((Question) type);
		}
		if (type instanceof Information) {
			fillWithInformation((Information) type);
		}
		if (type instanceof Image) {
			fillWithImage((Image) type);
		}
		if (type instanceof Video) {
			fillWithVideo((Video) type);
		}
		if (type instanceof Object3D) {
			fillWithObject3D((Object3D) type);
		}
	}

	private void fillWithObject3D(Object3D object) {
		type = "Object3D";
		url = object.getUrl();
	}

	private void fillWithQuestion(Question question) {
		type = "Question";
		description = question.getText();
		List<String> ansList = new ArrayList<String>();
		if (question.getAnswer1() != null) {
			ansList.add(question.getAnswer1());
		}
		if (question.getAnswer2() != null) {
			ansList.add(question.getAnswer2());
		}
		if (question.getAnswer3() != null) {
			ansList.add(question.getAnswer3());
		}
		if (question.getAnswer4() != null) {
			ansList.add(question.getAnswer4());
		}
		answers = ansList.toArray(new String[] {});
	}

	private void fillWithInformation(Information information) {
		type = "Information";
		description = information.getText();
	}

	private void fillWithImage(Image image) {
		type = "Image";
		url = image.getUrl();
	}

	private void fillWithVideo(Video video) {
		type = "Video";
		url = video.getVideoUrl();
	}

	// Getters and setters

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] newAnswers) {
		if (newAnswers == null) {
			this.answers = new String[0];
		} else {
			this.answers = Arrays.copyOf(newAnswers, newAnswers.length);
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getSubmitUrl() {
		return submitUrl;
	}

	public void setSubmitUrl(String submitUrl) {
		this.submitUrl = submitUrl;
	}

}
