package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.CategorieDepense;
import com.epetrole.backend.repository.CategorieDepenseRepository;
import com.epetrole.backend.service.CategorieDepenseService;
import com.epetrole.backend.service.dto.CategorieDepenseDTO;
import com.epetrole.backend.service.mapper.CategorieDepenseMapper;
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
 * Test class for the CategorieDepenseResource REST controller.
 *
 * @see CategorieDepenseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class CategorieDepenseResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CategorieDepenseRepository categorieDepenseRepository;

    @Autowired
    private CategorieDepenseMapper categorieDepenseMapper;

    @Autowired
    private CategorieDepenseService categorieDepenseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCategorieDepenseMockMvc;

    private CategorieDepense categorieDepense;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategorieDepenseResource categorieDepenseResource = new CategorieDepenseResource(categorieDepenseService);
        this.restCategorieDepenseMockMvc = MockMvcBuilders.standaloneSetup(categorieDepenseResource)
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
    public static CategorieDepense createEntity(EntityManager em) {
        CategorieDepense categorieDepense = new CategorieDepense()
            .libele(DEFAULT_LIBELE)
            .description(DEFAULT_DESCRIPTION);
        return categorieDepense;
    }

    @Before
    public void initTest() {
        categorieDepense = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorieDepense() throws Exception {
        int databaseSizeBeforeCreate = categorieDepenseRepository.findAll().size();

        // Create the CategorieDepense
        CategorieDepenseDTO categorieDepenseDTO = categorieDepenseMapper.toDto(categorieDepense);
        restCategorieDepenseMockMvc.perform(post("/api/categorie-depenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieDepenseDTO)))
            .andExpect(status().isCreated());

        // Validate the CategorieDepense in the database
        List<CategorieDepense> categorieDepenseList = categorieDepenseRepository.findAll();
        assertThat(categorieDepenseList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieDepense testCategorieDepense = categorieDepenseList.get(categorieDepenseList.size() - 1);
        assertThat(testCategorieDepense.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testCategorieDepense.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCategorieDepenseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorieDepenseRepository.findAll().size();

        // Create the CategorieDepense with an existing ID
        categorieDepense.setId(1L);
        CategorieDepenseDTO categorieDepenseDTO = categorieDepenseMapper.toDto(categorieDepense);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieDepenseMockMvc.perform(post("/api/categorie-depenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieDepenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieDepense in the database
        List<CategorieDepense> categorieDepenseList = categorieDepenseRepository.findAll();
        assertThat(categorieDepenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCategorieDepenses() throws Exception {
        // Initialize the database
        categorieDepenseRepository.saveAndFlush(categorieDepense);

        // Get all the categorieDepenseList
        restCategorieDepenseMockMvc.perform(get("/api/categorie-depenses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieDepense.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCategorieDepense() throws Exception {
        // Initialize the database
        categorieDepenseRepository.saveAndFlush(categorieDepense);

        // Get the categorieDepense
        restCategorieDepenseMockMvc.perform(get("/api/categorie-depenses/{id}", categorieDepense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categorieDepense.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategorieDepense() throws Exception {
        // Get the categorieDepense
        restCategorieDepenseMockMvc.perform(get("/api/categorie-depenses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorieDepense() throws Exception {
        // Initialize the database
        categorieDepenseRepository.saveAndFlush(categorieDepense);
        int databaseSizeBeforeUpdate = categorieDepenseRepository.findAll().size();

        // Update the categorieDepense
        CategorieDepense updatedCategorieDepense = categorieDepenseRepository.findOne(categorieDepense.getId());
        // Disconnect from session so that the updates on updatedCategorieDepense are not directly saved in db
        em.detach(updatedCategorieDepense);
        updatedCategorieDepense
            .libele(UPDATED_LIBELE)
            .description(UPDATED_DESCRIPTION);
        CategorieDepenseDTO categorieDepenseDTO = categorieDepenseMapper.toDto(updatedCategorieDepense);

        restCategorieDepenseMockMvc.perform(put("/api/categorie-depenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieDepenseDTO)))
            .andExpect(status().isOk());

        // Validate the CategorieDepense in the database
        List<CategorieDepense> categorieDepenseList = categorieDepenseRepository.findAll();
        assertThat(categorieDepenseList).hasSize(databaseSizeBeforeUpdate);
        CategorieDepense testCategorieDepense = categorieDepenseList.get(categorieDepenseList.size() - 1);
        assertThat(testCategorieDepense.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testCategorieDepense.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorieDepense() throws Exception {
        int databaseSizeBeforeUpdate = categorieDepenseRepository.findAll().size();

        // Create the CategorieDepense
        CategorieDepenseDTO categorieDepenseDTO = categorieDepenseMapper.toDto(categorieDepense);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCategorieDepenseMockMvc.perform(put("/api/categorie-depenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieDepenseDTO)))
            .andExpect(status().isCreated());

        // Validate the CategorieDepense in the database
        List<CategorieDepense> categorieDepenseList = categorieDepenseRepository.findAll();
        assertThat(categorieDepenseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCategorieDepense() throws Exception {
        // Initialize the database
        categorieDepenseRepository.saveAndFlush(categorieDepense);
        int databaseSizeBeforeDelete = categorieDepenseRepository.findAll().size();

        // Get the categorieDepense
        restCategorieDepenseMockMvc.perform(delete("/api/categorie-depenses/{id}", categorieDepense.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CategorieDepense> categorieDepenseList = categorieDepenseRepository.findAll();
        assertThat(categorieDepenseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieDepense.class);
        CategorieDepense categorieDepense1 = new CategorieDepense();
        categorieDepense1.setId(1L);
        CategorieDepense categorieDepense2 = new CategorieDepense();
        categorieDepense2.setId(categorieDepense1.getId());
        assertThat(categorieDepense1).isEqualTo(categorieDepense2);
        categorieDepense2.setId(2L);
        assertThat(categorieDepense1).isNotEqualTo(categorieDepense2);
        categorieDepense1.setId(null);
        assertThat(categorieDepense1).isNotEqualTo(categorieDepense2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieDepenseDTO.class);
        CategorieDepenseDTO categorieDepenseDTO1 = new CategorieDepenseDTO();
        categorieDepenseDTO1.setId(1L);
        CategorieDepenseDTO categorieDepenseDTO2 = new CategorieDepenseDTO();
        assertThat(categorieDepenseDTO1).isNotEqualTo(categorieDepenseDTO2);
        categorieDepenseDTO2.setId(categorieDepenseDTO1.getId());
        assertThat(categorieDepenseDTO1).isEqualTo(categorieDepenseDTO2);
        categorieDepenseDTO2.setId(2L);
        assertThat(categorieDepenseDTO1).isNotEqualTo(categorieDepenseDTO2);
        categorieDepenseDTO1.setId(null);
        assertThat(categorieDepenseDTO1).isNotEqualTo(categorieDepenseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(categorieDepenseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(categorieDepenseMapper.fromId(null)).isNull();
    }
}
