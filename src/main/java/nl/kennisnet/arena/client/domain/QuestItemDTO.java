package nl.kennisnet.arena.client.domain;

import java.io.Serializable;

public class QuestItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum TYPE {
		OPEN_QUESTION, MULTIPLE_CHOICE
	}

	private String name;
	private String typeName;
	private Double radius;
	private Double visibleRadius;
	private Double alt;
	private SimplePoint point;
	private String description;
	private String objectURL;
	private Integer questionType = 0;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private Integer correctOption;
	private Integer score;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuestItemDTO() {
		super();
	}

	public QuestItemDTO(String name, String typeName) {
		super();
		this.name = name;
		this.typeName = typeName;
	}

	public String getName() {
		return name;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public Double getVisibleRadius() {
		return visibleRadius;
	}

	public void setVisibleRadius(Double visibleRadius) {
		this.visibleRadius = visibleRadius;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public LatLng getPoint(){
	// return LatLng.newInstance(getLatitude(), getLongitude());
	// }

	public String getObjectURL() {
		return objectURL;
	}

	public SimplePoint getPoint() {
		return point;
	}

	public void setPoint(SimplePoint point) {
		this.point = point;
	}

	public void setObjectURL(String objectURL) {
		this.objectURL = objectURL;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setAlt(Double alt) {
		this.alt = alt;
	}

	public Double getAlt() {
		return alt;
	}

	public void setCorrectOption(Integer correctOption) {
		this.correctOption = correctOption;
	}

	public Integer getCorrectOption() {
		return correctOption;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public TYPE getQuestionTypeAsEnum() {
		if (questionType == null) {
			return TYPE.MULTIPLE_CHOICE;
		}
		switch (questionType) {
		case 0:
			return TYPE.MULTIPLE_CHOICE;
		case 1:
			return TYPE.OPEN_QUESTION;
		default:
			return TYPE.MULTIPLE_CHOICE;
		}
	}

	@Override
	public String toString() {
		return "QuestItemDTO [alt=" + alt + ", correctOption=" + correctOption
				+ ", description=" + description + ", id=" + id + ", name="
				+ name + ", objectURL=" + objectURL + ", option1=" + option1
				+ ", option2=" + option2 + ", option3=" + option3
				+ ", option4=" + option4 + ", point=" + point + ", radius="
				+ radius + ", typeName=" + typeName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alt == null) ? 0 : alt.hashCode());
		result = prime * result
				+ ((correctOption == null) ? 0 : correctOption.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((objectURL == null) ? 0 : objectURL.hashCode());
		result = prime * result + ((option1 == null) ? 0 : option1.hashCode());
		result = prime * result + ((option2 == null) ? 0 : option2.hashCode());
		result = prime * result + ((option3 == null) ? 0 : option3.hashCode());
		result = prime * result + ((option4 == null) ? 0 : option4.hashCode());
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		result = prime * result + ((radius == null) ? 0 : radius.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result
				+ ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		QuestItemDTO other = (QuestItemDTO) obj;
		if (alt == null) {
			if (other.alt != null) {
				return false;
			}
		} else if (!alt.equals(other.alt)) {
			return false;
		}
		if (correctOption == null) {
			if (other.correctOption != null) {
				return false;
			}
		} else if (!correctOption.equals(other.correctOption)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (objectURL == null) {
			if (other.objectURL != null) {
				return false;
			}
		} else if (!objectURL.equals(other.objectURL)) {
			return false;
		}
		if (option1 == null) {
			if (other.option1 != null) {
				return false;
			}
		} else if (!option1.equals(other.option1)) {
			return false;
		}
		if (option2 == null) {
			if (other.option2 != null) {
				return false;
			}
		} else if (!option2.equals(other.option2)) {
			return false;
		}
		if (option3 == null) {
			if (other.option3 != null) {
				return false;
			}
		} else if (!option3.equals(other.option3)) {
			return false;
		}
		if (option4 == null) {
			if (other.option4 != null) {
				return false;
			}
		} else if (!option4.equals(other.option4)) {
			return false;
		}
		if (point == null) {
			if (other.point != null) {
				return false;
			}
		} else if (!point.equals(other.point)) {
			return false;
		}
		if (radius == null) {
			if (other.radius != null) {
				return false;
			}
		} else if (!radius.equals(other.radius)) {
			return false;
		}
		if (score == null) {
			if (other.score != null) {
				return false;
			}
		} else if (!score.equals(other.score)) {
			return false;
		}
		if (typeName == null) {
			if (other.typeName != null) {
				return false;
			}
		} else if (!typeName.equals(other.typeName)) {
			return false;
		}
		return true;
	}

}
