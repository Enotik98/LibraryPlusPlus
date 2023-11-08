package com.libraryplusplus.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

@TypeDef(name = "request_status", typeClass = SQLEnumType.class)
@Table(name = "extension_requests", schema = "public")
@Data
@Entity(name = "extension_requests")
@Getter
@Setter
public class ExtensionRequest {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_date")
    private Date request_date;
    @Enumerated(EnumType.STRING)
    @Type(type = "request_status")
    @Column(name = "status")
    private RequestStatus status;

    @Temporal(TemporalType.DATE)
    @Column(name = "new_return_date")
    private Date new_return_date;

    @Column(name = "message")
    private String message;

    @PrePersist
    public void setDefaultValues(){
        if (status == null){
            status = RequestStatus.PENDING;
        }
        if (request_date == null){
            request_date = new Date();
        }
    }


}
