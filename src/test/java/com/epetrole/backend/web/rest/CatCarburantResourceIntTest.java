package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.CatCarburant;
import com.epetrole.backend.repository.CatCarburantRepository;
import com.epetrole.backend.service.CatCarburantService;
import com.epetrole.backend.service.dto.CatCarburantDTO;
import com.epetrole.backend.service.mapper.CatCarburantMapper;
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
 * Test class for the CatCarburantResource REST controller.
 *
 * @see CatCarburantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class CatCarburantResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CatCarburantRepository catCarburantRepository;

    @Autowired
    private CatCarburantMapper catCarburantMapper;

    @Autowired
    private CatCarburantService catCarburantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCatCarburantMockMvc;

    private CatCarburant catCarburant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CatCarburantResource catCarburantResource = new CatCarburantResource(catCarburantService);
        this.restCatCarburantMockMvc = MockMvcBuilders.standaloneSetup(catCarburantResource)
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
    public static CatCarburant createEntity(EntityManager em) {
        CatCarburant catCarburant = new CatCarburant()
            .libele(DEFAULT_LIBELE)
            .description(DEFAULT_DESCRIPTION);
        return catCarburant;
    }

    @Before
    public void initTest() {
        catCarburant = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatCarburant() throws Exception {
        int databaseSizeBeforeCreate = catCarburantRepository.findAll().size();

        // Create the CatCarburant
        CatCarburantDTO catCarburantDTO = catCarburantMapper.toDto(catCarburant);
        restCatCarburantMockMvc.perform(post("/api/cat-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catCarburantDTO)))
            .andExpect(status().isCreated());

        // Validate the CatCarburant in the database
        List<CatCarburant> catCarburantList = catCarburantRepository.findAll();
        assertThat(catCarburantList).hasSize(databaseSizeBeforeCreate + 1);
        CatCarburant testCatCarburant = catCarburantList.get(catCarburantList.size() - 1);
        assertThat(testCatCarburant.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testCatCarburant.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCatCarburantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catCarburantRepository.findAll().size();

        // Create the CatCarburant with an existing ID
        catCarburant.setId(1L);
        CatCarburantDTO catCarburantDTO = catCarburantMapper.toDto(catCarburant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatCarburantMockMvc.perform(post("/api/cat-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catCarburantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CatCarburant in the database
        List<CatCarburant> catCarburantList = catCarburantRepository.findAll();
        assertThat(catCarburantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCatCarburants() throws Exception {
        // Initialize the database
        catCarburantRepository.saveAndFlush(catCarburant);

        // Get all the catCarburantList
        restCatCarburantMockMvc.perform(get("/api/cat-carburants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catCarburant.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCatCarburant() throws Exception {
        // Initialize the database
        catCarburantRepository.saveAndFlush(catCarburant);

        // Get the catCarburant
        restCatCarburantMockMvc.perform(get("/api/cat-carburants/{id}", catCarburant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(catCarburant.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCatCarburant() throws Exception {
        // Get the catCarburant
        restCatCarburantMockMvc.perform(get("/api/cat-carburants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatCarburant() throws Exception {
        // Initialize the database
        catCarburantRepository.saveAndFlush(catCarburant);
        int databaseSizeBeforeUpdate = catCarburantRepository.findAll().size();

        // Update the catCarburant
        CatCarburant updatedCatCarburant = catCarburantRepository.findOne(catCarburant.getId());
        // Disconnect from session so that the updates on updatedCatCarburant are not directly saved in db
        em.detach(updatedCatCarburant);
        updatedCatCarburant
            .libele(UPDATED_LIBELE)
            .description(UPDATED_DESCRIPTION);
        CatCarburantDTO catCarburantDTO = catCarburantMapper.toDto(updatedCatCarburant);

        restCatCarburantMockMvc.perform(put("/api/cat-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catCarburantDTO)))
            .andExpect(status().isOk());

        // Validate the CatCarburant in the database
        List<CatCarburant> catCarburantList = catCarburantRepository.findAll();
        assertThat(catCarburantList).hasSize(databaseSizeBeforeUpdate);
        CatCarburant testCatCarburant = catCarburantList.get(catCarburantList.size() - 1);
        assertThat(testCatCarburant.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testCatCarburant.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCatCarburant() throws Exception {
        int databaseSizeBeforeUpdate = catCarburantRepository.findAll().size();

        // Create the CatCarburant
        CatCarburantDTO catCarburantDTO = catCarburantMapper.toDto(catCarburant);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCatCarburantMockMvc.perform(put("/api/cat-carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catCarburantDTO)))
            .andExpect(status().isCreated());

        // Validate the CatCarburant in the database
        List<CatCarburant> catCarburantList = catCarburantRepository.findAll();
        assertThat(catCarburantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCatCarburant() throws Exception {
        // Initialize the database
        catCarburantRepository.saveAndFlush(catCarburant);
        int databaseSizeBeforeDelete = catCarburantRepository.findAll().size();

        // Get the catCarburant
        restCatCarburantMockMvc.perform(delete("/api/cat-carburants/{id}", catCarburant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CatCarburant> catCarburantList = catCarburantRepository.findAll();
        assertThat(catCarburantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatCarburant.class);
        CatCarburant catCarburant1 = new CatCarburant();
        catCarburant1.setId(1L);
        CatCarburant catCarburant2 = new CatCarburant();
        catCarburant2.setId(catCarburant1.getId());
        assertThat(catCarburant1).isEqualTo(catCarburant2);
        catCarburant2.setId(2L);
        assertThat(catCarburant1).isNotEqualTo(catCarburant2);
        catCarburant1.setId(null);
        assertThat(catCarburant1).isNotEqualTo(catCarburant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatCarburantDTO.class);
        CatCarburantDTO catCarburantDTO1 = new CatCarburantDTO();
        catCarburantDTO1.setId(1L);
        CatCarburantDTO catCarburantDTO2 = new CatCarburantDTO();
        assertThat(catCarburantDTO1).isNotEqualTo(catCarburantDTO2);
        catCarburantDTO2.setId(catCarburantDTO1.getId());
        assertThat(catCarburantDTO1).isEqualTo(catCarburantDTO2);
        catCarburantDTO2.setId(2L);
        assertThat(catCarburantDTO1).isNotEqualTo(catCarburantDTO2);
        catCarburantDTO1.setId(null);
        assertThat(catCarburantDTO1).isNotEqualTo(catCarburantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(catCarburantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(catCarburantMapper.fromId(null)).isNull();
    }
}
