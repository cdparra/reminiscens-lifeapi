package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import enums.MementoCategory;

import play.db.ebean.Model;

@Entity
@Table(name="Contex_Index")
public class ContextIndex extends Model {

	private static final long serialVersionUID = -2021927948308276540L;

	@Id
    @GeneratedValue
    @Column(name="context_index_id")
    private Long publicMementoId;

	@Column
	private Long decade;
	
	@Column 
	private Long year;
	
	@Column 
	private Double distance;

	@Column 
	private Double category;
	
	@ManyToOne
	@MapsId
	@JoinColumn(name="city_id")
	private City closestCity;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="event_id")
	private static Event event;

	@OneToOne
	@MapsId
	@JoinColumn(name="work_id")
	private static CreativeWork creativeWork;

	@OneToOne
	@MapsId
	@JoinColumn(name="media_id")
	private static Media media;

	@OneToOne
	@MapsId
	@JoinColumn(name="famous_id")
	private static FamousPerson famous;

	@Column
	private Integer coordinatesTrust;
	
	@JsonIgnore
	@OneToMany(mappedBy="publicMemento")
	private List<PublicParticipation> publicParticipants;
	
	public static Model.Finder<Long,ContextIndex> find = new Model.Finder<Long, ContextIndex>(
            Long.class,ContextIndex.class
    );
    
    public static List<ContextIndex> all(){
        return find.all();
    }
    
    public static ContextIndex read(Long id){ 
    	ContextIndex pm = find.byId(id);    	
    	return pm;
    }	
    
    public static List<ContextIndex> readByDecade(Long decade){ 
    	List<ContextIndex> pmList = find.where()
				.eq("decade", decade).findList();;    	
    	return pmList;
    }	
    

    public static List<ContextIndex> readByDecadeAndCityId(Long decade, Long cityId){ 
    	List<ContextIndex> pmList = find.where()
				.eq("decade", decade).eq("cityId",cityId).findList();    	
    	return pmList;
    }
    
    public static List<ContextIndex> readByDecadeAndCityIdWithRadius(Long decade, Long cityId,Long radius){ 
    	List<ContextIndex> pmList = find.where()
				.eq("decade", decade).findList();    	
    	
    	List<ContextIndex> finalList = new ArrayList<ContextIndex>();
    	
    	for (ContextIndex publicMemento : pmList) {
    		City closestCity = publicMemento.getClosestCity();
    		Double distance = models.City.getDistance(closestCity.getCityId(), cityId);
			
    		if (distance < radius) {
    			finalList.add(publicMemento);
    		}
		}
    	
    	return finalList;
    }
    
	public static List<ContextIndex> readByDecadeAndCityIdAndCategoryWithLimit(
			Long decade, Long city, MementoCategory category, int limit, List<ContextIndex> exclusionList) {
		// TODO see how to filter a list of items in the exclusionList
    	List<ContextIndex> pmList = find.where()
				.eq("decade", decade)
				.eq("closestCity.cityId",city)
				.eq("category", category)
				.setOrderBy("distance desc")
				.setMaxRows(limit)
				.findList();
    	return pmList;
	}	
	
	public static List<ContextIndex> readByDecadeAndCountryIdAndCategoryWithLimit(
			Long decade, Long countryId, MementoCategory category, int limit, List<ContextIndex> exclusionList) {
		List<ContextIndex> pmList = find.where()
				.eq("decade", decade)
				.eq("closestCity.country.countryId", countryId)
				.eq("category", category)
				.setOrderBy("distance desc")
				.setMaxRows(limit)
				.findList();
    	return pmList;		
	}
	

	public static List<ContextIndex> readByDecadeAndCategoryWithLimit(
			Long decade, MementoCategory category, int limit, List<ContextIndex> exclusionList) {
		List<ContextIndex> pmList = find.where()
				.eq("decade", decade)
				.eq("category", category)
				.setOrderBy("rand()")
				.setMaxRows(limit)
				.findList();
    	return pmList;	
	} 
	
    

	public Long getPublicMementoId() {
		return publicMementoId;
	}

	public void setPublicMementoId(Long publicMementoId) {
		this.publicMementoId = publicMementoId;
	}

	public Long getDecade() {
		return decade;
	}

	public void setDecade(Long decade) {
		this.decade = decade;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getCategory() {
		return category;
	}

	public void setCategory(Double category) {
		this.category = category;
	}

	public City getClosestCity() {
		return closestCity;
	}

	public void setClosestCity(City city) {
		this.closestCity = city;
	}

	public static Event getEvent() {
		return event;
	}

	public static void setEvent(Event event) {
		ContextIndex.event = event;
	}

	public static CreativeWork getCreativeWork() {
		return creativeWork;
	}

	public static void setCreativeWork(CreativeWork creativeWork) {
		ContextIndex.creativeWork = creativeWork;
	}

	public static Media getMedia() {
		return media;
	}

	public static void setMedia(Media media) {
		ContextIndex.media = media;
	}

	public Integer getCoordinatesTrust() {
		return coordinatesTrust;
	}

	public void setCoordinatesTrust(Integer coordinates_trust) {
		this.coordinatesTrust = coordinates_trust;
	}

	public List<PublicParticipation> getFamousParticipants() {
		return publicParticipants;
	}

	public void setFamousParticipants(List<PublicParticipation> participants) {
		this.publicParticipants = participants;
	}   
}
