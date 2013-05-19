package teammates.test.cases;

import static org.testng.AssertJUnit.*;
import static teammates.common.Common.EOL;
import static teammates.common.FieldValidator.*;


import org.testng.annotations.Test;
import com.google.appengine.api.datastore.Text;

import teammates.common.Common;
import teammates.common.datatransfer.SubmissionAttributes;

public class SubmissionAttributesTest extends BaseTestCase {


	@Test
	public void testValidate() {
		SubmissionAttributes s = new SubmissionAttributes();
		
		s.course = "valid-course";
		s.evaluation = "valid-evaluation";
		s.reviewer = "valid.reviewer@gmail.com";
		s.reviewee = "valid.reviewee@gmail.com";
		s.team = "valid-team";
		s.justification = new Text("");
		s.p2pFeedback = null; //this can be null

		assertEquals("valid values, minimal properties", true, s.isValid());

		s.points = 10;
		s.p2pFeedback = new Text("valid-feedback");
		
		assertEquals("valid values, all properties", true, s.isValid());
		
		assertEquals("not self evaluation", false, s.isSelfEvaluation());
		s.reviewee = s.reviewer;
		assertEquals("not self evaluation", true, s.isSelfEvaluation());

		
		s.justification = null;
		try {
			s.getInvalidStateInfo();
			throw new RuntimeException("Assumption violation not detected");
		} catch (AssertionError e1) {
			assertTrue(true);
		}
		
		s.justification = new Text("");
		
		s.course = "invalid course id";
		s.evaluation = "";
		s.reviewer = "";
		s.reviewee = "";
		s.team = "valid-team";
		
		assertEquals("valid values, all properties", false, s.isValid());
		String errorMessage = 
				String.format(COURSE_ID_ERROR_MESSAGE, s.course, REASON_INCORRECT_FORMAT) + EOL 
				+ String.format(EVALUATION_NAME_ERROR_MESSAGE, s.evaluation, REASON_EMPTY) + EOL 
				+ "Invalid email address for the student receiving the evaluation: "+ String.format(EMAIL_ERROR_MESSAGE, s.reviewer, REASON_EMPTY) + EOL
				+ "Invalid email address for the student giving the evaluation: "+ String.format(EMAIL_ERROR_MESSAGE, s.reviewee, REASON_EMPTY);
		assertEquals("valid values", errorMessage, Common.toString(s.getInvalidStateInfo()));

	}
	
	@Test
	public void testIsSelfvaluation(){
	    //already tested in testValidate() above
	}
	
	@Test
	public void testGetInvalidStateInfo(){
	    //already tested in testValidate() above
	}
	
	@Test
	public void testIsValid(){
	    //already tested in testValidate() above
	}
	
	//TODO: test toString() 

}