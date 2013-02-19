package collections.maponetomany;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_map_user")
public class User {
	private int id;
	private String name;
	private Group group;

	@ManyToOne(cascade = CascadeType.ALL)
	/*
	 * @ManyToOne就是要生成一个外键去关联它的关联类
	 * 如果没有下面这个注解也会将在User实体对应的数据表中产生一个外键字段，它是参照Group的主键
	 */
	@JoinColumn(name = "groupid")
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
