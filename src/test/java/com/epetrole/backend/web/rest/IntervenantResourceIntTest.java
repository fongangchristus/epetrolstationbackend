package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Intervenant;
import com.epetrole.backend.repository.IntervenantRepository;
import com.epetrole.backend.service.IntervenantService;
import com.epetrole.backend.service.dto.IntervenantDTO;
import com.epetrole.backend.service.mapper.IntervenantMapper;
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
 * Test class for the IntervenantResource REST controller.
 *
 * @see IntervenantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class IntervenantResourceIntTest {

    private static final String DEFAULT_IMAGE_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COMPLET = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COMPLET = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION = "BBBBBBBBBB";

    private static final String DEFAULT_CNI = "AAAAAAAAAA";
    private static final String UPDATED_CNI = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_SOLDE_INIT = "AAAAAAAAAA";
    private static final String UPDATED_SOLDE_INIT = "BBBBBBBBBB";

    @Autowired
    private IntervenantRepository intervenantRepository;

    @Autowired
    private IntervenantMapper intervenantMapper;

    @Autowired
    private IntervenantService intervenantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIntervenantMockMvc;

    private Intervenant intervenant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IntervenantResource intervenantResource = new IntervenantResource(intervenantService);
        this.restIntervenantMockMvc = MockMvcBuilders.standaloneSetup(intervenantResource)
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
    public static Intervenant createEntity(EntityManager em) {
        Intervenant intervenant = new Intervenant()
            .imageFile(DEFAULT_IMAGE_FILE)
            .nomComplet(DEFAULT_NOM_COMPLET)
            .fonction(DEFAULT_FONCTION)
            .cni(DEFAULT_CNI)
            .tel(DEFAULT_TEL)
            .addresse(DEFAULT_ADDRESSE)
            .soldeInit(DEFAULT_SOLDE_INIT);
        return intervenant;
    }

    @Before
    public void initTest() {
        intervenant = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntervenant() throws Exception {
        int databaseSizeBeforeCreate = intervenantRepository.findAll().size();

        // Create the Intervenant
        IntervenantDTO intervenantDTO = intervenantMapper.toDto(intervenant);
        restIntervenantMockMvc.perform(post("/api/intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervenantDTO)))
            .andExpect(status().isCreated());

        // Validate the Intervenant in the database
        List<Intervenant> intervenantList = intervenantRepository.findAll();
        assertThat(intervenantList).hasSize(databaseSizeBeforeCreate + 1);
        Intervenant testIntervenant = intervenantList.get(intervenantList.size() - 1);
        assertThat(testIntervenant.getImageFile()).isEqualTo(DEFAULT_IMAGE_FILE);
        assertThat(testIntervenant.getNomComplet()).isEqualTo(DEFAULT_NOM_COMPLET);
        assertThat(testIntervenant.getFonction()).isEqualTo(DEFAULT_FONCTION);
        assertThat(testIntervenant.getCni()).isEqualTo(DEFAULT_CNI);
        assertThat(testIntervenant.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testIntervenant.getAddresse()).isEqualTo(DEFAULT_ADDRESSE);
        assertThat(testIntervenant.getSoldeInit()).isEqualTo(DEFAULT_SOLDE_INIT);
    }

    @Test
    @Transactional
    public void createIntervenantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intervenantRepository.findAll().size();

        // Create the Intervenant with an existing ID
        intervenant.setId(1L);
        IntervenantDTO intervenantDTO = intervenantMapper.toDto(intervenant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntervenantMockMvc.perform(post("/api/intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervenantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Intervenant in the database
        List<Intervenant> intervenantList = intervenantRepository.findAll();
        assertThat(intervenantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIntervenants() throws Exception {
        // Initialize the database
        intervenantRepository.saveAndFlush(intervenant);

        // Get all the intervenantList
        restIntervenantMockMvc.perform(get("/api/intervenants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intervenant.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageFile").value(hasItem(DEFAULT_IMAGE_FILE.toString())))
            .andExpect(jsonPath("$.[*].nomComplet").value(hasItem(DEFAULT_NOM_COMPLET.toString())))
            .andExpect(jsonPath("$.[*].fonction").value(hasItem(DEFAULT_FONCTION.toString())))
            .andExpect(jsonPath("$.[*].cni").value(hasItem(DEFAULT_CNI.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
            .andExpect(jsonPath("$.[*].addresse").value(hasItem(DEFAULT_ADDRESSE.toString())))
            .andExpect(jsonPath("$.[*].soldeInit").value(hasItem(DEFAULT_SOLDE_INIT.toString())));
    }

    @Test
    @Transactional
    public void getIntervenant() throws Exception {
        // Initialize the database
        intervenantRepository.saveAndFlush(intervenant);

        // Get the intervenant
        restIntervenantMockMvc.perform(get("/api/intervenants/{id}", intervenant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(intervenant.getId().intValue()))
            .andExpect(jsonPath("$.imageFile").value(DEFAULT_IMAGE_FILE.toString()))
            .andExpect(jsonPath("$.nomComplet").value(DEFAULT_NOM_COMPLET.toString()))
            .andExpect(jsonPath("$.fonction").value(DEFAULT_FONCTION.toString()))
            .andExpect(jsonPath("$.cni").value(DEFAULT_CNI.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()))
            .andExpect(jsonPath("$.addresse").value(DEFAULT_ADDRESSE.toString()))
            .andExpect(jsonPath("$.soldeInit").value(DEFAULT_SOLDE_INIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIntervenant() throws Exception {
        // Get the intervenant
        restIntervenantMockMvc.perform(get("/api/intervenants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntervenant() throws Exception {
        // Initialize the database
        intervenantRepository.saveAndFlush(intervenant);
        int databaseSizeBeforeUpdate = intervenantRepository.findAll().size();

        // Update the intervenant
        Intervenant updatedIntervenant = intervenantRepository.findOne(intervenant.getId());
        // Disconnect from session so that the updates on updatedIntervenant are not directly saved in db
        em.detach(updatedIntervenant);
        updatedIntervenant
            .imageFile(UPDATED_IMAGE_FILE)
            .nomComplet(UPDATED_NOM_COMPLET)
            .fonction(UPDATED_FONCTION)
            .cni(UPDATED_CNI)
            .tel(UPDATED_TEL)
            .addresse(UPDATED_ADDRESSE)
            .soldeInit(UPDATED_SOLDE_INIT);
        IntervenantDTO intervenantDTO = intervenantMapper.toDto(updatedIntervenant);

        restIntervenantMockMvc.perform(put("/api/intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervenantDTO)))
            .andExpect(status().isOk());

        // Validate the Intervenant in the database
        List<Intervenant> intervenantList = intervenantRepository.findAll();
        assertThat(intervenantList).hasSize(databaseSizeBeforeUpdate);
        Intervenant testIntervenant = intervenantList.get(intervenantList.size() - 1);
        assertThat(testIntervenant.getImageFile()).isEqualTo(UPDATED_IMAGE_FILE);
        assertThat(testIntervenant.getNomComplet()).isEqualTo(UPDATED_NOM_COMPLET);
        assertThat(testIntervenant.getFonction()).isEqualTo(UPDATED_FONCTION);
        assertThat(testIntervenant.getCni()).isEqualTo(UPDATED_CNI);
        assertThat(testIntervenant.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testIntervenant.getAddresse()).isEqualTo(UPDATED_ADDRESSE);
        assertThat(testIntervenant.getSoldeInit()).isEqualTo(UPDATED_SOLDE_INIT);
    }

    @Test
    @Transactional
    public void updateNonExistingIntervenant() throws Exception {
        int databaseSizeBeforeUpdate = intervenantRepository.findAll().size();

        // Create the Intervenant
        IntervenantDTO intervenantDTO = intervenantMapper.toDto(intervenant);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIntervenantMockMvc.perform(put("/api/intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervenantDTO)))
            .andExpect(status().isCreated());

        // Validate the Intervenant in the database
        List<Intervenant> intervenantList = intervenantRepository.findAll();
        assertThat(intervenantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIntervenant() throws Exception {
        // Initialize the database
        intervenantRepository.saveAndFlush(intervenant);
        int databaseSizeBeforeDelete = intervenantRepository.findAll().size();

        // Get the intervenant
        restIntervenantMockMvc.perform(delete("/api/intervenants/{id}", intervenant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Intervenant> intervenantList = intervenantRepository.findAll();
        assertThat(intervenantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Intervenant.class);
        Intervenant intervenant1 = new Intervenant();
        intervenant1.setId(1L);
        Intervenant intervenant2 = new Intervenant();
        intervenant2.setId(intervenant1.getId());
        assertThat(intervenant1).isEqualTo(intervenant2);
        intervenant2.setId(2L);
        assertThat(intervenant1).isNotEqualTo(intervenant2);
        intervenant1.setId(null);
        assertThat(intervenant1).isNotEqualTo(intervenant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntervenantDTO.class);
        IntervenantDTO intervenantDTO1 = new IntervenantDTO();
        intervenantDTO1.setId(1L);
        IntervenantDTO intervenantDTO2 = new IntervenantDTO();
        assertThat(intervenantDTO1).isNotEqualTo(intervenantDTO2);
        intervenantDTO2.setId(intervenantDTO1.getId());
        assertThat(intervenantDTO1).isEqualTo(intervenantDTO2);
        intervenantDTO2.setId(2L);
        assertThat(intervenantDTO1).isNotEqualTo(intervenantDTO2);
        intervenantDTO1.setId(null);
        assertThat(intervenantDTO1).isNotEqualTo(intervenantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(intervenantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(intervenantMapper.fromId(null)).isNull();
    }
}
