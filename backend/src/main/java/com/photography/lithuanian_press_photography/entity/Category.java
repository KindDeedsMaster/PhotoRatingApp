package com.photography.lithuanian_press_photography.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.photography.lithuanian_press_photography.enums.PhotoSubmissionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "max_total_submissions")
    private long maxTotalSubmissions;

    @Column(name = "max_user_submissions")
    private long maxUserSubmissions;

    @Column(name = "uploaded_photo")
    private List<String> uploadedPhotos;

    @CreatedDate
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private PhotoSubmissionType type;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhotoParticipation> photoParticipation;

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = ZonedDateTime.now();
    }
}
