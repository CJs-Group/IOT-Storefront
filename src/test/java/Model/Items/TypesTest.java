package Model.Items;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TypesTest {
    @Test
    void testValues() {
        for (Types type : Types.values()) {
            assertNotNull(type.name());
        }
    }
}
