package com.rabo.pims.service;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.rabo.pims.dto.PetRespDTO;
import com.rabo.pims.entity.PetEntity;

public interface PetService {

	public PetRespDTO addPet(PetEntity pet);
	public List<PetRespDTO> getAllPets();
	public PetRespDTO updatePetDetails(PetEntity newPet, int id);
	public HttpStatus deleteById(int id);
	public PetRespDTO linkPetToPerson(int personId, PetEntity pet);
}
