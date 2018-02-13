package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Tva;
import com.epetrole.backend.repository.TvaRepository;
import com.epetrole.backend.service.TvaService;
import com.epetrole.backend.service.dto.TvaDTO;
import com.epetrole.backend.service.mapper.TvaMapper;
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
 * Test class for the TvaResource REST controller.
 *
 * @see TvaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class TvaResourceIntTest {

    private static final String DEFAULT_MODE_TVA = "AAAAAAAAAA";
    private static final String UPDATED_MODE_TVA = "BBBBBBBBBB";

    private static final String DEFAULT_TAUX_TVA = "AAAAAAAAAA";
    private static final String UPDATED_TAUX_TVA = "BBBBBBBBBB";

    @Autowired
    private TvaRepository tvaRepository;

    @Autowired
    private TvaMapper tvaMapper;

    @Autowired
    private TvaService tvaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTvaMockMvc;

    private Tva tva;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TvaResource tvaResource = new TvaResource(tvaService);
        this.restTvaMockMvc = MockMvcBuilders.standaloneSetup(tvaResource)
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
    public static Tva createEntity(EntityManager em) {
        Tva tva = new Tva()
            .modeTva(DEFAULT_MODE_TVA)
            .tauxTva(DEFAULT_TAUX_TVA);
        return tva;
    }

    @Before
    public void initTest() {
        tva = createEntity(em);
    }

    @Test
    @Transactional
    public void createTva() throws Exception {
        int databaseSizeBeforeCreate = tvaRepository.findAll().size();

        // Create the Tva
        TvaDTO tvaDTO = tvaMapper.toDto(tva);
        restTvaMockMvc.perform(post("/api/tvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeCreate + 1);
        Tva testTva = tvaList.get(tvaList.size() - 1);
        assertThat(testTva.getModeTva()).isEqualTo(DEFAULT_MODE_TVA);
        assertThat(testTva.getTauxTva()).isEqualTo(DEFAULT_TAUX_TVA);
    }

    @Test
    @Transactional
    public void createTvaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tvaRepository.findAll().size();

        // Create the Tva with an existing ID
        tva.setId(1L);
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTvaMockMvc.perform(post("/api/tvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTvas() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList
        restTvaMockMvc.perform(get("/api/tvas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tva.getId().intValue())))
            .andExpect(jsonPath("$.[*].modeTva").value(hasItem(DEFAULT_MODE_TVA.toString())))
            .andExpect(jsonPath("$.[*].tauxTva").value(hasItem(DEFAULT_TAUX_TVA.toString())));
    }

    @Test
    @Transactional
    public void getTva() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get the tva
        restTvaMockMvc.perform(get("/api/tvas/{id}", tva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tva.getId().intValue()))
            .andExpect(jsonPath("$.modeTva").value(DEFAULT_MODE_TVA.toString()))
            .andExpect(jsonPath("$.tauxTva").value(DEFAULT_TAUX_TVA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTva() throws Exception {
        // Get the tva
        restTvaMockMvc.perform(get("/api/tvas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTva() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);
        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();

        // Update the tva
        Tva updatedTva = tvaRepository.findOne(tva.getId());
        // Disconnect from session so that the updates on updatedTva are not directly saved in db
        em.detach(updatedTva);
        updatedTva
            .modeTva(UPDATED_MODE_TVA)
            .tauxTva(UPDATED_TAUX_TVA);
        TvaDTO tvaDTO = tvaMapper.toDto(updatedTva);

        restTvaMockMvc.perform(put("/api/tvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isOk());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);
        Tva testTva = tvaList.get(tvaList.size() - 1);
        assertThat(testTva.getModeTva()).isEqualTo(UPDATED_MODE_TVA);
        assertThat(testTva.getTauxTva()).isEqualTo(UPDATED_TAUX_TVA);
    }

    @Test
    @Transactional
    public void updateNonExistingTva() throws Exception {
        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();

        // Create the Tva
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTvaMockMvc.perform(put("/api/tvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTva() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);
        int databaseSizeBeforeDelete = tvaRepository.findAll().size();

        // Get the tva
        restTvaMockMvc.perform(delete("/api/tvas/{id}", tva.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tva.class);
        Tva tva1 = new Tva();
        tva1.setId(1L);
        Tva tva2 = new Tva();
        tva2.setId(tva1.getId());
        assertThat(tva1).isEqualTo(tva2);
        tva2.setId(2L);
        assertThat(tva1).isNotEqualTo(tva2);
        tva1.setId(null);
        assertThat(tva1).isNotEqualTo(tva2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TvaDTO.class);
        TvaDTO tvaDTO1 = new TvaDTO();
        tvaDTO1.setId(1L);
        TvaDTO tvaDTO2 = new TvaDTO();
        assertThat(tvaDTO1).isNotEqualTo(tvaDTO2);
        tvaDTO2.setId(tvaDTO1.getId());
        assertThat(tvaDTO1).isEqualTo(tvaDTO2);
        tvaDTO2.setId(2L);
        assertThat(tvaDTO1).isNotEqualTo(tvaDTO2);
        tvaDTO1.setId(null);
        assertThat(tvaDTO1).isNotEqualTo(tvaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tvaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tvaMapper.fromId(null)).isNull();
    }
}
