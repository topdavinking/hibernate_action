package test;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import collections.maponetomany.Group;
import collections.maponetomany.User;

public class TestMapJunit {

	private static SessionFactory sessionFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// 生成表结构
		new SchemaExport(new AnnotationConfiguration().configure()).create(false, true);
		sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	@Test
	public void testSavaEntity() {
		//Session session = sessionFactory.openSession();  
	    /* 
	    * 在使用sessionFactory.getCurrentSession()是必须在hibernate的配置文件中加入 
	    * <property name="current_session_context_class">thread</property>  
	    */  
	  /** 
	    * openSession与getCurrentSession的区别是: 
	    * openSession每次都是新的,需要close 
	    * getCurrentSession从上下文找,如果有,用旧的,如果没有,建新的 
	    *  1.用途,界定事务边界 
	    *  2.事务提交自动close 
	    */  

		// Session session =sessionFactory.getCurrentSession(); //必须要在hibernate.cfg.xml 内增加
		 // <property name="current_session_context_class">thread</property>
		 //还可以采用：是由于函数getcurrentsession()造成的，可以将其改为opensession()
		 Session session =sessionFactory.openSession();
		 session.beginTransaction();
		 
		 Group group=new Group();
		 group.setName("kewen");
		 
		 Group group2 = new Group();
		 group2.setName("davin");
		 
		 User user =new User();
		 user.setName("myDog");
		 user.setGroup(group);
		 User user1 =new User();
		 user1.setName("myCat");
		 user1.setGroup(group);
		 
		 group.getUsers().put(user.getId(), user);
		 group.getUsers().put(user1.getId(), user1);
		 
		 session.save(group);
		 session.save(user);
		 session.save(user1);
		 session.getTransaction().commit();

	}
	
	@Test
	 public void loadGroupAndUser(){
	  sessionFactory = new AnnotationConfiguration ().configure().buildSessionFactory();
	  Session session=sessionFactory.getCurrentSession();
	  session.beginTransaction();
	  Group g=(Group)session.load(Group.class, 1);
	  
	  
	  for(Map.Entry<Integer,User> entry:g.getUsers().entrySet()){
	   System.out.println(entry.getValue().getId()+entry.getValue().getName());
	  }
	  session.getTransaction().commit();
	 }

}
