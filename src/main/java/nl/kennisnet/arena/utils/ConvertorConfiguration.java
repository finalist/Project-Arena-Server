package nl.kennisnet.arena.utils;

import nl.kennisnet.arena.model.Quest;

import org.apache.commons.configuration.CompositeConfiguration;

public class ConvertorConfiguration {

	private GamarayDataBean data;
	private CompositeConfiguration configuration;
	private Quest quest;
	
	public ConvertorConfiguration() {
		super();
	}

	public ConvertorConfiguration(GamarayDataBean data,
			CompositeConfiguration configuration, Quest quest) {
		super();
		this.data = data;
		this.configuration = configuration;
		this.quest = quest;
	}

	public GamarayDataBean getData() {
		return data;
	}

	public CompositeConfiguration getConfiguration() {
		return configuration;
	}

	public void setData(GamarayDataBean data) {
		this.data = data;
	}

	public void setConfiguration(CompositeConfiguration configuration) {
		this.configuration = configuration;
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
	}

	public Quest getQuest() {
		return quest;
	}
}
