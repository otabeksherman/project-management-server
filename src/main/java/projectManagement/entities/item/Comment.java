package projectManagement.entities.item;

import java.time.LocalDate;

public class Comment {
    Long id;
    Long userId;
    String content;
    LocalDate date;

    public Comment(Long userId, String content) {
        this.userId = userId;
        this.content = content;
        this.date = LocalDate.now();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
