package ua.reed.aws.nasa.pic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "camera")
@Builder
@Entity
@Table(name = "picture")
public class Picture {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "size")
    private long size;

    @JoinColumn(name = "camera_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Camera camera;
}
