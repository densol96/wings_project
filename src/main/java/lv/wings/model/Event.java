package lv.wings.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "event")
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Event {
	@Column(name = "event_id")
	@Id
	@Setter(value = AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int eventId;

	@NotNull
	@Column(name = "start_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate; /// sukumaDatums also includes time.

	@NotNull
	@Column(name = "end_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;

	@NotNull
	@Column(name = "title")
	@Size(min = 5, max = 300, message = "Nosaukums nedrīkst saturēt mazāk par 5 vai vairāk par 300 rakstzīmēm!")
	private String title;

	@NotNull
	@Column(name = "location")
	@Size(min = 2, max = 200, message = "Vieta nedrīkst saturēt mazāk par 2 vai vairāk par 200 rakstzīmēm!")
	private String location;

	@NotNull
	@Column(name = "description")
	@Size(min = 0, max = 3000, message = "Aprakstā par daudz rakstzīmju! (0-3000)")
	private String description;

	/// Need to validate this
	@NotNull
	@Column(name = "key_words")
	private String keyWords;

	/////// Saites //////
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Collection<EventPicture> eventPictures;

	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name = "event_category_id")
	private EventCategory eventCategory;
	
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

	public Event(Date startDate, Date endDate, String title, String location,
		String description, String keyWords, EventCategory eventCategory) {
		setStartDate(startDate);
		setEndDate(endDate);
		setTitle(title);
		setLocation(location);
		setDescription(description);
		setKeyWords(keyWords);
		setEventCategory(eventCategory);
	}

}