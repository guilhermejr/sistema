package net.guilhermejr.sistema.energia.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "acompanhamentos")
@Audited
public class Acompanhamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate inicio;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate fim;

    @Column(nullable = false)
    private Integer dias;

    private BigDecimal energiaGerada;

    @Column(nullable = false)
    private Integer energiaInjetada;

    @Column(nullable = false)
    private Integer energiaConsumidaConcessionaria;

    @Column(nullable = false)
    private BigDecimal energiaConsumidaTotal;

    @Column(nullable = false)
    private Integer saldoMes;

    @Column(nullable = false)
    private BigDecimal tusd;

    @Column(nullable = false)
    private BigDecimal te;

    @Column(nullable = false)
    private BigDecimal bandeira;

    @Column(nullable = false)
    private BigDecimal iluminacaoPublica;

    @Column(nullable = false)
    private BigDecimal desconto;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private UUID usuario;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime criado;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime atualizado;

}
