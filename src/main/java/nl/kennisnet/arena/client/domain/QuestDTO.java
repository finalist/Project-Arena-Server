package nl.kennisnet.arena.client.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestDTO implements Serializable {

	/**
    * 
    */
	private static final long serialVersionUID = 1L;

	private String name;
	private List<QuestItemDTO> items;
	private Long id;
	private String emailOwner;
	private SimplePolygon border;

	private List<RoundDTO> rounds = new ArrayList<RoundDTO>();
	private RoundDTO activeRound;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<QuestItemDTO> getItems() {
		return items;
	}

	public void setItems(List<QuestItemDTO> items) {
		this.items = items;
	}

	public void addItem(QuestItemDTO item) {
		if (items == null) {
			items = new ArrayList<QuestItemDTO>();
		}
		items.add(item);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailOwner() {
		return emailOwner;
	}

	public void setEmailOwner(String emailOwner) {
		this.emailOwner = emailOwner;
	}

	public SimplePolygon getBorder() {
		return border;
	}

	public void setBorder(SimplePolygon border) {
		this.border = border;
	}

	public void removeItem(QuestItemDTO itemDTO) {
		items.remove(itemDTO);
	}

	public void setRounds(List<RoundDTO> rounds) {
		this.rounds = rounds;
	}

	public List<RoundDTO> getRounds() {
		return rounds;
	}

	public void setActiveRound(RoundDTO activeRound) {
		this.activeRound = activeRound;
	}

	public RoundDTO getActiveRound() {
		return activeRound;
	}

}
