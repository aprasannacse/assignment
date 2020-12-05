package com.rabo.pims.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabo.pims.dto.PetReqDTO;
import com.rabo.pims.dto.PetRespDTO;
import com.rabo.pims.entity.PersonEntity;
import com.rabo.pims.entity.PetEntity;
import com.rabo.pims.exception.PersonNotFoundException;
import com.rabo.pims.exception.PetLinkedAlreadyException;
import com.rabo.pims.exception.PetNotAvailableException;
import com.rabo.pims.exception.PetNotFoundException;
import com.rabo.pims.repository.PetRepository;
import com.rabo.pims.utils.DataUtils;
import com.rabo.pims.utils.Log4jManager;


@Service
public class PetServiceImpl {

	@Autowired
	PetRepository repo;

	public PetRespDTO addPet(PetReqDTO petReqEntity) 
	{		
		Log4jManager.writeDebugLog("Invoked PetServiceImpl:addPet()");
		PetEntity pet = null;
		PetRespDTO objPetResEnty = null;

		try {
			pet = petReqEntity.getPetEntity();
			objPetResEnty=new PetRespDTO(repo.save(pet));
			Log4jManager.writeDebugLog("Invoked PetServiceImpl:addPet()|Pet added successfully");

		}catch(Exception e) {
			Log4jManager.writeDebugLog("Invoked PetServiceImpl:addPet()|Error occurred while adding Pet details");
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
		}

		return objPetResEnty;

	}

	public List<PetRespDTO> getAllPets() {
		List<PetEntity> lstPets = new ArrayList<PetEntity>();
		List<PetRespDTO> lstRespPets = new ArrayList<PetRespDTO>();
		try {
			Log4jManager.writeDebugLog("Invoked PetServiceImpl:getAllPets()");

			repo.findAll().forEach(lstPets::add);
			if (!lstPets.isEmpty()) {
				lstRespPets = lstPets.stream().map(PetRespDTO::new).collect(Collectors.toList());
				Log4jManager.writeDebugLog("PetServiceImpl:getAllPets()| Retrieved Pet Details successfully");
			}else {
				Log4jManager.writeDebugLog("PetServiceImpl:getAllPets()() | Pet Details not available");
				throw new PetNotFoundException("No Records found");
			}

		}catch(PetNotFoundException e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			throw new PetNotFoundException("No Records found");
		} catch (Exception e) {
			e.printStackTrace();
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			Log4jManager.writeDebugLog("PetServiceImpl:getAllPets()| Exception occurred while retrieving Pet Details");

		}
		return lstRespPets;
	}

	public PetRespDTO updatePetDetails(PetReqDTO petReqEntity, int id)  {

		Optional<PetEntity> petData =null;
		PetEntity oldPet = null;
		PetRespDTO perRespEntity=null;
		PetEntity newPet = null;
		try {
			Log4jManager.writeDebugLog("Invoked PetServiceImpl:updatePetDetails()");
			newPet = petReqEntity.getPetEntity();
			petData = repo.findById(id);

			if (petData.isPresent()) {
				oldPet = petData.get();
				oldPet.setName(newPet.getName());
				oldPet.setAge(newPet.getAge());	
				perRespEntity= new PetRespDTO(repo.save(oldPet));
				Log4jManager.writeDebugLog("PetServiceImpl:updatePetDetails() | Pet Details updated successfully");
			}
			else {
				Log4jManager.writeDebugLog("PetServiceImpl:updatePetDetails() | No Pet available for the given Id-"+id);
				throw new PetNotFoundException("Pet Id "+id+" not found");	
			}	
		}catch (PetNotFoundException e) {
			throw new PetNotFoundException("Pet Id "+id+" not found");
		}catch (Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			Log4jManager.writeDebugLog("PetServiceImpl:updatePetDetails() | Exception occurred while updating Pet details");

		}

		return perRespEntity;	
	}

	public Boolean deleteById(int id) {

		Boolean result=false;
		try {
			Log4jManager.writeDebugLog("Invoked PetServiceImpl:deleteById()");
			repo.deleteById(id);
			result=true;
		}catch (Exception e) {
			Log4jManager.writeDebugLog("PetServiceImpl:deleteById()| Exception occurred while Deleting Pet Details");
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
		}

		return result;
	}


	public PetRespDTO linkPetToPerson(int personId, PetReqDTO petReqEntity) throws PetLinkedAlreadyException  {		
		PersonEntity personEntity = new PersonEntity();
		Optional<PetEntity> petEntity = null;
		int linkedPersonID = 0;
		int petId=0;

		try {
			Log4jManager.writeDebugLog("Invoked PetServiceImpl:addPetToPerson()");
			petId=petReqEntity.getPetEntity().getPet_id();
			
			if(!(null == petReqEntity.getPetEntity())) {
				
				petEntity = repo.findById(petReqEntity.getPetEntity().getPet_id());
				
				if(petEntity.isPresent()) {
					
					if(petEntity.get().getPersonEntity() != null) {
						
						linkedPersonID = petEntity.get().getPersonEntity().getPerson_id();
						
						if(!(linkedPersonID == 0  )) {
							throw new PetLinkedAlreadyException("Pet id "+petId+" already linked to another person");
						}
					}else {
						personEntity.setPerson_id(personId);
						petEntity.get().setPersonEntity(personEntity);
						repo.save(petEntity.get());
						Log4jManager.writeDebugLog("Invoked PetServiceImpl:addPetToPerson()|Pet linked to person successfully");

					}						

				}else {
					Log4jManager.writeDebugLog("Invoked PetServiceImpl:addPetToPerson()| Exception occurred in Pet link.Requested Pet is not available");
					throw new PetNotAvailableException("Pet id "+petId+" not available to link");
				}
			}
		}catch (PetLinkedAlreadyException e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			throw new PetLinkedAlreadyException("Pet id "+petId+" already linked to another person");
		}catch (PetNotAvailableException e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			throw new PetNotAvailableException("Pet id "+petId+" not available to link");
		}catch (Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
		}

		return new PetRespDTO(petEntity.get());
	}
}
