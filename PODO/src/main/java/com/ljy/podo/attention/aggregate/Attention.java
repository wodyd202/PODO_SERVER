package com.ljy.podo.attention.aggregate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.attention.AttentionState;
import com.ljy.podo.attention.service.registerAttention.RegisterAttention;
import com.ljy.podo.attention.service.updateAttention.UpdateAttention;
import com.ljy.podo.portfolio.Content;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.Writer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attention")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attention {
	
	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "id"))
	private AttentionId attentionId;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "portfolioId"))
	private PortfolioId portfolioId;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "content"))
	private Content content;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "writer"))
	private Writer writer;
	
	@Enumerated(EnumType.STRING)
	private AttentionState state;
	
	private LocalDateTime createDate;
	
	public boolean isDelete() {
		return this.state == AttentionState.DELETE;
	}
	
	public void delete() {
		this.state = AttentionState.DELETE;
	}

	public void update(UpdateAttention obj) {
		this.content = new Content(obj.getContent());
	}
	
	public Attention(AttentionId id,RegisterAttention registerAttention) {
		this.attentionId = id;
		this.portfolioId = new PortfolioId(registerAttention.getPortfolioId());
		this.content = new Content(registerAttention.getContent());
		this.writer = new Writer(registerAttention.getWriter());
		this.state = AttentionState.CREATE;
		this.createDate = LocalDateTime.now();
	}

}
