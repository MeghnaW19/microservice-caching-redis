package com.stackroute.caching.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Track implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @Column
    private String comments;

}
