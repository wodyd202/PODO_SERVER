package com.ljy.podo.portfolio.aggregate;

import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ljy.podo.portfolio.Content;
import com.ljy.podo.portfolio.Header;
import com.ljy.podo.portfolio.Hit;
import com.ljy.podo.portfolio.Images;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.PortfolioState;
import com.ljy.podo.portfolio.ShowType;
import com.ljy.podo.portfolio.Title;
import com.ljy.podo.portfolio.Writer;
import com.ljy.podo.portfolio.service.registerPortfolio.RegisterPortfolio;
import com.ljy.podo.portfolio.service.updatePortfolio.UpdatePortfolio;
import com.ljy.podo.user.Major;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "portfolio")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {

	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "id"))
	private PortfolioId id;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "title"))
	private Title title;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "header"))
	private Header header;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "hits"))
	private Hit hits;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "writer"))
	private Writer writer;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "major"))
	private Major major;

	@Enumerated(EnumType.STRING)
	private PortfolioState state;
	
	@Enumerated(EnumType.STRING)
	private ShowType showType;
	
	private LocalDate createDate;

	@OneToOne(cascade = CascadeType.PERSIST)
	private PortfolioDetail detail;

	public boolean isDelete() {
		return this.state == PortfolioState.DELETE;
	}
	
	public void delete() {
		this.state = PortfolioState.DELETE;
	}
	
	public void update(UpdatePortfolio obj) {
		this.title = new Title(obj.getTitle());
		this.header = new Header(obj.getHeader());
		this.detail.update(obj, convertContent2Images(obj.getContent()));
		this.state = obj.getState();
		this.showType = obj.getShowType();
	}
	
	private String convertContent2Images(String value) {
		String calcaulteStr = value.toString();
		StringBuilder resultImages = new StringBuilder();
		while (calcaulteStr.indexOf("<img src=") != -1) {
			String originText = calcaulteStr;
			int startTag = originText.indexOf("<img src=") + 10;
			calcaulteStr = calcaulteStr.substring(startTag, calcaulteStr.length());
			int endTag = calcaulteStr.indexOf("\"");
			resultImages.append(calcaulteStr.substring(0, endTag) + ",");
			calcaulteStr = calcaulteStr.substring(endTag, calcaulteStr.length());
		}
		if (resultImages.toString().contains(",")) {
			resultImages.deleteCharAt(resultImages.length() - 1);
		}
		return resultImages.toString();
	}
	
	public Portfolio(PortfolioId id, RegisterPortfolio registerPortofolio) {
		this.id = id;
		this.title = new Title(registerPortofolio.getTitle());
		this.header = new Header(registerPortofolio.getHeader());
		this.hits = new Hit(0);
		this.major = registerPortofolio.getMajor();
		this.writer = new Writer(registerPortofolio.getWriter());
		this.state = registerPortofolio.getState();
		this.showType = registerPortofolio.getShowType();
		this.detail = new PortfolioDetail(
				this.id,new Content(registerPortofolio.getContent()),
				new Images(convertContent2Images(registerPortofolio.getContent())));
		this.createDate = LocalDate.now();
	}

}
