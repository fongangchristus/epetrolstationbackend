package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.FraisRecue;
import com.epetrole.backend.repository.FraisRecueRepository;
import com.epetrole.backend.service.FraisRecueService;
import com.epetrole.backend.service.dto.FraisRecueDTO;
import com.epetrole.backend.service.mapper.FraisRecueMapper;
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
 * Test class for the FraisRecueResource REST controller.
 *
 * @see FraisRecueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class FraisRecueResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    @Autowired
    private FraisRecueRepository fraisRecueRepository;

    @Autowired
    private FraisRecueMapper fraisRecueMapper;

    @Autowired
    private FraisRecueService fraisRecueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFraisRecueMockMvc;

    private FraisRecue fraisRecue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FraisRecueResource fraisRecueResource = new FraisRecueResource(fraisRecueService);
        this.restFraisRecueMockMvc = MockMvcBuilders.standaloneSetup(fraisRecueResource)
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
    public static FraisRecue createEntity(EntityManager em) {
        FraisRecue fraisRecue = new FraisRecue()
            .date(DEFAULT_DATE)
            .montant(DEFAULT_MONTANT);
        return fraisRecue;
    }

    @Before
    public void initTest() {
        fraisRecue = createEntity(em);
    }

    @Test
    @Transactional
    public void createFraisRecue() throws Exception {
        int databaseSizeBeforeCreate = fraisRecueRepository.findAll().size();

        // Create the FraisRecue
        FraisRecueDTO fraisRecueDTO = fraisRecueMapper.toDto(fraisRecue);
        restFraisRecueMockMvc.perform(post("/api/frais-recues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraisRecueDTO)))
            .andExpect(status().isCreated());

        // Validate the FraisRecue in the database
        List<FraisRecue> fraisRecueList = fraisRecueRepository.findAll();
        assertThat(fraisRecueList).hasSize(databaseSizeBeforeCreate + 1);
        FraisRecue testFraisRecue = fraisRecueList.get(fraisRecueList.size() - 1);
        assertThat(testFraisRecue.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFraisRecue.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    public void createFraisRecueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fraisRecueRepository.findAll().size();

        // Create the FraisRecue with an existing ID
        fraisRecue.setId(1L);
        FraisRecueDTO fraisRecueDTO = fraisRecueMapper.toDto(fraisRecue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraisRecueMockMvc.perform(post("/api/frais-recues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraisRecueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FraisRecue in the database
        List<FraisRecue> fraisRecueList = fraisRecueRepository.findAll();
        assertThat(fraisRecueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFraisRecues() throws Exception {
        // Initialize the database
        fraisRecueRepository.saveAndFlush(fraisRecue);

        // Get all the fraisRecueList
        restFraisRecueMockMvc.perform(get("/api/frais-recues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fraisRecue.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())));
    }

    @Test
    @Transactional
    public void getFraisRecue() throws Exception {
        // Initialize the database
        fraisRecueRepository.saveAndFlush(fraisRecue);

        // Get the fraisRecue
        restFraisRecueMockMvc.perform(get("/api/frais-recues/{id}", fraisRecue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fraisRecue.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFraisRecue() throws Exception {
        // Get the fraisRecue
        restFraisRecueMockMvc.perform(get("/api/frais-recues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFraisRecue() throws Exception {
        // Initialize the database
        fraisRecueRepository.saveAndFlush(fraisRecue);
        int databaseSizeBeforeUpdate = fraisRecueRepository.findAll().size();

        // Update the fraisRecue
        FraisRecue updatedFraisRecue = fraisRecueRepository.findOne(fraisRecue.getId());
        // Disconnect from session so that the updates on updatedFraisRecue are not directly saved in db
        em.detach(updatedFraisRecue);
        updatedFraisRecue
            .date(UPDATED_DATE)
            .montant(UPDATED_MONTANT);
        FraisRecueDTO fraisRecueDTO = fraisRecueMapper.toDto(updatedFraisRecue);

        restFraisRecueMockMvc.perform(put("/api/frais-recues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraisRecueDTO)))
            .andExpect(status().isOk());

        // Validate the FraisRecue in the database
        List<FraisRecue> fraisRecueList = fraisRecueRepository.findAll();
        assertThat(fraisRecueList).hasSize(databaseSizeBeforeUpdate);
        FraisRecue testFraisRecue = fraisRecueList.get(fraisRecueList.size() - 1);
        assertThat(testFraisRecue.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFraisRecue.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void updateNonExistingFraisRecue() throws Exception {
        int databaseSizeBeforeUpdate = fraisRecueRepository.findAll().size();

        // Create the FraisRecue
        FraisRecueDTO fraisRecueDTO = fraisRecueMapper.toDto(fraisRecue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFraisRecueMockMvc.perform(put("/api/frais-recues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraisRecueDTO)))
            .andExpect(status().isCreated());

        // Validate the FraisRecue in the database
        List<FraisRecue> fraisRecueList = fraisRecueRepository.findAll();
        assertThat(fraisRecueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFraisRecue() throws Exception {
        // Initialize the database
        fraisRecueRepository.saveAndFlush(fraisRecue);
        int databaseSizeBeforeDelete = fraisRecueRepository.findAll().size();

        // Get the fraisRecue
        restFraisRecueMockMvc.perform(delete("/api/frais-recues/{id}", fraisRecue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FraisRecue> fraisRecueList = fraisRecueRepository.findAll();
        assertThat(fraisRecueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraisRecue.class);
        FraisRecue fraisRecue1 = new FraisRecue();
        fraisRecue1.setId(1L);
        FraisRecue fraisRecue2 = new FraisRecue();
        fraisRecue2.setId(fraisRecue1.getId());
        assertThat(fraisRecue1).isEqualTo(fraisRecue2);
        fraisRecue2.setId(2L);
        assertThat(fraisRecue1).isNotEqualTo(fraisRecue2);
        fraisRecue1.setId(null);
        assertThat(fraisRecue1).isNotEqualTo(fraisRecue2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraisRecueDTO.class);
        FraisRecueDTO fraisRecueDTO1 = new FraisRecueDTO();
        fraisRecueDTO1.setId(1L);
        FraisRecueDTO fraisRecueDTO2 = new FraisRecueDTO();
        assertThat(fraisRecueDTO1).isNotEqualTo(fraisRecueDTO2);
        fraisRecueDTO2.setId(fraisRecueDTO1.getId());
        assertThat(fraisRecueDTO1).isEqualTo(fraisRecueDTO2);
        fraisRecueDTO2.setId(2L);
        assertThat(fraisRecueDTO1).isNotEqualTo(fraisRecueDTO2);
        fraisRecueDTO1.setId(null);
        assertThat(fraisRecueDTO1).isNotEqualTo(fraisRecueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fraisRecueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fraisRecueMapper.fromId(null)).isNull();
    }
}
