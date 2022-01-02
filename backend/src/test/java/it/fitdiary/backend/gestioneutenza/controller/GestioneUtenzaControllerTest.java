package it.fitdiary.backend.gestioneutenza.controller;

import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {GestioneUtenzaController.class})
@ExtendWith(SpringExtension.class)
class GestioneUtenzaControllerTest {
    @Autowired
    private GestioneUtenzaController gestioneUtenzaController;

    @MockBean
    private GestioneUtenzaService gestioneUtenzaService;


    @Test
    void registrazioneNewUserReturnCreated() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);
        String utenteJson = "{\n" +
                "    \"nome\": \"Daniele\",\n" +
                "    \"cognome\": \"De Marco\",\n" +
                "    \"dataNascita\": \"2000-03-03\",\n" +
                "    \"sesso\": \"M\",\n" +
                "    \"email\": \"fabrizio@gmail.com\",\n" +
                "    \"password\": \"Daniele123*\",\n" +
                "    \"confermaPassword\": \"Daniele123*\"\n" +
                "}";
        Utente utente = new Utente(null, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", true, LocalDate.parse("2000-03" +
                "-03"), null,
                null, "M", null, null, null,
                null, null, null, null, null, null);
        Utente newUtente = new Utente(1L, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", true, LocalDate.parse("2000-03-03"), null,
                null, "M", null, null, null,
                null, null, ruoloPrep, null, null, null);
        when(gestioneUtenzaService.registrazione(utente)).thenReturn(newUtente);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/utenti/preparatore").content(utenteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void iscrizioneClienteSuccess() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        String nome = "Fabrizio";
        String cognome = "Vitale";
        String email = "fabrizio@gmail.com";
        String emailPrep = "davide@gmail.com";
        String clienteJson = "{\n" +
                "    \"nome\": \"Fabrizio\",\n" +
                "    \"cognome\": \"Vitale\",\n" +
                "    \"email\": \"fabrizio@gmail.com\"\n" +
                "}";
        Utente preparatore = new Utente(1L, "Davide", "La Gamba", emailPrep
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), null,
                null, "M", null, null, null,
                null, null, ruoloPrep, null, null, null);
        Utente newCliente = new Utente(2L, nome, cognome, email
                , "Fabrizio123*", true, LocalDate.parse("2000-03-03"), null,
                null, "M", null, null, null,
                null, preparatore, ruoloPrep, null, null, null);

        Principal principal = () -> "User";
        when(gestioneUtenzaService.inserisciCliente(nome, cognome, email, principal.getName())).thenReturn(newCliente);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/utenti/createcliente").principal(principal).content(clienteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void iscrizioneClienteBadRequest() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        String nome = "Fabrizio";
        String cognome = "Vitale";
        String email = "fabrizio@gmail.com";
        String emailPrep = "davide@gmail.com";
        String clienteJson = "{\n" +
                "    \"nome\": \"Fabrizio\",\n" +
                "    \"cognome\": \"Vitale\",\n" +
                "    \"email\": \"fabrizio@gmail.com\"\n" +
                "}";
        Utente preparatore = new Utente(1L, "Davide", "La Gamba", emailPrep
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), null,
                null, "M", null, null, null,
                null, null, ruoloPrep, null, null, null);
        Utente newCliente = new Utente(2L, nome, cognome, email
                , "Fabrizio123*", true, LocalDate.parse("2000-03-03"), null,
                null, "M", null, null, null,
                null, preparatore, ruoloPrep, null, null, null);

        Principal principal = () -> "User";
        when(gestioneUtenzaService.inserisciCliente(nome, cognome, email, principal.getName())).thenThrow(IllegalArgumentException.class);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/utenti/createcliente").principal(principal).content(clienteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void visualizzaProfiloSuccess() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        Utente utente = new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), null,
                null, "M", null, null, null,
                null, null, ruoloPrep, null, null, null);

        Principal principal = () -> "User";
        when(gestioneUtenzaService.getUtenteByEmail(principal.getName())).thenReturn(utente);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/utenti/profilo").principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void visualizzaProfiloBadRequest() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        Utente utente = new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), null,
                null, "M", null, null, null,
                null, null, ruoloPrep, null, null, null);

        Principal principal = () -> "User";
        when(gestioneUtenzaService.getUtenteByEmail(principal.getName())).thenThrow(IllegalArgumentException.class);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/utenti/profilo").principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void inserimentoDatiPersonaliCliente()
            throws Exception {
        Ruolo r = new Ruolo(2L, "CLIENTE", null, null);
        String clienteJson = "{\n" +
                "    \"dataNascita\": \"2000-10-30\",\n" +
                "    \"telefono\": \"389485921\",\n" +
                "    \"via\": \"Francesco rinaldo\",\n" +
                "    \"cap\": \"94061\",\n" +
                "    \"citta\": \"Agropoli\"\n" +
               "}";
        Utente utenteNonModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                null, null, null, null, null, null, null, null, null, r, null, null, null);
        Utente utenteModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, null, null, "3894685921", "Francesco rinaldo", "94061", "Agropoli", null, r, null, null, null);
        Principal principal = () -> "User";
        when(gestioneUtenzaService.inserimentoDatiPersonaliCliente(utenteNonModificato, principal.getName())).thenReturn(utenteModificato);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/utenti/cliente").principal(principal).content(clienteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().

                is2xxSuccessful());
    }



    @Test
    void modificaDatiPersonaliCliente() throws Exception {
        Ruolo r = new Ruolo(2L, "CLIENTE", null, null);
        String clienteJson = "{\n" +
                "    \"nome\": \"Francesca\",\n" +
                "    \"cognome\": \"Di Matteo\",\n" +
                "    \"email\": \"beccadimatteoo@gmail.com\",\n" +
                "    \"password\": \"Becca123*\",\n" +
                "    \"dataNascita\": \"2000-10-30\",\n" +
                "    \"telefono\": \"389485921\",\n" +
                "    \"via\": \"Francesco rinaldo\",\n" +
                "    \"cap\": \"94061\",\n" +
                "    \"citta\": \"Agropoli\"\n" +
                "}";
        Utente utenteNonModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                null, null, null, null, null, null, null, null, null, r, null, null, null);
        Utente utenteModificato = new Utente(1L, "Francesca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, null, null, "3894685921", "Francesco rinaldo", "94061", "Agropoli", null, r, null, null, null);
        Principal principal = () -> "User";
        when(gestioneUtenzaService.modificaDatiPersonaliCliente(utenteNonModificato, principal.getName())).thenReturn(utenteModificato);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.put("/api/v1/utenti/cliente").principal(principal).content(clienteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().

                is2xxSuccessful());
    }
}

