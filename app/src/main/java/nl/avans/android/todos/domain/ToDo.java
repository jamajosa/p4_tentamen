package nl.avans.android.todos.domain;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

public class ToDo implements Serializable {

    private String title;
    private String contents;
    private String status;
    private DateTime createdAt;

    public ToDo(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.status = null;
        this.createdAt = new DateTime();
    }

    public ToDo(String title, String contents, String status, DateTime createdAt) {
        this.title = title;
        this.contents = contents;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
