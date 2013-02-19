package collections.mapmanytomany;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;

@Entity 
public class TopUser {
	
	private Integer id;
	private String name;
	private Map<Integer,TopFriend> firendMap = new HashMap<Integer, TopFriend>();
	
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
	
	@JoinTable(name="t_friend_user"
			,joinColumns={@JoinColumn(name="userID")}
	        ,inverseJoinColumns={@JoinColumn(name="friendID")})
	@ManyToMany(cascade = CascadeType.REFRESH,targetEntity=TopFriend.class)
	@MapKey(name="id")
	public Map<Integer, TopFriend> getFirendMap() {
		return firendMap;
	}
	public void setFirendMap(Map<Integer, TopFriend> firendMap) {
		this.firendMap = firendMap;
	}
}
