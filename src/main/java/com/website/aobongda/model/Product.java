package com.website.aobongda.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    @Lob
    private String description;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private int status;
    
//    private MultipartFile imageFile;
    private String image;
    
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_club")
	private Club club;
}
