package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.TypeTresorerie;
import com.epetrole.backend.repository.TypeTresorerieRepository;
import com.epetrole.backend.service.TypeTresorerieService;
import com.epetrole.backend.service.dto.TypeTresorerieDTO;
import com.epetrole.backend.service.mapper.TypeTresorerieMapper;
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
 * Test class for the TypeTresorerieResource REST controller.
 *
 * @see TypeTresorerieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class TypeTresorerieResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TypeTresorerieRepository typeTresorerieRepository;

    @Autowired
    private TypeTresorerieMapper typeTresorerieMapper;

    @Autowired
    private TypeTresorerieService typeTresorerieService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeTresorerieMockMvc;

    private TypeTresorerie typeTresorerie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeTresorerieResource typeTresorerieResource = new TypeTresorerieResource(typeTresorerieService);
        this.restTypeTresorerieMockMvc = MockMvcBuilders.standaloneSetup(typeTresorerieResource)
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
    public static TypeTresorerie createEntity(EntityManager em) {
        TypeTresorerie typeTresorerie = new TypeTresorerie()
            .libele(DEFAULT_LIBELE)
            .description(DEFAULT_DESCRIPTION);
        return typeTresorerie;
    }

    @Before
    public void initTest() {
        typeTresorerie = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeTresorerie() throws Exception {
        int databaseSizeBeforeCreate = typeTresorerieRepository.findAll().size();

        // Create the TypeTresorerie
        TypeTresorerieDTO typeTresorerieDTO = typeTresorerieMapper.toDto(typeTresorerie);
        restTypeTresorerieMockMvc.perform(post("/api/type-tresoreries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTresorerieDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeTresorerie in the database
        List<TypeTresorerie> typeTresorerieList = typeTresorerieRepository.findAll();
        assertThat(typeTresorerieList).hasSize(databaseSizeBeforeCreate + 1);
        TypeTresorerie testTypeTresorerie = typeTresorerieList.get(typeTresorerieList.size() - 1);
        assertThat(testTypeTresorerie.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testTypeTresorerie.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTypeTresorerieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeTresorerieRepository.findAll().size();

        // Create the TypeTresorerie with an existing ID
        typeTresorerie.setId(1L);
        TypeTresorerieDTO typeTresorerieDTO = typeTresorerieMapper.toDto(typeTresorerie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeTresorerieMockMvc.perform(post("/api/type-tresoreries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTresorerieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTresorerie in the database
        List<TypeTresorerie> typeTresorerieList = typeTresorerieRepository.findAll();
        assertThat(typeTresorerieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeTresoreries() throws Exception {
        // Initialize the database
        typeTresorerieRepository.saveAndFlush(typeTresorerie);

        // Get all the typeTresorerieList
        restTypeTresorerieMockMvc.perform(get("/api/type-tresoreries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTresorerie.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTypeTresorerie() throws Exception {
        // Initialize the database
        typeTresorerieRepository.saveAndFlush(typeTresorerie);

        // Get the typeTresorerie
        restTypeTresorerieMockMvc.perform(get("/api/type-tresoreries/{id}", typeTresorerie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeTresorerie.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeTresorerie() throws Exception {
        // Get the typeTresorerie
        restTypeTresorerieMockMvc.perform(get("/api/type-tresoreries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeTresorerie() throws Exception {
        // Initialize the database
        typeTresorerieRepository.saveAndFlush(typeTresorerie);
        int databaseSizeBeforeUpdate = typeTresorerieRepository.findAll().size();

        // Update the typeTresorerie
        TypeTresorerie updatedTypeTresorerie = typeTresorerieRepository.findOne(typeTresorerie.getId());
        // Disconnect from session so that the updates on updatedTypeTresorerie are not directly saved in db
        em.detach(updatedTypeTresorerie);
        updatedTypeTresorerie
            .libele(UPDATED_LIBELE)
            .description(UPDATED_DESCRIPTION);
        TypeTresorerieDTO typeTresorerieDTO = typeTresorerieMapper.toDto(updatedTypeTresorerie);

        restTypeTresorerieMockMvc.perform(put("/api/type-tresoreries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTresorerieDTO)))
            .andExpect(status().isOk());

        // Validate the TypeTresorerie in the database
        List<TypeTresorerie> typeTresorerieList = typeTresorerieRepository.findAll();
        assertThat(typeTresorerieList).hasSize(databaseSizeBeforeUpdate);
        TypeTresorerie testTypeTresorerie = typeTresorerieList.get(typeTresorerieList.size() - 1);
        assertThat(testTypeTresorerie.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testTypeTresorerie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeTresorerie() throws Exception {
        int databaseSizeBeforeUpdate = typeTresorerieRepository.findAll().size();

        // Create the TypeTresorerie
        TypeTresorerieDTO typeTresorerieDTO = typeTresorerieMapper.toDto(typeTresorerie);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeTresorerieMockMvc.perform(put("/api/type-tresoreries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeTresorerieDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeTresorerie in the database
        List<TypeTresorerie> typeTresorerieList = typeTresorerieRepository.findAll();
        assertThat(typeTresorerieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeTresorerie() throws Exception {
        // Initialize the database
        typeTresorerieRepository.saveAndFlush(typeTresorerie);
        int databaseSizeBeforeDelete = typeTresorerieRepository.findAll().size();

        // Get the typeTresorerie
        restTypeTresorerieMockMvc.perform(delete("/api/type-tresoreries/{id}", typeTresorerie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeTresorerie> typeTresorerieList = typeTresorerieRepository.findAll();
        assertThat(typeTresorerieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTresorerie.class);
        TypeTresorerie typeTresorerie1 = new TypeTresorerie();
        typeTresorerie1.setId(1L);
        TypeTresorerie typeTresorerie2 = new TypeTresorerie();
        typeTresorerie2.setId(typeTresorerie1.getId());
        assertThat(typeTresorerie1).isEqualTo(typeTresorerie2);
        typeTresorerie2.setId(2L);
        assertThat(typeTresorerie1).isNotEqualTo(typeTresorerie2);
        typeTresorerie1.setId(null);
        assertThat(typeTresorerie1).isNotEqualTo(typeTresorerie2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTresorerieDTO.class);
        TypeTresorerieDTO typeTresorerieDTO1 = new TypeTresorerieDTO();
        typeTresorerieDTO1.setId(1L);
        TypeTresorerieDTO typeTresorerieDTO2 = new TypeTresorerieDTO();
        assertThat(typeTresorerieDTO1).isNotEqualTo(typeTresorerieDTO2);
        typeTresorerieDTO2.setId(typeTresorerieDTO1.getId());
        assertThat(typeTresorerieDTO1).isEqualTo(typeTresorerieDTO2);
        typeTresorerieDTO2.setId(2L);
        assertThat(typeTresorerieDTO1).isNotEqualTo(typeTresorerieDTO2);
        typeTresorerieDTO1.setId(null);
        assertThat(typeTresorerieDTO1).isNotEqualTo(typeTresorerieDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeTresorerieMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeTresorerieMapper.fromId(null)).isNull();
    }
}
