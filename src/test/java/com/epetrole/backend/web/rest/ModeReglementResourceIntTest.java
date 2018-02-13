package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.ModeReglement;
import com.epetrole.backend.repository.ModeReglementRepository;
import com.epetrole.backend.service.ModeReglementService;
import com.epetrole.backend.service.dto.ModeReglementDTO;
import com.epetrole.backend.service.mapper.ModeReglementMapper;
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
 * Test class for the ModeReglementResource REST controller.
 *
 * @see ModeReglementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class ModeReglementResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    @Autowired
    private ModeReglementRepository modeReglementRepository;

    @Autowired
    private ModeReglementMapper modeReglementMapper;

    @Autowired
    private ModeReglementService modeReglementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModeReglementMockMvc;

    private ModeReglement modeReglement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModeReglementResource modeReglementResource = new ModeReglementResource(modeReglementService);
        this.restModeReglementMockMvc = MockMvcBuilders.standaloneSetup(modeReglementResource)
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
    public static ModeReglement createEntity(EntityManager em) {
        ModeReglement modeReglement = new ModeReglement()
            .code(DEFAULT_CODE)
            .libele(DEFAULT_LIBELE);
        return modeReglement;
    }

    @Before
    public void initTest() {
        modeReglement = createEntity(em);
    }

    @Test
    @Transactional
    public void createModeReglement() throws Exception {
        int databaseSizeBeforeCreate = modeReglementRepository.findAll().size();

        // Create the ModeReglement
        ModeReglementDTO modeReglementDTO = modeReglementMapper.toDto(modeReglement);
        restModeReglementMockMvc.perform(post("/api/mode-reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeReglementDTO)))
            .andExpect(status().isCreated());

        // Validate the ModeReglement in the database
        List<ModeReglement> modeReglementList = modeReglementRepository.findAll();
        assertThat(modeReglementList).hasSize(databaseSizeBeforeCreate + 1);
        ModeReglement testModeReglement = modeReglementList.get(modeReglementList.size() - 1);
        assertThat(testModeReglement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModeReglement.getLibele()).isEqualTo(DEFAULT_LIBELE);
    }

    @Test
    @Transactional
    public void createModeReglementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modeReglementRepository.findAll().size();

        // Create the ModeReglement with an existing ID
        modeReglement.setId(1L);
        ModeReglementDTO modeReglementDTO = modeReglementMapper.toDto(modeReglement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeReglementMockMvc.perform(post("/api/mode-reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeReglementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModeReglement in the database
        List<ModeReglement> modeReglementList = modeReglementRepository.findAll();
        assertThat(modeReglementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllModeReglements() throws Exception {
        // Initialize the database
        modeReglementRepository.saveAndFlush(modeReglement);

        // Get all the modeReglementList
        restModeReglementMockMvc.perform(get("/api/mode-reglements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modeReglement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())));
    }

    @Test
    @Transactional
    public void getModeReglement() throws Exception {
        // Initialize the database
        modeReglementRepository.saveAndFlush(modeReglement);

        // Get the modeReglement
        restModeReglementMockMvc.perform(get("/api/mode-reglements/{id}", modeReglement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modeReglement.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModeReglement() throws Exception {
        // Get the modeReglement
        restModeReglementMockMvc.perform(get("/api/mode-reglements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModeReglement() throws Exception {
        // Initialize the database
        modeReglementRepository.saveAndFlush(modeReglement);
        int databaseSizeBeforeUpdate = modeReglementRepository.findAll().size();

        // Update the modeReglement
        ModeReglement updatedModeReglement = modeReglementRepository.findOne(modeReglement.getId());
        // Disconnect from session so that the updates on updatedModeReglement are not directly saved in db
        em.detach(updatedModeReglement);
        updatedModeReglement
            .code(UPDATED_CODE)
            .libele(UPDATED_LIBELE);
        ModeReglementDTO modeReglementDTO = modeReglementMapper.toDto(updatedModeReglement);

        restModeReglementMockMvc.perform(put("/api/mode-reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeReglementDTO)))
            .andExpect(status().isOk());

        // Validate the ModeReglement in the database
        List<ModeReglement> modeReglementList = modeReglementRepository.findAll();
        assertThat(modeReglementList).hasSize(databaseSizeBeforeUpdate);
        ModeReglement testModeReglement = modeReglementList.get(modeReglementList.size() - 1);
        assertThat(testModeReglement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModeReglement.getLibele()).isEqualTo(UPDATED_LIBELE);
    }

    @Test
    @Transactional
    public void updateNonExistingModeReglement() throws Exception {
        int databaseSizeBeforeUpdate = modeReglementRepository.findAll().size();

        // Create the ModeReglement
        ModeReglementDTO modeReglementDTO = modeReglementMapper.toDto(modeReglement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModeReglementMockMvc.perform(put("/api/mode-reglements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeReglementDTO)))
            .andExpect(status().isCreated());

        // Validate the ModeReglement in the database
        List<ModeReglement> modeReglementList = modeReglementRepository.findAll();
        assertThat(modeReglementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModeReglement() throws Exception {
        // Initialize the database
        modeReglementRepository.saveAndFlush(modeReglement);
        int databaseSizeBeforeDelete = modeReglementRepository.findAll().size();

        // Get the modeReglement
        restModeReglementMockMvc.perform(delete("/api/mode-reglements/{id}", modeReglement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ModeReglement> modeReglementList = modeReglementRepository.findAll();
        assertThat(modeReglementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeReglement.class);
        ModeReglement modeReglement1 = new ModeReglement();
        modeReglement1.setId(1L);
        ModeReglement modeReglement2 = new ModeReglement();
        modeReglement2.setId(modeReglement1.getId());
        assertThat(modeReglement1).isEqualTo(modeReglement2);
        modeReglement2.setId(2L);
        assertThat(modeReglement1).isNotEqualTo(modeReglement2);
        modeReglement1.setId(null);
        assertThat(modeReglement1).isNotEqualTo(modeReglement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeReglementDTO.class);
        ModeReglementDTO modeReglementDTO1 = new ModeReglementDTO();
        modeReglementDTO1.setId(1L);
        ModeReglementDTO modeReglementDTO2 = new ModeReglementDTO();
        assertThat(modeReglementDTO1).isNotEqualTo(modeReglementDTO2);
        modeReglementDTO2.setId(modeReglementDTO1.getId());
        assertThat(modeReglementDTO1).isEqualTo(modeReglementDTO2);
        modeReglementDTO2.setId(2L);
        assertThat(modeReglementDTO1).isNotEqualTo(modeReglementDTO2);
        modeReglementDTO1.setId(null);
        assertThat(modeReglementDTO1).isNotEqualTo(modeReglementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(modeReglementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(modeReglementMapper.fromId(null)).isNull();
    }
}
