package com.ljy.podo.reAttention.aggregate;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.portfolio.Content;
import com.ljy.podo.portfolio.Writer;
import com.ljy.podo.reAttention.ReAttentionId;
import com.ljy.podo.reAttention.ReAttentionState;
import com.ljy.podo.reAttention.service.registerReAttention.RegisterReAttention;
import com.ljy.podo.reAttention.service.updateReAttention.UpdateReAttention;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reAttention")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReAttention {
	
	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "id"))
	private ReAttentionId reAttentionId;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "attentionId"))
	private AttentionId attentionId;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "content"))
	private Content content;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "writer"))
	private Writer writer;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Enumerated(EnumType.STRING)
	private ReAttentionState state;

	public boolean isDelete() {
		return this.state == ReAttentionState.DELETE;
	}
	
	public void delete() {
		this.state = ReAttentionState.DELETE;
	}
	
	public void update(UpdateReAttention obj) {
		this.content = new Content(obj.getContent());
	}
	
	public ReAttention(ReAttentionId reAttentionId, RegisterReAttention registerReAttention) {
		this.reAttentionId = reAttentionId;
		this.attentionId = new AttentionId(registerReAttention.getAttentionId());
		this.content = new Content(registerReAttention.getContent());
		this.writer = new Writer(registerReAttention.getWriter());
		this.createDate = new Date();
		this.state = ReAttentionState.CREATE;
	}



}
