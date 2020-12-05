package com.rabo.pims.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rabo.pims.TestBaseAbstract;
import com.rabo.pims.dto.PetReqDTO;
import com.rabo.pims.dto.PetRespDTO;

@SpringBootTest

class TestPetController extends TestBaseAbstract{

	@Autowired
	private PetController objPetCtrlr;
	
	MvcResult mvcResult;
	int status;
	String content;
	String uri;
    
    
    @Override
	@BeforeEach
	public void setUp() {
		super.setUp();
		
		mvcResult=null;	
		status=0;
		content=null;
		uri=null;
	}
    
   
	
	@Test
	void contextLoads() {
		assertThat(objPetCtrlr).isNotNull();
	}
	
  	
	@DisplayName("PetController-testAddPet()")
    @Test
		public void testAddPet() throws Exception {
			uri = "/pet";
			
			PetReqDTO objPetEntity=new PetReqDTO();
			objPetEntity.setName("Hamster");
			objPetEntity.setAge(2);
			
			String requestJSON=super.mapToJson(objPetEntity);
			
			mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJSON)).andReturn();
			status = mvcResult.getResponse().getStatus();
			assertEquals(201, status);			
		}
	
	
	@DisplayName("PetController-testGetAllPets()")
    @Test
	public void testGetAllPets() throws Exception {
		uri = "/pets";
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
		status = mvcResult.getResponse().getStatus();
		content = mvcResult.getResponse().getContentAsString();
		PetRespDTO[] objRespList = super.mapFromJson(content, PetRespDTO[].class);
		
		assertEquals(200, status);
		assertTrue(objRespList.length>0);
	}
	
	
	
	@DisplayName("PetController-testDeletePet()")
    @Test
	public void testDeletePet() throws Exception {

		Integer id=4; //Input Mock Data(Pet Id) Here
		
		uri = "/pet/"+id;
		
		mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();			

		status = mvcResult.getResponse().getStatus();						
		content = mvcResult.getResponse().getContentAsString();	
		assertEquals(200, status);

	}
	
	@DisplayName("PetController-testUpdatePetDetails()")
    @Test
		public void testUpdatePetDetails() throws Exception {
			
			int id=2;// Input Mock Data (Pet ID) here 
			uri = "/pet/"+id;
			
			PetReqDTO objPetEntity=new PetReqDTO();
			objPetEntity.setName("Bird");
			objPetEntity.setAge(20);
			
			String requestJSON=super.mapToJson(objPetEntity);
			
			mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJSON)).andReturn();
			status = mvcResult.getResponse().getStatus();
			
			assertEquals(200, status);			
		}
	
	@DisplayName("PetController-testUpdatePetNotExists()")
    @Test
		public void testUpdatePetNotExists() throws Exception {
			
			int id=300;// Input Mock Data here
			uri = "/pet/"+id;
			
			PetReqDTO objPetEntity=new PetReqDTO();
			objPetEntity.setName("Dog");
			objPetEntity.setAge(20);
			
			String requestJSON=super.mapToJson(objPetEntity);
			
			mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJSON)).andReturn();
			status = mvcResult.getResponse().getStatus();
			
			assertEquals(404, status);			
		}
	
	@DisplayName("PetController-testLinkPetToPerson()")
    @Test
		public void testLinkPetToPerson() throws Exception {
			
			int personId=4;// Input Mock Data(person id) here
			int petId=3;// Input Mock Data(pet id) here
			
			uri = "/pet/"+personId;
			
			PetReqDTO objPetEntity=new PetReqDTO();
			objPetEntity.setId(petId);			
			
			String requestJSON=super.mapToJson(objPetEntity);
			
			mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJSON)).andReturn();
			status = mvcResult.getResponse().getStatus();
			
			assertEquals(201, status);			
		}
	@DisplayName("PetController-testPetAlreadyLinked()")
    @Test
		public void testPetAlreadyLinked() throws Exception {
			
			int personId=1;// Input Mock Data(person id) here
			int petId=2;// Input Mock Data(pet id) here
			
			uri = "/pet/"+personId;
			
			PetReqDTO objPetEntity=new PetReqDTO();
			objPetEntity.setId(petId);
			
			String requestJSON=super.mapToJson(objPetEntity);
			
			mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJSON)).andReturn();
			status = mvcResult.getResponse().getStatus();
			
			assertEquals(404, status);			
		}
	
	

}
