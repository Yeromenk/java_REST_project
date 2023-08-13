package project.yer0013.eShop.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@Entity
@ToString
@EqualsAndHashCode
public class OrderCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Integer status;
    @Getter
    @Setter
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") //TODO prace s datum
    private Date completionDate;

    @Getter
    @Setter
    @ManyToOne
    private Item item;
}
