package com.psl.model;

/*import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor*/
public class JwtResponse {
	private String jwtToken;
	
	public JwtResponse(String token) {
		this.jwtToken=token;
	}
	
	public  String getToken() {
		return jwtToken;
	}
}//class