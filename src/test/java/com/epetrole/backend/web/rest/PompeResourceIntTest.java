package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Pompe;
import com.epetrole.backend.repository.PompeRepository;
import com.epetrole.backend.service.PompeService;
import com.epetrole.backend.service.dto.PompeDTO;
import com.epetrole.backend.service.mapper.PompeMapper;
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
 * Test class for the PompeResource REST controller.
 *
 * @see PompeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class PompeResourceIntTest {

    private static final String DEFAULT_NB_CHIFFRE = "AAAAAAAAAA";
    private static final String UPDATED_NB_CHIFFRE = "BBBBBBBBBB";

    private static final String DEFAULT_C_INIT = "AAAAAAAAAA";
    private static final String UPDATED_C_INIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MELANGE = false;
    private static final Boolean UPDATED_MELANGE = true;

    @Autowired
    private PompeRepository pompeRepository;

    @Autowired
    private PompeMapper pompeMapper;

    @Autowired
    private PompeService pompeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPompeMockMvc;

    private Pompe pompe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PompeResource pompeResource = new PompeResource(pompeService);
        this.restPompeMockMvc = MockMvcBuilders.standaloneSetup(pompeResource)
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
    public static Pompe createEntity(EntityManager em) {
        Pompe pompe = new Pompe()
            .nbChiffre(DEFAULT_NB_CHIFFRE)
            .cInit(DEFAULT_C_INIT)
            .melange(DEFAULT_MELANGE);
        return pompe;
    }

    @Before
    public void initTest() {
        pompe = createEntity(em);
    }

    @Test
    @Transactional
    public void createPompe() throws Exception {
        int databaseSizeBeforeCreate = pompeRepository.findAll().size();

        // Create the Pompe
        PompeDTO pompeDTO = pompeMapper.toDto(pompe);
        restPompeMockMvc.perform(post("/api/pompes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pompeDTO)))
            .andExpect(status().isCreated());

        // Validate the Pompe in the database
        List<Pompe> pompeList = pompeRepository.findAll();
        assertThat(pompeList).hasSize(databaseSizeBeforeCreate + 1);
        Pompe testPompe = pompeList.get(pompeList.size() - 1);
        assertThat(testPompe.getNbChiffre()).isEqualTo(DEFAULT_NB_CHIFFRE);
        assertThat(testPompe.getcInit()).isEqualTo(DEFAULT_C_INIT);
        assertThat(testPompe.isMelange()).isEqualTo(DEFAULT_MELANGE);
    }

    @Test
    @Transactional
    public void createPompeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pompeRepository.findAll().size();

        // Create the Pompe with an existing ID
        pompe.setId(1L);
        PompeDTO pompeDTO = pompeMapper.toDto(pompe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPompeMockMvc.perform(post("/api/pompes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pompeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pompe in the database
        List<Pompe> pompeList = pompeRepository.findAll();
        assertThat(pompeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPompes() throws Exception {
        // Initialize the database
        pompeRepository.saveAndFlush(pompe);

        // Get all the pompeList
        restPompeMockMvc.perform(get("/api/pompes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pompe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbChiffre").value(hasItem(DEFAULT_NB_CHIFFRE.toString())))
            .andExpect(jsonPath("$.[*].cInit").value(hasItem(DEFAULT_C_INIT.toString())))
            .andExpect(jsonPath("$.[*].melange").value(hasItem(DEFAULT_MELANGE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPompe() throws Exception {
        // Initialize the database
        pompeRepository.saveAndFlush(pompe);

        // Get the pompe
        restPompeMockMvc.perform(get("/api/pompes/{id}", pompe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pompe.getId().intValue()))
            .andExpect(jsonPath("$.nbChiffre").value(DEFAULT_NB_CHIFFRE.toString()))
            .andExpect(jsonPath("$.cInit").value(DEFAULT_C_INIT.toString()))
            .andExpect(jsonPath("$.melange").value(DEFAULT_MELANGE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPompe() throws Exception {
        // Get the pompe
        restPompeMockMvc.perform(get("/api/pompes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePompe() throws Exception {
        // Initialize the database
        pompeRepository.saveAndFlush(pompe);
        int databaseSizeBeforeUpdate = pompeRepository.findAll().size();

        // Update the pompe
        Pompe updatedPompe = pompeRepository.findOne(pompe.getId());
        // Disconnect from session so that the updates on updatedPompe are not directly saved in db
        em.detach(updatedPompe);
        updatedPompe
            .nbChiffre(UPDATED_NB_CHIFFRE)
            .cInit(UPDATED_C_INIT)
            .melange(UPDATED_MELANGE);
        PompeDTO pompeDTO = pompeMapper.toDto(updatedPompe);

        restPompeMockMvc.perform(put("/api/pompes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pompeDTO)))
            .andExpect(status().isOk());

        // Validate the Pompe in the database
        List<Pompe> pompeList = pompeRepository.findAll();
        assertThat(pompeList).hasSize(databaseSizeBeforeUpdate);
        Pompe testPompe = pompeList.get(pompeList.size() - 1);
        assertThat(testPompe.getNbChiffre()).isEqualTo(UPDATED_NB_CHIFFRE);
        assertThat(testPompe.getcInit()).isEqualTo(UPDATED_C_INIT);
        assertThat(testPompe.isMelange()).isEqualTo(UPDATED_MELANGE);
    }

    @Test
    @Transactional
    public void updateNonExistingPompe() throws Exception {
        int databaseSizeBeforeUpdate = pompeRepository.findAll().size();

        // Create the Pompe
        PompeDTO pompeDTO = pompeMapper.toDto(pompe);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPompeMockMvc.perform(put("/api/pompes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pompeDTO)))
            .andExpect(status().isCreated());

        // Validate the Pompe in the database
        List<Pompe> pompeList = pompeRepository.findAll();
        assertThat(pompeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePompe() throws Exception {
        // Initialize the database
        pompeRepository.saveAndFlush(pompe);
        int databaseSizeBeforeDelete = pompeRepository.findAll().size();

        // Get the pompe
        restPompeMockMvc.perform(delete("/api/pompes/{id}", pompe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pompe> pompeList = pompeRepository.findAll();
        assertThat(pompeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pompe.class);
        Pompe pompe1 = new Pompe();
        pompe1.setId(1L);
        Pompe pompe2 = new Pompe();
        pompe2.setId(pompe1.getId());
        assertThat(pompe1).isEqualTo(pompe2);
        pompe2.setId(2L);
        assertThat(pompe1).isNotEqualTo(pompe2);
        pompe1.setId(null);
        assertThat(pompe1).isNotEqualTo(pompe2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PompeDTO.class);
        PompeDTO pompeDTO1 = new PompeDTO();
        pompeDTO1.setId(1L);
        PompeDTO pompeDTO2 = new PompeDTO();
        assertThat(pompeDTO1).isNotEqualTo(pompeDTO2);
        pompeDTO2.setId(pompeDTO1.getId());
        assertThat(pompeDTO1).isEqualTo(pompeDTO2);
        pompeDTO2.setId(2L);
        assertThat(pompeDTO1).isNotEqualTo(pompeDTO2);
        pompeDTO1.setId(null);
        assertThat(pompeDTO1).isNotEqualTo(pompeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pompeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pompeMapper.fromId(null)).isNull();
    }
}
