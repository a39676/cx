package demo.config;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement // <tx:annotation-driven />
// 2016 multiple scan, 通配符的使用应放后边, 否则会被"覆盖?重写?"后失效
@MapperScan(basePackages = { "demo.*.*.*.mapper", "demo.*.*.mapper", "demo.*.mapper" })
public class DatabaseCXConfig {

	@Value("${databaseCX.driverClassName}")
	private String driverClassName;
	@Value("${databaseCX.url}")
	private String url;
	@Value("${databaseCX.username}")
	private String username;
	@Value("${databaseCX.password}")
	private String password;

	@Primary
	@Bean(name = "cxDataSourceProperties")
	@ConfigurationProperties(prefix = "spring.datasource.cx")
	public DataSourceProperties cxDataSourceProperties() {
		DataSourceProperties d = new DataSourceProperties();
		d.setDriverClassName(driverClassName);
		d.setUrl(url);
		d.setUsername(username);
		d.setPassword(password);
		return d;
	}

	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(driverClassName);
		dataSourceBuilder.url(url);
		dataSourceBuilder.username(username);
		dataSourceBuilder.password(password);
		return dataSourceBuilder.build();
	}
}
