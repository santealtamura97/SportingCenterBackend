package com.sportingCenterBackEnd.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rx.BackpressureOverflow;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "UserCode")
@NoArgsConstructor
@Getter
@Setter
public class UserCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usercode_id")
    private Long id;

    @Column(name = "display_name")
    private String display_name;

    @Column(name = "code")
    private String code;

    @Column(name ="id_abbonamento")
    @NotNull
    private String id_abbonamento;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId_abbonamento() {
        return id_abbonamento;
    }

    public void setId_abbonamento(String id_abbonamento) {
        this.id_abbonamento = id_abbonamento;
    }
}
