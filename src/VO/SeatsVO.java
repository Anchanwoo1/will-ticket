package VO;

public class SeatsVO {
	private int seatId;
	private int concertId;
	private String seatNumber;
	private String seatType;
	private int price;
	private String isAvailable;
	private Integer userId;

	public SeatsVO() {
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getConcertId() {
		return concertId;
	}

	public void setConcertId(int concertId) {
		this.concertId = concertId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
