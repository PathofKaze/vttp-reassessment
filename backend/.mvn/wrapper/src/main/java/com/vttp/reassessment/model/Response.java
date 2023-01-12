package com.vttp.reassessment.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Response {
	private Integer posting_id;
	private String name;
	private String email;

	public void setOrderId(Integer posting_id) { this.posting_id = posting_id; }
	public Integer getOrderId() { return this.posting_id; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }


	public JsonObject toJson() {
		return Json.createObjectBuilder()
		.add("orderId", posting_id)
		.add("name", name)
		.add("email", email)
		.build();
	}
	public void setCode(int i) {
	}
    public void setMessage(String formatted) {
    }

}           
