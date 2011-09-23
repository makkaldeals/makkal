dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
           // dbCreate = "create-drop" // one of 'create', 'create-drop','update'
           // url = "jdbc:hsqldb:mem:devDB"

          dbCreate = "create-drop"
	      url = "jdbc:hsqldb:file:devgrepdealsDb;shutdown=true"
          //uncomment next line to log sqls
          //logSql = true ;
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
			pooled = true
            dbCreate = "update"
            url = "jdbc:mysql://localhost/grepdeals"
            driverClassName = "com.mysql.jdbc.Driver"
            username = "grepdealsuser"
            password = "agile4life"
			properties {
				maxActive = 30
				maxIdle = 15
				minIdle = 5
				initialSize = 10
				minEvictableIdleTimeMillis = 60000
				timeBetweenEvictionRunsMillis = 60000
				maxWait = 10000
				testOnBorrow=true
				testWhileIdle=true
				testOnReturn=true
				validationQuery="SELECT 1 from dual"
			}
        }
    }
}
