package lv.wings.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "event_pictures")
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE event_pictures SET deleted = true WHERE event_pictures_id=?")
@Where(clause = "deleted=false")
public class EventPicture {
	@Column(name = "event_pictures_id")
	@Id
	@Setter(value = AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int eventPicturesId;

	/// image changed to image reference (link - string)
	@NotNull
	@Column(name = "reference_to_picture")
	private String referenceToPicture;

	@NotNull
	@Column(name = "title")
	@Size(min = 2, max = 200, message = "Nosaukums nedrīkst saturēt mazāk par 2 vai vairāk par 200 rakstzīmēm!")
	private String title;

	@NotNull
	@Column(name = "description")
	@Size(min = 0, max = 150, message = "Aprakstā par daudz rakstzīmju! (0-3000)")
	private String description;

	//TODO
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "event_id")
	private Event event;
	
	@CreatedDate
	@Column(nullable = false,updatable = false)
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

	//Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	public EventPicture(String referenceToPicture, String title, String description, Event event) {
		setReferenceToPicture(referenceToPicture);
		setTitle(title);
		setDescription(description);
		setEvent(event);
	}

}