package com.rabo.pims.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabo.pims.dto.PersonReqDTO;
import com.rabo.pims.dto.PersonRespDTO;
import com.rabo.pims.entity.PersonEntity;
import com.rabo.pims.exception.PersonAlreadyExistException;
import com.rabo.pims.exception.PersonNotFoundException;
import com.rabo.pims.repository.PersonRepository;
import com.rabo.pims.repository.PetRepository;
import com.rabo.pims.utils.DataUtils;
import com.rabo.pims.utils.Log4jManager;

@Service
public class PersonServiceImpl {

	@Autowired
	PersonRepository repo;

	@Autowired
	PetRepository petRepo;

	public PersonRespDTO addPerson(PersonReqDTO personReqEntity) 
	{			
		Log4jManager.writeDebugLog("Invoked PersonServiceImpl:addPerson()");
		PersonEntity person = null;
		PersonReqDTO personReq = new PersonReqDTO();
		PersonRespDTO objPersonResEnty = null;

		try {

			person = personReq.getPerson(personReqEntity);

			Optional<List<PersonEntity>> listObj=checkNameExists(person.getFirstName(),person.getLastName());

			if(!listObj.isPresent()) {
				objPersonResEnty=new PersonRespDTO(repo.save(person));
				
				Log4jManager.writeDebugLog("PersonServiceImpl:addPerson()|Person created succesfully");
			}else {
				throw new PersonAlreadyExistException("Person name "+personReqEntity.getFirstName()+","+personReqEntity.getLastName()+" already exist");
			}

		}catch (PersonAlreadyExistException p) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(p));
			throw new PersonAlreadyExistException("Person name "+personReqEntity.getFirstName()+","+personReqEntity.getLastName()+" already exist");
		}catch (Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
		}

		return objPersonResEnty;

	}

	public List<PersonRespDTO> getAllPersons() {	
		List<PersonEntity> lstPersons = new ArrayList<PersonEntity>();
		List<PersonRespDTO> lstRespPersons = new ArrayList<PersonRespDTO>();
		try {
			Log4jManager.writeDebugLog("Invoked PersonServiceImpl:getAllPersons()");
			repo.findAll().forEach(lstPersons::add);			
			if(!lstPersons.isEmpty()) {
				lstRespPersons = lstPersons.stream().map(PersonRespDTO::new).collect(Collectors.toList());
				Log4jManager.writeDebugLog("PersonServiceImpl:getAllPersons() | Person Details retrieved sucessfully");
			}else {
				Log4jManager.writeDebugLog("PersonServiceImpl:getAllPersons() | Person Details not available");
				throw new PersonNotFoundException("No Records found");
			}

		}catch(PersonNotFoundException e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
			throw new PersonNotFoundException("No Records found");
		}
		catch(Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
		}
		return lstRespPersons;
	}

	public PersonRespDTO getPersonById(Integer id)  {	

		Optional<PersonEntity> person_details =null;
		try {
			Log4jManager.writeDebugLog("Invoked PersonServiceImpl:getPersonById()");

			person_details =repo.findById(id);
			if(!person_details.isPresent()) {
				Log4jManager.writeDebugLog("PersonServiceImpl:getPersonById()| Person Id "+id+" not found");
				throw new PersonNotFoundException("Person Id "+id+" not found");
			}else {
				return new PersonRespDTO(person_details.get());
			}
		}catch(PersonNotFoundException p) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(p));
			throw new PersonNotFoundException("Person Id "+id+" not found");
		}catch(Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));

		}
		return new PersonRespDTO(null);

	}

	public PersonRespDTO updateAddress(PersonReqDTO personReqEntity,int id) { 

		PersonEntity personEntity,newPerson = new PersonEntity();
		Optional<PersonEntity> oldPerson = null;
		PersonReqDTO personReq = new PersonReqDTO();
		try {
			newPerson = personReq.getPerson(personReqEntity);
			oldPerson = repo.findById(id);

			if(oldPerson.isPresent()) {			
				personEntity = oldPerson.get();
				personEntity.setCurrentAddress(newPerson.getCurrentAddress());  
				return new PersonRespDTO(repo.save(personEntity));
			}else {
				Log4jManager.writeDebugLog("PersonServiceImpl:updateAddress()| Person Id "+id+" not found to update");
				throw new PersonNotFoundException("Person Id "+id+" not found to update");
			}
		}catch(PersonNotFoundException p) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(p));
			throw new PersonNotFoundException("Person Id "+id+" not found to update");
		}catch(Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));
		}
		return new PersonRespDTO(null);
	}

	public List<PersonRespDTO> getPersonByName(String firstName,String lastName) {

		Optional <List<PersonEntity>> lstPersons = null;
		List<PersonRespDTO> lstRespPersons = null;
		List<PersonEntity> persons = null;
		try {

			lstPersons =repo.findByName(firstName,lastName);
			
			if( (firstName == null || firstName.isEmpty() ) || (lastName == null || lastName.isEmpty() ) ) {
				persons = lstPersons.get().stream().
						filter(person -> person.getFirstName().equalsIgnoreCase(firstName) || person.getLastName().equalsIgnoreCase(lastName))
						.collect(Collectors.toList());
			}else {
				persons = lstPersons.get().stream().
						filter(person -> person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName) )
						.collect(Collectors.toList());				
			}
			
			if(!persons.isEmpty()) {
				lstRespPersons = persons.stream().map(PersonRespDTO::new).collect(Collectors.toList());
			}else {
				Log4jManager.writeDebugLog("PersonServiceImpl:getPersonByName()| Person firstName "+firstName+" and lastName "+lastName+ " not found");
				throw new PersonNotFoundException("Person firstName "+firstName+" and/or lastName "+lastName+ " not found ");
			}
		}catch(PersonNotFoundException p) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(p));
			throw new PersonNotFoundException("Person firstName "+firstName+" and/or lastName "+lastName+ " not found ");
		}catch (Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));

		}

		return lstRespPersons;


	}


	public Optional<List<PersonEntity>> checkNameExists(String firstName,String lastName) {

		Log4jManager.writeDebugLog("Invoked PersonServiceImpl:checkNameExists()");

		Optional<List<PersonEntity>>  person_details =null;
		try {
			person_details =repo.findByfirstAndlastName(firstName,lastName);	
		}catch(Exception e) {
			Log4jManager.writeErrorLog(DataUtils.setExceptionTrace(e));	
		}		
		return person_details;

	}


}
