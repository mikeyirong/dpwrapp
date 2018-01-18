package mike.dpwrapp;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;

public class DefaultPersisterFactory extends PersisterFactory{
	private Hashtable<String, EbeanServer> cache = new Hashtable<String, EbeanServer>();
	
	  private final String configPath;
	  
	  public DefaultPersisterFactory(String configPath)
	  {
	    this.configPath = configPath;
	    try {
	      init();
	    } catch (Exception e) {
	      throw new RuntimeException("Loading configure faild", e);
	    }
	  }

	  void init() throws Exception {
	    InputStream in = ConfigurationLoader.loadAsInputStream(this.configPath);
	    Properties properties = new Properties();
	    properties.load(in);

	    ServerConfig serverConfig = new ServerConfig();
	    DataSourceConfig ds = new DataSourceConfig();
	    ds.setDriver(properties.getProperty("ebean.jdbc.driver"));
	    ds.setUrl(properties.getProperty("ebean.jdbc.url"));
	    ds.setUsername(properties.getProperty("ebean.jdbc.username"));
	    ds.setPassword(properties.getProperty("ebean.jdbc.password"));
	    try
	    {
	      if (Boolean.parseBoolean(properties.getProperty("ebean.evalutions"))) {
	        serverConfig.setDdlGenerate(true);
	        serverConfig.setDdlRun(true);
	      }
	    } catch (Exception e) {
	      serverConfig.setDdlGenerate(false);
	      serverConfig.setDdlRun(false);
	    }

	    String name = properties.getProperty("ebean.name");
	    serverConfig.setName((name == null) ? "default" : name);
	    serverConfig.setDataSourceConfig(ds);

	    String pkg = properties.getProperty("ebean.models");
	    if (pkg != null) {
	      String[] pkgs = pkg.split(",");

	      for (String jpkg : pkgs) {
	        serverConfig.addClass(Thread.currentThread().getContextClassLoader().loadClass(jpkg));
	      }
	    }
	    EbeanServer server = EbeanServerFactory.create(serverConfig);
	    this.cache.put(serverConfig.getName(), server);
	  }
     

	@Override
	public EbeanServer getEbean(String parmeter) {
		 if (parmeter == null) {
		      throw new IllegalArgumentException("server name could not be null!");
		    }
		    EbeanServer matched = (EbeanServer)this.cache.get(parmeter);
		    if (matched == null)
		      throw new IllegalArgumentException("No such ebean server named: " + parmeter);
		    return matched;
	}
}
