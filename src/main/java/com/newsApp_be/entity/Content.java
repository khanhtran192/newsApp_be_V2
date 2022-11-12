//package com.booking.travel_booking_be.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name="content")
//public class Content {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private long id;
//	private String description;
//	@ManyToOne
//	@JoinColumn(name="news_id")
//	private News news;
//	@Column(name="image")
//	private String image;
//}
