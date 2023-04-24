package com.thiruacademy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="table_product")
public class Product {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String price;
	private String department;
	
	public Product(String name, String price, String department) {
		super();
		this.name = name;
		this.price = price;
		this.department = department;
	}
	
}
