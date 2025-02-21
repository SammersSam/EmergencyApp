package com.example.emergencyapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmergencyCall implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Caller callerDetails;
    private String location;
    private EmergencyType emergencyType;
    private LocalDateTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmergencyCall that = (EmergencyCall) o;
        return Objects.equals(id, that.id) && Objects.equals(callerDetails, that.callerDetails) &&
                Objects.equals(location, that.location) && emergencyType == that.emergencyType &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, callerDetails, location, emergencyType, time);
    }

    @Override
    public String toString() {
        return "EmergencyCall{" +
                "id=" + id +
                ", callerDetails=" + callerDetails +
                ", location='" + location + '\'' +
                ", emergencyType=" + emergencyType +
                ", time=" + time +
                '}';
    }
}
