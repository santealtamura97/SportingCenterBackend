package com.sportingCenterBackEnd.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the user database table.
 *
 */
@Entity
@Table(name = "User")
@NoArgsConstructor
@Getter
@Setter

public class User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 65981149772133526L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "PROVIDER_USER_ID")
	private String providerUserId;

	private String email;

	@Column(name = "enabled", columnDefinition = "BIT", length = 1)
	private boolean enabled;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "dataNascita")
	private String dataNascita;

	@Column(name = "created_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date modifiedDate;

	private String password;

	private String provider;

	@Column(name = "number")
	private String number;

	@Column(name = "idAbbonamento")
	private String abbonamento;

	@Column(name ="data_scadenza_abbonamento")
	private String scadenzaAbbonamento;

	@Column(name ="ingressi")
	private Long ingressi;

	@Column(name = "expired")
	private Boolean expired = false;

	@Lob
	@Column(name = "image_profile")
	private byte[] image;


	// bi-directional many-to-many association to Role
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public void setAbbonamento(String abbonamento) {
		this.abbonamento = abbonamento;
	}
	/*public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String getDataScadenza() {
		return this.dataScadenza;
	}*/

	public String getAbbonamento() {
		return this.abbonamento;
	}

	public void setExpired(Boolean checked) {
		this.expired = checked;
	}
	public Boolean getExpired() {
		return this.expired;
	}

	public String getScadenzaAbbonamento() {
		return scadenzaAbbonamento;
	}

	public void setScadenzaAbbonamento(String scadenzaAbbonamento) {
		this.scadenzaAbbonamento = scadenzaAbbonamento;
	}

	public Long getIngressi() {
		return ingressi;
	}

	public void setIngressi(Long ingressi) {
		this.ingressi = ingressi;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
