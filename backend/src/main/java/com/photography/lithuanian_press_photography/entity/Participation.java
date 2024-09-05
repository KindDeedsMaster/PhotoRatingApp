package com.photography.lithuanian_press_photography.entity;

import com.photography.lithuanian_press_photography.enums.ParticipationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "participation")
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ParticipationStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "contest_id", nullable = false)
    private Contest contest;

    @CreatedDate
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = ZonedDateTime.now();
    }
}
