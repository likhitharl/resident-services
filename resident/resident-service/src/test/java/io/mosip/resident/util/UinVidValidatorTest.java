package io.mosip.resident.util;

import io.mosip.kernel.core.idvalidator.exception.InvalidIDException;
import io.mosip.kernel.core.idvalidator.spi.UinValidator;
import io.mosip.kernel.core.idvalidator.spi.VidValidator;
import io.mosip.resident.constant.IdType;
import io.mosip.resident.constant.ResidentErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Mock
    private VidValidator<String> vidValidator;

    @Test
    public void testValidateUin() {
        when(uinValidator.validateId(anyString())).thenReturn(true);
        String uin = "123456789012";
        boolean isValid = uinVidValidator.validateUin(uin);
        assert isValid;
    }

    @Test
    public void testValidateUinWithInvalidUin() {
        Mockito.when(uinValidator.validateId(Mockito.any())).thenThrow(new InvalidIDException(ResidentErrorCode.INVALID_UIN.getErrorCode(),
                ResidentErrorCode.INVALID_UIN.getErrorMessage()));
        String uin = "123456789012";
        boolean isValid = uinVidValidator.validateUin(uin);
        assert !isValid;
    }

    @Test
    public void testValidateVid() {
        when(vidValidator.validateId(anyString())).thenReturn(true);
        String vid = "123456789012";
        boolean isValid = uinVidValidator.validateVid(vid);
        assert isValid;
    }

    @Test
    public void testValidateVidWithInvalidVid() {
        Mockito.when(vidValidator.validateId(Mockito.any())).thenThrow(new InvalidIDException(ResidentErrorCode.INVALID_VID.getErrorCode(),
                ResidentErrorCode.INVALID_VID.getErrorMessage()));
        String vid = "123456789012";
        boolean isValid = uinVidValidator.validateVid(vid);
        assert !isValid;
    }

    @Test
    public void testGetIndividualIdTypeWithUin() {
        when(uinVidValidator.validateUin(anyString())).thenReturn(true);
        String individualId = "123456789012";
        IdType idType = uinVidValidator.getIndividualIdType(individualId);
        assert idType == IdType.UIN;
    }

    @Test
    public void testGetIndividualIdTypeWithVid() {
        when(uinVidValidator.validateUin(anyString())).thenReturn(false);
        when(uinVidValidator.validateVid(anyString())).thenReturn(true);
        String individualId = "123456789012";
        IdType idType = uinVidValidator.getIndividualIdType(individualId);
        assert idType == IdType.VID;
    }

    @Test
    public void testGetIndividualIdTypeWithAid() {
        when(uinVidValidator.validateUin(anyString())).thenReturn(false);
        when(uinVidValidator.validateVid(anyString())).thenReturn(false);
        String individualId = "123456789012";
        IdType idType = uinVidValidator.getIndividualIdType(individualId);
        assert idType == IdType.AID;
    }

}
