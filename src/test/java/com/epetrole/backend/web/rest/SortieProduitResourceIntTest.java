package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.SortieProduit;
import com.epetrole.backend.repository.SortieProduitRepository;
import com.epetrole.backend.service.SortieProduitService;
import com.epetrole.backend.service.dto.SortieProduitDTO;
import com.epetrole.backend.service.mapper.SortieProduitMapper;
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
 * Test class for the SortieProduitResource REST controller.
 *
 * @see SortieProduitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class SortieProduitResourceIntTest {

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
    private SortieProduitRepository sortieProduitRepository;

    @Autowired
    private SortieProduitMapper sortieProduitMapper;

    @Autowired
    private SortieProduitService sortieProduitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSortieProduitMockMvc;

    private SortieProduit sortieProduit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SortieProduitResource sortieProduitResource = new SortieProduitResource(sortieProduitService);
        this.restSortieProduitMockMvc = MockMvcBuilders.standaloneSetup(sortieProduitResource)
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
    public static SortieProduit createEntity(EntityManager em) {
        SortieProduit sortieProduit = new SortieProduit()
            .date(DEFAULT_DATE)
            .motif(DEFAULT_MOTIF)
            .prixTotalht(DEFAULT_PRIX_TOTALHT)
            .quantite(DEFAULT_QUANTITE)
            .prixTTC(DEFAULT_PRIX_TTC);
        return sortieProduit;
    }

    @Before
    public void initTest() {
        sortieProduit = createEntity(em);
    }

    @Test
    @Transactional
    public void createSortieProduit() throws Exception {
        int databaseSizeBeforeCreate = sortieProduitRepository.findAll().size();

        // Create the SortieProduit
        SortieProduitDTO sortieProduitDTO = sortieProduitMapper.toDto(sortieProduit);
        restSortieProduitMockMvc.perform(post("/api/sortie-produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieProduitDTO)))
            .andExpect(status().isCreated());

        // Validate the SortieProduit in the database
        List<SortieProduit> sortieProduitList = sortieProduitRepository.findAll();
        assertThat(sortieProduitList).hasSize(databaseSizeBeforeCreate + 1);
        SortieProduit testSortieProduit = sortieProduitList.get(sortieProduitList.size() - 1);
        assertThat(testSortieProduit.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSortieProduit.getMotif()).isEqualTo(DEFAULT_MOTIF);
        assertThat(testSortieProduit.getPrixTotalht()).isEqualTo(DEFAULT_PRIX_TOTALHT);
        assertThat(testSortieProduit.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testSortieProduit.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
    }

    @Test
    @Transactional
    public void createSortieProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sortieProduitRepository.findAll().size();

        // Create the SortieProduit with an existing ID
        sortieProduit.setId(1L);
        SortieProduitDTO sortieProduitDTO = sortieProduitMapper.toDto(sortieProduit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSortieProduitMockMvc.perform(post("/api/sortie-produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieProduitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SortieProduit in the database
        List<SortieProduit> sortieProduitList = sortieProduitRepository.findAll();
        assertThat(sortieProduitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSortieProduits() throws Exception {
        // Initialize the database
        sortieProduitRepository.saveAndFlush(sortieProduit);

        // Get all the sortieProduitList
        restSortieProduitMockMvc.perform(get("/api/sortie-produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sortieProduit.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].motif").value(hasItem(DEFAULT_MOTIF.toString())))
            .andExpect(jsonPath("$.[*].prixTotalht").value(hasItem(DEFAULT_PRIX_TOTALHT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())));
    }

    @Test
    @Transactional
    public void getSortieProduit() throws Exception {
        // Initialize the database
        sortieProduitRepository.saveAndFlush(sortieProduit);

        // Get the sortieProduit
        restSortieProduitMockMvc.perform(get("/api/sortie-produits/{id}", sortieProduit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sortieProduit.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.motif").value(DEFAULT_MOTIF.toString()))
            .andExpect(jsonPath("$.prixTotalht").value(DEFAULT_PRIX_TOTALHT.doubleValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSortieProduit() throws Exception {
        // Get the sortieProduit
        restSortieProduitMockMvc.perform(get("/api/sortie-produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSortieProduit() throws Exception {
        // Initialize the database
        sortieProduitRepository.saveAndFlush(sortieProduit);
        int databaseSizeBeforeUpdate = sortieProduitRepository.findAll().size();

        // Update the sortieProduit
        SortieProduit updatedSortieProduit = sortieProduitRepository.findOne(sortieProduit.getId());
        // Disconnect from session so that the updates on updatedSortieProduit are not directly saved in db
        em.detach(updatedSortieProduit);
        updatedSortieProduit
            .date(UPDATED_DATE)
            .motif(UPDATED_MOTIF)
            .prixTotalht(UPDATED_PRIX_TOTALHT)
            .quantite(UPDATED_QUANTITE)
            .prixTTC(UPDATED_PRIX_TTC);
        SortieProduitDTO sortieProduitDTO = sortieProduitMapper.toDto(updatedSortieProduit);

        restSortieProduitMockMvc.perform(put("/api/sortie-produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieProduitDTO)))
            .andExpect(status().isOk());

        // Validate the SortieProduit in the database
        List<SortieProduit> sortieProduitList = sortieProduitRepository.findAll();
        assertThat(sortieProduitList).hasSize(databaseSizeBeforeUpdate);
        SortieProduit testSortieProduit = sortieProduitList.get(sortieProduitList.size() - 1);
        assertThat(testSortieProduit.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSortieProduit.getMotif()).isEqualTo(UPDATED_MOTIF);
        assertThat(testSortieProduit.getPrixTotalht()).isEqualTo(UPDATED_PRIX_TOTALHT);
        assertThat(testSortieProduit.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testSortieProduit.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingSortieProduit() throws Exception {
        int databaseSizeBeforeUpdate = sortieProduitRepository.findAll().size();

        // Create the SortieProduit
        SortieProduitDTO sortieProduitDTO = sortieProduitMapper.toDto(sortieProduit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSortieProduitMockMvc.perform(put("/api/sortie-produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieProduitDTO)))
            .andExpect(status().isCreated());

        // Validate the SortieProduit in the database
        List<SortieProduit> sortieProduitList = sortieProduitRepository.findAll();
        assertThat(sortieProduitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSortieProduit() throws Exception {
        // Initialize the database
        sortieProduitRepository.saveAndFlush(sortieProduit);
        int databaseSizeBeforeDelete = sortieProduitRepository.findAll().size();

        // Get the sortieProduit
        restSortieProduitMockMvc.perform(delete("/api/sortie-produits/{id}", sortieProduit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SortieProduit> sortieProduitList = sortieProduitRepository.findAll();
        assertThat(sortieProduitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SortieProduit.class);
        SortieProduit sortieProduit1 = new SortieProduit();
        sortieProduit1.setId(1L);
        SortieProduit sortieProduit2 = new SortieProduit();
        sortieProduit2.setId(sortieProduit1.getId());
        assertThat(sortieProduit1).isEqualTo(sortieProduit2);
        sortieProduit2.setId(2L);
        assertThat(sortieProduit1).isNotEqualTo(sortieProduit2);
        sortieProduit1.setId(null);
        assertThat(sortieProduit1).isNotEqualTo(sortieProduit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SortieProduitDTO.class);
        SortieProduitDTO sortieProduitDTO1 = new SortieProduitDTO();
        sortieProduitDTO1.setId(1L);
        SortieProduitDTO sortieProduitDTO2 = new SortieProduitDTO();
        assertThat(sortieProduitDTO1).isNotEqualTo(sortieProduitDTO2);
        sortieProduitDTO2.setId(sortieProduitDTO1.getId());
        assertThat(sortieProduitDTO1).isEqualTo(sortieProduitDTO2);
        sortieProduitDTO2.setId(2L);
        assertThat(sortieProduitDTO1).isNotEqualTo(sortieProduitDTO2);
        sortieProduitDTO1.setId(null);
        assertThat(sortieProduitDTO1).isNotEqualTo(sortieProduitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sortieProduitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sortieProduitMapper.fromId(null)).isNull();
    }
}
