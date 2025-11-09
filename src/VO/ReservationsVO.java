package VO;

import java.util.Date;

public class ReservationsVO {
	private int reservationId;
	private int userId;
	private int concertId;
	private int seatId;
	private Date reserveDate;
	private String status;

	// 추가 필드 (예약 상세 조회용)
	private String concertTitle;
	private String concertLocation;
	private String concertStartTime;
	private String seatNumber;

	public ReservationsVO() {
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getConcertId() {
		return concertId;
	}

	public void setConcertId(int concertId) {
		this.concertId = concertId;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public Date getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// ------ 추가된 getter/setter들 ------

	public String getConcertTitle() {
		return concertTitle;
	}

	public void setConcertTitle(String concertTitle) {
		this.concertTitle = concertTitle;
	}

	public String getConcertLocation() {
		return concertLocation;
	}

	public void setConcertLocation(String concertLocation) {
		this.concertLocation = concertLocation;
	}

	public String getConcertStartTime() {
		return concertStartTime;
	}

	public void setConcertStartTime(String concertStartTime) {
		this.concertStartTime = concertStartTime;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
}
