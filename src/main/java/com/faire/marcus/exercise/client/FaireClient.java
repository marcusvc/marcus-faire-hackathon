package com.faire.marcus.exercise.client;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.faire.marcus.exercise.config.ConfigurationProperties;
import com.faire.marcus.exercise.context.TokenInputContext;
import com.faire.marcus.exercise.model.InventoryLevel;
import com.faire.marcus.exercise.model.Option;
import com.faire.marcus.exercise.model.Orders;
import com.faire.marcus.exercise.model.Products;

@Service
public class FaireClient {

	private static final Logger LOG = LoggerFactory.getLogger(FaireClient.class);

	private static final String JERSEY_CONFIG_CLIENT_READ_TIMEOUT = "jersey.config.client.readTimeout";
	private static final String JERSEY_CONFIG_CLIENT_CONNECT_TIMEOUT = "jersey.config.client.connectTimeout";
	
	private TokenInputContext tokenInputContext;
	private ConfigurationProperties configurationProperties;
	
	public FaireClient(TokenInputContext tokenInputContext, ConfigurationProperties configurationProperties) {
		this.tokenInputContext = tokenInputContext;
		this.configurationProperties = configurationProperties;
	}
	
	public Products consumesAllProducts() {
		try {

			Client client = ClientBuilder.newClient()
					.property(JERSEY_CONFIG_CLIENT_CONNECT_TIMEOUT, configurationProperties.getConnectTimeout())
					.property(JERSEY_CONFIG_CLIENT_READ_TIMEOUT, configurationProperties.getReadTimeout());
			WebTarget brandTarget = client
					.target(new URI(configurationProperties.getApiBaseUri()))
					.path(configurationProperties.getProductResourcePath());
			
			Invocation.Builder invocationBuilder = brandTarget
					.request(MediaType.APPLICATION_JSON)
					.header(configurationProperties.getAccessTokenHeader(), tokenInputContext.getToken());
			Response response = invocationBuilder.get();
			return response.readEntity(Products.class);

		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Orders consumesAllOrders() {
		try {

			Client client = ClientBuilder.newClient()
					.property(JERSEY_CONFIG_CLIENT_CONNECT_TIMEOUT, configurationProperties.getConnectTimeout())
					.property(JERSEY_CONFIG_CLIENT_READ_TIMEOUT, configurationProperties.getReadTimeout());
			WebTarget brandTarget = client
					.target(new URI(configurationProperties.getApiBaseUri()))
					.path(configurationProperties.getOrderResourcePath());
			
			Invocation.Builder invocationBuilder = brandTarget
					.request(MediaType.APPLICATION_JSON)
					.header(configurationProperties.getAccessTokenHeader(), tokenInputContext.getToken());
			Response response = invocationBuilder.get();
			return response.readEntity(Orders.class);

		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateProductOption(Option updateOption) {
		try {

			Client client = ClientBuilder.newClient()
					.property(JERSEY_CONFIG_CLIENT_CONNECT_TIMEOUT, configurationProperties.getConnectTimeout())
					.property(JERSEY_CONFIG_CLIENT_READ_TIMEOUT, configurationProperties.getReadTimeout());
			WebTarget brandTarget = client
					.target(new URI(configurationProperties.getApiBaseUri()))
					.path(configurationProperties.getOptionResourcePath())
					.path(updateOption.getId())
					.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
			
			Invocation.Builder invocationBuilder = brandTarget
					.request(MediaType.APPLICATION_JSON)
					.header(configurationProperties.getAccessTokenHeader(), tokenInputContext.getToken());
			Response response = invocationBuilder.method("PATCH", Entity.entity(updateOption, MediaType.APPLICATION_JSON));
			LOG.debug("Update product option quantity - HTTP status code: {}", response.getStatus());
			
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

	}

	public void updateInventoryLevel(InventoryLevel inventoryLevel) {
		try {

			Client client = ClientBuilder.newClient()
					.property(JERSEY_CONFIG_CLIENT_CONNECT_TIMEOUT, configurationProperties.getConnectTimeout())
					.property(JERSEY_CONFIG_CLIENT_READ_TIMEOUT, configurationProperties.getReadTimeout());
			WebTarget brandTarget = client
					.target(new URI(configurationProperties.getApiBaseUri()))
					.path(configurationProperties.getInventoryLevelResourcePath())
					.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
			
			Invocation.Builder invocationBuilder = brandTarget
					.request(MediaType.APPLICATION_JSON)
					.header(configurationProperties.getAccessTokenHeader(), tokenInputContext.getToken());
			Response response = invocationBuilder.method("PATCH", Entity.entity(inventoryLevel, MediaType.APPLICATION_JSON));
			LOG.debug("Update inventory level - HTTP status code: {}", response.getStatus());
			
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
