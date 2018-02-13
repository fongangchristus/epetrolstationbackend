package com.epetrole.backend.web.rest;

import com.epetrole.backend.EpetrolstationbackendApp;

import com.epetrole.backend.domain.Produit;
import com.epetrole.backend.repository.ProduitRepository;
import com.epetrole.backend.service.ProduitService;
import com.epetrole.backend.service.dto.ProduitDTO;
import com.epetrole.backend.service.mapper.ProduitMapper;
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
 * Test class for the ProduitResource REST controller.
 *
 * @see ProduitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpetrolstationbackendApp.class)
public class ProduitResourceIntTest {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_SOLDE_INIT = 1D;
    private static final Double UPDATED_SOLDE_INIT = 2D;

    private static final Double DEFAULT_PRIX_ACHAT = 1D;
    private static final Double UPDATED_PRIX_ACHAT = 2D;

    private static final Double DEFAULT_PRIX_VENTE = 1D;
    private static final Double UPDATED_PRIX_VENTE = 2D;

    private static final Double DEFAULT_QUANTITE_DISPO = 1D;
    private static final Double UPDATED_QUANTITE_DISPO = 2D;

    private static final Double DEFAULT_QUANTITE_INIT = 1D;
    private static final Double UPDATED_QUANTITE_INIT = 2D;

    private static final Double DEFAULT_SEUIL_REAPROV = 1D;
    private static final Double UPDATED_SEUIL_REAPROV = 2D;

    private static final Double DEFAULT_REFERENCE = 1D;
    private static final Double UPDATED_REFERENCE = 2D;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitMapper produitMapper;

    @Autowired
    private ProduitService produitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProduitMockMvc;

    private Produit produit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProduitResource produitResource = new ProduitResource(produitService);
        this.restProduitMockMvc = MockMvcBuilders.standaloneSetup(produitResource)
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
    public static Produit createEntity(EntityManager em) {
        Produit produit = new Produit()
            .designation(DEFAULT_DESIGNATION)
            .soldeInit(DEFAULT_SOLDE_INIT)
            .prixAchat(DEFAULT_PRIX_ACHAT)
            .prixVente(DEFAULT_PRIX_VENTE)
            .quantiteDispo(DEFAULT_QUANTITE_DISPO)
            .quantiteInit(DEFAULT_QUANTITE_INIT)
            .seuilReaprov(DEFAULT_SEUIL_REAPROV)
            .reference(DEFAULT_REFERENCE);
        return produit;
    }

