package com.jsg.DocumentCreation.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int LoanDetailsid;
	private String loanType;
	private double loanAmount;
	private int tenureInYear;
}
