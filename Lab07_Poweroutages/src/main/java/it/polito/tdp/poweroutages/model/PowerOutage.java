package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutage {
	
	private Nerc nerc; 
	private int eventType;
	private LocalDateTime start;
	private LocalDateTime finish;
	private int customersAffected;
	
	public PowerOutage(Nerc nerc, int eventType, LocalDateTime start, LocalDateTime finish, int customersAffected) {
		super();
		this.nerc = nerc;
		this.eventType = eventType;
		this.start = start;
		this.finish = finish;
		this.customersAffected = customersAffected;
	}
	
	public long getDurata () {
		return Duration.between(start, finish).toHours();
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getFinish() {
		return finish;
	}

	public void setFinish(LocalDateTime finish) {
		this.finish = finish;
	}

	public int getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eventType;
		result = prime * result + ((finish == null) ? 0 : finish.hashCode());
		result = prime * result + ((nerc == null) ? 0 : nerc.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (eventType != other.eventType)
			return false;
		if (finish == null) {
			if (other.finish != null)
				return false;
		} else if (!finish.equals(other.finish))
			return false;
		if (nerc == null) {
			if (other.nerc != null)
				return false;
		} else if (!nerc.equals(other.nerc))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PowerOutage [nerc=" + nerc + ", eventType=" + eventType + ", start=" + start + ", finish=" + finish
				+ ", customersAffected=" + customersAffected + "]";
	}
	
	
	
	

}
