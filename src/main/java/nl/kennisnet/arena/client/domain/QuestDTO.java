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
	private List<PoiDTO> items;
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

	public List<PoiDTO> getItems() {
		return items;
	}

	public void setItems(List<PoiDTO> items) {
		this.items = items;
	}

	public void addItem(PoiDTO item) {
		if (items == null) {
			items = new ArrayList<PoiDTO>();
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

	public void removeItem(PoiDTO itemDTO) {
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
