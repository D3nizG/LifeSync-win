package App.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_preferences")
public class UserPreference {

    @Id
    private String id;
    private String userId;
    private String interestType;
    private String description;
    private LocalDateTime mentionedAt;
    private int mentionCount;

    public UserPreference() {}

    public UserPreference(String id, String userId, String interestType, String description, LocalDateTime mentionedAt, int mentionCount) {
        this.id = id;
        this.userId = userId;
        this.interestType = interestType;
        this.description = description;
        this.mentionedAt = mentionedAt;
        this.mentionCount = mentionCount;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getInterestType() {
        return interestType;
    }
    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public LocalDateTime getMentionedAt() {
        return mentionedAt;
    }
    public void setMentionedAt(LocalDateTime mentionedAt) {
        this.mentionedAt = mentionedAt;
    }


    public int getMentionCount() {
        return mentionCount;
    }
    public void incrementMentionCount() {
        this.mentionCount++;
    }
}
