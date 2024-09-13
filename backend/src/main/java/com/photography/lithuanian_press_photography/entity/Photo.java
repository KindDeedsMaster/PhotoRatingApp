package com.photography.lithuanian_press_photography.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "photos")
public class Photo {

    @Id
    @Column(name = "id")
    // This is NOT auto-generated
    private UUID id;

    private String localPathFull;
//    private String localPathMiddle;
//    private String localPathSmall;
    private String localPathThumbnail;
    private String urlFull;
//    private String urlMiddle;
//    private String urlSmall;
    private String urlThumbnail;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "photo_participation_id", nullable = false)
    private PhotoParticipation photoParticipation;

    private ZonedDateTime createdAt;
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
