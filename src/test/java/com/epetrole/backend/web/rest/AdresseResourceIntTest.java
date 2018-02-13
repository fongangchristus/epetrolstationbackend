package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Adresse;
import com.epetrole.backend.repository.AdresseRepository;
import com.epetrole.backend.service.AdresseService;
import com.epetrole.backend.service.dto.AdresseDTO;
import com.epetrole.backend.service.mapper.AdresseMapper;
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
 * Test class for the AdresseResource REST controller.
 *
 * @see AdresseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class AdresseResourceIntTest {

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_QUARTIER = "AAAAAAAAAA";
    private static final String UPDATED_QUARTIER = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    @Autowired
    private AdresseRepository adresseRepository;

    @Autowired
    private AdresseMapper adresseMapper;

    @Autowired
    private AdresseService adresseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdresseMockMvc;

    private Adresse adresse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdresseResource adresseResource = new AdresseResource(adresseService);
        this.restAdresseMockMvc = MockMvcBuilders.standaloneSetup(adresseResource)
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
    public static Adresse createEntity(EntityManager em) {
        Adresse adresse = new Adresse()
            .pays(DEFAULT_PAYS)
            .region(DEFAULT_REGION)
            .ville(DEFAULT_VILLE)
            .quartier(DEFAULT_QUARTIER)
            .codePostal(DEFAULT_CODE_POSTAL);
        return adresse;
    }

    @Before
    public void initTest() {
        adresse = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdresse() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);
        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isCreated());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate + 1);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testAdresse.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testAdresse.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdresse.getQuartier()).isEqualTo(DEFAULT_QUARTIER);
        assertThat(testAdresse.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void createAdresseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse with an existing ID
        adresse.setId(1L);
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdresses() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList
        restAdresseMockMvc.perform(get("/api/adresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].quartier").value(hasItem(DEFAULT_QUARTIER.toString())))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL.toString())));
    }

    @Test
    @Transactional
    public void getAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adresse.getId().intValue()))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.quartier").value(DEFAULT_QUARTIER.toString()))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdresse() throws Exception {
        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Update the adresse
        Adresse updatedAdresse = adresseRepository.findOne(adresse.getId());
        // Disconnect from session so that the updates on updatedAdresse are not directly saved in db
        em.detach(updatedAdresse);
        updatedAdresse
            .pays(UPDATED_PAYS)
            .region(UPDATED_REGION)
            .ville(UPDATED_VILLE)
            .quartier(UPDATED_QUARTIER)
            .codePostal(UPDATED_CODE_POSTAL);
        AdresseDTO adresseDTO = adresseMapper.toDto(updatedAdresse);

        restAdresseMockMvc.perform(put("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isOk());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testAdresse.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testAdresse.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresse.getQuartier()).isEqualTo(UPDATED_QUARTIER);
        assertThat(testAdresse.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingAdresse() throws Exception {
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdresseMockMvc.perform(put("/api/adresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isCreated());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        int databaseSizeBeforeDelete = adresseRepository.findAll().size();

        // Get the adresse
        restAdresseMockMvc.perform(delete("/api/adresses/{id}", adresse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adresse.class);
        Adresse adresse1 = new Adresse();
        adresse1.setId(1L);
        Adresse adresse2 = new Adresse();
        adresse2.setId(adresse1.getId());
        assertThat(adresse1).isEqualTo(adresse2);
        adresse2.setId(2L);
        assertThat(adresse1).isNotEqualTo(adresse2);
        adresse1.setId(null);
        assertThat(adresse1).isNotEqualTo(adresse2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdresseDTO.class);
        AdresseDTO adresseDTO1 = new AdresseDTO();
        adresseDTO1.setId(1L);
        AdresseDTO adresseDTO2 = new AdresseDTO();
        assertThat(adresseDTO1).isNotEqualTo(adresseDTO2);
        adresseDTO2.setId(adresseDTO1.getId());
        assertThat(adresseDTO1).isEqualTo(adresseDTO2);
        adresseDTO2.setId(2L);
        assertThat(adresseDTO1).isNotEqualTo(adresseDTO2);
        adresseDTO1.setId(null);
        assertThat(adresseDTO1).isNotEqualTo(adresseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adresseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adresseMapper.fromId(null)).isNull();
    }
}
