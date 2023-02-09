package com.website.aobongda.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartID implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long productId;
	private Long userId;
}
