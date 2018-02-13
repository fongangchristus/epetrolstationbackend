package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.TauxMelange;
import com.epetrole.backend.repository.TauxMelangeRepository;
import com.epetrole.backend.service.TauxMelangeService;
import com.epetrole.backend.service.dto.TauxMelangeDTO;
import com.epetrole.backend.service.mapper.TauxMelangeMapper;
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
 * Test class for the TauxMelangeResource REST controller.
 *
 * @see TauxMelangeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class TauxMelangeResourceIntTest {

    private static final Double DEFAULT_TAUX_MELANGE = 1D;
    private static final Double UPDATED_TAUX_MELANGE = 2D;

    private static final Double DEFAULT_PRIX_MELANGE = 1D;
    private static final Double UPDATED_PRIX_MELANGE = 2D;

    private static final Boolean DEFAULT_TAUX_EN_COURS = false;
    private static final Boolean UPDATED_TAUX_EN_COURS = true;

    @Autowired
    private TauxMelangeRepository tauxMelangeRepository;

    @Autowired
    private TauxMelangeMapper tauxMelangeMapper;

    @Autowired
    private TauxMelangeService tauxMelangeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTauxMelangeMockMvc;

    private TauxMelange tauxMelange;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TauxMelangeResource tauxMelangeResource = new TauxMelangeResource(tauxMelangeService);
        this.restTauxMelangeMockMvc = MockMvcBuilders.standaloneSetup(tauxMelangeResource)
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
    public static TauxMelange createEntity(EntityManager em) {
        TauxMelange tauxMelange = new TauxMelange()
            .tauxMelange(DEFAULT_TAUX_MELANGE)
            .prixMelange(DEFAULT_PRIX_MELANGE)
            .tauxEnCours(DEFAULT_TAUX_EN_COURS);
        return tauxMelange;
    }

    @Before
    public void initTest() {
        tauxMelange = createEntity(em);
    }

    @Test
    @Transactional
    public void createTauxMelange() throws Exception {
        int databaseSizeBeforeCreate = tauxMelangeRepository.findAll().size();

        // Create the TauxMelange
        TauxMelangeDTO tauxMelangeDTO = tauxMelangeMapper.toDto(tauxMelange);
        restTauxMelangeMockMvc.perform(post("/api/taux-melanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tauxMelangeDTO)))
            .andExpect(status().isCreated());

        // Validate the TauxMelange in the database
        List<TauxMelange> tauxMelangeList = tauxMelangeRepository.findAll();
        assertThat(tauxMelangeList).hasSize(databaseSizeBeforeCreate + 1);
        TauxMelange testTauxMelange = tauxMelangeList.get(tauxMelangeList.size() - 1);
        assertThat(testTauxMelange.getTauxMelange()).isEqualTo(DEFAULT_TAUX_MELANGE);
        assertThat(testTauxMelange.getPrixMelange()).isEqualTo(DEFAULT_PRIX_MELANGE);
        assertThat(testTauxMelange.isTauxEnCours()).isEqualTo(DEFAULT_TAUX_EN_COURS);
    }

    @Test
    @Transactional
    public void createTauxMelangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tauxMelangeRepository.findAll().size();

        // Create the TauxMelange with an existing ID
        tauxMelange.setId(1L);
        TauxMelangeDTO tauxMelangeDTO = tauxMelangeMapper.toDto(tauxMelange);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTauxMelangeMockMvc.perform(post("/api/taux-melanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tauxMelangeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TauxMelange in the database
        List<TauxMelange> tauxMelangeList = tauxMelangeRepository.findAll();
        assertThat(tauxMelangeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTauxMelanges() throws Exception {
        // Initialize the database
        tauxMelangeRepository.saveAndFlush(tauxMelange);

        // Get all the tauxMelangeList
        restTauxMelangeMockMvc.perform(get("/api/taux-melanges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tauxMelange.getId().intValue())))
            .andExpect(jsonPath("$.[*].tauxMelange").value(hasItem(DEFAULT_TAUX_MELANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixMelange").value(hasItem(DEFAULT_PRIX_MELANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].tauxEnCours").value(hasItem(DEFAULT_TAUX_EN_COURS.booleanValue())));
    }

    @Test
    @Transactional
    public void getTauxMelange() throws Exception {
        // Initialize the database
        tauxMelangeRepository.saveAndFlush(tauxMelange);

        // Get the tauxMelange
        restTauxMelangeMockMvc.perform(get("/api/taux-melanges/{id}", tauxMelange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tauxMelange.getId().intValue()))
            .andExpect(jsonPath("$.tauxMelange").value(DEFAULT_TAUX_MELANGE.doubleValue()))
            .andExpect(jsonPath("$.prixMelange").value(DEFAULT_PRIX_MELANGE.doubleValue()))
            .andExpect(jsonPath("$.tauxEnCours").value(DEFAULT_TAUX_EN_COURS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTauxMelange() throws Exception {
        // Get the tauxMelange
        restTauxMelangeMockMvc.perform(get("/api/taux-melanges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTauxMelange() throws Exception {
        // Initialize the database
        tauxMelangeRepository.saveAndFlush(tauxMelange);
        int databaseSizeBeforeUpdate = tauxMelangeRepository.findAll().size();

        // Update the tauxMelange
        TauxMelange updatedTauxMelange = tauxMelangeRepository.findOne(tauxMelange.getId());
        // Disconnect from session so that the updates on updatedTauxMelange are not directly saved in db
        em.detach(updatedTauxMelange);
        updatedTauxMelange
            .tauxMelange(UPDATED_TAUX_MELANGE)
            .prixMelange(UPDATED_PRIX_MELANGE)
            .tauxEnCours(UPDATED_TAUX_EN_COURS);
        TauxMelangeDTO tauxMelangeDTO = tauxMelangeMapper.toDto(updatedTauxMelange);

        restTauxMelangeMockMvc.perform(put("/api/taux-melanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tauxMelangeDTO)))
            .andExpect(status().isOk());

        // Validate the TauxMelange in the database
        List<TauxMelange> tauxMelangeList = tauxMelangeRepository.findAll();
        assertThat(tauxMelangeList).hasSize(databaseSizeBeforeUpdate);
        TauxMelange testTauxMelange = tauxMelangeList.get(tauxMelangeList.size() - 1);
        assertThat(testTauxMelange.getTauxMelange()).isEqualTo(UPDATED_TAUX_MELANGE);
        assertThat(testTauxMelange.getPrixMelange()).isEqualTo(UPDATED_PRIX_MELANGE);
        assertThat(testTauxMelange.isTauxEnCours()).isEqualTo(UPDATED_TAUX_EN_COURS);
    }

    @Test
    @Transactional
    public void updateNonExistingTauxMelange() throws Exception {
        int databaseSizeBeforeUpdate = tauxMelangeRepository.findAll().size();

        // Create the TauxMelange
        TauxMelangeDTO tauxMelangeDTO = tauxMelangeMapper.toDto(tauxMelange);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTauxMelangeMockMvc.perform(put("/api/taux-melanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tauxMelangeDTO)))
            .andExpect(status().isCreated());

        // Validate the TauxMelange in the database
        List<TauxMelange> tauxMelangeList = tauxMelangeRepository.findAll();
        assertThat(tauxMelangeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTauxMelange() throws Exception {
        // Initialize the database
        tauxMelangeRepository.saveAndFlush(tauxMelange);
        int databaseSizeBeforeDelete = tauxMelangeRepository.findAll().size();

        // Get the tauxMelange
        restTauxMelangeMockMvc.perform(delete("/api/taux-melanges/{id}", tauxMelange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TauxMelange> tauxMelangeList = tauxMelangeRepository.findAll();
        assertThat(tauxMelangeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TauxMelange.class);
        TauxMelange tauxMelange1 = new TauxMelange();
        tauxMelange1.setId(1L);
        TauxMelange tauxMelange2 = new TauxMelange();
        tauxMelange2.setId(tauxMelange1.getId());
        assertThat(tauxMelange1).isEqualTo(tauxMelange2);
        tauxMelange2.setId(2L);
        assertThat(tauxMelange1).isNotEqualTo(tauxMelange2);
        tauxMelange1.setId(null);
        assertThat(tauxMelange1).isNotEqualTo(tauxMelange2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TauxMelangeDTO.class);
        TauxMelangeDTO tauxMelangeDTO1 = new TauxMelangeDTO();
        tauxMelangeDTO1.setId(1L);
        TauxMelangeDTO tauxMelangeDTO2 = new TauxMelangeDTO();
        assertThat(tauxMelangeDTO1).isNotEqualTo(tauxMelangeDTO2);
        tauxMelangeDTO2.setId(tauxMelangeDTO1.getId());
        assertThat(tauxMelangeDTO1).isEqualTo(tauxMelangeDTO2);
        tauxMelangeDTO2.setId(2L);
        assertThat(tauxMelangeDTO1).isNotEqualTo(tauxMelangeDTO2);
        tauxMelangeDTO1.setId(null);
        assertThat(tauxMelangeDTO1).isNotEqualTo(tauxMelangeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tauxMelangeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tauxMelangeMapper.fromId(null)).isNull();
    }
}
