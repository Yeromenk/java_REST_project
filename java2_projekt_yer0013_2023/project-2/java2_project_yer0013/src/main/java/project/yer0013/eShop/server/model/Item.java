package project.yer0013.eShop.server.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity //TODO JPA
@ToString
@EqualsAndHashCode
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter //TODO lombok
    @Setter //TODO lombok
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Double price;
    @Getter
    @Setter
    private Double rating;
    @Getter
    @Setter
    private Integer ratingCount;

    @Getter
    @Setter
    @ManyToOne
    private Category category;

    public void updateRating(double rating) {
        ratingCount += 1;
        this.rating = ((this.rating * (ratingCount - 1)) + rating) / ratingCount;
    }
}
