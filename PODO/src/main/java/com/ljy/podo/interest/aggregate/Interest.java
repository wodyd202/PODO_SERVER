package com.ljy.podo.interest.aggregate;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ljy.podo.interest.InterestId;
import com.ljy.podo.interest.Interester;
import com.ljy.podo.interest.service.registerInterest.RegisterInterest;
import com.ljy.podo.portfolio.PortfolioId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interest")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {
	
	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "id"))
	private InterestId id;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "portfolioId"))
	private PortfolioId portfolioId;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "interester"))
	private Interester interester;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	public Interest(InterestId interestId, RegisterInterest registerInterest) {
		this.id = interestId;
		this.interester = new Interester(registerInterest.getInterester());
		this.portfolioId = new PortfolioId(registerInterest.getPortfolioId());
		this.createDate = new Date();
	}
}
