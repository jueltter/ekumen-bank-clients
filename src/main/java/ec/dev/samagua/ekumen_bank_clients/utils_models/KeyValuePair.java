package ec.dev.samagua.ekumen_bank_clients.utils_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyValuePair<E, T> {
    private E key;
    private T value;
}
