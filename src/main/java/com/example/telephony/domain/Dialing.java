package com.example.telephony.domain;

import com.example.telephony.converter.DialingStatusConverter;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.enums.DialingStatus;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "dialing")
@Data
public class Dialing extends BaseEntity {
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "status_code")
    @Convert(converter = DialingStatusConverter.class)
    private DialingStatus status;

    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    @Column(name = "caller_base_id")
    private Long callersBaseId;

    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Dialing dialing = (Dialing) o;
        return getId() != null && Objects.equals(getId(), dialing.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