    @Before
    public void initTest() {
        produit = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduit() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);
        restProduitMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isCreated());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate + 1);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testProduit.getSoldeInit()).isEqualTo(DEFAULT_SOLDE_INIT);
        assertThat(testProduit.getPrixAchat()).isEqualTo(DEFAULT_PRIX_ACHAT);
        assertThat(testProduit.getPrixVente()).isEqualTo(DEFAULT_PRIX_VENTE);
        assertThat(testProduit.getQuantiteDispo()).isEqualTo(DEFAULT_QUANTITE_DISPO);
        assertThat(testProduit.getQuantiteInit()).isEqualTo(DEFAULT_QUANTITE_INIT);
        assertThat(testProduit.getSeuilReaprov()).isEqualTo(DEFAULT_SEUIL_REAPROV);
        assertThat(testProduit.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // Create the Produit with an existing ID
        produit.setId(1L);
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProduits() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList
        restProduitMockMvc.perform(get("/api/produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].soldeInit").value(hasItem(DEFAULT_SOLDE_INIT.doubleValue())))
            .andExpect(jsonPath("$.[*].prixAchat").value(hasItem(DEFAULT_PRIX_ACHAT.doubleValue())))
            .andExpect(jsonPath("$.[*].prixVente").value(hasItem(DEFAULT_PRIX_VENTE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteDispo").value(hasItem(DEFAULT_QUANTITE_DISPO.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteInit").value(hasItem(DEFAULT_QUANTITE_INIT.doubleValue())))
            .andExpect(jsonPath("$.[*].seuilReaprov").value(hasItem(DEFAULT_SEUIL_REAPROV.doubleValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.doubleValue())));
    }

    @Test
    @Transactional
    public void getProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", produit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produit.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.soldeInit").value(DEFAULT_SOLDE_INIT.doubleValue()))
            .andExpect(jsonPath("$.prixAchat").value(DEFAULT_PRIX_ACHAT.doubleValue()))
            .andExpect(jsonPath("$.prixVente").value(DEFAULT_PRIX_VENTE.doubleValue()))
            .andExpect(jsonPath("$.quantiteDispo").value(DEFAULT_QUANTITE_DISPO.doubleValue()))
            .andExpect(jsonPath("$.quantiteInit").value(DEFAULT_QUANTITE_INIT.doubleValue()))
            .andExpect(jsonPath("$.seuilReaprov").value(DEFAULT_SEUIL_REAPROV.doubleValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProduit() throws Exception {
        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit
        Produit updatedProduit = produitRepository.findOne(produit.getId());
        // Disconnect from session so that the updates on updatedProduit are not directly saved in db
        em.detach(updatedProduit);
        updatedProduit
            .designation(UPDATED_DESIGNATION)
            .soldeInit(UPDATED_SOLDE_INIT)
            .prixAchat(UPDATED_PRIX_ACHAT)
            .prixVente(UPDATED_PRIX_VENTE)
            .quantiteDispo(UPDATED_QUANTITE_DISPO)
            .quantiteInit(UPDATED_QUANTITE_INIT)
            .seuilReaprov(UPDATED_SEUIL_REAPROV)
            .reference(UPDATED_REFERENCE);
        ProduitDTO produitDTO = produitMapper.toDto(updatedProduit);

        restProduitMockMvc.perform(put("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testProduit.getSoldeInit()).isEqualTo(UPDATED_SOLDE_INIT);
        assertThat(testProduit.getPrixAchat()).isEqualTo(UPDATED_PRIX_ACHAT);
        assertThat(testProduit.getPrixVente()).isEqualTo(UPDATED_PRIX_VENTE);
        assertThat(testProduit.getQuantiteDispo()).isEqualTo(UPDATED_QUANTITE_DISPO);
        assertThat(testProduit.getQuantiteInit()).isEqualTo(UPDATED_QUANTITE_INIT);
        assertThat(testProduit.getSeuilReaprov()).isEqualTo(UPDATED_SEUIL_REAPROV);
        assertThat(testProduit.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProduitMockMvc.perform(put("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isCreated());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);
        int databaseSizeBeforeDelete = produitRepository.findAll().size();

        // Get the produit
        restProduitMockMvc.perform(delete("/api/produits/{id}", produit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produit.class);
        Produit produit1 = new Produit();
        produit1.setId(1L);
        Produit produit2 = new Produit();
        produit2.setId(produit1.getId());
        assertThat(produit1).isEqualTo(produit2);
        produit2.setId(2L);
        assertThat(produit1).isNotEqualTo(produit2);
        produit1.setId(null);
        assertThat(produit1).isNotEqualTo(produit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduitDTO.class);
        ProduitDTO produitDTO1 = new ProduitDTO();
        produitDTO1.setId(1L);
        ProduitDTO produitDTO2 = new ProduitDTO();
        assertThat(produitDTO1).isNotEqualTo(produitDTO2);
        produitDTO2.setId(produitDTO1.getId());
        assertThat(produitDTO1).isEqualTo(produitDTO2);
        produitDTO2.setId(2L);
        assertThat(produitDTO1).isNotEqualTo(produitDTO2);
        produitDTO1.setId(null);
        assertThat(produitDTO1).isNotEqualTo(produitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(produitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(produitMapper.fromId(null)).isNull();
    }
}
