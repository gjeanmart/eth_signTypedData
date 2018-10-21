package net.consensys.tools.ethereum.eip712;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TypedDataSignature {

    private TypedDataSignature() {
    }

    public static byte[] generateSignatureHash(List<TypedData> typedData) {
        log.trace("generateSignatureHash(typedData={}) ...", typedData);

        byte[] result = SolidityPackEncoder
                .soliditySHA3(Arrays.asList(new Bytes32(SolidityPackEncoder.soliditySHA3(buildSchema(typedData))),
                        new Bytes32(SolidityPackEncoder.soliditySHA3(buildTypes(typedData)))));

        log.trace("generateSignatureHash(typedData={}) => {}", typedData, result);

        return result;
    }

    private static List<Type> buildSchema(List<TypedData> typedData) {

        return typedData.stream().map((t) -> new Utf8String(t.getType() + " " + t.getName()))
                .collect(Collectors.toList());
    }

    private static List<Type> buildTypes(List<TypedData> typedData) {

        return typedData.stream().map((t) -> {
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
        }).collect(Collectors.toList());
    }

    public static String zeroPadded(byte[] bs, int padding) {
        String s = Numeric.toHexStringNoPrefix(bs);
        BigInteger bi = Numeric.toBigInt(s);
        return Numeric.toHexStringNoPrefixZeroPadded(bi, padding);
    }

    public static String zeroPadded(byte[] bs) {
        return zeroPadded(bs, 64);
    }

    public static String concatSig(SignatureData sig) {
        List<String> signatureElements = Stream.of(sig.getR(), sig.getS()).
                                                map(TypedDataSignature::zeroPadded).
                                                collect(Collectors.toList());

        String r = signatureElements.get(0);
        String s = signatureElements.get(1);
        byte[] v = new byte[] { sig.getV() };
        return Numeric.prependHexPrefix(r + s + Numeric.toHexStringNoPrefix(v));
    }

    public static String signTypedData(org.web3j.crypto.Credentials credentials, List<TypedData> typedData) {

        byte[] messageHash = TypedDataSignature.generateSignatureHash(typedData);

        SignatureData signedMessage = Sign.signMessage(messageHash, credentials.getEcKeyPair(), false);
        return concatSig(signedMessage);
    }
}
