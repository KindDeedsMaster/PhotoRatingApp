package com.photography.lithuanian_press_photography.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "contests")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(exclude = "participation")
@ToString(exclude = "participation")
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "thumbnail_url")
    private String thumbnailURL;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "max_total_submissions")
    private long maxTotalSubmissions;

    @Column(name = "max_user_submissions")
    private long maxUserSubmissions;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @CreatedDate
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participation> participation;

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = ZonedDateTime.now();
    }
}
