package com.tcs.salesstore.domain.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="item_details", catalog="computer_store")
public class ItemDetails implements Serializable {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="series_number")
	private String seriesNumber;
	
	@Column(name="price")
	private double price;
	
	@Column(name="amount")
	private long amount;
	
	public ItemDetails() {
	}

	public ItemDetails(String seriesNumber, double price, long amount) {
		this.seriesNumber = seriesNumber;
		this.price = price;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSeriesNumber() {
		return seriesNumber;
	}

	public void setSeriesNumber(String seriesNumber) {
		this.seriesNumber = seriesNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, id, price, seriesNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDetails other = (ItemDetails) obj;
		return amount == other.amount && id == other.id
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(seriesNumber, other.seriesNumber);
	}

	@Override
	public String toString() {
		return "ItemDetails [id=" + id + ", seriesNumber=" + seriesNumber + ", price=" + price + ", amount=" + amount
				+ "]";
	}
}
