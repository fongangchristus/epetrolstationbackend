package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Carburant;
import com.epetrole.backend.repository.CarburantRepository;
import com.epetrole.backend.service.CarburantService;
import com.epetrole.backend.service.dto.CarburantDTO;
import com.epetrole.backend.service.mapper.CarburantMapper;
import com.epetrole.backend.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.epetrole.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CarburantResource REST controller.
 *
 * @see CarburantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class CarburantResourceIntTest {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_SOLDE_INIT = 1D;
    private static final Double UPDATED_SOLDE_INIT = 2D;

    private static final Double DEFAULT_PRIX_ACHAT = 1D;
    private static final Double UPDATED_PRIX_ACHAT = 2D;

    private static final Double DEFAULT_PRIX_VENTE = 1D;
    private static final Double UPDATED_PRIX_VENTE = 2D;

    private static final Double DEFAULT_REFERENCE = 1D;
    private static final Double UPDATED_REFERENCE = 2D;

    @Autowired
    private CarburantRepository carburantRepository;

    @Autowired
    private CarburantMapper carburantMapper;

    @Autowired
    private CarburantService carburantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarburantMockMvc;

    private Carburant carburant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarburantResource carburantResource = new CarburantResource(carburantService);
        this.restCarburantMockMvc = MockMvcBuilders.standaloneSetup(carburantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carburant createEntity(EntityManager em) {
        Carburant carburant = new Carburant()
            .designation(DEFAULT_DESIGNATION)
            .soldeInit(DEFAULT_SOLDE_INIT)
            .prixAchat(DEFAULT_PRIX_ACHAT)
            .prixVente(DEFAULT_PRIX_VENTE)
            .reference(DEFAULT_REFERENCE);
        return carburant;
    }

    @Before
    public void initTest() {
        carburant = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarburant() throws Exception {
        int databaseSizeBeforeCreate = carburantRepository.findAll().size();

        // Create the Carburant
        CarburantDTO carburantDTO = carburantMapper.toDto(carburant);
        restCarburantMockMvc.perform(post("/api/carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carburantDTO)))
            .andExpect(status().isCreated());

        // Validate the Carburant in the database
        List<Carburant> carburantList = carburantRepository.findAll();
        assertThat(carburantList).hasSize(databaseSizeBeforeCreate + 1);
        Carburant testCarburant = carburantList.get(carburantList.size() - 1);
        assertThat(testCarburant.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testCarburant.getSoldeInit()).isEqualTo(DEFAULT_SOLDE_INIT);
        assertThat(testCarburant.getPrixAchat()).isEqualTo(DEFAULT_PRIX_ACHAT);
        assertThat(testCarburant.getPrixVente()).isEqualTo(DEFAULT_PRIX_VENTE);
        assertThat(testCarburant.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createCarburantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carburantRepository.findAll().size();

        // Create the Carburant with an existing ID
        carburant.setId(1L);
        CarburantDTO carburantDTO = carburantMapper.toDto(carburant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarburantMockMvc.perform(post("/api/carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carburantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carburant in the database
        List<Carburant> carburantList = carburantRepository.findAll();
        assertThat(carburantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCarburants() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList
        restCarburantMockMvc.perform(get("/api/carburants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carburant.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].soldeInit").value(hasItem(DEFAULT_SOLDE_INIT.doubleValue())))
            .andExpect(jsonPath("$.[*].prixAchat").value(hasItem(DEFAULT_PRIX_ACHAT.doubleValue())))
            .andExpect(jsonPath("$.[*].prixVente").value(hasItem(DEFAULT_PRIX_VENTE.doubleValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.doubleValue())));
    }

    @Test
    @Transactional
    public void getCarburant() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get the carburant
        restCarburantMockMvc.perform(get("/api/carburants/{id}", carburant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carburant.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.soldeInit").value(DEFAULT_SOLDE_INIT.doubleValue()))
            .andExpect(jsonPath("$.prixAchat").value(DEFAULT_PRIX_ACHAT.doubleValue()))
            .andExpect(jsonPath("$.prixVente").value(DEFAULT_PRIX_VENTE.doubleValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarburant() throws Exception {
        // Get the carburant
        restCarburantMockMvc.perform(get("/api/carburants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarburant() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);
        int databaseSizeBeforeUpdate = carburantRepository.findAll().size();

        // Update the carburant
        Carburant updatedCarburant = carburantRepository.findOne(carburant.getId());
        // Disconnect from session so that the updates on updatedCarburant are not directly saved in db
        em.detach(updatedCarburant);
        updatedCarburant
            .designation(UPDATED_DESIGNATION)
            .soldeInit(UPDATED_SOLDE_INIT)
            .prixAchat(UPDATED_PRIX_ACHAT)
            .prixVente(UPDATED_PRIX_VENTE)
            .reference(UPDATED_REFERENCE);
        CarburantDTO carburantDTO = carburantMapper.toDto(updatedCarburant);

        restCarburantMockMvc.perform(put("/api/carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carburantDTO)))
            .andExpect(status().isOk());

        // Validate the Carburant in the database
        List<Carburant> carburantList = carburantRepository.findAll();
        assertThat(carburantList).hasSize(databaseSizeBeforeUpdate);
        Carburant testCarburant = carburantList.get(carburantList.size() - 1);
        assertThat(testCarburant.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCarburant.getSoldeInit()).isEqualTo(UPDATED_SOLDE_INIT);
        assertThat(testCarburant.getPrixAchat()).isEqualTo(UPDATED_PRIX_ACHAT);
        assertThat(testCarburant.getPrixVente()).isEqualTo(UPDATED_PRIX_VENTE);
        assertThat(testCarburant.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingCarburant() throws Exception {
        int databaseSizeBeforeUpdate = carburantRepository.findAll().size();

        // Create the Carburant
        CarburantDTO carburantDTO = carburantMapper.toDto(carburant);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarburantMockMvc.perform(put("/api/carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carburantDTO)))
            .andExpect(status().isCreated());

        // Validate the Carburant in the database
        List<Carburant> carburantList = carburantRepository.findAll();
        assertThat(carburantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarburant() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);
        int databaseSizeBeforeDelete = carburantRepository.findAll().size();

        // Get the carburant
        restCarburantMockMvc.perform(delete("/api/carburants/{id}", carburant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Carburant> carburantList = carburantRepository.findAll();
        assertThat(carburantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carburant.class);
        Carburant carburant1 = new Carburant();
        carburant1.setId(1L);
        Carburant carburant2 = new Carburant();
        carburant2.setId(carburant1.getId());
        assertThat(carburant1).isEqualTo(carburant2);
        carburant2.setId(2L);
        assertThat(carburant1).isNotEqualTo(carburant2);
        carburant1.setId(null);
        assertThat(carburant1).isNotEqualTo(carburant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarburantDTO.class);
        CarburantDTO carburantDTO1 = new CarburantDTO();
        carburantDTO1.setId(1L);
        CarburantDTO carburantDTO2 = new CarburantDTO();
        assertThat(carburantDTO1).isNotEqualTo(carburantDTO2);
        carburantDTO2.setId(carburantDTO1.getId());
        assertThat(carburantDTO1).isEqualTo(carburantDTO2);
        carburantDTO2.setId(2L);
        assertThat(carburantDTO1).isNotEqualTo(carburantDTO2);
        carburantDTO1.setId(null);
        assertThat(carburantDTO1).isNotEqualTo(carburantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carburantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carburantMapper.fromId(null)).isNull();
    }
}
