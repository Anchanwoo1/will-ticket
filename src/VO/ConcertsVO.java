package VO;

import java.sql.Date;

public class ConcertsVO {
    private int concertId;
    private String title;
    private String category;
    private String location;
    private Date concertDate;
    private String startTime;
    private Date createdAt;

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

    @Override
    public String toString() {
        return "ConcertsVO [concertId=" + concertId + ", title=" + title + ", category=" + category + ", location="
                + location + ", concertDate=" + concertDate + ", startTime=" + startTime + ", createdAt="
                + createdAt + "]";
    }
}
