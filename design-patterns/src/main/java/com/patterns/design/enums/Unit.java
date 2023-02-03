package com.patterns.design.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Unit {
	
	HAPPY_AROUND("Happy Around!"),
	PEAKY_PKEY("Peaky P-key"),
	PHOTON_MAIDEN("Photon Maiden"),
	MERM4ID("Merm4id"),
	RONDO("Rondo"),
	LYRICAL_LILY("Lyrical Lily"), 
	CALL_OF_ARTEMIS("Call of Artemis"),
	ABYSSMARE("Abyssmare"),
	UNICHORD("UniChØrd");
	
	private String name;
	
}
