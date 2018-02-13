package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.TypeIntervenant;
import com.epetrole.backend.repository.TypeIntervenantRepository;
import com.epetrole.backend.service.TypeIntervenantService;
import com.epetrole.backend.service.dto.TypeIntervenantDTO;
import com.epetrole.backend.service.mapper.TypeIntervenantMapper;
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
 * Test class for the TypeIntervenantResource REST controller.
 *
 * @see TypeIntervenantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class TypeIntervenantResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TypeIntervenantRepository typeIntervenantRepository;

    @Autowired
    private TypeIntervenantMapper typeIntervenantMapper;

    @Autowired
    private TypeIntervenantService typeIntervenantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeIntervenantMockMvc;

    private TypeIntervenant typeIntervenant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeIntervenantResource typeIntervenantResource = new TypeIntervenantResource(typeIntervenantService);
        this.restTypeIntervenantMockMvc = MockMvcBuilders.standaloneSetup(typeIntervenantResource)
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
    public static TypeIntervenant createEntity(EntityManager em) {
        TypeIntervenant typeIntervenant = new TypeIntervenant()
            .libele(DEFAULT_LIBELE)
            .description(DEFAULT_DESCRIPTION);
        return typeIntervenant;
    }

    @Before
    public void initTest() {
        typeIntervenant = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeIntervenant() throws Exception {
        int databaseSizeBeforeCreate = typeIntervenantRepository.findAll().size();

        // Create the TypeIntervenant
        TypeIntervenantDTO typeIntervenantDTO = typeIntervenantMapper.toDto(typeIntervenant);
        restTypeIntervenantMockMvc.perform(post("/api/type-intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeIntervenantDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeIntervenant in the database
        List<TypeIntervenant> typeIntervenantList = typeIntervenantRepository.findAll();
        assertThat(typeIntervenantList).hasSize(databaseSizeBeforeCreate + 1);
        TypeIntervenant testTypeIntervenant = typeIntervenantList.get(typeIntervenantList.size() - 1);
        assertThat(testTypeIntervenant.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testTypeIntervenant.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTypeIntervenantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeIntervenantRepository.findAll().size();

        // Create the TypeIntervenant with an existing ID
        typeIntervenant.setId(1L);
        TypeIntervenantDTO typeIntervenantDTO = typeIntervenantMapper.toDto(typeIntervenant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeIntervenantMockMvc.perform(post("/api/type-intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeIntervenantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeIntervenant in the database
        List<TypeIntervenant> typeIntervenantList = typeIntervenantRepository.findAll();
        assertThat(typeIntervenantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeIntervenants() throws Exception {
        // Initialize the database
        typeIntervenantRepository.saveAndFlush(typeIntervenant);

        // Get all the typeIntervenantList
        restTypeIntervenantMockMvc.perform(get("/api/type-intervenants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeIntervenant.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTypeIntervenant() throws Exception {
        // Initialize the database
        typeIntervenantRepository.saveAndFlush(typeIntervenant);

        // Get the typeIntervenant
        restTypeIntervenantMockMvc.perform(get("/api/type-intervenants/{id}", typeIntervenant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeIntervenant.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeIntervenant() throws Exception {
        // Get the typeIntervenant
        restTypeIntervenantMockMvc.perform(get("/api/type-intervenants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeIntervenant() throws Exception {
        // Initialize the database
        typeIntervenantRepository.saveAndFlush(typeIntervenant);
        int databaseSizeBeforeUpdate = typeIntervenantRepository.findAll().size();

        // Update the typeIntervenant
        TypeIntervenant updatedTypeIntervenant = typeIntervenantRepository.findOne(typeIntervenant.getId());
        // Disconnect from session so that the updates on updatedTypeIntervenant are not directly saved in db
        em.detach(updatedTypeIntervenant);
        updatedTypeIntervenant
            .libele(UPDATED_LIBELE)
            .description(UPDATED_DESCRIPTION);
        TypeIntervenantDTO typeIntervenantDTO = typeIntervenantMapper.toDto(updatedTypeIntervenant);

        restTypeIntervenantMockMvc.perform(put("/api/type-intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeIntervenantDTO)))
            .andExpect(status().isOk());

        // Validate the TypeIntervenant in the database
        List<TypeIntervenant> typeIntervenantList = typeIntervenantRepository.findAll();
        assertThat(typeIntervenantList).hasSize(databaseSizeBeforeUpdate);
        TypeIntervenant testTypeIntervenant = typeIntervenantList.get(typeIntervenantList.size() - 1);
        assertThat(testTypeIntervenant.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testTypeIntervenant.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeIntervenant() throws Exception {
        int databaseSizeBeforeUpdate = typeIntervenantRepository.findAll().size();

        // Create the TypeIntervenant
        TypeIntervenantDTO typeIntervenantDTO = typeIntervenantMapper.toDto(typeIntervenant);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeIntervenantMockMvc.perform(put("/api/type-intervenants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeIntervenantDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeIntervenant in the database
        List<TypeIntervenant> typeIntervenantList = typeIntervenantRepository.findAll();
        assertThat(typeIntervenantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeIntervenant() throws Exception {
        // Initialize the database
        typeIntervenantRepository.saveAndFlush(typeIntervenant);
        int databaseSizeBeforeDelete = typeIntervenantRepository.findAll().size();

        // Get the typeIntervenant
        restTypeIntervenantMockMvc.perform(delete("/api/type-intervenants/{id}", typeIntervenant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeIntervenant> typeIntervenantList = typeIntervenantRepository.findAll();
        assertThat(typeIntervenantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeIntervenant.class);
        TypeIntervenant typeIntervenant1 = new TypeIntervenant();
        typeIntervenant1.setId(1L);
        TypeIntervenant typeIntervenant2 = new TypeIntervenant();
        typeIntervenant2.setId(typeIntervenant1.getId());
        assertThat(typeIntervenant1).isEqualTo(typeIntervenant2);
        typeIntervenant2.setId(2L);
        assertThat(typeIntervenant1).isNotEqualTo(typeIntervenant2);
        typeIntervenant1.setId(null);
        assertThat(typeIntervenant1).isNotEqualTo(typeIntervenant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeIntervenantDTO.class);
        TypeIntervenantDTO typeIntervenantDTO1 = new TypeIntervenantDTO();
        typeIntervenantDTO1.setId(1L);
        TypeIntervenantDTO typeIntervenantDTO2 = new TypeIntervenantDTO();
        assertThat(typeIntervenantDTO1).isNotEqualTo(typeIntervenantDTO2);
        typeIntervenantDTO2.setId(typeIntervenantDTO1.getId());
        assertThat(typeIntervenantDTO1).isEqualTo(typeIntervenantDTO2);
        typeIntervenantDTO2.setId(2L);
        assertThat(typeIntervenantDTO1).isNotEqualTo(typeIntervenantDTO2);
        typeIntervenantDTO1.setId(null);
        assertThat(typeIntervenantDTO1).isNotEqualTo(typeIntervenantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeIntervenantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeIntervenantMapper.fromId(null)).isNull();
    }
}
