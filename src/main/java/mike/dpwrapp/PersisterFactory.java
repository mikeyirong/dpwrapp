package mike.dpwrapp;

import java.util.Hashtable;

import com.avaje.ebean.EbeanServer;


/**
 * @author mike_yi
 * @description 事务工厂
 * @since  2018-1-17
 */
public abstract class PersisterFactory {
  private static Hashtable<String,PersisterFactory> cache = new Hashtable<String,PersisterFactory>();
  public static PersisterFactory load(String path) {
	  PersisterFactory matched = (PersisterFactory)cache.get(path);
	    if (matched == null) {
	      cache.put(path, new DefaultPersisterFactory(path));
	      return load(path);
	    }
	    return matched;
  }
   public  EbeanServer getEbean() {
	   return getEbean("default");
   }
   public abstract EbeanServer getEbean(String parmeter) ;
}
