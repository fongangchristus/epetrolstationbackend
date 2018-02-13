package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Depense;
import com.epetrole.backend.repository.DepenseRepository;
import com.epetrole.backend.service.DepenseService;
import com.epetrole.backend.service.dto.DepenseDTO;
import com.epetrole.backend.service.mapper.DepenseMapper;
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
 * Test class for the DepenseResource REST controller.
 *
 * @see DepenseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class DepenseResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    @Autowired
    private DepenseRepository depenseRepository;

    @Autowired
    private DepenseMapper depenseMapper;

    @Autowired
    private DepenseService depenseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDepenseMockMvc;

    private Depense depense;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepenseResource depenseResource = new DepenseResource(depenseService);
        this.restDepenseMockMvc = MockMvcBuilders.standaloneSetup(depenseResource)
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
    public static Depense createEntity(EntityManager em) {
        Depense depense = new Depense()
            .date(DEFAULT_DATE)
            .code(DEFAULT_CODE)
            .designation(DEFAULT_DESIGNATION)
            .description(DEFAULT_DESCRIPTION)
            .montant(DEFAULT_MONTANT);
        return depense;
    }

    @Before
    public void initTest() {
        depense = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepense() throws Exception {
        int databaseSizeBeforeCreate = depenseRepository.findAll().size();

        // Create the Depense
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);
        restDepenseMockMvc.perform(post("/api/depenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depenseDTO)))
            .andExpect(status().isCreated());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeCreate + 1);
        Depense testDepense = depenseList.get(depenseList.size() - 1);
        assertThat(testDepense.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDepense.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDepense.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testDepense.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDepense.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    public void createDepenseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depenseRepository.findAll().size();

        // Create the Depense with an existing ID
        depense.setId(1L);
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepenseMockMvc.perform(post("/api/depenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDepenses() throws Exception {
        // Initialize the database
        depenseRepository.saveAndFlush(depense);

        // Get all the depenseList
        restDepenseMockMvc.perform(get("/api/depenses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depense.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())));
    }

    @Test
    @Transactional
    public void getDepense() throws Exception {
        // Initialize the database
        depenseRepository.saveAndFlush(depense);

        // Get the depense
        restDepenseMockMvc.perform(get("/api/depenses/{id}", depense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depense.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDepense() throws Exception {
        // Get the depense
        restDepenseMockMvc.perform(get("/api/depenses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepense() throws Exception {
        // Initialize the database
        depenseRepository.saveAndFlush(depense);
        int databaseSizeBeforeUpdate = depenseRepository.findAll().size();

        // Update the depense
        Depense updatedDepense = depenseRepository.findOne(depense.getId());
        // Disconnect from session so that the updates on updatedDepense are not directly saved in db
        em.detach(updatedDepense);
        updatedDepense
            .date(UPDATED_DATE)
            .code(UPDATED_CODE)
            .designation(UPDATED_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .montant(UPDATED_MONTANT);
        DepenseDTO depenseDTO = depenseMapper.toDto(updatedDepense);

        restDepenseMockMvc.perform(put("/api/depenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depenseDTO)))
            .andExpect(status().isOk());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeUpdate);
        Depense testDepense = depenseList.get(depenseList.size() - 1);
        assertThat(testDepense.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDepense.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDepense.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testDepense.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDepense.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void updateNonExistingDepense() throws Exception {
        int databaseSizeBeforeUpdate = depenseRepository.findAll().size();

        // Create the Depense
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDepenseMockMvc.perform(put("/api/depenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depenseDTO)))
            .andExpect(status().isCreated());

        // Validate the Depense in the database
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDepense() throws Exception {
        // Initialize the database
        depenseRepository.saveAndFlush(depense);
        int databaseSizeBeforeDelete = depenseRepository.findAll().size();

        // Get the depense
        restDepenseMockMvc.perform(delete("/api/depenses/{id}", depense.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Depense> depenseList = depenseRepository.findAll();
        assertThat(depenseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Depense.class);
        Depense depense1 = new Depense();
        depense1.setId(1L);
        Depense depense2 = new Depense();
        depense2.setId(depense1.getId());
        assertThat(depense1).isEqualTo(depense2);
        depense2.setId(2L);
        assertThat(depense1).isNotEqualTo(depense2);
        depense1.setId(null);
        assertThat(depense1).isNotEqualTo(depense2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepenseDTO.class);
        DepenseDTO depenseDTO1 = new DepenseDTO();
        depenseDTO1.setId(1L);
        DepenseDTO depenseDTO2 = new DepenseDTO();
        assertThat(depenseDTO1).isNotEqualTo(depenseDTO2);
        depenseDTO2.setId(depenseDTO1.getId());
        assertThat(depenseDTO1).isEqualTo(depenseDTO2);
        depenseDTO2.setId(2L);
        assertThat(depenseDTO1).isNotEqualTo(depenseDTO2);
        depenseDTO1.setId(null);
        assertThat(depenseDTO1).isNotEqualTo(depenseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(depenseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(depenseMapper.fromId(null)).isNull();
    }
}
