package ec.dev.samagua.ekumen_bank_clients.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Cliente extends Persona {

    @Column(value= "cliente_id")
    private String clienteId;

    @Column(value= "clave")
    private String clave;

    @Column(value= "estado")
    private String estado;

    public static Cliente getDefaultInstance() {
        return Cliente.builder()
                .id(-1L)
                .build();
    }

    public boolean isValidId() {
        return getId() != null && getId() > 0;
    }


}
