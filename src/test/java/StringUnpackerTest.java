import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUnpackerTest {

    @Test
    void unpack_test1() {
        String targetString = "2[xy3[z]y]";
        String result = StringUnpacker.unpack(targetString);
        String expected = "xyzzzyxyzzzy";
        assertEquals(expected,result);
    }

    @Test
    void unpack_test2(){
        String targetString = "3[xyz]4[xy]z";
        String result = StringUnpacker.unpack(targetString);
        String expected = "xyzxyzxyzxyxyxyxyz";
        assertEquals(expected,result);
    }

    @Test
    void unpack_test3(){
        String targetString = "2[3[x]y]";
        String result = StringUnpacker.unpack(targetString);
        String expected = "xxxyxxxy";
        assertEquals(expected,result);
    }
}