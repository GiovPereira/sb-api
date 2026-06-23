package br.edu.ifsudestemg.sb.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {
    private String login;
    private String token;
    private String role;
}