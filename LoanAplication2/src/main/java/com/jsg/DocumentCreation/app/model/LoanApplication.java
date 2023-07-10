package com.jsg.DocumentCreation.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int loanAppid;
	private String custFirstName;
	private String custMiddleName;
	private String custLastName;
	private String custEmailId;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	private LoanDetails loanDetails;
	
	@OneToOne(cascade = CascadeType.ALL)

	private SanctionDetails sanctionDetails;
}
