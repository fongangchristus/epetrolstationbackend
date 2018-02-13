package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Unite;
import com.epetrole.backend.repository.UniteRepository;
import com.epetrole.backend.service.UniteService;
import com.epetrole.backend.service.dto.UniteDTO;
import com.epetrole.backend.service.mapper.UniteMapper;
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
 * Test class for the UniteResource REST controller.
 *
 * @see UniteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class UniteResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final String DEFAULT_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_EQUIV_EN_LITRE = "AAAAAAAAAA";
    private static final String UPDATED_EQUIV_EN_LITRE = "BBBBBBBBBB";

    @Autowired
    private UniteRepository uniteRepository;

    @Autowired
    private UniteMapper uniteMapper;

    @Autowired
    private UniteService uniteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUniteMockMvc;

    private Unite unite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UniteResource uniteResource = new UniteResource(uniteService);
        this.restUniteMockMvc = MockMvcBuilders.standaloneSetup(uniteResource)
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
    public static Unite createEntity(EntityManager em) {
        Unite unite = new Unite()
            .libele(DEFAULT_LIBELE)
            .abreviation(DEFAULT_ABREVIATION)
            .equivEnLitre(DEFAULT_EQUIV_EN_LITRE);
        return unite;
    }

    @Before
    public void initTest() {
        unite = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnite() throws Exception {
        int databaseSizeBeforeCreate = uniteRepository.findAll().size();

        // Create the Unite
        UniteDTO uniteDTO = uniteMapper.toDto(unite);
        restUniteMockMvc.perform(post("/api/unites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uniteDTO)))
            .andExpect(status().isCreated());

        // Validate the Unite in the database
        List<Unite> uniteList = uniteRepository.findAll();
        assertThat(uniteList).hasSize(databaseSizeBeforeCreate + 1);
        Unite testUnite = uniteList.get(uniteList.size() - 1);
        assertThat(testUnite.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testUnite.getAbreviation()).isEqualTo(DEFAULT_ABREVIATION);
        assertThat(testUnite.getEquivEnLitre()).isEqualTo(DEFAULT_EQUIV_EN_LITRE);
    }

    @Test
    @Transactional
    public void createUniteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uniteRepository.findAll().size();

        // Create the Unite with an existing ID
        unite.setId(1L);
        UniteDTO uniteDTO = uniteMapper.toDto(unite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniteMockMvc.perform(post("/api/unites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uniteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Unite in the database
        List<Unite> uniteList = uniteRepository.findAll();
        assertThat(uniteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUnites() throws Exception {
        // Initialize the database
        uniteRepository.saveAndFlush(unite);

        // Get all the uniteList
        restUniteMockMvc.perform(get("/api/unites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].abreviation").value(hasItem(DEFAULT_ABREVIATION.toString())))
            .andExpect(jsonPath("$.[*].equivEnLitre").value(hasItem(DEFAULT_EQUIV_EN_LITRE.toString())));
    }

    @Test
    @Transactional
    public void getUnite() throws Exception {
        // Initialize the database
        uniteRepository.saveAndFlush(unite);

        // Get the unite
        restUniteMockMvc.perform(get("/api/unites/{id}", unite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unite.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.abreviation").value(DEFAULT_ABREVIATION.toString()))
            .andExpect(jsonPath("$.equivEnLitre").value(DEFAULT_EQUIV_EN_LITRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnite() throws Exception {
        // Get the unite
        restUniteMockMvc.perform(get("/api/unites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnite() throws Exception {
        // Initialize the database
        uniteRepository.saveAndFlush(unite);
        int databaseSizeBeforeUpdate = uniteRepository.findAll().size();

        // Update the unite
        Unite updatedUnite = uniteRepository.findOne(unite.getId());
        // Disconnect from session so that the updates on updatedUnite are not directly saved in db
        em.detach(updatedUnite);
        updatedUnite
            .libele(UPDATED_LIBELE)
            .abreviation(UPDATED_ABREVIATION)
            .equivEnLitre(UPDATED_EQUIV_EN_LITRE);
        UniteDTO uniteDTO = uniteMapper.toDto(updatedUnite);

        restUniteMockMvc.perform(put("/api/unites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uniteDTO)))
            .andExpect(status().isOk());

        // Validate the Unite in the database
        List<Unite> uniteList = uniteRepository.findAll();
        assertThat(uniteList).hasSize(databaseSizeBeforeUpdate);
        Unite testUnite = uniteList.get(uniteList.size() - 1);
        assertThat(testUnite.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testUnite.getAbreviation()).isEqualTo(UPDATED_ABREVIATION);
        assertThat(testUnite.getEquivEnLitre()).isEqualTo(UPDATED_EQUIV_EN_LITRE);
    }

    @Test
    @Transactional
    public void updateNonExistingUnite() throws Exception {
        int databaseSizeBeforeUpdate = uniteRepository.findAll().size();

        // Create the Unite
        UniteDTO uniteDTO = uniteMapper.toDto(unite);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUniteMockMvc.perform(put("/api/unites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uniteDTO)))
            .andExpect(status().isCreated());

        // Validate the Unite in the database
        List<Unite> uniteList = uniteRepository.findAll();
        assertThat(uniteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUnite() throws Exception {
        // Initialize the database
        uniteRepository.saveAndFlush(unite);
        int databaseSizeBeforeDelete = uniteRepository.findAll().size();

        // Get the unite
        restUniteMockMvc.perform(delete("/api/unites/{id}", unite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Unite> uniteList = uniteRepository.findAll();
        assertThat(uniteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Unite.class);
        Unite unite1 = new Unite();
        unite1.setId(1L);
        Unite unite2 = new Unite();
        unite2.setId(unite1.getId());
        assertThat(unite1).isEqualTo(unite2);
        unite2.setId(2L);
        assertThat(unite1).isNotEqualTo(unite2);
        unite1.setId(null);
        assertThat(unite1).isNotEqualTo(unite2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniteDTO.class);
        UniteDTO uniteDTO1 = new UniteDTO();
        uniteDTO1.setId(1L);
        UniteDTO uniteDTO2 = new UniteDTO();
        assertThat(uniteDTO1).isNotEqualTo(uniteDTO2);
        uniteDTO2.setId(uniteDTO1.getId());
        assertThat(uniteDTO1).isEqualTo(uniteDTO2);
        uniteDTO2.setId(2L);
        assertThat(uniteDTO1).isNotEqualTo(uniteDTO2);
        uniteDTO1.setId(null);
        assertThat(uniteDTO1).isNotEqualTo(uniteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uniteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uniteMapper.fromId(null)).isNull();
    }
}
