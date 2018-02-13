package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.SortieMyservice;
import com.epetrole.backend.repository.SortieMyserviceRepository;
import com.epetrole.backend.service.SortieMyserviceService;
import com.epetrole.backend.service.dto.SortieMyserviceDTO;
import com.epetrole.backend.service.mapper.SortieMyserviceMapper;
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
 * Test class for the SortieMyserviceResource REST controller.
 *
 * @see SortieMyserviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class SortieMyserviceResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_PRIX_TOTALHT = 1D;
    private static final Double UPDATED_PRIX_TOTALHT = 2D;

    private static final Double DEFAULT_PRIX_TTC = 1D;
    private static final Double UPDATED_PRIX_TTC = 2D;

    @Autowired
    private SortieMyserviceRepository sortieMyserviceRepository;

    @Autowired
    private SortieMyserviceMapper sortieMyserviceMapper;

    @Autowired
    private SortieMyserviceService sortieMyserviceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSortieMyserviceMockMvc;

    private SortieMyservice sortieMyservice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SortieMyserviceResource sortieMyserviceResource = new SortieMyserviceResource(sortieMyserviceService);
        this.restSortieMyserviceMockMvc = MockMvcBuilders.standaloneSetup(sortieMyserviceResource)
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
    public static SortieMyservice createEntity(EntityManager em) {
        SortieMyservice sortieMyservice = new SortieMyservice()
            .date(DEFAULT_DATE)
            .prixTotalht(DEFAULT_PRIX_TOTALHT)
            .prixTTC(DEFAULT_PRIX_TTC);
        return sortieMyservice;
    }

    @Before
    public void initTest() {
        sortieMyservice = createEntity(em);
    }

    @Test
    @Transactional
    public void createSortieMyservice() throws Exception {
        int databaseSizeBeforeCreate = sortieMyserviceRepository.findAll().size();

        // Create the SortieMyservice
        SortieMyserviceDTO sortieMyserviceDTO = sortieMyserviceMapper.toDto(sortieMyservice);
        restSortieMyserviceMockMvc.perform(post("/api/sortie-myservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieMyserviceDTO)))
            .andExpect(status().isCreated());

        // Validate the SortieMyservice in the database
        List<SortieMyservice> sortieMyserviceList = sortieMyserviceRepository.findAll();
        assertThat(sortieMyserviceList).hasSize(databaseSizeBeforeCreate + 1);
        SortieMyservice testSortieMyservice = sortieMyserviceList.get(sortieMyserviceList.size() - 1);
        assertThat(testSortieMyservice.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSortieMyservice.getPrixTotalht()).isEqualTo(DEFAULT_PRIX_TOTALHT);
        assertThat(testSortieMyservice.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
    }

    @Test
    @Transactional
    public void createSortieMyserviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sortieMyserviceRepository.findAll().size();

        // Create the SortieMyservice with an existing ID
        sortieMyservice.setId(1L);
        SortieMyserviceDTO sortieMyserviceDTO = sortieMyserviceMapper.toDto(sortieMyservice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSortieMyserviceMockMvc.perform(post("/api/sortie-myservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieMyserviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SortieMyservice in the database
        List<SortieMyservice> sortieMyserviceList = sortieMyserviceRepository.findAll();
        assertThat(sortieMyserviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSortieMyservices() throws Exception {
        // Initialize the database
        sortieMyserviceRepository.saveAndFlush(sortieMyservice);

        // Get all the sortieMyserviceList
        restSortieMyserviceMockMvc.perform(get("/api/sortie-myservices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sortieMyservice.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].prixTotalht").value(hasItem(DEFAULT_PRIX_TOTALHT.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())));
    }

    @Test
    @Transactional
    public void getSortieMyservice() throws Exception {
        // Initialize the database
        sortieMyserviceRepository.saveAndFlush(sortieMyservice);

        // Get the sortieMyservice
        restSortieMyserviceMockMvc.perform(get("/api/sortie-myservices/{id}", sortieMyservice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sortieMyservice.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.prixTotalht").value(DEFAULT_PRIX_TOTALHT.doubleValue()))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSortieMyservice() throws Exception {
        // Get the sortieMyservice
        restSortieMyserviceMockMvc.perform(get("/api/sortie-myservices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSortieMyservice() throws Exception {
        // Initialize the database
        sortieMyserviceRepository.saveAndFlush(sortieMyservice);
        int databaseSizeBeforeUpdate = sortieMyserviceRepository.findAll().size();

        // Update the sortieMyservice
        SortieMyservice updatedSortieMyservice = sortieMyserviceRepository.findOne(sortieMyservice.getId());
        // Disconnect from session so that the updates on updatedSortieMyservice are not directly saved in db
        em.detach(updatedSortieMyservice);
        updatedSortieMyservice
            .date(UPDATED_DATE)
            .prixTotalht(UPDATED_PRIX_TOTALHT)
            .prixTTC(UPDATED_PRIX_TTC);
        SortieMyserviceDTO sortieMyserviceDTO = sortieMyserviceMapper.toDto(updatedSortieMyservice);

        restSortieMyserviceMockMvc.perform(put("/api/sortie-myservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieMyserviceDTO)))
            .andExpect(status().isOk());

        // Validate the SortieMyservice in the database
        List<SortieMyservice> sortieMyserviceList = sortieMyserviceRepository.findAll();
        assertThat(sortieMyserviceList).hasSize(databaseSizeBeforeUpdate);
        SortieMyservice testSortieMyservice = sortieMyserviceList.get(sortieMyserviceList.size() - 1);
        assertThat(testSortieMyservice.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSortieMyservice.getPrixTotalht()).isEqualTo(UPDATED_PRIX_TOTALHT);
        assertThat(testSortieMyservice.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingSortieMyservice() throws Exception {
        int databaseSizeBeforeUpdate = sortieMyserviceRepository.findAll().size();

        // Create the SortieMyservice
        SortieMyserviceDTO sortieMyserviceDTO = sortieMyserviceMapper.toDto(sortieMyservice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSortieMyserviceMockMvc.perform(put("/api/sortie-myservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sortieMyserviceDTO)))
            .andExpect(status().isCreated());

        // Validate the SortieMyservice in the database
        List<SortieMyservice> sortieMyserviceList = sortieMyserviceRepository.findAll();
        assertThat(sortieMyserviceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSortieMyservice() throws Exception {
        // Initialize the database
        sortieMyserviceRepository.saveAndFlush(sortieMyservice);
        int databaseSizeBeforeDelete = sortieMyserviceRepository.findAll().size();

        // Get the sortieMyservice
        restSortieMyserviceMockMvc.perform(delete("/api/sortie-myservices/{id}", sortieMyservice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SortieMyservice> sortieMyserviceList = sortieMyserviceRepository.findAll();
        assertThat(sortieMyserviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SortieMyservice.class);
        SortieMyservice sortieMyservice1 = new SortieMyservice();
        sortieMyservice1.setId(1L);
        SortieMyservice sortieMyservice2 = new SortieMyservice();
        sortieMyservice2.setId(sortieMyservice1.getId());
        assertThat(sortieMyservice1).isEqualTo(sortieMyservice2);
        sortieMyservice2.setId(2L);
        assertThat(sortieMyservice1).isNotEqualTo(sortieMyservice2);
        sortieMyservice1.setId(null);
        assertThat(sortieMyservice1).isNotEqualTo(sortieMyservice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SortieMyserviceDTO.class);
        SortieMyserviceDTO sortieMyserviceDTO1 = new SortieMyserviceDTO();
        sortieMyserviceDTO1.setId(1L);
        SortieMyserviceDTO sortieMyserviceDTO2 = new SortieMyserviceDTO();
        assertThat(sortieMyserviceDTO1).isNotEqualTo(sortieMyserviceDTO2);
        sortieMyserviceDTO2.setId(sortieMyserviceDTO1.getId());
        assertThat(sortieMyserviceDTO1).isEqualTo(sortieMyserviceDTO2);
        sortieMyserviceDTO2.setId(2L);
        assertThat(sortieMyserviceDTO1).isNotEqualTo(sortieMyserviceDTO2);
        sortieMyserviceDTO1.setId(null);
        assertThat(sortieMyserviceDTO1).isNotEqualTo(sortieMyserviceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sortieMyserviceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sortieMyserviceMapper.fromId(null)).isNull();
    }
}
