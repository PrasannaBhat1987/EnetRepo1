package com.pras.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LineItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8043434252936503661L;
	@Id
	@GeneratedValue
	private long id;
	private String itemDescription;
	private int quantity;
	private double price;
	
	@ManyToOne
    @JoinColumn(name="rao_id")
	private Rao rao;

	public long getId() {
		return id;
	}

	public Rao getRao() {
		return rao;
	}

	public void setRao(Rao rao) {
		this.rao = rao;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
