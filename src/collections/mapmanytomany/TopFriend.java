package collections.mapmanytomany;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;

@Entity
public class TopFriend {
	private Integer id;
	private String name;
	
	private Map<Integer,TopUser> userMap = new HashMap<Integer, TopUser>();
  
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="firendMap",targetEntity=TopUser.class)
	@MapKey(name="id")
	public Map<Integer, TopUser> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<Integer, TopUser> userMap) {
		this.userMap = userMap;
	}
	
	
	
}
