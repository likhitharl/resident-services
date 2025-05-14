package io.mosip.resident.util;

import io.mosip.kernel.core.idvalidator.spi.UinValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/*
    *
    * @author Kamesh Shekhar Prasad
 */

@RunWith(MockitoJUnitRunner.class)
public class UinVidValidatorTest {

    @InjectMocks
    private UinVidValidator uinVidValidator = new UinVidValidator();

    @Mock
    private UinValidator<String> uinValidator;

    @Test
    public void testValidateUin() {
        when(uinValidator.validateId(anyString())).thenReturn(true);
        String uin = "123456789012";
        boolean isValid = uinVidValidator.validateUin(uin);
        assert isValid;
    }

}
