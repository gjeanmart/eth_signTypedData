package net.consensys.tools.ethereum.TypedDataSignature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TypedData {
    private String name;
    private String type;
    private Object value; 
}
