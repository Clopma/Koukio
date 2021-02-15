package com.carloslopez.koukiotest.Entities;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Post")
public class Post {

    @Id
    private int id;

    private String title;

    @Column(length = 10000)
    private String description;

    private Date publication;

    private String imageUrl;

}
