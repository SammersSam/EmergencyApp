package com.example.emergencyapp.emergencycall.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmergencyResource implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ResourcesType type;
    @Enumerated(EnumType.STRING)
    private ResourcesStatusType resourcesStatusType;
    private String location;
    @OneToOne
    @JoinColumn(name = "call_id")
    private EmergencyCall emergencyCall;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmergencyResource that = (EmergencyResource) o;
        return Objects.equals(id, that.id) && type == that.type && resourcesStatusType == that.resourcesStatusType &&
                Objects.equals(location, that.location) && Objects.equals(emergencyCall, that.emergencyCall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, resourcesStatusType, location, emergencyCall);
    }

    @Override
    public String toString() {
        return "EmergencyResource{" +
                "id=" + id +
                ", type=" + type +
                ", resourcesStatusType=" + resourcesStatusType +
                ", location='" + location + '\'' +
                ", emergencyCall=" + emergencyCall +
                '}';
    }
}
