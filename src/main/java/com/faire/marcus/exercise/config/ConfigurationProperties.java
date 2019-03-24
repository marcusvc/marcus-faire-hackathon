package com.faire.marcus.exercise.config;

import org.springframework.context.annotation.Configuration;

@Configuration
@org.springframework.boot.context.properties.ConfigurationProperties(prefix="faire")
public class ConfigurationProperties {
	
	private String accessTokenHeader;
	private String apiBaseUri;
	private String orderResourcePath;
	private String productResourcePath;
	private String optionResourcePath;
	private String inventoryLevelResourcePath;
	private String readTimeout;
	private String connectTimeout;

	public String getAccessTokenHeader() {
		return accessTokenHeader;
	}
	public void setAccessTokenHeader(String accessTokenHeader) {
		this.accessTokenHeader = accessTokenHeader;
	}
	public String getApiBaseUri() {
		return apiBaseUri;
	}
	public void setApiBaseUri(String apiBaseUri) {
		this.apiBaseUri = apiBaseUri;
	}
	public String getOrderResourcePath() {
		return orderResourcePath;
	}
	public void setOrderResourcePath(String orderResourcePath) {
		this.orderResourcePath = orderResourcePath;
	}
	public String getProductResourcePath() {
		return productResourcePath;
	}
	public void setProductResourcePath(String productResourcePath) {
		this.productResourcePath = productResourcePath;
	}
	public String getOptionResourcePath() {
		return optionResourcePath;
	}
	public void setOptionResourcePath(String optionResourcePath) {
		this.optionResourcePath = optionResourcePath;
	}
	public String getInventoryLevelResourcePath() {
		return inventoryLevelResourcePath;
	}
	public void setInventoryLevelResourcePath(String inventoryLevelResourcePath) {
		this.inventoryLevelResourcePath = inventoryLevelResourcePath;
	}
	public String getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(String readTimeout) {
		this.readTimeout = readTimeout;
	}
	public String getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(String connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

}
