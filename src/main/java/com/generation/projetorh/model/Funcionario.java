package com.generation.projetorh.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_funcionarios")
public class Funcionario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O atributo nome é Obrigatório!")
    @Size(min = 3, max = 100, message = "O atributo nome deve conter no mínimo 3 e no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O atributo cargo é Obrigatório!")
    @Size(min = 3, max = 50, message = "O atributo cargo deve conter no mínimo 3 e no máximo 50 caracteres")
    private String cargo;

    @NotBlank(message = "O atributo departamento é Obrigatório!")
    @Size(min = 3, max = 50, message = "O atributo departamento deve conter no mínimo 3 e no máximo 50 caracteres")
    private String departamento;

    @Positive(message = "O salário deve ser maior que zero")
    private BigDecimal salario;

    @UpdateTimestamp
    private LocalDateTime data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
    

}
