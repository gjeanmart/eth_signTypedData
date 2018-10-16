package net.consensys.tools.ethereum.eip712;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Bytes;
import org.web3j.abi.datatypes.NumericType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Hash;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SolidityPackEncoder {

    private SolidityPackEncoder() {
    }

    public static byte[] soliditySHA3(List<Type> types) {
        return Hash.sha3(solidityPack(types));
    }

    public static byte[] solidityPack(List<Type> types) {
        log.debug("solidityPack(types={})", types);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        types.forEach(t -> {
            byte[] val = encodeType(t);
            baos.write(val, 0, val.length);
        });

        log.debug("solidityPack(types={}) => {}", types, DatatypeConverter.printHexBinary(baos.toByteArray()));
        return baos.toByteArray();
    }

    public static byte[] encodeType(Type type) {

        if (type instanceof Utf8String) {
            return encodeUtf8String((Utf8String) type);

        } else if (type instanceof Bool) {
            return encodeBool((Bool) type);

        } else if (type instanceof NumericType) {
            return encodeNumeric((NumericType) type);

        } else if (type instanceof Bytes) {
            return encodeBytes((Bytes) type);

        } else {
            throw new UnsupportedOperationException("Type cannot be encoded: " + type.getClass());
        }

    }

    public static byte[] encodeBool(Bool type) {
        if (type == null)
            throw new IllegalArgumentException();

        byte[] res = type.getValue() ? new byte[] { (byte) 1 } : new byte[] { (byte) 0 };

        log.trace("encodeBool(val={}) => {}", type, DatatypeConverter.printHexBinary(res));
        return res;
    }

    public static byte[] encodeUtf8String(Utf8String type) {
        if (type == null)
            throw new IllegalArgumentException();

        byte[] res = type.getValue().getBytes();

        log.trace("encodeUtf8String(val={}) => {}", type, DatatypeConverter.printHexBinary(res));
        return res;
    }

    public static byte[] encodeNumeric(NumericType type) {
        if (type == null)
            throw new IllegalArgumentException();

        byte[] res = DatatypeConverter.parseHexBinary(TypeEncoder.encode(type));

        log.trace("encodeNumeric(val={}) => {}", type, DatatypeConverter.printHexBinary(res));
        return res;
    }

    public static byte[] encodeBytes(Bytes type) {
        if (type == null)
            throw new IllegalArgumentException();

        byte[] res = DatatypeConverter.parseHexBinary(TypeEncoder.encode(type));

        log.trace("encodeNumeric(val={}) => {}", type, DatatypeConverter.printHexBinary(res));
        return res;
    }

}
