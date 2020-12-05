/** Person Information Management System (PIMS) Application StartUp Class*/

package com.rabo.pims;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rabo.pims.entity.Role;
import com.rabo.pims.entity.User;
import com.rabo.pims.repository.RolesRepository;
import com.rabo.pims.repository.UserRepository;
import com.rabo.pims.utils.Log4jManager;




@SpringBootApplication
public class PIMSApplicationStartUp implements CommandLineRunner{
	@Autowired
	UserRepository userRepo;
	@Autowired
	RolesRepository rolesRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(PIMSApplicationStartUp.class, args);
    	Log4jManager.writeDebugLog("Person Information Mangement System (PIMS) Started");

	}
	@Override
	public void run(String... args) throws Exception {
		
		Role role = null;
		
		role =  new Role(); // Create Admin role 
		role.setId(1L);
		role.setName("admin");
		rolesRepo.save(role);
		
		role =  new Role(); // Create User role
		role.setId(2L);
		role.setName("user");
		rolesRepo.save(role);
		
		Log4jManager.writeDebugLog("Roles Details saved succesfully");

	
		User user = new User();
		user.setId(1L);
		user.setName("admin");	
		user.setPassowrd(new BCryptPasswordEncoder().encode("admin")); // Setting Password for ADMIN Role
		user.setRoles(rolesRepo.findById(1L).get());
	    userRepo.save(user);
		
		User user1 = new User();
		user1.setId(2L);
		user1.setName("user");	
		user1.setPassowrd(new BCryptPasswordEncoder().encode("user")); // Setting Password for USER Role
		user1.setRoles(rolesRepo.findById(2L).get());
	    userRepo.save(user1);
		
		List<User> userList = userRepo.findAll();
		Log4jManager.writeDebugLog("User Details "+userList.size());
	}

}
