package lv.wings.model;

import java.time.LocalDateTime;
import java.util.Collection;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//TODO: Need to check inputs or change something and create table relations if needed  NOT FINISHED!
@Setter
@Getter
@NoArgsConstructor
@Table(name = "event_category")
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE event_category SET deleted = true WHERE event_category_id=?")
@Where(clause = "deleted=false")
public class EventCategory {
	@Column(name = "event_category_id")
	@Id
	@Setter(value = AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int eventCategoryId;

	@NotNull
	@Column(name = "title")
	@Size(min = 3, max = 200, message = "Kategorijas nosaukums nedrīkst saturēt mazāk par 3 vai vairāk par 200 rakstzīmēm!")
	@Pattern(regexp = "^[a-zA-ZāčēģīķļņōŗšūžĀČĒĢĪĶĻŅŌŖŠŪŽ\\s]+$", message = "Kategorijas nosaukums drīkst saturēt tikai burtus un atstarpes!")
	private String title;

	@OneToMany(mappedBy = "category")
	@JsonBackReference
	private Collection<Event> events;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createDate;

	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModified;

	@CreatedBy
	@Column(updatable = false)
	private Integer createdBy;

	@LastModifiedBy
	@Column(insertable = false)
	private Integer lastModifiedBy;

	// Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	public EventCategory(String title) {
		setTitle(title);
	}
}