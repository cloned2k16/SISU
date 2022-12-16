package com.inditext.sisu;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.inditext.sisu.util.ILogger;
import com.inditext.sisu.util.Logger;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {
	private static ILogger log = Logger.getLogger("== YAMLConfig -");
	
	static { log.debug("static <init>");}
	
    private String 			name;
    private String 			ddbbDriver;
    private String 			ddbbUrl;
    private String 			ddbbUser;
    private String 			ddbbPass;
    private boolean         useStoredProcedure;   

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDdbbDriver() {
		return ddbbDriver;
	}
	public void setDdbbDriver(String ddbbDriver) {
		this.ddbbDriver = ddbbDriver;
	}
	public String getDdbbUrl() {
		return ddbbUrl;
	}
	public void setDdbbUrl(String ddbbUrl) {
		this.ddbbUrl = ddbbUrl;
	}
	public String getDdbbUser() {
		return ddbbUser;
	}
	public void setDdbbUser(String ddbbUser) {
		this.ddbbUser = ddbbUser;
	}
	public String getDdbbPass() {
		return ddbbPass;
	}
	public void setDdbbPass(String ddbbPass) {
		this.ddbbPass = ddbbPass;
	}
	public boolean isUseStoredProcedure() {
		return useStoredProcedure;
	}
	public void setUseStoredProcedure(boolean useStoredProcedure) {
		this.useStoredProcedure = useStoredProcedure;
	}

}