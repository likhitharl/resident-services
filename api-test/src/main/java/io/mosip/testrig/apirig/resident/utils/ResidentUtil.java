package io.mosip.testrig.apirig.resident.utils;

import org.apache.log4j.Logger;
import org.testng.SkipException;

import io.mosip.testrig.apirig.dto.TestCaseDTO;
import io.mosip.testrig.apirig.resident.testrunner.MosipTestRunner;
import io.mosip.testrig.apirig.testrunner.BaseTestCase;
import io.mosip.testrig.apirig.utils.AdminTestUtil;
import io.mosip.testrig.apirig.utils.ConfigManager;
import io.mosip.testrig.apirig.utils.GlobalConstants;
import io.mosip.testrig.apirig.utils.SkipTestCaseHandler;

public class ResidentUtil extends AdminTestUtil {

	private static final Logger logger = Logger.getLogger(ResidentUtil.class);
	
	public static String isTestCaseValidForExecution(TestCaseDTO testCaseDTO) {
		String testCaseName = testCaseDTO.getTestCaseName();
		
		int indexof = testCaseName.indexOf("_");
		String modifiedTestCaseName = testCaseName.substring(indexof + 1);

		addTestCaseDetailsToMap(modifiedTestCaseName, testCaseDTO.getUniqueIdentifier());
		
		if (MosipTestRunner.skipAll == true) {
			throw new SkipException(GlobalConstants.PRE_REQUISITE_FAILED_MESSAGE);
		}
		
		if (SkipTestCaseHandler.isTestCaseInSkippedList(testCaseName)) {
			throw new SkipException(GlobalConstants.KNOWN_ISSUES);
		}
		
		if ((ConfigManager.isInServiceNotDeployedList(GlobalConstants.ESIGNET))
				&& BaseTestCase.currentModule.equalsIgnoreCase("resident") && testCaseName.contains("_SignJWT_")) {
			throw new SkipException("esignet module is not deployed");
		}

		if ((ConfigManager.isInServiceNotDeployedList(GlobalConstants.ESIGNET))
				&& BaseTestCase.currentModule.equalsIgnoreCase("resident")
				&& (testCaseDTO.getRole() != null && (testCaseDTO.getRole().equalsIgnoreCase("residentNew")
						|| testCaseDTO.getRole().equalsIgnoreCase("residentNewVid")))) {
			throw new SkipException("esignet module is not deployed");
		}
		if (BaseTestCase.currentModule.equalsIgnoreCase(GlobalConstants.RESIDENT)) {
			if (testCaseDTO.getRole() != null && (testCaseDTO.getRole().equalsIgnoreCase(GlobalConstants.RESIDENTNEW)
					|| testCaseDTO.isValidityCheckRequired())) {
				if (testCaseName.contains("uin") || testCaseName.contains("UIN") || testCaseName.contains("Uin")) {
					if (BaseTestCase.getSupportedIdTypesValueFromActuator().contains("UIN")
							&& BaseTestCase.getSupportedIdTypesValueFromActuator().contains("uin")) {
						throw new SkipException("Idtype UIN not supported skipping the testcase");
					}
				}
			} else if (testCaseDTO.getRole() != null && (testCaseDTO.getRole().equalsIgnoreCase("residentNewVid")
					|| testCaseDTO.isValidityCheckRequired())) {
				if (testCaseName.contains("vid") || testCaseName.contains("VID") || testCaseName.contains("Vid")) {
					if (BaseTestCase.getSupportedIdTypesValueFromActuator().contains("VID")
							&& BaseTestCase.getSupportedIdTypesValueFromActuator().contains("vid")) {
						throw new SkipException("Idtype VID not supported skipping the testcase");
					}
				}
			}
		}
		
		
		
		
		
		return testCaseName;
	}
	
}