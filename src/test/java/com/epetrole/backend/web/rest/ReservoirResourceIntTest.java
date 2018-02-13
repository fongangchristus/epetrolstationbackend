package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Reservoir;
import com.epetrole.backend.repository.ReservoirRepository;
import com.epetrole.backend.service.ReservoirService;
import com.epetrole.backend.service.dto.ReservoirDTO;
import com.epetrole.backend.service.mapper.ReservoirMapper;
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
 * Test class for the ReservoirResource REST controller.
 *
 * @see ReservoirResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class ReservoirResourceIntTest {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITE_INIT = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITE_INIT = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITE_MAX = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITE_MAX = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITE_ALERTE = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITE_ALERTE = "BBBBBBBBBB";

    @Autowired
    private ReservoirRepository reservoirRepository;

    @Autowired
    private ReservoirMapper reservoirMapper;

    @Autowired
    private ReservoirService reservoirService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReservoirMockMvc;

    private Reservoir reservoir;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReservoirResource reservoirResource = new ReservoirResource(reservoirService);
        this.restReservoirMockMvc = MockMvcBuilders.standaloneSetup(reservoirResource)
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
    public static Reservoir createEntity(EntityManager em) {
        Reservoir reservoir = new Reservoir()
            .designation(DEFAULT_DESIGNATION)
            .quantiteInit(DEFAULT_QUANTITE_INIT)
            .quantiteMax(DEFAULT_QUANTITE_MAX)
            .quantiteAlerte(DEFAULT_QUANTITE_ALERTE);
        return reservoir;
    }

    @Before
    public void initTest() {
        reservoir = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservoir() throws Exception {
        int databaseSizeBeforeCreate = reservoirRepository.findAll().size();

        // Create the Reservoir
        ReservoirDTO reservoirDTO = reservoirMapper.toDto(reservoir);
        restReservoirMockMvc.perform(post("/api/reservoirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservoirDTO)))
            .andExpect(status().isCreated());

        // Validate the Reservoir in the database
        List<Reservoir> reservoirList = reservoirRepository.findAll();
        assertThat(reservoirList).hasSize(databaseSizeBeforeCreate + 1);
        Reservoir testReservoir = reservoirList.get(reservoirList.size() - 1);
        assertThat(testReservoir.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testReservoir.getQuantiteInit()).isEqualTo(DEFAULT_QUANTITE_INIT);
        assertThat(testReservoir.getQuantiteMax()).isEqualTo(DEFAULT_QUANTITE_MAX);
        assertThat(testReservoir.getQuantiteAlerte()).isEqualTo(DEFAULT_QUANTITE_ALERTE);
    }

    @Test
    @Transactional
    public void createReservoirWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservoirRepository.findAll().size();

        // Create the Reservoir with an existing ID
        reservoir.setId(1L);
        ReservoirDTO reservoirDTO = reservoirMapper.toDto(reservoir);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservoirMockMvc.perform(post("/api/reservoirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservoirDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reservoir in the database
        List<Reservoir> reservoirList = reservoirRepository.findAll();
        assertThat(reservoirList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReservoirs() throws Exception {
        // Initialize the database
        reservoirRepository.saveAndFlush(reservoir);

        // Get all the reservoirList
        restReservoirMockMvc.perform(get("/api/reservoirs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservoir.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].quantiteInit").value(hasItem(DEFAULT_QUANTITE_INIT.toString())))
            .andExpect(jsonPath("$.[*].quantiteMax").value(hasItem(DEFAULT_QUANTITE_MAX.toString())))
            .andExpect(jsonPath("$.[*].quantiteAlerte").value(hasItem(DEFAULT_QUANTITE_ALERTE.toString())));
    }

    @Test
    @Transactional
    public void getReservoir() throws Exception {
        // Initialize the database
        reservoirRepository.saveAndFlush(reservoir);

        // Get the reservoir
        restReservoirMockMvc.perform(get("/api/reservoirs/{id}", reservoir.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reservoir.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.quantiteInit").value(DEFAULT_QUANTITE_INIT.toString()))
            .andExpect(jsonPath("$.quantiteMax").value(DEFAULT_QUANTITE_MAX.toString()))
            .andExpect(jsonPath("$.quantiteAlerte").value(DEFAULT_QUANTITE_ALERTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReservoir() throws Exception {
        // Get the reservoir
        restReservoirMockMvc.perform(get("/api/reservoirs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservoir() throws Exception {
        // Initialize the database
        reservoirRepository.saveAndFlush(reservoir);
        int databaseSizeBeforeUpdate = reservoirRepository.findAll().size();

        // Update the reservoir
        Reservoir updatedReservoir = reservoirRepository.findOne(reservoir.getId());
        // Disconnect from session so that the updates on updatedReservoir are not directly saved in db
        em.detach(updatedReservoir);
        updatedReservoir
            .designation(UPDATED_DESIGNATION)
            .quantiteInit(UPDATED_QUANTITE_INIT)
            .quantiteMax(UPDATED_QUANTITE_MAX)
            .quantiteAlerte(UPDATED_QUANTITE_ALERTE);
        ReservoirDTO reservoirDTO = reservoirMapper.toDto(updatedReservoir);

        restReservoirMockMvc.perform(put("/api/reservoirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservoirDTO)))
            .andExpect(status().isOk());

        // Validate the Reservoir in the database
        List<Reservoir> reservoirList = reservoirRepository.findAll();
        assertThat(reservoirList).hasSize(databaseSizeBeforeUpdate);
        Reservoir testReservoir = reservoirList.get(reservoirList.size() - 1);
        assertThat(testReservoir.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testReservoir.getQuantiteInit()).isEqualTo(UPDATED_QUANTITE_INIT);
        assertThat(testReservoir.getQuantiteMax()).isEqualTo(UPDATED_QUANTITE_MAX);
        assertThat(testReservoir.getQuantiteAlerte()).isEqualTo(UPDATED_QUANTITE_ALERTE);
    }

    @Test
    @Transactional
    public void updateNonExistingReservoir() throws Exception {
        int databaseSizeBeforeUpdate = reservoirRepository.findAll().size();

        // Create the Reservoir
        ReservoirDTO reservoirDTO = reservoirMapper.toDto(reservoir);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReservoirMockMvc.perform(put("/api/reservoirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservoirDTO)))
            .andExpect(status().isCreated());

        // Validate the Reservoir in the database
        List<Reservoir> reservoirList = reservoirRepository.findAll();
        assertThat(reservoirList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReservoir() throws Exception {
        // Initialize the database
        reservoirRepository.saveAndFlush(reservoir);
        int databaseSizeBeforeDelete = reservoirRepository.findAll().size();

        // Get the reservoir
        restReservoirMockMvc.perform(delete("/api/reservoirs/{id}", reservoir.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reservoir> reservoirList = reservoirRepository.findAll();
        assertThat(reservoirList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reservoir.class);
        Reservoir reservoir1 = new Reservoir();
        reservoir1.setId(1L);
        Reservoir reservoir2 = new Reservoir();
        reservoir2.setId(reservoir1.getId());
        assertThat(reservoir1).isEqualTo(reservoir2);
        reservoir2.setId(2L);
        assertThat(reservoir1).isNotEqualTo(reservoir2);
        reservoir1.setId(null);
        assertThat(reservoir1).isNotEqualTo(reservoir2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservoirDTO.class);
        ReservoirDTO reservoirDTO1 = new ReservoirDTO();
        reservoirDTO1.setId(1L);
        ReservoirDTO reservoirDTO2 = new ReservoirDTO();
        assertThat(reservoirDTO1).isNotEqualTo(reservoirDTO2);
        reservoirDTO2.setId(reservoirDTO1.getId());
        assertThat(reservoirDTO1).isEqualTo(reservoirDTO2);
        reservoirDTO2.setId(2L);
        assertThat(reservoirDTO1).isNotEqualTo(reservoirDTO2);
        reservoirDTO1.setId(null);
        assertThat(reservoirDTO1).isNotEqualTo(reservoirDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reservoirMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reservoirMapper.fromId(null)).isNull();
    }
}
