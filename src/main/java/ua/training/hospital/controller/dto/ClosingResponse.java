package ua.training.hospital.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.metamodel.StaticMetamodel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClosingResponse {
    private String response;
}
