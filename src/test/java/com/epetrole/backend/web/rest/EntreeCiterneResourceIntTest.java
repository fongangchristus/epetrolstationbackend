package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.EntreeCiterne;
import com.epetrole.backend.repository.EntreeCiterneRepository;
import com.epetrole.backend.service.EntreeCiterneService;
import com.epetrole.backend.service.dto.EntreeCiterneDTO;
import com.epetrole.backend.service.mapper.EntreeCiterneMapper;
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
 * Test class for the EntreeCiterneResource REST controller.
 *
 * @see EntreeCiterneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class EntreeCiterneResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_VALEUR_MAX = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR_MAX = "BBBBBBBBBB";

    private static final String DEFAULT_VALEUR_ACTUEL = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR_ACTUEL = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITE = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITE = "BBBBBBBBBB";

    @Autowired
    private EntreeCiterneRepository entreeCiterneRepository;

    @Autowired
    private EntreeCiterneMapper entreeCiterneMapper;

    @Autowired
    private EntreeCiterneService entreeCiterneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntreeCiterneMockMvc;

    private EntreeCiterne entreeCiterne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntreeCiterneResource entreeCiterneResource = new EntreeCiterneResource(entreeCiterneService);
        this.restEntreeCiterneMockMvc = MockMvcBuilders.standaloneSetup(entreeCiterneResource)
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
    public static EntreeCiterne createEntity(EntityManager em) {
        EntreeCiterne entreeCiterne = new EntreeCiterne()
            .date(DEFAULT_DATE)
            .valeurMax(DEFAULT_VALEUR_MAX)
            .valeurActuel(DEFAULT_VALEUR_ACTUEL)
            .quantite(DEFAULT_QUANTITE);
        return entreeCiterne;
    }

    @Before
    public void initTest() {
        entreeCiterne = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntreeCiterne() throws Exception {
        int databaseSizeBeforeCreate = entreeCiterneRepository.findAll().size();

        // Create the EntreeCiterne
        EntreeCiterneDTO entreeCiterneDTO = entreeCiterneMapper.toDto(entreeCiterne);
        restEntreeCiterneMockMvc.perform(post("/api/entree-citernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeCiterneDTO)))
            .andExpect(status().isCreated());

        // Validate the EntreeCiterne in the database
        List<EntreeCiterne> entreeCiterneList = entreeCiterneRepository.findAll();
        assertThat(entreeCiterneList).hasSize(databaseSizeBeforeCreate + 1);
        EntreeCiterne testEntreeCiterne = entreeCiterneList.get(entreeCiterneList.size() - 1);
        assertThat(testEntreeCiterne.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEntreeCiterne.getValeurMax()).isEqualTo(DEFAULT_VALEUR_MAX);
        assertThat(testEntreeCiterne.getValeurActuel()).isEqualTo(DEFAULT_VALEUR_ACTUEL);
        assertThat(testEntreeCiterne.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    public void createEntreeCiterneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entreeCiterneRepository.findAll().size();

        // Create the EntreeCiterne with an existing ID
        entreeCiterne.setId(1L);
        EntreeCiterneDTO entreeCiterneDTO = entreeCiterneMapper.toDto(entreeCiterne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntreeCiterneMockMvc.perform(post("/api/entree-citernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeCiterneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntreeCiterne in the database
        List<EntreeCiterne> entreeCiterneList = entreeCiterneRepository.findAll();
        assertThat(entreeCiterneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEntreeCiternes() throws Exception {
        // Initialize the database
        entreeCiterneRepository.saveAndFlush(entreeCiterne);

        // Get all the entreeCiterneList
        restEntreeCiterneMockMvc.perform(get("/api/entree-citernes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entreeCiterne.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].valeurMax").value(hasItem(DEFAULT_VALEUR_MAX.toString())))
            .andExpect(jsonPath("$.[*].valeurActuel").value(hasItem(DEFAULT_VALEUR_ACTUEL.toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.toString())));
    }

    @Test
    @Transactional
    public void getEntreeCiterne() throws Exception {
        // Initialize the database
        entreeCiterneRepository.saveAndFlush(entreeCiterne);

        // Get the entreeCiterne
        restEntreeCiterneMockMvc.perform(get("/api/entree-citernes/{id}", entreeCiterne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entreeCiterne.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.valeurMax").value(DEFAULT_VALEUR_MAX.toString()))
            .andExpect(jsonPath("$.valeurActuel").value(DEFAULT_VALEUR_ACTUEL.toString()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntreeCiterne() throws Exception {
        // Get the entreeCiterne
        restEntreeCiterneMockMvc.perform(get("/api/entree-citernes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntreeCiterne() throws Exception {
        // Initialize the database
        entreeCiterneRepository.saveAndFlush(entreeCiterne);
        int databaseSizeBeforeUpdate = entreeCiterneRepository.findAll().size();

        // Update the entreeCiterne
        EntreeCiterne updatedEntreeCiterne = entreeCiterneRepository.findOne(entreeCiterne.getId());
        // Disconnect from session so that the updates on updatedEntreeCiterne are not directly saved in db
        em.detach(updatedEntreeCiterne);
        updatedEntreeCiterne
            .date(UPDATED_DATE)
            .valeurMax(UPDATED_VALEUR_MAX)
            .valeurActuel(UPDATED_VALEUR_ACTUEL)
            .quantite(UPDATED_QUANTITE);
        EntreeCiterneDTO entreeCiterneDTO = entreeCiterneMapper.toDto(updatedEntreeCiterne);

        restEntreeCiterneMockMvc.perform(put("/api/entree-citernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeCiterneDTO)))
            .andExpect(status().isOk());

        // Validate the EntreeCiterne in the database
        List<EntreeCiterne> entreeCiterneList = entreeCiterneRepository.findAll();
        assertThat(entreeCiterneList).hasSize(databaseSizeBeforeUpdate);
        EntreeCiterne testEntreeCiterne = entreeCiterneList.get(entreeCiterneList.size() - 1);
        assertThat(testEntreeCiterne.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEntreeCiterne.getValeurMax()).isEqualTo(UPDATED_VALEUR_MAX);
        assertThat(testEntreeCiterne.getValeurActuel()).isEqualTo(UPDATED_VALEUR_ACTUEL);
        assertThat(testEntreeCiterne.getQuantite()).isEqualTo(UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void updateNonExistingEntreeCiterne() throws Exception {
        int databaseSizeBeforeUpdate = entreeCiterneRepository.findAll().size();

        // Create the EntreeCiterne
        EntreeCiterneDTO entreeCiterneDTO = entreeCiterneMapper.toDto(entreeCiterne);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntreeCiterneMockMvc.perform(put("/api/entree-citernes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entreeCiterneDTO)))
            .andExpect(status().isCreated());

        // Validate the EntreeCiterne in the database
        List<EntreeCiterne> entreeCiterneList = entreeCiterneRepository.findAll();
        assertThat(entreeCiterneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntreeCiterne() throws Exception {
        // Initialize the database
        entreeCiterneRepository.saveAndFlush(entreeCiterne);
        int databaseSizeBeforeDelete = entreeCiterneRepository.findAll().size();

        // Get the entreeCiterne
        restEntreeCiterneMockMvc.perform(delete("/api/entree-citernes/{id}", entreeCiterne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EntreeCiterne> entreeCiterneList = entreeCiterneRepository.findAll();
        assertThat(entreeCiterneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntreeCiterne.class);
        EntreeCiterne entreeCiterne1 = new EntreeCiterne();
        entreeCiterne1.setId(1L);
        EntreeCiterne entreeCiterne2 = new EntreeCiterne();
        entreeCiterne2.setId(entreeCiterne1.getId());
        assertThat(entreeCiterne1).isEqualTo(entreeCiterne2);
        entreeCiterne2.setId(2L);
        assertThat(entreeCiterne1).isNotEqualTo(entreeCiterne2);
        entreeCiterne1.setId(null);
        assertThat(entreeCiterne1).isNotEqualTo(entreeCiterne2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntreeCiterneDTO.class);
        EntreeCiterneDTO entreeCiterneDTO1 = new EntreeCiterneDTO();
        entreeCiterneDTO1.setId(1L);
        EntreeCiterneDTO entreeCiterneDTO2 = new EntreeCiterneDTO();
        assertThat(entreeCiterneDTO1).isNotEqualTo(entreeCiterneDTO2);
        entreeCiterneDTO2.setId(entreeCiterneDTO1.getId());
        assertThat(entreeCiterneDTO1).isEqualTo(entreeCiterneDTO2);
        entreeCiterneDTO2.setId(2L);
        assertThat(entreeCiterneDTO1).isNotEqualTo(entreeCiterneDTO2);
        entreeCiterneDTO1.setId(null);
        assertThat(entreeCiterneDTO1).isNotEqualTo(entreeCiterneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(entreeCiterneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(entreeCiterneMapper.fromId(null)).isNull();
    }
}
