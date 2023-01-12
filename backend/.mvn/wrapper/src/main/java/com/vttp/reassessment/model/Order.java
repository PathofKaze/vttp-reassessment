package com.vttp.reassessment.model;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Order {
    private String name;
    private String email;
	private String phone;
	private String title;
	private String description;
    private String image;
    private String posting_id;
    private Date sdf;

    public Date getSdf() {
        return sdf;
    }

    public void setSdf(Date sdf) {
        this.sdf = sdf;
    }

    public String getPosting_id() {
        return posting_id;
    }

    public void setPosting_id(String posting_id) {
        this.posting_id = posting_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public String getImage() {
        return image;
    }

    public void setImage(String string) {
        this.image = string;
    }

    public static Order create(String json) {
		Order o = new Order();
		JsonReader reader = Json.createReader(new StringReader(json));
		JsonObject obj = reader.readObject();
		o.setName(obj.getString("name"));
		o.setEmail(obj.getString("email"));
		o.setPhone(obj.getString("phone"));
		o.setTitle(obj.getString("title"));
		o.setDescription(obj.getString("description"));
        o.setImage(obj.getString("image"));
		return o;
	}
}