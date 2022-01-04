package it.fitdiary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Protocollo {
    /**
     * id protocollo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * data crazione protocollo.
     */
    @NotNull(message = "Data di creazione non può essere nullo")
    @Column(name = "data_creazione")
    private LocalDateTime dataCreazione;
    /**
     * data aggiornamento protocollo.
     */
    @NotNull(message = "La data di aggiornamento non può essere nullo")
    @Column(name = "data_aggiornamento")
    private LocalDateTime dataAggiornamento;
    /**
     * data scadenza protocollo.
     */
    @NotNull(message = "La data di scadenza non può essere nullo")
    @Column(name = "data_scadenza")
    @Future(message =
            "La data di scadenza deve essere successiva alla data odierna")
    private LocalDateTime dataScadenza;
    /**
     * scheda alimentare.
     */
    @OneToOne(mappedBy = "protocollo")
    private SchedaAlimentare schedaAlimentare;
    /**
     * scheda allenamento.
     */
    @OneToOne(mappedBy = "protocollo")
    private SchedaAllenamento schedaAllenamento;
    /**
     * cliente.
     */
    @NotNull(message = "Il cliente non può essere nullo")
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Utente cliente;
    /**
     * preparatore.
     */
    @NotNull(message = "Il preparatore non può essere nullo")
    @ManyToOne
    @JoinColumn(name = "preparatore_id")
    private Utente preparatore;

}
