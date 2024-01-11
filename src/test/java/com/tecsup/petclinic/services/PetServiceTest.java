package com.tecsup.petclinic.services;

import java.util.List;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import static org.hamcrest.Matchers.empty;

@SpringBootTest

public class PetServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(PetServiceTest.class);

    @Autowired
    private PetService petService;
    
    // LISTAR - INTEGRACION
    @Test
    public void testListPets() {
    	Iterable<Pet> pets = petService.findAll();

        assertThat(pets, notNullValue());
    }
    
 // BUSCAR_ID - INTEGRACION
    @Test
    public void testFindById() throws PetNotFoundException {
    	Pet pet = new Pet("Bobby", 1, 1);
        Pet petCreated = petService.create(pet);
        
    	Pet foundPet = petService.findById(petCreated.getId());
    	
        assertThat(foundPet, notNullValue());
    }
    
    
    // CREAR - UNITARIO
    @Test
    public void testCreatePet() {
        String PET_NAME = "Ponky";
        int OWNER_ID = 1;
        int TYPE_ID = 1;

        Pet pet = new Pet(PET_NAME, OWNER_ID, TYPE_ID);
        Pet petCreated = petService.create(pet);

        logger.info("PET CREATED: " + petCreated);

        assertThat(petCreated.getId(), notNullValue());
        assertThat(petCreated.getName(), is(PET_NAME));
        assertThat(petCreated.getOwnerId(), is(OWNER_ID));
        assertThat(petCreated.getTypeId(), is(TYPE_ID));
    }

    // ELIMINAR - UNITARIO
    @Test
    public void testDeletePet() {
        String PET_NAME = "Tommy";
        int OWNER_ID = 1;
        int TYPE_ID = 1;

        
        Pet pet = new Pet(PET_NAME, OWNER_ID, TYPE_ID);
        pet = petService.create(pet);
        logger.info("Pet created: " + pet);

     
        try {
            petService.delete(pet.getId());
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }

       
        try {
            petService.findById(pet.getId());
            fail("Pet id = " + pet.getId() + " has not been deleted");
        } catch (PetNotFoundException e) {
          
        }
    }
}