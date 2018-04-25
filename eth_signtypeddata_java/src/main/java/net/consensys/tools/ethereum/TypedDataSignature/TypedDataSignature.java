package net.consensys.tools.ethereum.TypedDataSignature;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class TypedDataSignature {

    public static byte[] generateSignatureHash(List<TypedData> typedData) {
	log.trace("generateSignatureHash(typedData={}) ...", typedData);
	
	byte[] result = SolidityPackEncoder.soliditySHA3(
		Arrays.asList(
			new Bytes32(SolidityPackEncoder.soliditySHA3(buildSchema(typedData))),
			new Bytes32(SolidityPackEncoder.soliditySHA3(buildTypes(typedData)))
		)
	);
	
	log.trace("generateSignatureHash(typedData={}) => {}", typedData, result);
	
	return result;
    }
    
    private static List<Type> buildSchema(List<TypedData> typedData) {
	
	return typedData
		.stream() 
		.map( (t) -> new Utf8String(t.getType() + " " + t.getName())  )
		.collect(Collectors.toList());
    }
    
    private static List<Type> buildTypes(List<TypedData> typedData) {
	
	return typedData
		.stream() 
		.map( (t) -> {
		    Type type = null;
		    switch (t.getType().toLowerCase()) {
		    case "string":
			type = new Utf8String((String) t.getValue());
			break;
			
		    case "uint":
			type = new Uint(new BigInteger(String.valueOf(t.getValue())));
			break;
			
		    case "bool":
			type = new Bool((boolean) t.getValue());
			break;

		    default:
			log.error("Unknow type [{}]", t.getType().toLowerCase());
			throw new IllegalArgumentException();
		    }
		    
		    return type;
		})
		.collect(Collectors.toList());
    }
    

    

}
