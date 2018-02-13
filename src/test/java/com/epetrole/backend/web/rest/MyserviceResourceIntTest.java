package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Myservice;
import com.epetrole.backend.repository.MyserviceRepository;
import com.epetrole.backend.service.MyserviceService;
import com.epetrole.backend.service.dto.MyserviceDTO;
import com.epetrole.backend.service.mapper.MyserviceMapper;
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
 * Test class for the MyserviceResource REST controller.
 *
 * @see MyserviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class MyserviceResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MyserviceRepository myserviceRepository;

    @Autowired
    private MyserviceMapper myserviceMapper;

    @Autowired
    private MyserviceService myserviceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMyserviceMockMvc;

    private Myservice myservice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MyserviceResource myserviceResource = new MyserviceResource(myserviceService);
        this.restMyserviceMockMvc = MockMvcBuilders.standaloneSetup(myserviceResource)
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
    public static Myservice createEntity(EntityManager em) {
        Myservice myservice = new Myservice()
            .libele(DEFAULT_LIBELE)
            .description(DEFAULT_DESCRIPTION);
        return myservice;
    }

    @Before
    public void initTest() {
        myservice = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyservice() throws Exception {
        int databaseSizeBeforeCreate = myserviceRepository.findAll().size();

        // Create the Myservice
        MyserviceDTO myserviceDTO = myserviceMapper.toDto(myservice);
        restMyserviceMockMvc.perform(post("/api/myservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myserviceDTO)))
            .andExpect(status().isCreated());

        // Validate the Myservice in the database
        List<Myservice> myserviceList = myserviceRepository.findAll();
        assertThat(myserviceList).hasSize(databaseSizeBeforeCreate + 1);
        Myservice testMyservice = myserviceList.get(myserviceList.size() - 1);
        assertThat(testMyservice.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testMyservice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMyserviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myserviceRepository.findAll().size();

        // Create the Myservice with an existing ID
        myservice.setId(1L);
        MyserviceDTO myserviceDTO = myserviceMapper.toDto(myservice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyserviceMockMvc.perform(post("/api/myservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myserviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Myservice in the database
        List<Myservice> myserviceList = myserviceRepository.findAll();
        assertThat(myserviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMyservices() throws Exception {
        // Initialize the database
        myserviceRepository.saveAndFlush(myservice);

        // Get all the myserviceList
        restMyserviceMockMvc.perform(get("/api/myservices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myservice.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getMyservice() throws Exception {
        // Initialize the database
        myserviceRepository.saveAndFlush(myservice);

        // Get the myservice
        restMyserviceMockMvc.perform(get("/api/myservices/{id}", myservice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myservice.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyservice() throws Exception {
        // Get the myservice
        restMyserviceMockMvc.perform(get("/api/myservices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyservice() throws Exception {
        // Initialize the database
        myserviceRepository.saveAndFlush(myservice);
        int databaseSizeBeforeUpdate = myserviceRepository.findAll().size();

        // Update the myservice
        Myservice updatedMyservice = myserviceRepository.findOne(myservice.getId());
        // Disconnect from session so that the updates on updatedMyservice are not directly saved in db
        em.detach(updatedMyservice);
        updatedMyservice
            .libele(UPDATED_LIBELE)
            .description(UPDATED_DESCRIPTION);
        MyserviceDTO myserviceDTO = myserviceMapper.toDto(updatedMyservice);

        restMyserviceMockMvc.perform(put("/api/myservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myserviceDTO)))
            .andExpect(status().isOk());

        // Validate the Myservice in the database
        List<Myservice> myserviceList = myserviceRepository.findAll();
        assertThat(myserviceList).hasSize(databaseSizeBeforeUpdate);
        Myservice testMyservice = myserviceList.get(myserviceList.size() - 1);
        assertThat(testMyservice.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testMyservice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMyservice() throws Exception {
        int databaseSizeBeforeUpdate = myserviceRepository.findAll().size();

        // Create the Myservice
        MyserviceDTO myserviceDTO = myserviceMapper.toDto(myservice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMyserviceMockMvc.perform(put("/api/myservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myserviceDTO)))
            .andExpect(status().isCreated());

        // Validate the Myservice in the database
        List<Myservice> myserviceList = myserviceRepository.findAll();
        assertThat(myserviceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMyservice() throws Exception {
        // Initialize the database
        myserviceRepository.saveAndFlush(myservice);
        int databaseSizeBeforeDelete = myserviceRepository.findAll().size();

        // Get the myservice
        restMyserviceMockMvc.perform(delete("/api/myservices/{id}", myservice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Myservice> myserviceList = myserviceRepository.findAll();
        assertThat(myserviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Myservice.class);
        Myservice myservice1 = new Myservice();
        myservice1.setId(1L);
        Myservice myservice2 = new Myservice();
        myservice2.setId(myservice1.getId());
        assertThat(myservice1).isEqualTo(myservice2);
        myservice2.setId(2L);
        assertThat(myservice1).isNotEqualTo(myservice2);
        myservice1.setId(null);
        assertThat(myservice1).isNotEqualTo(myservice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyserviceDTO.class);
        MyserviceDTO myserviceDTO1 = new MyserviceDTO();
        myserviceDTO1.setId(1L);
        MyserviceDTO myserviceDTO2 = new MyserviceDTO();
        assertThat(myserviceDTO1).isNotEqualTo(myserviceDTO2);
        myserviceDTO2.setId(myserviceDTO1.getId());
        assertThat(myserviceDTO1).isEqualTo(myserviceDTO2);
        myserviceDTO2.setId(2L);
        assertThat(myserviceDTO1).isNotEqualTo(myserviceDTO2);
        myserviceDTO1.setId(null);
        assertThat(myserviceDTO1).isNotEqualTo(myserviceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(myserviceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(myserviceMapper.fromId(null)).isNull();
    }
}
