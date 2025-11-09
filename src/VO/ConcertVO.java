package VO;

import java.util.Date;

public class ConcertVO {
	private int concertId;
	private String title;
	private String category;
	private String location;
	private Date concertDate;
	private String caster;
	private String info;
	private String startTime;
	private Date createdAt;
	private String image;

	public ConcertVO() {
	}

	public int getConcertId() {
		return concertId;
	}

	public void setConcertId(int concertId) {
		this.concertId = concertId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getConcertDate() {
		return concertDate;
	}

	public void setConcertDate(Date concertDate) {
		this.concertDate = concertDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCaster() {
		return caster;
	}

	public void setCaster(String caster) {
		this.caster = caster;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
}