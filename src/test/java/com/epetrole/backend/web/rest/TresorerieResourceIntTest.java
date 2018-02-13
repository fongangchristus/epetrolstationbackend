package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Tresorerie;
import com.epetrole.backend.repository.TresorerieRepository;
import com.epetrole.backend.service.TresorerieService;
import com.epetrole.backend.service.dto.TresorerieDTO;
import com.epetrole.backend.service.mapper.TresorerieMapper;
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
 * Test class for the TresorerieResource REST controller.
 *
 * @see TresorerieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class TresorerieResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_RIB = "AAAAAAAAAA";
    private static final String UPDATED_CODE_RIB = "BBBBBBBBBB";

    private static final String DEFAULT_SOLDE_RESERVE = "AAAAAAAAAA";
    private static final String UPDATED_SOLDE_RESERVE = "BBBBBBBBBB";

    private static final String DEFAULT_SOLDE_OUVERTURE = "AAAAAAAAAA";
    private static final String UPDATED_SOLDE_OUVERTURE = "BBBBBBBBBB";

    private static final String DEFAULT_CHIFFRE_COMPTABLE = "AAAAAAAAAA";
    private static final String UPDATED_CHIFFRE_COMPTABLE = "BBBBBBBBBB";

    @Autowired
    private TresorerieRepository tresorerieRepository;

    @Autowired
    private TresorerieMapper tresorerieMapper;

    @Autowired
    private TresorerieService tresorerieService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTresorerieMockMvc;

    private Tresorerie tresorerie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TresorerieResource tresorerieResource = new TresorerieResource(tresorerieService);
        this.restTresorerieMockMvc = MockMvcBuilders.standaloneSetup(tresorerieResource)
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
    public static Tresorerie createEntity(EntityManager em) {
        Tresorerie tresorerie = new Tresorerie()
            .libele(DEFAULT_LIBELE)
            .codeRib(DEFAULT_CODE_RIB)
            .soldeReserve(DEFAULT_SOLDE_RESERVE)
            .soldeOuverture(DEFAULT_SOLDE_OUVERTURE)
            .chiffreComptable(DEFAULT_CHIFFRE_COMPTABLE);
        return tresorerie;
    }

    @Before
    public void initTest() {
        tresorerie = createEntity(em);
    }

    @Test
    @Transactional
    public void createTresorerie() throws Exception {
        int databaseSizeBeforeCreate = tresorerieRepository.findAll().size();

        // Create the Tresorerie
        TresorerieDTO tresorerieDTO = tresorerieMapper.toDto(tresorerie);
        restTresorerieMockMvc.perform(post("/api/tresoreries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tresorerieDTO)))
            .andExpect(status().isCreated());

        // Validate the Tresorerie in the database
        List<Tresorerie> tresorerieList = tresorerieRepository.findAll();
        assertThat(tresorerieList).hasSize(databaseSizeBeforeCreate + 1);
        Tresorerie testTresorerie = tresorerieList.get(tresorerieList.size() - 1);
        assertThat(testTresorerie.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testTresorerie.getCodeRib()).isEqualTo(DEFAULT_CODE_RIB);
        assertThat(testTresorerie.getSoldeReserve()).isEqualTo(DEFAULT_SOLDE_RESERVE);
        assertThat(testTresorerie.getSoldeOuverture()).isEqualTo(DEFAULT_SOLDE_OUVERTURE);
        assertThat(testTresorerie.getChiffreComptable()).isEqualTo(DEFAULT_CHIFFRE_COMPTABLE);
    }

    @Test
    @Transactional
    public void createTresorerieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tresorerieRepository.findAll().size();

        // Create the Tresorerie with an existing ID
        tresorerie.setId(1L);
        TresorerieDTO tresorerieDTO = tresorerieMapper.toDto(tresorerie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTresorerieMockMvc.perform(post("/api/tresoreries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tresorerieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tresorerie in the database
        List<Tresorerie> tresorerieList = tresorerieRepository.findAll();
        assertThat(tresorerieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTresoreries() throws Exception {
        // Initialize the database
        tresorerieRepository.saveAndFlush(tresorerie);

        // Get all the tresorerieList
        restTresorerieMockMvc.perform(get("/api/tresoreries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tresorerie.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].codeRib").value(hasItem(DEFAULT_CODE_RIB.toString())))
            .andExpect(jsonPath("$.[*].soldeReserve").value(hasItem(DEFAULT_SOLDE_RESERVE.toString())))
            .andExpect(jsonPath("$.[*].soldeOuverture").value(hasItem(DEFAULT_SOLDE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].chiffreComptable").value(hasItem(DEFAULT_CHIFFRE_COMPTABLE.toString())));
    }

    @Test
    @Transactional
    public void getTresorerie() throws Exception {
        // Initialize the database
        tresorerieRepository.saveAndFlush(tresorerie);

        // Get the tresorerie
        restTresorerieMockMvc.perform(get("/api/tresoreries/{id}", tresorerie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tresorerie.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.codeRib").value(DEFAULT_CODE_RIB.toString()))
            .andExpect(jsonPath("$.soldeReserve").value(DEFAULT_SOLDE_RESERVE.toString()))
            .andExpect(jsonPath("$.soldeOuverture").value(DEFAULT_SOLDE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.chiffreComptable").value(DEFAULT_CHIFFRE_COMPTABLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTresorerie() throws Exception {
        // Get the tresorerie
        restTresorerieMockMvc.perform(get("/api/tresoreries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTresorerie() throws Exception {
        // Initialize the database
        tresorerieRepository.saveAndFlush(tresorerie);
        int databaseSizeBeforeUpdate = tresorerieRepository.findAll().size();

        // Update the tresorerie
        Tresorerie updatedTresorerie = tresorerieRepository.findOne(tresorerie.getId());
        // Disconnect from session so that the updates on updatedTresorerie are not directly saved in db
        em.detach(updatedTresorerie);
        updatedTresorerie
            .libele(UPDATED_LIBELE)
            .codeRib(UPDATED_CODE_RIB)
            .soldeReserve(UPDATED_SOLDE_RESERVE)
            .soldeOuverture(UPDATED_SOLDE_OUVERTURE)
            .chiffreComptable(UPDATED_CHIFFRE_COMPTABLE);
        TresorerieDTO tresorerieDTO = tresorerieMapper.toDto(updatedTresorerie);

        restTresorerieMockMvc.perform(put("/api/tresoreries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tresorerieDTO)))
            .andExpect(status().isOk());

        // Validate the Tresorerie in the database
        List<Tresorerie> tresorerieList = tresorerieRepository.findAll();
        assertThat(tresorerieList).hasSize(databaseSizeBeforeUpdate);
        Tresorerie testTresorerie = tresorerieList.get(tresorerieList.size() - 1);
        assertThat(testTresorerie.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testTresorerie.getCodeRib()).isEqualTo(UPDATED_CODE_RIB);
        assertThat(testTresorerie.getSoldeReserve()).isEqualTo(UPDATED_SOLDE_RESERVE);
        assertThat(testTresorerie.getSoldeOuverture()).isEqualTo(UPDATED_SOLDE_OUVERTURE);
        assertThat(testTresorerie.getChiffreComptable()).isEqualTo(UPDATED_CHIFFRE_COMPTABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTresorerie() throws Exception {
        int databaseSizeBeforeUpdate = tresorerieRepository.findAll().size();

        // Create the Tresorerie
        TresorerieDTO tresorerieDTO = tresorerieMapper.toDto(tresorerie);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTresorerieMockMvc.perform(put("/api/tresoreries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tresorerieDTO)))
            .andExpect(status().isCreated());

        // Validate the Tresorerie in the database
        List<Tresorerie> tresorerieList = tresorerieRepository.findAll();
        assertThat(tresorerieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTresorerie() throws Exception {
        // Initialize the database
        tresorerieRepository.saveAndFlush(tresorerie);
        int databaseSizeBeforeDelete = tresorerieRepository.findAll().size();

        // Get the tresorerie
        restTresorerieMockMvc.perform(delete("/api/tresoreries/{id}", tresorerie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tresorerie> tresorerieList = tresorerieRepository.findAll();
        assertThat(tresorerieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tresorerie.class);
        Tresorerie tresorerie1 = new Tresorerie();
        tresorerie1.setId(1L);
        Tresorerie tresorerie2 = new Tresorerie();
        tresorerie2.setId(tresorerie1.getId());
        assertThat(tresorerie1).isEqualTo(tresorerie2);
        tresorerie2.setId(2L);
        assertThat(tresorerie1).isNotEqualTo(tresorerie2);
        tresorerie1.setId(null);
        assertThat(tresorerie1).isNotEqualTo(tresorerie2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TresorerieDTO.class);
        TresorerieDTO tresorerieDTO1 = new TresorerieDTO();
        tresorerieDTO1.setId(1L);
        TresorerieDTO tresorerieDTO2 = new TresorerieDTO();
        assertThat(tresorerieDTO1).isNotEqualTo(tresorerieDTO2);
        tresorerieDTO2.setId(tresorerieDTO1.getId());
        assertThat(tresorerieDTO1).isEqualTo(tresorerieDTO2);
        tresorerieDTO2.setId(2L);
        assertThat(tresorerieDTO1).isNotEqualTo(tresorerieDTO2);
        tresorerieDTO1.setId(null);
        assertThat(tresorerieDTO1).isNotEqualTo(tresorerieDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tresorerieMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tresorerieMapper.fromId(null)).isNull();
    }
}
