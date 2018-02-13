package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Citerne;
import com.epetrole.backend.repository.CiterneRepository;
import com.epetrole.backend.service.CiterneService;
import com.epetrole.backend.service.dto.CiterneDTO;
import com.epetrole.backend.service.mapper.CiterneMapper;
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
 * Test class for the CiterneResource REST controller.
 *
 * @see CiterneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class CiterneResourceIntTest {

    private static final String DEFAULT_VOLUME_INIT = "AAAAAAAAAA";
    private static final String UPDATED_VOLUME_INIT = "BBBBBBBBBB";

    private static final String DEFAULT_VOLUME_MAX = "AAAAAAAAAA";
    private static final String UPDATED_VOLUME_MAX = "BBBBBBBBBB";

    @Autowired
    private CiterneRepository citerneRepository;

    @Autowired
    private CiterneMapper citerneMapper;

    @Autowired
    private CiterneService citerneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCiterneMockMvc;

    private Citerne citerne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CiterneResource citerneResource = new CiterneResource(citerneService);
        this.restCiterneMockMvc = MockMvcBuilders.standaloneSetup(citerneResource)
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
    public static Citerne createEntity(EntityManager em) {
        Citerne citerne = new Citerne()
            .volumeInit(DEFAULT_VOLUME_INIT)
            .volumeMax(DEFAULT_VOLUME_MAX);
        return citerne;
    }

    @Before
    public void initTest() {
        citerne = createEntity(em);
    }

    @Test
    @Transactional
    public void createCiterne() throws Exception {
        int databaseSizeBeforeCreate = citerneRepository.findAll().size();

        // Create the Citerne
        CiterneDTO citerneDTO = citerneMapper.toDto(citerne);
        restCiterneMockMvc.perform(post("/api/citernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citerneDTO)))
            .andExpect(status().isCreated());

        // Validate the Citerne in the database
        List<Citerne> citerneList = citerneRepository.findAll();
        assertThat(citerneList).hasSize(databaseSizeBeforeCreate + 1);
        Citerne testCiterne = citerneList.get(citerneList.size() - 1);
        assertThat(testCiterne.getVolumeInit()).isEqualTo(DEFAULT_VOLUME_INIT);
        assertThat(testCiterne.getVolumeMax()).isEqualTo(DEFAULT_VOLUME_MAX);
    }

    @Test
    @Transactional
    public void createCiterneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = citerneRepository.findAll().size();

        // Create the Citerne with an existing ID
        citerne.setId(1L);
        CiterneDTO citerneDTO = citerneMapper.toDto(citerne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiterneMockMvc.perform(post("/api/citernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citerneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Citerne in the database
        List<Citerne> citerneList = citerneRepository.findAll();
        assertThat(citerneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCiternes() throws Exception {
        // Initialize the database
        citerneRepository.saveAndFlush(citerne);

        // Get all the citerneList
        restCiterneMockMvc.perform(get("/api/citernes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citerne.getId().intValue())))
            .andExpect(jsonPath("$.[*].volumeInit").value(hasItem(DEFAULT_VOLUME_INIT.toString())))
            .andExpect(jsonPath("$.[*].volumeMax").value(hasItem(DEFAULT_VOLUME_MAX.toString())));
    }

    @Test
    @Transactional
    public void getCiterne() throws Exception {
        // Initialize the database
        citerneRepository.saveAndFlush(citerne);

        // Get the citerne
        restCiterneMockMvc.perform(get("/api/citernes/{id}", citerne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(citerne.getId().intValue()))
            .andExpect(jsonPath("$.volumeInit").value(DEFAULT_VOLUME_INIT.toString()))
            .andExpect(jsonPath("$.volumeMax").value(DEFAULT_VOLUME_MAX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCiterne() throws Exception {
        // Get the citerne
        restCiterneMockMvc.perform(get("/api/citernes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCiterne() throws Exception {
        // Initialize the database
        citerneRepository.saveAndFlush(citerne);
        int databaseSizeBeforeUpdate = citerneRepository.findAll().size();

        // Update the citerne
        Citerne updatedCiterne = citerneRepository.findOne(citerne.getId());
        // Disconnect from session so that the updates on updatedCiterne are not directly saved in db
        em.detach(updatedCiterne);
        updatedCiterne
            .volumeInit(UPDATED_VOLUME_INIT)
            .volumeMax(UPDATED_VOLUME_MAX);
        CiterneDTO citerneDTO = citerneMapper.toDto(updatedCiterne);

        restCiterneMockMvc.perform(put("/api/citernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citerneDTO)))
            .andExpect(status().isOk());

        // Validate the Citerne in the database
        List<Citerne> citerneList = citerneRepository.findAll();
        assertThat(citerneList).hasSize(databaseSizeBeforeUpdate);
        Citerne testCiterne = citerneList.get(citerneList.size() - 1);
        assertThat(testCiterne.getVolumeInit()).isEqualTo(UPDATED_VOLUME_INIT);
        assertThat(testCiterne.getVolumeMax()).isEqualTo(UPDATED_VOLUME_MAX);
    }

    @Test
    @Transactional
    public void updateNonExistingCiterne() throws Exception {
        int databaseSizeBeforeUpdate = citerneRepository.findAll().size();

        // Create the Citerne
        CiterneDTO citerneDTO = citerneMapper.toDto(citerne);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCiterneMockMvc.perform(put("/api/citernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citerneDTO)))
            .andExpect(status().isCreated());

        // Validate the Citerne in the database
        List<Citerne> citerneList = citerneRepository.findAll();
        assertThat(citerneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCiterne() throws Exception {
        // Initialize the database
        citerneRepository.saveAndFlush(citerne);
        int databaseSizeBeforeDelete = citerneRepository.findAll().size();

        // Get the citerne
        restCiterneMockMvc.perform(delete("/api/citernes/{id}", citerne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Citerne> citerneList = citerneRepository.findAll();
        assertThat(citerneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Citerne.class);
        Citerne citerne1 = new Citerne();
        citerne1.setId(1L);
        Citerne citerne2 = new Citerne();
        citerne2.setId(citerne1.getId());
        assertThat(citerne1).isEqualTo(citerne2);
        citerne2.setId(2L);
        assertThat(citerne1).isNotEqualTo(citerne2);
        citerne1.setId(null);
        assertThat(citerne1).isNotEqualTo(citerne2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiterneDTO.class);
        CiterneDTO citerneDTO1 = new CiterneDTO();
        citerneDTO1.setId(1L);
        CiterneDTO citerneDTO2 = new CiterneDTO();
        assertThat(citerneDTO1).isNotEqualTo(citerneDTO2);
        citerneDTO2.setId(citerneDTO1.getId());
        assertThat(citerneDTO1).isEqualTo(citerneDTO2);
        citerneDTO2.setId(2L);
        assertThat(citerneDTO1).isNotEqualTo(citerneDTO2);
        citerneDTO1.setId(null);
        assertThat(citerneDTO1).isNotEqualTo(citerneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(citerneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(citerneMapper.fromId(null)).isNull();
    }
}
