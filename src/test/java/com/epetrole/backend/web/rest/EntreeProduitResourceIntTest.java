package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.EntreeProduit;
import com.epetrole.backend.repository.EntreeProduitRepository;
import com.epetrole.backend.service.EntreeProduitService;
import com.epetrole.backend.service.dto.EntreeProduitDTO;
import com.epetrole.backend.service.mapper.EntreeProduitMapper;
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
 * Test class for the EntreeProduitResource REST controller.
 *
 * @see EntreeProduitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class EntreeProduitResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MOTIF = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_TOTALHT = 1D;
    private static final Double UPDATED_PRIX_TOTALHT = 2D;

    private static final Double DEFAULT_QUANTITE = 1D;
    private static final Double UPDATED_QUANTITE = 2D;

    private static final Double DEFAULT_PRIX_TTC = 1D;
    private static final Double UPDATED_PRIX_TTC = 2D;

    @Autowired
    private EntreeProduitRepository entreeProduitRepository;

    @Autowired
    private EntreeProduitMapper entreeProduitMapper;

    @Autowired
    private EntreeProduitService entreeProduitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntreeProduitMockMvc;

    private EntreeProduit entreeProduit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntreeProduitResource entreeProduitResource = new EntreeProduitResource(entreeProduitService);
        this.restEntreeProduitMockMvc = MockMvcBuilders.standaloneSetup(entreeProduitResource)
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
    public static EntreeProduit createEntity(EntityManager em) {
        EntreeProduit entreeProduit = new EntreeProduit()
            .date(DEFAULT_DATE)
            .motif(DEFAULT_MOTIF)
            .prixTotalht(DEFAULT_PRIX_TOTALHT)
            .quantite(DEFAULT_QUANTITE)
            .prixTTC(DEFAULT_PRIX_TTC);
        return entreeProduit;
    }

    @Before
    public void initTest() {
        entreeProduit = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntreeProduit() throws Exception {
        int databaseSizeBeforeCreate = entreeProduitRepository.findAll().size();

        // Create the EntreeProduit
        EntreeProduitDTO entreeProduitDTO = entreeProduitMapper.toDto(entreeProduit);
        restEntreeProduitMockMvc.perform(post("/api/entree-produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeProduitDTO)))
            .andExpect(status().isCreated());

        // Validate the EntreeProduit in the database
        List<EntreeProduit> entreeProduitList = entreeProduitRepository.findAll();
        assertThat(entreeProduitList).hasSize(databaseSizeBeforeCreate + 1);
        EntreeProduit testEntreeProduit = entreeProduitList.get(entreeProduitList.size() - 1);
        assertThat(testEntreeProduit.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEntreeProduit.getMotif()).isEqualTo(DEFAULT_MOTIF);
        assertThat(testEntreeProduit.getPrixTotalht()).isEqualTo(DEFAULT_PRIX_TOTALHT);
        assertThat(testEntreeProduit.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testEntreeProduit.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
    }

    @Test
    @Transactional
    public void createEntreeProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entreeProduitRepository.findAll().size();

        // Create the EntreeProduit with an existing ID
        entreeProduit.setId(1L);
        EntreeProduitDTO entreeProduitDTO = entreeProduitMapper.toDto(entreeProduit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntreeProduitMockMvc.perform(post("/api/entree-produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeProduitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntreeProduit in the database
        List<EntreeProduit> entreeProduitList = entreeProduitRepository.findAll();
        assertThat(entreeProduitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEntreeProduits() throws Exception {
        // Initialize the database
        entreeProduitRepository.saveAndFlush(entreeProduit);

        // Get all the entreeProduitList
        restEntreeProduitMockMvc.perform(get("/api/entree-produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entreeProduit.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].motif").value(hasItem(DEFAULT_MOTIF.toString())))
            .andExpect(jsonPath("$.[*].prixTotalht").value(hasItem(DEFAULT_PRIX_TOTALHT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())));
    }

    @Test
    @Transactional
    public void getEntreeProduit() throws Exception {
        // Initialize the database
        entreeProduitRepository.saveAndFlush(entreeProduit);

        // Get the entreeProduit
        restEntreeProduitMockMvc.perform(get("/api/entree-produits/{id}", entreeProduit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entreeProduit.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.motif").value(DEFAULT_MOTIF.toString()))
            .andExpect(jsonPath("$.prixTotalht").value(DEFAULT_PRIX_TOTALHT.doubleValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEntreeProduit() throws Exception {
        // Get the entreeProduit
        restEntreeProduitMockMvc.perform(get("/api/entree-produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntreeProduit() throws Exception {
        // Initialize the database
        entreeProduitRepository.saveAndFlush(entreeProduit);
        int databaseSizeBeforeUpdate = entreeProduitRepository.findAll().size();

        // Update the entreeProduit
        EntreeProduit updatedEntreeProduit = entreeProduitRepository.findOne(entreeProduit.getId());
        // Disconnect from session so that the updates on updatedEntreeProduit are not directly saved in db
        em.detach(updatedEntreeProduit);
        updatedEntreeProduit
            .date(UPDATED_DATE)
            .motif(UPDATED_MOTIF)
            .prixTotalht(UPDATED_PRIX_TOTALHT)
            .quantite(UPDATED_QUANTITE)
            .prixTTC(UPDATED_PRIX_TTC);
        EntreeProduitDTO entreeProduitDTO = entreeProduitMapper.toDto(updatedEntreeProduit);

        restEntreeProduitMockMvc.perform(put("/api/entree-produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeProduitDTO)))
            .andExpect(status().isOk());

        // Validate the EntreeProduit in the database
        List<EntreeProduit> entreeProduitList = entreeProduitRepository.findAll();
        assertThat(entreeProduitList).hasSize(databaseSizeBeforeUpdate);
        EntreeProduit testEntreeProduit = entreeProduitList.get(entreeProduitList.size() - 1);
        assertThat(testEntreeProduit.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEntreeProduit.getMotif()).isEqualTo(UPDATED_MOTIF);
        assertThat(testEntreeProduit.getPrixTotalht()).isEqualTo(UPDATED_PRIX_TOTALHT);
        assertThat(testEntreeProduit.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testEntreeProduit.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingEntreeProduit() throws Exception {
        int databaseSizeBeforeUpdate = entreeProduitRepository.findAll().size();

        // Create the EntreeProduit
        EntreeProduitDTO entreeProduitDTO = entreeProduitMapper.toDto(entreeProduit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntreeProduitMockMvc.perform(put("/api/entree-produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeProduitDTO)))
            .andExpect(status().isCreated());

        // Validate the EntreeProduit in the database
        List<EntreeProduit> entreeProduitList = entreeProduitRepository.findAll();
        assertThat(entreeProduitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntreeProduit() throws Exception {
        // Initialize the database
        entreeProduitRepository.saveAndFlush(entreeProduit);
        int databaseSizeBeforeDelete = entreeProduitRepository.findAll().size();

        // Get the entreeProduit
        restEntreeProduitMockMvc.perform(delete("/api/entree-produits/{id}", entreeProduit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EntreeProduit> entreeProduitList = entreeProduitRepository.findAll();
        assertThat(entreeProduitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntreeProduit.class);
        EntreeProduit entreeProduit1 = new EntreeProduit();
        entreeProduit1.setId(1L);
        EntreeProduit entreeProduit2 = new EntreeProduit();
        entreeProduit2.setId(entreeProduit1.getId());
        assertThat(entreeProduit1).isEqualTo(entreeProduit2);
        entreeProduit2.setId(2L);
        assertThat(entreeProduit1).isNotEqualTo(entreeProduit2);
        entreeProduit1.setId(null);
        assertThat(entreeProduit1).isNotEqualTo(entreeProduit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntreeProduitDTO.class);
        EntreeProduitDTO entreeProduitDTO1 = new EntreeProduitDTO();
        entreeProduitDTO1.setId(1L);
        EntreeProduitDTO entreeProduitDTO2 = new EntreeProduitDTO();
        assertThat(entreeProduitDTO1).isNotEqualTo(entreeProduitDTO2);
        entreeProduitDTO2.setId(entreeProduitDTO1.getId());
        assertThat(entreeProduitDTO1).isEqualTo(entreeProduitDTO2);
        entreeProduitDTO2.setId(2L);
        assertThat(entreeProduitDTO1).isNotEqualTo(entreeProduitDTO2);
        entreeProduitDTO1.setId(null);
        assertThat(entreeProduitDTO1).isNotEqualTo(entreeProduitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(entreeProduitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(entreeProduitMapper.fromId(null)).isNull();
    }
}
