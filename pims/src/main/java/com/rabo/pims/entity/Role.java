package com.rabo.pims.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Table(name="Roles")
@Data
@NoArgsConstructor
public class Role {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="id")
		private long id;
	
		@Column(name="name")
		private String name;
		
					
		public Role(Long id,@NotNull String name) {
			this.id = id;
			this.name=name;
			
		}
		
		
}
