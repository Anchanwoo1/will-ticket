package VO;

public class ReviewVO {
    private int reviewId;
    private int userId;
    private int reservationId;
    private int concertId;       // 콘서트 ID 추가
    private int score;
    private String content;

    // 공연 제목 (쿼리 조인으로 받아서 보여주기 위해)
    private String concertTitle;
    
    // 기존 userName 제거하고 name 필드로 변경
    private String name;

    public ReviewVO() {
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConcertTitle() {
        return concertTitle;
    }

    public void setConcertTitle(String concertTitle) {
        this.concertTitle = concertTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}