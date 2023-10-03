package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "stream_mark")
public class StreamMark {

	@Id
	@Column(nullable = true)
    private int streamMarkId;
	
    @Column(nullable = true)
    private int streamId;

    @Column(nullable = true)
    private int num;

    @Column(nullable = true)
    private int markId;

}
