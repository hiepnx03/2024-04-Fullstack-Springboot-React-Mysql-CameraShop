package com.example.camerashop_be.dto;

public enum SocialProvider {
	FACEBOOK("facebook"),
	TWITTER("twitter"),
	LINKEDIN("linkedin"),
	GOOGLE("google"),
	GITHUB("github"),
	LOCAL("local");

	private String providerType;

	public String getProviderType() {
		return providerType;
	}

	SocialProvider(final String providerType) {
		this.providerType = providerType;
	}
}
