package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.SortieCarburant;
import com.epetrole.backend.repository.SortieCarburantRepository;
import com.epetrole.backend.service.SortieCarburantService;
import com.epetrole.backend.service.dto.SortieCarburantDTO;
import com.epetrole.backend.service.mapper.SortieCarburantMapper;
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
 * Test class for the SortieCarburantResource REST controller.
 *
 * @see SortieCarburantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class SortieCarburantResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_PRIX_TOTALHT = 1D;
    private static final Double UPDATED_PRIX_TOTALHT = 2D;

    private static final Double DEFAULT_QUANTITE = 1D;
    private static final Double UPDATED_QUANTITE = 2D;

    private static final Double DEFAULT_QUANTITE_I_NIT = 1D;
    private static final Double UPDATED_QUANTITE_I_NIT = 2D;

    private static final Double DEFAULT_QUANTITE_FINAL = 1D;
    private static final Double UPDATED_QUANTITE_FINAL = 2D;

    private static final Double DEFAULT_PRIX_TTC = 1D;
    private static final Double UPDATED_PRIX_TTC = 2D;

    @Autowired
    private SortieCarburantRepository sortieCarburantRepository;

    @Autowired
    private SortieCarburantMapper sortieCarburantMapper;

    @Autowired
    private SortieCarburantService sortieCarburantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSortieCarburantMockMvc;

    private SortieCarburant sortieCarburant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SortieCarburantResource sortieCarburantResource = new SortieCarburantResource(sortieCarburantService);
        this.restSortieCarburantMockMvc = MockMvcBuilders.standaloneSetup(sortieCarburantResource)
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
    public static SortieCarburant createEntity(EntityManager em) {
        SortieCarburant sortieCarburant = new SortieCarburant()
            .date(DEFAULT_DATE)
            .prixTotalht(DEFAULT_PRIX_TOTALHT)
            .quantite(DEFAULT_QUANTITE)
            .quantiteINit(DEFAULT_QUANTITE_I_NIT)
            .quantiteFinal(DEFAULT_QUANTITE_FINAL)
            .prixTTC(DEFAULT_PRIX_TTC);
        return sortieCarburant;
    }

    @Before
    public void initTest() {
        sortieCarburant = createEntity(em);
    }

    @Test
    @Transactional
    public void createSortieCarburant() throws Exception {
        int databaseSizeBeforeCreate = sortieCarburantRepository.findAll().size();

        // Create the SortieCarburant
        SortieCarburantDTO sortieCarburantDTO = sortieCarburantMapper.toDto(sortieCarburant);
        restSortieCarburantMockMvc.perform(post("/api/sortie-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieCarburantDTO)))
            .andExpect(status().isCreated());

        // Validate the SortieCarburant in the database
        List<SortieCarburant> sortieCarburantList = sortieCarburantRepository.findAll();
        assertThat(sortieCarburantList).hasSize(databaseSizeBeforeCreate + 1);
        SortieCarburant testSortieCarburant = sortieCarburantList.get(sortieCarburantList.size() - 1);
        assertThat(testSortieCarburant.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSortieCarburant.getPrixTotalht()).isEqualTo(DEFAULT_PRIX_TOTALHT);
        assertThat(testSortieCarburant.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testSortieCarburant.getQuantiteINit()).isEqualTo(DEFAULT_QUANTITE_I_NIT);
        assertThat(testSortieCarburant.getQuantiteFinal()).isEqualTo(DEFAULT_QUANTITE_FINAL);
        assertThat(testSortieCarburant.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
    }

    @Test
    @Transactional
    public void createSortieCarburantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sortieCarburantRepository.findAll().size();

        // Create the SortieCarburant with an existing ID
        sortieCarburant.setId(1L);
        SortieCarburantDTO sortieCarburantDTO = sortieCarburantMapper.toDto(sortieCarburant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSortieCarburantMockMvc.perform(post("/api/sortie-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieCarburantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SortieCarburant in the database
        List<SortieCarburant> sortieCarburantList = sortieCarburantRepository.findAll();
        assertThat(sortieCarburantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSortieCarburants() throws Exception {
        // Initialize the database
        sortieCarburantRepository.saveAndFlush(sortieCarburant);

        // Get all the sortieCarburantList
        restSortieCarburantMockMvc.perform(get("/api/sortie-carburants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sortieCarburant.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].prixTotalht").value(hasItem(DEFAULT_PRIX_TOTALHT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteINit").value(hasItem(DEFAULT_QUANTITE_I_NIT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteFinal").value(hasItem(DEFAULT_QUANTITE_FINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())));
    }

    @Test
    @Transactional
    public void getSortieCarburant() throws Exception {
        // Initialize the database
        sortieCarburantRepository.saveAndFlush(sortieCarburant);

        // Get the sortieCarburant
        restSortieCarburantMockMvc.perform(get("/api/sortie-carburants/{id}", sortieCarburant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sortieCarburant.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.prixTotalht").value(DEFAULT_PRIX_TOTALHT.doubleValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()))
            .andExpect(jsonPath("$.quantiteINit").value(DEFAULT_QUANTITE_I_NIT.doubleValue()))
            .andExpect(jsonPath("$.quantiteFinal").value(DEFAULT_QUANTITE_FINAL.doubleValue()))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSortieCarburant() throws Exception {
        // Get the sortieCarburant
        restSortieCarburantMockMvc.perform(get("/api/sortie-carburants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSortieCarburant() throws Exception {
        // Initialize the database
        sortieCarburantRepository.saveAndFlush(sortieCarburant);
        int databaseSizeBeforeUpdate = sortieCarburantRepository.findAll().size();

        // Update the sortieCarburant
        SortieCarburant updatedSortieCarburant = sortieCarburantRepository.findOne(sortieCarburant.getId());
        // Disconnect from session so that the updates on updatedSortieCarburant are not directly saved in db
        em.detach(updatedSortieCarburant);
        updatedSortieCarburant
            .date(UPDATED_DATE)
            .prixTotalht(UPDATED_PRIX_TOTALHT)
            .quantite(UPDATED_QUANTITE)
            .quantiteINit(UPDATED_QUANTITE_I_NIT)
            .quantiteFinal(UPDATED_QUANTITE_FINAL)
            .prixTTC(UPDATED_PRIX_TTC);
        SortieCarburantDTO sortieCarburantDTO = sortieCarburantMapper.toDto(updatedSortieCarburant);

        restSortieCarburantMockMvc.perform(put("/api/sortie-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieCarburantDTO)))
            .andExpect(status().isOk());

        // Validate the SortieCarburant in the database
        List<SortieCarburant> sortieCarburantList = sortieCarburantRepository.findAll();
        assertThat(sortieCarburantList).hasSize(databaseSizeBeforeUpdate);
        SortieCarburant testSortieCarburant = sortieCarburantList.get(sortieCarburantList.size() - 1);
        assertThat(testSortieCarburant.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSortieCarburant.getPrixTotalht()).isEqualTo(UPDATED_PRIX_TOTALHT);
        assertThat(testSortieCarburant.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testSortieCarburant.getQuantiteINit()).isEqualTo(UPDATED_QUANTITE_I_NIT);
        assertThat(testSortieCarburant.getQuantiteFinal()).isEqualTo(UPDATED_QUANTITE_FINAL);
        assertThat(testSortieCarburant.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingSortieCarburant() throws Exception {
        int databaseSizeBeforeUpdate = sortieCarburantRepository.findAll().size();

        // Create the SortieCarburant
        SortieCarburantDTO sortieCarburantDTO = sortieCarburantMapper.toDto(sortieCarburant);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSortieCarburantMockMvc.perform(put("/api/sortie-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieCarburantDTO)))
            .andExpect(status().isCreated());

        // Validate the SortieCarburant in the database
        List<SortieCarburant> sortieCarburantList = sortieCarburantRepository.findAll();
        assertThat(sortieCarburantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSortieCarburant() throws Exception {
        // Initialize the database
        sortieCarburantRepository.saveAndFlush(sortieCarburant);
        int databaseSizeBeforeDelete = sortieCarburantRepository.findAll().size();

        // Get the sortieCarburant
        restSortieCarburantMockMvc.perform(delete("/api/sortie-carburants/{id}", sortieCarburant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SortieCarburant> sortieCarburantList = sortieCarburantRepository.findAll();
        assertThat(sortieCarburantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SortieCarburant.class);
        SortieCarburant sortieCarburant1 = new SortieCarburant();
        sortieCarburant1.setId(1L);
        SortieCarburant sortieCarburant2 = new SortieCarburant();
        sortieCarburant2.setId(sortieCarburant1.getId());
        assertThat(sortieCarburant1).isEqualTo(sortieCarburant2);
        sortieCarburant2.setId(2L);
        assertThat(sortieCarburant1).isNotEqualTo(sortieCarburant2);
        sortieCarburant1.setId(null);
        assertThat(sortieCarburant1).isNotEqualTo(sortieCarburant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SortieCarburantDTO.class);
        SortieCarburantDTO sortieCarburantDTO1 = new SortieCarburantDTO();
        sortieCarburantDTO1.setId(1L);
        SortieCarburantDTO sortieCarburantDTO2 = new SortieCarburantDTO();
        assertThat(sortieCarburantDTO1).isNotEqualTo(sortieCarburantDTO2);
        sortieCarburantDTO2.setId(sortieCarburantDTO1.getId());
        assertThat(sortieCarburantDTO1).isEqualTo(sortieCarburantDTO2);
        sortieCarburantDTO2.setId(2L);
        assertThat(sortieCarburantDTO1).isNotEqualTo(sortieCarburantDTO2);
        sortieCarburantDTO1.setId(null);
        assertThat(sortieCarburantDTO1).isNotEqualTo(sortieCarburantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sortieCarburantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sortieCarburantMapper.fromId(null)).isNull();
    }
}
