package net.consensys.tools.ethereum.eip712;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;

import lombok.extern.slf4j.Slf4j;
import net.consensys.tools.ethereum.eip712.SolidityPackEncoder;

@Slf4j
public class SolidityPackEncoderTest {

    @Test
    public void encodeUtf8StringTypeTest() {
	log.info("###########################################################");
	log.info("[START] encodeUtf8StringTypeTest");
	String val = "val1";
	Utf8String field = new Utf8String(val);
	
	byte[] result = SolidityPackEncoder.encodeType(field);
	log.info(new String(result));
	
	assertEquals(DatatypeConverter.printHexBinary(val.getBytes()), DatatypeConverter.printHexBinary(result));
	
	log.info("[END] encodeUtf8StringTypeTest");
    }
    
    @Test
    public void encodeBoolTypeTest() {
	log.info("###########################################################");
	log.info("[START] encodeBoolTypeTest");
	Boolean val = true;
	Bool field = new Bool(val);
	
	byte[] result = SolidityPackEncoder.encodeType(field);
	log.info(DatatypeConverter.printHexBinary(result));
	
	assertEquals(DatatypeConverter.printHexBinary(new byte[] {(byte)1}), DatatypeConverter.printHexBinary(result));
	
	log.info("[END] encodeBoolTypeTest");
    }
    
    @Test
    public void encodeNumericTypeTest() {
	log.info("###########################################################");
	log.info("[START] encodeBoolTypeTest");
	String val = "5";
	Uint field = new Uint(new BigInteger(val));
	
	byte[] result = SolidityPackEncoder.encodeType(field);
	log.info(DatatypeConverter.printHexBinary(result));
	
	assertEquals("0000000000000000000000000000000000000000000000000000000000000005", DatatypeConverter.printHexBinary(result));
	
	log.info("[END] encodeBoolTypeTest");
    }
    
    @Test
    public void solidityPackTest1() {
	log.info("###########################################################");
	log.info("[START] solidityPackTest1");
	String val1 = "val1";
	Utf8String field1 = new Utf8String(val1);
	String val2 = "val2";
	Utf8String field2 = new Utf8String(val2);
	
	
	byte[] result = SolidityPackEncoder.solidityPack(Arrays.asList(field1, field2));
	log.info(DatatypeConverter.printHexBinary(result));
	
	assertEquals(DatatypeConverter.printHexBinary((val1+val2).getBytes()), DatatypeConverter.printHexBinary(result));
	
	log.info("[END] solidityPackTest1");
    }
    
    @Test
    public void solidityPackTest2() {
	log.info("###########################################################");
	log.info("[START] solidityPackTest2");
	Utf8String field1 = new Utf8String("val1");
	Uint field2 = new Uint(new BigInteger("5"));
	Bool field3 = new Bool(true);
	
	
	
	byte[] result = SolidityPackEncoder.solidityPack(Arrays.asList(field1, field2, field3));
	log.info(DatatypeConverter.printHexBinary(result));
	
	assertEquals(
		"76616C31000000000000000000000000000000000000000000000000000000000000000501", 
		DatatypeConverter.printHexBinary(result));
	
	log.info("[END] solidityPackTest2");
    }
    
    @Test
    public void soliditySha3Test() {
	log.info("###########################################################");
	log.info("[START] soliditySha3Test");
	Utf8String field1 = new Utf8String("val1");
	Uint field2 = new Uint(new BigInteger("5"));
	Bool field3 = new Bool(true);
	
	
	
	byte[] hash = SolidityPackEncoder.soliditySHA3(Arrays.asList(field1, field2, field3));
	log.info(DatatypeConverter.printHexBinary(hash));
	
	assertEquals(
		"9918c2158639e8f34d0b94768e9701c900bd932dcb79d76c88dfeab17019c451", 
		DatatypeConverter.printHexBinary(hash).toLowerCase());
	
	log.info("[END] soliditySha3Test");
    }
    
    
    
}
