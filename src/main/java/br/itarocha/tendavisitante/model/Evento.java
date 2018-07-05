package br.itarocha.tendavisitante.model;

public class Evento {
	private Long id;
	private String title;
	private String start;
	private boolean completed;
	
	
	public Evento(){}
	
	public Evento(Long id, String title, String start){
		this.setId(id);
		this.title = title;
		this.start = start;
		this.completed = true;
	}
	
	public Evento(Long id, String title, String start, boolean completed){
		this.setId(id);
		this.title = title;
		this.start = start;
		this.completed = completed;
	}
	
	public String getColor() {
		return (this.completed) ? "#66ff66" : "#ffcc99"; 
	}

	public String getBorderColor() {
		return (this.completed) ? "#00cc00" : "#cc6600";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return this.start;
	}
	public void setStart(String start) {
		this.start = start;
	}

	public boolean isCompleted() {
		return this.completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
