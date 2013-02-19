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

import collections.mapmanytomany.TopFriend;
import collections.mapmanytomany.TopUser;

public class TestMapManyToManyJunit {
	
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
		 
		 TopUser tuser = new TopUser();
		 tuser.setName("zhangsan");
		 
		 TopUser tuser2 = new TopUser();
		 tuser2.setName("lisi");
		 
		 TopFriend tfriend = new TopFriend();
		 tfriend.setName("John");
		 
		 TopFriend tfriend2 = new TopFriend();
		 tfriend2.setName("helen");
		 
		 ////////////////////////////////////
		 
		 tuser.getFirendMap().put(1, tfriend);
		 tuser.getFirendMap().put(2, tfriend2);
		 
		 tuser2.getFirendMap().put(1, tfriend);
		 tuser2.getFirendMap().put(2, tfriend2);
		
		 //////////////////////////////////////////////
		 
		 tfriend.getUserMap().put(1, tuser);
		 tfriend.getUserMap().put(2, tuser2);
		 
		 tfriend2.getUserMap().put(1, tuser);
		 tfriend2.getUserMap().put(2, tuser2);
		 
		 /////////////////////////////////////////////////
		 
		 session.save(tuser);
		 session.save(tuser2);
		 session.save(tfriend);
		 session.save(tfriend2);
		 
		 
		 session.getTransaction().commit();

	}
	
	@Test
	 public void loadGroupAndUser(){
	  sessionFactory = new AnnotationConfiguration ().configure().buildSessionFactory();
	  Session session=sessionFactory.getCurrentSession();
	  session.beginTransaction();
	  //Group g=(Group)session.load(Group.class, 1);
	  
	  
	 /* for(Map.Entry<Integer,User> entry:g.getUsers().entrySet()){
	   System.out.println(entry.getValue().getId()+entry.getValue().getName());
	  }*/
	 TopUser user = (TopUser)session.load(TopUser.class, 1);
	 TopUser user2 = (TopUser)session.load(TopUser.class, 2);
	 
	 System.out.println("user 1 "+user.getName()+" 的好友是：");
	 Map<Integer,TopFriend> friendMap = user.getFirendMap();
	 for(Map.Entry<Integer, TopFriend> entry:friendMap.entrySet()){
		 TopFriend friend = entry.getValue();
		 System.out.println("friend name is: "+friend.getName());
	 }
	 
	 System.out.println("UUUUUUUUUU user 2 "+user2.getName()+" 的好友是：");
	 Map<Integer,TopFriend> friendMap2 = user2.getFirendMap();
	 for(Map.Entry<Integer, TopFriend> entry:friendMap2.entrySet()){
		 TopFriend friend = entry.getValue();
		 System.out.println("friend name is: "+friend.getName());
	 }
	 
	 System.out.println("**********************************");
	 
	 TopFriend friend=(TopFriend)session.load(TopFriend.class, 1);	  
	 TopFriend friend2=(TopFriend)session.load(TopFriend.class, 2);	
	 
	 System.out.println("friend 1 name is: "+friend.getName() +" 和 下面这些user 是好友");
	 Map<Integer,TopUser> userMap = friend.getUserMap();
	 for(Map.Entry<Integer, TopUser> entry:userMap.entrySet()){
		 TopUser t_user = entry.getValue();
		 System.out.println("user name is:"+t_user.getName());
	 }
	 
	 
	 System.out.println("FFFFFFFFF  friend 2 name is: "+friend2.getName() +" 和 下面这些user 是好友");
	 Map<Integer,TopUser> userMap2 = friend2.getUserMap();
	 for(Map.Entry<Integer, TopUser> entry:userMap2.entrySet()){
		 TopUser t_user = entry.getValue();
		 System.out.println("user name is:"+t_user.getName());
	 }
	 
	 
	  session.getTransaction().commit();
	 }

}
