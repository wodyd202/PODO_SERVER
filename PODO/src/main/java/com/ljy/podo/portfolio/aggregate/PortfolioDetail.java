package com.ljy.podo.portfolio.aggregate;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ljy.podo.portfolio.Content;
import com.ljy.podo.portfolio.Images;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.service.updatePortfolio.UpdatePortfolio;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "portfolioDetail")
public class PortfolioDetail {

	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "id"))
	private PortfolioId seq;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "content"))
	private Content content;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "images"))
	private Images images;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModify;

	protected void update(UpdatePortfolio updatePortfolio, String images) {
		this.content = new Content(updatePortfolio.getContent());
		this.images = new Images(images);
		this.lastModify = new Date();
	}

	public PortfolioDetail(PortfolioId seq, Content content, Images images) {
		this.content = content;
		this.images = images;
		this.seq = seq;
	}
}
