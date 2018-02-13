package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.EntreeCarburant;
import com.epetrole.backend.repository.EntreeCarburantRepository;
import com.epetrole.backend.service.EntreeCarburantService;
import com.epetrole.backend.service.dto.EntreeCarburantDTO;
import com.epetrole.backend.service.mapper.EntreeCarburantMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.epetrole.backend.web.rest.TestUtil.sameInstant;
import static com.epetrole.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EntreeCarburantResource REST controller.
 *
 * @see EntreeCarburantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class EntreeCarburantResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_PRIX_TOTALHT = 1D;
    private static final Double UPDATED_PRIX_TOTALHT = 2D;

    private static final Double DEFAULT_QUANTITE = 1D;
    private static final Double UPDATED_QUANTITE = 2D;

    private static final Double DEFAULT_PRIX_TTC = 1D;
    private static final Double UPDATED_PRIX_TTC = 2D;

    @Autowired
    private EntreeCarburantRepository entreeCarburantRepository;

    @Autowired
    private EntreeCarburantMapper entreeCarburantMapper;

    @Autowired
    private EntreeCarburantService entreeCarburantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntreeCarburantMockMvc;

    private EntreeCarburant entreeCarburant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntreeCarburantResource entreeCarburantResource = new EntreeCarburantResource(entreeCarburantService);
        this.restEntreeCarburantMockMvc = MockMvcBuilders.standaloneSetup(entreeCarburantResource)
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
    public static EntreeCarburant createEntity(EntityManager em) {
        EntreeCarburant entreeCarburant = new EntreeCarburant()
            .date(DEFAULT_DATE)
            .prixTotalht(DEFAULT_PRIX_TOTALHT)
            .quantite(DEFAULT_QUANTITE)
            .prixTTC(DEFAULT_PRIX_TTC);
        return entreeCarburant;
    }

    @Before
    public void initTest() {
        entreeCarburant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntreeCarburant() throws Exception {
        int databaseSizeBeforeCreate = entreeCarburantRepository.findAll().size();

        // Create the EntreeCarburant
        EntreeCarburantDTO entreeCarburantDTO = entreeCarburantMapper.toDto(entreeCarburant);
        restEntreeCarburantMockMvc.perform(post("/api/entree-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeCarburantDTO)))
            .andExpect(status().isCreated());

        // Validate the EntreeCarburant in the database
        List<EntreeCarburant> entreeCarburantList = entreeCarburantRepository.findAll();
        assertThat(entreeCarburantList).hasSize(databaseSizeBeforeCreate + 1);
        EntreeCarburant testEntreeCarburant = entreeCarburantList.get(entreeCarburantList.size() - 1);
        assertThat(testEntreeCarburant.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEntreeCarburant.getPrixTotalht()).isEqualTo(DEFAULT_PRIX_TOTALHT);
        assertThat(testEntreeCarburant.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testEntreeCarburant.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
    }

    @Test
    @Transactional
    public void createEntreeCarburantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entreeCarburantRepository.findAll().size();

        // Create the EntreeCarburant with an existing ID
        entreeCarburant.setId(1L);
        EntreeCarburantDTO entreeCarburantDTO = entreeCarburantMapper.toDto(entreeCarburant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntreeCarburantMockMvc.perform(post("/api/entree-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeCarburantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntreeCarburant in the database
        List<EntreeCarburant> entreeCarburantList = entreeCarburantRepository.findAll();
        assertThat(entreeCarburantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEntreeCarburants() throws Exception {
        // Initialize the database
        entreeCarburantRepository.saveAndFlush(entreeCarburant);

        // Get all the entreeCarburantList
        restEntreeCarburantMockMvc.perform(get("/api/entree-carburants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entreeCarburant.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].prixTotalht").value(hasItem(DEFAULT_PRIX_TOTALHT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())));
    }

    @Test
    @Transactional
    public void getEntreeCarburant() throws Exception {
        // Initialize the database
        entreeCarburantRepository.saveAndFlush(entreeCarburant);

        // Get the entreeCarburant
        restEntreeCarburantMockMvc.perform(get("/api/entree-carburants/{id}", entreeCarburant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entreeCarburant.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.prixTotalht").value(DEFAULT_PRIX_TOTALHT.doubleValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEntreeCarburant() throws Exception {
        // Get the entreeCarburant
        restEntreeCarburantMockMvc.perform(get("/api/entree-carburants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntreeCarburant() throws Exception {
        // Initialize the database
        entreeCarburantRepository.saveAndFlush(entreeCarburant);
        int databaseSizeBeforeUpdate = entreeCarburantRepository.findAll().size();

        // Update the entreeCarburant
        EntreeCarburant updatedEntreeCarburant = entreeCarburantRepository.findOne(entreeCarburant.getId());
        // Disconnect from session so that the updates on updatedEntreeCarburant are not directly saved in db
        em.detach(updatedEntreeCarburant);
        updatedEntreeCarburant
            .date(UPDATED_DATE)
            .prixTotalht(UPDATED_PRIX_TOTALHT)
            .quantite(UPDATED_QUANTITE)
            .prixTTC(UPDATED_PRIX_TTC);
        EntreeCarburantDTO entreeCarburantDTO = entreeCarburantMapper.toDto(updatedEntreeCarburant);

        restEntreeCarburantMockMvc.perform(put("/api/entree-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeCarburantDTO)))
            .andExpect(status().isOk());

        // Validate the EntreeCarburant in the database
        List<EntreeCarburant> entreeCarburantList = entreeCarburantRepository.findAll();
        assertThat(entreeCarburantList).hasSize(databaseSizeBeforeUpdate);
        EntreeCarburant testEntreeCarburant = entreeCarburantList.get(entreeCarburantList.size() - 1);
        assertThat(testEntreeCarburant.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEntreeCarburant.getPrixTotalht()).isEqualTo(UPDATED_PRIX_TOTALHT);
        assertThat(testEntreeCarburant.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testEntreeCarburant.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingEntreeCarburant() throws Exception {
        int databaseSizeBeforeUpdate = entreeCarburantRepository.findAll().size();

        // Create the EntreeCarburant
        EntreeCarburantDTO entreeCarburantDTO = entreeCarburantMapper.toDto(entreeCarburant);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntreeCarburantMockMvc.perform(put("/api/entree-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeCarburantDTO)))
            .andExpect(status().isCreated());

        // Validate the EntreeCarburant in the database
        List<EntreeCarburant> entreeCarburantList = entreeCarburantRepository.findAll();
        assertThat(entreeCarburantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntreeCarburant() throws Exception {
        // Initialize the database
        entreeCarburantRepository.saveAndFlush(entreeCarburant);
        int databaseSizeBeforeDelete = entreeCarburantRepository.findAll().size();

        // Get the entreeCarburant
        restEntreeCarburantMockMvc.perform(delete("/api/entree-carburants/{id}", entreeCarburant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EntreeCarburant> entreeCarburantList = entreeCarburantRepository.findAll();
        assertThat(entreeCarburantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntreeCarburant.class);
        EntreeCarburant entreeCarburant1 = new EntreeCarburant();
        entreeCarburant1.setId(1L);
        EntreeCarburant entreeCarburant2 = new EntreeCarburant();
        entreeCarburant2.setId(entreeCarburant1.getId());
        assertThat(entreeCarburant1).isEqualTo(entreeCarburant2);
        entreeCarburant2.setId(2L);
        assertThat(entreeCarburant1).isNotEqualTo(entreeCarburant2);
        entreeCarburant1.setId(null);
        assertThat(entreeCarburant1).isNotEqualTo(entreeCarburant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntreeCarburantDTO.class);
        EntreeCarburantDTO entreeCarburantDTO1 = new EntreeCarburantDTO();
        entreeCarburantDTO1.setId(1L);
        EntreeCarburantDTO entreeCarburantDTO2 = new EntreeCarburantDTO();
        assertThat(entreeCarburantDTO1).isNotEqualTo(entreeCarburantDTO2);
        entreeCarburantDTO2.setId(entreeCarburantDTO1.getId());
        assertThat(entreeCarburantDTO1).isEqualTo(entreeCarburantDTO2);
        entreeCarburantDTO2.setId(2L);
        assertThat(entreeCarburantDTO1).isNotEqualTo(entreeCarburantDTO2);
        entreeCarburantDTO1.setId(null);
        assertThat(entreeCarburantDTO1).isNotEqualTo(entreeCarburantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(entreeCarburantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(entreeCarburantMapper.fromId(null)).isNull();
    }
}
