package com.lta.springboot.student_management.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student")
    @EqualsAndHashCode.Include
    Long idStudent;

    @Column(nullable = false, length = 50)
    String name;

    @Column(name = "last_name", nullable = false, length = 50)
    String lastName;

    @Column(nullable = false, length = 50, unique = true)
    String email;

    @Setter(AccessLevel.NONE)
    @Column(name = "created_at", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime createdAt;

    @Setter(AccessLevel.NONE)
    @Column(name = "update_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime updateAt;

    @PrePersist
    private void beforePersisting() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void notificationDate() {
        this.updateAt = LocalDateTime.now();
    }

}
