package com.example.web.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String email;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @BatchSize(size = 50)
    @Setter
    @JsonManagedReference
    private List<UserRoom> userRoom;

    public User(String email) {
        this.email = email;
    }
}
