//package com.booking.travel_booking_be.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Set;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name="classification")
//public class NewsClassification implements Serializable{
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@GeneratedValue(strategy =GenerationType.IDENTITY )
//	private int id;
//
//	@Column(name="name")
//	private String name;
//
//	@JsonIgnore
//	@OneToMany(mappedBy = "classify",fetch = FetchType.LAZY)
//	private Set<News> listNews;
//
//}
